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
package org.mintshell.mcl.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.mintshell.command.Command;
import org.mintshell.command.CommandParameter;
import org.mintshell.command.DefaultCommand;
import org.mintshell.command.DefaultCommandParameter;
import org.mintshell.command.DefaultPipedCommand;
import org.mintshell.interpreter.CommandInterpreteException;
import org.mintshell.interpreter.CommandInterpreter;
import org.mintshell.mcl.MCLLexer;
import org.mintshell.mcl.MCLParser;
import org.mintshell.mcl.MCLParser.CommandContext;
import org.mintshell.mcl.MCLParser.CommandLineContext;
import org.mintshell.mcl.MCLParser.CommandNameContext;
import org.mintshell.mcl.MCLParser.CommandParameterContext;
import org.mintshell.mcl.MCLParser.CommandParameterValueContext;
import org.mintshell.mcl.MCLParser.LongCommandParameterContext;
import org.mintshell.mcl.MCLParser.ShortCommandParameterContext;
import org.mintshell.mcl.SyntaxException;
import org.mintshell.mcl.SytaxExceptionErrorListener;

/**
 * Implementation of a {@link CommandInterpreter} that understands the (M)intshell (C)ommand (L)anguage.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public class MclCommandInterpreter implements CommandInterpreter {

  private final SytaxExceptionErrorListener errorListener;

  /**
   * Creates a new instance.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public MclCommandInterpreter() {
    this.errorListener = new SytaxExceptionErrorListener();
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.interpreter.CommandInterpreter#interprete(java.lang.String)
   */
  @Override
  public Command interprete(final String commandMessage) throws CommandInterpreteException {
    try {
      final CommandLineContext parsedCommandLine = this.createParser(commandMessage).commandLine();
      final List<CommandContext> pipedCommands = new ArrayList<>(parsedCommandLine.pipedCommand().stream() //
          .map(piped -> piped.command()) //
          .collect(Collectors.toList()));
      pipedCommands.add(0, parsedCommandLine.command());
      return this.handleCommandPipe(pipedCommands);
    } catch (final SyntaxException e) {
      throw new CommandInterpreteException(e.getMessage(), e);
    }
  }

  private MCLParser createParser(final String command) {
    final MCLLexer lexer = new MCLLexer(new ANTLRInputStream(command));
    lexer.removeErrorListeners();
    lexer.addErrorListener(this.errorListener);
    final CommonTokenStream tokens = new CommonTokenStream(lexer);
    final MCLParser parser = new MCLParser(tokens);
    parser.removeErrorListeners();
    parser.addErrorListener(this.errorListener);
    return parser;
  }

  private String extractCommandName(final CommandNameContext parsedCommand) {
    if (parsedCommand == null) {
      return null;
    }
    else if (parsedCommand.CHARACTER() != null) {
      return parsedCommand.CHARACTER().toString();
    }
    else if (parsedCommand.UNQUOTED() != null) {
      return parsedCommand.UNQUOTED().toString();
    }
    else if (parsedCommand.QUOTED() != null) {
      final String content = parsedCommand.QUOTED().toString();
      return content.substring(1, content.length() - 1);
    }
    else {
      throw new IllegalStateException(String.format("Illegal command parameter value [%s]", parsedCommand));
    }
  }

  private String extractCommandParameterValue(final CommandParameterValueContext parsedCommandParameterValue) {
    if (parsedCommandParameterValue == null) {
      return null;
    }
    else if (parsedCommandParameterValue.CHARACTER() != null) {
      return parsedCommandParameterValue.CHARACTER().toString();
    }
    else if (parsedCommandParameterValue.UNQUOTED() != null) {
      return parsedCommandParameterValue.UNQUOTED().toString();
    }
    else if (parsedCommandParameterValue.QUOTED() != null) {
      final String content = parsedCommandParameterValue.QUOTED().toString();
      return content.substring(1, content.length() - 1);
    }
    else {
      throw new IllegalStateException(String.format("Illegal command parameter value [%s]", parsedCommandParameterValue));
    }
  }

  private Command handleCommand(final CommandContext parsedCommand) {
    final List<CommandParameter> commandParameters = this.handleCommandParameters(parsedCommand.commandParameter());
    final Command result = new DefaultCommand(this.extractCommandName(parsedCommand.commandName()), commandParameters);
    return result;
  }

  private List<CommandParameter> handleCommandParameters(final List<CommandParameterContext> parsedCommandParameters) {
    final List<CommandParameter> commandParameters = new ArrayList<>();
    final AtomicInteger indexCounter = new AtomicInteger(0);
    for (final CommandParameterContext parsedCommandParameter : parsedCommandParameters) {
      if (parsedCommandParameter.longCommandParameter() != null) {
        commandParameters.add(this.handleLongCommandParameter(indexCounter.getAndIncrement(), parsedCommandParameter.longCommandParameter(),
            parsedCommandParameter.commandParameterValue()));
      }
      else if (parsedCommandParameter.shortCommandParameter() != null) {
        commandParameters.add(this.handleShortCommandParameter(indexCounter.getAndIncrement(), parsedCommandParameter.shortCommandParameter(),
            parsedCommandParameter.commandParameterValue()));
      }
      else if (parsedCommandParameter.commandParameterValue() != null) {
        commandParameters.add(
            new DefaultCommandParameter(indexCounter.getAndIncrement(), this.extractCommandParameterValue(parsedCommandParameter.commandParameterValue())));
      }
      else {
        throw new IllegalStateException("There is none of short/long/anonymous command parameter available");
      }
    }
    return commandParameters;
  }

  private Command handleCommandPipe(final List<CommandContext> pipedCommands) {
    final CommandContext commandContext = pipedCommands.remove(0);
    if (pipedCommands.isEmpty()) {
      return this.handleCommand(commandContext);
    }
    return this.handlePipedCommand(commandContext, this.handleCommandPipe(pipedCommands));
  }

  private CommandParameter handleLongCommandParameter(final int index, final LongCommandParameterContext parsedCommandParameter,
      final CommandParameterValueContext parsedCommandParameterValue) {
    final String name = parsedCommandParameter.longCommandParameterName().getText();
    final String value = this.extractCommandParameterValue(parsedCommandParameterValue);
    return new DefaultCommandParameter(index, name, null, value == null || value.isEmpty() ? null : value);
  }

  private Command handlePipedCommand(final CommandContext parsedCommand, final Command target) {
    final List<CommandParameter> commandParameters = this.handleCommandParameters(parsedCommand.commandParameter());
    final Command result = new DefaultPipedCommand(this.extractCommandName(parsedCommand.commandName()), commandParameters, target);
    return result;
  }

  private CommandParameter handleShortCommandParameter(final int index, final ShortCommandParameterContext parsedCommandParameter,
      final CommandParameterValueContext parsedCommandParameterValue) {
    final char shortName = parsedCommandParameter.shortCommandParameterName().getText().charAt(0);
    final String value = this.extractCommandParameterValue(parsedCommandParameterValue);
    return new DefaultCommandParameter(index, null, shortName, value.isEmpty() ? null : value);
  }
}
