package app.integrations.impl;

import app.coverletter.CoverLetter;
import app.cv.CV;
import app.integrations.IResponseParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

@Component
public class JsonResponseParser implements IResponseParser {
    private final ObjectMapper objectMapper;

    public JsonResponseParser() {
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Override
    public TailoredCVResponse parseResponse(String response) {
        try {
            int jsonStart = response.indexOf("{");
            int jsonEnd = response.lastIndexOf("}");
            if (jsonStart == -1 || jsonEnd == -1) {
                throw new RuntimeException("No valid JSON object found in the response.");
            }

            String jsonContent = response.substring(jsonStart, jsonEnd + 1);

            JsonNode rootNode = objectMapper.readTree(jsonContent);

            CV tailoredCV = objectMapper.treeToValue(rootNode.get("cv"), CV.class);
            CoverLetter coverLetter = objectMapper.treeToValue(rootNode.get("coverLetter"), CoverLetter.class);

            return new TailoredCVResponse(tailoredCV, coverLetter);
        } catch (Exception e) {
            throw new RuntimeException("Error while parsing response: " + e.getMessage(), e);
        }
    }
}