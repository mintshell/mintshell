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

import static java.util.Arrays.deepEquals;
import static java.util.Arrays.deepHashCode;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.mintshell.CommandInterface;
import org.mintshell.annotation.Nullable;
import org.mintshell.assertion.Assert;

/**
 * <p>
 * A {@link Command} is the intent to invoke an operation within the application, in Java usually a method, which is
 * induced by a {@link CommandInterface}. One or more {@link CommandParameter}s may be provided together with the
 * {@link Command}. A {@link Command} always produces a {@link CommandResult}.
 * </p>
 * <p>
 * <b>Note:</b> {@link Command}s are identified by their name attribute which means that two {@link Command}s are equal
 * when their names are equal, no matter of their other attributes.
 * </p>
 *
 * @param <P>
 *          type of the command parameters
 *
 * @author Noqmar
 * @since 0.1.0
 * @see CommandResult
 */
public class Command<P extends CommandParameter> {

  public static final String DEFAULT_HELP_PARAMETER_NAME = "help";

  private final String description;
  private final String name;
  private final List<P> parameters;
  private final Optional<String> helpParameterName;

  /**
   * Creates a new {@link Command}.
   *
   * @param name
   *          name
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Command(final String name) {
    this(name, null, DEFAULT_HELP_PARAMETER_NAME, Collections.emptyList());
  }

  /**
   * Creates a new {@link Command}.
   *
   * @param name
   *          name
   * @param parameters
   *          {@link List} of {@link CommandParameter}s
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Command(final String name, final List<P> parameters) {
    this(name, null, DEFAULT_HELP_PARAMETER_NAME, parameters);
  }

  /**
   * Creates a new {@link Command}.
   *
   * @param name
   *          name
   * @param description
   *          (optional) description text
   * @param helpParameterName
   *          (optional) name of the help parameter
   * @param parameters
   *          {@link List} of {@link CommandParameter}s
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Command(final String name, final @Nullable String description, final @Nullable String helpParameterName, final List<P> parameters) {
    this.name = Assert.ARG.isNotNull(name, "[name] must not be [null]");
    this.description = description;
    this.helpParameterName = Optional.ofNullable(helpParameterName);
    this.parameters = new ArrayList<>(Assert.ARG.isNotNull(parameters, "[parameters] must not be [null]"));
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (this.getClass() != other.getClass()) {
      return false;
    }
    final Command<?> that = (Command<?>) other;
    return deepEquals(new Object[] { this.name }, new Object[] { that.name });
  }

  /**
   * Returns an optional description of what the {@link Command} does.
   *
   * @return optional description of the {@link Command}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public @Nullable String getDescription() {
    return this.description;
  }

  public Optional<String> getHelpParameterName() {
    return this.helpParameterName;
  }

  /**
   * Returns the name of the {@link Command}
   *
   * @return name of the {@link Command}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the number of parameters this command can take.
   *
   * @return number of parameters
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public int getParameterCount() {
    return this.getParameters().size();
  }

  /**
   * Returns the ordered {@link List} of {@link CommandParameter}s that are supported by the {@link Command}.
   *
   * @return {@link List} of supported {@link CommandParameter}s or an empty {@link List}, if the {@link Command} needs
   *         no {@link CommandParameter}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public List<P> getParameters() {
    return unmodifiableList(this.parameters);
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return deepHashCode(new Object[] { this.name });
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return this.name;
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

  /**
   * Builder to convenient create {@link Command}s.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public static final class CommandBuilder {

    private String description;
    private final String name;
    private String helpParameterName;
    private final List<CommandParameter> parameters;

    private CommandBuilder(final String name) {
      this.name = Assert.ARG.isNotNull(name, "[name] must not be [null]");
      this.parameters = new ArrayList<>();
    }

    /**
     * Builds the {@link Command} from the current builder state.
     *
     * @return {@link Command} instance
     *
     * @author Noqmar
     * @since 0.1.0
     */
    public Command<CommandParameter> build() {
      return new Command<>(this.name, this.description, this.helpParameterName, this.parameters);
    }

    /**
     * Sets a description to the current builder state.
     *
     * @param description
     *          the description to be set
     * @return {@link CommandBuilder} instance
     *
     * @author Noqmar
     * @since 0.1.0
     */
    public CommandBuilder withDescription(final String description) {
      this.description = description;
      return this;
    }

    /**
     * Sets a help parameter name to the current builder state.
     *
     * @param helpParameterName
     *          the help parameter name to be set
     * @return {@link CommandBuilder} instance
     *
     * @author Noqmar
     * @since 0.1.0
     */
    public CommandBuilder withHelpParameterNameDescription(final @Nullable String helpParameterName) {
      this.helpParameterName = helpParameterName;
      return this;
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
  }
}
