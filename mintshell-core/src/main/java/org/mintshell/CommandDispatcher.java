/*
 * Copyright © 2017 mintshell.org
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
package org.mintshell;

import java.util.Set;

import org.mintshell.command.Command;
import org.mintshell.command.CommandResult;

/**
 * The {@link CommandDispatcher} is responsible to manage {@link CommandTarget}s and dispatch given {@link Command}s to
 * the propriate {@link CommandTarget}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public abstract interface CommandDispatcher {

  /**
   * Adds {@link CommandTarget}s to take into account during dispatching.
   *
   * @param commandTargets
   *          {@link CommandTarget}s to add
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract void addCommandTargets(final CommandTarget... commandTargets);

  /**
   * Adds {@link CommandTarget}s to take into account during dispatching.
   *
   * @param commandTargets
   *          {@link CommandTarget}s to add
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract void addCommandTargets(final Set<CommandTarget> commandTargets);

  /**
   * Dispatches the given {@link Command} to a propriate {@link CommandTarget}.
   *
   * @param command
   *          {@link Command} to dispatch
   * @return result of dispatching and executing the given {@link Command}
   * @throws CommandDispatchException
   *           if dispatching failed
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract CommandResult<?> dispatch(final Command command) throws CommandDispatchException;
}
