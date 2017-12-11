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
package org.mintshell.examples.terminal.ssh;

import org.mintshell.annotation.Command;
import org.mintshell.annotation.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple class that can be used as instance command target providing nothing more than (annotated) methods to grab
 * static system information.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class AnnotationCommandTarget {

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
  @Command("add")
  public int add( //
      final @Param(shortName = 'a') int a, //
      final @Param(shortName = 'b') int b) {
    LOG.info("Called add({}, {})", a, b);
    return a + b;
  }

  @Command("exit")
  public void exit() {
    LOG.info("Called exit()");
  }

  /**
   * Returns the amount of total memory bytes.
   *
   * @return amount of total memory bytes
   *
   * @author Noqmar
   * @since 0.1.0
   */
  @Command("mem")
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
  @Command("memfree")
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
  @Command("memused")
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
  @Command("sub")
  public int sub( //
      final @Param(shortName = 'a') int a, //
      final @Param(shortName = 'b') int b) {
    LOG.info("Called add({}, {})", a, b);
    return a - b;
  }
}
