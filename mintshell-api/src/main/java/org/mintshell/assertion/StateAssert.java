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

import java.util.function.Supplier;

import org.mintshell.annotation.Nullable;

/**
 * Assertion responsible to assert states, meaning asserting boolean expressions to {@code true} or {@code false} .
 *
 * @param <T>
 *          type of {@link Throwable} for invalid assertion arguments
 *
 * @author Noqmar
 * @since 0.1.0
 */
public abstract interface StateAssert<T extends Throwable> extends SupplierAssert<T> {

  /**
   * Asserts that the given expression is {@code false}.
   *
   * @param expression
   *          expression to assert
   * @return the given expression, in fact {@code false}
   * @throws T
   *           if the given expression is illegally {@code true}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default boolean isFalse(final boolean expression) throws T {
    return isFalse(expression, "The given expression is illegally true.");
  }

  /**
   * Asserts that the given expression is {@code false}.
   *
   * @param expression
   *          expression to assert
   * @param reason
   *          reason to be delegated to the thrown {@link Throwable} if the expression is illegally {@code true}
   * @return the given expression, in fact {@code false}
   * @throws T
   *           if the expression is illegally {@code true}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default boolean isFalse(final boolean expression, @Nullable final String reason) throws T {
    if (expression) {
      throw invalid(reason);
    }
    return expression;
  }

  /**
   * Asserts that the boolean supplied by the given {@link Supplier} is {@code false} .
   *
   * @param supplier
   *          supplier to assert
   * @return the given expression, in fact {@code false}
   * @throws T
   *           if the boolean supplied is illegally {@code true} or throws itself an {@link Exception}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default boolean isFalse(final Supplier<Boolean> supplier) throws T {
    return isFalse(supplier, "The supplied expression is illegally true.");
  }

  /**
   * Asserts that the boolean supplied by the given {@link Supplier} is {@code false} .
   *
   * @param supplier
   *          supplier to assert
   * @param reason
   *          reason delegated to the thrown {@link Throwable} if the {@link Supplier} supplies illegally {@code false}
   * @return the given expression, in fact {@code false}
   * @throws T
   *           if the boolean supplied is illegally {@code true} or throws itself an {@link Exception}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default boolean isFalse(final Supplier<Boolean> supplier, @Nullable final String reason) throws T {
    ARG.isNotNull(supplier);
    final Boolean expression;
    try {
      expression = supplier.get();
    } catch (final Exception any) {
      throw invalid(reason, any);
    }
    return isFalse(expression);
  }

  /**
   * Asserts that the given expression is {@code true}.
   *
   * @param expression
   *          expression to assert
   * @return the given expression, in fact {@code true}
   * @throws T
   *           if the given expression is {@code false}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default boolean isTrue(final boolean expression) throws T {
    return isTrue(expression, "The given expression is illegally false.");
  }

  /**
   * Asserts that the given expession is {@code true}.
   *
   * @param expression
   *          expression to assert
   * @param reason
   *          reason to be delegated to the thrown {@link Throwable} if the expression is {@code false}
   * @return the given expression, in fact {@code true}
   * @throws T
   *           if the given expression is {@code false}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default boolean isTrue(final boolean expression, @Nullable final String reason) throws T {
    if (!expression) {
      throw invalid(reason);
    }
    return expression;
  }

  /**
   * Asserts that the boolean supplied by the given {@link Supplier} is {@code true}.
   *
   * @param supplier
   *          supplier to assert
   * @return the supplied expression, in fact {@code true}
   * @throws T
   *           if the boolean supplied is {@code false} or throws itself an {@link Exception}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default boolean isTrue(final Supplier<Boolean> supplier) throws T {
    return isTrue(supplier, "The supplied expression is illegally false.");
  }

  /**
   * Asserts that the boolean supplied by the given {@link Supplier} is {@code true}.
   *
   * @param supplier
   *          supplier to assert
   * @param reason
   *          reason to be delegated to the thrown {@link Throwable} if the expression is {@code false}
   * @return the supplied expression, in fact {@code true}
   * @throws T
   *           if the boolean supplied is {@code false} or throws itself an {@link Exception}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default boolean isTrue(final Supplier<Boolean> supplier, @Nullable final String reason) throws T {
    ARG.isNotNull(supplier);
    final Boolean expression;
    try {
      expression = supplier.get();
    } catch (final Exception any) {
      throw invalid("The given supplier is invalid.", any);
    }
    return isTrue(expression, reason);
  }
}
