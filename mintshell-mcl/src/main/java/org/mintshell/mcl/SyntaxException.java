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
package org.mintshell.mcl;

import static java.lang.String.format;

import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * Exception that indicates invalid command syntax.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class SyntaxException extends RuntimeException {

  private static final String MESSAGE_PATTERN = "Syntax error at [%s,%s]: %s";

  private static final long serialVersionUID = -1373278119311501534L;

  /**
   * Creates a new instance.
   *
   * @param recognizer
   *          antlr recognizer
   * @param offendingSymbol
   *          offending symbol
   * @param line
   *          line number
   * @param charPositionInLine
   *          character position in line
   * @param message
   *          message text
   * @param cause
   *          causing {@link Exception}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public SyntaxException(final Recognizer<?, ?> recognizer, final Object offendingSymbol, final int line, final int charPositionInLine, final String message,
      final RecognitionException cause) {
    super(format(MESSAGE_PATTERN, line, charPositionInLine, message), cause);
  }
}
