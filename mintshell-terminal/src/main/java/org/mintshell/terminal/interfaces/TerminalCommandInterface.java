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
package org.mintshell.terminal.interfaces;

import java.util.Collection;

import org.mintshell.CommandInterface;
import org.mintshell.terminal.Key;
import org.mintshell.terminal.KeyBinding;

/**
 * A special kind of {@link CommandInterface} that uses underlying terminal to receive commands.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public abstract interface TerminalCommandInterface extends CommandInterface {

  /**
   * Adds {@link KeyBinding}s to the terminal interface.
   *
   * @param keyBindings
   *          {@link KeyBinding}s to add
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract void addKeyBindings(KeyBinding... keyBindings);

  /**
   * Clears all {@link KeyBinding}s.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract void clearKeyBindings();

  /**
   * Returns the active {@link KeyBinding}s
   *
   * @return active {@link KeyBinding}s
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract Collection<KeyBinding> getKeyBindings();

  /**
   * Displays the given text to the screen of the underlying terminal.
   *
   * @param text
   *          text to display
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract void print(String text);

  /**
   * Displays the given text followed by a newline/carriage return to the screen of the underlying terminal.
   *
   * @param text
   *          text to display
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract void println(String text);

  /**
   * Reads a {@link Key} from the underlying terminal. This method blocks, until a {@link Key} was read.
   *
   * @return read {@link Key}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract Key readKey();

  /**
   * Removes the given {@link KeyBinding} from the managed {@link KeyBinding}s.
   *
   * @param keyBinding
   *          {@link KeyBinding} to remove
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public abstract void removeKeyBinding(KeyBinding keyBinding);
}
