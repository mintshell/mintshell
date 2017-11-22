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
package org.mintshell.dispatcher.annotation;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.mintshell.CommandTarget;
import org.mintshell.annotation.Command;

/**
 * Testclass to be used as {@link CommandTarget} to test the {@link AnnotationCommandDispatcher}.
 *
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class AnnotationCommandTarget {

  private final Map<String, AtomicInteger> invokations = new HashMap<>();

  public int getInvokationsOf(final String methodName) {
    if (!this.invokations.containsKey(methodName)) {
      this.invokations.put(methodName, new AtomicInteger(0));
    }
    return this.invokations.get(methodName).get();
  }

  @Command(name = "invokeMe")
  public void m1() {
    this.trace("invokeMe");
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
