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
package org.mintshell.target.reflection;

import static java.lang.String.format;
import static java.util.Arrays.stream;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mintshell.annotation.Nullable;
import org.mintshell.command.Command;
import org.mintshell.command.CommandParameter;
import org.mintshell.target.BaseCommandShell;
import org.mintshell.target.CommandInvocationException;
import org.mintshell.target.CommandShell;
import org.mintshell.target.CommandTarget;
import org.mintshell.target.CommandTargetException;
import org.mintshell.target.CommandTargetParameter;
import org.mintshell.target.CommandTargetSource;

/**
 * <p>
 * Base implementation of a {@link CommandShell} that inspects command targets via reflection and translates methods
 * into commands. If a command target source is a {@link Class} instead of an {@link Object}, only static methods
 * getting into account.
 * </p>
 * <p>
 * This {@link CommandShell} supports the following {@link CommandTargetParameter}s by default:
 * </p>
 * <ul>
 * <li>{@link PrimitiveParameter}</li>
 * <li>{@link StaticStringConstructionMethodParameter}</li>
 * <li>{@link StringConstructorParameter}</li>
 * </ul>
 * <p>
 * Further {@link CommandParameter}s may be added via
 * {@link #addSupportedParameters(org.mintshell.target.reflection.ReflectionCommandTargetParameterFactory...)}
 * </p>
 *
 * @author Noqmar
 * @since 0.2.0
 */
public abstract class BaseReflectionCommandShell extends BaseCommandShell {

  public static final ReflectionCommandTargetParameterFactory[] DEFAULT_SUPPORTED_PARAMETERS = new ReflectionCommandTargetParameterFactory[] {
      PrimitiveParameter.FACTORY, StaticStringConstructionMethodParameter.FACTORY, StringConstructorParameter.FACTORY };

  private final Set<ReflectionCommandTargetParameterFactory> supportedCommandParameters;

  /**
   * Creates a new instance without prompt path separator.
   *
   * @param prompt
   *          prompt text
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected BaseReflectionCommandShell(final String prompt) {
    this(prompt, null);
  }

  /**
   * Creates a new instance.
   *
   * @param prompt
   *          prompt text
   * @param promptPathSeparator
   *          (optional) prompt path separator of this shell
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected BaseReflectionCommandShell(final String prompt, final @Nullable String promptPathSeparator) {
    super(prompt, promptPathSeparator);
    this.supportedCommandParameters = new HashSet<>();
    this.addSupportedParameters(DEFAULT_SUPPORTED_PARAMETERS);
  }

  /**
   * Adds {@link ReflectionCommandTargetParameterFactory} meaning concrete types of {@link CommandParameter}s to be
   * supported.
   *
   * @param supportedParameters
   *          more supported {@link ReflectionCommandTargetParameterFactory}s
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public void addSupportedParameters(final ReflectionCommandTargetParameterFactory... supportedParameters) {
    if (supportedParameters != null) {
      stream(supportedParameters) //
          .filter(param -> param != null) //
          .forEach(this.supportedCommandParameters::add);
    }
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.target.CommandShell#invoke(org.mintshell.command.Command, org.mintshell.target.CommandTarget)
   */
  @Override
  public Object invoke(final Command command, final CommandTarget commandTarget) throws CommandInvocationException, CommandTargetException {
    final Method method = ((ReflectionCommandTarget) commandTarget).getMethod();
    final CommandTargetSource source = this.getCommandTargetSources().get(commandTarget);
    final boolean accessible = method.isAccessible();
    try {
      final Object[] args = this.createInvocationArguments(command, commandTarget);
      method.setAccessible(true);
      return this.invokeMethod(method, args, source.isInstance() ? source.getTargetInstance() : null);
    } catch (final InvocationTargetException e) {
      throw new CommandTargetException(e.getTargetException());
    } catch (final IllegalAccessException e) {
      throw new CommandInvocationException(format("Failed to execute command [%s]", command), e);
    } catch (final RuntimeException e) {
      throw new CommandInvocationException(format("Failed to execute command [%s]", command), e);
    } finally {
      method.setAccessible(accessible);
    }
  }

