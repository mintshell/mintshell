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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mintshell.annotation.Nullable;
import org.mintshell.command.Command;
import org.mintshell.command.CommandResult;
import org.mintshell.command.DefaultCommandResult;
import org.mintshell.dispatcher.CommandDispatchException;
import org.mintshell.dispatcher.CommandDispatcher;
import org.mintshell.interpreter.CommandInterpreteException;
import org.mintshell.interpreter.CommandInterpreter;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests functionality of {@link BaseCommandInterface}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class BaseCommandInterfaceTest {

  private AtomicInteger preCommandCounter;
  private AtomicInteger postCommandCounter;

  @Mock
  private Command commandMock;

  @Mock
  private CommandDispatcher commandDispatcherMock;

  @Mock
  private CommandInterpreter commandInterpreterMock;

  private BaseCommandInterface sut;

  @Before
  public void before() throws Exception {
    this.preCommandCounter = new AtomicInteger(0);
    this.postCommandCounter = new AtomicInteger(0);
    this.sut = new BaseCommandInterface() {
      @Override
      protected void postCommand(@Nullable final CommandResult<?> result) {
        BaseCommandInterfaceTest.this.postCommandCounter.incrementAndGet();
      }

      @Override
      protected CommandInterfaceCommandResult<?> preCommand(final Command command) {
        BaseCommandInterfaceTest.this.preCommandCounter.incrementAndGet();
        return new CommandInterfaceCommandResult<>(command, Optional.empty(), false);
      }
    };

  }

  @Test
  public void testActivateDeactivate() {
    assertThat(this.sut.isActivated()).isFalse();
    this.sut.activate(this.commandInterpreterMock, this.commandDispatcherMock);
    assertThat(this.sut.isActivated()).isTrue();
    this.sut.deactivate();
    assertThat(this.sut.isActivated()).isFalse();
  }

  @Test(expected = IllegalStateException.class)
  public void testActivateTwiceFails() {
    assertThat(this.sut.isActivated()).isFalse();
    this.sut.activate(this.commandInterpreterMock, this.commandDispatcherMock);
    assertThat(this.sut.isActivated()).isTrue();
    this.sut.activate(this.commandInterpreterMock, this.commandDispatcherMock);
  }

  @Test
  public void testPerformCommandWithDispatchException() throws Exception {

    // prepare
    final String errorMessage = "foobar";
    doReturn(this.commandMock).when(this.commandInterpreterMock).interprete(Mockito.anyString());
    doThrow(new CommandDispatchException(errorMessage)).when(this.commandDispatcherMock).dispatch(this.commandMock);
    assertThat(this.preCommandCounter.get()).isEqualTo(0);
    assertThat(this.postCommandCounter.get()).isEqualTo(0);

    // perform
    this.sut.activate(this.commandInterpreterMock, this.commandDispatcherMock);
    final String result = this.sut.performCommand("foobar");

    // proof
    assertThat(result).isEqualTo(errorMessage);
    assertThat(this.preCommandCounter.get()).isEqualTo(1);
    assertThat(this.postCommandCounter.get()).isEqualTo(1);
  }

  @Test
  public void testPerformCommandWithFailureSuccess() throws Exception {

    // prepare
    final String errorMessage = "foobar";
    final Throwable cause = new IllegalStateException(errorMessage);
    final CommandResult<?> commandResult = new DefaultCommandResult<>(this.commandMock, cause);
    doReturn(this.commandMock).when(this.commandInterpreterMock).interprete(Mockito.anyString());
    doReturn(commandResult).when(this.commandDispatcherMock).dispatch(this.commandMock);
    assertThat(this.preCommandCounter.get()).isEqualTo(0);
    assertThat(this.postCommandCounter.get()).isEqualTo(0);

    // perform
    this.sut.activate(this.commandInterpreterMock, this.commandDispatcherMock);
    final String result = this.sut.performCommand("foobar");

    // proof
    assertThat(result).isEqualTo(errorMessage);
    assertThat(this.preCommandCounter.get()).isEqualTo(1);
    assertThat(this.postCommandCounter.get()).isEqualTo(1);
  }

  @Test
  public void testPerformCommandWithInterpreteException() throws Exception {

    // prepare
    final String errorMessage = "foobar";
    doThrow(new CommandInterpreteException(errorMessage)).when(this.commandInterpreterMock).interprete(Mockito.anyString());
    assertThat(this.preCommandCounter.get()).isEqualTo(0);
    assertThat(this.postCommandCounter.get()).isEqualTo(0);

    // perform
    this.sut.activate(this.commandInterpreterMock, this.commandDispatcherMock);
    final String result = this.sut.performCommand("foobar");

    // proof
    assertThat(result).isEqualTo(errorMessage);
    assertThat(this.preCommandCounter.get()).isEqualTo(0);
    assertThat(this.postCommandCounter.get()).isEqualTo(1);
  }

  @Test
  public void testPerformCommandWithSuccess() throws Exception {

    // prepare
    final String expectedResult = "success";
    final CommandResult<?> commandResult = new DefaultCommandResult<>(this.commandMock, Optional.of(expectedResult));
    doReturn(this.commandMock).when(this.commandInterpreterMock).interprete(Mockito.anyString());
    doReturn(commandResult).when(this.commandDispatcherMock).dispatch(this.commandMock);
    assertThat(this.preCommandCounter.get()).isEqualTo(0);
    assertThat(this.postCommandCounter.get()).isEqualTo(0);

    // perform
    this.sut.activate(this.commandInterpreterMock, this.commandDispatcherMock);
    final String result = this.sut.performCommand("foobar");

    // proof
    assertThat(result).isEqualTo(expectedResult);
    assertThat(this.preCommandCounter.get()).isEqualTo(1);
    assertThat(this.postCommandCounter.get()).isEqualTo(1);
  }

}
