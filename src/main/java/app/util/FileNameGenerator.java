package app.util;

import java.time.LocalDate;

public interface FileNameGenerator {
    String generateCvFileName(String companyName, String personName, String positionName, LocalDate creationDate);
    String generateCoverLetterFileName(String companyName, String personName, String positionName, LocalDate creationDate);
}
