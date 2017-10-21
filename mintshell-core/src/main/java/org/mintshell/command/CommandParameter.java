package org.mintshell.command;

/**
 * A {@link CommandParameter} is used to provide data for the execution of a {@link Command}. If it
 * {@link #isRequired()} it means, that the {@link Command} getting this {@link CommandParameter} passed cannot be
 * executed without it.
 *
 * @author Noqmar
 * @since 0.1.0
 */
public final class CommandParameter {

  private final boolean required;

  /**
   * Creates a new command parameter.
   * 
   * @param required
   *          {@code true}, if the command parameter is required, {@code false} otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public CommandParameter(final boolean required) {
    this.required = required;
  }

  /**
   * Returns whether the {@link CommandParameter} is required for the execution of the {@link Command} it gets passed.
   *
   * @return {@code true}, if the {@link CommandParameter} is required, {@code false} otherwise
   *
   * @author Noqmar
   * @since 0.1.0
   */
  public boolean isRequired() {
    return this.required;
  }
}
