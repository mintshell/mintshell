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

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.mintshell.assertion.Assert;
import org.mintshell.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extension of a {@link Command} that uses reflection to map a {@link Method} to a {@link Command}.
 *
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class ReflectionCommand extends Command<ReflectionCommandParameter> {

  private static final Logger LOG = LoggerFactory.getLogger(ReflectionCommand.class);

  private final Method method;

  /**
   * Creates a new instance.
   *
   * @param method
   *          method to use
   * @param supportedCommandParameters
   *          supported command parameter types (factories)
   * @throws UnsupportedParameterTypeException
   *           if at least one of the given method's parameters isn't supported
   * @author Noqmar
   * @since 0.1.0
   */
  public ReflectionCommand(final Method method, final Set<ReflectionCommandParameterFactory> supportedCommandParameters)
      throws UnsupportedParameterTypeException {
    super(Assert.ARG.isNotNull(method, "[method] must not be [null]").getName(),
        createCommandParameters(method, Assert.ARG.isNotNull(supportedCommandParameters, "[supportedCommandParameters] must not be [null]")));
    this.method = method;
  }

  /**
   * Returns the method this {@link ReflectionCommand} is derived from.
   * 
   * @return method
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Method getMethod() {
    return this.method;
  }

  private static ReflectionCommandParameter createCommandParameter(final Parameter parameter, final int index,
      final Set<ReflectionCommandParameterFactory> supportedCommandParameters) throws UnsupportedParameterTypeException {
    for (final ReflectionCommandParameterFactory supportedParameter : supportedCommandParameters) {
      try {
        return supportedParameter.create(index, parameter.getType());
      } catch (final UnsupportedParameterTypeException e) {
        LOG.trace("Failed to create command parameter from parameter [{}] with parameter factory [{}]", parameter, supportedParameter, e);
      }
    }
    throw new UnsupportedParameterTypeException(format("Failed to create command parameter from reflection parameter [{}]", parameter));
  }

  private static List<ReflectionCommandParameter> createCommandParameters(final Method method,
      final Set<ReflectionCommandParameterFactory> supportedCommandParameters) throws UnsupportedParameterTypeException {
    final Parameter[] parameters = method.getParameters();
    final List<ReflectionCommandParameter> commandParameters = new ArrayList<>();
    for (int i = 0; i < parameters.length; i++) {
      final ReflectionCommandParameter commandParameter = createCommandParameter(parameters[i], i, supportedCommandParameters);
      commandParameters.add(commandParameter);
    }
    return commandParameters;
  }
}
