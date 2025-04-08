package models;

import java.io.*;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.util.Pair;
public class DecisionBox {
	
	static Pair<Integer, String> textId = new Pair<>(-1, "");

	
	public static Pair<Integer, String> display (String title, String message, boolean helper) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setOnCloseRequest(e -> {
			textId = new Pair<>(-2, "");
		});
		window.setTitle(title);
		Label label = new Label();
		label.setText(message);
		label.setFont(Font.font(null,FontWeight.BOLD, 20));
		
		Label scenId = new Label();
		scenId.setText("Scenario id:");
		scenId.setFont(new Font(15));
		
		TextField id = new TextField();
		id.setAlignment(Pos.CENTER);
		id.setPromptText("id number");
		id.setPrefWidth(100);
		
		Button select = new Button("Select");
		select.setFont(new Font(15));
		
		select.setOnAction(e -> {
			if (!id.getText().equals("")) {
				textId = new Pair<>(1, id.getText());
				if(helper) {
					if (searchFile(textId.getValue())) window.close();
					else {
						Message.showError("Not Valid ID", "Choose Id of Dictionary that exists in Medialab\n or create one from Open Library!");
						id.clear();
						textId = new Pair<>(-1, "");
					}
				}
				else {
					if (!searchFile(textId.getValue())) window.close();
					else {
						Message.showError("ID exists", "Hangman dictionary with the same id already exists!");
						id.clear();
						textId = new Pair<>(-1, "");
					}
				}
				
			}
		});
		HBox hbox = new HBox(10);
		hbox.getChildren().addAll(scenId, id, select);
		hbox.setAlignment(Pos.CENTER);

		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(label, hbox);
		vbox.setAlignment(Pos.CENTER);
        vbox.setPrefSize(500, 100);
		vbox.setPadding(new Insets(20, 20, 20, 20));

		Scene scene = new Scene(vbox);
		window.setScene(scene);
		window.showAndWait();
		
		return textId;
	}
	
	public static boolean searchFile(String id) {		
		return new File("src/medialab/hangman_" + id + ".txt").exists();
	}
}
