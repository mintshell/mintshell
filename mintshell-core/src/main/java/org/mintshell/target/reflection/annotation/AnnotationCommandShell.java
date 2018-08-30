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
package org.mintshell.target.reflection.annotation;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.mintshell.target.reflection.annotation.CommandShellExiter.EXIT_METHOD_NAME;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.mintshell.annotation.Param;
import org.mintshell.command.CommandParameter;
import org.mintshell.target.CommandShell;
import org.mintshell.target.CommandTarget;
import org.mintshell.target.CommandTargetSource;
import org.mintshell.target.reflection.BaseReflectionCommandShell;
import org.mintshell.target.reflection.DefaultReflectionCommandTarget;
import org.mintshell.target.reflection.PrimitiveParameter;
import org.mintshell.target.reflection.ReflectionCommandTargetParameter;
import org.mintshell.target.reflection.ReflectionCommandTargetParameterFactory;
import org.mintshell.target.reflection.StaticStringConstructionMethodParameter;
import org.mintshell.target.reflection.StringConstructorParameter;
import org.mintshell.target.reflection.UnsupportedParameterTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Implementation of a {@link BaseReflectionCommandShell} that inspects command targets via reflection searching for
 * annotations and translates annotated methods into commands. If targeting a {@link Class} instead of an
 * {@link Object}, only static methods getting into account.
 * </p>
 * <p>
 * This {@link CommandShell} supports the following {@link CommandParameter}s by default:
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
public class AnnotationCommandShell extends BaseReflectionCommandShell {

  public static final String DEFAULT_PROMPT = "Mintshell";
  private static final Logger LOG = LoggerFactory.getLogger(AnnotationCommandShell.class);

  /**
   * Creates a new instance with {@link #DEFAULT_PROMPT}.
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public AnnotationCommandShell() {
    this(DEFAULT_PROMPT);
  }

  /**
   * Creates a new instance from an {@link org.mintshell.annotation.CommandShell} annotation.
   *
   * @param annotation
   *          annotation
   * @param commandTargetSource
   *          command target source
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public AnnotationCommandShell(final org.mintshell.annotation.CommandShell annotation, final CommandTargetSource commandTargetSource) {
    super(annotation.prompt());
    this.addCommandTargetSources(commandTargetSource);
    this.addAnnotatedExitCommands(annotation);
  }

  /**
   * Creates a new instance.
   *
   * @param prompt
   *          prompt text
   * @author Noqmar
   * @since 0.2.0
   */
  public AnnotationCommandShell(final String prompt) {
    super(prompt);
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.target.reflection.BaseReflectionCommandShell#createCommandTargetFromMethod(java.lang.reflect.Method)
   */
  @Override
  protected Optional<CommandTarget> createCommandTargetFromMethod(final Method method) {
    try {
      final AnnotationCommandTarget commandTarget = new AnnotationCommandTarget(method, this.createCommandParameters(method, this.getSupportedParameters()));
      LOG.trace("Successfully created command [{}] from method [{}]", commandTarget, method);
      return Optional.of(commandTarget);
    } catch (final UnsupportedParameterTypeException e) {
      LOG.warn("Failed to create command from method [{}]", method, e);
      return Optional.empty();
    }
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.target.reflection.BaseReflectionCommandShell#determineSupportedMethods(java.lang.Class)
   */
  @Override
  protected List<Method> determineSupportedMethods(final Class<?> target) {
    return stream(target.getMethods()) //
        .filter(method -> method.getAnnotation(org.mintshell.annotation.CommandTarget.class) != null) //
        .collect(toList());
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.target.reflection.BaseReflectionCommandShell#invokeMethod(java.lang.reflect.Method,
   *      java.lang.Object[], java.lang.Object)
   */
  @Override
  protected Object invokeMethod(final Method method, final Object[] args, final Object source) throws IllegalAccessException, InvocationTargetException {
    final Object invocationResult = super.invokeMethod(method, args, source);
    if (invocationResult != null && !(invocationResult instanceof CommandShell)) {
      final org.mintshell.annotation.CommandShell shellAnnotation = invocationResult.getClass().getAnnotation(org.mintshell.annotation.CommandShell.class);
      if (shellAnnotation != null) {
        return new AnnotationCommandShell(shellAnnotation, new CommandTargetSource(invocationResult));
      }
    }
    return invocationResult;
  }

  private void addAnnotatedExitCommands(final org.mintshell.annotation.CommandShell annotation) {
    if (annotation.exitCommands().length > 0) {
      final String exitCommandDescription = annotation.exitCommandDescription().isEmpty() ? null : annotation.exitCommandDescription();
      final CommandShellExiter exiter = new CommandShellExiter(annotation.exitCommandMessage());
      for (final String exitCommand : annotation.exitCommands()) {
        try {
          final Method exitMethod = exiter.getClass().getMethod(EXIT_METHOD_NAME);
          final DefaultReflectionCommandTarget target = new DefaultReflectionCommandTarget(exitMethod, exitCommand, exitCommandDescription, emptyList());
          this.commandTargetSources.put(target, new CommandTargetSource(exiter));
        } catch (UnsupportedParameterTypeException | NoSuchMethodException | SecurityException e) {
          LOG.warn("Failed to add annotated exit command [{}]", exitCommand, e);
        }
      }
    }
  }

  private ReflectionCommandTargetParameter createCommandParameter(final Parameter parameter, final int index,
      final Set<ReflectionCommandTargetParameterFactory> supportedCommandParameters) throws UnsupportedParameterTypeException {
    final Param annotation = parameter.getAnnotation(Param.class);
    if (annotation == null) {
      throw new UnsupportedParameterTypeException(String.format("Parameter [%s] isn't annotated with [@%s]", parameter.getName(), Param.class.getSimpleName()));
    }
    for (final ReflectionCommandTargetParameterFactory supportedParameter : supportedCommandParameters) {
      try {
        final ReflectionCommandTargetParameter reflectionParameter = supportedParameter.create(parameter.getType(), index,
            annotation.name().isEmpty() ? null : annotation.name(), annotation.shortName() != Character.UNASSIGNED ? annotation.shortName() : null,
            annotation.description(), annotation.required() || parameter.getType().isPrimitive());
        return reflectionParameter;
      } catch (final UnsupportedParameterTypeException e) {
        LOG.trace("Failed to create command parameter from parameter [{}] with parameter factory [{}]", parameter, supportedParameter, e);
      }
    }
    throw new UnsupportedParameterTypeException(format("Failed to create command parameter from reflection parameter [{}]", parameter));
  }

  private List<ReflectionCommandTargetParameter> createCommandParameters(final Method method,
      final Set<ReflectionCommandTargetParameterFactory> supportedCommandParameters) throws UnsupportedParameterTypeException {
    final Parameter[] parameters = method.getParameters();
    final List<ReflectionCommandTargetParameter> commandParameters = new ArrayList<>();
    for (int i = 0; i < parameters.length; i++) {
      final ReflectionCommandTargetParameter commandParameter = this.createCommandParameter(parameters[i], i, supportedCommandParameters);
      commandParameters.add(commandParameter);
    }
    return commandParameters;
  }
}
