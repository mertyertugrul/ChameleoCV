package app.entity.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Language(@JsonProperty("name") String name,
                       @JsonProperty("level") String level) {
    @Override
    public String toString() {
        return name + " (" + level + ")";
    }
}
