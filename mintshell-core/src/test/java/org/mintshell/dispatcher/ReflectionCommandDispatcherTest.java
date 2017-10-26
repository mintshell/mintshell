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
package org.mintshell.dispatcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mintshell.CommandTarget;
import org.mintshell.command.Command;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReflectionCommandDispatcherTest {

  @Mock
  private CommandTarget instanceTargetMock;

  private PublicCommandTarget publicCommandTarget;

  @Mock
  private CommandTarget classTargetMock;

  private ReflectionCommandDispatcher sut;

  @Before
  public void before() {
    this.publicCommandTarget = new PublicCommandTarget();
    this.sut = new ReflectionCommandDispatcher();

    when(this.instanceTargetMock.isInstance()).thenReturn(true);
    doReturn(this.publicCommandTarget).when(this.instanceTargetMock).getTargetInstance();
    doReturn(PublicCommandTarget.class).when(this.instanceTargetMock).getTargetClass();

    when(this.classTargetMock.isInstance()).thenReturn(false);
    doReturn(null).when(this.classTargetMock).getTargetInstance();
    doReturn(PublicCommandTarget.class).when(this.classTargetMock).getTargetClass();
  }

  @Test
  public void testPackagePrivateVoidParamless() throws Exception {
    this.sut.addCommandTargets(this.instanceTargetMock);
    final Command command = new Command("invokeMePackagePrivate");
    assertThat(this.publicCommandTarget.getInvokationsOf(command.getName())).isZero();
    this.sut.dispatch(command);
    assertThat(this.publicCommandTarget.getInvokationsOf(command.getName())).isOne();
  }

  @Test
  public void testPrivateVoidParamless() throws Exception {
    this.sut.addCommandTargets(this.instanceTargetMock);
    final Command command = new Command("invokeMePrivate");
    assertThat(this.publicCommandTarget.getInvokationsOf(command.getName())).isZero();
    this.sut.dispatch(command);
    assertThat(this.publicCommandTarget.getInvokationsOf(command.getName())).isOne();
  }

  @Test
  public void testProtectedVoidParamless() throws Exception {
    this.sut.addCommandTargets(this.instanceTargetMock);
    final Command command = new Command("invokeMeProtected");
    assertThat(this.publicCommandTarget.getInvokationsOf(command.getName())).isZero();
    this.sut.dispatch(command);
    assertThat(this.publicCommandTarget.getInvokationsOf(command.getName())).isOne();
  }

  @Test
  public void testPublicVoidParamless() throws Exception {
    this.sut.addCommandTargets(this.instanceTargetMock);
    final Command command = new Command("invokeMePublic");
    assertThat(this.publicCommandTarget.getInvokationsOf(command.getName())).isZero();
    this.sut.dispatch(command);
    assertThat(this.publicCommandTarget.getInvokationsOf(command.getName())).isOne();
  }

}
