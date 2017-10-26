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
package org.mintshell.command.parameter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.mintshell.command.UnsupportedParameterTypeException;

/**
 * Tests the functionality of {@link PrimitiveParameter}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class PrimitiveParameterTest {

  @Test(expected = UnsupportedParameterTypeException.class)
  public void testBooleanObject() throws Exception {
    final String value = "true";
    final Class<?> type = Boolean.class;
    new PrimitiveParameter(type).of(value);
  }

  @Test
  public void testBooleanPrimitive() throws Exception {
    final String value = "true";
    final Class<?> type = boolean.class;
    assertThat(new PrimitiveParameter(type).of(value)).isEqualTo(true);
  }

  @Test(expected = UnsupportedParameterTypeException.class)
  public void testByteObject() throws Exception {
    final String value = "42";
    final Class<?> type = Byte.class;
    new PrimitiveParameter(type).of(value);
  }

  @Test
  public void testBytePrimitive() throws Exception {
    final String value = "42";
    final Class<?> type = byte.class;
    assertThat(new PrimitiveParameter(type).of(value)).isEqualTo((byte) 42);
  }

  @Test(expected = UnsupportedParameterTypeException.class)
  public void testCharObject() throws Exception {
    final String value = "C";
    final Class<?> type = Character.class;
    new PrimitiveParameter(type).of(value);
  }

  @Test
  public void testCharPrimitive() throws Exception {
    final String value = "C";
    final Class<?> type = char.class;
    assertThat(new PrimitiveParameter(type).of(value)).isEqualTo('C');
  }

  @Test(expected = UnsupportedParameterTypeException.class)
  public void testDoubleObject() throws Exception {
    final String value = "42";
    final Class<?> type = Double.class;
    new PrimitiveParameter(type).of(value);
  }

  @Test
  public void testDoublePrimitive() throws Exception {
    final String value = "42";
    final Class<?> type = double.class;
    assertThat(new PrimitiveParameter(type).of(value)).isEqualTo(42.0);
  }

  @Test(expected = UnsupportedParameterTypeException.class)
  public void testFloatObject() throws Exception {
    final String value = "42";
    final Class<?> type = Float.class;
    new PrimitiveParameter(type).of(value);
  }

  @Test
  public void testFloatPrimitive() throws Exception {
    final String value = "42";
    final Class<?> type = float.class;
    assertThat(new PrimitiveParameter(type).of(value)).isEqualTo(42.0f);
  }

  @Test(expected = UnsupportedParameterTypeException.class)
  public void testIntegerObject() throws Exception {
    final String value = "42";
    final Class<?> type = Integer.class;
    new PrimitiveParameter(type).of(value);
  }

  @Test
  public void testIntegerPrimitive() throws Exception {
    final String value = "42";
    final Class<?> type = int.class;
    assertThat(new PrimitiveParameter(type).of(value)).isEqualTo(42);
  }

  @Test(expected = UnsupportedParameterTypeException.class)
  public void testLongObject() throws Exception {
    final String value = "42";
    final Class<?> type = Long.class;
    new PrimitiveParameter(type).of(value);
  }

  @Test
  public void testLongPrimitive() throws Exception {
    final String value = "42";
    final Class<?> type = long.class;
    assertThat(new PrimitiveParameter(type).of(value)).isEqualTo(42L);
  }

  @Test
  public void testNullPrimitive() throws Exception {
    final String value = null;
    final Class<?> type = int.class;
    assertThat(new PrimitiveParameter(type).of(value)).isNull();
  }

  @Test(expected = UnsupportedParameterTypeException.class)
  public void testShortObject() throws Exception {
    final String value = "42";
    final Class<?> type = Short.class;
    new PrimitiveParameter(type).of(value);
  }

  @Test
  public void testShortPrimitive() throws Exception {
    final String value = "42";
    final Class<?> type = short.class;
    assertThat(new PrimitiveParameter(type).of(value)).isEqualTo((short) 42);
  }

  @Test
  public void testStringPrimitive() throws Exception {
    final String value = "42";
    final Class<?> type = String.class;
    assertThat(new PrimitiveParameter(type).of(value)).isEqualTo(value);
  }

}
