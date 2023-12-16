package org.comcom.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtils {
    public static String asJsonString(Object object, ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    public static <T> T fromJson(String jsonString, Class<T> type, ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(jsonString, type);
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }
}
