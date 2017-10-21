/*
 * Copyright © 2017 mintshell.org
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
import java.util.Set;

import org.mintshell.CommandDispatchException;
import org.mintshell.CommandDispatcher;
import org.mintshell.CommandTarget;
import org.mintshell.assertion.Assert;
import org.mintshell.command.Command;
import org.mintshell.command.CommandResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Basic implementation of a {@link CommandDispatcher} managing {@link CommandTarget}s. It is possible to enforce that
 * {@link Command}s determined from added {@link CommandTarget}s are unique. In this case adding a {@link CommandTarget}
 * providing an already managed {@link Command} an Ill duplicated {@link Command} will cause an
 * {@link IllegalStateException} will be thrown. In the other case the {@link CommandDispatcher} accepts duplicated
 * {@link Command}s but doesn't ensure any order for exection.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public abstract class AbstractCommandDispatcher implements CommandDispatcher {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractCommandDispatcher.class);

  private final Map<Command, CommandTarget> commands;
  private final boolean forceUniqueCommands;

  /**
   * Creates a new instance with enforcement of unique {@link Command}s by added {@link CommandTarget}s.‚
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected AbstractCommandDispatcher() {
    this(true);
  }

  /**
   * Creates a new instance.
   *
   * @param forceUniqueCommands
   *          {@code true} if unique {@link Command}s are enforced on adding {@link CommandTarget}s, {@code false}
   *          otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected AbstractCommandDispatcher(final boolean forceUniqueCommands) {
    this.forceUniqueCommands = forceUniqueCommands;
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
  public CommandResult<?> dispatch(final Command command) throws CommandDispatchException {
    if (!this.commands.containsKey(command)) {
      throw new CommandDispatchException(format("Command [%s] is unknown", command));
    }
    final CommandTarget commandTarget = this.commands.get(command);
    return this.invokeCommand(command, commandTarget);
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
    final Set<Command> commandTargetCommands = this.determineCommands(commandTarget);
    for (final Command commandTargetCommand : commandTargetCommands) {
      if (this.commands.containsKey(commandTargetCommand)) {
        final String message = String.format("Command [%s] is duplicated");
        if (this.forceUniqueCommands) {
          throw new IllegalStateException(message);
        }
        else {
          LOG.warn(message);
        }
      }
      this.commands.put(commandTargetCommand, commandTarget);
    }
  }

  /**
   * Inspects the given {@link CommandTarget} and determines all available {@link Command}s for this
   * {@link CommandTarget}.
   *
   * @param commandTarget
   *          command target to be inspected
   * @return {@link Set} of determined {@link Command}s
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected abstract Set<Command> determineCommands(final CommandTarget commandTarget);

  /**
   * Performs the invokation of the given {@link Command} with it's corresponding {@link CommandTarget}.
   *
   * @param command
   *          command to be performed
   * @param commandTarget
   *          target on which the command will be performed
   * @return result of the invokation
   * @throws CommandDispatchException
   *           if the invocation itself failed
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected abstract CommandResult<?> invokeCommand(final Command command, final CommandTarget commandTarget) throws CommandDispatchException;

  /**
   * Returns whether unique {@link Command}s are enforced.
   * 
   * @return {@code true} if unique {@link Command}s are enforced, {@code false} otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected boolean isForceUniqueCommands() {
    return this.forceUniqueCommands;
  }
}
