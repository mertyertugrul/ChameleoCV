package app.entity.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Location(@JsonProperty("city") String city,
                       @JsonProperty("country") String country) {
    @Override
    public String toString() {
        return city + ", " + country;
    }
}
