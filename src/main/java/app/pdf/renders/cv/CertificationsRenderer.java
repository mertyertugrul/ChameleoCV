package app.pdf.renders.cv;

import com.itextpdf.text.*;
import app.entity.common.Certification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CertificationsRenderer implements PdfSectionRenderer<Certification> {

    @Override
    public void renderSection(Document document, Certification certification) throws DocumentException {
        if (certification == null) {
            log.warn("Encountered null Certification object.");
            return;
        }

        String title = certification.title();
        String certificationId = certification.certificationId();

        if (title == null || title.trim().isEmpty()) {
            log.warn("Certification title is null or empty for certification ID: {}", certificationId);
            return;
        }

        List certificationList = new List(List.UNORDERED);
        certificationList.setListSymbol(new Chunk("•    ", NORMAL_FONT));
        certificationList.setIndentationLeft(20);

        ListItem listItem = new ListItem();

        if (certification.link() != null && certification.link().url() != null && !certification.link().url().trim().isEmpty()) {
            listItem.add(PdfSectionRenderer.createHyperlink(title, certification.link().url()));
        } else {
            listItem.add(new Chunk(title, NORMAL_FONT));
        }

        if (certificationId != null && !certificationId.trim().isEmpty()) {
            Chunk idChunk = new Chunk("  Certification ID: " + certificationId, PdfSectionRenderer.NORMAL_FONT);
            listItem.add(idChunk);
        }

        certificationList.add(listItem);
        document.add(certificationList);
    }

    @Override
    public Class<Certification> getSupportedClass() {
        return Certification.class;
    }
}