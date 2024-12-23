package app.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Summary(
        @JsonProperty("summaryText") String summaryText,
        @JsonProperty("header") String header
) implements CVSection {
    @Override
    public String getHeader() {
        return "Summary";
    }

}
