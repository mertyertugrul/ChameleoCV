package app.service;

import app.integrations.IChatModelClient;
import app.integrations.IPromptBuilder;
import app.integrations.IResponseParser;
import app.integrations.OpenAIModel;
import app.integrations.impl.TailoredCVResponse;
import app.pdf.impl.CVPdfGenerator;
import app.pdf.impl.CoverLetterPdfGenerator;
import app.repositories.ICVRepository;
import app.util.impl.DefaultFileNameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.time.LocalDate;

@Slf4j
@Service
public class CVProcessingService {

    private final ICVRepository cvRepository;
    private final IPromptBuilder promptBuilder;
    private final IChatModelClient chatClient;
    private final IResponseParser responseParser;
    private final CVPdfGenerator cvPdfGenerator;
    private final CoverLetterPdfGenerator clPdfGenerator;
    private DefaultFileNameGenerator fileNameGenerator;

    public CVProcessingService(ICVRepository cvRepository, IPromptBuilder promptBuilder,
                               IChatModelClient chatClient, IResponseParser responseParser,
                               CVPdfGenerator cvPdfGenerator, CoverLetterPdfGenerator clPdfGenerator, DefaultFileNameGenerator fileNameGenerator) {
        this.cvRepository = cvRepository;
        this.promptBuilder = promptBuilder;
        this.chatClient = chatClient;
        this.responseParser = responseParser;
        this.cvPdfGenerator = cvPdfGenerator;
        this.clPdfGenerator = clPdfGenerator;
        this.fileNameGenerator = fileNameGenerator;
    }

    public String[] createTailoredCVAndCoverLetter(Path inputCVPath, String jobDescription,
                                               Path outputDir, OpenAIModel model) {
        log.info("Loading CV from file: {}", inputCVPath);
        try {
            var originalCV = cvRepository.loadCV(inputCVPath);
            log.debug("Original CV loaded successfully.");

            String prompt = promptBuilder.buildPrompt(originalCV, jobDescription);
            log.info("Sending prompt to OpenAI model: {}", model.getModelName());

            String response = chatClient.getResponse(model, prompt);
            log.debug("Response received from OpenAI model.");

            TailoredCVResponse tailoredResponse = responseParser.parseResponse(response);

            var tailoredCVPdfPath = outputDir.resolve(
                    fileNameGenerator.generateCvFileName(tailoredResponse.getCoverLetter().companyName(),
                            tailoredResponse.getTailoredCV().personalInfo().firstName() + " " +
                                    tailoredResponse.getTailoredCV().personalInfo().lastName(),
                            tailoredResponse.getCoverLetter().position(), LocalDate.now()));
            log.info("Generating tailored CV PDF at: {}", tailoredCVPdfPath);
            cvPdfGenerator.generateCV(tailoredCVPdfPath.toString(), tailoredResponse.getTailoredCV());

            var coverLetterPdfPath = outputDir.resolve(
                    fileNameGenerator.generateCoverLetterFileName(tailoredResponse.getCoverLetter().companyName(),
                            tailoredResponse.getTailoredCV().personalInfo().firstName() + " " +
                                    tailoredResponse.getTailoredCV().personalInfo().lastName(),
                            tailoredResponse.getCoverLetter().position(), LocalDate.now()));
            log.info("Generating tailored Cover Letter PDF at: {}", coverLetterPdfPath);
            clPdfGenerator.generateCoverLetter(coverLetterPdfPath.toString(), tailoredResponse.getCoverLetter());

            log.info("Successfully generated tailored CV and cover letter.");
            return new String[]{tailoredCVPdfPath.toString(), coverLetterPdfPath.toString()};
        } catch (Exception e) {
            log.error("Failed to create tailored CV and cover letter", e);
            throw new RuntimeException(e);
        }
    }


}
