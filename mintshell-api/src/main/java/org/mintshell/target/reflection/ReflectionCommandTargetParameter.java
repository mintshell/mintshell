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
import org.mintshell.target.CommandTargetParameter;

/**
 * {@link CommandTargetParameter} that maps the a {@link Method} {@link Parameter} using reflection.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public abstract interface ReflectionCommandTargetParameter extends CommandTargetParameter {

  /**
   * Returns whether this {@link ReflectionCommandTargetParameter} supports the given type, where 'support' means
   * technical support and doesn't exclude any exeptions when trying to convert a concrete {@link String} value via the
   * {@link #of(String)} method.
   *
   * @param type
   *          type to be checked
   * @return {@code true} if this {@link ReflectionCommandTargetParameter} supports the given type, {@code false}
   *         otherwise
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract boolean isTypeSupported(Class<?> type);

  /**
   * Converts the given {@link String} value into an instance of the {@link ReflectionCommandTargetParameter}'s type.
   *
   * @param value
   *          {@link String} value to convert, may be {@code null}
   * @return type instance or {@code null}, if the given value was {@code null}
   * @throws ParameterConversionException
   *           if the conversion failed
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract Object of(@Nullable String value) throws ParameterConversionException;
}
