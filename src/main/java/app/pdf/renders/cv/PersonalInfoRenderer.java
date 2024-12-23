package app.pdf.renders.cv;

import com.itextpdf.text.*;
import app.entity.PersonalInfo;
import org.springframework.stereotype.Component;

@Component
public class PersonalInfoRenderer implements PdfSectionRenderer<PersonalInfo> {

    @Override
    public void renderSection(Document document, PersonalInfo section) throws DocumentException {
        Paragraph name = new Paragraph(section.getHeader(), HEADER_FONT);
        name.setAlignment(Element.ALIGN_CENTER);
        document.add(name);

        Paragraph address = new Paragraph(String.valueOf(section.address()), NORMAL_FONT);
        address.setAlignment(Element.ALIGN_CENTER);
        address.setSpacingBefore(5);
        address.setSpacingAfter(-5);
        document.add(address);

        Paragraph contactInfo = new Paragraph();
        contactInfo.setAlignment(Element.ALIGN_CENTER);
        contactInfo.add(new Chunk(section.telNumber(), NORMAL_FONT));
        contactInfo.add(new Chunk(" | ", NORMAL_FONT));

        Anchor emailAnchor = PdfSectionRenderer.createHyperlink(String.valueOf(section.email()), "mailto:" + section.email());
        contactInfo.add(emailAnchor);
        contactInfo.add(new Chunk(" | ", NORMAL_FONT));

        Anchor linkedinAnchor = PdfSectionRenderer.createHyperlink(section.linkedin().url(), section.linkedin().url());
        contactInfo.add(linkedinAnchor);
        contactInfo.add(new Chunk(" | ", NORMAL_FONT));

        Anchor githubAnchor = PdfSectionRenderer.createHyperlink(section.github().url(), section.github().url());
        contactInfo.add(githubAnchor);

        document.add(contactInfo);

    }

    @Override
    public Class<PersonalInfo> getSupportedClass() {
        return PersonalInfo.class;
    }
}

