package app.integrations.impl;

import app.config.log.DebugFileWriter;
import app.cv.CV;
import app.integrations.IPromptBuilder;
import app.service.CVJsonService;
import app.service.CoverLetterService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DefaultPromptBuilder implements IPromptBuilder {
    @Override
    public String buildPrompt(CV cv, String jobDescription) {
        try {
            String prompt =  """
                    You are a professional AI assistant. Your task is to generate a CV and a cover letter that match the provided job description and align with the details of the user's existing CV.
                    You may taylor the details of the Professional Experience, Skills, and other sections of the CV to better match the job requirements.
                    The output must be in JSON format and adhere to the following detailed structure for the CV and cover letter.
                    The JSON object has keys "cv" for the CV and "coverLetter" key for the cover letter. Do not skip any keys because this jsons will be read by an application.
                    Also keep the person's professional experiences and educations, for instance if the person worked with 3 different companies or study 2 school do not remove one you can only change the details of the experience to tailor for the job description.
                    For the cover letter, you can change the details of the cover letter to tailor for the job description. Additionally, include "Key Highlights of My Experience:" part with bullet points.
                    ...
                    Here is the existing CV:
                    %s
                    
                    Here is the job description:
                    %s
                    
                    
                    Please return the CV with the same format of JSON object so that the application can read it.
                    This is an example CoverLetter JSON object
                    %s
                    """.formatted(CVJsonService.convertCVToJson(cv), jobDescription, CoverLetterService.getExampleCoverLetterJson());
            DebugFileWriter.addPromptToLogFile(prompt);
            return prompt;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
