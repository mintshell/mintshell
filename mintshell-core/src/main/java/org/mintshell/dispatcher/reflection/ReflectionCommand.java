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
package org.mintshell.dispatcher.reflection;

import java.lang.reflect.Method;
import java.util.List;

import org.mintshell.annotation.Nullable;
import org.mintshell.assertion.Assert;
import org.mintshell.command.Command;
import org.mintshell.command.CommandParameter;

/**
 * Extension of a {@link Command} that uses reflection to map a {@link Method} to a {@link Command}.
 *
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class ReflectionCommand<P extends ReflectionCommandParameter> extends Command<P> {

  private final Method method;

  /**
   * Creates a new instance.
   *
   * @param method
   *          method to use
   * @param parameters
   *          {@link List} of {@link CommandParameter}s
   * @throws UnsupportedParameterTypeException
   *           if at least one of the given method's parameters isn't supported
   * @author Noqmar
   * @since 0.1.0
   */
  public ReflectionCommand(final Method method, final List<P> parameters) throws UnsupportedParameterTypeException {
    this(Assert.ARG.isNotNull(method, "[method] must not be [null]"), method.getName(), null, parameters);
  }

  /**
   * Creates a new instance.
   *
   * @param method
   *          method to use
   * @param name
   *          name
   * @param description
   *          description text
   * @param parameters
   *          {@link List} of {@link CommandParameter}s
   * @throws UnsupportedParameterTypeException
   *           if at least one of the given method's parameters isn't supported
   * @author Noqmar
   * @since 0.1.0
   */
  public ReflectionCommand(final Method method, final String name, final @Nullable String description, final List<P> parameters)
      throws UnsupportedParameterTypeException {
    super(name, description, parameters);
    this.method = method;
  }

  /**
   * Returns the method this {@link ReflectionCommand} is derived from.
   *
   * @return method
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Method getMethod() {
    return this.method;
  }
}
