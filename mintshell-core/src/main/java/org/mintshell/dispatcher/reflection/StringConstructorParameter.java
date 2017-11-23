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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.mintshell.annotation.Nullable;
import org.mintshell.command.CommandParameter;

/**
 * Implementation of a {@link CommandParameter} that is able to handle classes, that provide a
 * {@link String}-constructor.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class StringConstructorParameter extends ReflectionCommandParameter {

  /**
   * {@link ReflectionCommandParameterFactory} that creates instance of {@link StringConstructorParameter}s.
   */
  public static final ReflectionCommandParameterFactory FACTORY = (type, index) -> new StringConstructorParameter(type, index);

  private final Constructor<?> stringConstructor;

  /**
   * Creates a new command parameter.
   * 
   * @param type
   *          type of the parameter
   * @param index
   *          index of the parameter in the originating methods's signature
   *
   * @throws UnsupportedParameterTypeException
   *           if the given parameter type isn's supported
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public StringConstructorParameter(final Class<?> type, final int index) throws UnsupportedParameterTypeException {
    super(type, index);
    try {
      this.stringConstructor = this.getType().getConstructor(String.class);
    } catch (NoSuchMethodException | SecurityException e) {
      throw new UnsupportedParameterTypeException(String.format("Type [%s] is not supported by [%s]", type.getName(), this.getClass().getName()), e);
    }
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.command.CommandParameter#isTypeSupported(java.lang.Class)
   */
  @Override
  public boolean isTypeSupported(final Class<?> type) {
    try {
      type.getConstructor(String.class);
      return true;
    } catch (final NoSuchMethodException e) {
      return false;
    }
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
      return this.getType().cast(this.stringConstructor.newInstance(value));
    } catch (InstantiationException | IllegalAccessException | RuntimeException e) {
      throw new ParameterConversionException("Conversion of [%s] into instance of [%s] failed", e);
    } catch (final InvocationTargetException e) {
      throw new ParameterConversionException("Conversion of [%s] into instance of [%s] failed", e.getCause());
    }
  }
}
