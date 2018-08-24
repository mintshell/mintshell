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
package org.mintshell.target.reflection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.mintshell.target.reflection.ParameterConversionException;
import org.mintshell.target.reflection.StringConstructorParameter;

/**
 * Tests the functionality of {@link StringConstructorParameter}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class StringConstructorParameterTest {

  @Test
  public void testInvalidUrl() throws Exception {
    final String value = "";
    final Class<?> type = URL.class;
    try {
      new StringConstructorParameter(type, 0).of(value);
      fail("ParameterConversionException expected");
    } catch (final ParameterConversionException expected) {
      assertThat(expected.getCause()).isInstanceOf(MalformedURLException.class);
    }
  }

  @Test
  public void testNull() throws Exception {
    final String value = null;
    final Class<?> type = URL.class;
    assertThat(new StringConstructorParameter(type, 0).of(value)).isNull();
  }

  @Test
  public void testUrl() throws Exception {
    final String value = "http://fooo.bar";
    final Class<?> type = URL.class;
    final URL url = (URL) new StringConstructorParameter(type, 0).of(value);
    assertThat(url.toString()).isEqualTo(value);
  }
}
