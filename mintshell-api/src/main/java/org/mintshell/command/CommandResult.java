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
package org.mintshell.command;

import java.util.Optional;

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
public abstract interface CommandResult<T> {

  /**
   * Returns the (optional) causing {@link Throwable} of a command execution.
   *
   * @return causing {@link Throwable} of a command execution, if the command execution failed, {@link Optional#empty()}
   *         otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract Optional<Throwable> getCause();

  /**
   * Returns the command, that produced this result.
   *
   * @return command, that produced this result
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract Command getCommand();

  /**
   * Returns the result's state.
   *
   * @return the result's state
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract State getState();

  /**
   * Returns the (optional) value of a command execution.
   *
   * @return value of a command execution, if the command execution succeeded, {@link Optional#empty()} otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract Optional<T> getValue();

  /**
   * Returns whether this result represents a failed {@link Command} execution.
   *
   * @return {@code true} if this result represents a failed {@link Command} execution, {@code false} otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default boolean isFailed() {
    return State.FAILED.equals(getState());
  }

  /**
   * Returns whether this result represents a succeeded {@link Command} execution.
   *
   * @return {@code true} if this result represents a succeeded {@link Command} execution, {@code false} otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public default boolean isSucceeded() {
    return State.SUCCEEDED.equals(this.getState());
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
