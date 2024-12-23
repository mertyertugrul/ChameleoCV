package app.entity;

import app.entity.common.Address;
import app.entity.common.Email;
import app.entity.common.Hyperlink;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PersonalInfo(
        @JsonProperty("firstName") String firstName,
        @JsonProperty("middleName") String middleName,
        @JsonProperty("lastName") String lastName,
        @JsonProperty("email") Email email,
        @JsonProperty("telNumber") String telNumber,
        @JsonProperty("linkedin") Hyperlink linkedin,
        @JsonProperty("github") Hyperlink github,
        @JsonProperty("address") Address address,
        @JsonProperty("header") String header
) implements CVSection{
    @Override
    public String getHeader() {
        return header;
    }
}



