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
package org.mintshell.assertion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

import java.util.function.Supplier;

import org.junit.Test;

/**
 * Tests the functionality of the {@link StateAssert} class.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public final class StateAssertTest {

  @Test(expected = IllegalStateException.class)
  public void assertStateFalseWithFailingSupplier() {
    final Supplier<Boolean> suppplier = () -> {
      throw new RuntimeException();
    };
    Assert.STATE.isFalse(suppplier);
  }

  @Test
  public void assertStateFalseWithFalse() {
    final boolean expression = false;
    Assert.STATE.isFalse(expression);
  }

  @Test(expected = IllegalArgumentException.class)
  public void assertStateFalseWithNullSupplier() {
    final Supplier<Boolean> suppplier = null;
    Assert.STATE.isFalse(suppplier);
  }

  @Test
  public void assertStateFalseWithSupplier() {
    final Supplier<Boolean> suppplier = () -> false;
    final boolean result = Assert.STATE.isFalse(suppplier);
    assertThat(result).isFalse();
  }

  @Test(expected = IllegalStateException.class)
  public void assertStateFalseWithTrue() {
    Assert.STATE.isFalse(true);
  }

  @Test(expected = IllegalStateException.class)
  public void assertStateFalseWithTrueSupplier() {
    final Supplier<Boolean> suppplier = () -> true;
    Assert.STATE.isFalse(suppplier);
  }

  @Test
  public void assertStateFalseWithTrueWithMessage() {
    final String message = "This is a custom error message";
    try {
      Assert.STATE.isFalse(true, message);
      fail("IllegalStateException expected");
    } catch (final IllegalStateException expected) {
      assertThat(expected.getMessage()).isEqualTo(message);
    }
  }

  @Test
  public void assertStateFalseWithTrueWithNullMessage() {
    final String message = null;
    try {
      Assert.STATE.isFalse(true, message);
      fail("IllegalStateException expected");
    } catch (final IllegalStateException expected) {
      assertThat(expected.getMessage()).isEqualTo(message);
    }
  }

  @Test
  public void assertStateTrue() {
    final boolean expression = true;
    final boolean result = Assert.STATE.isTrue(expression);
    assertThat(result).isEqualTo(expression);
  }

  @Test(expected = IllegalStateException.class)
  public void assertStateTrueWithFailingSupplier() {
    final Supplier<Boolean> suppplier = () -> {
      throw new RuntimeException();
    };
    Assert.STATE.isTrue(suppplier);
  }

  @Test(expected = IllegalStateException.class)
  public void assertStateTrueWithFalse() {
    Assert.STATE.isTrue(false);
  }

  @Test(expected = IllegalStateException.class)
  public void assertStateTrueWithFalseSupplier() {
    final Supplier<Boolean> suppplier = () -> false;
    Assert.STATE.isTrue(suppplier);
  }

  @Test
  public void assertStateTrueWithFalseWithMessage() {
    final String message = "This is a custom error message";
    try {
      Assert.STATE.isTrue(false, message);
      fail("IllegalStateException expected");
    } catch (final IllegalStateException expected) {
      assertThat(expected.getMessage()).isEqualTo(message);
    }
  }

  @Test
  public void assertStateTrueWithFalseWithNullMessage() {
    final String message = null;
    try {
      Assert.STATE.isTrue(false, message);
      fail("IllegalStateException expected");
    } catch (final IllegalStateException expected) {
      assertThat(expected.getMessage()).isEqualTo(message);
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void assertStateTrueWithNullSupplier() {
    final Supplier<Boolean> suppplier = null;
    Assert.STATE.isTrue(suppplier);
  }

  @Test
  public void assertStateTrueWithSupplier() {
    final Supplier<Boolean> suppplier = () -> true;
    final boolean result = Assert.STATE.isTrue(suppplier);
    assertThat(result).isTrue();
  }
}
