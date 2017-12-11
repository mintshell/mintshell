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
package org.mintshell.assertion;

/**
 * Assertion responsible to assert properties on {@link Number}s.
 *
 * @param <T>
 *          type of {@link Throwable} for invalid assertion arguments
 *
 * @author Noqmar
 * @since 0.1.0
 */
public abstract interface NumberAssert<T extends Throwable> extends Assert<T> {

  /**
   * Asserts that the given {@link Number} is greater than a given {@link Number}-reference.
   *
   * @param number
   *          {@link Number} to assert
   * @param reference
   *          reference {@link Number}
   * @return asserted {@link Number}
   * @throws T
   *           if the given number is smaller or equal to the given refenrence
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default <N extends Number> N isGreater(final N number, final N reference) throws T {
    if (Assert.ARG.isNotNull(number).longValue() <= Assert.ARG.isNotNull(reference).longValue()) {
      throw invalid("[%s] is not greater than [%s]");
    }
    return number;
  }

  /**
   * Asserts that the given {@link Number} is smaller than a given {@link Number}-reference.
   *
   * @param number
   *          {@link Number} to assert
   * @param reference
   *          reference {@link Number}
   * @return asserted {@link Number}
   * @throws T
   *           if the given number is greater or equal to the given refenrence
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default <N extends Number> N isSmaller(final N number, final N reference) throws T {
    if (Assert.ARG.isNotNull(number).longValue() >= Assert.ARG.isNotNull(reference).longValue()) {
      throw invalid("[%s] is not greater than [%s]");
    }
    return number;
  }
}
