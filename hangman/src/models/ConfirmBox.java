package models;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class ConfirmBox {
	static boolean result;
	public static boolean display (String title, String message) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		Label label = new Label();
		label.setText(message);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setFont(Font.font(null,FontWeight.BOLD, 20));
		Button yesB = new Button("yes");
		yesB.setFont(new Font(15));
		Button noB = new Button("no");
		noB.setFont(new Font(15));
		
		yesB.setOnAction(e -> {
			result = true;
			window.close();
		});
		noB.setOnAction(e -> {
			result = false;
			window.close();
		});
		
		HBox hbox = new HBox(20);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(yesB, noB);
		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(label, hbox);
		vbox.setAlignment(Pos.CENTER);
        vbox.setPrefSize(600, 100);
		vbox.setPadding(new Insets(20, 20, 20, 20));

		Scene scene = new Scene(vbox);
		window.setScene(scene);
		window.showAndWait();
		
		return result;
	}
}
