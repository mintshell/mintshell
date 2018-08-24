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
package org.mintshell.dispatcher;

import java.util.Stack;

import org.mintshell.annotation.Nullable;
import org.mintshell.command.Command;
import org.mintshell.command.CommandResult;
import org.mintshell.target.CommandShell;
import org.mintshell.target.CommandTarget;

/**
 * The {@link CommandDispatcher} is responsible to manage a {@link Stack} of {@link CommandShell}s and to dispatch given
 * {@link Command}s to the propriate {@link CommandShell}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public abstract interface CommandDispatcher {

  /**
   * Dispatches the given {@link Command} by determining a matching {@link CommandTarget} from the current
   * {@link CommandShell} and delegating both {@link Command} and {@link CommandTarget} to the current
   * {@link CommandShell} for invocation. It also wraps the result into a {@link CommandResult}.
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

  /**
   * Returns the configured {@link CommandHelp} facility.
   *
   * @return {@link CommandHelp} facility
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract @Nullable CommandHelp getCommandHelp();
}
