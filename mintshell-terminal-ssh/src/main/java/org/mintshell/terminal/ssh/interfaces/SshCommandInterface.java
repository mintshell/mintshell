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
import static org.mintshell.terminal.interfaces.AbstractTerminalCommandInterface.DEFAULT_COMMAND_SUBMISSION_KEY;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.mintshell.CommandDispatcher;
import org.mintshell.CommandInterpreter;
import org.mintshell.annotation.Nullable;
import org.mintshell.terminal.Key;
import org.mintshell.terminal.KeyBinding;
import org.mintshell.terminal.interfaces.TerminalCommandInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of an {@link TerminalCommandInterface} that allows connections through SSH.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class SshCommandInterface implements TerminalCommandInterface {

  public static final int DEFAULT_PORT = 8022;
  private static final Logger LOG = LoggerFactory.getLogger(SshCommandInterface.class);

  private final int port;
  private final SshServer sshServer;
  private CommandInterpreter commandInterpreter;
  private CommandDispatcher commandDispatcher;

  public SshCommandInterface(final int port, final String prompt, @Nullable final String banner, final Key commandSubmissionKey,
      @Nullable final KeyBinding... keyBindings) {
    this.port = port;
    this.sshServer = SshServer.setUpDefaultServer();
    this.sshServer.setPort(port);
    this.sshServer.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(new File("hostkey.ser")));
    this.sshServer.setPublickeyAuthenticator(new AlwaysAuthenticatedlPublicKeyAuthenticator());
    this.sshServer.setShellFactory(
        () -> new SshCommandInterfaceSession(this.getCommandInterpreter(), this.getCommandDispatcher(), prompt, banner, commandSubmissionKey, keyBindings));
  }

  public SshCommandInterface(final String prompt) {
    this(prompt, null);
  }

  public SshCommandInterface(final String prompt, @Nullable final String banner) {
    this(DEFAULT_PORT, prompt, banner, DEFAULT_COMMAND_SUBMISSION_KEY);
  }

  public SshCommandInterface(final String prompt, @Nullable final String banner, final @Nullable KeyBinding... keyBindings) {
    this(DEFAULT_PORT, prompt, banner, DEFAULT_COMMAND_SUBMISSION_KEY, keyBindings);
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.terminal.interfaces.AbstractTerminalCommandInterface#activate(org.mintshell.CommandInterpreter,
   *      org.mintshell.CommandDispatcher)
   */
  @Override
  public void activate(final CommandInterpreter commandInterpreter, final CommandDispatcher commandDispatcher) throws IllegalStateException {
    this.commandInterpreter = commandInterpreter;
    this.commandDispatcher = commandDispatcher;
    try {
      this.sshServer.start();
      LOG.info("SSH server running on port [{}]", this.sshServer.getPort());
    } catch (final IOException e) {
      throw new SshInterfaceException(format("Failed to start SSH server on port [%s]!", this.port));
    }
  }

  @Override
  public void addKeyBindings(final KeyBinding... keyBindings) {
    // TODO forward keybinding operations to sessions
  }

  @Override
  public void clearKeyBindings() {
    // TODO forward keybinding operations to sessions
  }

  @Override
  public Collection<KeyBinding> getKeyBindings() {
    // TODO forward keybinding operations to sessions
    return null;
  }

  @Override
  public boolean isActivated() {
    return this.getCommandInterpreter() != null && this.getCommandDispatcher() != null;
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#print(java.lang.String)
   */
  @Override
  public void print(final String text) {
    throw new UnsupportedOperationException("Direct invokation is not available on SSH interface but within SSH session.");
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#println(java.lang.String)
   */
  @Override
  public void println(final String text) {
    throw new UnsupportedOperationException("Direct invokation is not available on SSH interface but within SSH session.");
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#readKey()
   */
  @Override
  public Key readKey() {
    throw new UnsupportedOperationException("Direct invokation is not available on SSH interface but within SSH session.");
  }

  @Override
  public void removeKeyBinding(final KeyBinding keyBinding) {
    // TODO forward keybinding operations to sessions
  }

  CommandDispatcher getCommandDispatcher() {
    return this.commandDispatcher;
  }

  CommandInterpreter getCommandInterpreter() {
    return this.commandInterpreter;
  }
}
