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
package org.mintshell.terminal.ssh.interfaces;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * {@link FilterInputStream} that reads {@link AnsiKey}s from a given {@link InputStream}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class AnsiKeyFilterInputStream extends FilterInputStream {

  /**
   * Creates a new {@link AnsiKeyFilterInputStream} based on the given {@link InputStream}.
   *
   * @param in
   *          basic {@link InputStream} to filter
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public AnsiKeyFilterInputStream(final InputStream in) {
    super(in);
  }

  /**
   * Reads one or more bytes from the underlying {@link InputStream} and transforms them in to a {@link AnsiKey}.
   *
   * @return read {@link AnsiKey}
   * @throws IOException
   *           if reading failed
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public AnsiKey readKey() throws IOException {
    final int input = this.read();
    final int moreAvailable = this.available();
    if (input == -1) {
      return AnsiKey.UNDEFINED;
    }
    final AnsiKey key = AnsiKey.bySequence(new byte[] { (byte) (char) input });
    if (!AnsiKey.ESCAPE.equals(key) || moreAvailable == 0) {
      return key;
    }
    else {
      final byte[] sequence = new byte[moreAvailable + 1];
      sequence[0] = (byte) input;
      this.read(sequence, 1, moreAvailable);
      return AnsiKey.bySequence(sequence);
    }
  }
}
