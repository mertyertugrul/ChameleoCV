package app.entity.common;

import app.formatter.CVFormatter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Details( @JsonProperty("details") List<String> details) {
    @Override
    public String toString() {
        return CVFormatter.formatWithBulletPoints(details);
    }
}
