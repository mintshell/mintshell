/*
 * Copyright © 2017-2019 mintshell.org
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
package org.mintshell.terminal;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Representation of a virtual cursor to track and provide the current position of a terminal cursor. Counting of rows
 * and colums start at zero and cannot get negative.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class Cursor {

  private final int maxColumn;
  private final int maxRow;
  private int column;
  private int row;

  /**
   * Creates a new cursor instance at position 0/0.
   *
   * @param maxColumn
   *          maximum column number
   * @param maxRow
   *          maximum row number
   * @author Noqmar
   * @since 0.1.0
   */
  public Cursor(final int maxColumn, final int maxRow) {
    this(0, 0, maxColumn, maxRow);
  }

  /**
   * Creates a new cursor instance.
   *
   * @param initialColumn
   *          initial column number
   * @param initialRow
   *          initial row number
   * @param maxColumn
   *          maximum column number
   * @param maxRow
   *          maximum row number
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Cursor(final int initialColumn, final int initialRow, final int maxColumn, final int maxRow) {
    this.column = initialColumn;
    this.row = initialRow;
    this.maxColumn = maxColumn;
    this.maxRow = maxRow;
  }

  /**
   * Return the current column number of the cursor.
   *
   * @return column number
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public int getColumn() {
    return this.column;
  }

  /**
   * Returns the maximum number of columns which shoud be related to the terminal's capabilities.
   *
   * @return maximum number of columns
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public int getMaxColumn() {
    return this.maxColumn;
  }

  /**
   * Returns the maximum number of rows which shoud be related to the terminal's capabilities.
   *
   * @return maximum number of rows
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public int getMaxRow() {
    return this.maxRow;
  }

  /**
   * Return the current row number of the cursor.
   *
   * @return row number
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public int getRow() {
    return this.row;
  }

  /**
   * Moves the cursor one row down by incrementing the row number by {@code 1} if it was not already
   * {@link #getMaxRow()}.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public void moveDown() {
    if (this.row < this.maxRow - 1) {
      this.row += 1;
    }
  }

  /**
   * Moves the cursor one column left by decrementing the column number by {@code 1} if it was not already {@code 0} .
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public void moveLeft() {
    if (this.column > 0) {
      this.column -= 1;
    }
  }

  /**
   * Moves the cursor one column right by incrementing the column number by {@code 1}, if it was not already
   * {@link #getMaxColumn()}.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public void moveRight() {
    if (this.column < this.maxColumn - 1) {
      this.column += 1;
    }
    else {
      this.moveDown();
      this.column = 0;
    }
  }

  /**
   * Moves the cursor one row up by decrementing the row number by {@code 1} if it was not already {@code 0} .
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public void moveUp() {
    if (this.row > 0) {
      this.row -= 1;
    }
  }

  /**
   * Sets the cursor to the given column number. If the given number is negative, the cursor is set to column {@code 0}
   * and if it is greater than {@link #getMaxColumn()} it is set to this value.
   *
   * @param column
   *          new cursor column
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public void setColumn(final int column) {
    if (column < this.maxColumn - 1) {
      this.column = min(max(0, column), this.maxColumn);
    }
    else {
      this.moveDown();
      this.column = 0;
    }
  }

  /**
   * Sets the cursor to the given position. Negative values are set to {@code 0} and values greater than
   * {@link #getMaxColumn()} or {@link #getMaxRow()} are set to these maximums.
   *
   * @param col
   *          new column number
   * @param row
   *          new row number
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public void setPosition(final int col, final int row) {
    this.setColumn(col);
    this.setRow(row);
  }

  /**
   * Sets the cursor to the given row number. If the given number is negative, the cursor is set to row {@code 0} and if
   * it is greater than {@link #getMaxRow()} it is set to this value.
   *
   * @param row
   *          new cursor row
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public void setRow(final int row) {
    this.row = min(max(0, row), this.maxColumn);
  }
}
