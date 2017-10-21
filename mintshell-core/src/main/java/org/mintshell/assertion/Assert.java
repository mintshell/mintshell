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

import org.mintshell.annotation.Nullable;

/**
 * Collection of assertions providing constans for different purposes with specialized exceptions to be thrown due to
 * invalid assertion arguments.
 *
 *
 * @param <T>
 *          type of {@link Throwable} to throw in case of invalid assertion
 *
 * @author Noqmar
 * @since 0.1.0
 */
public abstract interface Assert<T extends Throwable> {

  /**
   * Object assertion to be used within any method context to check local variables.
   */
  public static final ObjectAssertion OBJ = new ObjectAssertion();

  /**
   * Argument assertion to be used to check method arguments.
   */
  public static final ArgumentAssertion ARG = new ArgumentAssertion();

  /**
   * Number assertion to be used within any method context to check local variables.
   */
  public static final NumberAssertion NUMBER = new NumberAssertion();

  /**
   * State assertions to be used to check expressions representing states within any context.
   */
  public static final StateAssert<IllegalStateException> STATE = new StateAssertion();

  /**
   * Returns a specialized {@link Throwable} and is called, if an assertion failed.
   *
   * @param reason
   *          reason describing why assertion failed
   * @return the {@link Throwable} to indicate invalid assertion arguments
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract T invalid(final String reason);

  /**
   * Asserts that the given subject is not {@code null}.
   *
   * @param subject
   *          subject to assert
   * @return asserted subject
   * @throws T
   *           if the subject is {@code null}
   * @param <S>
   *          subject type
   *
   * @author Noqmar
   * @since 0.1.0
   */

  public default <S> S isNotNull(@Nullable final S subject) throws T {
    return isNotNull(subject, "The given subject is illegally null!");
  }

  /**
   * Asserts that the given subject is not {@code null}.
   *
   * @param subject
   *          subject to assert
   * @param reason
   *          reason to be delegated to the thrown {@link Throwable} if the given subject is illegally {@code null}
   * @return asserted subject
   * @throws T
   *           if the subject is {@code null}
   * @param <S>
   *          subject type
   *
   * @author Noqmar
   * @since 0.1.0
   */

  public default <S> S isNotNull(@Nullable final S subject, @Nullable final String reason) throws T {
    if (subject == null) {
      throw invalid(reason);
    }
    return subject;
  }

  /**
   * Assertion implementation providing all checks while throwing {@link IllegalArgumentException}s. This assertion is
   * intended to be used to assert method arguments.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public static final class ArgumentAssertion implements SequenceAssert<IllegalArgumentException>, CollectionAssert<IllegalArgumentException>,
      StateAssert<IllegalArgumentException>, NumberAssert<IllegalArgumentException> {

    /**
     *
     * @{inheritDoc}
     * @see org.mintshell.assertion.Assert#invalid(java.lang.String)
     */
    @Override
    public IllegalArgumentException invalid(final String reason) {
      return new IllegalArgumentException(reason);
    }

    /**
     *
     * @{inheritDoc}
     * @see org.mintshell.assertion.SupplierAssert#invalid(java.lang.String, java.lang.Throwable)
     */
    @Override
    public IllegalArgumentException invalid(final String reason, final Throwable cause) {
      return new IllegalArgumentException(reason, cause);
    }
  }

  /**
   * Assertion implementation providing number format or range checks throwing {@link NumberFormatException}s. This
   * assertion is intended to be used to assert local variables.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public static final class NumberAssertion implements NumberAssert<NumberFormatException> {

    /**
     *
     * @{inheritDoc}
     * @see org.mintshell.assertion.Assert#invalid(java.lang.String)
     */
    @Override
    public NumberFormatException invalid(final String reason) {
      return new NumberFormatException(reason);
    }
  }

  /**
   * Assertion implementation providing {@code null}-checks throwing {@link NullPointerException}s. This assertion is
   * intended to be used to assert local variables.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public static final class ObjectAssertion implements Assert<NullPointerException> {

    /**
     *
     * @{inheritDoc}
     * @see org.mintshell.assertion.Assert#invalid(java.lang.String)
     */
    @Override
    public NullPointerException invalid(final String reason) {
      return new NullPointerException(reason);
    }
  }

  /**
   * Assertion implementation providing expression checks throwing {@link IllegalStateException}s. This assertion is
   * intended to be used to assert expressions representing states in any context.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public static final class StateAssertion implements StateAssert<IllegalStateException> {

    /**
     *
     * @{inheritDoc}
     * @see org.mintshell.assertion.Assert#invalid(java.lang.String)
     */
    @Override
    public IllegalStateException invalid(final String reason) {
      return new IllegalStateException(reason);
    }

    /**
     *
     * @{inheritDoc}
     * @see org.mintshell.assertion.SupplierAssert#invalid(java.lang.String, java.lang.Throwable)
     */
    @Override
    public IllegalStateException invalid(final String reason, final Throwable cause) {
      return new IllegalStateException(reason, cause);
    }
  }
}
