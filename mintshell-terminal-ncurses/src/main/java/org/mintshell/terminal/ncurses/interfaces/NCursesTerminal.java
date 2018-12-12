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
package org.mintshell.terminal.ncurses.interfaces;

import static java.lang.String.format;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Native interface to the underlying terminal using the ncurses library.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public final class NCursesTerminal {

  private static final String LIB_PATTERN = "mintshell-terminal-ncurses-%s.%s";

  private static final NCursesTerminal INSTANCE = new NCursesTerminal();

  /**
   * Initializes the ncurses native library.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  private NCursesTerminal() {
    loadLibrary();
    Runtime.getRuntime().addShutdownHook(new Thread(() -> NCursesTerminal.endwin()));
    NCursesTerminal.init();
  }

  /**
   * Clears the terminal screen calling {@code ncurses#clear()}.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  void clearScreen() {
    clear();
  }

  /**
   * Deletes the character under the cursor.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  void deleteChar() {
    delCh();
  }

  /**
   * Deletes a character at the given position.
   *
   * @param col
   *          column number
   * @param row
   *          row number
   *
   * @author Noqmar
   * @since 0.1.0
   */
  void deleteCharAt(final int col, final int row) {
    mvDelCh(col, row);
  }

  /**
   * Returns the current column number of the cursor.
   *
   * @return column number
   *
   * @author Noqmar
   * @since 0.1.0
   */
  int getCol() {
    return getX();
  }

  /**
   * Returns the maximum column number of the cursor.
   *
   * @return maximum column number
   *
   * @author Noqmar
   * @since 0.1.0
   */
  int getMaxCol() {
    return getMaxX();
  }

  /**
   * Returns the maximum row number of the cursor.
   *
   * @return maximum row number
   *
   * @author Noqmar
   * @since 0.1.0
   */
  int getMaxRow() {
    return getMaxY();
  }

  /**
   * Returns the current row number of the cursor.
   *
   * @return row number
   *
   * @author Noqmar
   * @since 0.1.0
   */
  int getRow() {
    return getY();
  }

  /**
   * Moves the cursor to the given position using {@code ncurses#move()}.
   *
   * @param col
   *          column number
   * @param row
   *          row number
   *
   * @author Noqmar
   * @since 0.1.0
   */
  void moveCursor(final int col, final int row) {
    move(col, row);
  }

  /**
   * Prints the given character to the terminal at cursor position using {@code ncurses#addch()} and
   * {@code ncurses#refresh()}.
   *
   * @param character
   *          character to print
   *
   * @author Noqmar
   * @since 0.1.0
   */
  void print(final char character) {
    addCh(character);
    refresh();
  }

  /**
   * Prints the given {@link CharSequence} to the terminal at cursor position using {@code ncurses#addch()} and
   * {@code ncurses#refresh()}.
   *
   * @param sequence
   *          {@link CharSequence} to print
   *
   * @author Noqmar
   * @since 0.1.0
   */
  void print(final CharSequence sequence) {
    for (int i = 0; i < sequence.length(); i++) {
      addCh(sequence.charAt(i));
    }
    refresh();
  }

  /**
   * Reads the next valid {@link NCursesKey} from the terminal using {@code ncurses#getch()}.This method blocks until a
   * valid key was read.
   *
   * @return read {@link NCursesKey}
   *
   * @author Noqmar
   * @since 0.1.0
   */
  NCursesKey readKey() {
    int ch = -1;
    do {
      ch = getCh();
    } while (ch == -1);
    return NCursesKey.byCode(ch);
  }

  /**
   * Shuts down the {@link NCursesTerminal} instance.
   *
   * @author Noqmar
   * @since 0.2.0
   */
  void shutDown() {
    endwin();
  }

  /**
   * Returns the {@link NCursesTerminal} singleton instance.
   *
   * @return {@link NCursesTerminal} singleton instance
   *
   * @author Noqmar
   * @since 0.1.0
   */
  static NCursesTerminal getInstance() {
    return INSTANCE;
  }

  /**
   * Calls {@code ncurses#addch(ch)}.
   *
   * @param ch
   *          ch
   *
   * @author Noqmar
   * @since 0.1.0
   */
  private static native void addCh(int ch);

  /**
   * Calls {@code ncurses#beep()}.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  private static native void beep();

  /**
   * Calls {@code ncurses#clear()}.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  private static native void clear();

  /**
   * Calls {@code ncurses#delch()}.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  private static native void delCh();

  /**
   * Calls {@code ncurses#endwin()}.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  private static native void endwin();

  /**
   * Calls {@code ncurses#getch}.
   *
   * @return ch
   *
   * @author Noqmar
   * @since 0.1.0
   */
  private static native int getCh();

  private static String getLibraryName(final boolean withArchitecture) {
    final String osArch = System.getProperty("os.arch", "unknown");
    return withArchitecture ? format(LIB_PATTERN, osArch, getLibrarySuffix()) : format(LIB_PATTERN, "unknown", getLibrarySuffix());
  }

  private static String getLibrarySuffix() {
    final String os = System.getProperty("os.name", "unknown").trim().toLowerCase();
    if (os.startsWith("windows")) {
      return "dll";
    }
    else if (os.startsWith("mac")) {
      return "jnilib";
    }
    else {
      return "so";
    }
  }

  /**
   * Returns the maximum number of columns the screen can display using {@code ncurses#getmaxyx()}.
   *
   * @return maximum number of columns
   *
   * @author Noqmar
   * @since 0.1.0
   */
  private static native int getMaxX();

  /**
   * Returns the maximum number of rows the screen can display using {@code ncurses#getmaxyx()}.
   *
   * @return maximum number of rows
   *
   * @author Noqmar
   * @since 0.1.0
   */
  private static native int getMaxY();

  /**
   * Returns the current column of the cursor using {@code ncurses#getxy()}.
   *
   * @return current column of the cursor
   *
   * @author Noqmar
   * @since 0.1.0
   */
  private static native int getX();

  /**
   * Returns the current row of the cursor {@code ncurses#getxy()}.
   *
   * @return current row of the cursor
   *
   * @author Noqmar
   * @since 0.1.0
   */
  private static native int getY();

  /**
   * Returns if the underlying terminal is capable of displaying colors using {@code ncurses#has_colors()} .
   *
   * @return {@code true} if if the underlying terminal is capable of displaying colors, {@code false} otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  private static native boolean hasColors();

  /**
   * Initializes the display using {@code ncurses}.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  private static native void init();

  private static void loadLibrary() {
    final String libFileNameWithArch = getLibraryName(true);
    try {
      loadLibrary(libFileNameWithArch);
    } catch (final UnsatisfiedLinkError e) {
      final String libFileNameWithoutArch = getLibraryName(false);
      loadLibrary(libFileNameWithoutArch);
    }
  }

  private static void loadLibrary(final String libFileName) {
    final Path localPath = Paths.get(libFileName);
    try {
      System.load(localPath.toAbsolutePath().toString());
    } catch (final UnsatisfiedLinkError e) {
      final InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(libFileName);
      if (inputStream == null) {
        throw new UnsatisfiedLinkError(format("Unable to read mintshell terminal native library [%s] from classpath or working directory", libFileName));
      }
      try {
        final Path tempFile = Files.createTempFile(libFileName, UUID.randomUUID().toString());
        Files.copy(inputStream, tempFile, REPLACE_EXISTING);
        System.load(tempFile.toAbsolutePath().toString());
      } catch (final IOException io) {
        throw new UnsatisfiedLinkError(
            format("Failed to create and load mintshell terminal native library [%s] from temp file: %s", libFileName, io.getMessage()));
      }
    }
  }

  /**
   * Places the cursor at the given position using {@code ncurses#move()}.
   *
   * @param col
   *          column number
   * @param row
   *          row number
   *
   * @author Noqmar
   * @since 0.1.0
   */
  private static native void move(int col, int row);

  /**
   * Calls {@code ncurses#mvdelch()}.
   *
   * @param col
   *          column number
   * @param row
   *          row number
   * @author Noqmar
   * @since 0.1.0
   */
  private static native void mvDelCh(int col, int row);

  /**
   * Calls {@code ncurses#refresh()}.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  private static native void refresh();

  /**
   * Calls {@code ncurses#scroll()}.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  private static native void scroll();

  /**
   * {@link InputStream} connected to the {@link NCursesTerminal}.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public static class TerminalInputStream extends InputStream {

    /**
     *
     * {@inheritDoc}
     *
     * @see java.io.InputStream#read()
     */
    @Override
    public int read() throws IOException {
      return getCh();
    }
  }

  /**
   * {@link OutputStream} connected to the {@link NCursesTerminal}.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public static class TerminalOutputStream extends OutputStream {

    /**
     *
     * {@inheritDoc}
     *
     * @see java.io.OutputStream#close()
     */
    @Override
    public void close() throws IOException {
      endwin();
    }

    /**
     *
     * {@inheritDoc}
     *
     * @see java.io.OutputStream#flush()
     */
    @Override
    public void flush() throws IOException {
      refresh();
    }

    /**
     *
     * {@inheritDoc}
     *
     * @see java.io.OutputStream#write(int)
     */
    @Override
    public void write(final int b) throws IOException {
      addCh(b);
    }
  }
}
