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
package org.mintshell.target;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.mintshell.assertion.Assert;

/**
 * Base implementation of a {@link CommandShell} that manages it's {@link Map} of {@link CommandTarget}s and
 * {@link CommandTargetSource}s.
 *
 * @author Noqmar
 * @since 0.2.0
 */
public abstract class BaseCommandShell implements CommandShell {

  private final String prompt;
  private final Map<CommandTarget, CommandTargetSource> commandTargetSources;

  /**
   * Creates a new instance and determines all {@link CommandTarget}s from the given command target sources
   *
   * @param prompt
   *          prompt text
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected BaseCommandShell(final String prompt) {
    this.prompt = Assert.ARG.isNotNull(prompt, "[prompt] must not be [null]");
    this.commandTargetSources = new HashMap<>();
  }

  /**
   * Adds {@link CommandTargetSource}s to be managed by this {@link CommandShell} and updates it's internal managed
   * {@link CommandTarget}s.
   *
   * @param commandTargetSources
   *          more managed {@link CommandTargetSource}s
   *
   * @author Noqmar
   * @since 0.2.0
   * @see #determineCommandTargets(CommandTargetSource)
   */
  public void addCommandTargetSources(final CommandTargetSource... commandTargetSources) {
    if (commandTargetSources != null) {
      for (final CommandTargetSource source : commandTargetSources) {
        if (source != null) {
          final Set<CommandTarget> sources = this.determineCommandTargets(source);
          sources.forEach(target -> this.commandTargetSources.put(target, source));
        }
      }
    }
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.target.CommandShell#getPrompt()
   */
  @Override
  public String getPrompt() {
    return this.prompt;
  }

  /**
   *
   * {@inheritDoc}
   *
   * @see org.mintshell.target.CommandShell#getTargets()
   */
  @Override
  public Set<CommandTarget> getTargets() {
    return new HashSet<>(this.commandTargetSources.keySet());
  }

  /**
   * Inspects the given command target source and determines all available {@link CommandTarget}s.
   *
   * @param commandTargetSource
   *          object containing {@link CommandTarget}s
   * @return {@link Set} of determined {@link CommandTarget}s
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected abstract Set<CommandTarget> determineCommandTargets(final CommandTargetSource commandTargetSource);

  /**
   * Returns the currently managed mapping of {@link CommandTarget}s to it's {@link CommandTargetSource}s.
   *
   * @return mapping of {@link CommandTarget}s to it's {@link CommandTargetSource}s
   *
   * @author Noqmar
   * @since 0.2.0
   */
  protected Map<CommandTarget, CommandTargetSource> getCommandTargetSources() {
    return this.commandTargetSources;
  }
}
