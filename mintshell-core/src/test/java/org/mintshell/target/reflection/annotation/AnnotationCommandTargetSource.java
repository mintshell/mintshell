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
package org.mintshell.target.reflection.annotation;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.mintshell.annotation.CommandTarget;
import org.mintshell.annotation.Param;
import org.mintshell.target.CommandTargetSource;

/**
 * Testclass to be used as {@link CommandTargetSource} to test the {@link AnnotationCommandShell}.
 *
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class AnnotationCommandTargetSource {

  final Map<String, AtomicInteger> invokations = new HashMap<>();

  public int getInvokationsOf(final String methodName) {
    if (!this.invokations.containsKey(methodName)) {
      this.invokations.put(methodName, new AtomicInteger(0));
    }
    return this.invokations.get(methodName).get();
  }

  @CommandTarget(name = "invokeMe")
  public void m1() {
    this.trace("invokeMe");
  }

  @CommandTarget(name = "invokeMeWithParams")
  public void m2( //
      final @Param(name = "flag", shortName = 'f', required = true, description = "A simple boolean flag") boolean flag, //
      final @Param(name = "number", shortName = 'n', required = false, description = "A number greater than 0") Integer number, //
      final @Param(name = "description", required = false) String description) {
    this.trace(String.format("invokeMeWithParams-%s-%s-%s", flag, number, description));
  }

  public void notAnnotated() {
    this.trace("notAnnotated");
  }

  private void trace(final String methodName) {
    if (!this.invokations.containsKey(methodName)) {
      this.invokations.put(methodName, new AtomicInteger(0));
    }
    this.invokations.get(methodName).incrementAndGet();
  }

}
