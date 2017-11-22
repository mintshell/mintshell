package org.mintshell.dispatcher.annotation;

import org.mintshell.annotation.Nullable;
import org.mintshell.dispatcher.reflection.ParameterConversionException;
import org.mintshell.dispatcher.reflection.ReflectionCommandParameter;
import org.mintshell.dispatcher.reflection.UnsupportedParameterTypeException;

public class AnnotationCommandParameter extends ReflectionCommandParameter {

  public AnnotationCommandParameter(final int index, final Class<?> type) throws UnsupportedParameterTypeException {
    super(index, type);
    // TODO implement constructor
  }

  @Override
  public Object of(@Nullable final String value) throws ParameterConversionException {
    // TODO implement method of
    return null;
  }

  @Override
  protected boolean isTypeSupported(final Class<?> type) {
    // TODO implement method isTypeSupported
    return false;
  }

}
