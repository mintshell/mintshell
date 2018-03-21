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
package org.mintshell.interpreter;

import java.util.ArrayList;
import java.util.List;

import org.mintshell.CommandInterpreteException;
import org.mintshell.CommandInterpreter;
import org.mintshell.command.Command;
import org.mintshell.command.CommandParameter;

/**
 * Simple implementation of a {@link CommandInterpreter} that splits a given command message on it's spaces using the
 * first token as command and all following as parameters.
 *
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class StringTokenCommandInterpreter implements CommandInterpreter {

  /**
   *
   * {@inheritDoc}
   * @see org.mintshell.CommandInterpreter#interprete(java.lang.String)
   */
  @Override
  public Command<?> interprete(final String commandMessage) throws CommandInterpreteException {
    if (commandMessage == null || commandMessage.trim().isEmpty()) {
      throw new CommandInterpreteException("The command message doesn't contain a command");
    }
    final String[] parts = commandMessage.trim().split(" ");
    final String name = parts[0].trim();

    final List<CommandParameter> parameters = new ArrayList<>();
    for (int i = 1; i < parts.length; i++) {
      parameters.add(new CommandParameter(i - 1, parts[i]));
    }
    return new Command<>(name, parameters);
  }
}
