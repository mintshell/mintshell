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
package org.mintshell.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.mintshell.command.CommandParameter;

/**
 * Annotation to map a method parameter to {@link CommandParameter}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
@Documented
@Retention(RUNTIME)
@Target({ PARAMETER })
public abstract @interface Param {

  /**
   * Returns the description of the command parameter.
   *
   * @return description of the command parameter
   *
   * @author Noqmar
   * @since 0.1.0
   */
  String description() default "";

  /**
   * Returns the (long) name of the command parameter.
   *
   * @return name of the command parameter
   *
   * @author Noqmar
   * @since 0.1.0
   */
  String name() default "";

  /**
   * Flag that classifies the command parameter to be required for command execution.
   *
   * @return {@code true} if the command parameter is required for command execution, {@code false} otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  boolean required() default true;

  /**
   * Returns the short name of the command parameter (option in case of a boolean parameter).
   *
   * @return short name of the command parameter
   *
   * @author Noqmar
   * @since 0.1.0
   */
  char shortName() default Character.UNASSIGNED;
}
