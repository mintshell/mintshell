/*
 * Copyright © 2017 mintshell.org
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumSet;

import org.apache.sshd.common.channel.PtyMode;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.shell.TtyFilterInputStream;
import org.mintshell.CommandDispatcher;
import org.mintshell.CommandInterpreter;
import org.mintshell.annotation.Nullable;
import org.mintshell.terminal.Key;
import org.mintshell.terminal.KeyBinding;
import org.mintshell.terminal.interfaces.AbstractTerminalCommandInterface;
import org.mintshell.terminal.interfaces.TerminalCommandInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of an {@link TerminalCommandInterface} that allows connections through SSH.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class SshCommandInterface extends AbstractTerminalCommandInterface implements TerminalCommandInterface, Command {

  public static final int DEFAULT_PORT = 8022;
  private static final Logger LOG = LoggerFactory.getLogger(SshCommandInterface.class);

  private final int port;
  private final SshServer sshServer;
  private AnsiKeyFilterInputStream in;
  private OutputStream out;

  public SshCommandInterface(final int port, final String prompt) {
    this(port, prompt, null);
  }

  public SshCommandInterface(final int port, final String prompt, @Nullable final String banner) {
    this(port, prompt, banner, DEFAULT_COMMAND_SUBMISSION_KEY);
  }

  public SshCommandInterface(final int port, final String prompt, @Nullable final String banner, final Key commandSubmissionKey,
      @Nullable final KeyBinding... keyBindings) {
    super(prompt, banner, commandSubmissionKey, keyBindings);
    this.port = port;
    this.sshServer = SshServer.setUpDefaultServer();
    this.sshServer.setPort(port);
    this.sshServer.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(new File("hostkey.ser")));
    this.sshServer.setPublickeyAuthenticator(new AlwaysAuthenticatedlPublicKeyAuthenticator());
    this.sshServer.setShellFactory(() -> this);
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.terminal.interfaces.AbstractTerminalCommandInterface#activate(org.mintshell.CommandInterpreter,
   *      org.mintshell.CommandDispatcher)
   */
  @Override
  public void activate(final CommandInterpreter commandInterpreter, final CommandDispatcher commandDispatcher) throws IllegalStateException {
    super.activate(commandInterpreter, commandDispatcher);
    try {
      this.sshServer.start();
      LOG.info("SSH server running on port [{}]", this.sshServer.getPort());
    } catch (final IOException e) {
      throw new SshInterfaceException(format("Failed to start SSH server on port [%s]!", this.port));
    }
  }

  @Override
  public void destroy() throws Exception {
    // currently not used
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
      this.out.write("\n".getBytes());
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
    // currently not used
  }

  @Override
  protected void clearScreen() {
    // TODO : issue the correct command to the output stream
  }

  @Override
  protected void moveCursor(final int row, final int col) {
    // TODO : issue the correct command to the output stream
  }
}
