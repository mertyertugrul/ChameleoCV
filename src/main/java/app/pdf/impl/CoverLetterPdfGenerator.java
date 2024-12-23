package app.pdf.impl;

import app.coverletter.CoverLetter;
import app.pdf.renders.cl.CoverLetterRenderer;

import app.pdf.renders.cl.registory.CoverLetterRendererRegistry;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;

@Component
public class CoverLetterPdfGenerator {
    private final CoverLetterRendererRegistry rendererRegistry;

    @Autowired
    public CoverLetterPdfGenerator(CoverLetterRendererRegistry rendererRegistry) {
        this.rendererRegistry = rendererRegistry;
    }

    public void generateCoverLetter(String fileName, CoverLetter coverLetter) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            Document document = new Document();
            PdfWriter.getInstance(document, fos);
            document.open();

            renderSection(document, coverLetter);

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error generating Cover Letter PDF: " + e.getMessage(), e);
        }
    }

    private <T extends CoverLetter> void renderSection(Document document, T coverLetter) {
        CoverLetterRenderer<T> renderer = (CoverLetterRenderer<T>) rendererRegistry.getRenderer(coverLetter.getClass());
        try {
            renderer.renderSection(document, coverLetter);
        } catch (Exception e) {
            throw new RuntimeException("Error rendering cover letter: " + coverLetter.getClass().getSimpleName(), e);
        }
    }
}