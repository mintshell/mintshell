/*
 * Copyright © 2017-2018 mintshell.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.mintshell.terminal.ssh.interfaces;

import static java.lang.String.format;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.sshd.common.channel.PtyMode;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.shell.TtyFilterInputStream;
import org.mintshell.assertion.Assert;
import org.mintshell.command.CommandResult;
import org.mintshell.dispatcher.CommandDispatcher;
import org.mintshell.interpreter.CommandInterpreter;
import org.mintshell.terminal.Key;
import org.mintshell.terminal.KeyBinding;
import org.mintshell.terminal.interfaces.BaseTerminalCommandInterface;
import org.mintshell.terminal.interfaces.TerminalCommandInterface;

/**
 * Implementation of a {@link TerminalCommandInterface} using SSH and representing a concrete SSH session.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class SshCommandInterfaceSession extends BaseTerminalCommandInterface implements org.apache.sshd.server.Command {

  private AnsiKeyFilterInputStream in;
  private OutputStream out;
  private Future<?> task;
  private ExitCallback exitCallback;
  private final SessionRegistry sessionRegistry;
  private final ExecutorService executor;
  private final CommandInterpreter commandInterpreter;
  private final CommandDispatcher commandDispatcher;

  /**
   * Creates a new instance.
   *
   * @param sessionRegistry
   *          session registry
   * @param executor
   *          executor service to run within
   * @param commandInterpreter
   *          {@link CommandInterpreter} which would be usually propagated though
   *          {@link #activate(CommandInterpreter, CommandDispatcher)}
   * @param commandDispatcher
   *          {@link CommandDispatcher} which would be usually propagated though
   *          {@link #activate(CommandInterpreter, CommandDispatcher)}
   * @param banner
   *          welcome banner
   * @param commandSubmissionKey
   *          key that issues a command submission
   * @param keyBindings
   *          (optional) {@link KeyBinding}s
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public SshCommandInterfaceSession(final SessionRegistry sessionRegistry, final ExecutorService executor, final CommandInterpreter commandInterpreter,
      final CommandDispatcher commandDispatcher, final String banner, final Key commandSubmissionKey, final KeyBinding... keyBindings) {
    super(DEFAULT_PROMPT_STOP, banner, commandSubmissionKey, keyBindings);
    this.sessionRegistry = Assert.ARG.isNotNull(sessionRegistry, "[sessionRegistry] must not be [null]");
    this.executor = Assert.ARG.isNotNull(executor, "[executor] must not be [null]");
    this.commandInterpreter = Assert.ARG.isNotNull(commandInterpreter, "[commandInterpreter] must not be [null]");
    this.commandDispatcher = Assert.ARG.isNotNull(commandDispatcher, "[commandDispatcher] must not be [null]");
    sessionRegistry.register(this);
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.BaseTerminalCommandInterface#deactivate()
   */
  @Override
  public void deactivate() {
    if (this.task != null) {
      this.task.cancel(true);
    }
    super.deactivate();
    if (this.exitCallback != null) {
      this.exitCallback.onExit(0);
    }
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.apache.sshd.server.CommandLifecycle#destroy()
   */
  @Override
  public void destroy() throws Exception {
    this.deactivate();
    this.sessionRegistry.unregister(this);
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#eraseNext()
   */
  @Override
  public void eraseNext() {
    try {
      this.out.write(AnsiControlCommand.DELETE_SINGLE_CHARACTER.getSequence());
      this.out.flush();
    } catch (final IOException e) {
      throw new IllegalStateException("Failed to erase next character");
    }
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#erasePrevious()
   */
  @Override
  public void erasePrevious() {
    try {
      this.out.write(AnsiKey.LEFT.getSequence());
      this.out.flush();
      this.out.write(AnsiControlCommand.DELETE_SINGLE_CHARACTER.getSequence());
      this.out.flush();
    } catch (final IOException e) {
      throw new IllegalStateException("Failed to erase previous character");
    }
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#moveNext()
   */
  @Override
  public void moveNext() {
    try {
      this.out.write(AnsiKey.RIGHT.getSequence());
      this.out.flush();
    } catch (final IOException e) {
      throw new IllegalStateException("Failed to move cursor to next position");
    }
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#movePrevious()
   */
  @Override
  public void movePrevious() {
    try {
      this.out.write(AnsiKey.LEFT.getSequence());
      this.out.flush();
    } catch (final IOException e) {
      throw new IllegalStateException("Failed to move cursor to next position");
    }
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#newLine()
   */
  @Override
  public void newLine() {
    this.print("\n\r");
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#print(java.lang.String)
   */
  @Override
  public void print(final String text) {
    try {
      for (final byte b : text.getBytes()) {
        if (this.getCursorColumn() > 0) {
          this.out.write(AnsiControlCommand.INSERT_SINGLE_CHARACTER.getSequence());
        }
        this.out.write(b);
      }
      this.out.flush();
    } catch (final IOException e) {
      throw new IllegalStateException(format("Failed to print text [%s]", text));
    }
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#readKey()
   */
  @Override
  public Key readKey() {
    try {
      final AnsiKey ansiKey = this.in.readKey();
      return ansiKey.getKey();
    } catch (final IOException e) {
      return Key.UNDEFINED;
    }
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.apache.sshd.server.Command#setErrorStream(java.io.OutputStream)
   */
  @Override
  public void setErrorStream(final OutputStream err) {
    // TODO: find usage of STDERR
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.apache.sshd.server.Command#setExitCallback(org.apache.sshd.server.ExitCallback)
   */
  @Override
  public void setExitCallback(final ExitCallback exitCallback) {
    this.exitCallback = exitCallback;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.apache.sshd.server.Command#setInputStream(java.io.InputStream)
   */
  @Override
  public void setInputStream(final InputStream in) {
    this.in = new AnsiKeyFilterInputStream(new TtyFilterInputStream(in, EnumSet.of(PtyMode.ECHO, PtyMode.ICRNL, PtyMode.ONLCR)));
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.apache.sshd.server.Command#setOutputStream(java.io.OutputStream)
   */
  @Override
  public void setOutputStream(final OutputStream out) {
    this.out = out;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.apache.sshd.server.CommandLifecycle#start(org.apache.sshd.server.Environment)
   */
  @Override
  public void start(final Environment env) throws IOException {
    this.task = this.executor.submit(() -> {
      SshCommandInterfaceSession.this.activate(SshCommandInterfaceSession.this.commandInterpreter, SshCommandInterfaceSession.this.commandDispatcher);
      try {
        SshCommandInterfaceSession.this.out.write(AnsiControlCommand.SET_EDIT_EXTEND_MODE.getSequence());
        SshCommandInterfaceSession.this.out.flush();
      } catch (final IOException e) {
        throw new IllegalStateException("Failed to set edit extend mode");
      }
      return null;
    });
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.BaseTerminalCommandInterface#clearScreen()
   */
  @Override
  protected void clearScreen() {
    try {
      this.out.write(AnsiControlCommand.ERASE_ENTIRE_SCREEN.getSequence());
      this.out.flush();
    } catch (final IOException e) {
      throw new IllegalStateException("Failed to clear screen");
    }
    this.moveCursor(0, 0);
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.BaseTerminalCommandInterface#moveCursor(int, int)
   */
  @Override
  protected void moveCursor(final int row, final int col) {
    try {
      this.out.write(AnsiControlCommand.MOVE_CURSOR.getSequence(row, col));
      this.out.flush();
    } catch (final IOException e) {
      throw new IllegalStateException("Failed to move cursor");
    }
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.BaseCommandInterface#postCommand(org.mintshell.command.CommandResult)
   */
  @Override
  protected void postCommand(final CommandResult<?> result) {
    super.postCommand(result);
  }
}
