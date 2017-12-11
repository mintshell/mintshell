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
import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.mintshell.CommandDispatchException;
import org.mintshell.CommandDispatcher;
import org.mintshell.CommandTarget;
import org.mintshell.assertion.Assert;
import org.mintshell.command.Command;
import org.mintshell.command.CommandAlias;
import org.mintshell.command.CommandResult;

/**
 * Basic implementation of a {@link CommandDispatcher} managing {@link CommandTarget}s. Each {@link CommandTarget} that
 * is added to this {@link CommandDispatcher} is inspected to get all available {@link Command}s supported by that
 * {@link CommandTarget} using the {@link #determineCommands(CommandTarget)} method. It also enforces that
 * {@link Command}s determined from added {@link CommandTarget}s are unique, which means when adding a
 * {@link CommandTarget} providing an already managed {@link Command} an {@link IllegalStateException} will be thrown.
 * Dispatching {@link Command}s means to find a supported {@link Command} and the corresponding managed
 * {@link CommandTarget} and execute it via the {@link #invokeCommand(Command, CommandTarget)} method. These two methods
 * have to be provided by subclasses.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public abstract class AbstractCommandDispatcher<C extends Command<?>> implements CommandDispatcher {

  private final Map<C, CommandTarget> commands;

  /**
   * Creates a new instance.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected AbstractCommandDispatcher() {
    this.commands = new HashMap<>();
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.CommandDispatcher#addCommandTargets(org.mintshell.CommandTarget[])
   */
  @Override
  public void addCommandTargets(final CommandTarget... commandTargets) {
    Assert.ARG.isNotNull(commandTargets, "[commandTargets] must not be [null]");
    this.addCommandTargets(new HashSet<>(asList(commandTargets)));
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.CommandDispatcher#addCommandTargets(java.util.Set)
   */
  @Override
  public void addCommandTargets(final Set<CommandTarget> commandTargets) {
    Assert.ARG.isNotNull(commandTargets, "[commandTargets] must not be [null]");
    commandTargets.forEach(this::addCommandTarget);
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.CommandDispatcher#dispatch(org.mintshell.command.Command)
   */
  @Override
  public CommandResult<?> dispatch(final Command<?> command) throws CommandDispatchException {
    final Optional<Entry<C, CommandTarget>> entryCandidate = this.commands.entrySet().stream() //
        .filter(entry -> entry.getKey().getName().equals(command.getName())) //
        .findFirst();
    final Entry<C, CommandTarget> entry = entryCandidate.orElseThrow(() -> new CommandDispatchException(format("%s: command not found", command)));
    final C targetCommand = this.resolveAliases(entry.getKey());
    return this.invokeCommand(command, targetCommand, entry.getValue());
  }

  /**
   * Returns the count of currently managed {@link Command}s.
   *
   * @return count currently managed {@link Command}s
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public int getCommandCount() {
    return this.commands.size();
  }

  /**
   * Returns all currently managed {@link Command}s.
   *
   * @return all currently managed {@link Command}s
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Map<C, CommandTarget> getCommands() {
    return new HashMap<>(this.commands);
  }

  /**
   * Adds the given {@link CommandTarget} and checks uniquenes of the determinded {@link Command}s if
   * {@link #isForceUniqueCommands()} is set to {@code true}.
   *
   * @param commandTarget
   *          command target to add
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected void addCommandTarget(final CommandTarget commandTarget) {
    Assert.ARG.isNotNull(commandTarget, "[commandTarget] must not be [null]");
    final Set<C> commandTargetCommands = this.determineCommands(commandTarget);
    for (final C commandTargetCommand : commandTargetCommands) {
      if (this.commands.containsKey(commandTargetCommand)) {
        throw new IllegalStateException(format("Command [%s] is duplicated"));
      }
      this.commands.put(commandTargetCommand, commandTarget);
    }
  }

  /**
   * Inspects the given {@link CommandTarget} and determines all available {@link Command} names for this
   * {@link CommandTarget}.
   *
   * @param commandTarget
   *          command target to be inspected
   * @return {@link Set} of determined {@link Command} names
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected abstract Set<C> determineCommands(final CommandTarget commandTarget);

  /**
   * Performs the invokation of the given {@link Command} with it's corresponding {@link CommandTarget}.
   *
   * @param command
   *          command to be performed
   * @param targetCommand
   *          command determined from command target
   * @param commandTarget
   *          target on which the command will be performed
   * @return result of the invokation
   * @throws CommandDispatchException
   *           if the invocation itself failed
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected abstract CommandResult<?> invokeCommand(final Command<?> command, final C targetCommand, final CommandTarget commandTarget)
      throws CommandDispatchException;

  /**
   * Resolves recursively {@link CommandAlias}es.
   *
   * @param command
   *          (possible) {@link CommandAlias} to be resolved
   * @return resolved {@link Command}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  @SuppressWarnings("unchecked")
  protected C resolveAliases(final C command) {
    if (!CommandAlias.class.isInstance(command)) {
      return command;
    }
    final CommandAlias<?> alias = CommandAlias.class.cast(command);
    final C aliasedCommand = (C) alias.getCommand();
    return this.resolveAliases(aliasedCommand);
  }
}
