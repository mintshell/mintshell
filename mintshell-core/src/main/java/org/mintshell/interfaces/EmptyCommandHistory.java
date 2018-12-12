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
package org.mintshell.interfaces;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.mintshell.annotation.Nullable;

/**
 * Implementation of a {@link CommandHistory} that is always empty.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public final class EmptyCommandHistory implements CommandHistory {

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandHistory#addCommandLine(java.lang.String)
   */
  @Override
  public void addCommandLine(final String commandLine) {
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandHistory#clear()
   */
  @Override
  public void clear() {
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandHistory#getCommandLine(int)
   */
  @Override
  public Optional<String> getCommandLine(final int number) {
    return Optional.empty();
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandHistory#getCommandLines()
   */
  @Override
  public Map<Integer, String> getCommandLines() {
    return Collections.emptyMap();
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandHistory#getFirstCommandLineNumber()
   */
  @Override
  public int getFirstCommandLineNumber() {
    return 0;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandHistory#getLastCommandLineNumber()
   */
  @Override
  public int getLastCommandLineNumber() {
    return 0;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandHistory#getMaxCommands()
   */
  @Override
  public int getMaxCommands() {
    return 0;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandHistory#getNextCommandLine()
   */
  @Override
  public @Nullable String getNextCommandLine() {
    return null;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.CommandHistory#getPreviousCommandLine()
   */
  @Override
  public @Nullable String getPreviousCommandLine() {
    return null;
  }
}
