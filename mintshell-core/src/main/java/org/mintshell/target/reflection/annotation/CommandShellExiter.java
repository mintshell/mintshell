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
package org.mintshell.target.reflection.annotation;

import org.mintshell.assertion.Assert;
import org.mintshell.target.CommandShellExitException;

/**
 * May be used as command target source for command shell exit. It has only one method throwing the necessary
 * {@link CommandShellExitException}.
 *
 *
 * @author Noqmar
 * @since 0.2.0
 */
final class CommandShellExiter {

  static final String EXIT_METHOD_NAME = "exit";

  private final String exitMessage;

  /**
   * Creates a new instance with empty exit message.
   *
   * @author Noqmar
   * @since 0.2.0
   */
  CommandShellExiter() {
    this("");
  }

  /**
   * Creates a new instance with an exit message.
   *
   * @param exitMessage
   *          exit message
   *
   * @author Noqmar
   * @since 0.2.0
   */
  CommandShellExiter(final String exitMessage) {
    this.exitMessage = Assert.ARG.isNotNull(exitMessage, "[exitMessage] must not be [null]");
  }

  /**
   * Exit method.
   *
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public void exit() {
    throw new CommandShellExitException(this.exitMessage);
  }
}
