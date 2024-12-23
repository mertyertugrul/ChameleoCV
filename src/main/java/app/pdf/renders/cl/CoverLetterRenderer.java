package app.pdf.renders.cl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import app.coverletter.CoverLetter;

public interface CoverLetterRenderer<T extends CoverLetter> {
    void renderSection(Document document, T coverLetter) throws DocumentException;
    Class<T> getSupportedClass();
}