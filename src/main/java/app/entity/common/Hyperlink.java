package app.entity.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Hyperlink(@JsonProperty("text") String text,
                        @JsonProperty("url") String url) {
    @Override
    public String toString() {
        return text + " (" + url + ")";
    }
}
