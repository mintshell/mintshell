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

import static java.lang.String.format;

import org.mintshell.annotation.Nullable;
import org.mintshell.command.CommandParameter;

/**
 * Implementation of a {@link CommandParameter} that is able to handle primitive Types and it Box-Classes.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class PrimitiveParameter extends ReflectionCommandParameter {

  /**
   * {@link ReflectionCommandParameterFactory} that creates instance of {@link PrimitiveParameter}s.
   */
  public static final ReflectionCommandParameterFactory FACTORY = (type, index, name, shortName, description, required) -> new PrimitiveParameter(type, index,
      name, shortName, description, required);

  /**
   * Creates a new command parameter.
   *
   * @param type
   *          type of the parameter
   * @param index
   *          index of the parameter in the originating methods's signature
   * @throws UnsupportedParameterTypeException
   *           if the given parameter type isn's supported
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public PrimitiveParameter(final Class<?> type, final int index) throws UnsupportedParameterTypeException {
    this(type, index, null, null, null, DEFAULT_REQUIRED);
  }

  /**
   * Creates a new command parameter.
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
   *          {@code true} if the parameter is mandatory, {@code false} otherwise
   * @throws UnsupportedParameterTypeException
   *           if the given parameter type isn't supported
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public PrimitiveParameter(final Class<?> type, final int index, @Nullable final String name, @Nullable final Character shortName,
      final @Nullable String description, final boolean required) throws UnsupportedParameterTypeException {
    super(type, index, name, shortName, description, required);
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.command.CommandParameter#isTypeSupported(java.lang.Class)
   */
  @Override
  public boolean isTypeSupported(final Class<?> type) {
    return false //
        || type == boolean.class //
        || type == byte.class //
        || type == char.class //
        || type == double.class //
        || type == float.class //
        || type == int.class //
        || type == long.class //
        || type == short.class //
        || type == String.class;
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
      if (this.getType() == boolean.class) {
        return Boolean.parseBoolean(value);
      }
      if (this.getType() == byte.class) {
        return Byte.parseByte(value);
      }
      if (this.getType() == char.class) {
        return value.charAt(0);
      }
      if (this.getType() == double.class) {
        return Double.parseDouble(value);
      }
      if (this.getType() == float.class) {
        return Float.parseFloat(value);
      }
      if (this.getType() == int.class) {
        return Integer.parseInt(value);
      }
      if (this.getType() == long.class) {
        return Long.parseLong(value);
      }
      if (this.getType() == short.class) {
        return Short.parseShort(value);
      }
      if (this.getType() == String.class) {
        return value;
      }
      throw new UnsupportedParameterTypeException(format("Type [%s] is not supported by [%s]", this.getType().getName(), this.getClass().getName()));
    } catch (final RuntimeException e) {
      throw new ParameterConversionException(format("Conversion of [%s] into instance of [%s] failed", value, this.getType().getName()), e);
    } catch (final Exception e) {
      throw new ParameterConversionException("Conversion of [%s] into instance of [%s] failed", e);
    }
  }
}
