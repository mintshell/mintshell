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
package org.mintshell.examples.targets;

import org.mintshell.annotation.CommandShell;
import org.mintshell.annotation.CommandTarget;
import org.mintshell.annotation.Param;
import org.mintshell.target.CommandShellList;
import org.mintshell.target.reflection.annotation.AnnotationCommandShellList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple class that can be used as instance command target providing nothing more than (annotated) methods to grab
 * static system information.
 *
 * @author Noqmar
 * @since 0.1.0
 */
@CommandShell(prompt = "Main", exitCommands = { "exit" }, exitCommandDescription = "exits the main shell", exitMessage = "Closing ssh session")
public class AnnotationMainCommandShell extends BaseShell {

  private static final Logger LOG = LoggerFactory.getLogger(SimpleCommandTarget.class);

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
  @CommandTarget(name = "add", description = "adds two integers")
  public int add( //
      final @Param(shortName = 'f', name = "first", description = "first summand <int>)") int a, //
      final @Param(shortName = 's', name = "second", description = "second summand <int>)") int b) {
    LOG.info("Called add({}, {})", a, b);
    return a + b;
  }

  @CommandTarget(name = "leafshella", description = "opens the shell path to the leaf shell")
  public BaseShell[] leafshellArray() {
    return new BaseShell[] { new AnnotationSubCommandShell(), new AnnotationLeafCommandShell() };
  }

  @CommandTarget(name = "leafshelll", description = "opens the shell path to the leaf shell")
  public CommandShellList<?> leafshellList() {
    return new AnnotationCommandShellList("Welcome to the instant leaf shell", new AnnotationSubCommandShell(), new AnnotationLeafCommandShell());
  }

  /**
   * Returns the amount of total memory bytes.
   *
   * @return amount of total memory bytes
   *
   * @author Noqmar
   * @since 0.1.0
   */
  @CommandTarget(name = "mem", description = "prints the amount of total memory in bytes")
  public long mem() {
    LOG.info("Called mem()");
    final Runtime runtime = Runtime.getRuntime();
    return runtime.totalMemory();
  }

  /**
   * Returns the current amount of free memory bytes.
   *
   * @return current amount of free memory bytes
   *
   * @author Noqmar
   * @since 0.1.0
   */
  @CommandTarget(name = "memfree", description = "prints the amount of free memory in bytes")
  public long memfree() {
    LOG.info("Called memfree()");
    final Runtime runtime = Runtime.getRuntime();
    return runtime.freeMemory();
  }

  /**
   * Returns the current amount of used memory bytes.
   *
   * @return current amount of used memory bytes
   *
   * @author Noqmar
   * @since 0.1.0
   */
  @CommandTarget(name = "memused", description = "prints the amount of used memory in bytes")
  public long memused() {
    LOG.info("Called memused()");
    final Runtime runtime = Runtime.getRuntime();
    return runtime.totalMemory() - runtime.freeMemory();
  }

  /**
   * Substracts the given arguments.
   *
   * @param a
   *          first argument
   * @param b
   *          second argument
   * @return difference of both arguments
   *
   * @author Noqmar
   * @since 0.1.0
   */
  @CommandTarget(name = "sub")
  public int sub( //
      final @Param int a, //
      final @Param int b) {
    LOG.info("Called add({}, {})", a, b);
    return a - b;
  }

  @CommandTarget(name = "subshell", description = "opens the sub shell")
  public AnnotationSubCommandShell subshell() {
    return new AnnotationSubCommandShell();
  }
}
