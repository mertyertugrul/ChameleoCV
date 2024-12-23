package app.pdf.renders.cv;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import app.entity.Education;
import org.springframework.stereotype.Component;

@Component
public class EducationRenderer implements PdfSectionRenderer<Education> {
    @Override
    public void renderSection(Document document, Education section) throws DocumentException {
        PdfPTable schoolTable = PdfSectionRenderer.createTwoColumnTable();
        schoolTable.addCell(PdfSectionRenderer.createCell(section.school(), HEADER_FONT, Rectangle.NO_BORDER, 5));
        schoolTable.addCell(PdfSectionRenderer.createRightAlignedCell(section.location().toString(), HEADER_FONT, Rectangle.NO_BORDER, 0));
        document.add(schoolTable);

        PdfPTable degreeDateTable = PdfSectionRenderer.createTwoColumnTable();
        String degreeText = section.degree() + ", " + section.institution();
        if (section.grade() != null && !section.grade().isEmpty()) {
            degreeText += "\nGraduated with " + section.grade();
        }
        Paragraph degreeParagraph = new Paragraph(degreeText, ITALIC_FONT);
        degreeParagraph.setLeading(11f);
        degreeDateTable.addCell(PdfSectionRenderer.createCell(degreeParagraph, Rectangle.NO_BORDER, 5));

        String dateRangeText = ""; // Date is optional.
        degreeDateTable.addCell(PdfSectionRenderer.createRightAlignedCell(dateRangeText, NORMAL_FONT, Rectangle.NO_BORDER, 0));
        document.add(degreeDateTable);

        if (section.details() != null && !section.details().toString().isEmpty()) {
            Paragraph details = new Paragraph(section.details().toString(), NORMAL_FONT);
            details.setIndentationLeft(20);
            details.setSpacingBefore(-6);
            document.add(details);
        }
    }

    @Override
    public Class<Education> getSupportedClass() {
        return Education.class;
    }
}
