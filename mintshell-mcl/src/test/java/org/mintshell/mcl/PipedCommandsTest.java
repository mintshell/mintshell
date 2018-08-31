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
package org.mintshell.mcl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mintshell.mcl.MCLParser.CommandLineContext;

/**
 * Tests that invalid command lines lead to {@link SyntaxException}.
 *
 * @author Noqmar
 * @since 0.1.0
 */
@RunWith(Parameterized.class)
public class PipedCommandsTest {

  private static final String FILE = "valid.pipedcommands";

  private final String commandLine;

  public PipedCommandsTest(final String name, final String commandLine) {
    this.commandLine = commandLine;
  }

  @Test
  public void test() {
    final CommandLineContext ctx = this.getParser(this.commandLine).commandLine();
    assertThat(ctx.getText()).isEqualTo(this.commandLine);
  }

  private MCLParser getParser(final String command) {
    final SytaxExceptionErrorListener errorListener = new SytaxExceptionErrorListener();
    final MCLLexer lexer = new MCLLexer(new ANTLRInputStream(command));
    lexer.removeErrorListeners();
    lexer.addErrorListener(errorListener);
    final CommonTokenStream tokens = new CommonTokenStream(lexer);
    final MCLParser parser = new MCLParser(tokens);
    parser.removeErrorListeners();
    parser.addErrorListener(errorListener);
    return parser;
  }

  @Parameters(name = "{0}:{1}")
  public static List<Object[]> data() throws IOException, URISyntaxException {
    final Properties data = new Properties();
    data.load(PipedCommandsTest.class.getResourceAsStream(FILE));
    return data.entrySet().stream() //
        .map(entry -> new Object[] { entry.getKey(), entry.getValue() }) //
        .collect(Collectors.toList());
  }
}
