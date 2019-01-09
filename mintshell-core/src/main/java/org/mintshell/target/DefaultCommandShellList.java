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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Default implementation of a {@link CommandShellList} wrapper.
 *
 * @author Noqmar
 * @since 0.3.0
 */
public class DefaultCommandShellList<S extends CommandShell> extends ArrayList<S> implements CommandShellList<S> {

  private static final long serialVersionUID = -6713286179690275261L;

  private final String resultMessage;

  /**
   * Constructs an empty list with an initial capacity of ten.
   *
   * @param resultMessage
   *          result message text
   * @param shells
   *          shell instances
   *
   * @author Noqmar
   * @since 0.3.0
   */
  @SafeVarargs
  public DefaultCommandShellList(final String resultMessage, final S... shells) {
    super();
    this.resultMessage = resultMessage;
    Arrays.stream(shells).forEach(this::add);
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.target.CommandShellList#getResultMessage()
   */
  @Override
  public String getResultMessage() {
    return this.resultMessage;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see java.util.AbstractCollection#toString()
   */
  @Override
  public String toString() {
    return this.getResultMessage();
  }
}
