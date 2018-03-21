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

import org.mintshell.assertion.Assert;
import org.mintshell.command.Command;
import org.mintshell.command.CommandHelp;
import org.mintshell.command.CommandParameter;

/**
 * Simple implementation of a {@link CommandHelp} facility.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class SimpleCommandHelp implements CommandHelp {

  public static final String DEFAULT_HELP_COMMAND_NAME = "help";
  public static final String DEFAULT_COMMAND_OVERVIEW_PATTERN = "%s\t\t%s";
  public static final String DEFAULT_COMMAND_OVERVIEW_FOOTER_PATTERN = "\n\rFor detailed command description use: %s <command>";
  public static final String DEFAULT_COMMAND_DETAIL_PATTERN = "%s\n\n\r%s\n\r%s";
  public static final String DEFAULT_COMMAND_USAGE_PATTERN = "usage: %s%s";
  public static final String DEFAULT_COMMAND_NOT_FOUND_PATTERN = "%s: %s: command not found";

  private final String helpCommandName;
  private final String commandOverviewPattern;
  private final String commandOverviewFooterPattern;
  private final String commandDetailPattern;
  private final String commandUsagePattern;
  private final String commandNotFoundPattern;

  /**
   * Creates a new instance with all default patterns.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public SimpleCommandHelp() {
    this(DEFAULT_HELP_COMMAND_NAME, DEFAULT_COMMAND_OVERVIEW_PATTERN, DEFAULT_COMMAND_OVERVIEW_FOOTER_PATTERN, DEFAULT_COMMAND_DETAIL_PATTERN,
        DEFAULT_COMMAND_USAGE_PATTERN, DEFAULT_COMMAND_NOT_FOUND_PATTERN);
  }

  /**
   * Creates a new instance.
   *
   * @param helpCommandName
   *          name of the help command
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
   * @since 0.1.0
   */
  public SimpleCommandHelp(final String helpCommandName, final String commandOverviewPattern, final String commandOverviewFooterPattern,
      final String commandDetailPattern, final String commandUsagePattern, final String commandNotFoundPattern) {
    this.helpCommandName = Assert.ARG.isNotNull(helpCommandName, "[helpCommandName] must not be [null]");
    this.commandOverviewPattern = Assert.ARG.isNotNull(commandOverviewPattern, "[commandOverviewPattern] must not be [null]");
    this.commandOverviewFooterPattern = Assert.ARG.isNotNull(commandOverviewFooterPattern, "[commandOverviewFooterPattern] must not be [null]");
    this.commandDetailPattern = Assert.ARG.isNotNull(commandDetailPattern, "[commandDetailPattern] must not be [null]");
    this.commandUsagePattern = Assert.ARG.isNotNull(commandUsagePattern, "[commandUsagePattern] must not be [null]");
    this.commandNotFoundPattern = Assert.ARG.isNotNull(commandNotFoundPattern, "[commandNotFoundPattern] must not be [null]");
  }

  /**
   *
   * {@inheritDoc}
   * @see org.mintshell.command.CommandHelp#getCommandDetailText(org.mintshell.command.Command)
   */
  @Override
  public String getCommandDetailText(final Command<?> command) {
    final StringBuilder builder = new StringBuilder();
    if (this.hasAtLeastOneParameterADescription(command)) {
      command.getParameters().stream() //
          .forEach(parameter -> builder.append("\n\r").append(this.getCommandParameterText(parameter)));
    }
    return String.format(this.commandDetailPattern, this.getCommandOverviewText(command), this.getUsageText(command), builder.toString());
  }

  /**
   *
   * {@inheritDoc}
   * @see org.mintshell.command.CommandHelp#getCommandNotFoundText(java.lang.String)
   */
  @Override
  public String getCommandNotFoundText(final String commandName) {
    return String.format(this.commandNotFoundPattern, this.getHelpCommandName(), commandName);
  }

  /**
   *
   * {@inheritDoc}
   * @see org.mintshell.command.CommandHelp#getCommandOverviewFooterText()
   */
  @Override
  public Optional<String> getCommandOverviewFooterText() {
    return Optional.of(String.format(this.commandOverviewFooterPattern, this.helpCommandName));
  }

  /**
   *
   * {@inheritDoc}
   * @see org.mintshell.command.CommandHelp#getCommandOverviewText(org.mintshell.command.Command)
   */
  @Override
  public String getCommandOverviewText(final Command<?> command) {
    return String.format(this.commandOverviewPattern, command.getName(), command.getDescription());
  }

  /**
   *
   * {@inheritDoc}
   * @see org.mintshell.command.CommandHelp#getHelpCommandName()
   */
  @Override
  public String getHelpCommandName() {
    return this.helpCommandName;
  }

  /**
   * Returns formatted detail text for a {@link CommandParameter}.
   *
   * @param commandParameter
   *          {@link CommandParameter} to get text for
   * @return formatted detail text
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected String getCommandParameterText(final CommandParameter commandParameter) {
    final StringBuilder builder = new StringBuilder();
    if (!commandParameter.getShortName().isPresent() && !commandParameter.getName().isPresent()) {
      builder.append(" arg").append(commandParameter.getIndex());
    }
    else {
      if (commandParameter.getShortName().isPresent()) {
        builder.append(" -").append(commandParameter.getShortName().get());
        if (commandParameter.getName().isPresent()) {
          builder.append(",");
        }
      }
      if (commandParameter.getName().isPresent()) {
        builder.append(" --").append(commandParameter.getName().get());
      }
    }
    builder.append("\t\t").append(commandParameter.getDescription() != null ? commandParameter.getDescription() : "no description available");
    return builder.toString();
  }

  /**
   * Returns usage information text for the given {@link Command}.
   *
   * @param command
   *          command to generate the usage information text
   * @return usage information text
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected String getUsageText(final Command<?> command) {
    final StringBuilder builder = new StringBuilder();
    command.getParameters().stream() //
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
    return String.format(this.commandUsagePattern, command.getName(), builder.toString());
  }

  private boolean hasAtLeastOneParameterADescription(final Command<?> command) {
    return command.getParameters().stream().filter(param -> param.getDescription() != null && !param.getDescription().trim().isEmpty()).findAny().isPresent();
  }
}
