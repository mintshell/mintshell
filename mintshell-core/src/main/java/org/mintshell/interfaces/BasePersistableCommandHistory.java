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
package org.mintshell.interfaces;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.stream.Collectors;

import org.mintshell.assertion.Assert;
import org.mintshell.common.IoProvider;

/**
 * Base implementation of a {@link CommandHistory} that is persistable.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public abstract class BasePersistableCommandHistory extends BaseCommandHistory {

  private final IoProvider ioProvider;

  /**
   * Creates a new persistable command history using the given I/O provider.
   *
   * @param ioProvider
   *          I/O provider to be used to load and store the command history
   *
   * @author Noqmar
   * @since 0.2.0
   */
  public BasePersistableCommandHistory(final IoProvider ioProvider) {
    this.ioProvider = Assert.ARG.isNotNull(ioProvider, "[ioProvider] must not be [null]");
    try {
      this.load();
    } catch (final IOException e) {
      throw new IllegalStateException("Failed to restore command history", e);
    }
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interfaces.BaseCommandHistory#addCommandLine(java.lang.String)
   */
  @Override
  public void addCommandLine(final String commandLine) {
    super.addCommandLine(commandLine);
    try {
      this.store();
    } catch (final IOException e) {
      throw new IllegalStateException("Failed to persist command history", e);
    }
  }

  /**
   * Returns the internally used {@link IoProvider}.
   *
   * @return internally used {@link IoProvider}
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected IoProvider getIoProvider() {
    return this.ioProvider;
  }

  /**
   * Loads the persisted command history from the internal {@link IoProvider}.
   *
   * @throws IOException
   *           if loading the command history failed
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected void load() throws IOException {
    try (final BufferedReader reader = new BufferedReader(new InputStreamReader(this.ioProvider.createIn()))) {
      this.clear();
      String line = null;
      while ((line = reader.readLine()) != null) {
        this.addCommandLine(line);
      }
    }
  }

  /**
   * Stores the current command history to the internal {@link IoProvider}.
   *
   * @throws IOException
   *           if storing the command history failed
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected void store() throws IOException {
    try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.ioProvider.createOut()))) {
      final List<String> lines = this.getCommandLines().entrySet().stream() //
          .sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey())) //
          .map(entry -> entry.getValue()) //
          .collect(Collectors.toList());
      for (final String line : lines) {
        writer.newLine();
        writer.write(line);
        writer.flush();
      }
    }
  }
}
