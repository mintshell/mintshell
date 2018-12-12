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

import java.util.List;

import org.mintshell.interfaces.CommandInterface;
import org.mintshell.interpreter.CommandInterpreter;

/**
 * <p>
 * A {@link Command} is the intent to execute a functionality within the application. It is induced by a
 * {@link CommandInterface} in conjunctions with a {@link CommandInterpreter}. One or more {@link CommandParameter}s may
 * be provided together with the {@link Command}. A {@link Command} always produces a {@link CommandResult}.
 * </p>
 * <p>
 * <b>Note:</b> {@link Command}s are identified by their name attribute which means that two {@link Command}s are equal
 * when their names are equal, no matter of their other attributes.
 * </p>
 *
 *
 * @author Noqmar
 * @since 0.1.0
 * @see CommandResult
 */
public abstract interface Command {

  /**
   * Returns the name of the {@link Command}
   *
   * @return name of the {@link Command}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract String getName();

  /**
   * Returns the ordered {@link List} of {@link CommandParameter}s that are supported by the {@link Command}.
   *
   * @return {@link List} of supported {@link CommandParameter}s or an empty {@link List}, if the {@link Command} needs
   *         no {@link CommandParameter}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract List<? extends CommandParameter> getParameters();
}
