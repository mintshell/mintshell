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
package org.mintshell.dispatcher.annotation;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.mintshell.CommandDispatcher;
import org.mintshell.annotation.Command;
import org.mintshell.command.CommandParameter;
import org.mintshell.dispatcher.reflection.AbstractReflectionCommandDispatcher;
import org.mintshell.dispatcher.reflection.PrimitiveParameter;
import org.mintshell.dispatcher.reflection.ReflectionCommandParameter;
import org.mintshell.dispatcher.reflection.ReflectionCommandParameterFactory;
import org.mintshell.dispatcher.reflection.StaticStringConstructionMethodParameter;
import org.mintshell.dispatcher.reflection.StringConstructorParameter;
import org.mintshell.dispatcher.reflection.UnsupportedParameterTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Implementation of a {@link AbstractReflectionCommandDispatcher} that inspects command targets via reflection
 * searching for annotations and translates annotated methods into commands. If targeting a {@link Class} instead of an
 * {@link Object}, only static methods getting into account.
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
public class AnnotationCommandDispatcher extends AbstractReflectionCommandDispatcher<AnnotationCommandParameter, AnnotationCommand<AnnotationCommandParameter>>
    implements CommandDispatcher {

  private static final Logger LOG = LoggerFactory.getLogger(AnnotationCommandDispatcher.class);

  /**
   * Creates a new instance with {@link #DEFAULT_SUPPORTED_PARAMETERS}.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public AnnotationCommandDispatcher() {
    super();
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
  public AnnotationCommandDispatcher(final ReflectionCommandParameterFactory... supportedCommandParameters) {
    super(supportedCommandParameters);
  }

  /**
   *
   * @{inheritDoc}
   * @see org.mintshell.dispatcher.reflection.AbstractReflectionCommandDispatcher#createCommandFromMethod(java.lang.reflect.Method)
   */
  @Override
  protected Optional<AnnotationCommand<AnnotationCommandParameter>> createCommandFromMethod(final Method method) {
    try {
      final AnnotationCommand<AnnotationCommandParameter> command = new AnnotationCommand<>(method,
          this.createCommandParameters(method, this.getSupportedParameters()));
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
   * @see org.mintshell.dispatcher.reflection.AbstractReflectionCommandDispatcher#determineSupportedMethods(java.lang.Class)
   */
  @Override
  protected List<Method> determineSupportedMethods(final Class<?> target) {
    return stream(target.getMethods()) //
        .filter(method -> method.getAnnotation(Command.class) != null) //
        .collect(toList());
  }

  private AnnotationCommandParameter createCommandParameter(final Parameter parameter, final int index,
      final Set<ReflectionCommandParameterFactory> supportedCommandParameters) throws UnsupportedParameterTypeException {
    for (final ReflectionCommandParameterFactory supportedParameter : supportedCommandParameters) {
      try {
        final ReflectionCommandParameter reflectionParameter = supportedParameter.create(parameter.getType(), index);
        // TODO: parse name, shortname, required from annotation
        final String name = null;
        final Character shortName = null;
        final boolean required = false;

        return new AnnotationCommandParameter(reflectionParameter, name, shortName, required);
      } catch (final UnsupportedParameterTypeException e) {
        LOG.trace("Failed to create command parameter from parameter [{}] with parameter factory [{}]", parameter, supportedParameter, e);
      }
    }
    throw new UnsupportedParameterTypeException(format("Failed to create command parameter from reflection parameter [{}]", parameter));
  }

  private List<AnnotationCommandParameter> createCommandParameters(final Method method, final Set<ReflectionCommandParameterFactory> supportedCommandParameters)
      throws UnsupportedParameterTypeException {
    final Parameter[] parameters = method.getParameters();
    final List<AnnotationCommandParameter> commandParameters = new ArrayList<>();
    for (int i = 0; i < parameters.length; i++) {
      final AnnotationCommandParameter commandParameter = this.createCommandParameter(parameters[i], i, supportedCommandParameters);
      commandParameters.add(commandParameter);
    }
    return commandParameters;
  }
}
