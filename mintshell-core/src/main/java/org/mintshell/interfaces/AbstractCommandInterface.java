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
package org.mintshell.interfaces;

import static java.lang.String.format;

import java.util.Optional;

import org.mintshell.CommandDispatchException;
import org.mintshell.CommandDispatcher;
import org.mintshell.CommandInterface;
import org.mintshell.CommandInterpreteException;
import org.mintshell.CommandInterpreter;
import org.mintshell.annotation.Nullable;
import org.mintshell.assertion.Assert;
import org.mintshell.command.Command;
import org.mintshell.command.CommandResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base implementation of a {@link CommandInterface} managing {@link CommandInterpreter} and {@link CommandDispatcher}s.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public abstract class AbstractCommandInterface implements CommandInterface {

  private final Logger LOG = LoggerFactory.getLogger(AbstractCommandInterface.class);

  private CommandInterpreter commandInterpreter;
  private CommandDispatcher commandDispatcher;

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.CommandInterface#activate(org.mintshell.CommandInterpreter, org.mintshell.CommandDispatcher)
   */
  @Override
  public void activate(final CommandInterpreter commandInterpreter, final CommandDispatcher commandDispatcher) throws IllegalStateException {
    if (this.isActivated()) {
      throw new IllegalStateException(String.format("[%s] already activated", this.getClass().getSimpleName()));
    }
    this.commandInterpreter = Assert.ARG.isNotNull(commandInterpreter, "[commandInterpreter] must not be [null]");
    this.commandDispatcher = Assert.ARG.isNotNull(commandDispatcher, "[commandDispatcher] must not be [null]");
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.CommandInterface#deactivate()
   */
  @Override
  public void deactivate() {
    this.commandInterpreter = null;
    this.commandDispatcher = null;
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.CommandInterface#isActivated()
   */
  @Override
  public boolean isActivated() {
    return this.commandInterpreter != null && this.commandDispatcher != null;
  }

  /**
   * Returns the managed {@link CommandDispatcher}.
   *
   * @return managed {@link CommandDispatcher}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected CommandDispatcher getCommandDispatcher() {
    return this.commandDispatcher;
  }

  /**
   * Returns the managed {@link CommandInterpreter}
   *
   * @return managed {@link CommandInterpreter}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected CommandInterpreter getCommandInterpreter() {
    return this.commandInterpreter;
  }

  /**
   * Performs a command in the way that the given command message is interpreted by the managed
   * {@link CommandInterpreter} and then passed to the managed {@link CommandDispatcher}. The value (or cause) from the
   * {@link CommandResult} is returned in it's {@link String} representation. If an exception occurs from
   * {@link CommandInterpreter} or {@link CommandDispatcher} it is logged and also converted into a {@link String}
   * representation that is returned.
   *
   * @param commandMessage
   *          command message of the command to perform
   * @return {@link String} representation of the {@link CommandResult}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected synchronized String performCommand(final String commandMessage) {
    CommandResult<?> result = null;
    try {
      final Command<?> command = this.preCommand(this.commandInterpreter.interprete(commandMessage));
      result = Assert.ARG.isNotNull(this.commandDispatcher.dispatch(command),
          format("Performing command [%s] doesn't lead to a valid command result", commandMessage));

      switch (result.getState()) {
        case SUCCEEDED:
          final Optional<?> resultValue = result.getValue();
          return resultValue.isPresent() ? resultValue.get().toString() : "";
        case FAILED:
          final Optional<Throwable> resultCause = result.getCause();
          return resultCause.isPresent() ? resultCause.get().getMessage() : "Failed for unknown reason";
      }
      return "";
    } catch (final CommandInterpreteException e) {
      this.LOG.warn("Failed to interprete command [{}]", commandMessage, e);
      return e.getMessage();
    } catch (final CommandDispatchException e) {
      this.LOG.warn("Failed to dispatch command [{}]", commandMessage, e);
      return e.getMessage();
    } catch (final RuntimeException e) {
      this.LOG.error("Failed to perform command [{}]", commandMessage, e);
      return format("%s: command failure: %s", commandMessage, e.getMessage());
    } finally {
      this.postCommand(result);
    }
  }

  /**
   * <p>
   * Handles the given {@link Command} <b>after</b> it got passed to the {@link CommandDispatcher} or any
   * {@link Exception} occured.
   * </p>
   * <p>
   * This method is intended to be overwritten by subclasses, if they need to get notified about certain
   * {@link CommandResult}s to treat them in a special way.
   * </p>
   *
   * @param result
   *          result of {@link Command} execution or {@code null} if the execution failed and did not produce a
   *          {@link CommandResult}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected void postCommand(final @Nullable CommandResult<?> result) {
    // does nothing here
  }

  /**
   * <p>
   * Handles the given {@link Command} <b>before</b> it gets passed to the {@link CommandDispatcher}. <b>Note</b>: If an
   * exception occurs during {@link CommandInterpreter#interprete(String)} this method is <b>not</b> invoked.
   * </p>
   * <p>
   * This method is intended to be overwritten by subclasses, if they need to get notified about certain {@link Command}
   * to treat them in a special way.
   * </p>
   *
   * @param command
   *          command to be handled
   * @return the handled command, maybe the given command itself, if no (manipulating) treatment is necessary
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected Command<?> preCommand(final Command<?> command) {
    return command;
  }
}
