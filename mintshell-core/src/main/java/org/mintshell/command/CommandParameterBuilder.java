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

/**
 * Builder to convenient create {@link CommandParameter}s.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public final class CommandParameterBuilder {

  private final int index;
  private String name;
  private Character shortName;
  private String value;

  private CommandParameterBuilder(final int index) {
    this.index = index;
  }

  /**
   * Builds the {@link CommandParameter} from the current builder state.
   *
   * @return {@link CommandParameter} instance
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public CommandParameter build() {
    return new DefaultCommandParameter(this.index, this.name, this.shortName, this.value);
  }

  /**
   * Sets a name to the current builder state.
   *
   * @param name
   *          the name to be set
   * @return {@link CommandParameterBuilder} instance
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public CommandParameterBuilder withName(final String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets a short name to the current builder state.
   *
   * @param shortName
   *          the shortName to be set
   * @return {@link CommandParameterBuilder} instance
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public CommandParameterBuilder withShortName(final Character shortName) {
    this.shortName = shortName;
    return this;
  }

  /**
   * Sets a value to the current builder state.
   *
   * @param value
   *          the value to be set
   * @return {@link CommandParameterBuilder} instance
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public CommandParameterBuilder withValue(final String value) {
    this.value = value;
    return this;
  }

  /**
   * Returns a {@link CommandParameterBuilder} which provides a convenient way to create new {@link CommandParameter}
   * instances.
   *
   * @param index
   *          index of the {@link CommandParameter} to build
   * @return {@link CommandParameterBuilder} instance
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public static CommandParameterBuilder create(final int index) {
    return new CommandParameterBuilder(index);
  }
}