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
package org.mintshell.dispatcher;

import java.util.Optional;

import org.mintshell.annotation.Nullable;
import org.mintshell.assertion.Assert;
import org.mintshell.target.CommandTarget;
import org.mintshell.target.CommandTargetParameter;

/**
 * Default implementation of a {@link CommandHelp} facility.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public class DefaultCommandHelp implements CommandHelp {

  public static final String DEFAULT_HELP_COMMAND_NAME = "help";
  public static final String DEFAULT_HELP_COMMAND_PARAMETER_NAME = "help";
  public static final String DEFAULT_COMMAND_OVERVIEW_PATTERN = "%s\t\t%s";
  public static final String DEFAULT_COMMAND_OVERVIEW_FOOTER_PATTERN = "\n\rFor detailed command description use: %s <command>";
  public static final String DEFAULT_COMMAND_DETAIL_PATTERN = "%s\n\n\r%s\n\r%s";
  public static final String DEFAULT_COMMAND_USAGE_PATTERN = "usage: %s%s";
  public static final String DEFAULT_COMMAND_NOT_FOUND_PATTERN = "%s: %s: command not found";

  private final String helpCommandName;
  private final Optional<String> helpCommandParameterName;
  private final String commandOverviewPattern;
  private final String commandOverviewFooterPattern;
  private final String commandDetailPattern;
  private final String commandUsagePattern;
  private final String commandNotFoundPattern;

  /**
   * Creates a new instance with all default patterns.
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public DefaultCommandHelp() {
    this(DEFAULT_HELP_COMMAND_NAME, DEFAULT_HELP_COMMAND_PARAMETER_NAME, DEFAULT_COMMAND_OVERVIEW_PATTERN, DEFAULT_COMMAND_OVERVIEW_FOOTER_PATTERN,
        DEFAULT_COMMAND_DETAIL_PATTERN, DEFAULT_COMMAND_USAGE_PATTERN, DEFAULT_COMMAND_NOT_FOUND_PATTERN);
  }

  /**
   * Creates a new instance.
   *
   * @param helpCommandName
   *          name of the help command
   * @param helpCommandParameterName
   *          (optional) name of a help parameter
   * @param commandOverviewPattern
   *          pattern for command overview, must contain placeholder for target command name and command description
   * @param commandOverviewFooterPattern
   *          pattern for command overview footer, must contain placeholder for help command name
   * @param commandDetailPattern
   *          pattern for command detail, must contain placeholder for command overview, command usage and command
   *          parameters
   * @param commandUsagePattern
   *          pattern for command usage, must contain placeholder for target command name and command parameters
   * @param commandNotFoundPattern
   *          pattern for command not found message, must contain placeholder for help command name and target command
   *          name
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public DefaultCommandHelp(final String helpCommandName, final @Nullable String helpCommandParameterName, final String commandOverviewPattern,
      final String commandOverviewFooterPattern, final String commandDetailPattern, final String commandUsagePattern, final String commandNotFoundPattern) {
    this.helpCommandName = Assert.ARG.isNotNull(helpCommandName, "[helpCommandName] must not be [null]");
    this.helpCommandParameterName = Optional.ofNullable(helpCommandParameterName);
    this.commandOverviewPattern = Assert.ARG.isNotNull(commandOverviewPattern, "[commandOverviewPattern] must not be [null]");
    this.commandOverviewFooterPattern = Assert.ARG.isNotNull(commandOverviewFooterPattern, "[commandOverviewFooterPattern] must not be [null]");
    this.commandDetailPattern = Assert.ARG.isNotNull(commandDetailPattern, "[commandDetailPattern] must not be [null]");
    this.commandUsagePattern = Assert.ARG.isNotNull(commandUsagePattern, "[commandUsagePattern] must not be [null]");
    this.commandNotFoundPattern = Assert.ARG.isNotNull(commandNotFoundPattern, "[commandNotFoundPattern] must not be [null]");
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.dispatcher.CommandHelp#getCommandDetailText(org.mintshell.target.CommandTarget)
   */
  @Override
  public String getCommandDetailText(final CommandTarget commandExecution) {
    final StringBuilder builder = new StringBuilder();
    if (this.hasAtLeastOneParameterADescription(commandExecution)) {
      commandExecution.getParameters().stream() //
          .forEach(parameter -> builder.append("\n\r").append(this.getCommandParameterText(parameter)));
    }
    return String.format(this.commandDetailPattern, this.getCommandOverviewText(commandExecution), this.getUsageText(commandExecution), builder.toString());
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.dispatcher.CommandHelp#getCommandNotFoundText(java.lang.String)
   */
  @Override
  public String getCommandNotFoundText(final String commandName) {
    return String.format(this.commandNotFoundPattern, this.getHelpCommandName(), commandName);
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.dispatcher.CommandHelp#getCommandOverviewFooterText()
   */
  @Override
  public Optional<String> getCommandOverviewFooterText() {
    return Optional.of(String.format(this.commandOverviewFooterPattern, this.helpCommandName));
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.dispatcher.CommandHelp#getCommandOverviewText(org.mintshell.target.CommandTarget)
   */
  @Override
  public String getCommandOverviewText(final CommandTarget command) {
    return String.format(this.commandOverviewPattern, command.getName(), command.getDescription().orElse("no description available"));
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.dispatcher.CommandHelp#getHelpCommandName()
   */
  @Override
  public String getHelpCommandName() {
    return this.helpCommandName;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.dispatcher.CommandHelp#getHelpCommandParamterName()
   */
  @Override
  public Optional<String> getHelpCommandParamterName() {
    return this.helpCommandParameterName;
  }

  /**
   * Returns formatted detail text for a {@link CommandTargetParameter}.
   *
   * @param commandTargetParameter
   *          {@link CommandTargetParameter} to get text for
   * @return formatted detail text
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected String getCommandParameterText(final CommandTargetParameter commandTargetParameter) {
    final StringBuilder builder = new StringBuilder();
    if (!commandTargetParameter.getShortName().isPresent() && !commandTargetParameter.getName().isPresent()) {
      builder.append(" arg").append(commandTargetParameter.getIndex());
    }
    else {
      if (commandTargetParameter.getShortName().isPresent()) {
        builder.append(" -").append(commandTargetParameter.getShortName().get());
        if (commandTargetParameter.getName().isPresent()) {
          builder.append(",");
        }
      }
      if (commandTargetParameter.getName().isPresent()) {
        builder.append(" --").append(commandTargetParameter.getName().get());
      }
    }
    builder.append("\t\t").append(commandTargetParameter.getDescription().orElse("no description available"));
    return builder.toString();
  }

  /**
   * Returns usage information text for the given {@link CommandTarget}.
   *
   * @param commandExecution
   *          command execution to generate the usage information text
   * @return usage information text
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected String getUsageText(final CommandTarget commandExecution) {
    final StringBuilder builder = new StringBuilder();
    commandExecution.getParameters().stream() //
        .map(parameter -> {
          if (parameter.getName().isPresent()) {
            return String.format(parameter.isRequired() ? "<%s>" : "[%s]", parameter.getName().get());
          }
          else if (parameter.getShortName().isPresent()) {
            return String.format(parameter.isRequired() ? "<%s>" : "[%s]", parameter.getShortName().get());
          }
          else {
            return String.format(parameter.isRequired() ? "<arg%s>" : "[arg%s]", parameter.getIndex());
          }
        }) //
        .forEach(text -> builder.append(" ").append(text));
    return String.format(this.commandUsagePattern, commandExecution.getName(), builder.toString());
  }

  private boolean hasAtLeastOneParameterADescription(final CommandTarget commandExecution) {
    return commandExecution.getParameters().stream().filter(param -> param.getDescription().isPresent() && !param.getDescription().get().trim().isEmpty())
        .findAny().isPresent();
  }
}
