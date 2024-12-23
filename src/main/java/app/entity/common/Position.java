package app.entity.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Position(@JsonProperty("title") String title,
                       @JsonProperty("department") String department) {
    @Override
    public String toString() {
        return title + ", " + department;
    }
}
