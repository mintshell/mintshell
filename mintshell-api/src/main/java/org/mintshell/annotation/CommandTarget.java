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
package org.mintshell.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation to map a method to a {@link org.mintshell.target.CommandTarget}.
 *
 * @author Noqmar
 * @since 0.2.0
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface CommandTarget {

  /**
   * Returns an array of aliases for this {@link CommandTarget}.
   *
   * @return array of aliases or an empty array
   *
   * @author Noqmar
   * @since 0.2.0
   */
  String[] aliases() default {};

  /**
   * Returns the description of the command target.
   *
   * @return description of the command target
   *
   * @author Noqmar
   * @since 0.2.0
   */
  String description() default "";

  /**
   * Returns the name of the command target (<b>not</b> the value).
   *
   * @return name of the command target
   *
   * @author Noqmar
   * @since 0.2.0
   */
  String name();
}
