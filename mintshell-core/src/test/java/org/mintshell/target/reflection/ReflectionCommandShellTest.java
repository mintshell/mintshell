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
package org.mintshell.target.reflection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mintshell.command.Command;
import org.mintshell.command.CommandBuilder;
import org.mintshell.command.CommandParameterBuilder;
import org.mintshell.dispatcher.CommandDispatchException;
import org.mintshell.dispatcher.DefaultCommandDispatcher;
import org.mintshell.target.CommandTargetSource;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests the functionality of {@link ReflectionCommandShell}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class ReflectionCommandShellTest {

  private CommandTargetSource instanceCommandTargetSourceMock;

  private PublicCommandTarget publicCommandTarget;

  private ReflectionCommandShell sut;

  @Before
  public void before() {
    this.publicCommandTarget = new PublicCommandTarget();
    this.sut = new ReflectionCommandShell("");
    this.instanceCommandTargetSourceMock = new CommandTargetSource(this.publicCommandTarget);
  }

  @Test(expected = CommandDispatchException.class)
  public void testPackagePrivateVoidParamless() throws Exception {
    this.sut.addCommandTargetSources(this.instanceCommandTargetSourceMock);
    final Command command = CommandBuilder.create("invokeMePackagePrivate").build();
    new DefaultCommandDispatcher(this.sut).dispatch(command);
    assertThat(this.publicCommandTarget.getInvokationsOf(command.getName())).isZero();
  }

  @Test(expected = CommandDispatchException.class)
  public void testPrivateVoidParamless() throws Exception {
    this.sut.addCommandTargetSources(this.instanceCommandTargetSourceMock);
    final Command command = CommandBuilder.create("invokeMePrivate").build();
    new DefaultCommandDispatcher(this.sut).dispatch(command);
    assertThat(this.publicCommandTarget.getInvokationsOf(command.getName())).isZero();
  }

  @Test(expected = CommandDispatchException.class)
  public void testProtectedVoidParamless() throws Exception {
    this.sut.addCommandTargetSources(this.instanceCommandTargetSourceMock);
    final Command command = CommandBuilder.create("invokeMeProtected").build();
    new DefaultCommandDispatcher(this.sut).dispatch(command);
    assertThat(this.publicCommandTarget.getInvokationsOf(command.getName())).isZero();
  }

  @Test
  public void testPublicVoidParamless() throws Exception {
    this.sut.addCommandTargetSources(this.instanceCommandTargetSourceMock);
    final Command command = CommandBuilder.create("invokeMePublic").build();
    assertThat(this.publicCommandTarget.getInvokationsOf(command.getName())).isZero();
    new DefaultCommandDispatcher(this.sut).dispatch(command);
    assertThat(this.publicCommandTarget.getInvokationsOf(command.getName())).isOne();
  }

  @Test
  public void testPublicVoidWithPrimitive() throws Exception {
    this.sut.addCommandTargetSources(this.instanceCommandTargetSourceMock);
    final Command command = CommandBuilder.create("invokeMePublicWithPrimitive").withParameter(CommandParameterBuilder.create(0).withValue("1").build())
        .build();
    assertThat(this.publicCommandTarget.getInvokationsOf(command.getName())).isZero();
    new DefaultCommandDispatcher(this.sut).dispatch(command);
    assertThat(this.publicCommandTarget.getInvokationsOf(command.getName())).isOne();
  }

  @Test(expected = CommandDispatchException.class)
  public void testPublicVoidWithPrimitiveNotProvidingParam() throws Exception {
    this.sut.addCommandTargetSources(this.instanceCommandTargetSourceMock);
    final Command command = CommandBuilder.create("invokeMePublicWithPrimitive").build();
    new DefaultCommandDispatcher(this.sut).dispatch(command);
  }
}
