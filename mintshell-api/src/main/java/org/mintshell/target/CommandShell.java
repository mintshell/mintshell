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

import java.util.Optional;
import java.util.Set;

import org.mintshell.command.Command;
import org.mintshell.common.PromptProvider;

/**
 * A {@link CommandShell} encapsulates a kind of command execution environment. It manages a {@link Set} of supported
 * {@link CommandTarget}s and is able to invoke them on their corresponsind {@link CommandTargetSource} objects.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public abstract interface CommandShell extends PromptProvider {

  /**
   * Returns the (optional) prompt path separator.
   * 
   * @return prompt path separator or {@link Optional#empty()} if prompt pathing shall be disabled
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public Optional<String> getPromptPathSeparator();

  /**
   * Returns a {@link Set} of provided {@link CommandTarget}s within the scope of this {@link CommandShell}.
   *
   * @return {@link Set} of provided {@link CommandTarget}s
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract Set<CommandTarget> getTargets();

  /**
   * Determines a managed {@link CommandTarget} that matches the given {@link Command} and performs it's invocation.
   *
   * @param command
   *          intended command
   * @param commandTarget
   *          {@link CommandTarget} that should be used to execute the {@link Command}
   * @return result of the invocation
   * @throws CommandInvocationException
   *           if the invokation of the {@link CommandTarget} by the {@link Command} failed
   * @throws CommandTargetException
   *           if the invocation raised a (functional) exception
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public abstract Object invoke(Command command, CommandTarget commandTarget) throws CommandInvocationException, CommandTargetException;
}
