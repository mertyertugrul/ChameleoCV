package app.ui.utils;

import javafx.scene.control.Alert;

public class AlertUtil {

    public static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showSuccess(String[] outputLocations) {
        String message = String.format(
                "Files generated successfully:%nCV: %s%nCover Letter: %s",
                outputLocations[0], outputLocations[1]
        );
        showAlert("Success", message);
    }
}