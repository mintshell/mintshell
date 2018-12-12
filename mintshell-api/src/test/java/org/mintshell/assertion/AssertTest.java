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
package org.mintshell.assertion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

import org.junit.Test;

/**
 * Tests the functionality of the {@link Assert} class.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class AssertTest {

  @Test
  public void assertObjectNonNull() {
    final Object subject = new Object();
    final Object object = Assert.OBJ.isNotNull(subject);
    assertThat(object).isEqualTo(subject);
  }

  @Test(expected = NullPointerException.class)
  public void assertObjectNull() {
    Assert.OBJ.isNotNull(null);
  }

  @Test
  public void assertObjectNullWithNullMessage() {
    final String message = null;
    try {
      Assert.OBJ.isNotNull(null, message);
      fail("NullPointerException expected");
    } catch (final NullPointerException expected) {
      assertThat(expected.getMessage()).isEqualTo(message);
    }
  }

  @Test
  public void assertObjNullWithMessage() {
    final String message = "This is a custom error message";
    try {
      Assert.OBJ.isNotNull(null, message);
      fail("NullPointerException expected");
    } catch (final NullPointerException expected) {
      assertThat(expected.getMessage()).isEqualTo(message);
    }
  }
}
