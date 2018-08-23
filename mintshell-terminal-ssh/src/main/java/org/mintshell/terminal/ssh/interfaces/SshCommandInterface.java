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
import static org.mintshell.terminal.interfaces.BaseTerminalCommandInterface.DEFAULT_COMMAND_SUBMISSION_KEY;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.mintshell.annotation.Nullable;
import org.mintshell.command.DefaultCommand;
import org.mintshell.dispatcher.CommandDispatcher;
import org.mintshell.interpreter.CommandInterpreter;
import org.mintshell.terminal.Key;
import org.mintshell.terminal.KeyBinding;
import org.mintshell.terminal.interfaces.BaseTerminalCommandInterface;
import org.mintshell.terminal.interfaces.TerminalCommandHistory;
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
  public static final String DEFAULT_EXIT_COMMAND_NAME = "exit";
  private static final Logger LOG = LoggerFactory.getLogger(SshCommandInterface.class);

  private final ExecutorService executor;
  private final int port;
  private final SshServer sshServer;
  private CommandInterpreter commandInterpreter;
  private CommandDispatcher commandDispatcher;
  private final List<KeyBinding> keyBindings;
  private final SessionRegistry sessionRegistry;
  private TerminalCommandHistory commandHistory;

  /**
   * Creates a new instance.
   *
   * @param port
   *          port number to bind the SSH server to
   * @param exitCommandName
   *          Name of the command that is recognized and leads to close a SSH session
   * @param prompt
   *          shell prompt
   * @param banner
   *          welcome banner
   * @param commandSubmissionKey
   *          key that issues command submission
   * @param keyBindings
   *          (optional) {@link KeyBinding}s
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public SshCommandInterface(final int port, final String exitCommandName, final String prompt, @Nullable final String banner, final Key commandSubmissionKey,
      @Nullable final KeyBinding... keyBindings) {
    this.executor = Executors.newCachedThreadPool();
    this.port = port;
    this.sshServer = SshServer.setUpDefaultServer();
    this.sshServer.setPort(port);
    this.sshServer.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(new File("hostkey.ser")));
    this.sshServer.setPublickeyAuthenticator(new AlwaysAuthenticatedlPublicKeyAuthenticator());
    this.keyBindings = new ArrayList<>(Arrays.asList(keyBindings));
    this.sessionRegistry = new SessionRegistry();
    this.sshServer.setShellFactory(() -> {
      final SshCommandInterfaceSession newSession = new SshCommandInterfaceSession(this.sessionRegistry, this.executor, this.getCommandInterpreter(),
          this.getCommandDispatcher(), new DefaultCommand(exitCommandName), prompt, banner, commandSubmissionKey, this.getKeyBindingsArray());
      newSession.configureCommandHistory(this.commandHistory);
      return newSession;
    });
  }

  /**
   * Creates a new instance using the {@link #DEFAULT_PORT} and the
   * {@link BaseTerminalCommandInterface#DEFAULT_COMMAND_SUBMISSION_KEY}.
   *
   * @param prompt
   *          shell prompt
   * @param banner
   *          welcome banner
   * @param keyBindings
   *          (optional) {@link KeyBinding}s
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public SshCommandInterface(final String prompt, @Nullable final String banner, final @Nullable KeyBinding... keyBindings) {
    this(DEFAULT_PORT, DEFAULT_EXIT_COMMAND_NAME, prompt, banner, DEFAULT_COMMAND_SUBMISSION_KEY, keyBindings);
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.BaseTerminalCommandInterface#activate(org.mintshell.interpreter.CommandInterpreter,
   *      org.mintshell.dispatcher.CommandDispatcher)
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

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#addKeyBindings(org.mintshell.terminal.KeyBinding[])
   */
  @Override
  public void addKeyBindings(final KeyBinding... keyBindings) {
    this.keyBindings.addAll(Arrays.stream(keyBindings).collect(Collectors.toList()));
    this.sessionRegistry.getSessions().forEach(session -> session.addKeyBindings(keyBindings));
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#clearKeyBindings()
   */
  @Override
  public void clearKeyBindings() {
    this.keyBindings.clear();
    this.sessionRegistry.getSessions().forEach(SshCommandInterfaceSession::clearKeyBindings);
  }

  /**
   * Configures the given {@link TerminalCommandHistory} to be used by this {@link SshCommandInterface}.
   *
   * @param commandHistory
   *          new {@link TerminalCommandHistory} or {@code null} to remove an already configured
   *          {@link TerminalCommandHistory}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public void configureCommandHistory(final @Nullable TerminalCommandHistory commandHistory) {
    this.commandHistory = commandHistory;
    this.sessionRegistry.getSessions().forEach(session -> session.configureCommandHistory(commandHistory));
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandInterface#deactivate()
   */
  @Override
  public void deactivate() {
    this.sessionRegistry.getSessions().forEach(session -> session.deactivate());
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#eraseNext()
   */
  @Override
  public void eraseNext() {
    throw new UnsupportedOperationException("Direct invokation is not available on SSH interface but within SSH session.");
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#erasePrevious()
   */
  @Override
  public void erasePrevious() {
    throw new UnsupportedOperationException("Direct invokation is not available on SSH interface but within SSH session.");
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandInterface#getCommandDispatcher()
   */
  @Override
  public CommandDispatcher getCommandDispatcher() {
    return this.commandDispatcher;
  }

  /**
   *
   * {@inheritDoc}
   * 
   * @see org.mintshell.interfaces.CommandInterface#getCommandInterpreter()
   */
  @Override
  public CommandInterpreter getCommandInterpreter() {
    return this.commandInterpreter;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#getKeyBindings()
   */
  @Override
  public Collection<KeyBinding> getKeyBindings() {
    return new ArrayList<>(this.keyBindings);
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandInterface#isActivated()
   */
  @Override
  public boolean isActivated() {
    return this.getCommandInterpreter() != null && this.getCommandDispatcher() != null;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#moveNext()
   */
  @Override
  public void moveNext() {
    throw new UnsupportedOperationException("Direct invokation is not available on SSH interface but within SSH session.");
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#movePrevious()
   */
  @Override
  public void movePrevious() {
    throw new UnsupportedOperationException("Direct invokation is not available on SSH interface but within SSH session.");
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#newLine()
   */
  @Override
  public void newLine() {
    throw new UnsupportedOperationException("Direct invokation is not available on SSH interface but within SSH session.");
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#print(java.lang.String)
   */
  @Override
  public void print(final String text) {
    throw new UnsupportedOperationException("Direct invokation is not available on SSH interface but within SSH session.");
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#readKey()
   */
  @Override
  public Key readKey() {
    throw new UnsupportedOperationException("Direct invokation is not available on SSH interface but within SSH session.");
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#removeKeyBinding(org.mintshell.terminal.KeyBinding)
   */
  @Override
  public void removeKeyBinding(final KeyBinding keyBinding) {
    this.keyBindings.remove(keyBinding);
    this.sessionRegistry.getSessions().forEach(session -> session.removeKeyBinding(keyBinding));
  }

  private KeyBinding[] getKeyBindingsArray() {
    final Collection<KeyBinding> currentBindings = this.getKeyBindings();
    final KeyBinding[] bindings = new KeyBinding[currentBindings.size()];
    return currentBindings.toArray(bindings);
  }
}
