package app.integrations;

import app.cv.CV;

public interface IPromptBuilder {
    String buildPrompt(CV cv, String jobDescription);
}

