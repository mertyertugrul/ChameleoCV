package app.entity.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Framework(@JsonProperty("name") String name,
                        @JsonProperty("level") String level) {
    @Override
    public String toString() {
        return name + " (" + level + ")";
    }
}
