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

/**
 * Enumerates all accessible keys of a keyboard.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public enum Key {

  CANCEL("Cancel"),

  BACK_SPACE("Backspace"),

  TAB("Tab", KeyCodeClass.WHITESPACE | KeyCodeClass.PRINTABLE),

  CLEAR("Clear"),

  ENTER("Enter", KeyCodeClass.WHITESPACE),

  ESCAPE("Esc"),

  SPACE(" ", KeyCodeClass.WHITESPACE | KeyCodeClass.PRINTABLE),

  EXCLAMATION("!", KeyCodeClass.SPECIAL),

  DOUBLE_QUOTE("\"", KeyCodeClass.SPECIAL),

  HASH("#", KeyCodeClass.SPECIAL),

  SECTION("§", KeyCodeClass.SPECIAL),

  DOLLAR("$", KeyCodeClass.SPECIAL),

  PERCENT("%", KeyCodeClass.SPECIAL),

  AMPERSAND("&", KeyCodeClass.SPECIAL),

  SINGLE_QUOTE("'", KeyCodeClass.SPECIAL),

  BRACKET_ROUND_OPEN("(", KeyCodeClass.SPECIAL),

  BRACKET_ROUND_CLOSE(")", KeyCodeClass.SPECIAL),

  ASTERISK("*", KeyCodeClass.SPECIAL),

  PLUS("+", KeyCodeClass.SPECIAL),

  COMMA(",", KeyCodeClass.SPECIAL),

  MINUS("-", KeyCodeClass.SPECIAL),

  DOT(".", KeyCodeClass.SPECIAL),

  SLASH("/", KeyCodeClass.SPECIAL),

  ZERO("0", KeyCodeClass.DIGIT),

  ONE("1", KeyCodeClass.DIGIT),

  TWO("2", KeyCodeClass.DIGIT),

  THREE("3", KeyCodeClass.DIGIT),

  FOUR("4", KeyCodeClass.DIGIT),

  FIVE("5", KeyCodeClass.DIGIT),

  SIX("6", KeyCodeClass.DIGIT),

  SEVEN("7", KeyCodeClass.DIGIT),

  EIGHT("8", KeyCodeClass.DIGIT),

  NINE("9", KeyCodeClass.DIGIT),

  COLON(":", KeyCodeClass.SPECIAL),

  SEMICOLON(";", KeyCodeClass.SPECIAL),

  SMALLER("<", KeyCodeClass.SPECIAL),

  EQUALS("=", KeyCodeClass.SPECIAL),

  GREATER(">", KeyCodeClass.SPECIAL),

  QUESTION_MARK("?", KeyCodeClass.SPECIAL),

  AT("@", KeyCodeClass.SPECIAL),

  A_UP("A", KeyCodeClass.LETTER),

  B_UP("B", KeyCodeClass.LETTER),

  C_UP("C", KeyCodeClass.LETTER),

  D_UP("D", KeyCodeClass.LETTER),

  E_UP("E", KeyCodeClass.LETTER),

  F_UP("F", KeyCodeClass.LETTER),

  G_UP("G", KeyCodeClass.LETTER),

  H_UP("H", KeyCodeClass.LETTER),

  I_UP("I", KeyCodeClass.LETTER),

  J_UP("J", KeyCodeClass.LETTER),

  K_UP("K", KeyCodeClass.LETTER),

  L_UP("L", KeyCodeClass.LETTER),

  M_UP("M", KeyCodeClass.LETTER),

  N_UP("N", KeyCodeClass.LETTER),

  O_UP("O", KeyCodeClass.LETTER),

  P_UP("P", KeyCodeClass.LETTER),

  Q_UP("Q", KeyCodeClass.LETTER),

  R_UP("R", KeyCodeClass.LETTER),

  S_UP("S", KeyCodeClass.LETTER),

  T_UP("T", KeyCodeClass.LETTER),

  U_UP("U", KeyCodeClass.LETTER),

  V_UP("V", KeyCodeClass.LETTER),

  W_UP("W", KeyCodeClass.LETTER),

  X_UP("X", KeyCodeClass.LETTER),

  Y_UP("Y", KeyCodeClass.LETTER),

  Z_UP("Z", KeyCodeClass.LETTER),

  BRACKET_SQUARE_OPEN("[", KeyCodeClass.SPECIAL),

  BACK_SLASH("\\", KeyCodeClass.SPECIAL),

  BRACKET_SQUARE_CLOSE("]", KeyCodeClass.SPECIAL),

  CARET("^", KeyCodeClass.SPECIAL),

  UNDERLINE("_", KeyCodeClass.SPECIAL),

  TICK_DOWN("`", KeyCodeClass.SPECIAL),

  A_LOW("a", KeyCodeClass.LETTER),

  B_LOW("b", KeyCodeClass.LETTER),

  C_LOW("c", KeyCodeClass.LETTER),

  D_LOW("d", KeyCodeClass.LETTER),

  E_LOW("e", KeyCodeClass.LETTER),

  F_LOW("f", KeyCodeClass.LETTER),

  G_LOW("g", KeyCodeClass.LETTER),

  H_LOW("h", KeyCodeClass.LETTER),

  I_LOW("i", KeyCodeClass.LETTER),

  J_LOW("j", KeyCodeClass.LETTER),

  K_LOW("k", KeyCodeClass.LETTER),

  L_LOW("l", KeyCodeClass.LETTER),

  M_LOW("m", KeyCodeClass.LETTER),

  N_LOW("n", KeyCodeClass.LETTER),

  O_LOW("o", KeyCodeClass.LETTER),

  P_LOW("p", KeyCodeClass.LETTER),

  Q_LOW("q", KeyCodeClass.LETTER),

  R_LOW("r", KeyCodeClass.LETTER),

  S_LOW("s", KeyCodeClass.LETTER),

  T_LOW("t", KeyCodeClass.LETTER),

  U_LOW("u", KeyCodeClass.LETTER),

  V_LOW("v", KeyCodeClass.LETTER),

  W_LOW("w", KeyCodeClass.LETTER),

  X_LOW("x", KeyCodeClass.LETTER),

  Y_LOW("y", KeyCodeClass.LETTER),

  Z_LOW("z", KeyCodeClass.LETTER),

  BRACKET_CURLY_OPEN("{", KeyCodeClass.SPECIAL),

  PIPE("|", KeyCodeClass.SPECIAL),

  BRACKET_CURLY_CLOSE("}", KeyCodeClass.SPECIAL),

  TILDE("~", KeyCodeClass.SPECIAL),

  DEL("Del"),

  PAGE_UP("Page Up", KeyCodeClass.NAVIGATION),

  PAGE_DOWN("Page Down", KeyCodeClass.NAVIGATION),

  END("End", KeyCodeClass.NAVIGATION),

  HOME("Home", KeyCodeClass.NAVIGATION),

  INSERT("Insert"),

  DELETE("Delete"),

  LEFT("Left", KeyCodeClass.ARROW | KeyCodeClass.NAVIGATION),

  UP("Up", KeyCodeClass.ARROW | KeyCodeClass.NAVIGATION),

  RIGHT("Right", KeyCodeClass.ARROW | KeyCodeClass.NAVIGATION),

  DOWN("Down", KeyCodeClass.ARROW | KeyCodeClass.NAVIGATION),

  F1("F1", KeyCodeClass.FUNCTION),

  F2("F2", KeyCodeClass.FUNCTION),

  F3("F3", KeyCodeClass.FUNCTION),

  F4("F4", KeyCodeClass.FUNCTION),

  F5("F5", KeyCodeClass.FUNCTION),

  F6("F6", KeyCodeClass.FUNCTION),

  F7("F7", KeyCodeClass.FUNCTION),

  F8("F8", KeyCodeClass.FUNCTION),

  F9("F9", KeyCodeClass.FUNCTION),

  F10("F10", KeyCodeClass.FUNCTION),

  F11("F11", KeyCodeClass.FUNCTION),

  F12("F12", KeyCodeClass.FUNCTION),

  UNDEFINED("Undefined"),

  ;

  final String value;
  private int mask;

  private Key(final String value) {
    this(value, 0);
  }

  private Key(final String value, final int mask) {
    this.value = value;
    this.mask = mask;
  }

  /**
   * Returns the {@link String} value of this key.
   *
   * @return {@link String} value of this key
   */
  public final String getValue() {
    return this.value;
  }

  /**
   * Left, right, up, down keys (including the keypad arrows)
   *
   * @return true if this key code corresponds to an arrow key
   * @since JavaFX 2.2
   */
  public final boolean isArrowKey() {
    return (this.mask & KeyCodeClass.ARROW) != 0;
  }

  /**
   * All Digit keys (including the keypad digits)
   *
   * @return true if this key code corresponds to a digit key
   * @since JavaFX 2.2
   */
  public final boolean isDigitKey() {
    return (this.mask & KeyCodeClass.DIGIT) != 0;
  }

  /**
   * Function keys like F1, F2, etc...
   *
   * @return true if this key code corresponds to a functional key
   */
  public final boolean isFunctionKey() {
    return (this.mask & KeyCodeClass.FUNCTION) != 0;
  }

  /**
   * All keys on the keypad
   *
   * @return true if this key code corresponds to a keypad key
   */
  public final boolean isKeypadKey() {
    return (this.mask & KeyCodeClass.KEYPAD) != 0;
  }

  /**
   * All keys with letters
   *
   * @return true if this key code corresponds to a letter key
   */
  public final boolean isLetterKey() {
    return (this.mask & KeyCodeClass.LETTER) != 0;
  }

  /**
   * Keys that could act as a modifier
   *
   * @return true if this key code corresponds to a modifier key
   */
  public final boolean isModifierKey() {
    return (this.mask & KeyCodeClass.MODIFIER) != 0;
  }

  /**
   * Navigation keys are arrow keys and Page Down, Page Up, Home, End (including keypad keys)
   *
   * @return true if this key code corresponds to a navigation key
   */
  public final boolean isNavigationKey() {
    return (this.mask & KeyCodeClass.NAVIGATION) != 0;
  }

  /**
   * Printable key.
   *
   * @return true if this key is printable
   */
  public final boolean isPrintableKey() {
    return this.isLetterKey() || this.isDigitKey() || this.isSpecialKey() || (this.mask & KeyCodeClass.PRINTABLE) != 0;
  }

  /**
   * All special keys (exclamation, ampersanc etc.)
   *
   * @return true if this key code corresponds to a special key
   */
  public final boolean isSpecialKey() {
    return (this.mask & KeyCodeClass.SPECIAL) != 0;
  }

  /**
   * Space, tab and enter
   *
   * @return true if this key code corresponds to a whitespace key
   */
  public final boolean isWhitespaceKey() {
    return (this.mask & KeyCodeClass.WHITESPACE) != 0;
  }

  private static class KeyCodeClass {
    private static final int FUNCTION = 1;;

    private static final int NAVIGATION = 1 << 1;
    private static final int ARROW = 1 << 2;
    private static final int MODIFIER = 1 << 3;
    private static final int LETTER = 1 << 4;
    private static final int DIGIT = 1 << 5;
    private static final int KEYPAD = 1 << 6;
    private static final int WHITESPACE = 1 << 7;
    private static final int SPECIAL = 1 << 8;
    private static final int PRINTABLE = 1 << 9;

    private KeyCodeClass() {
    }
  }

}
