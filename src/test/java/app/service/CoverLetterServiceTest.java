package app.service;

import app.pdf.renders.cl.DefaultCoverLetterRenderer;
import app.pdf.renders.cl.registory.CoverLetterRendererRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import app.coverletter.CoverLetter;
import app.entity.TestData;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import app.pdf.impl.CoverLetterPdfGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CoverLetterServiceTest {

    private CoverLetterService coverLetterService;
    private CoverLetter coverLetter;
    private final String generatedFilePath = "src/test/resources/service/test_cover_letter_output.pdf";
    private final String expectedFilePath = "src/test/resources/service/test_cover_letter_output_expected.pdf";

    @BeforeEach
    void setUp() {
        coverLetterService = new CoverLetterService();
        coverLetter = TestData.createTestCoverLetter();
        Logger.getLogger("org.apache.pdfbox.pdmodel.font.FileSystemFontProvider").setLevel(Level.SEVERE);
        Logger.getLogger("org.apache.pdfbox.pdmodel.font.FontManager").setLevel(Level.SEVERE);
    }

    @AfterEach
    void cleanUp() {
        try {
            Files.deleteIfExists(Path.of(generatedFilePath));
        } catch (IOException e) {
            log.warn("Failed to delete generated file: " + generatedFilePath);
        }
    }

    @Test
    void coverLetterPdfContentComparison() {
        CoverLetterRendererRegistry rendererRegistry =
                new CoverLetterRendererRegistry(List.of(new DefaultCoverLetterRenderer()));
        CoverLetterPdfGenerator generator = new CoverLetterPdfGenerator(rendererRegistry);
        generator.generateCoverLetter(generatedFilePath, coverLetter);
        assertTrue(Files.exists(Path.of(generatedFilePath)), "Generated PDF file should exist.");
        try {
            String generatedPdfText = extractPdfText(generatedFilePath);
            String expectedPdfText = extractPdfText(expectedFilePath);
            String processedGeneratedText = Arrays.stream(generatedPdfText.split("\\r?\\n"))
                    .filter(line -> !line.trim().startsWith("Date:"))
                    .collect(Collectors.joining("\n"))
                    .trim()
                    .replaceAll("\\s+", " ");

            String processedExpectedText = Arrays.stream(expectedPdfText.split("\\r?\\n"))
                    .filter(line -> !line.trim().startsWith("Date:"))
                    .collect(Collectors.joining("\n"))
                    .trim()
                    .replaceAll("\\s+", " ");

            assertEquals(processedExpectedText, processedGeneratedText,
                    "The generated PDF content does not match the expected content, ignoring the date.");
        } catch (IOException e) {
            fail("Failed to extract text from PDF files: " + e.getMessage());
        }
    }

    private String extractPdfText(String filePath) throws IOException {
        try (PDDocument document = PDDocument.load(Files.newInputStream(Path.of(filePath)))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    @Test
    void testCoverLetterJsonCreation() {
        String jsonFilePath = generatedFilePath.replace(".pdf", ".json");
        coverLetterService.saveToJsonFile(coverLetter, Path.of(jsonFilePath));

        try {
            String generatedJson = Files.readString(Path.of(jsonFilePath));
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            String expectedJson = objectMapper.writeValueAsString(TestData.createTestCoverLetter());
            assertEquals(expectedJson.trim(), generatedJson.trim(),
                    "The generated JSON content does not match the expected content.");
        } catch (IOException e) {
            fail("Failed to read the JSON file for comparison: " + e.getMessage());
        } finally {
            try {
                Files.deleteIfExists(Path.of(jsonFilePath));
            } catch (IOException e) {
                log.warn("Failed to delete the JSON file: " + jsonFilePath);
            }
        }
    }
}