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
package org.mintshell;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.stream.Collectors;

import org.mintshell.assertion.Assert;
import org.mintshell.command.Command;
import org.mintshell.command.DefaultCommandTarget;

/**
 * <p>
 * Kind of runtime environment for a connected {@link Command} interpretation structure consisting of
 * {@link CommandInterface}s receiving {@link Command}s, submiting them to their {@link CommandDispatcher}s, which
 * dispatch the submitted {@link Command}s to their {@link CommandTarget}s when possible.
 * </p>
 * <p>
 * It creates a {@link ForkJoinPool} with a {@link ForkJoinWorkerThread} per {@link CommandInterface} and runs the
 * {@link CommandInterface} in parallel within that {@link ForkJoinPool}.<br/>
 * It also provides the factory methods {@link #from(CommandInterface...)} and {@link #from(Set)} to get builder and
 * subbuilder instances to build a connected {@link Command} interpretation structure.
 * </p>
 *
 * @author Noqmar
 * @since 0.1.0
 */
public final class Mintshell {

  private final Set<CommandInterface> commandInterfaces;
  private final CommandInterpreter commandInterpreter;
  private final CommandDispatcher commandDispatcher;

  private Mintshell(final Set<CommandInterface> commandInterfaces, final CommandInterpreter commandInterpreter, final CommandDispatcher commandDispatcher,
      final Set<CommandTarget> commandTargets) {
    if (commandInterfaces == null) {
      throw new IllegalArgumentException("[commandInterfaces] must not be [null]");
    }
    this.commandInterfaces = new HashSet<>(commandInterfaces);
    if (commandInterpreter == null) {
      throw new IllegalArgumentException("[commandInterpreter] must not be [null]");
    }
    this.commandInterpreter = commandInterpreter;
    if (commandDispatcher == null) {
      throw new IllegalArgumentException("[commandDispatcher] must not be [null]");
    }
    this.commandDispatcher = commandDispatcher;
    if (commandTargets == null) {
      throw new IllegalArgumentException("[commandTargets] must not be [null]");
    }
    this.commandDispatcher.addCommandTargets(commandTargets);
    this.commandInterfaces.forEach(ci -> ci.activate(this.commandInterpreter, this.commandDispatcher));
  }

  /**
   * Returns all {@link CommandInterface}s managed by this {@link Mintshell}.
   *
   * @return managed {@link CommandInterface}s
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public Set<CommandInterface> getCommandInterfaces() {
    return this.commandInterfaces;
  }

  /**
   * Adds {@link CommandInterface}s to the builder.
   *
   * @param commandInterfaces
   *          {@link CommandInterface}s to add
   * @return builder instance
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public static MintShellInterfaces from(final CommandInterface... commandInterfaces) {
    return from(stream(commandInterfaces).collect(toSet()));
  }

  /**
   * Adds {@link CommandInterface}s to the builder.
   *
   * @param commandInterfaces
   *          {@link CommandInterface}s to add
   * @return builder instance
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public static MintShellInterfaces from(final Set<CommandInterface> commandInterfaces) {
    return new MintShellBuilder(commandInterfaces);
  }

  private static CommandTarget wrap(final Object commandTarget) {
    return new DefaultCommandTarget(commandTarget);
  }

  /**
   * Subbuilder collecting {@link CommandTarget}s.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public static abstract interface MintshellDispatcher {

    /**
     * Defines a variable amount of {@link Object} or {@link Class} instances as {@link CommandTarget}s for the
     * {@link CommandDispatcher}s.
     *
     * @param commandTargets
     *          variable amount of targets
     * @return new subbuilder instance
     *
     * @author Noqmar
     * @since 0.1.0
     */
    public abstract MintshellTargets to(Object... commandTargets);

