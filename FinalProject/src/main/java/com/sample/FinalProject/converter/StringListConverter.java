package com.sample.FinalProject.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        if (stringList == null || stringList.isEmpty()) return "[]";
        try {
            return objectMapper.writeValueAsString(stringList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert list to JSON string", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String json) {
        if (json == null || json.isEmpty()) return new ArrayList<>();
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert JSON string to list", e);
        }
    }
}

