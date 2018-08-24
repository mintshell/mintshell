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

import org.mintshell.annotation.Nullable;
import org.mintshell.command.Command;
import org.mintshell.command.CommandResult;
import org.mintshell.common.PromptProvider;
import org.mintshell.dispatcher.CommandDispatcher;
import org.mintshell.interpreter.CommandInterpreter;

/**
 * A {@link CommandInterface} is responsible to provide a technical endpoint, that is able to receive intents and to
 * transform them to command messages. After that, the {@link CommandInterface} let it's {@link CommandInterpreter}
 * parse the command message into a {@link Command} and provides that {@link Command} to it's {@link CommandDispatcher}.
 * The dispatching results in a {@link CommandResult}, that is then converted into an output and provided back to the
 * intent emitter.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public abstract interface CommandInterface extends PromptProvider {

  /**
   * Activates the {@link CommandInterface} with the given {@link CommandInterpreter} and {@link CommandDispatcher} to
   * be used internally.
   *
   * @param commandInterpreter
   *          interpreter to be used
   * @param commandDispatcher
   *          dispatcher to be used
   * @throws IllegalStateException
   *           if the {@link CommandInterface} is already activated
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract void activate(CommandInterpreter commandInterpreter, CommandDispatcher commandDispatcher) throws IllegalStateException;

  /**
   * Deactivates the {@link CommandInterface} meaning it looses it's {@link CommandInterpreter} and
   * {@link CommandDispatcher}. If the {@link CommandInterface} wasn't active, nothing happens.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract void deactivate();

  /**
   * Returns the internally used {@link CommandDispatcher}.
   *
   * @return internally used {@link CommandDispatcher} or {@code null}, if the {@link CommandInterface} isn't active
   *
   * @author Noqmar
   * @since 0.1.0
   * @see #activate(CommandInterpreter, CommandDispatcher)
   */
  public abstract @Nullable CommandDispatcher getCommandDispatcher();

  /**
   * Returns the internally used {@link CommandInterpreter}.
   *
   * @return internally used {@link CommandInterpreter} or {@code null}, if the {@link CommandInterface} isn't active
   *
   * @author Noqmar
   * @since 0.1.0
   * @see #activate(CommandInterpreter, CommandDispatcher)
   */
  public abstract @Nullable CommandInterpreter getCommandInterpreter();

  /**
   * Returns whether this {@link CommandInterface} was activated using
   * {@link #activate(CommandInterpreter, CommandDispatcher)}.
   *
   * @return {@code true}, if this {@link CommandInterface} was activated, {@code false} otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract boolean isActivated();
}
