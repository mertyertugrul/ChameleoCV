package app.pdf.renders.cv.registory;

import app.entity.CVSection;
import app.pdf.renders.cv.PdfSectionRenderer;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

import java.util.List;
import java.util.Objects;

public class SectionRenderer {

    public <T extends CVSection> void renderSection(Document document, T section, PdfSectionRenderer<T> renderer) {
        try {
            renderer.renderSection(document, section);
        } catch (DocumentException e) {
            throw new RuntimeException("Error rendering section: " + section.getClass().getSimpleName(), e);
        }
    }

    public <T extends CVSection> void renderGroupedSection(
            Document document, String header, List<T> sections, PdfSectionRenderer<T> renderer) {
        if (sections.isEmpty()) return;

        try {
            PdfSectionRenderer.renderHeader(document, header);

            if (Objects.equals(header, "RECOMMENDATIONS")) {
                Paragraph infoParagraph = new Paragraph("Contact information will be provided upon request.", PdfSectionRenderer.ITALIC_FONT);
                document.add(infoParagraph);
            }
        } catch (DocumentException e) {
            throw new RuntimeException("Error rendering section header: " + header, e);
        }

        for (T section : sections) {
            renderSection(document, section, renderer);
        }
    }
}
