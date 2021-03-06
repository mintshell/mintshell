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
package org.mintshell.examples.terminal.ncurses;

import java.io.File;

import org.mintshell.Mintshell;
import org.mintshell.common.FileIoProvider;
import org.mintshell.examples.targets.AnnotationMainCommandShell;
import org.mintshell.mcl.interpreter.MclCommandInterpreter;
import org.mintshell.terminal.interfaces.TerminalCommandHistory;
import org.mintshell.terminal.ncurses.interfaces.NCursesTerminalCommandInterface;

/**
 * This class demonstrates the usage of a shell using the {@link NCursesTerminalCommandInterface}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class NCursesTerminalShell {

  public NCursesTerminalShell(final String[] args) throws Exception {

    final NCursesTerminalCommandInterface commandInterface = new NCursesTerminalCommandInterface(
        new TerminalCommandHistory(new FileIoProvider(new File("command.history"))), "Welcome to Mintshell with nCurses\n\r",
        NCursesTerminalCommandInterface.KEYBINDING_EXIT);

    Mintshell //
        .from(commandInterface) //
        .with(new MclCommandInterpreter()) //
        .to(new AnnotationMainCommandShell()) //
        .apply();
    Thread.sleep(Long.MAX_VALUE);
  }

  public static void main(final String[] args) throws Exception {
    new NCursesTerminalShell(args);
  }
}
