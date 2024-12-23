package app.util.impl;

import app.util.FileNameGenerator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DefaultFileNameGenerator implements FileNameGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MM yyyy");
    private final Path baseDirectory;

    public DefaultFileNameGenerator() {
        this.baseDirectory = Path.of(".");
    }

    public DefaultFileNameGenerator(Path baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    @Override
    public String generateCvFileName(String companyName, String personName, String positionName, LocalDate creationDate) {
        createCompanyDirectory(companyName);
        return String.format("%s/%s - CV - %s - %s - %s.pdf",
                sanitizeFileName(companyName),
                sanitizeFileName(personName),
                sanitizeFileName(companyName),
                sanitizeFileName(positionName),
                DATE_FORMATTER.format(creationDate));
    }

    @Override
    public String generateCoverLetterFileName(String companyName, String personName, String positionName, LocalDate creationDate) {
        createCompanyDirectory(companyName);
        return String.format("%s/%s - Cover Letter - %s - %s - %s.pdf",
                sanitizeFileName(companyName),
                sanitizeFileName(personName),
                sanitizeFileName(companyName),
                sanitizeFileName(positionName),
                DATE_FORMATTER.format(creationDate));
    }

    private void createCompanyDirectory(String companyName) {
        String directoryPath = sanitizeFileName(companyName);
        Path dirPath = baseDirectory.resolve(directoryPath); // Correct: Resolves relative to baseDirectory
        if (!Files.exists(dirPath)) {
            try {
                Files.createDirectories(dirPath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create directory: " + dirPath, e);
            }
        }
    }

    private String sanitizeFileName(String input) {
        return input.replaceAll("[\\\\/:*?\"<>|]", "_");
    }
}