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
package org.mintshell.terminal;

import java.util.List;
import java.util.Vector;

/**
 * A cursor and position aware character buffer that represents a current line of termina input.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class LineBuffer implements CharSequence {

  private final Vector<Character> sequence;
  private int cursorPosition = 0;

  /**
   * Creates an emptyt {@link LineBuffer} with cursor at the beginning.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public LineBuffer() {
    this.sequence = new Vector<>();
  }

  /**
   * Creates a new {@link LineBuffer} filled with the given character sequence an cursor at the end.
   *
   * @param sequence
   *          initial content
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public LineBuffer(final List<Character> sequence) {
    this.sequence = new Vector<>(sequence);
  }

  /**
   * Appends a character to the end of the {@link LineBuffer} and places the cursor at the end.
   *
   * @param character
   *          character to append
   * @return this instance to allow fluent calls
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public LineBuffer append(final char character) {
    this.sequence.add(character);
    this.cursorPosition = this.sequence.size();
    return this;
  }

  /**
   * Appends all characters of the given {@link CharSequence} to the end of the {@link LineBuffer} and places the cursor
   * at the end.
   *
   * @param sequence
   *          {@link CharSequence} to append
   * @return this instance to allow fluent calls
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public LineBuffer append(final CharSequence sequence) {
    for (int index = 0; index < sequence.length(); index++) {
      this.append(sequence.charAt(index));
    }
    return this;
  }

  /**
   *
   * @{inheritDoc}
   * @see java.lang.CharSequence#charAt(int)
   */
  @Override
  public char charAt(final int index) {
    if (index < 0 || index >= this.sequence.size()) {
      throw new StringIndexOutOfBoundsException(index);
    }
    return this.sequence.get(index);
  }

  /**
   * Clears the {@link LineBuffer} and places the cursur at the beginning.
   *
   * @return this instance to allow fluent calls
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public LineBuffer clear() {
    this.sequence.clear();
    this.cursorPosition = 0;
    return this;
  }

  /**
   * Returns the current position of the cursor.
   *
   * @return current cursor position
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public int getCursorPosition() {
    return this.cursorPosition;
  }

  /**
   * Insert the given character left of the current cursor position and thereby implicitely increments the current
   * cursor position and the length of the {@link LineBuffer} by one.
   *
   * @param character
   *          character to insert
   * @return this instance to allow fluent calls
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public LineBuffer insertLeft(final char character) {
    this.sequence.insertElementAt(character, this.cursorPosition);
    this.moveCursorRight();
    return this;
  }

  /**
   * Insert the given {@link CharSequence} left of the current cursor position and thereby implicitely increments the
   * current cursor position and the length of the {@link LineBuffer} by the length of the given {@link CharSequence}.
   *
   * @param sequence
   *          {@link CharSequence} to insert
   * @return this instance to allow fluent calls
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public LineBuffer insertLeft(final CharSequence sequence) {
    for (int index = 0; index < sequence.length(); index++) {
      this.insertLeft(sequence.charAt(index));
    }
    return this;
  }

  /**
   * Insert the given character right of the current cursor position and thereby implicitely increments the length of
   * the {@link LineBuffer} position by one while the cursor position stays the same.
   *
   * @param character
   *          character to insert
   * @return this instance to allow fluent calls
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public LineBuffer insertRight(final char character) {
    this.sequence.insertElementAt(character, this.cursorPosition);
    return this;
  }

  /**
   * Insert the given {@link CharSequence} right of the current cursor position and thereby implicitely increments the
   * length of the {@link LineBuffer} by the length of the given {@link CharSequence} while the cursor position stays
   * the same.
   *
   * @param sequence
   *          {@link CharSequence} to insert
   * @return this instance to allow fluent calls
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public LineBuffer insertRight(final CharSequence sequence) {
    for (int index = 0; index < sequence.length(); index++) {
      this.insertRight(sequence.charAt(index));
    }
    return this;
  }

  /**
   *
   * @{inheritDoc}
   * @see java.lang.CharSequence#length()
   */
  @Override
  public int length() {
    return this.sequence.size();
  }

  /**
   * Moves the cursor to the given, absoute position in a robust manner. That means, that if the given position is
   * {@code <=0} the cursor is placed at the beginning, if it is {@code > length} it is placed at the end.
   *
   * @param position
   *          absolute position to place the cursor at
   * @return cursor position after movement
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public int moveCursor(final int position) {
    if (position < 0) {
      this.cursorPosition = 0;
    }
    else if (position > this.sequence.size()) {
      this.cursorPosition = this.sequence.size();
    }
    else {
      this.cursorPosition = position;
    }
    return this.cursorPosition;
  }

  /**
   * Moves the cursor one character left, meaning to decrement it's current position by one. If the cursor was already
   * at the beginning nothing happens.
   *
   * @return cursor position after movement
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public int moveCursorLeft() {
    if (this.cursorPosition > 0) {
      this.cursorPosition--;
    }
    return this.cursorPosition;
  }

  /**
   * Moves the cursor one character right, meaning to increment it's current position by one. If the cursor was already
   * at the end nothing happens.
   *
   * @return cursor position after movement
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public int moveCursorRight() {
    if (this.cursorPosition < this.sequence.size()) {
      this.cursorPosition++;
    }
    return this.cursorPosition;
  }

  /**
   * Overwrites the character right of the cursor with the given one. If the cursor is already at the end of the
   * {@link LineBuffer} the character will be appended.
   *
   * @param character
   *          character to overwrite with
   * @return this instance to allow fluent calls
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public LineBuffer overwrite(final char character) {
    if (this.cursorPosition == this.sequence.size()) {
      this.append(character);
    }
    else {
      this.sequence.removeElementAt(this.cursorPosition);
      this.sequence.insertElementAt(character, this.cursorPosition);
      this.moveCursorRight();
    }
    return this;
  }

  /**
   * Overwrites all characters right of the cursor with the given {@link CharSequence}. If the cursor is already at the
   * end of the {@link LineBuffer} or the given {@link CharSequence} exceeds the length of the {@link LineBuffer}, the
   * considering characters will be appended.
   *
   * @param sequence
   *          characters to overwrite with
   * @return this instance to allow fluent calls
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public LineBuffer overwrite(final CharSequence sequence) {
    for (int index = 0; index < sequence.length(); index++) {
      this.overwrite(sequence.charAt(index));
    }
    return this;
  }

  /**
   * Removes the character left of the current cursor position and thereby implicitely decrements the current cursor
   * position and the length of the {@link LineBuffer} by one. If the cursor is already at the beginning nothing
   * happens.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public void removeLeft() {
    if (this.cursorPosition > 0) {
      this.sequence.remove(--this.cursorPosition);
    }
  }

  /**
   * Removes the character rigt of the current cursor position and thereby implicitely decrements the length of the
   * {@link LineBuffer} by one while the cursor position stays the same. If the cursor is already at the end nothing
   * happens.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public void removeRight() {
    if (this.cursorPosition < this.sequence.size()) {
      this.sequence.remove(this.cursorPosition);
    }
  }

  /**
   *
   * @{inheritDoc}
   * @see java.lang.CharSequence#subSequence(int, int)
   */
  @Override
  public CharSequence subSequence(final int start, final int end) {
    return new LineBuffer(this.sequence.subList(start, end));
  }

  /**
   *
   * @{inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final char[] value = new char[this.sequence.size()];
    for (int index = 0; index < value.length; index++) {
      value[index] = this.sequence.get(index);
    }
    return new String(value);
  }
}
