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

import static java.util.Arrays.deepEquals;
import static java.util.Arrays.deepHashCode;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mintshell.assertion.Assert;

/**
 * Default implementation of a {@link Command}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class DefaultCommand implements Command {

  private final String name;
  private final List<CommandParameter> parameters;

  /**
   * Creates a new {@link DefaultCommand}.
   *
   * @param name
   *          name
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public DefaultCommand(final String name) {
    this(name, Collections.emptyList());
  }

  /**
   * Creates a new {@link DefaultCommand}.
   *
   * @param name
   *          name
   * @param parameters
   *          {@link List} of {@link CommandParameter}s
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public DefaultCommand(final String name, final List<CommandParameter> parameters) {
    this.name = Assert.ARG.isNotNull(name, "[name] must not be [null]");
    this.parameters = new ArrayList<>(Assert.ARG.isNotNull(parameters, "[parameters] must not be [null]"));
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (this.getClass() != other.getClass()) {
      return false;
    }
    final DefaultCommand that = (DefaultCommand) other;
    return deepEquals(new Object[] { this.name }, new Object[] { that.name });
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.command.Command#getName()
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   *
   * {@inheritDoc}
   * 
   * @see org.mintshell.command.Command#getParameters()
   */
  @Override
  public List<CommandParameter> getParameters() {
    return unmodifiableList(this.parameters);
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return deepHashCode(new Object[] { this.name });
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return this.name;
  }
}
