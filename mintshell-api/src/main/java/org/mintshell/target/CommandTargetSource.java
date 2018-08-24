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
package org.mintshell.target;

import org.mintshell.annotation.Nullable;
import org.mintshell.command.Command;

/**
 * A command target source contains the operations (methods) that may be invoked by {@link Command}s. A command target
 * can either be an {@link Object} instance or a {@link Class}. In the first case, {@link Command}s can invoke object
 * methods as well as static methods, in the second case only the static methods are invokable and
 * {@link #getTargetInstance()} returns {@code null}.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public final class CommandTargetSource {

  private final Class<?> targetClass;
  private final Object targetInstance;

  /**
   * Creates a new {@link CommandTarget}.
   *
   * @param commandTarget
   *          {@link Object}-instance or {@link Class} to use as {@link CommandTarget}
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public CommandTargetSource(final Object commandTarget) {
    if (Class.class.isInstance(commandTarget)) {
      this.targetClass = (Class<?>) commandTarget;
      this.targetInstance = null;
    }
    else {
      this.targetClass = commandTarget.getClass();
      this.targetInstance = commandTarget;
    }
  }

  /**
   * Returns the {@link Class} of the contained command target.
   *
   * @return {@link Class} of the contained command target
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public Class<?> getTargetClass() {
    return this.targetClass;
  }

  /**
   * Returns the instance of the command target.
   *
   * @return instance of the command target or {@code null}, if {@link #isInstance()} returns {@code false}
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public @Nullable Object getTargetInstance() {
    return this.targetInstance;
  }

  /**
   * Returns whether the contained command target is an {@link Object} instance.
   *
   * @return {@code true}, if the the contained command target is an {@link Object} instance, {@code false} if it is a
   *         {@link Class}
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public boolean isInstance() {
    return this.targetInstance != null;
  }
}