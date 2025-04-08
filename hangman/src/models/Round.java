package models;

import java.util.*;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class Round {
	private String winner, word;
	private int shots;
	
	public Round(String winner, String word, int shots) {
		super();
		this.winner = winner;
		this.word = word;
		this.shots = shots;
	}
	
	public String getWinner() {
		return winner;
	}
	
	public void setWinner(String winner) {
		this.winner = winner;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public int getShots() {
		return shots;
	}
	
	public void setShots(int shots) {
		this.shots = shots;
	}
	
	public static void displayRounds (String title, String message, LinkedList<Round> rounds) {
		message = message.replace("?", String.valueOf(rounds.size()));
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		Label label = new Label();
		label.setText(message);
		label.setFont(Font.font(null,FontWeight.BOLD, 25));
		GridPane displayObjects = new GridPane();
	    displayObjects.setStyle("-fx-background-color: #FAFAD2;");	
	    ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(35);
	    ColumnConstraints column2 = new ColumnConstraints();
	    column2.setPercentWidth(30);
	    ColumnConstraints column3 = new ColumnConstraints();
	    column3.setPercentWidth(35);
	    displayObjects.getColumnConstraints().addAll(column1, column2, column3);
	    displayObjects.setGridLinesVisible(true);
	    
	    Text wordChosen = new Text("Chosen Word");
	    wordChosen.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(wordChosen, VPos.CENTER);
		GridPane.setHalignment(wordChosen, HPos.CENTER);
	    displayObjects.add(wordChosen, 0, 0);
	    
	    Text noGuesses = new Text("No of Guesses");
	    noGuesses.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(noGuesses, VPos.CENTER);
		GridPane.setHalignment(noGuesses, HPos.CENTER);
	    displayObjects.add(noGuesses, 1, 0);
	    
	    Text result = new Text("Winner");
	    result.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(result, VPos.CENTER);
		GridPane.setHalignment(result, HPos.CENTER);
	    displayObjects.add(result, 2, 0);
	    
	    int i = 1;
		for (Round round : rounds) {
			
			Text wC = new Text(round.getWord());
			wC.setFont(new Font(20));
		    GridPane.setValignment(wC, VPos.CENTER);
			GridPane.setHalignment(wC, HPos.CENTER);
		    displayObjects.add(wC, 0, i);
		    Text guess = new Text(String.valueOf(round.getShots()));
		    guess.setFont(new Font(20));
		    GridPane.setValignment(guess, VPos.CENTER);
			GridPane.setHalignment(guess, HPos.CENTER);
		    displayObjects.add(guess, 1, i);
		    Text w = new Text(round.getWinner());
		    w.setFont(new Font(20));
		    GridPane.setValignment(w, VPos.CENTER);
			GridPane.setHalignment(w, HPos.CENTER);
		    displayObjects.add(w, 2, i);
		    i++;
		}
		VBox vbox = new VBox(20);
		vbox.setPadding(new Insets(20, 20, 20, 20));

		vbox.getChildren().addAll(label, displayObjects);
		vbox.setAlignment(Pos.CENTER);
        vbox.setPrefSize(800, 300);

		Scene scene = new Scene(vbox);
		window.setScene(scene);
		window.showAndWait();
	    
	}
}
