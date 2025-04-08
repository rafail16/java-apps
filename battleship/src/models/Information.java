package models;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.*;

public class Information {
	static TreeView<String> tree;
	static Label showInfo;
	
	public static void display (String title, String message) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		
		Label label = new Label();
		label.setText(message);
		label.setFont(Font.font(null,FontWeight.BOLD, 25));
		
		TreeItem<String> root, app, details, general, menu, colors;
		
		root = new TreeItem<>();
		root.setExpanded(false);
		
		general = makeBranch("General Info", root);
		makeBranch("Move State", general);
		makeBranch("End of game", general);
		
		menu = makeBranch("Menu Items", root);
		app = makeBranch("Application", menu);
		makeBranch("Start", app);
		makeBranch("Load", app);
		makeBranch("App Info", app);
		makeBranch("Exit", app);		
		
		details = makeBranch("details", menu);
		makeBranch("Enemy Ships", details);
		makeBranch("Player Shots", details);
		makeBranch("Enemy Shots", details);
		
		colors = makeBranch("Grid Colors", root);
		makeBranch("Blue", colors);
		makeBranch("Grey", colors);
		makeBranch("White", colors);
		makeBranch("Red", colors);
		
		tree = new TreeView<>(root);
		tree.setShowRoot(false);
		tree.setMaxHeight(150);
		tree.getSelectionModel().selectedItemProperty().addListener((v, oldVal, newVal) -> {
			if(newVal != null) {
				displayInfo(newVal.getValue());
			}
		});
		showInfo = new Label("");
	    showInfo.setFont(new Font(20));
	    showInfo.setWrapText(true);
	    
		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(label, tree, showInfo);
		vbox.setPrefSize(400, 400);
		vbox.setPadding(new Insets(20, 20, 20, 20));
		Scene scene = new Scene(vbox);
		window.setScene(scene);
		window.showAndWait();
	}
	
	public static TreeItem<String> makeBranch(String name, TreeItem<String> parent){
		TreeItem<String> item = new TreeItem<String>(name);
		item.setExpanded(false);
		parent.getChildren().add(item);
		return item;
	}
	
	public static void displayInfo(String name) {
		switch (name) {
		case "Move State": 
			showInfo.setText("Chose your next move by typing in the coordinate boxes the desired target and press the button. Then the computer will make it's move as well.");
			break;
		case "End of game":
			showInfo.setText("The game will end if one player loses all the ships or 40 moves by each player have been made. The winner is the player that has ships (if only one) or the player with the more points.");
			break;
		case "Start":
			showInfo.setText("Start action lets you restart the current scenario. Each time a random player to begin is chosen.");
			break;
		case "Load":
			showInfo.setText("Load action lets you start a new game by choosing a valid with no errors scenario id.  Each time a random player to begin is chosen.");
			break;
		case "App Info":
			showInfo.setText("App Info takes you to this window that you can find information for the different actions that this app allows the user to make.");
			break;
		case "Exit":
			showInfo.setText("Enables the user to close the app not only by the X button in top left of app screen. You will be asked if you for sure want to leave.");
			break;
		case "White":
			showInfo.setText("White color represents that the fired shot has missed a target.");
			break;
		case "Grey":
			showInfo.setText("Grey color is for the ships of the player. The opponent's ships are not visible.");
			break;
		case "Blue":
			showInfo.setText("Blue color is for all the water in the player's board or unknown cells (either ship or nothing) in opponent's board.");
			break;
		case "Red":
			showInfo.setText("Red is shown when a player makes a succesful shot and hits one of the ships.");
			break;
		case "Enemy Ships":
			showInfo.setText("The player can see by choosing this option all the states for the different ships of the opponent.");
			break;
		case "Player Shots":
			showInfo.setText("It shows max 5 last shots that the player (you) has made and the result of them. The shots are being displayed in descending chronological order (most recent to the top).");
			break;
		case "Enemy Shots":
			showInfo.setText("It shows max 5 last shots that the opponetn has made and the result of them. The shots are being displayed in descending chronological order (most recent to the top).");
		}
	}
}
