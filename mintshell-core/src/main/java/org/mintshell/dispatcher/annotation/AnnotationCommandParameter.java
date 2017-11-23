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
package org.mintshell.dispatcher.annotation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.mintshell.annotation.Nullable;
import org.mintshell.assertion.Assert;
import org.mintshell.command.CommandParameter;
import org.mintshell.dispatcher.reflection.ParameterConversionException;
import org.mintshell.dispatcher.reflection.ReflectionCommandParameter;
import org.mintshell.dispatcher.reflection.UnsupportedParameterTypeException;

/**
 * Extension of a {@link ReflectionCommandParameter} that uses reflection to map a annotated {@link Method}
 * {@link Parameter} to a {@link CommandParameter}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class AnnotationCommandParameter extends ReflectionCommandParameter {

  private final ReflectionCommandParameter delegate;

  /**
   * Creates a new instance.
   *
   * @param delegate
   *          based {@link ReflectionCommandParameter}
   * @param name
   *          (optional) parameter (long) name
   * @param shortName
   *          (optional) parameter short name
   * @param required
   *          {@code true} if the parameter is mandatory, {@code false} sonst
   * @throws UnsupportedParameterTypeException
   *           if the given parameter type isn't supported
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public AnnotationCommandParameter(final ReflectionCommandParameter delegate, final @Nullable String name, final @Nullable Character shortName,
      final boolean required) throws UnsupportedParameterTypeException {
    super(Assert.ARG.isNotNull(delegate, "[delegate] must not be [null]").getType(), delegate.getIndex(), name, shortName, required);
    this.delegate = delegate;
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.dispatcher.reflection.ReflectionCommandParameter#isTypeSupported(java.lang.Class)
   */
  @Override
  public boolean isTypeSupported(final Class<?> type) {
    return this.delegate.isTypeSupported(type);
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.dispatcher.reflection.ReflectionCommandParameter#of(java.lang.String)
   */
  @Override
  public Object of(@Nullable final String value) throws ParameterConversionException {
    return this.delegate.of(value);
  }
}
