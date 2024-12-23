package app.pdf.renders.cl;

import app.coverletter.CoverLetter;
import app.entity.PersonalInfo;
import app.pdf.formatters.BulletListFormatter;
import app.pdf.formatters.PdfParagraphFormatter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DefaultCoverLetterRenderer implements CoverLetterRenderer<CoverLetter> {


    @Override
    public void renderSection(Document document, CoverLetter coverLetter) throws DocumentException {
        PersonalInfo info = coverLetter.personalInfo();

        addHeader(document, info);
        addAddress(document, info);
        addContactDetails(document, info);
        addSocialLinks(document, info);
        addDate(document, coverLetter);
        addContent(document, coverLetter);
        addClosing(document, info, coverLetter.content());
    }

    @Override
    public Class<CoverLetter> getSupportedClass() {
        return CoverLetter.class;
    }

    private void addHeader(Document document, PersonalInfo info) throws DocumentException {
        Paragraph name = PdfParagraphFormatter.createParagraph(
                info.getHeader(), PdfFonts.HEADER_FONT_BOLD, Element.ALIGN_CENTER, 0, 0);
        document.add(name);
    }

    private void addAddress(Document document, PersonalInfo info) throws DocumentException {
        String addressText = String.format("%s, %s, %s, %s, %s",
                info.address().street(),
                info.address().flatOrHouseNumber(),
                info.address().zipCode(),
                info.address().city(),
                info.address().country());
        Paragraph address = PdfParagraphFormatter.createParagraph(
                addressText, PdfFonts.NORMAL_FONT, Element.ALIGN_CENTER, 1, 1);
        document.add(address);
    }

    private void addContactDetails(Document document, PersonalInfo info) throws DocumentException {
        String contactText = String.format("%s | %s", info.telNumber(), info.email());
        Paragraph contact = PdfParagraphFormatter.createParagraph(
                contactText, PdfFonts.NORMAL_FONT, Element.ALIGN_CENTER, 1, 1);
        document.add(contact);
    }

    private void addSocialLinks(Document document, PersonalInfo info) throws DocumentException {
        String socialsText = String.format("LinkedIn: %s | GitHub: %s",
                info.linkedin().url(),
                info.github().url());
        Paragraph socials = PdfParagraphFormatter.createParagraph(
                socialsText, PdfFonts.ITALIC_FONT, Element.ALIGN_CENTER, 1, 1);
        document.add(socials);
    }

    private void addDate(Document document, CoverLetter coverLetter) throws DocumentException {
        String dateText = "Date: " + checkDate(coverLetter.date()).format(DateTimeFormatter.ofPattern("d MMMM yyyy"));
        Paragraph date = PdfParagraphFormatter.createParagraph(
                dateText, PdfFonts.NORMAL_FONT, Element.ALIGN_LEFT, 5, 1);
        document.add(date);
    }

    private LocalDate checkDate(LocalDate date) {
        if (date == null || date.isBefore(LocalDate.now())) {
            return LocalDate.now();
        }
        return date;
    }


    private void addContent(Document document, CoverLetter coverLetter) throws DocumentException {
        String content = coverLetter.content();
        List<String> lines = splitContentIntoLines(content);

        Paragraph contentParagraph = new Paragraph();
        contentParagraph.setSpacingBefore(2);
        contentParagraph.setSpacingAfter(1);
        contentParagraph.setFont(PdfFonts.NORMAL_FONT);
        contentParagraph.setAlignment(Element.ALIGN_JUSTIFIED);

        List<String> bulletPoints = new ArrayList<>();

        for (String line : lines) {
            if (isBulletPoint(line)) {
                bulletPoints.add(extractBulletText(line));
            } else {
                if (!bulletPoints.isEmpty()) {
                    contentParagraph.add(BulletListFormatter.createBulletList(bulletPoints));
                    bulletPoints.clear();
                }
                contentParagraph.add(createNormalLine(line));
            }
        }

        if (!bulletPoints.isEmpty()) {
            contentParagraph.add(BulletListFormatter.createBulletList(bulletPoints));
        }

        document.add(contentParagraph);
    }

    private List<String> splitContentIntoLines(String content) {
        return Arrays.asList(content.split("\n"));
    }

    private boolean isBulletPoint(String line) {
        return line.trim().startsWith("•");
    }

    private String extractBulletText(String line) {
        return line.trim().substring(1).trim();
    }

    private Paragraph createNormalLine(String line) {
        Paragraph normalLine = new Paragraph(line.trim(), PdfFonts.NORMAL_FONT);
        normalLine.setSpacingAfter(5);
        normalLine.setAlignment(Element.ALIGN_JUSTIFIED);
        return normalLine;
    }

    private void addClosing(Document document, PersonalInfo info, String content) throws DocumentException {
        String[] paragraphs = content.split("\n\n");
        String lastParagraph = paragraphs[paragraphs.length - 1].trim();

        boolean hasClosing = lastParagraph.toLowerCase().contains("thank you") &&
                lastParagraph.toLowerCase().contains("best regards") &&
                lastParagraph.contains(info.getHeader());

        if (!hasClosing) {
            String closingText = String.format(
                    "Thank you for considering my application.\n\nBest regards,\n%s", info.getHeader());
            Paragraph closing = PdfParagraphFormatter.createParagraph(
                    closingText, PdfFonts.NORMAL_FONT, Element.ALIGN_LEFT, 5, 5);
            document.add(closing);
        }
    }
}