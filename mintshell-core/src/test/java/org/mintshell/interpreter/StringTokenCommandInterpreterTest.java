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
package org.mintshell.interpreter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.mintshell.command.Command;

/**
 * Tests functionality of {@link StringTokenCommandInterpreter}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class StringTokenCommandInterpreterTest {

  @Test
  public void testInterprete() throws CommandInterpreteException {
    final StringTokenCommandInterpreter sut = new StringTokenCommandInterpreter();
    final Command result = sut.interprete("foo b a r");
    assertThat(result.getName()).isEqualTo("foo");
    assertThat(result.getParameters().size()).isEqualTo(3);
    assertThat(result.getParameters().get(0).getIndex()).isEqualTo(0);
    assertThat(result.getParameters().get(0).getName()).isEmpty();
    assertThat(result.getParameters().get(0).getShortName()).isEmpty();
    assertThat(result.getParameters().get(0).getValue().get()).isEqualTo("b");

    assertThat(result.getParameters().get(1).getIndex()).isEqualTo(1);
    assertThat(result.getParameters().get(1).getName()).isEmpty();
    assertThat(result.getParameters().get(1).getShortName()).isEmpty();
    assertThat(result.getParameters().get(1).getValue().get()).isEqualTo("a");

    assertThat(result.getParameters().get(2).getIndex()).isEqualTo(2);
    assertThat(result.getParameters().get(2).getName()).isEmpty();
    assertThat(result.getParameters().get(2).getShortName()).isEmpty();
    assertThat(result.getParameters().get(2).getValue().get()).isEqualTo("r");
  }

  @Test(expected = CommandInterpreteException.class)
  public void testInterpreteEmpty() throws CommandInterpreteException {
    final StringTokenCommandInterpreter sut = new StringTokenCommandInterpreter();
    sut.interprete("");
  }

  @Test(expected = CommandInterpreteException.class)
  public void testInterpreteNull() throws CommandInterpreteException {
    final StringTokenCommandInterpreter sut = new StringTokenCommandInterpreter();
    sut.interprete(null);
  }

}
