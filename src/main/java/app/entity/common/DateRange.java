package app.entity.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DateRange(@JsonProperty("startDate") String startDate,
                        @JsonProperty("endDate") String endDate) {
    @Override
    public String toString() {
        return startDate + " – " + endDate;
    }
}
