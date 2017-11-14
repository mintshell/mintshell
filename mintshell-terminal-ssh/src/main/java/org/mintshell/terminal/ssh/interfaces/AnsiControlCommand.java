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
package org.mintshell.terminal.ssh.interfaces;

/**
 * Enumerates ANSI control commands.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public enum AnsiControlCommand {

  INSERT_SINGLE_CHARACTER("\u001B[@"),

  DELETE_SINGLE_CHARACTER("\u001B[P"),

  ERASE_SINGLE_CHARACTER("\u001B[X"),

  ERASE_ENTIRE_SCREEN("\u001B[2J"),

  MOVE_CURSOR("\u001B[%d;%dH"),

  SET_EDIT_EXTEND_MODE("\u001B[Q"),

  ;

  private final String sequence;

  private AnsiControlCommand(final String sequence) {
    this.sequence = sequence;
  }

  /**
   * Returns the byte sequence of this control command.
   *
   * @param args
   *          arguments
   * @return byte sequence
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public byte[] getSequence(final Object... args) {
    return String.format(this.sequence, args).getBytes();
  }
}
