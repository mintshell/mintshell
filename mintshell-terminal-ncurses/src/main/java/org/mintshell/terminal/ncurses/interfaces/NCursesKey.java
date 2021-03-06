/*
 * Copyright © 2017-2019 mintshell.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * Key.UNKNOWN), to deal in the Software without restriction, including
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

import static java.lang.String.format;

import org.mintshell.terminal.Key;

/**
 * Enumerates key codes and {@link Key} mappings provided by the native {@link NCursesTerminal}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
enum NCursesKey {

  CANCEL(3, Key.CANCEL),

  BACK_SPACE(0x08, Key.BACK_SPACE),

  TAB(0x09, Key.TAB),

  CLEAR(0x0C, Key.CLEAR),

  ENTER(0x0D, Key.ENTER),

  ESCAPE(0x1B, Key.ESCAPE),

  SPACE(0x20, Key.SPACE),

  EXCLAMATION(0x21, Key.EXCLAMATION),

  DOUBLE_QUOTE(0x22, Key.DOUBLE_QUOTE),

  HASH(0x23, Key.HASH),

  SECTION(167, Key.SECTION),

  DOLLAR(0x24, Key.DOLLAR),

  PERCENT(0x25, Key.PERCENT),

  AMPERSAND(0x26, Key.AMPERSAND),

  SINGLE_QUOTE(0x27, Key.SINGLE_QUOTE),

  BRACKET_ROUND_OPEN(0x28, Key.BRACKET_ROUND_OPEN),

  BRACKET_ROUND_CLOSE(0x29, Key.BRACKET_ROUND_CLOSE),

  ASTERISK(0x2A, Key.ASTERISK),

  PLUS(0x2B, Key.PLUS),

  COMMA(0x2C, Key.COMMA),

  MINUS(0x2D, Key.MINUS),

  DOT(0x2E, Key.DOT),

  SLASH(0x2F, Key.SLASH),

  ZERO(0x30, Key.ZERO),

  ONE(0x31, Key.ONE),

  TWO(0x32, Key.TWO),

  THREE(0x33, Key.THREE),

  FOUR(0x34, Key.FOUR),

  FIVE(0x35, Key.FIVE),

  SIX(0x36, Key.SIX),

  SEVEN(0x37, Key.SEVEN),

  EIGHT(0x38, Key.EIGHT),

  NINE(0x39, Key.NINE),

  COLON(0x3A, Key.COLON),

  SEMICOLON(0x3B, Key.SEMICOLON),

  SMALLER(0x3C, Key.SMALLER),

  EQUALS(0x3D, Key.EQUALS),

  GREATER(0x3E, Key.GREATER),

  QUESTION_MARK(0x3F, Key.QUESTION_MARK),

  AT(0x40, Key.AT),

  A_UP(0x41, Key.A_UP),

  B_UP(0x42, Key.B_UP),

  C_UP(0x43, Key.C_UP),

  D_UP(0x44, Key.D_UP),

  E_UP(0x45, Key.E_UP),

  F_UP(0x46, Key.F_UP),

  G_UP(0x47, Key.G_UP),

  H_UP(0x48, Key.H_UP),

  I_UP(0x49, Key.I_UP),

  J_UP(0x4A, Key.J_UP),

  K_UP(0x4B, Key.K_UP),

  L_UP(0x4C, Key.L_UP),

  M_UP(0x4D, Key.M_UP),

  N_UP(0x4E, Key.N_UP),

  O_UP(0x4F, Key.O_UP),

  P_UP(0x50, Key.P_UP),

  Q_UP(0x51, Key.Q_UP),

  R_UP(0x52, Key.R_UP),

  S_UP(0x53, Key.S_UP),

  T_UP(0x54, Key.T_UP),

  U_UP(0x55, Key.U_UP),

  V_UP(0x56, Key.V_UP),

  W_UP(0x57, Key.W_UP),

  X_UP(0x58, Key.X_UP),

  Y_UP(0x59, Key.Y_UP),

  Z_UP(0x5A, Key.Z_UP),

  BRACKET_SQUARE_OPEN(0x5B, Key.BRACKET_SQUARE_OPEN),

  BACK_SLASH(0x5C, Key.BACK_SLASH),

  BRACKET_SQUARE_CLOSE(0x5D, Key.BRACKET_SQUARE_CLOSE),

  CARET(0x5E, Key.CARET),

  UNDERLINE(0x5F, Key.UNDERLINE),

  TICK_DOWN(0x60, Key.TICK_DOWN),

  A_LOW(0x61, Key.A_LOW),

  B_LOW(0x62, Key.B_LOW),

  C_LOW(0x63, Key.C_LOW),

  D_LOW(0x64, Key.D_LOW),

  E_LOW(0x65, Key.E_LOW),

  F_LOW(0x66, Key.F_LOW),

  G_LOW(0x67, Key.G_LOW),

  H_LOW(0x68, Key.H_LOW),

  I_LOW(0x69, Key.I_LOW),

  J_LOW(0x6A, Key.J_LOW),

  K_LOW(0x6B, Key.K_LOW),

  L_LOW(0x6C, Key.L_LOW),

  M_LOW(0x6D, Key.M_LOW),

  N_LOW(0x6E, Key.N_LOW),

  O_LOW(0x6F, Key.O_LOW),

  P_LOW(0x70, Key.P_LOW),

  Q_LOW(0x71, Key.Q_LOW),

  R_LOW(0x72, Key.R_LOW),

  S_LOW(0x73, Key.S_LOW),

  T_LOW(0x74, Key.T_LOW),

  U_LOW(0x75, Key.U_LOW),

  V_LOW(0x76, Key.V_LOW),

  W_LOW(0x77, Key.W_LOW),

  X_LOW(0x78, Key.X_LOW),

  Y_LOW(0x79, Key.Y_LOW),

  Z_LOW(0x7A, Key.Z_LOW),

  BRACKET_CURLY_OPEN(0x7B, Key.BRACKET_CURLY_OPEN),

  PIPE(0x7C, Key.PIPE),

  BRACKET_CURLY_CLOSE(0x7D, Key.BRACKET_CURLY_CLOSE),

  TILDE(0x7E, Key.TILDE),

  DEL(0x7F, Key.DEL),

  PAGE_UP(339, Key.PAGE_UP),

  PAGE_DOWN(338, Key.PAGE_DOWN),

  END(360, Key.END),

  HOME(262, Key.HOME),

  DELETE(330, Key.DELETE),

  LEFT(260, Key.LEFT),

  UP(259, Key.UP),

  RIGHT(261, Key.RIGHT),

  DOWN(258, Key.DOWN),

  F1(265, Key.F1),

  F2(266, Key.F2),

  F3(267, Key.F3),

  F4(268, Key.F4),

  F5(269, Key.F5),

  F6(270, Key.F6),

  F7(271, Key.F7),

  F8(272, Key.F8),

  F9(273, Key.F9),

  F10(274, Key.F10),

  F11(275, Key.F11),

  F12(276, Key.F12),

  RESIZE(410, Key.UNDEFINED),

  UNDEFINED(0, Key.UNDEFINED),

  ;

  private final int code;
  private final Key key;

  private NCursesKey(final int code, final Key key) {
    this.code = code;
    this.key = key;
  }

  /**
   * Returns native code of this {@link NCursesKey}.
   *
   * @return native code of this {@link NCursesKey}
   */
  public final int getCode() {
    return this.code;
  }

  /**
   * Returns the {@link Key}-Mapping of this {@link NCursesKey}.
   *
   * @return {@link Key}-Mapping of this {@link NCursesKey}
   */
  public final Key getKey() {
    return this.key;
  }

  /**
   *
   * {@inheritDoc}
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString() {
    return format("%s [%s]", this.name(), this.code);
  }

  /**
   * Returns the {@link NCursesKey} that belongs to the given native code.
   *
   * @param code
   *          native code to search for
   * @return {@link NCursesKey} that belongs to the given native code or {@link Key#UNDEFINED} if the given code is
   *         unknown
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public static NCursesKey byCode(final int code) {
    for (final NCursesKey key : values()) {
      if (key.getCode() == code) {
        return key;
      }
    }
    return NCursesKey.UNDEFINED;
  }
}
