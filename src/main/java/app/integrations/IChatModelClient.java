package app.integrations;

public interface IChatModelClient {
    String getResponse(OpenAIModel model, String prompt);
}


