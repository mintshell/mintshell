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

import org.mintshell.annotation.Nullable;
import org.mintshell.target.CommandShell;
import org.mintshell.target.CommandTarget;

/**
 * Default implementation of a {@link CommandDispatcher}.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public class DefaultCommandDispatcher extends BaseCommandDispatcher<CommandTarget> {

  /**
   * Creates a new instance with an initial {@link CommandShell} and with {@link DefaultCommandHelp}.
   *
   * @param initialShell
   *          inital {@link CommandShell}
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public DefaultCommandDispatcher(final CommandShell initialShell) {
    super(initialShell);
  }

  /**
   * Creates a new instance with an initial {@link CommandShell}.
   *
   * @param initialShell
   *          inital {@link CommandShell}
   * @param commandHelp
   *          (optional) command help facility
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public DefaultCommandDispatcher(final CommandShell initialShell, @Nullable final CommandHelp commandHelp) {
    super(initialShell, commandHelp);
  }
}
