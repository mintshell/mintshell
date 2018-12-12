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

import org.junit.Test;
import org.mintshell.target.reflection.StaticStringConstructionMethodParameter;
import org.mintshell.target.reflection.UnsupportedParameterTypeException;

/**
 * Tests the functionality of {@link StaticStringConstructionMethodParameter}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class StaticStringConstructionMethodParameterTest {

  @Test
  public void testIntegerObject() throws Exception {
    final String value = "42";
    final Class<?> type = Integer.class;
    assertThat(new StaticStringConstructionMethodParameter(type, 0).of(value)).isEqualTo(Integer.valueOf(42));
  }

  @Test(expected = UnsupportedParameterTypeException.class)
  public void testIntegerPrimitive() throws Exception {
    final String value = "42";
    final Class<?> type = int.class;
    new StaticStringConstructionMethodParameter(type, 0).of(value);
  }

  @Test
  public void testNull() throws Exception {
    final String value = null;
    final Class<?> type = Integer.class;
    assertThat(new StaticStringConstructionMethodParameter(type, 0).of(value)).isNull();
  }

}
