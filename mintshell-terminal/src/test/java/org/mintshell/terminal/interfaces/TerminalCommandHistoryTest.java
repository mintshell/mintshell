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
package org.mintshell.terminal.interfaces;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mintshell.common.FileIoProvider;

/**
 * Tests the functionality of {@link TerminalCommandHistory}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class TerminalCommandHistoryTest {

  private File file;
  private TerminalCommandHistory sut;

  @After
  public void after() throws IOException {
    Files.delete(this.file.toPath());
  }

  @Before
  public void before() throws IOException {
    this.file = Files.createTempFile(UUID.randomUUID().toString(), "tmp").toFile();
    this.sut = new TerminalCommandHistory(new FileIoProvider(this.file));
  }

  @Test
  public void testAddCommandLine() {
    final String commandLine = "foobar";
    assertThat(this.sut.getCommandLines()).isEmpty();
    assertThat(this.sut.getPreviousCommandLine()).isEmpty();
    this.sut.addCommandLine(commandLine);
    assertThat(this.sut.getCommandLines()).hasSize(1);
    assertThat(this.sut.getCommandLines().get(TerminalCommandHistory.FIRST_COMMAND_NUMBER)).isEqualTo(commandLine);
    assertThat(this.sut.getPreviousCommandLine()).isEqualTo(commandLine);
  }

  @Test
  public void testClear() {
    final String commandLine = "foobar";
    assertThat(this.sut.getCommandLines()).isEmpty();
    this.sut.addCommandLine(commandLine);
    assertThat(this.sut.getCommandLines()).hasSize(1);
    this.sut.clear();
    assertThat(this.sut.getCommandLines()).isEmpty();
    this.sut.addCommandLine(commandLine);
    assertThat(this.sut.getPreviousCommandLine()).isEqualTo(commandLine);
  }

  @Test
  public void testGetDedicatedCommandLine() {
    final String commandLine1 = "foobar";
    final String commandLine2 = "foo";
    final String commandLine3 = "foobar";
    assertThat(this.sut.getCommandLines()).isEmpty();
    this.sut.addCommandLine(commandLine1);
    this.sut.addCommandLine(commandLine2);
    this.sut.addCommandLine(commandLine3);
    assertThat(this.sut.getCommandLines()).hasSize(3);
    assertThat(this.sut.getCommandLine(2).get()).isEqualTo(commandLine2);
  }

  @Test
  public void testNavigation1() {
    final String commandLine1 = "foobar1";
    final String commandLine2 = "foobar2";
    final String commandLine3 = "foobar3";
    this.sut.addCommandLine(commandLine1);
    this.sut.addCommandLine(commandLine2);
    this.sut.addCommandLine(commandLine3);
    assertThat(this.sut.getCommandLines()).hasSize(3);
    assertThat(this.sut.getPreviousCommandLine()).isEqualTo(commandLine3);
    assertThat(this.sut.getPreviousCommandLine()).isEqualTo(commandLine2);
    assertThat(this.sut.getPreviousCommandLine()).isEqualTo(commandLine1);
    assertThat(this.sut.getPreviousCommandLine()).isEqualTo(commandLine1);
    assertThat(this.sut.getNextCommandLine()).isEqualTo(commandLine2);
    assertThat(this.sut.getNextCommandLine()).isEqualTo(commandLine3);
    assertThat(this.sut.getNextCommandLine()).isEqualTo("");
  }

  @Test
  public void testNavigation2() {
    final String commandLine1 = "foobar1";
    final String commandLine2 = "foobar2";
    this.sut.addCommandLine(commandLine1);
    this.sut.addCommandLine(commandLine2);
    assertThat(this.sut.getCommandLines()).hasSize(2);
    assertThat(this.sut.getPreviousCommandLine()).isEqualTo(commandLine2);
    assertThat(this.sut.getPreviousCommandLine()).isEqualTo(commandLine1);
    assertThat(this.sut.getNextCommandLine()).isEqualTo(commandLine2);
    assertThat(this.sut.getNextCommandLine()).isEqualTo("");
    assertThat(this.sut.getPreviousCommandLine()).isEqualTo(commandLine2);
  }

  @Test
  public void testNavigation3() {
    final String commandLine1 = "foobar1";
    final String commandLine2 = "foobar2";
    this.sut.addCommandLine(commandLine1);
    this.sut.addCommandLine(commandLine2);
    assertThat(this.sut.getCommandLines()).hasSize(2);
    assertThat(this.sut.getPreviousCommandLine()).isEqualTo(commandLine2);
    assertThat(this.sut.getPreviousCommandLine()).isEqualTo(commandLine1);
    assertThat(this.sut.getNextCommandLine()).isEqualTo(commandLine2);
    assertThat(this.sut.getNextCommandLine()).isEqualTo("");
    assertThat(this.sut.getPreviousCommandLine()).isEqualTo(commandLine2);
    assertThat(this.sut.getNextCommandLine()).isEqualTo("");
  }
}
