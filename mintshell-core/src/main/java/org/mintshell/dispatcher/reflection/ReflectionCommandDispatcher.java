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
package org.mintshell.dispatcher.reflection;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.mintshell.CommandDispatchException;
import org.mintshell.CommandDispatcher;
import org.mintshell.CommandTarget;
import org.mintshell.command.Command;
import org.mintshell.command.CommandParameter;
import org.mintshell.command.CommandResult;
import org.mintshell.dispatcher.AbstractCommandDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Implementation of a {@link CommandDispatcher} that inspects command targets via reflection and translates methods
 * into commands. If targeting a {@link Class} instead of an {@link Object}, only static methods getting into account.
 * For all {@link Command}s short names cannot be assigned automatically and will be therefore omited.
 * </p>
 * <p>
 * This {@link CommandDispatcher} supports the following {@link CommandParameter}s by default:
 * <ul>
 * <li>{@link PrimitiveParameter}</li>
 * <li>{@link StaticStringConstructionMethodParameter}</li>
 * <li>{@link StringConstructorParameter}</li>
 * </ul>
 * Further {@link CommandParameter}s may be added via
 * {@link #addSupportedParameters(org.mintshell.dispatcher.reflection.ReflectionCommandParameterFactory...)}
 * </p>
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class ReflectionCommandDispatcher extends AbstractCommandDispatcher<ReflectionCommand> implements CommandDispatcher {

  private static final Logger LOG = LoggerFactory.getLogger(ReflectionCommandDispatcher.class);

  public static final ReflectionCommandParameterFactory[] DEFAULT_SUPPORTED_PARAMETERS = new ReflectionCommandParameterFactory[] { PrimitiveParameter.FACTORY,
      StaticStringConstructionMethodParameter.FACTORY, StringConstructorParameter.FACTORY };

  private final Map<CommandTarget, Map<ReflectionCommand, Method>> commandTargetCommandMethods;
  private final Set<ReflectionCommandParameterFactory> supportedCommandParameters;

  /**
   * Creates a new instance with {@link #DEFAULT_SUPPORTED_PARAMETERS}.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public ReflectionCommandDispatcher() {
    this(DEFAULT_SUPPORTED_PARAMETERS);
  }

  /**
   * Creates a new instance.
   *
   * @param supportedCommandParameters
   *          supported {@link ReflectionCommandParameterFactory}s
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public ReflectionCommandDispatcher(final ReflectionCommandParameterFactory... supportedCommandParameters) {
    this.commandTargetCommandMethods = new HashMap<>();
    this.supportedCommandParameters = new HashSet<>();
    this.addSupportedParameters(supportedCommandParameters);
  }

  /**
   * Adds {@link ReflectionCommandParameterFactory} meaning concrete types of {@link CommandParameter}s to be supported.
   *
   * @param supportedParameters
   *          more supported {@link ReflectionCommandParameterFactory}s
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public void addSupportedParameters(final ReflectionCommandParameterFactory... supportedParameters) {
    if (supportedParameters != null) {
      stream(supportedParameters).filter(param -> param != null).forEach(this.supportedCommandParameters::add);
    }
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.CommandDispatcher#getSupportedParameters()
   */
  @Override
  public Set<ReflectionCommandParameterFactory> getSupportedParameters() {
    return new HashSet<>(this.supportedCommandParameters);
  }

  /**
   * Tries to create a {@link Command} from a given {@link Method}.
   *
   * @param method
   *          {@link Method} to create a {@link Command} from
   * @return created {@link Command} or {@link Optional#empty()}, if for some reason the given {@link Method} can't be
   *         used for {@link Command} creation
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected Optional<ReflectionCommand> createCommandFromMethod(final Method method) {
    try {
      final ReflectionCommand command = new ReflectionCommand(method, this.getSupportedParameters());
      LOG.trace("Successfully created command [{}] from method [{}]", command, method);
      return Optional.of(command);
    } catch (final UnsupportedParameterTypeException e) {
      LOG.warn("Failed to create command from method [{}]", method, e);
      return Optional.empty();
    }
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.dispatcher.AbstractCommandDispatcher#determineCommands(org.mintshell.CommandTarget)
   */
  @Override
  protected Set<ReflectionCommand> determineCommands(final CommandTarget commandTarget) {
    final Map<ReflectionCommand, Method> commandMethods = new HashMap<>();
    this.determineSupportedMethods(commandTarget.getTargetClass()).stream() //
        .filter(method -> (commandTarget.isInstance() || !commandTarget.isInstance() && Modifier.isStatic(method.getModifiers()))) //
        .forEach(method -> this.createCommandFromMethod(method).ifPresent(command -> commandMethods.put(command, method)));
    this.commandTargetCommandMethods.put(commandTarget, commandMethods);
    return commandMethods.keySet();
  }

  /**
   * Returns a {@link List} of basically supported {@link Method}s for a given target {@link Class}. May be overwritten
   * by subclasses.
   *
   * @param target
   *          target {@link Class}
   * @return {@link List} of basically supported {@link Method}s
   *
   * @author Noqmar
   * @since 0.1.0
   */
  protected List<Method> determineSupportedMethods(final Class<?> target) {
    return stream(target.getMethods()).collect(toList());
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.dispatcher.AbstractCommandDispatcher#invokeCommand(org.mintshell.command.Command,
   *      org.mintshell.command.Command, org.mintshell.CommandTarget)
   */
  @Override
  protected CommandResult<?> invokeCommand(final Command<?> command, final ReflectionCommand targetCommand, final CommandTarget commandTarget)
      throws CommandDispatchException {

    final Object[] args = new Object[targetCommand.getParameterCount()];
    for (int i = 0; i < targetCommand.getParameterCount(); i++) {
      final int index = i;
      final Optional<? extends CommandParameter> commandParameter = command.getParameters().stream().filter(p -> p.getIndex() == index).findFirst();
      final ReflectionCommandParameter reflectionParameter = targetCommand.getParameters().stream() //
          .filter(p -> p.getIndex() == index) //
          .findFirst() //
          .orElseThrow(() -> new CommandDispatchException(String.format("Target command parameter with index [%s] is missing", index)));
      if (reflectionParameter.isRequired() && !commandParameter.isPresent()) {
        throw new CommandDispatchException(String.format("Required parameter [%s] is missing", reflectionParameter));
      }
      final Optional<String> value = commandParameter.isPresent() ? commandParameter.get().getValue() : Optional.empty();
      try {
        args[index] = value.isPresent() ? reflectionParameter.of(value.get()) : null;
      } catch (final ParameterConversionException e) {
        throw new CommandDispatchException(String.format("Unsufficient value [%s] for parameter [%s]", value.get(), reflectionParameter));
      }
    }

    final Method method = targetCommand.getMethod();
    final boolean accessible = method.isAccessible();
    try {
      method.setAccessible(true);
      final Object result = method.invoke(commandTarget.isInstance() ? commandTarget.getTargetInstance() : null, args);
      return new CommandResult<>(command, result);
    } catch (final InvocationTargetException e) {
      return new CommandResult<>(command, e.getTargetException());
    } catch (final IllegalAccessException e) {
      throw new CommandDispatchException(format("Failed to execute command [%s]", command), e);
    } catch (final RuntimeException e) {
      throw new CommandDispatchException(format("Failed to execute command [%s]", command), e);
    } finally {
      method.setAccessible(accessible);
    }
  }
}
