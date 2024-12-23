package app.ui;

import app.ui.handlers.CVEditHandler;
import app.ui.handlers.CVProcessingHandler;
import app.ui.utils.AlertUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;

public class FxApp extends Application {
    @Setter
    private static ConfigurableApplicationContext applicationContext;

    private CVProcessingHandler cvProcessingHandler;

    private TextArea jobDescriptionArea;
    private TextField outputDirField;
    private Button browseButton;
    private Button processButton;
    private Button editCVButton;
    private ProgressIndicator progressIndicator;

    @Override
    public void start(Stage primaryStage) {
        cvProcessingHandler = new CVProcessingHandler(applicationContext);
        try {
            new CVEditHandler();
        } catch (Exception e) {
            AlertUtil.showAlert("Error", "Failed to initialize CV handler: " + e.getMessage());
            return;
        }

        jobDescriptionArea = new TextArea();
        jobDescriptionArea.setWrapText(true);

        outputDirField = new TextField();
        browseButton = new Button("Browse");
        processButton = new Button("Process");
        editCVButton = new Button("Edit CV");
        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);

        browseButton.setOnAction(e -> handleBrowse(primaryStage));
        processButton.setOnAction(e -> handleProcess());
        editCVButton.setOnAction(e -> {
            try {
                new CVEditHandler().openEditWindow();
            } catch (Exception ex) {
                AlertUtil.showAlert("Error", "Failed to open CV edit window: " + ex.getMessage());
            }
        });

        GridPane grid = createLayout();
        Scene scene = new Scene(new StackPane(grid), 600, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("ChameleoCV - Tailor Your CV & Cover Letter");
        primaryStage.show();
    }

    private GridPane createLayout() {
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        grid.add(new Label("Job Description:"), 0, 0);
        grid.add(jobDescriptionArea, 0, 1, 3, 1);
        grid.add(new Label("Output Directory:"), 0, 2);
        grid.add(outputDirField, 1, 2);
        grid.add(browseButton, 2, 2);
        grid.add(processButton, 0, 3);
        grid.add(progressIndicator, 1, 3);
        grid.add(editCVButton, 2, 3); // Add Edit CV button

        return grid;
    }

    private void handleBrowse(Stage primaryStage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDir = directoryChooser.showDialog(primaryStage);
        if (selectedDir != null) {
            outputDirField.setText(selectedDir.getAbsolutePath());
        }
    }

    private void handleProcess() {
        String jobDescription = jobDescriptionArea.getText().trim();
        String outputPath = outputDirField.getText().trim();

        if (!cvProcessingHandler.validateInputs(jobDescription, outputPath)) {
            return;
        }

        progressIndicator.setVisible(true);
        processButton.setDisable(true);

        cvProcessingHandler.process(jobDescription, outputPath, () -> {
            progressIndicator.setVisible(false);
            processButton.setDisable(false);
        });
    }

    @Override
    public void stop() throws Exception {
        cvProcessingHandler.shutdown();
        super.stop();
    }
}