package app.pdf.renders.cv;

import com.itextpdf.text.*;
import app.entity.Skills;
import app.entity.common.Framework;
import app.entity.common.Language;
import app.entity.common.Tool;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SkillsRenderer implements PdfSectionRenderer<Skills> {

    @Override
    public void renderSection(Document document, Skills section) throws DocumentException {

        // Add header
        PdfSectionRenderer.renderHeader(document, section.getHeader());

        // Add sub-header for "Languages, Frameworks and Tools"
        Paragraph subHeader = new Paragraph("Languages, Frameworks and Tools", PdfSectionRenderer.ITALIC_FONT);
        subHeader.setSpacingBefore(2);
        subHeader.setSpacingAfter(0);
        document.add(subHeader);


        String languages = section.languages().stream()
                .map(Language::name) // Collect only the names
                .collect(Collectors.joining(", ")); // Combine them with a comma

        String frameworks = section.frameworks().stream()
                .map(Framework::name)
                .collect(Collectors.joining(", "));

        String tools = section.tools().stream()
                .map(Tool::name)
                .collect(Collectors.joining(", "));

        // Create a combined skills list
        List combinedSkillsList = new List(List.UNORDERED);
        combinedSkillsList.setListSymbol(new com.itextpdf.text.Chunk("• ", PdfSectionRenderer.NORMAL_FONT));
        combinedSkillsList.setIndentationLeft(20);

        // Add each category as a single item in the list
        if (!languages.isEmpty()) {
            combinedSkillsList.add(new ListItem(languages, PdfSectionRenderer.NORMAL_FONT));
        }

        if (!frameworks.isEmpty()) {
            combinedSkillsList.add(new ListItem(frameworks, PdfSectionRenderer.NORMAL_FONT));
        }

        if (!tools.isEmpty()) {
            combinedSkillsList.add(new ListItem(tools, PdfSectionRenderer.NORMAL_FONT));
        }

        // Add the list to the document
        document.add(combinedSkillsList);
        }

    @Override
    public Class<Skills> getSupportedClass() {
        return Skills.class;
    }
}

