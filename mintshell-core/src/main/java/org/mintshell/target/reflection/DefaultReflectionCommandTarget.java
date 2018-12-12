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
package org.mintshell.target.reflection;

import java.lang.reflect.Method;
import java.util.List;

import org.mintshell.annotation.Nullable;
import org.mintshell.assertion.Assert;
import org.mintshell.target.BaseCommandTarget;

/**
 * Default implementation of a {@link ReflectionCommandTarget}.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public class DefaultReflectionCommandTarget extends BaseCommandTarget implements ReflectionCommandTarget {

  private final Method method;

  /**
   * Creates a new instance.
   *
   * @param method
   *          method to use
   * @param name
   *          name
   * @param description
   *          (optional) description text
   * @param parameters
   *          {@link List} of {@link ReflectionCommandTargetParameter}s
   * @throws UnsupportedParameterTypeException
   *           if at least one of the given method's parameters isn't supported
   * @author Noqmar
   * @since 0.2.0
   */
  public DefaultReflectionCommandTarget(final Method method, final String name, final @Nullable String description,
      final List<? extends ReflectionCommandTargetParameter> parameters) throws UnsupportedParameterTypeException {
    super(name, description, parameters);
    this.method = Assert.ARG.isNotNull(method, "[method] must not be [null]");
  }

  /**
   *
   * {@inheritDoc}
   * 
   * @see org.mintshell.target.reflection.ReflectionCommandTarget#getMethod()
   */
  @Override
  public Method getMethod() {
    return this.method;
  }
}
