package app.formatter;

import java.util.List;

public class CVFormatter {
    private CVFormatter() {
    }
    public static String formatWithBulletPoints(List<String> items) {
        StringBuilder sb = new StringBuilder();
        for (String item : items) {
            sb.append("•    ").append(item).append("\n");
        }
        return sb.toString();
    }
}
