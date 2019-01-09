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
package org.mintshell.examples.targets;

import org.mintshell.annotation.CommandShell;
import org.mintshell.annotation.CommandTarget;
import org.mintshell.target.CommandShellExitException;

/**
 * Simple class that can be used as instance command target providing nothing more than (annotated) methods to grab
 * static system information.
 *
 * @author Noqmar
 * @since 0.1.0
 */
@CommandShell(prompt = "Leaf", promptPathSeparator = "/", enterMessage = "Welcome to the leaf shell")
public class AnnotationLeafCommandShell extends BaseShell {

  @CommandTarget(name = "exit", description = "exits the leafshell")
  public void exit() {
    throw CommandShellExitException.createExit("Leafshell closed");
  }

  @CommandTarget(name = "exitall", description = "exits to the main shell")
  public void exitall() {
    throw CommandShellExitException.createExitAll("Subshells closed");
  }
}
