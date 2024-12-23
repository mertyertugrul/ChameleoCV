package app.entity.common;

import app.entity.CVSection;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Certification (
        @JsonProperty("title") String title,
        @JsonProperty("certificationId") String certificationId,
        @JsonProperty("link") Hyperlink link
) implements CVSection {
    @Override
    public String toString() {
        return title + " Certification ID: " + certificationId + " " + link;
    }

    @Override
    public String getHeader() {
        return "Certifications";
    }
}