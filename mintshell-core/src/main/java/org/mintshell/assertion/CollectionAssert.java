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

import java.util.Collection;

import org.mintshell.annotation.Nullable;

/**
 * Assertion resposible to assert properties on {@link Collection}s
 *
 * @param <T>
 *          type of {@link Throwable} for invalid assertion arguments
 *
 * @author Noqmar
 * @since 0.1.0
 */
public abstract interface CollectionAssert<T extends Throwable> extends Assert<T> {

  /**
   * Asserts that a given {@link Collection} contains a required element.
   *
   * @param collection
   *          collection to assert
   * @param element
   *          element that must be contained by the {@link Collection}
   * @return the asserted collection
   * @throws T
   *           if the required element is not contained by the given {@link Collection}
   * @param <E>
   *          element type
   * @param <C>
   *          collection type
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default <E, C extends Collection<E>> C contains(final C collection, @Nullable final E element) throws T {
    return contains(collection, element, String.format("The given collection [%s] does not contain the element[%s]!", collection, element));
  }

  /**
   * Asserts that a given {@link Collection} contains a required element.
   *
   * @param collection
   *          collection to assert
   * @param element
   *          element that must be contained by the {@link Collection}
   * @param reason
   *          to be delegated to the thrown {@link Throwable} if collenction does not contain the given subject
   * @return the asserted collection
   * @throws T
   *           if the required element is not contained by the given {@link Collection}
   * @param <E>
   *          element type
   * @param <C>
   *          collection type
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default <E, C extends Collection<E>> C contains(final C collection, @Nullable final E element, final String reason) throws T {
    isNotNull(collection);
    if (!collection.contains(element)) {
      throw invalid(reason);
    }
    return collection;
  }
}
