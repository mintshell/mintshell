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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.mintshell.annotation.Nullable;
import org.mintshell.assertion.Assert;
import org.mintshell.command.CommandHistory;
import org.mintshell.terminal.Key;

/**
 * {@link TerminalCommandInterface}-specific implementation of a {@link CommandHistory}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class TerminalCommandHistory implements CommandHistory {

  public static final String DEFAULT_HISTORY_LIST_COMMAND = "history";

  public static final Key DEFAULT_KEY_HISTORY_NEXT = Key.DOWN;
  public static final Key DEFAULT_KEY_HISTORY_PREV = Key.UP;

  public static final int FIRST_COMMAND_NUMBER = 1;

  private final Map<Integer, String> commandHistory;
  private final Key keyHistoryNext;
  private final Key keyHistoryPrev;
  private final String historyListCommand;
  private int commandCounter;
  private int position;

  public TerminalCommandHistory() {
    this(DEFAULT_KEY_HISTORY_NEXT, DEFAULT_KEY_HISTORY_PREV, DEFAULT_HISTORY_LIST_COMMAND);
  }

  public TerminalCommandHistory(final Key keyHistoryNext, final Key keyHistoryPrev, final String historyListCommand) {
    this.keyHistoryNext = Assert.ARG.isNotNull(keyHistoryNext, "[keyHistoryNext] must not be [null]");
    this.keyHistoryPrev = Assert.ARG.isNotNull(keyHistoryPrev, "[keyHistoryPrev] must not be [null]");
    this.historyListCommand = Assert.ARG.isNotNull(historyListCommand, "[historyListCommand] must not be [null]");
    this.commandHistory = new ConcurrentHashMap<>();
    this.commandCounter = FIRST_COMMAND_NUMBER - 1;
    this.position = this.commandCounter + 1;
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.command.CommandHistory#addCommandLine(java.lang.String)
   */
  @Override
  public void addCommandLine(final String commandLine) {
    this.commandHistory.put(++this.commandCounter, commandLine);
    this.position = this.commandCounter + 1;
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.command.CommandHistory#clear()
   */
  @Override
  public void clear() {
    this.commandHistory.clear();
    this.commandCounter = FIRST_COMMAND_NUMBER - 1;
    this.position = this.commandCounter + 1;

  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.command.CommandHistory#getCommandLine(int)
   */
  @Override
  public Optional<String> getCommandLine(final int number) {
    return Optional.ofNullable(this.commandHistory.get(number));
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.command.CommandHistory#getCommandLines()
   */
  @Override
  public Map<Integer, String> getCommandLines() {
    return new HashMap<>(this.commandHistory);
  }

  /**
   * Returns the configured command name to list the {@link TerminalCommandHistory}.
   *
   * @return command name to list
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public String getHistoryListCommand() {
    return this.historyListCommand;
  }

  /**
   * Returns the configured {@link Key} to move next within the {@link TerminalCommandHistory}.
   *
   * @return {@link Key} to move next
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Key getHistoryNextKey() {
    return this.keyHistoryNext;
  }

  /**
   * Returns the configured {@link Key} to move previous within the {@link TerminalCommandHistory}.
   *
   * @return {@link Key} to move previous
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Key getHistoryPrevKey() {
    return this.keyHistoryPrev;
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.command.CommandHistory#getMaxCommandLineNumber()
   */
  @Override
  public int getMaxCommandLineNumber() {
    return this.commandCounter;
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.command.CommandHistory#getNextCommandLine()
   */
  @Override
  public String getNextCommandLine() {
    if (this.position <= this.commandCounter) {
      this.position++;
    }
    final String commandLine = this.commandHistory.get(this.position);
    return commandLine != null ? commandLine : "";
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.command.CommandHistory#getPreviousCommandLine()
   */
  @Override
  public @Nullable String getPreviousCommandLine() {
    if (this.position > FIRST_COMMAND_NUMBER) {
      this.position--;
    }
    final String commandLine = this.commandHistory.get(this.position);
    return commandLine != null ? commandLine : "";
  }
}
