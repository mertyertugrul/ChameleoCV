package app.service;

import app.cv.CV;
import app.service.impl.JsonSerializerService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CVJsonService {
    private final Serializer<CV> serializer;

    public CVJsonService() {
        this.serializer = new JsonSerializerService<>();
    }

    public void saveToJsonFile(CV cv, Path filePath) {
        try {
            String json = serializer.serialize(cv);
            Files.writeString(filePath, json);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save CV to JSON file", e);
        }
    }

    public CV loadFromJsonFile(Path filePath) {
        try {
            String json = Files.readString(filePath);
            return serializer.deserialize(json, CV.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load CV from JSON file", e);
        }
    }

    public static String convertCVToJson(CV cv) throws IOException {
        return new JsonSerializerService<CV>().serialize(cv);
    }
}