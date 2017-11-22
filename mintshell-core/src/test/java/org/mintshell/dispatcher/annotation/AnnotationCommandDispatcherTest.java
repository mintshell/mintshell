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
package org.mintshell.dispatcher.annotation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mintshell.CommandDispatchException;
import org.mintshell.CommandTarget;
import org.mintshell.command.Command;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests functionality of {@link AnnotationCommandDispatcher}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AnnotationCommandDispatcherTest {

  @Mock
  private CommandTarget instanceTargetMock;

  private AnnotationCommandTarget annotationCommandTarget;

  @Mock
  private CommandTarget classTargetMock;

  private AnnotationCommandDispatcher sut;

  @Before
  public void before() {
    this.annotationCommandTarget = new AnnotationCommandTarget();
    this.sut = new AnnotationCommandDispatcher();

    when(this.instanceTargetMock.isInstance()).thenReturn(true);
    doReturn(this.annotationCommandTarget).when(this.instanceTargetMock).getTargetInstance();
    doReturn(AnnotationCommandTarget.class).when(this.instanceTargetMock).getTargetClass();

    when(this.classTargetMock.isInstance()).thenReturn(false);
    doReturn(null).when(this.classTargetMock).getTargetInstance();
    doReturn(AnnotationCommandTarget.class).when(this.classTargetMock).getTargetClass();
  }

  @Test(expected = CommandDispatchException.class)
  public void testExistingButNotAnnotatedMethod() throws Exception {
    this.sut.addCommandTargets(this.instanceTargetMock);
    final Command<?> command = new Command<>("notAnnotated");
    this.sut.dispatch(command);
  }

  @Test
  public void testPublicVoidParamless() throws Exception {
    this.sut.addCommandTargets(this.instanceTargetMock);
    final Command<?> command = new Command<>("invokeMe");
    assertThat(this.annotationCommandTarget.getInvokationsOf(command.getName())).isZero();
    this.sut.dispatch(command);
    assertThat(this.annotationCommandTarget.getInvokationsOf(command.getName())).isOne();
  }
}