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

import static java.lang.String.format;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Optional;
import java.util.Stack;

import org.mintshell.annotation.Nullable;
import org.mintshell.assertion.Assert;
import org.mintshell.command.Command;
import org.mintshell.command.CommandResult;
import org.mintshell.command.DefaultCommandResult;
import org.mintshell.target.CommandInvocationException;
import org.mintshell.target.CommandShell;
import org.mintshell.target.CommandShellExitException;
import org.mintshell.target.CommandTarget;
import org.mintshell.target.CommandTargetAlias;
import org.mintshell.target.CommandTargetException;

/**
 * Base implementation of a {@link CommandDispatcher} managing a {@link Stack} {@link CommandShell}s.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public abstract class BaseCommandDispatcher<C extends CommandTarget> implements CommandDispatcher {

  private final CommandHelp commandHelp;
  private final Stack<CommandShell> commandShells;
  private Optional<String> promptPathSeparator;

  /**
   * Creates a new instance with an initial {@link CommandShell} and with {@link DefaultCommandHelp} and without
   * {@link #getPromptPathSeparator()}.
   *
   * @param initialShell
   *          inital {@link CommandShell}
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected BaseCommandDispatcher(final CommandShell initialShell) {
    this(initialShell, new DefaultCommandHelp(), null);
  }

  /**
   * Creates a new instance with an initial {@link CommandShell}.
   *
   * @param initialShell
   *          inital {@link CommandShell}
   * @param commandHelp
   *          (optional) command help facility
   * @param promptPathSeparator
   *          sepearator to divide prompt of different {@link CommandShell}s or {@code null} if prompt pathing should be
   *          disabled
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected BaseCommandDispatcher(final CommandShell initialShell, final @Nullable CommandHelp commandHelp, final @Nullable String promptPathSeparator) {
    Assert.ARG.isNotNull(initialShell, "[initialShell] must not be [null]");
    this.commandShells = new Stack<>();
    this.commandShells.push(initialShell);
    this.commandHelp = commandHelp;
    this.promptPathSeparator = Optional.ofNullable(promptPathSeparator);
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.dispatcher.CommandDispatcher#dispatch(org.mintshell.command.Command)
   */
  @Override
  public CommandResult<?> dispatch(final Command command) throws CommandDispatchException, CommandShellExitException {

    // handle help
    if (this.getCommandHelp() != null) {
      if (command.getName().equals(this.getCommandHelp().getHelpCommandName())) {
        return this.handleHelpCommand(command, this.getCommandHelp());
      }
      else if (this.getCommandHelp().getHelpCommandParamterName().isPresent() && command.getParameters().size() == 1
          && command.getParameters().get(0).getName().isPresent()
          && command.getParameters().get(0).getName().get().equals(this.getCommandHelp().getHelpCommandParamterName().orElse(null))) {
        return new DefaultCommandResult<>(command, Optional.of(this.createDetailCommandHelpText(command.getName())));
      }
    }
    // dispatch command
    try {
      final CommandShell currentCommandShell = this.commandShells.peek();
      final CommandTarget commandTarget = this.resolveAliases(currentCommandShell.getTargets().stream() //
          .filter(target -> target.getName().equals(command.getName())) //
          .findFirst() //
          .orElseThrow(() -> new CommandDispatchException(format("%s: command not found", command))));

      final Object result = currentCommandShell.invoke(command, commandTarget);
      if (result instanceof CommandShell) {
        this.commandShells.push((CommandShell) result);
      }
      return new DefaultCommandResult<>(command, Optional.ofNullable(result));
    } catch (final CommandDispatchException e) {
      throw e;
    } catch (final CommandInvocationException e) {
      throw new CommandDispatchException(format("%s: command invocation failed", command), e);
    } catch (final CommandTargetException e) {
      if (e.getCause() instanceof CommandShellExitException) {
        if (this.commandShells.size() > 1) {
          this.commandShells.pop();
        }
        else {
          throw (CommandShellExitException) e.getCause();
        }
      }
      return new DefaultCommandResult<>(command, e.getCause());
    } catch (final EmptyStackException e) {
      throw new CommandDispatchException(format("%s: missing command shell instance", command), e);
    } catch (final RuntimeException e) {
      throw new CommandDispatchException(format("%s: failed to dispatch command: %", command, e.getMessage()), e);
    }
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.dispatcher.CommandDispatcher#getCommandHelp()
   */
  @Override
  public @Nullable CommandHelp getCommandHelp() {
    return this.commandHelp;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.common.PromptProvider#getPrompt()
   */
  @Override
  public String getPrompt() {
    if (this.promptPathSeparator.isPresent()) {
      return this.createPromptPath(this.promptPathSeparator.get());
    }
    else {
      return this.commandShells.peek().getPrompt();
    }
  }

  /**
   * Returns the (optional) prompt path seperator.
   *
   * @return (optional) prompt path separator or {@link Optional#empty()} if prompt pathing is disabled
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public Optional<String> getPromptPathSeparator() {
    return this.promptPathSeparator;
  }

  /**
   * Sets a new prompt path separator.
   *
   * @param promptPathSeparator
   *          prompt path seperator or {@code null}, if prompt pathing should be diabled
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public void setPromptPathSeparator(final @Nullable String promptPathSeparator) {
    this.promptPathSeparator = Optional.ofNullable(promptPathSeparator);
  }

  /**
   * Returns detail help text for a command with the given command name.
   *
   * @param commandName
   *          name of the command to create detail text for
   * @return detail help text
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected String createDetailCommandHelpText(final String commandName) {
    final CommandShell currentCommandShell = this.commandShells.peek();
    final StringBuilder builder = new StringBuilder();
    final Optional<CommandTarget> searchedCommand = currentCommandShell.getTargets().stream() //
        .filter(cmd -> commandName.equals(cmd.getName())) //
        .findAny();
    if (searchedCommand.isPresent()) {
      builder.append(this.commandHelp.getCommandDetailText(searchedCommand.get()));
    }
    else {
      builder.append(this.commandHelp.getCommandNotFoundText(commandName));
    }
    return builder.toString();
  }

  /**
   * Iterates over the {@link Stack} of {@link CommandShell}s and builds a path with their prompts using the given
   * separator.
   *
   * @param separator
   *          path separator
   * @return prompt path from the {@link Stack} of {@link CommandShell}s
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected String createPromptPath(final String separator) {
    final StringBuilder builder = new StringBuilder();
    final Iterator<CommandShell> it = this.commandShells.iterator();
    while (it.hasNext()) {
      builder.append(it.next().getPrompt());
      if (it.hasNext()) {
        builder.append(separator);
      }
    }
    return builder.toString();
  }

  /**
   * Handles execution of the help command.
   *
   * @param command
   *          command to be handled
   * @param commandHelp
   *          {@link CommandHelp} support
   * @return result of the handling
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected CommandResult<?> handleHelpCommand(final Command command, final CommandHelp commandHelp) {
    final CommandShell currentCommandShell = this.commandShells.peek();
    final StringBuilder builder = new StringBuilder();
    if (command.getParameters().size() == 0 || !command.getParameters().get(0).getValue().isPresent()
        || command.getParameters().get(0).getValue().get().trim().isEmpty()) {
      currentCommandShell.getTargets().stream() //
          .sorted((cmd1, cmd2) -> cmd1.getName().compareTo(cmd2.getName())) //
          .map(cmd -> commandHelp.getCommandOverviewText(cmd)) //
          .forEach(line -> builder.append(line).append("\n\r"));
      builder.append(commandHelp.getCommandOverviewFooterText().orElse(""));
    }
    else {
      final String commandToSearch = command.getParameters().get(0).getValue().orElse("");
      builder.append(this.createDetailCommandHelpText(commandToSearch));
    }
    return new DefaultCommandResult<>(command, Optional.of(builder.toString()));
  }

  /**
   * Resolves recursively {@link CommandTargetAlias}es.
   *
   * @param commandTarget
   *          (possible) {@link CommandTargetAlias} to be resolved
   * @return resolved {@link CommandTarget}
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected CommandTarget resolveAliases(final CommandTarget commandTarget) {
    if (!CommandTargetAlias.class.isInstance(commandTarget)) {
      return commandTarget;
    }
    final CommandTargetAlias alias = CommandTargetAlias.class.cast(commandTarget);
    final CommandTarget aliasedtarget = alias.getTarget();
    return this.resolveAliases(aliasedtarget);
  }
}