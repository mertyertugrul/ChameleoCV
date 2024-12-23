package app.coverletter;

import app.entity.PersonalInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CoverLetter(
        PersonalInfo personalInfo,
        LocalDate date,
        String companyName,
        String position,
        String content
) {
    public CoverLetter {
        if(date == null) {
            date = LocalDate.now();
        }
    }
    public CoverLetter(PersonalInfo personalInfo, String companyName, String position, String content) {
        this(personalInfo, LocalDate.now(), companyName, position, content);
    }
}

