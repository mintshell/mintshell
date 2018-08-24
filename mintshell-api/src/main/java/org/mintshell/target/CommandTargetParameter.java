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

import org.mintshell.command.Command;

/**
 * A {@link CommandTargetParameter} is used to describe data needed for the execution invoked by a {@link Command}. A
 * {@link CommandTargetParameter} provides at least an index and optional additional references like a name and
 * description and a flag, that marks the {@link CommandTargetParameter} as required or optional.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public interface CommandTargetParameter {

  /**
   * Returns the parameter's description, if available.
   *
   * @return description of the parameter or {@link Optional#empty()}, if the parameter has no description
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract Optional<String> getDescription();

  /**
   * Returns the index of this parameter as the corresponding {@link CommandTarget} expects it.
   *
   * @return parameter index
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract int getIndex();

  /**
   * Returns the parameter's (full) name, if available.
   *
   * @return (full) name of the parameter or {@link Optional#empty()}, if the parameter isn't named
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract Optional<String> getName();

  /**
   * Returns the parameter's short name, if available.
   *
   * @return short name of the parameter or {@link Optional#empty()}, if the parameter has no short named
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract Optional<Character> getShortName();

  /**
   * Returns, whether this parameter is required for the corresponding {@link CommandTarget}.
   *
   * @return {@code true}, if this parameter is required, {@code false} otherwise
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract boolean isRequired();
}
