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
package org.mintshell.terminal.ncurses.interfaces;

import org.mintshell.annotation.Nullable;
import org.mintshell.interfaces.CommandInterface;
import org.mintshell.terminal.Cursor;
import org.mintshell.terminal.Key;
import org.mintshell.terminal.KeyBinding;
import org.mintshell.terminal.interfaces.BaseTerminalCommandInterface;
import org.mintshell.terminal.interfaces.TerminalCommandHistory;
import org.mintshell.terminal.interfaces.TerminalCommandInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of a {@link TerminalCommandInterface} using a native interface to the ncursrs library.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class NCursesTerminalCommandInterface extends BaseTerminalCommandInterface implements CommandInterface {

  private static final Logger LOG = LoggerFactory.getLogger(NCursesTerminalCommandInterface.class);

  private final NCursesTerminal nCurses;
  private Cursor cursor;

  /**
   * Creates a new instance.
   *
   * @param commandHistory
   *          command history
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
  public NCursesTerminalCommandInterface(final TerminalCommandHistory commandHistory, final @Nullable String banner, final Key commandSubmissionKey,
      @Nullable final KeyBinding... keyBindings) {
    super(commandHistory, banner, commandSubmissionKey, keyBindings);
    this.nCurses = NCursesTerminal.getInstance();
    this.cursor = new Cursor(this.nCurses.getCol(), this.nCurses.getRow(), this.nCurses.getMaxCol(), this.nCurses.getMaxRow());
    this.logPositions();
  }

  /**
   * Creates a new instance.
   *
   * @param commandHistory
   *          command history
   * @param banner
   *          welcome banner
   * @param keyBindings
   *          initial {@link KeyBinding}s
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public NCursesTerminalCommandInterface(final TerminalCommandHistory commandHistory, final @Nullable String banner, final KeyBinding... keyBindings) {
    this(commandHistory, banner, DEFAULT_COMMAND_SUBMISSION_KEY, keyBindings);
  }

  @Override
  public void deactivate() {
    super.deactivate();
    System.exit(0);
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#eraseNext()
   */
  @Override
  public void eraseNext() {
    this.nCurses.deleteChar();
    this.logPositions();
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#erasePrevious()
   */
  @Override
  public void erasePrevious() {
    this.cursor.moveLeft();
    this.nCurses.deleteCharAt(this.cursor.getColumn(), this.cursor.getRow());
    this.logPositions();

  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#moveNext()
   */
  @Override
  public void moveNext() {
    this.cursor.moveRight();
    this.nCurses.moveCursor(this.cursor.getColumn(), this.cursor.getRow());
    this.logPositions();
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#movePrevious()
   */
  @Override
  public void movePrevious() {
    this.cursor.moveLeft();
    this.nCurses.moveCursor(this.cursor.getColumn(), this.cursor.getRow());
    this.logPositions();
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#newLine()
   */
  @Override
  public void newLine() {
    this.print("\n\r");
    this.logPositions();
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#print(java.lang.String)
   */
  @Override
  public void print(final String output) {
    output.chars().forEach(i -> {
      final char c = (char) i;
      switch (c) {
        case '\r':
          this.cursor.setColumn(0);
          break;
        case '\n':
          this.cursor.moveDown();
          break;
        default:
          this.cursor.moveRight();
      }
    });
    this.nCurses.print(output);
    this.logPositions();
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.TerminalCommandInterface#readKey()
   */
  @Override
  public Key readKey() {
    final NCursesKey key = this.nCurses.readKey();
    if (NCursesKey.RESIZE.equals(key)) {
      this.cursor = new Cursor(this.nCurses.getCol(), this.nCurses.getRow(), this.nCurses.getMaxCol(), this.nCurses.getMaxRow());
    }
    return key.getKey();
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.BaseTerminalCommandInterface#clearScreen()
   */
  @Override
  protected void clearScreen() {
    this.cursor.setPosition(0, 0);
    this.nCurses.clearScreen();
    this.logPositions();
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.terminal.interfaces.BaseTerminalCommandInterface#moveCursor(int, int)
   */
  @Override
  protected void moveCursor(final int col, final int row) {
    this.cursor.setPosition(col, row);
    this.nCurses.moveCursor(this.cursor.getColumn(), this.cursor.getRow());
    this.logPositions();
  }

  private void logPositions() {
    LOG.trace("Cursor [{}/{}:[{}][{}]]  Terminal [{}/{}]:[{}][{}]", this.cursor.getColumn(), this.cursor.getRow(), this.cursor.getMaxColumn(),
        this.cursor.getMaxRow(), this.nCurses.getCol(), this.nCurses.getRow(), this.nCurses.getMaxCol(), this.nCurses.getMaxRow());
  }
}
