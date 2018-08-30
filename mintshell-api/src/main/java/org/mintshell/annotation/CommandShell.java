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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.mintshell.target.CommandShellExitException;

/**
 * Annotation to map a type (class) to a {@link org.mintshell.target.CommandShell}
 *
 * @author Noqmar
 * @since 0.2.0
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface CommandShell {

  /**
   * Returns the message to be displayed when entering the {@link org.mintshell.target.CommandShell}.
   *
   * @return enter message
   *
   * @author Noqmar
   * @since 0.2.0
   */
  String enterMessage() default "";

  /**
   * Returns the description of the {@link #exitCommands()}, if any.
   *
   * @return descrition of the {@link #exitCommands()}
   *
   * @author Noqmar
   * @since 0.2.0
   */
  String exitCommandDescription() default "";

  /**
   * Command names, that should be mapped to {@link CommandTarget}s, that exit the
   * {@link org.mintshell.target.CommandShell} by throwing a {@link CommandShellExitException}.
   *
   * @return exit command names
   *
   * @author Noqmar
   * @since 0.2.0
   */
  String[] exitCommands() default {};

  /**
   * Returns the message to be displayed when exiting the {@link org.mintshell.target.CommandShell}.
   *
   * @return exit message
   *
   * @author Noqmar
   * @since 0.2.0
   */
  String exitMessage() default "";

  /**
   * Returns the (static) prompt of the command shell.
   *
   * @return (static) propt of the command shell
   *
   * @author Noqmar
   * @since 0.2.0
   */
  String prompt();

  /**
   * Returns a separator to building a prompt path. If the separator is empty no path shall be displayed.
   *
   * @return prompt path separator
   *
   * @author Noqmar
   * @since 0.2.0
   */
  String promptPathSeparator() default "";
}
