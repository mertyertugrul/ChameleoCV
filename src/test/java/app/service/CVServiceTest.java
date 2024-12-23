package app.service;

import app.cv.CV;
import app.entity.*;
import app.entity.common.Certification;
import app.pdf.impl.CVPdfGenerator;
import app.pdf.renders.cv.*;
import app.pdf.renders.cv.registory.CvRendererRegistry;
import com.itextpdf.text.Document;
import app.entity.TestData;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
class CVServiceTest {
    private CV cv;
    private final String generatedFilePath = "src/test/resources/service/test_cv_output.pdf";
    private final String expectedFilePath = "src/test/resources/service/test_cv_output_expected.pdf";
    private CVService cvService;

    @BeforeEach
    void setUp() {
        cv = TestData.createTestCV();
        Logger.getLogger("org.apache.pdfbox.pdmodel.font.FileSystemFontProvider").setLevel(Level.SEVERE);
        Logger.getLogger("org.apache.pdfbox.pdmodel.font.FontManager").setLevel(Level.SEVERE);

        CvRendererRegistry cvRendererRegistry = new CvRendererRegistry();
        cvRendererRegistry.addRenderer(PersonalInfo.class, new PersonalInfoRenderer());
        cvRendererRegistry.addRenderer(Summary.class, new SummaryRenderer());
        cvRendererRegistry.addRenderer(ProfessionalExperience.class, new ProfessionalExperienceRenderer());
        cvRendererRegistry.addRenderer(Education.class, new EducationRenderer());
        cvRendererRegistry.addRenderer(Skills.class, new SkillsRenderer());
        cvRendererRegistry.addRenderer(Certification.class, new CertificationsRenderer());
        cvRendererRegistry.addRenderer(Recommendation.class, new RecommendationsRenderer());


        CVPdfGenerator cvPdfGenerator = new CVPdfGenerator(new Document(), cvRendererRegistry);

        // Initialize cvService
        cvService = new CVService(cvPdfGenerator, cvRendererRegistry);
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
    void cvPdfContentComparison() {
        try {
            cvService.exportCvToPdf(cv, generatedFilePath);
            String generatedPdfContent = extractPdfContent(generatedFilePath);
            String expectedPdfContent = extractPdfContent(expectedFilePath);
            assertEquals(expectedPdfContent.trim(), generatedPdfContent.trim(),
                    "The generated PDF content does not match the expected content.");
        } catch (IOException e) {
            fail("Failed to process the PDF files for comparison: " + e.getMessage());
        }
    }

    private String extractPdfContent(String filePath) throws IOException {
        try (PDDocument document = PDDocument.load(Files.newInputStream(Path.of(filePath)))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

}
