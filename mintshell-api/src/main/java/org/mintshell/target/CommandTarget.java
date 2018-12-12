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

import java.util.List;
import java.util.Optional;

import org.mintshell.command.Command;

/**
 * <p>
 * A {@link CommandTarget} is an acceptor of {@link Command}s and responsible to execute the intended funtionality. One
 * or more {@link CommandTargetParameter}s may be accepted and/or required to perform the operation that maps to the
 * accepted {@link Command}.
 * </p>
 *
 *
 * @author Noqmar
 * @since 0.2.0
 */
public abstract interface CommandTarget {

  /**
   * Returns the (optional) description of the {@link CommandTarget}
   *
   * @return (optional) description of the {@link CommandTarget}
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract Optional<String> getDescription();

  /**
   * Returns the name of the {@link CommandTarget}
   *
   * @return name of the {@link CommandTarget}
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract String getName();

  /**
   * Returns the ordered {@link List} of {@link CommandTargetParameter}s that are supported by the
   * {@link CommandTarget}.
   *
   * @return {@link List} of supported {@link CommandTargetParameter}s or an empty {@link List}, if the
   *         {@link CommandTarget} supports no {@link CommandTargetParameter}s
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract List<? extends CommandTargetParameter> getParameters();
}
