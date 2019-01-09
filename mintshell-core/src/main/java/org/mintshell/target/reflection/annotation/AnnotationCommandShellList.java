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
package org.mintshell.target.reflection.annotation;

import java.util.Arrays;
import java.util.Collection;

import org.mintshell.assertion.Assert;
import org.mintshell.target.CommandShell;
import org.mintshell.target.CommandShellList;
import org.mintshell.target.CommandTargetSource;
import org.mintshell.target.DefaultCommandShellList;

/**
 * Implementation of a {@link CommandShellList}, that provides two methods {@link #addAnnotated(Object)} and
 * {@link #setAnnotated(int, Object)} for objects, that are annotated with the
 * {@link org.mintshell.annotation.CommandShell} annotation.
 *
 *
 * @author Noqmar
 * @since 0.3.0
 */
public class AnnotationCommandShellList extends DefaultCommandShellList<AnnotationCommandShell> {

  private static final long serialVersionUID = -7259178729787939341L;

  /**
   * Constructs an empty list with an initial capacity of ten.
   *
   * @param resultMessage
   *          result message text
   * @param shells
   *          annotated shell instances
   *
   * @author Noqmar
   * @since 0.3.0
   */
  public AnnotationCommandShellList(final String resultMessage, final Object... shells) {
    super(resultMessage);
    Arrays.stream(shells).forEach(this::addAnnotated);
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see java.util.ArrayList#add(java.lang.Object)
   */
  @Override
  public boolean add(final AnnotationCommandShell e) {
    return super.add(e);
  }

  /**
   * Adaption of {@link #add(AnnotationCommandShell)} for non-{@link CommandShell} instances.
   *
   * @param element
   *          element to be appended to this list
   * @return {@code true} (as specified by {@link Collection#add})
   *
   * @author Noqmar
   * @since 0.3.0
   */
  public boolean addAnnotated(final Object element) {
    return super.add(checkAndConvert(element));
  }

  /**
   *
   * {@inheritDoc}
   * 
   * @see java.util.ArrayList#set(int, java.lang.Object)
   */
  @Override
  public AnnotationCommandShell set(final int index, final AnnotationCommandShell element) {
    return super.set(index, element);
  }

  /**
   * Adaption of {@link #set(int, AnnotationCommandShell)} for non-{@link AnnotationCommandShell} instances.
   *
   * @param index
   *          index of the element to replace
   * @param element
   *          element to be stored at the specified position
   * @return the element previously at the specified position
   * @throws IndexOutOfBoundsException
   *           {@inheritDoc}
   *
   * @author Noqmar
   * @since 0.3.0
   */
  public CommandShell setAnnotated(final int index, final Object element) {
    return super.set(index, checkAndConvert(element));
  }

  private static AnnotationCommandShell checkAndConvert(final Object commandShell) {
    Assert.ARG.isNotNull(commandShell, "[commandShell] must not be [null]");
    if (commandShell instanceof AnnotationCommandShell) {
      return (AnnotationCommandShell) commandShell;
    }
    final org.mintshell.annotation.CommandShell shellAnnotation = commandShell.getClass().getAnnotation(org.mintshell.annotation.CommandShell.class);
    Assert.ARG.isNotNull(commandShell, "[shellAnnotation] must not be [null]");
    return new AnnotationCommandShell(shellAnnotation, new CommandTargetSource(commandShell));
  }
}
