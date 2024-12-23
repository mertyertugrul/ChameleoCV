package app.pdf.renders.cv;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import app.entity.Summary;
import org.springframework.stereotype.Component;

@Component
public class SummaryRenderer implements PdfSectionRenderer<Summary> {
    @Override
    public void renderSection(Document document, Summary section) throws DocumentException {
        Paragraph summary = new Paragraph(section.summaryText(), NORMAL_FONT);
        summary.setSpacingBefore(5);
        document.add(summary);
    }

    @Override
    public Class<Summary> getSupportedClass() {
        return Summary.class;
    }
}

