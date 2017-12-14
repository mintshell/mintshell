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
package org.mintshell.command;

import java.util.Map;
import java.util.Optional;

/**
 * The command history provides access to previously entered commands and allows it to list them or step forward or
 * backward through them. Each added command will get a consecutive number assigned.
 *
 *
 * @author Noqmar
 * @since 0.1.0
 */
public abstract interface CommandHistory {

  /**
   * Adds a new command line to the history and sets the internal position to the end. If the given command line is
   * empty, it is ignored.
   *
   * @param commandLine
   *          command line to be added
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract void addCommandLine(String commandLine);

  /**
   * Clears the complete history and sets the internal position to the beginning.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract void clear();

  /**
   * Returns the command line for the given number.
   *
   * @param number
   *          consecutive number of the wanted command line
   * @return (optional) command line for the given number
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract Optional<String> getCommandLine(int number);

  /**
   * Returns a {@link Map} of historical command lines and their numbers.
   *
   * @return {@link Map} of historical command lines
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract Map<Integer, String> getCommandLines();

  /**
   * Returns the current maximum command line number.
   * 
   * @return current maximum command line number
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract int getMaxCommandLineNumber();

  /**
   * Returns the next command line relative to the internal position and increases the internal position by one. If the
   * internal position already points to the last command line the last command line is returned and the internal
   * position doesn't change.
   *
   * @return next command line relative to the internal position or {@code null}, if the history is empty
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract String getNextCommandLine();

  /**
   * Returns the previous command line relative to the internal position and decreases the internal position by one. If
   * the internal position already points to the first command line the first command line is returned and the internal
   * position doesn't change.
   *
   * @return previous command line relative to the internal position or {@code null}, if the history is empty
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract String getPreviousCommandLine();
}
