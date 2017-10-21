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
import static java.util.Arrays.stream;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.mintshell.CommandDispatchException;
import org.mintshell.CommandDispatcher;
import org.mintshell.CommandTarget;
import org.mintshell.command.Command;
import org.mintshell.command.CommandResult;

/**
 * Implementation of a {@link CommandDispatcher} that inspects command targets via reflection and translates methods
 * into commands. If targeting a {@link Class} instead of an {@link Object}, only static methods getting into account.
 * For all {@link Command}s short names cannot be assigned automatically and will be therefore omited.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class ReflectionCommandDispatcher extends AbstractCommandDispatcher implements CommandDispatcher {

  private final Map<CommandTarget, Map<Command, Method>> commandTargetCommandMethods = new HashMap<>();

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.dispatcher.AbstractCommandDispatcher#determineCommands(org.mintshell.CommandTarget)
   */
  @Override
  protected Set<Command> determineCommands(final CommandTarget commandTarget) {
    final Map<Command, Method> commandMethods = new HashMap<>();
    stream(commandTarget.getTargetClass().getMethods()) //
        .filter(method -> (commandTarget.isInstance() || !commandTarget.isInstance() && Modifier.isStatic(method.getModifiers()))) //
        .forEach(method -> commandMethods.put(this.createCommandFromMethod(method), method));
    this.commandTargetCommandMethods.put(commandTarget, commandMethods);
    return commandMethods.keySet();
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.dispatcher.AbstractCommandDispatcher#invokeCommand(org.mintshell.command.Command,
   *      org.mintshell.CommandTarget)
   */
  @Override
  protected CommandResult<?> invokeCommand(final Command command, final CommandTarget commandTarget) throws CommandDispatchException {
    if (!this.commandTargetCommandMethods.containsKey(commandTarget)) {
      throw new CommandDispatchException(format("Command [%s] is unknown", command));
    }
    final Map<Command, Method> commandMethods = this.commandTargetCommandMethods.get(commandTarget);
    if (!commandMethods.containsKey(command)) {
      throw new CommandDispatchException(format("Command [%s] is unknown", command));
    }
    final Method method = commandMethods.get(command);
    final boolean accessible = method.isAccessible();
    try {
      method.setAccessible(true);
      // TODO: handle parameters
      final Object result = method.invoke(commandTarget.isInstance() ? commandTarget.getTargetInstance() : commandTarget.getTargetClass());
      return new CommandResult<>(result);
    } catch (final InvocationTargetException e) {
      return new CommandResult<>(e.getTargetException());
    } catch (final IllegalAccessException e) {
      throw new CommandDispatchException(format("Failed to execute command [%s]", command), e);
    } catch (final RuntimeException e) {
      throw new CommandDispatchException(format("Failed to execute command [%s]", command), e);
    } finally {
      method.setAccessible(accessible);
    }
  }

  private Command createCommandFromMethod(final Method method) {
    // TODO: handle parameters
    return new Command(method.getName(), null, Collections.emptyList());
  }
}
