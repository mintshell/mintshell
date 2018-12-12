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
package org.mintshell.target;

import org.mintshell.annotation.Nullable;
import org.mintshell.assertion.Assert;
import org.mintshell.command.Command;

/**
 * A {@link DefaultCommandTargetAlias} is a delegation to a (real) {@link CommandTarget} with it's own name and
 * description.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class DefaultCommandTargetAlias extends BaseCommandTarget implements CommandTargetAlias {

  private final CommandTarget target;

  /**
   * Creates a new {@link DefaultCommandTargetAlias} for the given {@link Command}. Note that the given {@link Command}
   * may be and {@link DefaultCommandTargetAlias} again.
   *
   * @param target
   *          {@link CommandTarget} to be aliased
   * @param name
   *          name of the alias
   * @param descripion
   *          (optional) description of the alias
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public DefaultCommandTargetAlias(final CommandTarget target, final String name, final @Nullable String descripion) {
    super(name, descripion, Assert.ARG.isNotNull(target, "[commandExecution] must not be [null]").getParameters());
    this.target = target;
  }

  /**
   *
   * {@inheritDoc}
   * 
   * @see org.mintshell.target.CommandTargetAlias#getTarget()
   */
  @Override
  public CommandTarget getTarget() {
    return this.target;
  }
}
