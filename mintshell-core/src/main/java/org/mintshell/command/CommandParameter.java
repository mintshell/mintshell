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
package org.mintshell.command;

import org.mintshell.annotation.Nullable;
import org.mintshell.assertion.Assert;

/**
 * A {@link CommandParameter} is used to provide data for the execution of a {@link Command}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public abstract class CommandParameter {

  private final Class<?> type;

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
  public CommandParameter(final Class<?> type) throws UnsupportedParameterTypeException {
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
