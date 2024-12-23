package app.mock;

import app.integrations.IChatModelClient;
import app.integrations.OpenAIModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MockChatModelClient implements IChatModelClient {

    private final String mockResponse;

    public MockChatModelClient(String mockResponseFilePath) {
        try {
            mockResponse = Files.readString(Path.of(mockResponseFilePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read mock response file: " + mockResponseFilePath, e);
        }
    }

    @Override
    public String getResponse(OpenAIModel model, String prompt) {
        return mockResponse;
    }
}

