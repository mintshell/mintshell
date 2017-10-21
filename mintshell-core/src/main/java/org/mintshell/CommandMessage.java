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
 * A {@link CommandMessage} unites a content (the command) with it's {@link CommandContext}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public final class CommandMessage<T> {

  private final CommandContext commandContext;
  private final T content;

  public CommandMessage(final CommandContext commandContext, final T content) {
    assert commandContext != null : "The given [commandContext] must not be [null]!";
    this.commandContext = commandContext;
    assert content != null : "The given [content] must not be [null]!";
    this.content = content;
  }

  /**
   * Returns the message content.
   *
   * @return message content
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public T getContent() {
    return this.content;
  }

  /**
   * Returns the {@link CommandContext} the message content belongs to.
   *
   * @return {@link CommandContext} the message content belongs to
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public CommandContext getContext() {
    return this.commandContext;
  }
}
