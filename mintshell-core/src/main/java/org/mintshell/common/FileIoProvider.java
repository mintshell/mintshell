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
package org.mintshell.common;

import static java.lang.String.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import org.mintshell.assertion.Assert;

/**
 * Implementation of an {@link IoProvider} that uses a {@link File}.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public class FileIoProvider implements IoProvider {

  public static final boolean DEFAULT_CREATE = true;

  private final File file;

  /**
   * Creates a new file based {@link IoProvider}, that tries to create the file, if it doesn't exist.
   *
   * @param file
   *          file to be used for I/O
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public FileIoProvider(final File file) {
    this(file, DEFAULT_CREATE);
  }

  /**
   * Creates a new file based {@link IoProvider}.
   *
   * @param file
   *          file to be used for I/O
   * @param create
   *          {@code true} if the provider should try to create the file, if it doesn't exist, false otherwise
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public FileIoProvider(final File file, final boolean create) {
    this.file = Assert.ARG.isNotNull(file, "[file] must not be [null]");
    if (create && !file.exists()) {
      try {
        Files.createFile(file.toPath());
      } catch (final IOException e) {
        throw new IllegalArgumentException(format("Failed to create file [%s]", file), e);
      }
    }
    Assert.ARG.isTrue(file.exists(), format("[%s] must exist", file));
    Assert.ARG.isTrue(file.canRead(), format("[%s] must be readable", file));
    Assert.ARG.isTrue(file.canWrite(), format("[%s] must writable", file));
    Assert.ARG.isFalse(file.isDirectory(), format("[%s] must not be a directory", file));
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.common.IoProvider#createIn()
   */
  @Override
  public InputStream createIn() throws IOException {
    return new FileInputStream(this.file);
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.common.IoProvider#createOut()
   */
  @Override
  public OutputStream createOut() throws IOException {
    return new FileOutputStream(this.file);
  }
}
