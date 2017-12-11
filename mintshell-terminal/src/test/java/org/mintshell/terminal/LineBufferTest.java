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
package org.mintshell.terminal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests the functionality of the {@link LineBuffer} class.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class LineBufferTest {

  @Test
  public void testInsertLeftFromHead() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.moveCursor(0);
    sut.insertLeft("o");
    assertThat(sut.toString()).isEqualTo("oFoobar");
    assertThat(sut.getCursorPosition()).isEqualTo(1);
  }

  @Test
  public void testInsertLeftFromMiddle() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.moveCursor(3);
    sut.insertLeft("o");
    assertThat(sut.toString()).isEqualTo("Fooobar");
    assertThat(sut.getCursorPosition()).isEqualTo(4);
  }

  @Test
  public void testInsertLeftFromTail() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.insertLeft("o");
    assertThat(sut.toString()).isEqualTo("Foobaro");
    assertThat(sut.getCursorPosition()).isEqualTo(7);
  }

  @Test
  public void testInsertRightFromHead() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.moveCursor(0);
    sut.insertRight("o");
    assertThat(sut.toString()).isEqualTo("oFoobar");
    assertThat(sut.getCursorPosition()).isEqualTo(0);
  }

  @Test
  public void testInsertRightFromMiddle() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.moveCursor(3);
    sut.insertRight("o");
    assertThat(sut.toString()).isEqualTo("Fooobar");
    assertThat(sut.getCursorPosition()).isEqualTo(3);
  }

  @Test
  public void testInsertRightFromTail() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.insertRight("o");
    assertThat(sut.toString()).isEqualTo("Foobaro");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
  }

  @Test
  public void testMoveLeftFromHead() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.moveCursor(0);
    sut.moveCursorLeft();
    assertThat(sut.getCursorPosition()).isEqualTo(0);
  }

  @Test
  public void testMoveLeftFromMiddle() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.moveCursor(3);
    sut.moveCursorLeft();
    assertThat(sut.getCursorPosition()).isEqualTo(2);
  }

  @Test
  public void testMoveLeftFromTail() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.moveCursorLeft();
    assertThat(sut.getCursorPosition()).isEqualTo(5);
  }

  @Test
  public void testMoveRightFromHead() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.moveCursor(0);
    sut.moveCursorRight();
    assertThat(sut.getCursorPosition()).isEqualTo(1);
  }

  @Test
  public void testMoveRightFromMiddle() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.moveCursor(3);
    sut.moveCursorRight();
    assertThat(sut.getCursorPosition()).isEqualTo(4);
  }

  @Test
  public void testMoveRightFromTail() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.moveCursorRight();
    assertThat(sut.getCursorPosition()).isEqualTo(6);
  }

  @Test
  public void testOverwriteFromHead() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.moveCursor(0);
    sut.overwrite("o");
    assertThat(sut.toString()).isEqualTo("ooobar");
    assertThat(sut.getCursorPosition()).isEqualTo(1);
  }

  @Test
  public void testOverwriteFromMiddle() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.moveCursor(3);
    sut.overwrite("o");
    assertThat(sut.toString()).isEqualTo("Foooar");
    assertThat(sut.getCursorPosition()).isEqualTo(4);
  }

  @Test
  public void testOverwriteFromTail() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.overwrite("o");
    assertThat(sut.toString()).isEqualTo("Foobaro");
    assertThat(sut.getCursorPosition()).isEqualTo(7);
  }

  @Test
  public void testRemoveLeftFromHead() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.moveCursor(0);
    sut.removeLeft();
    assertThat(sut.toString()).isEqualTo("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(0);
  }

  @Test
  public void testRemoveLeftFromMiddle() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.moveCursor(3);
    sut.removeLeft();
    assertThat(sut.toString()).isEqualTo("Fobar");
    assertThat(sut.getCursorPosition()).isEqualTo(2);
  }

  @Test
  public void testRemoveLeftFromTail() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.removeLeft();
    assertThat(sut.toString()).isEqualTo("Fooba");
    assertThat(sut.getCursorPosition()).isEqualTo(5);
  }

  @Test
  public void testRemoveRightFromHead() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.moveCursor(0);
    sut.removeRight();
    assertThat(sut.toString()).isEqualTo("oobar");
    assertThat(sut.getCursorPosition()).isEqualTo(0);
  }

  @Test
  public void testRemoveRightFromMiddle() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.moveCursor(3);
    sut.removeRight();
    assertThat(sut.toString()).isEqualTo("Fooar");
    assertThat(sut.getCursorPosition()).isEqualTo(3);
  }

  @Test
  public void testRemoveRightFromTail() {
    final LineBuffer sut = new LineBuffer();
    sut.append("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
    sut.removeRight();
    assertThat(sut.toString()).isEqualTo("Foobar");
    assertThat(sut.getCursorPosition()).isEqualTo(6);
  }

}
