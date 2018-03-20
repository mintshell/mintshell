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
package org.mintshell.dispatcher.reflection;

import java.lang.reflect.Parameter;

import org.mintshell.annotation.Nullable;
import org.mintshell.command.CommandParameter;

/**
 * Factory responsible to create {@link CommandParameter} instances from reflection {@link Parameter}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
@FunctionalInterface
public abstract interface ReflectionCommandParameterFactory {

  /**
   * Creates a new {@link CommandParameter} from the given {@link Parameter} and index.
   *
   * @param type
   *          type of the parameter
   * @param index
   *          paramter index within the method's signature
   * @param name
   *          (optional) parameter (long) name
   * @param shortName
   *          (optional) parameter short name
   * @param description
   *          (optional) parameter description
   * @param required
   *          {@code true} if the parameter is mandatory, {@code false} otherwise
   * @return {@link CommandParameter} instance
   * @throws UnsupportedParameterTypeException
   *           if the given type is not supported
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract ReflectionCommandParameter create(Class<?> type, int index, @Nullable String name, @Nullable Character shortName,
      @Nullable String description, boolean required) throws UnsupportedParameterTypeException;
}
