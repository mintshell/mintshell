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
package org.mintshell.command;

/**
 * A {@link Command} that represents the source of a pipe containing the {@link Command} which is the target. By
 * evaluating a {@link PipedCommand} the result of executing the source {@link Command} shall be passed as first
 * parameter to target {@link Command}. To allow a chain of piped {@link Command}s the target {@link Command} may also
 * be {@link PipedCommand}.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public abstract interface PipedCommand extends Command {

  /**
   * Returns the pipe's target {@link Command} using the given result of the pipe's source.
   *
   * @param prevCommandResult
   *          result of the pipe's source command
   * @return target {@link Command}
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract Command createPipeTarget(final String prevCommandResult);

  /**
   * Returns the pipe's target {@link Command}.
   *
   * @return target {@link Command}
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract Command getPipeTarget();
}
