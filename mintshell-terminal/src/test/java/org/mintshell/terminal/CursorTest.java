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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests the functionality of the class {@link Cursor}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class CursorTest {

  @Test
  public void testMoveDown() {
    final Cursor sut = new Cursor(0, 42, 100, 100);
    assertThat(sut.getRow()).isEqualTo(42);
    sut.moveDown();
    assertThat(sut.getRow()).isEqualTo(43);
  }

  @Test
  public void testMoveLeft() {
    final Cursor sut = new Cursor(42, 0, 100, 100);
    assertThat(sut.getColumn()).isEqualTo(42);
    sut.moveLeft();
    assertThat(sut.getColumn()).isEqualTo(41);
  }

  @Test
  public void testMoveLeftFromZero() {
    final Cursor sut = new Cursor(100, 100);
    assertThat(sut.getColumn()).isEqualTo(0);
    sut.moveLeft();
    assertThat(sut.getColumn()).isEqualTo(0);
  }

  @Test
  public void testMoveRight() {
    final Cursor sut = new Cursor(42, 0, 100, 100);
    assertThat(sut.getColumn()).isEqualTo(42);
    sut.moveRight();
    assertThat(sut.getColumn()).isEqualTo(43);
  }

  @Test
  public void testMoveUp() {
    final Cursor sut = new Cursor(0, 42, 100, 100);
    assertThat(sut.getRow()).isEqualTo(42);
    sut.moveUp();
    assertThat(sut.getRow()).isEqualTo(41);
  }

  @Test
  public void testMoveUpFromZero() {
    final Cursor sut = new Cursor(100, 100);
    assertThat(sut.getRow()).isEqualTo(0);
    sut.moveUp();
    assertThat(sut.getRow()).isEqualTo(0);
  }

  @Test
  public void testSetColumnNegative() {
    final Cursor sut = new Cursor(42, 42, 100, 100);
    assertThat(sut.getColumn()).isEqualTo(42);
    sut.setColumn(-42);
    assertThat(sut.getColumn()).isEqualTo(0);
  }

  @Test
  public void testSetColumnTooBig() {
    final Cursor sut = new Cursor(42, 42, 100, 100);
    assertThat(sut.getColumn()).isEqualTo(42);
    sut.setColumn(200);
    assertThat(sut.getColumn()).isEqualTo(0);
    assertThat(sut.getRow()).isEqualTo(43);
  }

  @Test
  public void testSetRowNegative() {
    final Cursor sut = new Cursor(42, 42, 100, 100);
    assertThat(sut.getRow()).isEqualTo(42);
    sut.setRow(-42);
    assertThat(sut.getRow()).isEqualTo(0);
  }

  @Test
  public void testSetRowTooBig() {
    final Cursor sut = new Cursor(42, 42, 100, 100);
    assertThat(sut.getRow()).isEqualTo(42);
    sut.setRow(200);
    assertThat(sut.getRow()).isEqualTo(100);
  }
}
