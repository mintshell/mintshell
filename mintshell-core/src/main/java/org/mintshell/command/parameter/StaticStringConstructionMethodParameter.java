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
package org.mintshell.command.parameter;

import static java.lang.String.format;
import static java.util.Arrays.stream;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;

import org.mintshell.annotation.Nullable;
import org.mintshell.command.CommandParameter;
import org.mintshell.command.ParameterConversionException;
import org.mintshell.command.UnsupportedParameterTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of a {@link CommandParameter} that is able to handle classes, that provide a static Method that takes
 * a single {@link String} as parameter and returns an instance of that class.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class StaticStringConstructionMethodParameter extends CommandParameter {

  private static final Logger LOG = LoggerFactory.getLogger(StaticStringConstructionMethodParameter.class);

  private final List<Method> candidates;

  /**
   * Creates a new command parameter.
   *
   * @param type
   *          type of the parameter
   * @throws UnsupportedParameterTypeException
   *           if the given parameter type isn's supported
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public StaticStringConstructionMethodParameter(final Class<?> type) throws UnsupportedParameterTypeException {
    super(type);
    this.candidates = this.findCandidates(type);
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.command.CommandParameter#of(java.lang.String)
   */
  @Override
  public Object of(final @Nullable String value) throws ParameterConversionException {
    try {
      if (value == null) {
        return null;
      }
      for (final Method candidate : this.candidates) {
        try {
          return this.getType().cast(candidate.invoke(null, value));
        } catch (final IllegalAccessException | RuntimeException e) {
          throw new ParameterConversionException("Conversion of [%s] into instance of [%s] failed", e);
        } catch (final InvocationTargetException e) {
          throw new ParameterConversionException("Conversion of [%s] into instance of [%s] failed", e.getCause());
        }
      }
      throw new UnsupportedParameterTypeException(format("Type [%s] is not supported by [%s]", this.getType().getName(), this.getClass().getName()));
    } catch (final Exception e) {
      throw new ParameterConversionException("Conversion of [%s] into instance of [%s] failed", e);
    }
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.command.CommandParameter#isTypeSupported(java.lang.Class)
   */
  @Override
  protected boolean isTypeSupported(final Class<?> type) {
    return !this.findCandidates(type).isEmpty();
  }

  private final List<Method> findCandidates(final Class<?> type) {
    return stream(type.getMethods()) //
        .filter(method -> Modifier.isStatic(method.getModifiers())) //
        .filter(method -> method.getParameterTypes().length == 1) //
        .filter(method -> method.getParameterTypes()[0] == String.class) //
        .filter(method -> method.getReturnType() == type) //
        .collect(Collectors.toList());
  }
}
