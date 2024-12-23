package app.pdf.renders.cv;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import app.entity.ProfessionalExperience;
import org.springframework.stereotype.Component;

@Component
public class ProfessionalExperienceRenderer implements PdfSectionRenderer<ProfessionalExperience> {
    @Override
    public void renderSection(Document document, ProfessionalExperience section) throws DocumentException {
        PdfPTable companyTable = PdfSectionRenderer.createTwoColumnTable();
        companyTable.addCell(PdfSectionRenderer.createCell(section.company(), HEADER_FONT, Rectangle.NO_BORDER, 5));
        companyTable.addCell(PdfSectionRenderer.createRightAlignedCell(section.location().toString(), HEADER_FONT, Rectangle.NO_BORDER, 0));
        document.add(companyTable);

        PdfPTable positionTable = PdfSectionRenderer.createTwoColumnTable();
        positionTable.addCell(PdfSectionRenderer.createCell(String.valueOf(section.position()), ITALIC_FONT, Rectangle.NO_BORDER, 5));
        positionTable.addCell(PdfSectionRenderer.createRightAlignedCell(section.dateRange().toString(), NORMAL_FONT, Rectangle.NO_BORDER, 0));
        document.add(positionTable);

        if (section.details() != null && !section.details().toString().isEmpty()) {
            Paragraph details = new Paragraph(section.details().toString(), NORMAL_FONT);
            details.setIndentationLeft(20);
            details.setSpacingBefore(-1);
            document.add(details);
        }
    }

    @Override
    public Class<ProfessionalExperience> getSupportedClass() {
        return ProfessionalExperience.class;
    }
}

