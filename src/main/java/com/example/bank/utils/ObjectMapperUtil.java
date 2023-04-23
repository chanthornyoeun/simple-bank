package com.example.bank.utils;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T convertToObject(Object object, Class<T> clazz) {
        return objectMapper.convertValue(object, clazz);
    }

    public static <T> T updateObject(T objectToUpdate, Object valueToOverride) {
        try {
            return objectMapper.updateValue(objectToUpdate, valueToOverride);
        } catch (JsonMappingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
