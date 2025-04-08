package models;

import java.util.Deque;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class DisplayBox {
	static String[] names = {"Carrier", "Battleship", "Cruiser", "Submarine", "Destroyer"};
	public static void displayShips (String title, String message, int[] ships) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		Label label = new Label();
		label.setText(message);
		label.setFont(Font.font(null,FontWeight.BOLD, 25));
		
		GridPane displayObjects = new GridPane();
	    displayObjects.setStyle("-fx-background-color: #FAFAD2;");
	    
	    ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(50);
	    ColumnConstraints column2 = new ColumnConstraints();
	    column2.setPercentWidth(50);
	    displayObjects.getColumnConstraints().addAll(column1, column2);
	    displayObjects.setGridLinesVisible(true);
	    
	    Text shipName = new Text("Ship Name");
	    shipName.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(shipName, VPos.CENTER);
		GridPane.setHalignment(shipName, HPos.CENTER);
	    displayObjects.add(shipName, 0, 0);
	    
	    Text shipState = new Text("Ship State");
	    shipState.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(shipState, VPos.CENTER);
		GridPane.setHalignment(shipState, HPos.CENTER);
	    displayObjects.add(shipState, 1, 0);
	    
	    for (int i = 1; i <= 5; i ++) {
	    	Text name = new Text(names[i-1]);
		    name.setFont(new Font(20));
		    GridPane.setValignment(name, VPos.CENTER);
			GridPane.setHalignment(name, HPos.CENTER);
		    displayObjects.add(name, 0, i);
		    String state = ships[i-1] == 1 ? "Hit" : ships[i-1] == 2 ? "Sunk" : "Unharmed";
		    Text stateText = new Text(state);
		    stateText.setFont(new Font(20));
		    GridPane.setValignment(stateText, VPos.CENTER);
			GridPane.setHalignment(stateText, HPos.CENTER);
		    displayObjects.add(stateText, 1, i);
	    }
		VBox vbox = new VBox(20);
		vbox.setPadding(new Insets(20, 20, 20, 20));

		vbox.getChildren().addAll(label, displayObjects);
		vbox.setAlignment(Pos.CENTER);
        vbox.setPrefSize(350, 250);

		Scene scene = new Scene(vbox);
		window.setScene(scene);
		window.showAndWait();
		
	}
	public static void displayShots (String title, String message, Deque<Shot> shots) {
		int size = shots.size();
		message = message.replace("?", String.valueOf(size));
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		Label label = new Label();
		label.setText(message);
		label.setFont(Font.font(null,FontWeight.BOLD, 25));
		
		GridPane displayObjects = new GridPane();
	    displayObjects.setStyle("-fx-background-color: #FAFAD2;");	
	    
	    ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(30);
	    ColumnConstraints column2 = new ColumnConstraints();
	    column2.setPercentWidth(30);
	    ColumnConstraints column3 = new ColumnConstraints();
	    column3.setPercentWidth(40);
	    displayObjects.getColumnConstraints().addAll(column1, column2, column3);
	    displayObjects.setGridLinesVisible(true);
	    
	    Text coords = new Text("Coordinates");
	    coords.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(coords, VPos.CENTER);
		GridPane.setHalignment(coords, HPos.CENTER);
	    displayObjects.add(coords, 0, 0);
	    
	    Text result = new Text("Result");
	    result.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(result, VPos.CENTER);
		GridPane.setHalignment(result, HPos.CENTER);
	    displayObjects.add(result, 1, 0);
	    
	    Text typeShip = new Text("Type of Ship");
	    typeShip.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(typeShip, VPos.CENTER);
		GridPane.setHalignment(typeShip, HPos.CENTER);
	    displayObjects.add(typeShip, 2, 0);
	    
	    int i = 1;
		for (Shot shot : shots) {
			String x = String.valueOf(shot.getX()), y = String.valueOf(shot.getY());
			int made = shot.getMade(), type = shot.getTypeOfShip();
			String madeS = made == 1 ? "Made" : "Missed", shipName = type == -1 ? "" : names[type];
			Text xy = new Text("("+ x + ", " + y +")");
			xy.setFont(new Font(20));
		    GridPane.setValignment(xy, VPos.CENTER);
			GridPane.setHalignment(xy, HPos.CENTER);
		    displayObjects.add(xy, 0, i);
		    Text stateText = new Text(madeS);
		    stateText.setFont(new Font(20));
		    GridPane.setValignment(stateText, VPos.CENTER);
			GridPane.setHalignment(stateText, HPos.CENTER);
		    displayObjects.add(stateText, 1, i);
		    Text types = new Text(shipName);
		    types.setFont(new Font(20));
		    GridPane.setValignment(types, VPos.CENTER);
			GridPane.setHalignment(types, HPos.CENTER);
		    displayObjects.add(types, 2, i);
		    i++;
		}
		VBox vbox = new VBox(20);
		vbox.setPadding(new Insets(20, 20, 20, 20));

		vbox.getChildren().addAll(label, displayObjects);
		vbox.setAlignment(Pos.CENTER);
        vbox.setPrefSize(500, 300);

		Scene scene = new Scene(vbox);
		window.setScene(scene);
		window.showAndWait();
		
	}
}
