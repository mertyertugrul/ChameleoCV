package app.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import app.entity.common.DateRange;
import app.entity.common.Details;
import app.entity.common.Location;

public record Education(
        @JsonProperty("school") String school,
        @JsonProperty("institution") String institution,
        @JsonProperty("degree") String degree,
        @JsonProperty("grade") String grade,
        @JsonProperty("location") Location location,
        @JsonProperty("dateRange") DateRange dateRange,
        @JsonProperty("details") Details details,
        @JsonProperty("header") String header
) implements CVSection {
    @Override
    public String getHeader() {
        return "Education";
    }

}
