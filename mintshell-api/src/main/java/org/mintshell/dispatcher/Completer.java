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
package org.mintshell.dispatcher;

import java.util.Set;
import java.util.SortedSet;

import org.mintshell.annotation.CommandTarget;

/**
 * Extension of a {@link CommandDispatcher}, that supports the completion of command fragments.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public abstract interface Completer {

  /**
   * Returns a {@link SortedSet} of currently available {@link CommandTarget} names, that start with the given command
   * fragment.
   *
   * @param commandFragment
   *          command fragment to be completed
   * @return {@link SortedSet} of matching {@link CommandTarget} names or an empty {@link Set}, if nothing matches
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract SortedSet<String> complete(final String commandFragment);
}
