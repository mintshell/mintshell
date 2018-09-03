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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mintshell.assertion.Assert;

/**
 * Builder to convenient create {@link Command}s.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public final class CommandBuilder {

  private final String name;
  private final List<CommandParameter> parameters;

  private CommandBuilder(final String name) {
    this.name = Assert.ARG.isNotNull(name, "[name] must not be [null]");
    this.parameters = new ArrayList<>();
  }

  /**
   * Builds a {@link Command} from the current builder state.
   *
   * @return {@link Command} instance
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Command build() {
    return new DefaultCommand(this.name, this.parameters);
  }

  /**
   * Builds a {@link PipedCommand} from the current builder state.
   *
   * @return {@link PipedCommand} instance
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public Command build(final Command target) {
    return new DefaultPipedCommand(this.name, this.parameters, target);
  }

  /**
   * Adds a singe {@link CommandParameter} to the current builder state.
   *
   * @param commandParameter
   *          the {@link CommandParameter} to be added
   * @return {@link CommandBuilder} instance
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public CommandBuilder withParameter(final CommandParameter commandParameter) {
    this.parameters.add(Assert.ARG.isNotNull(commandParameter, "[commandParameter] must not be [null]"));
    return this;
  }

  /**
   * Adds a {@link Collection} of {@link CommandParameter}s to the current builder state.
   *
   * @param commandParameters
   *          {@link Collection} of {@link CommandParameter}s to be added
   * @return {@link CommandBuilder} instance
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public CommandBuilder withParameters(final Collection<CommandParameter> commandParameters) {
    this.parameters.addAll(Assert.ARG.isNotNull(commandParameters, "[commandParameters] must not be [null]"));
    return this;
  }

  /**
   * Adds one or more {@link CommandParameter}s to the current builder state.
   *
   * @param commandParameters
   *          variable amount of {@link CommandParameter}s to be added
   * @return {@link CommandBuilder} instance
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public CommandBuilder withParameters(final CommandParameter... commandParameters) {
    Assert.ARG.isNotNull(commandParameters, "[commandParameters] must not be [null]");
    for (final CommandParameter commandParameter : commandParameters) {
      this.withParameter(commandParameter);
    }
    return this;
  }

  /**
   * Returns a {@link CommandBuilder} which provides a convenient way to create new {@link Command} instances.
   *
   * @param name
   *          name of the {@link Command} to build
   * @return {@link CommandBuilder} instance
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public static CommandBuilder create(final String name) {
    return new CommandBuilder(name);
  }
}