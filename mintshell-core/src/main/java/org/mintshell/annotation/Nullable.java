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
package org.mintshell.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Qualifier for a reference type in a {@link ElementType#TYPE_USE TYPE_USE} position: The type that has this annotation
 * is explicitly intended to include the value {@code null} at runtime.
 * </p>
 *
 * <p>
 * If annotation based {@code null} analysis is enabled, using this annotation has two consequences:
 * <ol>
 * <li>Binding a {@code null} value to an entity (field, local variable, method parameter, or method return value) of
 * this type is legal.</li>
 * <li>Dereferencing an expression of this type is unsafe, i.&thinsp;e., a {@code NullPointerException} can occur at
 * runtime.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Note, some developer environment might come along with some annotation based {@code null} analysis.
 * <dl>
 * <dt><a href="http://eclipse.org">Eclipse</a></dt>
 * <dd>Eclipse provides settings under:<br/>
 * <em>Window</em>&rarr;<em>Preferences</em>&rarr;<em>Java</em>&rarr;<em>Compiler</em>&rarr;<em>Errors/Warnings</em> in
 * section <em>Null analysis</em></dd>
 * </dl>
 * </p>
 *
 * @author Noqmar
 * @since 0.1.0
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE_USE)
public @interface Nullable {
  // marker annotation with no members
}