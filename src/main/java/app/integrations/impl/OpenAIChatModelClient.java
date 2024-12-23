package app.integrations.impl;

import app.config.log.DebugFileWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.config.IntegrationsConfig;
import app.integrations.IChatModelClient;
import app.integrations.OpenAIModel;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Component
public class OpenAIChatModelClient implements IChatModelClient {
    private static final String DEFAULT_SYSTEM_MESSAGE =
            "This GPT assists with job applications, including creating cover letters tailored to specific job postings, " +
                    "refining CVs, and answering potential recruiter questions. It uses the user's provided CV and cover letters " +
                    "as reference points to ensure accuracy and alignment with the job application process. When giving answers, " +
                    "the model should focus on what the applicant can bring to the team rather than praising the company. When relevant, " +
                    "the GPT should also highlight the user's experience in the industry.";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String apiKey;
    private final String apiUrl;

    public OpenAIChatModelClient(IntegrationsConfig config) {
        this.apiKey = config.openAiKey();
        this.apiUrl = config.openAiUrl();
    }


    @Override
    public String getResponse(OpenAIModel model, String prompt) {
        try {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            String requestPayload = buildRequestPayload(objectMapper, model, prompt);
            log.debug("Request payload created: {}", requestPayload);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(requestPayload))
                    .build();

            log.debug("Sending HTTP request to {}", apiUrl);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            log.info("Received response from OpenAI API with status code: {}", response.statusCode());

            if (response.statusCode() != 200) {
                log.warn("Unexpected status code: {}, response body: {}", response.statusCode(), response.body());
            }

            String result = parseResponse(objectMapper, response.body());
            DebugFileWriter.addResponseToLogFile(result);
            return result;

        } catch (Exception e) {
            throw new RuntimeException("Error while getting response from OpenAI: " + e.getMessage(), e);
        }
    }

    private String buildRequestPayload(ObjectMapper objectMapper, OpenAIModel model, String prompt) {
        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("model", model.getModelName());
        payload.put("max_tokens", 4096);
        payload.put("temperature", 0.5);

        payload.putArray("messages")
                .add(objectMapper.createObjectNode()
                        .put("role", "system")
                        .put("content", DEFAULT_SYSTEM_MESSAGE))
                .add(objectMapper.createObjectNode()
                        .put("role", "user")
                        .put("content", prompt));

        return payload.toString();
    }

    private String parseResponse(ObjectMapper objectMapper, String responseBody) {
        try {
            ObjectNode jsonResponse = (ObjectNode) objectMapper.readTree(responseBody);
            String result = jsonResponse
                    .get("choices")
                    .get(0)
                    .get("message")
                    .get("content")
                    .asText();
            log.debug("Response parsing was successful.");
            return result;
        } catch (Exception e) {
            log.error("Failed to parse response: {}", responseBody, e);
            throw new RuntimeException("Error while parsing response: " + e.getMessage(), e);
        }
    }
}
