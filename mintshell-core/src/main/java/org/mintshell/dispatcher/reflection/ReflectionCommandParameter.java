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
import java.lang.reflect.Parameter;

import org.mintshell.annotation.Nullable;
import org.mintshell.assertion.Assert;
import org.mintshell.command.Command;
import org.mintshell.command.CommandParameter;

/**
 * Extension of a {@link CommandParameter} that uses reflection to map a {@link Method} {@link Parameter} to a
 * {@link CommandParameter}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public abstract class ReflectionCommandParameter extends CommandParameter {

  private final Class<?> type;

  /**
   * Creates a new command parameter.
   *
   * @param index
   *          index of the parameter in the originating methods's signature
   * @param type
   *          type of the parameter
   *
   * @throws UnsupportedParameterTypeException
   *           if the given parameter type isn's supported
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public ReflectionCommandParameter(final int index, final Class<?> type) throws UnsupportedParameterTypeException {
    super(index);
    this.type = Assert.ARG.isNotNull(type, "[type] must not be [null]");
    if (!this.isTypeSupported(type)) {
      throw new UnsupportedParameterTypeException(String.format("Type [%s] is not supported by [%s]", type.getName(), this.getClass().getName()));
    }
  }

  /**
   * Returns the type of the {@link CommandParameter}.
   *
   * @return parameter type
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Class<?> getType() {
    return this.type;
  }

  /**
   * Returns whether this parameter is required meaning it must be provided in order to execute the {@link Command} it
   * belongs to.
   *
   * @return {@code true} if this parameter is required, {@code false} otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public boolean isRequired() {
    return this.getType().isPrimitive();
  }

  /**
   * Converts the given {@link String} value into an instance of the {@link CommandParameter}'s type.
   *
   * @param value
   *          {@link String} value to convert, may be {@code null}
   * @return type instance or {@code null}, if the given value was {@code null}
   * @throws ParameterConversionException
   *           if the conversion failed
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract Object of(@Nullable String value) throws ParameterConversionException;

  /**
   * Returns whether this {@link CommandParameter} supports the given type, where 'support' means technical support and
   * doesn't exclude any exeptions when trying to convert a concrete {@link String} value via the
   * {@link #of(Class, String)} method.
   *
   * @param type
   *          type to be checked
   * @return {@code true} if this {@link CommandParameter} supports the given type, {@code false} otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected abstract boolean isTypeSupported(Class<?> type);
}
