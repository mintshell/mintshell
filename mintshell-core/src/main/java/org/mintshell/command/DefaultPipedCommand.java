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
package org.mintshell.command;

import java.util.ArrayList;
import java.util.List;

import org.mintshell.assertion.Assert;

/**
 * Default implementation of a {@link PipedCommand}.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public class DefaultPipedCommand extends DefaultCommand implements PipedCommand {

  private final Command pipeTarget;

  /**
   * Creates a new instance.
   *
   * @param name
   *          name
   * @param pipeTarget
   *          pipe target command
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public DefaultPipedCommand(final String name, final Command pipeTarget) {
    super(name);
    this.pipeTarget = Assert.ARG.isNotNull(pipeTarget, "[pipeTarget] must not be [null]");
  }

  /**
   * Creates a new instance.
   *
   * @param name
   *          name
   * @param parameters
   *          {@link List} of {@link CommandParameter}s
   * @param pipeTarget
   *          pipe target command
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public DefaultPipedCommand(final String name, final List<CommandParameter> parameters, final Command pipeTarget) {
    super(name, parameters);
    this.pipeTarget = Assert.ARG.isNotNull(pipeTarget, "[pipeTarget] must not be [null]");
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.command.PipedCommand#createPipeTarget(java.lang.String)
   */
  @Override
  public Command createPipeTarget(final String prevCommandResult) {
    final List<CommandParameter> parameters = new ArrayList<>();
    parameters.add(CommandParameterBuilder.create(0).withValue(prevCommandResult).build());
    for (int index = 1; index <= this.pipeTarget.getParameters().size(); index++) {
      final CommandParameter originalParameter = this.pipeTarget.getParameters().get(index - 1);

      parameters.add(CommandParameterBuilder.create(index) //
          .withName(originalParameter.getName().orElse(null)) //
          .withShortName(originalParameter.getShortName().orElse(null)) //
          .withValue(originalParameter.getValue().orElse(null)) //
          .build()  //
      );
    }
    final CommandBuilder builder = CommandBuilder.create(this.pipeTarget.getName()).withParameters(parameters);
    return this.pipeTarget instanceof PipedCommand ? builder.build(((PipedCommand) this.pipeTarget).getPipeTarget()) : builder.build();
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.command.PipedCommand#getPipeTarget()
   */
  @Override
  public Command getPipeTarget() {
    return this.pipeTarget;
  }
}
