package app.repositories;

import app.cv.CV;
import org.springframework.stereotype.Repository;
import app.service.Serializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
public class JsonCVRepository implements ICVRepository {
    private final Serializer<CV> serializer;

    public JsonCVRepository(Serializer<CV> serializer) {
        this.serializer = serializer;
    }

    @Override
    public CV loadCV(Path filePath) {
        try {
            String jsonContent = Files.readString(filePath);
            return serializer.deserialize(jsonContent, CV.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load CV from JSON file: " + filePath, e);
        }
    }

    @Override
    public void saveCV(Path filePath, CV cv) {
        try {
            String jsonContent = serializer.serialize(cv);
            Files.writeString(filePath, jsonContent);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save CV to JSON file: " + filePath, e);
        }
    }
}
