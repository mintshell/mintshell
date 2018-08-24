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

import java.util.Optional;

import org.mintshell.annotation.Nullable;

/**
 * Base implementation of a {@link CommandTargetParameter}.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public abstract class BaseCommandTargetParameter implements CommandTargetParameter {

  private final int index;
  private final Optional<String> name;
  private final Optional<String> description;
  private final Optional<Character> shortName;
  private final boolean required;

  /**
   * Creates a new instance.
   *
   * @param index
   *          index of the parameter
   * @param name
   *          name of the parameter or {@code null}, if the paramter has no name
   * @param shortName
   *          short name of the parameter or {@code null}, if the paramter has no short name
   * @param description
   *          description text or {@code null}, if the parameter has no description
   * @param required
   *          {@code true}, if the parameter is required for execution, {@code false} otherwise
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public BaseCommandTargetParameter(final int index, final @Nullable String name, final @Nullable Character shortName, final @Nullable String description,
      final boolean required) {
    this.index = index;
    this.name = Optional.ofNullable(name);
    this.shortName = Optional.ofNullable(shortName);
    this.description = Optional.ofNullable(description);
    this.required = required;
  }

  /**
   *
   * {@inheritDoc}
   * 
   * @see org.mintshell.target.CommandTargetParameter#getDescription()
   */
  @Override
  public Optional<String> getDescription() {
    return this.description;
  }

  /**
   *
   * {@inheritDoc}
   * 
   * @see org.mintshell.target.CommandTargetParameter#getIndex()
   */
  @Override
  public int getIndex() {
    return this.index;
  }

  /**
   *
   * {@inheritDoc}
   * 
   * @see org.mintshell.target.CommandTargetParameter#getName()
   */
  @Override
  public Optional<String> getName() {
    return this.name;
  }

  /**
   *
   * {@inheritDoc}
   * 
   * @see org.mintshell.target.CommandTargetParameter#getShortName()
   */
  @Override
  public Optional<Character> getShortName() {
    return this.shortName;
  }

  /**
   *
   * {@inheritDoc}
   * 
   * @see org.mintshell.target.CommandTargetParameter#isRequired()
   */
  @Override
  public boolean isRequired() {
    return this.required;
  }
}
