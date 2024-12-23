package app.pdf.renders.cv;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import app.entity.CVSection;

public interface PdfSectionRenderer<T extends CVSection> {
    Font HEADER_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
    Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 9);
    Font ITALIC_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.ITALIC);
    Font LINK_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.UNDERLINE, new BaseColor(0, 0, 255));

    void renderSection(Document document, T section) throws DocumentException;

    static void renderHeader(Document document, String header) throws DocumentException {
        Paragraph headerParagraph = new Paragraph(header, HEADER_FONT);
        headerParagraph.setSpacingBefore(7);
        document.add(headerParagraph);
    }

    static Anchor createHyperlink(String text, String url) {
        Anchor anchor = new Anchor(text, LINK_FONT);
        anchor.setReference(url);
        return anchor;
    }

    static PdfPTable createTwoColumnTable() {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        return table;
    }

    static PdfPCell createCell(String text, Font font, int border, int padding) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(border);
        cell.setPaddingLeft(padding);
        return cell;
    }

    static PdfPCell createCell(Paragraph paragraph, int border, float padding) {
        PdfPCell cell = new PdfPCell();
        cell.addElement(paragraph);
        cell.setBorder(border);
        cell.setPaddingBottom(padding);
        return cell;
    }

    static PdfPCell createRightAlignedCell(String text, Font font, int border, int padding) {
        PdfPCell cell = createCell(text, font, border, padding);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        return cell;
    }

    Class<T> getSupportedClass();

}

