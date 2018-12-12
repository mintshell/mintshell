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
import static java.util.stream.Collectors.toList;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mintshell.annotation.Nullable;
import org.mintshell.command.CommandParameter;
import org.mintshell.target.CommandShell;
import org.mintshell.target.CommandTarget;
import org.mintshell.target.CommandTargetParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Implementation of a {@link CommandShell} that inspects command targets via reflection and translates methods into
 * commands. If a command target source is a {@link Class} instead of an {@link Object}, only static methods getting
 * into account.
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
public class ReflectionCommandShell extends BaseReflectionCommandShell {

  private static final Logger LOG = LoggerFactory.getLogger(ReflectionCommandShell.class);

  /**
   * Creates a new instance without prompt path separator.
   *
   * @param prompt
   *          prompt text
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected ReflectionCommandShell(final String prompt) {
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
  protected ReflectionCommandShell(final String prompt, final @Nullable String promptPathSeparator) {
    super(prompt, promptPathSeparator);
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.target.reflection.BaseReflectionCommandShell#createCommandTargetsFromMethod(java.lang.reflect.Method)
   */
  @Override
  protected Set<CommandTarget> createCommandTargetsFromMethod(final Method method) {
    final Set<CommandTarget> result = new HashSet<>();
    try {
      final DefaultReflectionCommandTarget commandTarget = new DefaultReflectionCommandTarget(method, method.getName(), null,
          this.createCommandParameters(method, this.getSupportedParameters()));
      LOG.trace("Successfully created command target [{}] from method [{}]", commandTarget, method);
      result.add(commandTarget);
    } catch (final UnsupportedParameterTypeException e) {
      LOG.warn("Failed to create command target from method [{}]", method, e);
    }
    return result;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.target.reflection.BaseReflectionCommandShell#determineSupportedMethods(java.lang.Class)
   */
  @Override
  protected List<Method> determineSupportedMethods(final Class<?> target) {
    return stream(target.getMethods()).collect(toList());
  }

  private ReflectionCommandTargetParameter createCommandParameter(final Parameter parameter, final int index,
      final Set<ReflectionCommandTargetParameterFactory> supportedCommandParameters) throws UnsupportedParameterTypeException {
    for (final ReflectionCommandTargetParameterFactory supportedParameter : supportedCommandParameters) {
      try {
        return supportedParameter.create(parameter.getType(), index, null, null, null, parameter.getType().isPrimitive());
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
