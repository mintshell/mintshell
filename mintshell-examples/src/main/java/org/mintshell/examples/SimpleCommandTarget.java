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
package org.mintshell.examples;

/**
 * Simple class that can be used as instance command target providing nothing more that (not annotated) methods to grab
 * static system information.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class SimpleCommandTarget {

  public void exit() {
    System.exit(0);
  }

  /**
   * Returns the amount of total memory bytes.
   *
   * @return amount of total memory bytes
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public long mem() {
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
  public long memfree() {
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
  public long memused() {
    final Runtime runtime = Runtime.getRuntime();
    return runtime.totalMemory() - runtime.freeMemory();
  }
}