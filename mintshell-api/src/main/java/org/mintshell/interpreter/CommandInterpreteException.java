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
package org.mintshell.interpreter;

import org.mintshell.command.Command;

/**
 * Exception indicating a problem during {@link Command} interpretion.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public final class CommandInterpreteException extends Exception {

  private static final long serialVersionUID = 668838547511715567L;

  /**
   * Constructs a new runtime exception with the specified detail message. The cause is not initialized, and may
   * subsequently be initialized by a call to {@link #initCause}.
   *
   * @param message
   *          the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
   * 
   * @author Noqmar
   * @since 0.1.0
   */
  public CommandInterpreteException(final String message) {
    super(message);
  }

  /**
   * /** Constructs a new runtime exception with the specified detail message and cause.
   * <p>
   * Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated in this runtime
   * exception's detail message.
   *
   *
   * @param message
   *          the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
   * @param cause
   *          the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt> value
   *          is permitted, and indicates that the cause is nonexistent or unknown.)
   * 
   * @author Noqmar
   * @since 0.1.0
   */
  public CommandInterpreteException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new runtime exception with the specified cause and a detail message of
   * <tt>(cause==null ? null : cause.toString())</tt> (which typically contains the class and detail message of
   * <tt>cause</tt>). This constructor is useful for runtime exceptions that are little more than wrappers for other
   * throwables.
   *
   * @param cause
   *          the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt> value
   *          is permitted, and indicates that the cause is nonexistent or unknown.)
   * 
   * @author Noqmar
   * @since 0.1.0
   *
   */
  public CommandInterpreteException(final Throwable cause) {
    super(cause);
  }
}
