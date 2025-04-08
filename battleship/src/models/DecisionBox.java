package models;

import java.io.*;
import java.util.*;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
/**
* This class contains exclusively of methods that either create a pop up
* window to help the user select id for the game he wants to load.
* 
* @author      Rafail Daskos
*/
public class DecisionBox {
	/**
	 * Initialize the value of the Id of the application.
	 * Defined outside of functions and static because otherwise
	 * there are problems in the requests
	 */
	static int textId = -1;
	/**
	 * Method to help select a new scenario to load that allows the user to choose 
	 * new scenario_id or just close it and continue her/his current game. Return 
	 * an integer that is going to be explained in the return.
	 * 
	 * @param title		the title of the pop-up window
	 * @param message	the message to be displayed in the pop-up window
	 * @return			the value of the usage of the pop-up window. It is going
	 * 					to be -1 if the user selects an invalid id meaning that
	 * 					not both files (enemy_gameID.txt, player_gameID.txt) exist
	 * 					in the medialab folder. It will be -2 if the user just
	 * 					closes the window so that the game will resume and equal to
	 * 					the game id chosen if both files are present
	 */
	public static int display (String title, String message) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setOnCloseRequest(e -> {
			textId = -2;
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
				textId = Integer.parseInt(id.getText());
				if (searchFile(textId)) window.close();
				else {
					Messages.showError("Not Valid ID", "Choose Id of scenario that both files\n exist in medialab!");
					id.clear();
					textId = -1;
				}
			}
		});
		HBox hbox = new HBox(10);
		hbox.getChildren().addAll(scenId, id, select);
		hbox.setAlignment(Pos.CENTER);

		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(label, hbox);
		vbox.setAlignment(Pos.CENTER);
        vbox.setPrefSize(400, 100);
		vbox.setPadding(new Insets(20, 20, 20, 20));

		Scene scene = new Scene(vbox);
		window.setScene(scene);
		window.showAndWait();
		
		return textId;
	}
	
	/**
	 * Method that returns whether on not the files for the scenario_id are present
	 * inside the medialab folder.
	 * 
	 * @param id	the scenario_id that we want to check if the files exist
	 * @return		boolean true if both files are present, false if one ore both
	 * 				are missing from the desired folder
	 */
	public static boolean searchFile(int id) {
		return new File("src/medialab/player_" + String.valueOf(id) + ".txt").exists() && new File("src/medialab/enemy_" + String.valueOf(id) + ".txt").exists();
	}
	
}
