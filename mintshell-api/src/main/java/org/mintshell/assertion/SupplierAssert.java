/*
 * Copyright © 2017-2019 mintshell.org
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

/**
 * Describes an extended assertion component that is able to handle {@link Supplier} for assertion and therefore must
 * create invalidity {@link Throwable}s having a cause if handling the {@link Supplier} fails.
 *
 * @param <T>
 *          type of {@link Throwable} thrown on invalid assertion arguments
 *
 * @author Noqmar
 * @since 0.1.0
 */
public abstract interface SupplierAssert<T extends Throwable> extends Assert<T> {

  /**
   * Returns a specialized {@link Throwable} and is called, if assertion failed.
   *
   * @param reason
   *          reason describing why assertion failed
   * @param cause
   *          causing {@link Throwable}
   * @return the {@link Throwable} to indicate invalid assertion arguments
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract T invalid(String reason, Throwable cause);
}
