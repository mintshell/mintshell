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
package org.mintshell.terminal;

import org.mintshell.CommandDispatcher;
import org.mintshell.CommandInterpreter;
import org.mintshell.CommandTarget;
import org.mintshell.assertion.Assert;
import org.mintshell.terminal.interfaces.TerminalCommandInterface;

/**
 * A {@link KeyBinding} maps a {@link Key} of an {@link String}, that is marked as 'internal', which means all involved
 * components like {@link CommandInterpreter}, {@link CommandDispatcher} or {@link CommandTarget} may treat them in a
 * special matter.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class KeyBinding {

  private final Key key;
  private final String command;

  /**
   * Creates a new key binding.
   *
   * @param key
   *          key to bind
   * @param command
   *          command to bind to the key
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public KeyBinding(final Key key, final String command) {
    this.key = Assert.ARG.isNotNull(key, "[key] must not be [null]");
    this.command = Assert.ARG.isNotNull(command, "[command] must not be [null]");
  }

  /**
   *
   * @{inheritDoc}
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final KeyBinding other = (KeyBinding) obj;
    if (this.command == null) {
      if (other.command != null) {
        return false;
      }
    }
    else if (!this.command.equals(other.command)) {
      return false;
    }
    if (this.key != other.key) {
      return false;
    }
    return true;
  }

  /**
   * Returns the command string issued from a {@link TerminalCommandInterface} when a bound {@link Key} was detected.
   *
   * @return command string bound to {@link #getKey()}
   */
  public String getCommand() {
    return this.command;
  }

  /**
   * Returns the {@link Key} this {@link KeyBinding} is bound to.
   *
   * @return {@link Key} of this {@link KeyBinding}
   */
  public Key getKey() {
    return this.key;
  }

  /**
   *
   * @{inheritDoc}
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (this.command == null ? 0 : this.command.hashCode());
    result = prime * result + (this.key == null ? 0 : this.key.hashCode());
    return result;
  }
}
