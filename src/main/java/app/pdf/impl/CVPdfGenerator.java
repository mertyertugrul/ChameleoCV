package app.pdf.impl;

import app.cv.CV;
import app.entity.*;
import app.entity.common.Certification;
import app.pdf.ICVExporter;
import app.pdf.renders.cv.registory.CvRendererRegistry;
import app.pdf.renders.cv.registory.SectionRenderer;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class CVPdfGenerator implements ICVExporter {
    private final Document document;
    private final CvRendererRegistry registry;
    private final SectionRenderer sectionRenderer;

    public CVPdfGenerator(Document document, CvRendererRegistry registry) {
        this.document = document;
        this.registry = registry;
        this.sectionRenderer = new SectionRenderer();
    }


    public void generateCV(String fileName, CV cv) {
        registry.validateRenderers(
                PersonalInfo.class,
                Summary.class,
                ProfessionalExperience.class,
                Education.class,
                Recommendation.class,
                Certification.class,
                Skills.class
        );

        try {
            checkTheDir(Paths.get(fileName));

            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                PdfWriter.getInstance(document, fos);
                document.open();

                sectionRenderer.renderSection(document, cv.personalInfo(), registry.getRenderer(PersonalInfo.class));
                sectionRenderer.renderSection(document, cv.summary(), registry.getRenderer(Summary.class));
                sectionRenderer.renderGroupedSection(document, "PROFESSIONAL EXPERIENCE", cv.professionalExperience(), registry.getRenderer(ProfessionalExperience.class));
                sectionRenderer.renderGroupedSection(document, "EDUCATION", cv.education(), registry.getRenderer(Education.class));
                sectionRenderer.renderGroupedSection(document, "RECOMMENDATIONS", cv.recommendations(), registry.getRenderer(Recommendation.class));
                sectionRenderer.renderGroupedSection(document, "CERTIFICATIONS", cv.certifications(), registry.getRenderer(Certification.class));
                sectionRenderer.renderSection(document, cv.skills(), registry.getRenderer(Skills.class));

                document.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error generating CV PDF: " + e.getMessage(), e);
        }
    }

    private void checkTheDir(Path filePath) {
        var parentDir = filePath.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            try {
                Files.createDirectories(parentDir);
            } catch (Exception e) {
                throw new RuntimeException("Error creating directories: " + e.getMessage(), e);
            }
        }
    }


}
