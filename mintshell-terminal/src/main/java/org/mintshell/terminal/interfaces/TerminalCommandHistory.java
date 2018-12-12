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

import org.mintshell.assertion.Assert;
import org.mintshell.common.IoProvider;
import org.mintshell.interfaces.BasePersistableCommandHistory;
import org.mintshell.interfaces.CommandHistory;
import org.mintshell.terminal.Key;

/**
 * {@link TerminalCommandInterface}-specific implementation of a {@link CommandHistory}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class TerminalCommandHistory extends BasePersistableCommandHistory {

  public static final String DEFAULT_HISTORY_LIST_COMMAND = "history";

  public static final Key DEFAULT_KEY_HISTORY_NEXT = Key.DOWN;
  public static final Key DEFAULT_KEY_HISTORY_PREV = Key.UP;

  public static final int FIRST_COMMAND_NUMBER = 1;

  private final Key keyHistoryNext;
  private final Key keyHistoryPrev;
  private final String historyListCommand;

  public TerminalCommandHistory(final IoProvider ioProvider) {
    this(ioProvider, DEFAULT_KEY_HISTORY_NEXT, DEFAULT_KEY_HISTORY_PREV, DEFAULT_HISTORY_LIST_COMMAND);
  }

  public TerminalCommandHistory(final IoProvider ioProvider, final Key keyHistoryNext, final Key keyHistoryPrev, final String historyListCommand) {
    super(ioProvider);
    this.keyHistoryNext = Assert.ARG.isNotNull(keyHistoryNext, "[keyHistoryNext] must not be [null]");
    this.keyHistoryPrev = Assert.ARG.isNotNull(keyHistoryPrev, "[keyHistoryPrev] must not be [null]");
    this.historyListCommand = Assert.ARG.isNotNull(historyListCommand, "[historyListCommand] must not be [null]");
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
}
