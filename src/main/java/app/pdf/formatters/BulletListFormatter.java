package app.pdf.formatters;

import com.itextpdf.text.*;
import com.itextpdf.text.List;
import app.pdf.renders.cl.PdfFonts;

public class BulletListFormatter {

    private static final Font DEFAULT_FONT = PdfFonts.NORMAL_FONT;

    public static List createBulletList(java.util.List<String> items) {
        List bulletList = new List(List.UNORDERED);
        bulletList.setListSymbol(new Chunk("•    ", DEFAULT_FONT));
        bulletList.setIndentationLeft(15);

        for (String item : items) {
            ListItem listItem = new ListItem(item.trim(), DEFAULT_FONT);
            listItem.setSpacingAfter(1);
            bulletList.add(listItem);
        }
        return bulletList;
    }
}