    /**
     * Defines a {@link Set} of {@link Object} or {@link Class} instances as {@link CommandTarget}s for the
     * {@link CommandDispatcher}s.
     *
     * @param commandTargets
     *          {@link Set} of targets
     * @return new subbuilder instance
     *
     * @author Noqmar
     * @since 0.1.0
     */
    public abstract MintshellTargets to(Set<Object> commandTargets);
  }

  /**
   * Subbuilder collecting the {@link CommandInterpreter}.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public static abstract interface MintShellInterfaces {

    /**
     * Defines a {@link CommandInterpreter} for the {@link CommandInterface}s
     *
     * @param commandInterpreter
     *          {@link CommandInterpreter} used to interprete input from all {@link CommandInterface}
     * @return new subbuilder instance
     *
     * @author Noqmar
     * @since 0.1.0
     */
    public abstract MintshellInterpreter interpretedBy(CommandInterpreter commandInterpreter);
  }

  /**
   * Subbuilder collecting the {@link CommandDispatcher}.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public static abstract interface MintshellInterpreter {

    /**
     * Defines a {@link CommandDispatcher} for the {@link CommandInterpreter}s
     *
     * @param commandDispatcher
     *          {@link CommandDispatcher} used to dispatch an interpreted command to all {@link CommandTarget}s
     * @return new subbuilder instance
     *
     * @author Noqmar
     * @since 0.1.0
     */
    public abstract MintshellDispatcher dispatchedBy(CommandDispatcher commandDispatcher);

    /**
     * Defines a {@link Set} of {@link Object} or {@link Class} instances as {@link CommandTarget}s for the
     * {@link CommandDispatcher}s.
     *
     * @param commandTargets
     *          {@link Set} of targets
     * @return new subbuilder instance
     *
     * @author Noqmar
     * @since 0.1.0
     */
    public abstract MintshellTargets to(Set<Object> commandTargets);
  }

  /**
   * Final subbuilder to start the {@link Mintshell} command interpretation.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public static abstract interface MintshellTargets {

    /**
     * Connects all collected {@link CommandTarget}s with all collected {@link CommandDispatcher}s and those to all
     * collected {@link CommandInterface} and creates a new {@link Mintshell} instance, which is responsible of starting
     * the interpretation processing.
     *
     * @return new {@link Mintshell} instance with all the connected structure
     *
     * @author Noqmar
     * @since 0.1.0
     */
    public abstract Mintshell apply();
  }

  private static class MintShellBuilder implements MintShellInterfaces, MintshellInterpreter, MintshellDispatcher, MintshellTargets {

    private final Set<CommandInterface> commandInterfaces;
    private CommandDispatcher commandDispatcher;
    private CommandInterpreter commandInterpreter;
    private final Set<CommandTarget> commandTargets;

    private MintShellBuilder(final Set<CommandInterface> commandInterfaces) {
      this.commandInterfaces = new HashSet<>(Assert.ARG.isNotNull(commandInterfaces, "[commandInterfaces] must not be [null]"));
      this.commandTargets = new HashSet<>();
    }

    /**
     *
     * @{inheritDoc}
     * @see org.mintshell.Mintshell.MintshellTargets#apply()
     */
    @Override
    public Mintshell apply() {
      return new Mintshell(this.commandInterfaces, this.commandInterpreter, this.commandDispatcher, this.commandTargets);
    }

    /**
     *
     * @{inheritDoc}
     * @see org.mintshell.Mintshell.MintshellInterpreter#dispatchedBy(org.mintshell.CommandDispatcher)
     */
    @Override
    public MintshellDispatcher dispatchedBy(final CommandDispatcher commandDispatcher) {
      this.commandDispatcher = Assert.ARG.isNotNull(commandDispatcher, "[commandDispatcher] must not be [null]");
      return this;
    }

    /**
     *
     * @{inheritDoc}
     * @see org.mintshell.Mintshell.MintShellInterfaces#interpretedBy(org.mintshell.CommandInterpreter)
     */
    @Override
    public MintshellInterpreter interpretedBy(final CommandInterpreter commandInterpreter) {
      this.commandInterpreter = Assert.ARG.isNotNull(commandInterpreter, "[commandInterpreter] must not be [null]");
      return this;
    }

    /**
     *
     * @{inheritDoc}
     * @see org.mintshell.Mintshell.MintshellDispatcher#to(java.lang.Object[])
     */
    @Override
    public MintshellTargets to(final Object... commandTargets) {
      return this.to(stream(commandTargets).collect(Collectors.toSet()));
    }

    /**
     *
     * @{inheritDoc}
     * @see org.mintshell.Mintshell.MintshellInterpreter#to(java.util.Set)
     */
    @Override
    public MintshellTargets to(final Set<Object> commandTargets) {
      this.commandTargets.addAll( //
          commandTargets.stream() //
              .map(t -> (CommandTarget) (CommandTarget.class.isInstance(t) ? t : wrap(t))) //
              .collect(Collectors.toSet()));
      return this;
    }
  }
}
