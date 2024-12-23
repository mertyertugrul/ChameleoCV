package app.service;

import app.coverletter.CoverLetter;
import app.pdf.impl.CoverLetterPdfGenerator;
import app.pdf.renders.cl.DefaultCoverLetterRenderer;
import app.pdf.renders.cl.registory.CoverLetterRendererRegistry;
import app.service.impl.JsonSerializerService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class CoverLetterService {
    private final Serializer<CoverLetter> serializer;

    public CoverLetterService() {
        this.serializer = new JsonSerializerService<>();
    }


    public static String getExampleCoverLetterJson() {
        try {
            Path templatePath = Paths.get(
                    Objects.requireNonNull(CoverLetterService.class.getClassLoader()
                                    .getResource("templates/cover_letter.json"))
                            .toURI()
            );
            return Files.readString(templatePath);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to load example Cover Letter JSON", e);
        }
    }

    public void saveToJsonFile(CoverLetter coverLetter, Path filePath) {
        try {
            String json = serializer.serialize(coverLetter);
            Files.writeString(filePath, json);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save Cover Letter to JSON file", e);
        }
    }
}