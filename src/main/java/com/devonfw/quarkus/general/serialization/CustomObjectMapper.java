package com.devonfw.quarkus.general.serialization;

import javax.inject.Singleton;

import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.quarkus.jackson.ObjectMapperCustomizer;

@Singleton
public class CustomObjectMapper implements ObjectMapperCustomizer {

  @Override
  public void customize(ObjectMapper objectMapper) {

    SimpleModule module = new SimpleModule();
    module.addDeserializer(Pageable.class, new PageableJsonDeserializer());
    objectMapper.registerModule(module);
  }
}