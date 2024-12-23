package app.pdf.renders.cv;

import com.itextpdf.text.*;
import app.entity.Recommendation;
import org.springframework.stereotype.Component;

@Component
public class RecommendationsRenderer implements PdfSectionRenderer<Recommendation> {
    @Override
    public void renderSection(Document document, Recommendation section) throws DocumentException {
        List recommendationList = new List(List.UNORDERED);
        recommendationList.setListSymbol(new Chunk("• ", NORMAL_FONT));
        recommendationList.setIndentationLeft(20);

        ListItem listItem = new ListItem();
        listItem.add(PdfSectionRenderer.createHyperlink(section.name(), section.link().url()));
        recommendationList.add(listItem);

        document.add(recommendationList);
    }

    @Override
    public Class<Recommendation> getSupportedClass() {
        return Recommendation.class;
    }
}
