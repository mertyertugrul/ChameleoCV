package app.repositories;

import app.cv.CV;

import java.nio.file.Path;

public interface ICVRepository {
    CV loadCV(Path filePath);
    void saveCV(Path filePath, CV cv);
}
