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
package org.mintshell.command;

import java.util.Optional;

import org.mintshell.annotation.Nullable;

/**
 * A {@link CommandParameter} is used to provide data for the execution of a {@link Command}. A {@link CommandParameter}
 * provides at least an index and optional additional references like a name, a short name and of course a value.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class CommandParameter {

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
  public CommandParameter(final int index) {
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
  public CommandParameter(final int index, final @Nullable String value) {
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
  public CommandParameter(final int index, final @Nullable String name, final @Nullable Character shortName, final @Nullable String value) {
    this.index = index;
    this.name = Optional.ofNullable(name);
    this.shortName = Optional.ofNullable(shortName);
    this.value = Optional.ofNullable(value);
  }

  /**
   * Returns the index of this parameter as the corresponding {@link Command} expects it.
   *
   * @return parameter index
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public int getIndex() {
    return this.index;
  }

  /**
   * Returns the parameter's (full) name, if available.
   *
   * @return (full) name of the parameter or {@link Optional#empty()}, if the parameter isn't named
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Optional<String> getName() {
    return this.name;
  }

  /**
   * Returns the parameter's short name, if available.
   *
   * @return short name of the parameter or {@link Optional#empty()}, if the parameter has not short named
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Optional<Character> getShortName() {
    return this.shortName;
  }

  /**
   * Returns the parameter's value if available.
   *
   * @return value of the parameter or {@link Optional#empty()}, if the parameter has no value
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Optional<String> getValue() {
    return this.value;
  }
}