  /**
   * Tries to create a {@link ReflectionCommandTarget} from a given {@link Method}.
   *
   * @param method
   *          {@link Method} to create a {@link Command} from
   * @return created {@link ReflectionCommandTarget} or {@link Optional#empty()}, if for some reason the given
   *         {@link Method} can't be used for {@link ReflectionCommandTarget} creation
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected abstract Set<CommandTarget> createCommandTargetsFromMethod(final Method method);

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.target.BaseCommandShell#determineCommandTargets(org.mintshell.target.CommandTargetSource)
   */
  @Override
  protected Set<CommandTarget> determineCommandTargets(final CommandTargetSource commandTargetSource) {
    return this.determineSupportedMethods(commandTargetSource.getTargetClass()).stream() //
        .filter(method -> (commandTargetSource.isInstance() || !commandTargetSource.isInstance() && Modifier.isStatic(method.getModifiers()))) //
        .flatMap(method -> this.createCommandTargetsFromMethod(method).stream()) //
        .collect(Collectors.toSet());
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
   * @since 0.2.0
   */
  protected abstract List<Method> determineSupportedMethods(final Class<?> target);

  /**
   * Returns the supported parameters.
   *
   * @return supported parameters
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected Set<ReflectionCommandTargetParameterFactory> getSupportedParameters() {
    return new HashSet<>(this.supportedCommandParameters);
  }

  /**
   * Allows subclasses to handle or maniulate the method invocation.
   *
   * @param method
   *          method to be invoked
   * @param args
   *          method arguments
   * @param source
   *          source object for the invocatoin
   * @return invocation result
   * @throws IllegalAccessException
   *           if this {@code Method} object is enforcing Java language access control and the underlying method is
   *           inaccessible.
   * @throws InvocationTargetException
   *           if the underlying method throws an exception.
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected Object invokeMethod(final Method method, final Object[] args, final Object source) throws IllegalAccessException, InvocationTargetException {
    return method.invoke(source, args);
  }

  private Object createInvocationArgument(final List<? extends CommandParameter> commandParameters,
      final ReflectionCommandTargetParameter commandTargetParameter) throws CommandInvocationException {
    final CommandParameter parameter = commandParameters.stream() //
        .map(cp -> (CommandParameter) cp) //
        .filter(cp -> commandTargetParameter.getName().isPresent() && commandTargetParameter.getName().equals(cp.getName())) //
        .findFirst() //
        .orElseGet(() -> {
          return commandParameters.stream() //
              .map(cp -> (CommandParameter) cp) //
              .filter(cp -> commandTargetParameter.getShortName().isPresent() && commandTargetParameter.getShortName().equals(cp.getShortName())) //
              .findFirst() //
              .orElseGet(() -> {
                return commandParameters.stream() //
                    .map(cp -> (CommandParameter) cp) //
                    .filter(cp -> commandTargetParameter.getIndex() == cp.getIndex() && !cp.getName().isPresent() && !cp.getShortName().isPresent()) //
                    .findFirst() //
                    .orElse(null);
              });
        });
    if (parameter == null) {
      if (commandTargetParameter.isRequired()) {
        throw new CommandInvocationException(String.format("Parameter [%s] is missing", commandTargetParameter));
      }
      else {
        return null;
      }
    }
    else if (parameter.getValue().isPresent()) {
      final String value = parameter.getValue().get();
      try {
        return commandTargetParameter.of(value);
      } catch (final ParameterConversionException e) {
        throw new CommandInvocationException(format("Insufficient value [%s] for parameter [%s]", value, commandTargetParameter), e);
      }
    }
    else if (!commandTargetParameter.isRequired()) {
      return null;
    }
    else {
      throw new CommandInvocationException(String.format("Required parameter [%s] is missing", commandTargetParameter));
    }
  }

  private Object[] createInvocationArguments(final Command command, final CommandTarget commandTarget) throws CommandInvocationException {
    final Object[] args = new Object[commandTarget.getParameters().size()];
    for (int i = 0; i < commandTarget.getParameters().size(); i++) {
      args[i] = this.createInvocationArgument(command.getParameters(), (ReflectionCommandTargetParameter) commandTarget.getParameters().get(i));
    }
    return args;
  }
}
