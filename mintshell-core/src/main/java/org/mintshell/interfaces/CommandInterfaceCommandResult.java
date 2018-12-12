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
package org.mintshell.interfaces;

import java.util.Optional;

import org.mintshell.command.Command;
import org.mintshell.command.CommandResult;
import org.mintshell.command.DefaultCommandResult;
import org.mintshell.dispatcher.CommandDispatcher;

/**
 * A {@link CommandResult} as the result of executing a {@link Command} within a {@link CommandInterface} before it gets
 * to be dispatched by a {@link CommandDispatcher}. It contains a flag that indicates whether the executed
 * {@link Command} shall be treated as consumed, which means that it doesn't get dispatched anymore.
 *
 * @param <T>
 *          type of the possibly contained value
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class CommandInterfaceCommandResult<T> extends DefaultCommandResult<T> {

  private final boolean commandConsumed;

  /**
   * Creates a new {@link State#SUCCEEDED} command result.
   *
   * @param command
   *          command that produced this result
   * @param value
   *          value of the command execution
   * @param commandConsumed
   *          flag that indicates that the executed {@link Command} shall be treated as consumed and in this case not be
   *          dispatched
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public CommandInterfaceCommandResult(final Command command, final Optional<T> value, final boolean commandConsumed) {
    super(command, value);
    this.commandConsumed = commandConsumed;
  }

  /**
   * Creates a new {@link State#FAILED} command result.
   *
   * @param command
   *          command that produced this result
   * @param cause
   *          cause of the command failure
   * @param commandConsumed
   *          flag that indicates that the executed {@link Command} shall be treated as consumed and in this case not be
   *          dispatched
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public CommandInterfaceCommandResult(final Command command, final Throwable cause, final boolean commandConsumed) {
    super(command, cause);
    this.commandConsumed = commandConsumed;
  }

  /**
   * Returns whether the {@link Command}, that's execution produced this {@link CommandInterfaceCommandResult}, shall be
   * treated as consumed and in this case not be dispatched anymore.
   *
   * @return {@code true} if the executed {@link Command} shall be treated as consumed, {@code false} otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public boolean isCommandConsumed() {
    return this.commandConsumed;
  }
}
