package com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nttdata.technicaltest.services.infrastructure.exception.JsonParsingException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JsonSerializer {
    private JsonSerializer() {
    }

    private static final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    @SneakyThrows
    public static String jsonObjectToString(Object object) {
        String result;
        try {
            result = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException error) {
            log.error("ERROR JsonSerializer jsonObjectToString error {}", error.getMessage());
            throw new JsonParsingException();
        }
        return result;
    }

    @SneakyThrows
    public static <T> T jsonStringToObject(String jsonString, Class<T> desiredClass)  {

        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }

        T jsonObject;
        try {
            jsonObject = objectMapper.readValue(jsonString, desiredClass);
        } catch (JsonProcessingException error) {
            log.error("ERROR JsonSerializer jsonStringToObject error {}", error.getMessage());
            throw new JsonParsingException();
        }
        return jsonObject;
    }
}
