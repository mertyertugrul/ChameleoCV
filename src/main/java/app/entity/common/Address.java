package app.entity.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Address(
        @JsonProperty("street") String street,
        @JsonProperty("flatOrHouseNumber") String flatOrHouseNumber,
        @JsonProperty("city") String city,
        @JsonProperty("country") String country,
        @JsonProperty("zipCode") String zipCode) {
    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s", street, flatOrHouseNumber, city, zipCode, country);
    }
}