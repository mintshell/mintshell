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
package org.mintshell.target.reflection.annotation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mintshell.annotation.Param;
import org.mintshell.command.Command;
import org.mintshell.command.CommandBuilder;
import org.mintshell.command.CommandParameterBuilder;
import org.mintshell.dispatcher.CommandDispatchException;
import org.mintshell.dispatcher.DefaultCommandDispatcher;
import org.mintshell.target.CommandTargetSource;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests functionality of {@link AnnotationCommandShell}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AnnotationCommandShellTest {

  private CommandTargetSource instanceTargetMock;

  private AnnotationCommandTargetSource annotationCommandTarget;

  private AnnotationCommandShell sut;

  @After
  public void afterClass() {
    this.annotationCommandTarget.invokations.forEach((k, v) -> System.out.println(k + ":" + v));
  }

  @Before
  public void before() {
    this.annotationCommandTarget = new AnnotationCommandTargetSource();
    this.sut = new AnnotationCommandShell("");
    this.instanceTargetMock = new CommandTargetSource(this.annotationCommandTarget);
  }

  @Test
  public void testAddCommandTargetWithAnnotatedMethodButNotAnnotatedParam() throws Exception {
    assertThat(this.sut.getTargets().size()).isZero();
    final Runnable runnable = new Runnable() {
      @Override
      public void run() {
      }

      @org.mintshell.annotation.CommandTarget("test")
      public void test(@Param(name = "a") final int a, final int b) {

      }
    };

    this.sut.addCommandTargetSources(new CommandTargetSource(runnable));
    assertThat(this.sut.getTargets().size()).isZero();
  }

  @Test(expected = CommandDispatchException.class)
  public void testExistingButNotAnnotatedMethod() throws Exception {
    this.sut.addCommandTargetSources(this.instanceTargetMock);
    final Command command = CommandBuilder.create("notAnnotated").build();
    new DefaultCommandDispatcher(this.sut).dispatch(command);
  }

  @Test
  public void testPublicVoidParamless() throws Exception {
    this.sut.addCommandTargetSources(this.instanceTargetMock);
    final Command command = CommandBuilder.create("invokeMe").build();
    assertThat(this.annotationCommandTarget.getInvokationsOf(command.getName())).isZero();
    new DefaultCommandDispatcher(this.sut).dispatch(command);
    assertThat(this.annotationCommandTarget.getInvokationsOf(command.getName())).isOne();
  }

  @Test
  public void testPublicVoidWithParams() throws Exception {
    this.sut.addCommandTargetSources(this.instanceTargetMock);
    final Command command = CommandBuilder.create("invokeMeWithParams") //
        .withParameter(CommandParameterBuilder.create(0).withName("number").withValue("42").build()) //
        .withParameter(CommandParameterBuilder.create(1).withShortName('f').withValue("false").build()) //
        .withParameter(CommandParameterBuilder.create(2).withValue("some text").build()) //
        .build();
    final String expectedInvocation = "invokeMeWithParams-false-42-some text";
    assertThat(this.annotationCommandTarget.getInvokationsOf(expectedInvocation)).isZero();
    new DefaultCommandDispatcher(this.sut).dispatch(command);
    assertThat(this.annotationCommandTarget.getInvokationsOf(expectedInvocation)).isOne();
  }
}
