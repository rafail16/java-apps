package models;

import javafx.scene.control.Alert;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.StageStyle;

public class Messages {
	public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
	public static void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Informations");
        alert.setHeaderText(title);
        Text text = new Text(message);
        text.setWrappingWidth(400);
        text.setFont(new Font(20));
        text.setTextAlignment(TextAlignment.CENTER);
        alert.getDialogPane().setContent(text);
        alert.showAndWait();
    }
}
