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
package org.mintshell.command;

import java.util.Optional;

import org.mintshell.assertion.Assert;

/**
 * A {@link CommandResult} is the result of executing a {@link Command}. It may contain a value as the originary result
 * of the invoked operation as well as a state and in dependence of this state maybe a {@link Throwable}.
 *
 * @param <T>
 *          type of the possibly contained value
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class CommandResult<T> {

  private final Command<?> command;
  private final State state;
  private final Optional<T> value;
  private final Optional<Throwable> cause;

  /**
   * Creates a new {@link State#SUCCEEDED} command result.
   *
   * @param command
   *          command that produced this result
   * @param value
   *          value of the command execution
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public CommandResult(final Command<?> command, final Optional<T> value) {
    this.command = Assert.ARG.isNotNull(command, "[command] must not be [null]");
    this.value = Assert.ARG.isNotNull(value, "[value] must not be [null]");
    this.state = State.SUCCEEDED;
    this.cause = Optional.empty();
  }

  /**
   * Creates a new {@link State#FAILED} command result.
   *
   * @param command
   *          command that produced this result
   * @param cause
   *          cause of the command failure
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public CommandResult(final Command<?> command, final Throwable cause) {
    this.command = Assert.ARG.isNotNull(command, "[command] must not be [null]");
    this.value = Optional.empty();
    this.state = State.FAILED;
    this.cause = Optional.of(Assert.ARG.isNotNull(cause, "[cause] must not be [null]"));
  }

  /**
   * Returns the (optional) causing {@link Throwable} of a command execution.
   *
   * @return causing {@link Throwable} of a command execution, if the command execution failed, {@link Optional#empty()}
   *         otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Optional<Throwable> getCause() {
    return this.cause;
  }

  /**
   * Returns the command, that produced this result.
   *
   * @return command, that produced this result
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Command<?> getCommand() {
    return this.command;
  }

  /**
   * Returns the result's state.
   *
   * @return the result's state
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public State getState() {
    return this.state;
  }

  /**
   * Returns the (optional) value of a command execution.
   *
   * @return value of a command execution, if the command execution succeeded, {@link Optional#empty()} otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Optional<T> getValue() {
    return this.value;
  }

  /**
   * Returns whether this result represents a failed {@link Command} execution.
   *
   * @return {@code true} if this result represents a failed {@link Command} execution, {@code false} otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public boolean isFailed() {
    return State.FAILED.equals(this.state);
  }

  /**
   * Returns whether this result represents a succeeded {@link Command} execution.
   *
   * @return {@code true} if this result represents a succeeded {@link Command} execution, {@code false} otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public boolean isSucceeded() {
    return State.SUCCEEDED.equals(this.state);
  }

  /**
   * Enumerates all {@link CommandResult} states.
   *
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public static enum State {
    SUCCEEDED, FAILED;
  }
}
