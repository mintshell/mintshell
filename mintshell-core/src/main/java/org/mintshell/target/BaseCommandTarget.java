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
package org.mintshell.target;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mintshell.annotation.Nullable;
import org.mintshell.assertion.Assert;

/**
 * Base implementation of a {@link CommandTarget}.
 *
 *
 * @author Noqmar
 * @since 0.2.0
 */
public abstract class BaseCommandTarget implements CommandTarget {

  private final String name;
  private final List<? extends CommandTargetParameter> parameters;
  private final Optional<String> description;

  /**
   * Creates a new instance.
   *
   * @param name
   *          name
   * @param parameters
   *          {@link List} of {@link CommandTargetParameter}s
   * @param description
   *          description text or {@code null}, if no description is available
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public BaseCommandTarget(final String name, final @Nullable String description, final List<? extends CommandTargetParameter> parameters) {
    this.name = Assert.ARG.isNotNull(name, "[name] must not be [null]");
    this.parameters = new ArrayList<>(Assert.ARG.isNotNull(parameters, "[parameters] must not be [null]"));
    this.description = Optional.ofNullable(description);
  }

  /**
   *
   * {@inheritDoc}
   * 
   * @see org.mintshell.target.CommandTarget#getDescription()
   */
  @Override
  public Optional<String> getDescription() {
    return this.description;
  }

  /**
   *
   * {@inheritDoc}
   * 
   * @see org.mintshell.target.CommandTarget#getName()
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   *
   * {@inheritDoc}
   * 
   * @see org.mintshell.target.CommandTarget#getParameters()
   */
  @Override
  public List<? extends CommandTargetParameter> getParameters() {
    return new ArrayList<>(this.parameters);
  }
}
