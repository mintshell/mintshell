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

import java.util.Optional;

import org.mintshell.command.Command;
import org.mintshell.target.CommandTarget;
import org.mintshell.target.CommandTargetParameter;

/**
 * Help facility responsible to display information about available {@link CommandTarget}s and their
 * {@link CommandTargetParameter}s.
 *
 * @author Noqmar
 * @since 0.2.0
 * @see CommandDispatcher
 */
public abstract interface CommandHelp {

  /**
   * Returns the detail help text for the given {@link CommandTarget} (usually with information about
   * {@link CommandTargetParameter}s).
   *
   * @param commandTarget
   *          {@link CommandTarget} to get detail text for
   * @return detail help text
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract String getCommandDetailText(final CommandTarget commandTarget);

  /**
   * Returns the help text, if the given command name doesn't match any known {@link Command}.
   *
   * @param commandName
   *          name of the unknown {@link Command}
   * @return help text
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract String getCommandNotFoundText(final String commandName);

  /**
   * Returns an optional footer text to be displayed after the command overview.
   *
   * @return optional footer text
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract Optional<String> getCommandOverviewFooterText();

  /**
   * Returns the overview help text for the given {@link Command}.
   *
   * @param commandTarget
   *          command target to get overview text for
   * @return overview help text
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract String getCommandOverviewText(final CommandTarget commandTarget);

  /**
   * Returns the name of the help command.
   *
   * @return name of the help command
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract String getHelpCommandName();

  /**
   * Returns the name of the help command parameter.
   *
   * @return name of the help command parameter
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract Optional<String> getHelpCommandParamterName();
}
