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
package org.mintshell.terminal.interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.mintshell.annotation.Nullable;
import org.mintshell.assertion.Assert;
import org.mintshell.command.Command;
import org.mintshell.dispatcher.CommandDispatcher;
import org.mintshell.interfaces.BaseCommandInterface;
import org.mintshell.interfaces.CommandInterfaceCommandResult;
import org.mintshell.interpreter.CommandInterpreter;
import org.mintshell.terminal.Key;
import org.mintshell.terminal.KeyBinding;
import org.mintshell.terminal.LineBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base implementation of a {@link TerminalCommandInterface}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public abstract class BaseTerminalCommandInterface extends BaseCommandInterface implements TerminalCommandInterface {

  public static final KeyBinding KEYBINDING_EXIT = new KeyBinding(Key.CANCEL, "exit");
  public static final Key DEFAULT_COMMAND_SUBMISSION_KEY = Key.ENTER;

  private static final Logger LOG = LoggerFactory.getLogger(BaseTerminalCommandInterface.class);

  private final ExecutorService executor;
  private Future<?> task;
  private Future<?> keyTask;
  private final String prompt;
  private final Optional<String> banner;
  private final List<KeyBinding> keyBindings;
  private final Key commandSubmissionKey;
  private final LineBuffer lineBuffer;

  private TerminalCommandHistory commandHistory;

  /**
   * Creates a new instance using the given command prompt.
   *
   * @param prompt
   *          command prompt
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public BaseTerminalCommandInterface(final String prompt) {
    this(prompt, null);
  }

  /**
   * Creates a new instance using the given command prompt and the (optional) welcome banner.
   *
   * @param prompt
   *          command prompt
   * @param banner
   *          welcome banner
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public BaseTerminalCommandInterface(final String prompt, final @Nullable String banner) {
    this(prompt, banner, DEFAULT_COMMAND_SUBMISSION_KEY);
  }

  /**
   * Creates a new instance.
   *
   * @param prompt
   *          command prompt
   * @param banner
   *          welcome banner
   * @param commandSubmissionKey
   *          command submission key
   * @param keyBindings
   *          initial {@link KeyBinding}s
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public BaseTerminalCommandInterface(final String prompt, final @Nullable String banner, final Key commandSubmissionKey,
      final @Nullable KeyBinding... keyBindings) {
    this.prompt = Assert.ARG.isNotNull(prompt, "[prompt] must not be [null]");
    this.banner = Optional.ofNullable(banner);
    this.commandSubmissionKey = Assert.ARG.isNotNull(commandSubmissionKey, "[commandSubmissionKey] must not be [null]");
    this.keyBindings = new ArrayList<>();
    this.addKeyBindings(keyBindings);
    this.executor = Executors.newCachedThreadPool();
    this.lineBuffer = new LineBuffer();
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.BaseCommandInterface#activate(org.mintshell.interpreter.CommandInterpreter,
   *      org.mintshell.dispatcher.CommandDispatcher)
   */
  @Override
  public void activate(final CommandInterpreter commandInterpreter, final CommandDispatcher commandDispatcher) throws IllegalStateException {
    super.activate(commandInterpreter, commandDispatcher);
    this.task = this.executor.submit(() -> {
      while (!Thread.interrupted()) {
        try {
          final Key key = this.readKey();
          this.keyTask = this.executor.submit(() -> {
            try {
              this.handleKey(key);
            } catch (final Exception e) {
              LOG.error("Failed to handle input [{}]", key, e);
              this.print(e.getMessage());
            }
          });
        } catch (final Exception e) {
          LOG.error("Failed to read input", e);
          this.print(e.getMessage());
        }
      }
    });
    this.clearScreen();
    if (this.banner.isPresent()) {
      this.println(this.banner.get());
    }
    this.printPrompt();
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#addKeyBindings(org.mintshell.terminal.KeyBinding[])
   */
  @Override
  public void addKeyBindings(final KeyBinding... keyBindings) {
    Assert.ARG.isNotNull(keyBindings, "[keyBindings] must not be [null]");
    this.keyBindings.addAll(Arrays.stream(keyBindings).filter(kb -> kb != null).collect(Collectors.toList()));
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
  }

  /**
   * Configures the given {@link TerminalCommandHistory} to be used by this {@link TerminalCommandInterface}.
   *
   * @param commandHistory
   *          new {@link TerminalCommandHistory} or {@code null} to remove an already configured
   *          {@link TerminalCommandHistory}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public void configureCommandHistory(final @Nullable TerminalCommandHistory commandHistory) {
    if (this.getCommandHistory() != null && !this.getCommandHistory().equals(commandHistory)) {
      // TODO (Noqmar): remove commands
    }
    if (commandHistory != null) {
      // TODO (Noqmar): add commands
    }
    this.commandHistory = commandHistory;
  }

  /**
   *
   * {@inheritDoc}
   * 
   * @see org.mintshell.interfaces.BaseCommandInterface#deactivate()
   */
  @Override
  public void deactivate() {
    if (this.task != null) {
      this.task.cancel(true);
    }
    if (this.keyTask != null) {
      this.keyTask.cancel(true);
    }
    this.executor.shutdownNow();
  }

  /**
   * Returns the currently configured {@link TerminalCommandHistory}.
   *
   * @return currently configured {@link TerminalCommandHistory} or {@code null}, if there is no
   *         {@link TerminalCommandHistory} configured
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public @Nullable TerminalCommandHistory getCommandHistory() {
    return this.commandHistory;
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
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#removeKeyBinding(org.mintshell.terminal.KeyBinding)
   */
  @Override
  public void removeKeyBinding(final KeyBinding keyBinding) {
    this.keyBindings.remove(Assert.ARG.isNotNull(keyBinding, "[keyBinding] must not be [null]"));
  }

  /**
   * Clears the screen of the underlying terminal.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected abstract void clearScreen();

  /**
   * Returns the zero-based column number of the current cursor position.
   *
   * @return current column number
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected int getCursorColumn() {
    return this.lineBuffer.getCursorPosition();
  }

  /**
   * Checks if the given {@link Command} is related to the given {@link TerminalCommandHistory} and executes it in that
   * case.
   *
   * @param command
   *          command to check
   * @param terminalCommandHistory
   *          {@link TerminalCommandHistory} to use
   * @return result of the history command execution of {@link Optional#empty()}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected Optional<CommandInterfaceCommandResult<?>> handleCommandHistory(final Command command, final TerminalCommandHistory terminalCommandHistory) {
    if (terminalCommandHistory.getHistoryListCommand().equals(command.getName())) {
      final int digitCount = Integer.toString(terminalCommandHistory.getMaxCommandLineNumber()).length();
      final StringBuilder builder = new StringBuilder();
      terminalCommandHistory.getCommandLines().entrySet().stream() //
          .sorted((e1, e2) -> Integer.compare(e1.getKey(), e2.getKey())) //
          .map(entry -> String.format("%" + digitCount + "d %s", entry.getKey(), entry.getValue())) //
          .forEach(entry -> builder.append(entry).append("\n\r"));
      final String result = builder.toString();
      return Optional.of(new CommandInterfaceCommandResult<>(command, Optional.of(result.substring(0, result.length() - 2)), true));
    }
    else {
      return Optional.empty();
    }
  }

  /**
   * Checks if the given {@link Key} is the command submission key and handles it by issuing a command. May be
   * overwritten by subclasses.
   *
   * @param key
   *          {@link Key} to handle
   * @return {@code true} if a command submission was handled, {@code false} otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected boolean handleCommandSubmission(final Key key) {
    if (key.equals(this.commandSubmissionKey)) {
      this.moveCursorToEndOfLine();
      final String commandMessage = this.lineBuffer.toString();
      this.lineBuffer.clear();
      this.newLine();
      if (!commandMessage.isEmpty()) {
        if (this.commandHistory != null) {
          this.commandHistory.addCommandLine(commandMessage);
        }
      }
      if (!commandMessage.trim().isEmpty()) {
        final String result = this.performCommand(commandMessage);
        this.println(result);
      }
      this.printPrompt();
      return true;
    }
    return false;
  }

  /**
   * Handles the given {@link Key}. May be overwritten by subclasses.
   *
   * @param key
   *          {@link Key} to handle
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected synchronized void handleKey(final Key key) {
    if (!this.handleCommandSubmission(key) && !this.handleKeyBinding(key)) {
      if (key.isPrintableKey()) {
        this.lineBuffer.insertLeft(key.getValue());
        this.print(key.getValue());
      }
      else if (this.commandHistory != null && key.equals(this.commandHistory.getHistoryPrevKey())) {
        this.eraseCursorToStartOfLine();
        final String previousCommandMessage = this.getCommandHistory().getPreviousCommandLine();
        this.lineBuffer.insertLeft(previousCommandMessage);
        this.print(previousCommandMessage);
      }
      else if (this.commandHistory != null && key.equals(this.commandHistory.getHistoryNextKey())) {
        this.eraseCursorToStartOfLine();
        final String nextCommandMessage = this.getCommandHistory().getNextCommandLine();
        this.lineBuffer.insertLeft(nextCommandMessage);
        this.print(nextCommandMessage);
      }
      else {
        switch (key) {
          case LEFT:
            if (this.lineBuffer.getCursorPosition() > 0) {
              this.lineBuffer.moveCursorLeft();
              this.movePrevious();
            }
            break;
          case RIGHT:
            if (this.lineBuffer.getCursorPosition() < this.lineBuffer.length()) {
              this.lineBuffer.moveCursorRight();
              this.moveNext();
            }
            break;
          case DEL:
            if (this.lineBuffer.getCursorPosition() > 0) {
              this.lineBuffer.removeLeft();
              this.erasePrevious();
            }
            break;
          case DELETE:
            if (this.lineBuffer.getCursorPosition() < this.lineBuffer.length()) {
              this.lineBuffer.removeRight();
              this.eraseNext();
            }
            break;
          default:
            LOG.warn("Unsupported key [{}]", key);
        }
      }
    }
  }

  /**
   * Checks if the given {@link Key} is bound to a command and handles it. May be overwritten by subclasses.
   *
   * @param key
   *          {@link Key} to handle
   * @return {@code true} if a {@link KeyBinding} was handled, {@code false} otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected boolean handleKeyBinding(final Key key) {
    final String commandMessage = this.keyBindings.stream() //
        .filter(keyBinding -> keyBinding.getKey().equals(key)) //
        .map(keyBinding -> keyBinding.getCommand()) //
        .findFirst().orElse(null);
    if (commandMessage != null) {
      this.print("\n\r");
      final String result = this.performCommand(commandMessage);
      this.println(result);
      this.print("\n\r");
      this.printPrompt();
      return true;
    }
    return false;
  }

  /**
   * Moves the cursor of the underlying terminal to the given position. Position is zero-based.
   *
   * @param col
   *          column number of the new position
   * @param row
   *          row number of the new position
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected abstract void moveCursor(int col, int row);

  /**
   *
   * {@inheritDoc}
   * 
   * @see org.mintshell.interfaces.BaseCommandInterface#preCommand(org.mintshell.command.Command)
   */
  @Override
  protected CommandInterfaceCommandResult<?> preCommand(final Command command) {
    if (this.commandHistory != null) {
      final Optional<CommandInterfaceCommandResult<?>> commandHistoryResult = this.handleCommandHistory(command, this.commandHistory);
      if (commandHistoryResult.isPresent()) {
        return commandHistoryResult.get();
      }
    }
    return super.preCommand(command);
  }

  /**
   * Prints the prompt.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected void printPrompt() {
    this.print(this.prompt);
  }

  private void eraseCursorToStartOfLine() {
    while (this.lineBuffer.getCursorPosition() > 0) {
      this.lineBuffer.removeLeft();
      this.erasePrevious();
    }
  }

  private void moveCursorToEndOfLine() {
    while (this.lineBuffer.getCursorPosition() < this.lineBuffer.length()) {
      this.lineBuffer.moveCursorRight();
      this.moveNext();
    }
  }
}
