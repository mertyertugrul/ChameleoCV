package app.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import app.entity.common.Hyperlink;

public record Recommendation(
        @JsonProperty("name") String name,
        @JsonProperty("link") Hyperlink link,
        @JsonProperty("header") String header
) implements CVSection {
@Override
public String getHeader() {
    return name;
}

}
