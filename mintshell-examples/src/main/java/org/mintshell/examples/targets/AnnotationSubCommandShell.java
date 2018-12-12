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
package org.mintshell.examples.targets;

import org.mintshell.annotation.CommandShell;
import org.mintshell.annotation.CommandTarget;
import org.mintshell.annotation.Param;
import org.mintshell.target.CommandShellExitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple class that can be used as instance command target providing nothing more than (annotated) methods to grab
 * static system information.
 *
 * @author Noqmar
 * @since 0.1.0
 */
@CommandShell(prompt = "Sub", promptPathSeparator = "/", enterMessage = "Welcome to the sub shell")
public class AnnotationSubCommandShell extends BaseShell {

  private static final Logger LOG = LoggerFactory.getLogger(SimpleCommandTarget.class);

  @CommandTarget(name = "exit", description = "exits the subshell")
  public void exit() {
    throw new CommandShellExitException("Subshell closed");
  }

  /**
   * Adds the given arguments.
   *
   * @param a
   *          first argument
   * @param b
   *          second argument
   * @return sum of both arguments
   *
   * @author Noqmar
   * @since 0.1.0
   */
  @CommandTarget(name = "mul", description = "multiplies two integers")
  public int mul( //
      final @Param(shortName = 'f', name = "first", description = "first summand <int>)") int a, //
      final @Param(shortName = 's', name = "second", description = "second summand <int>)") int b) {
    LOG.info("Called mul({}, {})", a, b);
    return a * b;
  }
}
