package app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import app.service.Serializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsonSerializerService<T> implements Serializer<T> {
    private final ObjectMapper objectMapper;

    public JsonSerializerService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public String serialize(T object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }


    @Override
    public T deserialize(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }
}
