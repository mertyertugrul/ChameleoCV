package app.ui.handlers;

import app.ui.utils.AlertUtil;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class CVEditHandler {
    private final Path cvJsonPath;

    public CVEditHandler() throws Exception {
        var cvJsonUrl = getClass().getResource("/cv/cv.json");
        if (cvJsonUrl == null) {
            throw new Exception("Could not find cv.json");
        }
        log.info("cvJsonUrl: {}", cvJsonUrl);
        this.cvJsonPath = Path.of(cvJsonUrl.toURI());
    }

    public void openEditWindow() {
        Stage editStage = new Stage();
        editStage.setTitle("Edit CV");

        TextArea cvEditor = new TextArea();
        cvEditor.setWrapText(true);
        cvEditor.setPrefWidth(600);
        cvEditor.setPrefHeight(400);

        try {
            String cvContent = Files.readString(cvJsonPath);
            cvEditor.setText(cvContent);
        } catch (Exception e) {
            AlertUtil.showAlert("Error", "Could not load CV data: " + e.getMessage());
            return;
        }

        VBox layout = getVBox(cvEditor, editStage);
        Scene editScene = new Scene(layout, 600, 450);

        editStage.setScene(editScene);
        editStage.show();
    }

    @NotNull
    private VBox getVBox(TextArea cvEditor, Stage editStage) {
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            try {
                Files.writeString(cvJsonPath, cvEditor.getText());
                AlertUtil.showAlert("Success", "CV saved successfully!");
                editStage.close();
            } catch (Exception ex) {
                AlertUtil.showAlert("Error", "Could not save CV data: " + ex.getMessage());
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> editStage.close());

        HBox buttonBox = new HBox(10, saveButton, cancelButton);

        VBox layout = new VBox(10, cvEditor, buttonBox);
        layout.setPadding(new javafx.geometry.Insets(10));
        return layout;
    }
}