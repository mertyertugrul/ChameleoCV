package app.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import app.entity.common.Framework;
import app.entity.common.Language;
import app.entity.common.Tool;

import java.util.List;

public record Skills(
        @JsonProperty("languages") List<Language> languages,
        @JsonProperty("frameworks") List<Framework> frameworks,
        @JsonProperty("tools") List<Tool> tools,
        @JsonProperty("header") String header
) implements CVSection {
    @Override
    public String getHeader() {
        return "Computing / Coding Skills";
    }

}
