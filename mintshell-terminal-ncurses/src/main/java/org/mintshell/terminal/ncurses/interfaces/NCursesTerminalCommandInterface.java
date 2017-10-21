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
package org.mintshell.terminal.ncurses.interfaces;

import org.mintshell.CommandInterface;
import org.mintshell.annotation.Nullable;
import org.mintshell.terminal.Key;
import org.mintshell.terminal.KeyBinding;
import org.mintshell.terminal.interfaces.AbstractTerminalCommandInterface;
import org.mintshell.terminal.interfaces.TerminalCommandInterface;

/**
 * Implementation of a {@link TerminalCommandInterface} using a native interface to the ncursrs library.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class NCursesTerminalCommandInterface extends AbstractTerminalCommandInterface implements CommandInterface {

  private final NCursesTerminal nCurses;

  /**
   * Creates a new instance using the given command prompt.
   *
   * @param prompt
   *          command prompt
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public NCursesTerminalCommandInterface(final String prompt) {
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
  public NCursesTerminalCommandInterface(final String prompt, @Nullable final String banner) {
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
  public NCursesTerminalCommandInterface(final String prompt, @Nullable final String banner, final Key commandSubmissionKey,
      @Nullable final KeyBinding... keyBindings) {
    super(prompt, banner, commandSubmissionKey, keyBindings);
    this.nCurses = NCursesTerminal.getInstance();
  }

  /**
   * Creates a new instance.
   *
   * @param prompt
   *          command prompt
   * @param banner
   *          welcome banner
   * @param keyBindings
   *          initial {@link KeyBinding}s
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public NCursesTerminalCommandInterface(final String prompt, @Nullable final String banner, final KeyBinding... keyBindings) {
    this(prompt, banner, DEFAULT_COMMAND_SUBMISSION_KEY, keyBindings);
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#print(java.lang.String)
   */
  @Override
  public void print(final String output) {
    this.nCurses.print(output);
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#println(java.lang.String)
   */
  @Override
  public void println(final String text) {
    this.print(new StringBuffer(text).append("\n\r").toString());
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#readKey()
   */
  @Override
  public Key readKey() {
    final NCursesKey key = this.nCurses.readKey();
    return key.getKey();
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.terminal.interfaces.AbstractTerminalCommandInterface#clearScreen()
   */
  @Override
  protected void clearScreen() {
    this.nCurses.clearScreen();
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.terminal.interfaces.AbstractTerminalCommandInterface#moveCursor(int, int)
   */
  @Override
  protected void moveCursor(final int row, final int col) {
    // TODO implement method moveCursor
  }
}
