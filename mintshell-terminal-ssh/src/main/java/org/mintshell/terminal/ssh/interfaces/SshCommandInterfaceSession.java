package org.mintshell.terminal.ssh.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.concurrent.Future;

import org.apache.sshd.common.channel.PtyMode;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.shell.TtyFilterInputStream;
import org.mintshell.CommandDispatcher;
import org.mintshell.CommandInterpreter;
import org.mintshell.terminal.Key;
import org.mintshell.terminal.KeyBinding;
import org.mintshell.terminal.interfaces.AbstractTerminalCommandInterface;

public class SshCommandInterfaceSession extends AbstractTerminalCommandInterface implements Command {

  private AnsiKeyFilterInputStream in;
  private OutputStream out;
  private Future<?> task;
  private final CommandInterpreter commandInterpreter;
  private final CommandDispatcher commandDispatcher;

  public SshCommandInterfaceSession(final CommandInterpreter commandInterpreter, final CommandDispatcher commandDispatcher, final String prompt,
      final String banner, final Key commandSubmissionKey, final KeyBinding... keyBindings) {
    super(prompt, banner, commandSubmissionKey, keyBindings);
    this.commandInterpreter = commandInterpreter;
    this.commandDispatcher = commandDispatcher;
  }

  /**
   *
   * @{inheritDoc}
   * @see org.apache.sshd.server.CommandLifecycle#destroy()
   */
  @Override
  public void destroy() throws Exception {
    if (this.task != null && !this.task.isDone() && !this.task.isCancelled()) {
      this.task.cancel(true);
    }
    this.task = null;
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#print(java.lang.String)
   */
  @Override
  public void print(final String text) {
    try {
      this.out.write(text.getBytes());
      this.out.flush();
    } catch (final IOException e) {
      throw new IllegalStateException(String.format("Failed to print text [%s]", text));
    }
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#println(java.lang.String)
   */
  @Override
  public void println(final String text) {
    try {
      this.out.write(text.getBytes());
      this.out.write("\n\r".getBytes());
      this.out.flush();
    } catch (final IOException e) {
      throw new IllegalStateException(String.format("Failed to print text [%s]", text));
    }
  }

  /**
   *
   * @{inheritDoc}
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
   * @{inheritDoc}
   * @see org.apache.sshd.server.Command#setErrorStream(java.io.OutputStream)
   */
  @Override
  public void setErrorStream(final OutputStream err) {
    // TODO: find usage of STDERR
  }

  /**
   *
   * @{inheritDoc}
   * @see org.apache.sshd.server.Command#setExitCallback(org.apache.sshd.server.ExitCallback)
   */
  @Override
  public void setExitCallback(final ExitCallback callback) {
    // TODO: make sure that an exit command also notifies this callback
  }

  /**
   *
   * @{inheritDoc}
   * @see org.apache.sshd.server.Command#setInputStream(java.io.InputStream)
   */
  @Override
  public void setInputStream(final InputStream in) {
    this.in = new AnsiKeyFilterInputStream(new TtyFilterInputStream(in, EnumSet.of(PtyMode.ECHO, PtyMode.ICRNL, PtyMode.ONLCR)));
  }

  /**
   *
   * @{inheritDoc}
   * @see org.apache.sshd.server.Command#setOutputStream(java.io.OutputStream)
   */
  @Override
  public void setOutputStream(final OutputStream out) {
    this.out = out;
  }

  @Override
  public void start(final Environment env) throws IOException {
    super.activate(this.commandInterpreter, this.commandDispatcher);
  }

  @Override
  protected void clearScreen() {
    // TODO implement method clearScreen

  }

  @Override
  protected void moveCursor(final int row, final int col) {
    // TODO implement method moveCursor

  }

}
