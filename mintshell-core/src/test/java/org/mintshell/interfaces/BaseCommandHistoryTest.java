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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests functionality of {@link BaseCommandHistory}.
 *
 *
 * @author Noqmar
 * @since 0.2.0
 */
public class BaseCommandHistoryTest {

  private static final int MAX_COMMANDS = 3;

  private BaseCommandHistory sut;

  @Before
  public void before() {
    this.sut = new BaseCommandHistory(MAX_COMMANDS) {
    };
  }

  @Test
  public void testAddCommand() {
    final String commandLine = "first";
    this.sut.addCommandLine(commandLine);
    assertThat(this.sut.getFirstCommandLineNumber()).isEqualTo(1);
    assertThat(this.sut.getLastCommandLineNumber()).isEqualTo(1);
    assertThat(this.sut.getNextCommandLine()).isEmpty();
    assertThat(this.sut.getPreviousCommandLine()).isEqualTo(commandLine);
    assertThat(this.sut.getCommandLines()).hasSize(1);
  }

  @Test
  public void testInit() {
    assertThat(this.sut.getFirstCommandLineNumber()).isEqualTo(0);
    assertThat(this.sut.getLastCommandLineNumber()).isEqualTo(0);
    assertThat(this.sut.getNextCommandLine()).isEmpty();
    assertThat(this.sut.getPreviousCommandLine()).isEmpty();
    assertThat(this.sut.getCommandLines()).isEmpty();
  }
}
