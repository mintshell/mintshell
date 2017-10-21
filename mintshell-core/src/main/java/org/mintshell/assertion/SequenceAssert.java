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
package org.mintshell.assertion;

import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mintshell.annotation.Nullable;

/**
 * Assertion responsible to assert {@link CharSequence}s and {@link String}s
 *
 * @param <T>
 *          type of {@link Throwable} for invalid assertion arguments
 *
 * @author Noqmar
 * @since 0.1.0
 */
public abstract interface SequenceAssert<T extends Throwable> extends SupplierAssert<T> {

  /**
   * Asserts that the given {@link String} isn't blank, meaning it is not {@code null} and not {@link String#isEmpty()}
   * .
   *
   * @param string
   *          {@link String} to assert
   * @return the asserted {@link String}
   * @throws T
   *           if the given {@link String} is blank
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default String isNotBlank(@Nullable final String string) throws T {
    return isNotBlank(string, "The given string [%s] is illegally blank.");
  }

  /**
   * Asserts that the given {@link String} isn't blank, meaning it is not {@code null} and not {@link String#isEmpty()}
   * .
   *
   * @param string
   *          {@link String} to assert
   * @param reason
   *          reason to be delegated to the thrown {@link Throwable} if the given {@link String} is blank
   * @return the asserted {@link String}
   * @throws T
   *           if the given {@link String} is blank
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default String isNotBlank(@Nullable final String string, @Nullable final String reason) throws T {
    isNotNull(string);
    if (string.trim().isEmpty()) {
      throw invalid(reason == null ? reason : String.format(reason, string));
    }
    return string;
  }

  /**
   * Asserts that the supplied {@link String} isn't blank, meaning it is not {@code null} and not
   * {@link String#isEmpty()} .
   *
   * @param supplier
   *          supplies the {@link String} to be asserted
   * @return the asserted {@link String}
   * @throws T
   *           if the given {@link Supplier} is invalid or the supplied {@link String} is blank
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default String isNotBlank(final Supplier<String> supplier) throws T {
    return isNotBlank(supplier, "The supplied string [%s] is illegally blank.");

  }

  /**
   * Asserts that the supplied {@link String} isn't blank, meaning it is not {@code null} and not
   * {@link String#isEmpty()} .
   *
   * @param supplier
   *          supplies the {@link String} to be asserted
   * @param reason
   *          reason to be delegated to the thrown {@link Throwable} if the supplied {@link String} is blank
   * @return the asserted {@link String}
   * @throws T
   *           if the given {@link Supplier} is invalid or the supplied {@link String} is blank
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default String isNotBlank(final Supplier<String> supplier, @Nullable final String reason) throws T {
    isNotNull(supplier, "The given supplier is illegally null!");
    try {
      return isNotBlank(supplier.get());
    } catch (final Exception any) {
      throw invalid("The given supplier is invalid.", any);
    }
  }

  /**
   * Asserts that the given {@link CharSequence} matches the given pattern of a regular expression. Therefore the
   * {@link CharSequence} is also asserted to be not {@code null}.
   *
   * @param sequence
   *          {@link CharSequence} to assert
   * @param pattern
   *          the pattern to be asserted
   * @return the asserted {@link CharSequence}
   * @throws T
   *           if the given {@link CharSequence} doesn't match the given pattern
   * @param <S>
   *          sequence type
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default <S extends CharSequence> S matches(@Nullable final S sequence, final Pattern pattern) throws T {
    isNotNull(sequence);
    isNotNull(pattern);
    final Matcher matcher = pattern.matcher(sequence);
    if (matcher.matches() == false) {
      throw invalid(String.format("The character sequence [%s] does not match the pattern [%s].", sequence, pattern));
    }
    return sequence;
  }
}
