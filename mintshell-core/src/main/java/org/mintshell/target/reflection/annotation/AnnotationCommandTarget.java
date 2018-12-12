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
package org.mintshell.target.reflection.annotation;

import java.lang.reflect.Method;
import java.util.List;

import org.mintshell.annotation.CommandTarget;
import org.mintshell.annotation.Nullable;
import org.mintshell.assertion.Assert;
import org.mintshell.target.reflection.DefaultReflectionCommandTarget;
import org.mintshell.target.reflection.ReflectionCommandTargetParameter;
import org.mintshell.target.reflection.UnsupportedParameterTypeException;

/**
 * Extension of a {@link DefaultReflectionCommandTarget} that uses information of an annotated {@link Method} to map it
 * to a {@link CommandTarget}.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public class AnnotationCommandTarget extends DefaultReflectionCommandTarget {

  /**
   * Creates a new instance.
   *
   * @param method
   *          method to use
   * @param commandParameters
   *          list of command parameters
   * @throws UnsupportedParameterTypeException
   *           if at least one of the given method's parameters isn't supported
   * @author Noqmar
   * @since 0.2.0
   */
  public AnnotationCommandTarget(final Method method, final List<ReflectionCommandTargetParameter> commandParameters) throws UnsupportedParameterTypeException {
    super(Assert.ARG.isNotNull(method, "[method] must not be [null]"), findName(method), findDescription(method), commandParameters);
  }

  private static @Nullable String findDescription(final Method method) throws UnsupportedParameterTypeException {
    final org.mintshell.annotation.CommandTarget annotation = method.getAnnotation(org.mintshell.annotation.CommandTarget.class);
    if (annotation == null) {
      throw new UnsupportedParameterTypeException(
          String.format("[@%s] annotation missing at method [%s]", CommandTarget.class.getSimpleName(), method.getName()));
    }
    return annotation.description();
  }

  private static String findName(final Method method) throws UnsupportedParameterTypeException {
    final org.mintshell.annotation.CommandTarget annotation = method.getAnnotation(org.mintshell.annotation.CommandTarget.class);
    if (annotation == null) {
      throw new UnsupportedParameterTypeException(
          String.format("[@%s] annotation missing at method [%s]", CommandTarget.class.getSimpleName(), method.getName()));
    }
    return annotation.name();
  }
}
