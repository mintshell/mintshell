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
package org.mintshell.command;

import java.util.Optional;

import org.mintshell.annotation.Nullable;

/**
 * Default implementation of a {@link CommandParameter}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class DefaultCommandParameter implements CommandParameter {

  private final int index;
  private final Optional<String> name;
  private final Optional<Character> shortName;
  private final Optional<String> value;

  /**
   * Creates a new instance with an index.
   *
   * @param index
   *          parameter index
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public DefaultCommandParameter(final int index) {
    this(index, null);
  }

  /**
   * Creates a new instance with an index and an (optional) value.
   *
   * @param index
   *          parameter index
   * @param value
   *          (optional) parameter value
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public DefaultCommandParameter(final int index, final @Nullable String value) {
    this(index, null, null, value);
  }

  /**
   * Creates a new instance.
   *
   * @param index
   *          parameter index
   * @param name
   *          (optional) parameter (long) name
   * @param shortName
   *          (optional) parameter short name
   * @param value
   *          (optional) parameter value
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public DefaultCommandParameter(final int index, final @Nullable String name, final @Nullable Character shortName, final @Nullable String value) {
    this.index = index;
    this.name = Optional.ofNullable(name);
    this.shortName = Optional.ofNullable(shortName);
    this.value = Optional.ofNullable(value);
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.command.CommandParameter#getIndex()
   */
  @Override
  public int getIndex() {
    return this.index;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.command.CommandParameter#getName()
   */
  @Override
  public Optional<String> getName() {
    return this.name;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.command.CommandParameter#getShortName()
   */
  @Override
  public Optional<Character> getShortName() {
    return this.shortName;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.command.CommandParameter#getValue()
   */
  @Override
  public Optional<String> getValue() {
    return this.value;
  }

  /**
   *
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    if (this.getName().isPresent() && !this.getName().get().isEmpty()) {
      builder.append(this.getName().get());
    }
    else if (this.getShortName().isPresent()) {
      builder.append(this.getShortName().get());
    }
    else {
      builder.append(Integer.toString(this.getIndex()));
    }
    if (this.getValue().isPresent()) {
      builder.append("=").append(this.getValue().get());
    }
    return builder.toString();
  }
}
