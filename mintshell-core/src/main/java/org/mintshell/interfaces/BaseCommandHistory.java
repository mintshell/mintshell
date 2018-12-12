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
package org.mintshell.interfaces;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.mintshell.annotation.Nullable;

/**
 * Base implementation of a {@link CommandHistory}.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public abstract class BaseCommandHistory implements CommandHistory {

  public static final int FIRST_COMMAND_NUMBER = 1;
  public static final int DEFAULT_MAX_COMMANDS = 1000;

  private final Map<Integer, String> commandHistory;
  private final int maxCommands;
  private int commandCounter;
  private int position;

  /**
   * Creates a new instance with {@link #DEFAULT_MAX_COMMANDS} size.
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected BaseCommandHistory() {
    this(DEFAULT_MAX_COMMANDS);
  }

  /**
   * Creates a new instance.
   *
   * @param maxCommands
   *          maximum amount of managed commands
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected BaseCommandHistory(final int maxCommands) {
    this.commandHistory = new ConcurrentHashMap<>();
    this.maxCommands = maxCommands;
    this.commandCounter = FIRST_COMMAND_NUMBER - 1;
    this.position = this.commandCounter + 1;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandHistory#addCommandLine(java.lang.String)
   */
  @Override
  public void addCommandLine(final String commandLine) {
    final int firstCommandLineNumber = this.getFirstCommandLineNumber();
    this.commandHistory.put(++this.commandCounter, commandLine);
    if (this.commandHistory.size() > this.maxCommands) {
      this.commandHistory.remove(firstCommandLineNumber);
    }
    this.position = this.commandCounter + 1;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandHistory#clear()
   */
  @Override
  public void clear() {
    this.commandHistory.clear();
    this.commandCounter = FIRST_COMMAND_NUMBER - 1;
    this.position = this.commandCounter + 1;

  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandHistory#getCommandLine(int)
   */
  @Override
  public Optional<String> getCommandLine(final int number) {
    return Optional.ofNullable(this.commandHistory.get(number));
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandHistory#getCommandLines()
   */
  @Override
  public Map<Integer, String> getCommandLines() {
    return new HashMap<>(this.commandHistory);
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandHistory#getFirstCommandLineNumber()
   */
  @Override
  public int getFirstCommandLineNumber() {
    return this.commandCounter <= this.maxCommands ? this.commandCounter : this.commandCounter - this.commandHistory.size();
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandHistory#getLastCommandLineNumber()
   */
  @Override
  public int getLastCommandLineNumber() {
    return this.commandCounter;
  }

  @Override
  public int getMaxCommands() {
    return this.maxCommands;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandHistory#getNextCommandLine()
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
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandHistory#getPreviousCommandLine()
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
