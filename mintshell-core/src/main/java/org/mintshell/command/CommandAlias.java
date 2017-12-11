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

import org.mintshell.annotation.Nullable;
import org.mintshell.assertion.Assert;

/**
 * A {@link CommandAlias} is a delegation to a (real) command with it's own name and description.
 *
 * @param <P>
 *          type of the command parameters
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class CommandAlias<P extends CommandParameter> extends Command<P> {

  private final Command<P> command;

  /**
   * Creates a new {@link CommandAlias} for the given {@link Command}. Note that the given {@link Command} may be and
   * {@link CommandAlias} again.
   *
   * @param command
   *          command to be aliased
   * @param name
   *          name of the alias
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public CommandAlias(final Command<P> command, final String name) {
    this(command, name, null);
  }

  /**
   * Creates a new {@link CommandAlias} for the given {@link Command}. Note that the given {@link Command} may be and
   * {@link CommandAlias} again.
   *
   * @param command
   *          command to be aliased
   * @param name
   *          name of the alias
   * @param descripion
   *          (optional) description of the alias
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public CommandAlias(final Command<P> command, final String name, final @Nullable String descripion) {
    super(name, descripion, Assert.ARG.isNotNull(command, "[command] must not be [null]").getParameters());
    this.command = command;
  }

  /**
   * Returns the {@link Command} of which this is an alias for.
   *
   * @return aliased {@link Command}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Command<P> getCommand() {
    return this.command;
  }
}
