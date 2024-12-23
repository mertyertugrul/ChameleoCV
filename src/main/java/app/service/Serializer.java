package app.service;

import java.io.IOException;


public interface Serializer<T> {
    String serialize(T object) throws IOException;
    T deserialize(String json, Class<T> clazz) throws IOException;
}
