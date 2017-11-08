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

import org.mintshell.Mintshell;
import org.mintshell.dispatcher.reflection.ReflectionCommandDispatcher;
import org.mintshell.interpreter.StringTokenCommandInterpreter;
import org.mintshell.terminal.interfaces.AbstractTerminalCommandInterface;
import org.mintshell.terminal.ssh.interfaces.SshCommandInterface;

/**
 * This class demonstrates the usage of a shell using the {@link SshCommandInterface}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class SshTerminalShell {

  public SshTerminalShell(final String[] args) throws Exception {
    Mintshell //
        .from(new SshCommandInterface("Mintshell> ", "Welcome to Mintshell with SSH\r\n", AbstractTerminalCommandInterface.KEYBINDING_EXIT)) //
        .interpretedBy(new StringTokenCommandInterpreter()) //
        .dispatchedBy(new ReflectionCommandDispatcher()) //
        .to(new SimpleCommandTarget()) //
        .apply();
    Thread.sleep(Long.MAX_VALUE);
  }

  public static void main(final String[] args) throws Exception {
    new SshTerminalShell(args);
  }
}
