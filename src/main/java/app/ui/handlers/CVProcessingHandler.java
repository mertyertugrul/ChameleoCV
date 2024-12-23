package app.ui.handlers;

import app.integrations.OpenAIModel;
import app.service.CVProcessingService;
import app.ui.utils.AlertUtil;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;

import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CVProcessingHandler {
    private final CVProcessingService cvProcessingService;
    private final ExecutorService executorService;

    public CVProcessingHandler(ConfigurableApplicationContext applicationContext) {
        this.cvProcessingService = applicationContext.getBean(CVProcessingService.class);
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public boolean validateInputs(String jobDescription, String outputPath) {
        if (jobDescription.isEmpty()) {
            AlertUtil.showAlert("Validation Error", "Job description cannot be empty!");
            return false;
        }

        if (outputPath.isEmpty()) {
            AlertUtil.showAlert("Validation Error", "Please select an output directory!");
            return false;
        }

        return true;
    }

    public void process(String jobDescription, String outputPath, Runnable onComplete) {
        executorService.submit(() -> {
            try {
                var cvJsonUrl = getClass().getResource("/cv/cv.json");
                if (cvJsonUrl == null) {
                    Platform.runLater(() -> AlertUtil.showAlert("Error", "Could not find cv.json"));
                    return;
                }

                var cvJsonPath = Path.of(cvJsonUrl.toURI());
                var outputDir = Path.of(outputPath);

                String[] outputLocations = cvProcessingService.createTailoredCVAndCoverLetter(
                        cvJsonPath, jobDescription, outputDir, OpenAIModel.GPT_4_TURBO
                );

                Platform.runLater(() -> AlertUtil.showSuccess(outputLocations));
            } catch (Exception e) {
                log.error("Error during processing: {}", e.getMessage());
                Platform.runLater(() -> AlertUtil.showAlert("Error", "An error occurred: " + e.getMessage()));
            } finally {
                Platform.runLater(onComplete);
            }
        });
    }

    public void shutdown() {
        executorService.shutdownNow();
    }
}