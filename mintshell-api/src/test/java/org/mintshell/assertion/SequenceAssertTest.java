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

import java.util.function.Supplier;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Tests the functionality of the {@link SequenceAssert} class.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public final class SequenceAssertTest {

  @Test
  public void assertArgumentMatches() {
    final String sequence = "foobar";
    final Pattern pattern = Pattern.compile("foo.*");
    final String result = Assert.ARG.matches(sequence, pattern);
    assertThat(result).isEqualTo(sequence);
  }

  @Test(expected = IllegalArgumentException.class)
  public void assertArgumentMatchesNot() {
    final String sequence = "foobar";
    final Pattern pattern = Pattern.compile("bar.*");
    Assert.ARG.matches(sequence, pattern);
  }

  @Test(expected = IllegalArgumentException.class)
  public void assertArgumentMatchesNullSequence() {
    final String sequence = null;
    final Pattern pattern = Pattern.compile(".*");
    Assert.ARG.matches(sequence, pattern);
  }

  @Test
  public void assertArgumentNotBlank() {
    final String sequence = "foobar";
    final String result = Assert.ARG.isNotBlank(sequence);
    assertThat(result).isEqualTo(sequence);
  }

  @Test(expected = IllegalArgumentException.class)
  public void assertArgumentNotBlankWithBlank() {
    final String sequence = "";
    Assert.ARG.isNotBlank(sequence);
  }

  @Test
  public void assertArgumentNotBlankWithCustomMessage() {
    final String message = "foobar";
    try {
      Assert.ARG.isNotBlank("", message);
      fail("IllegalArgumentException expected");
    } catch (final IllegalArgumentException expected) {
      assertThat(expected.getMessage()).isEqualTo(message);
    }
  }

  @Test
  public void assertArgumentNotBlankWithNullMessage() {
    final String message = null;
    try {
      Assert.ARG.isNotBlank("", message);
      fail("IllegalArgumentException expected");
    } catch (final IllegalArgumentException expected) {
      assertThat(expected.getMessage()).isEqualTo(message);
    }
  }

  @Test
  public void assertArgumentSupplierNotBlank() {
    final Supplier<String> supplier = () -> "foobar";
    final String result = Assert.ARG.isNotBlank(supplier);
    assertThat(result).isEqualTo("foobar");
  }

  @Test(expected = IllegalArgumentException.class)
  public void assertArgumentSupplierNotBlankWithBlank() {
    final Supplier<String> supplier = () -> "";
    Assert.ARG.isNotBlank(supplier);
  }

  @Test(expected = IllegalArgumentException.class)
  public void assertArgumentSupplierNotBlankWithException() {
    final Supplier<String> supplier = () -> {
      throw new RuntimeException();
    };
    Assert.ARG.isNotBlank(supplier);
  }

  @Test(expected = IllegalArgumentException.class)
  public void assertArgumentSupplierNotBlankWithNull() {
    final Supplier<String> supplier = null;
    Assert.ARG.isNotBlank(supplier);
  }
}
