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
package org.mintshell.target;

import org.mintshell.assertion.Assert;

/**
 * Exception that indicates that {@link #getCount()} subshells shall be closed.<br>
 * <b>Note</b>:The first (main) shell cannot be closed so if {@link #getCount()} exceeds the number of subshells, all
 * subshells are closed except of the main shell.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public class CommandShellExitException extends RuntimeException {

  private static final long serialVersionUID = -5020533993505239924L;

  private final int count;

  /**
   * Constructs a new runtime exception with the specified detail message. The cause is not initialized, and may
   * subsequently be initialized by a call to {@link #initCause}.
   *
   * @param message
   *          the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
   *          The message must not be {@code null} but may be empty.
   * @author Noqmar
   * @since 0.2.0
   */
  public CommandShellExitException(final String message, final int count) {
    super(Assert.ARG.isNotNull(message, "[messge] must not be [null]"));
    this.count = count;
  }

  /**
   * Returns the number of shells to be exited.
   *
   * @return number of subshells to exit, a negative value means to exit all subshells
   *
   * @author Noqmar
   * @since 0.3.0
   */
  public int getCount() {
    return this.count;
  }

  /**
   * Creates an exception to close only the current subshell.
   * 
   * @param message
   *          message to be displayed
   * @return exception
   *
   * @author Noqmar
   * @since 0.3.0
   */
  public static CommandShellExitException createExit(final String message) {
    return new CommandShellExitException(message, 1);
  }

  /**
   * Creates an exception to close all subshells.
   * 
   * @param message
   *          message to be displayed
   * @return exception
   *
   * @author Noqmar
   * @since 0.3.0
   */
  public static CommandShellExitException createExitAll(final String message) {
    return new CommandShellExitException(message, -1);
  }
}
