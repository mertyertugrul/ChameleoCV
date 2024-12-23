package app.entity;

import app.entity.common.DateRange;
import app.entity.common.Details;
import app.entity.common.Location;
import app.entity.common.Position;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ProfessionalExperience(
        @JsonProperty("company") String company,
        @JsonProperty("position") Position position,
        @JsonProperty("location") Location location,
        @JsonProperty("dateRange") DateRange dateRange,
        @JsonProperty("details") Details details,
        @JsonProperty("header") String header
) implements CVSection {
    @Override
    public String getHeader() {
        return company;
    }

}


