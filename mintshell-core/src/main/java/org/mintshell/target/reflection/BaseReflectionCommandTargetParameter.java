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
import java.lang.reflect.Parameter;

import org.mintshell.annotation.Nullable;
import org.mintshell.assertion.Assert;
import org.mintshell.target.BaseCommandTargetParameter;
import org.mintshell.target.CommandTargetParameter;

/**
 * Base implementation of a {@link CommandTargetParameter} that uses reflection to map a {@link Method}
 * {@link Parameter} to a {@link CommandTargetParameter}.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public abstract class BaseReflectionCommandTargetParameter extends BaseCommandTargetParameter implements ReflectionCommandTargetParameter {

  protected static final boolean DEFAULT_REQUIRED = false;

  private final Class<?> type;

  /**
   * Creates a new parameter.
   *
   * @param type
   *          type of the parameter
   * @param index
   *          parameter index
   * @throws UnsupportedParameterTypeException
   *           if the given parameter type isn't supported
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public BaseReflectionCommandTargetParameter(final Class<?> type, final int index) throws UnsupportedParameterTypeException {
    this(type, index, null, null, null, DEFAULT_REQUIRED);
  }

  /**
   * Creates a new parameter.
   *
   * @param type
   *          type of the parameter
   * @param index
   *          parameter index
   * @param required
   *          {@code true} if the parameter is required for execution, {@code false} otherwise
   * @throws UnsupportedParameterTypeException
   *           if the given parameter type isn't supported
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public BaseReflectionCommandTargetParameter(final Class<?> type, final int index, final boolean required) throws UnsupportedParameterTypeException {
    this(type, index, null, null, null, required);
  }

  /**
   * Creates a new parameter.
   *
   * @param type
   *          type of the parameter
   * @param index
   *          parameter index
   * @param name
   *          (optional) parameter (long) name
   * @param shortName
   *          (optional) parameter short name
   * @param description
   *          (optional) parameter description
   * @param required
   *          {@code true} if the parameter is required, {@code false} otherwise
   * @throws UnsupportedParameterTypeException
   *           if the given parameter type isn't supported
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public BaseReflectionCommandTargetParameter(final Class<?> type, final int index, final @Nullable String name, final @Nullable Character shortName,
      final @Nullable String description, final boolean required) throws UnsupportedParameterTypeException {
    super(index, name, shortName, description, required);
    this.type = Assert.ARG.isNotNull(type, "[type] must not be [null]");
    if (!this.isTypeSupported(type)) {
      throw new UnsupportedParameterTypeException(String.format("Type [%s] is not supported by [%s]", type.getName(), this.getClass().getName()));
    }
  }

  /**
   * Returns the type of the parameter.
   *
   * @return parameter type
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public Class<?> getType() {
    return this.type;
  }

  /**
   *
   * {@inheritDoc}
   * 
   * @see org.mintshell.target.BaseCommandTargetParameter#isRequired()
   */
  @Override
  public boolean isRequired() {
    return this.getType().isPrimitive();
  }
}
