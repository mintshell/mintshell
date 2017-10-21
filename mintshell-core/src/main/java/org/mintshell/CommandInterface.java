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
package org.mintshell;

/**
 * A {@link CommandInterface} is responsible of exchaning {@link CommandMessage}s with one or multiple users.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public abstract interface CommandInterface {

  /**
   * Activates the {@link CommandInterface} with the given {@link CommandInterpreter} and {@link CommandDispatcher}.
   *
   * @param commandInterpreter
   *          interpreter to be used
   * @param commandDispatcher
   *          dispatcher to be used
   * @throws IllegalStateException
   *           if the {@link CommandInterface} is already activated
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract void activate(CommandInterpreter commandInterpreter, CommandDispatcher commandDispatcher) throws IllegalStateException;

  /**
   * Returns whether this {@link CommandInterface} was activated using
   * {@link #activate(CommandInterpreter, CommandDispatcher)}.
   *
   * @return {@code true}, if this {@link CommandInterface} was activated, {@code false} otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract boolean isActivated();
}
