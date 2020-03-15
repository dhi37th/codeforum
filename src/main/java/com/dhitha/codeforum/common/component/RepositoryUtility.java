package com.dhitha.codeforum.common.component;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

@Component
public final class RepositoryUtility {
  private RepositoryUtility() {}

  public static <T> String[] getNullPropertyNames(T object) {
    BeanWrapper wrapper = new BeanWrapperImpl(object);
    return Stream.of(wrapper.getPropertyDescriptors())
        .map(FeatureDescriptor::getName)
        .filter(name -> wrapper.getPropertyValue(name) == null)
        .toArray(String[]::new);
  }
}
