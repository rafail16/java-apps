package application;

import java.io.File;
import java.util.*;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.stage.*;
import models.*;
import players.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;


public class Main extends Application {
	Stage window;
	private boolean end = false;
	TextField vertical, horizontal;
	Text shotsRemaining, activeShipsYou, totalPointsYou, succShotsYou, activeShipsOpp, totalPointsOpp, succShotsOpp;
	private Opponent opponent = new Opponent();
	private User user = new User();
	int[] sizeShip = {5,4,3,3,2};
	HBox hbox;
	int scenario_id = -1, totalShots = 0;
	boolean startingPlayer; //true if player starts first false if opponent starts first 
	
	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		window.setTitle("Medialab Battleship");
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});
		BorderPane p = new BorderPane();
		p.setStyle("-fx-background-color: #FFFFFF;");	
		Scene scene = new Scene(p);
		
		MenuBar menubar = new MenuBar(); 
		Menu appMenu = new Menu("Application"); 
		
	    MenuItem startApp = new MenuItem("Start"); 
	    startApp.setOnAction(e -> start());
	    
	    MenuItem loadApp = new MenuItem("Load");
	    loadApp.setOnAction(e -> load("Resuming Game!"));
	    
	    MenuItem helpApp = new MenuItem("App info");
	    helpApp.setOnAction(e -> appInfo());
	    
	    MenuItem exitApp = new MenuItem("Exit");
	    exitApp.setOnAction(e -> closeProgram());
	    
	    Menu detailsMenu = new Menu("Details");  
	    
	    MenuItem enShipDet = new MenuItem("Enemy Ships"); 
	    enShipDet.setOnAction(e -> enemyShips());
	    
	    MenuItem plShotDet = new MenuItem("Player Shots");  
	    plShotDet.setOnAction(e -> recentShots("Player Shots", "Last ? player shots (from most recent)", user.getShots()));
	    
	    MenuItem enShotDet = new MenuItem("Enemy Shots");  
	    enShotDet.setOnAction(e -> recentShots("Enemy Shots", "Last ? enemy shots (from most recent)", opponent.getShots()));
	    
	    appMenu.getItems().addAll(startApp, loadApp, new SeparatorMenuItem(), helpApp, new SeparatorMenuItem(), exitApp);  
	    p.setTop(menubar);  
        detailsMenu.getItems().addAll(enShipDet, plShotDet, enShotDet);  
        menubar.getMenus().addAll(appMenu, detailsMenu);
	    
	    VBox vbox = new VBox(15);
	    vbox.setPadding(new Insets(20, 20, 20, 20));
	   
	    
	    //create upper part of the screen
	    GridPane displayObjects = new GridPane();
	    displayObjects.setStyle("-fx-background-color: #FAFAD2;");	    
	    
	    ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(30);
	    ColumnConstraints column2 = new ColumnConstraints();
	    column2.setPercentWidth(35);
	    ColumnConstraints column3 = new ColumnConstraints();
	    column3.setPercentWidth(35);
	    displayObjects.getColumnConstraints().addAll(column1, column2, column3);
	    displayObjects.setGridLinesVisible(true);
	    
	    Text yourLabel = new Text("You");
	    yourLabel.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(yourLabel, VPos.CENTER);
	    GridPane.setHalignment(yourLabel, HPos.CENTER);
	    displayObjects.add(yourLabel, 1, 0);
	    
	    Text opponetLabel = new Text("Opponent");
	    opponetLabel.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(opponetLabel, VPos.CENTER);
		GridPane.setHalignment(opponetLabel, HPos.CENTER);
	    displayObjects.add(opponetLabel, 2, 0);
	    
	    Text activeShips = new Text("Active ships: ");
	    activeShips.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(activeShips, VPos.CENTER);
		GridPane.setHalignment(activeShips, HPos.CENTER);
	    displayObjects.add(activeShips, 0, 1);
	    
	    Text totalPoints = new Text("Total points: ");
	    totalPoints.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(totalPoints, VPos.CENTER);
		GridPane.setHalignment(totalPoints, HPos.CENTER);
	    displayObjects.add(totalPoints, 0, 2);
	    
	    Text succShots = new Text("Succesful Shots (%): ");
	    succShots.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(succShots, VPos.CENTER);
		GridPane.setHalignment(succShots, HPos.CENTER);
	    displayObjects.add(succShots, 0, 3);
	    
	    activeShipsYou = new Text(String.valueOf("5"));
	    activeShipsYou.setFont(new Font(20));
	    GridPane.setValignment(activeShipsYou, VPos.CENTER);
		GridPane.setHalignment(activeShipsYou, HPos.CENTER);
	    displayObjects.add(activeShipsYou, 1, 1);
	    
	    totalPointsYou = new Text(String.valueOf("0"));
	    totalPointsYou.setFont(new Font(20));
	    GridPane.setValignment(totalPointsYou, VPos.CENTER);
		GridPane.setHalignment(totalPointsYou, HPos.CENTER);
	    displayObjects.add(totalPointsYou, 1, 2);
	    
	    succShotsYou = new Text(String.valueOf("0"));
	    succShotsYou.setFont(new Font(20));
	    GridPane.setValignment(succShotsYou, VPos.CENTER);
		GridPane.setHalignment(succShotsYou, HPos.CENTER);
	    displayObjects.add(succShotsYou, 1, 3);
	    
	    activeShipsOpp = new Text(String.valueOf("5"));
	    activeShipsOpp.setFont(new Font(20));
	    GridPane.setValignment(activeShipsOpp, VPos.CENTER);
		GridPane.setHalignment(activeShipsOpp, HPos.CENTER);
	    displayObjects.add(activeShipsOpp, 2, 1);
	    
	    totalPointsOpp = new Text(String.valueOf("0"));
	    totalPointsOpp.setFont(new Font(20));
	    GridPane.setValignment(totalPointsOpp, VPos.CENTER);
		GridPane.setHalignment(totalPointsOpp, HPos.CENTER);
	    displayObjects.add(totalPointsOpp, 2, 2);
	    
	    succShotsOpp = new Text(String.valueOf("0"));
	    succShotsOpp.setFont(new Font(20));
	    GridPane.setValignment(succShotsOpp, VPos.CENTER);
		GridPane.setHalignment(succShotsOpp, HPos.CENTER);
	    displayObjects.add(succShotsOpp, 2, 3);
	    
	    vbox.getChildren().add(displayObjects);
	    
	    //create the two boards
	    GridPane boardLabels = new GridPane();
	    
	    ColumnConstraints c1 = new ColumnConstraints();
	    c1.setPercentWidth(50);
	    ColumnConstraints c2 = new ColumnConstraints();
	    c2.setPercentWidth(50);
	    boardLabels.getColumnConstraints().addAll(c1, c2);

	    Text playerBoardLabel = new Text("Your Board");
	    playerBoardLabel.setFont(Font.font(null,FontWeight.BOLD, 30));
	    GridPane.setValignment(playerBoardLabel, VPos.CENTER);
		GridPane.setHalignment(playerBoardLabel, HPos.CENTER);
		boardLabels.add(playerBoardLabel, 0, 0);
		
	    Text opponetBoardLabel = new Text("Opponent's Board");
	    opponetBoardLabel.setFont(Font.font(null,FontWeight.BOLD, 30));
	    GridPane.setValignment(opponetBoardLabel, VPos.CENTER);
		GridPane.setHalignment(opponetBoardLabel, HPos.CENTER);
		boardLabels.add(opponetBoardLabel, 1, 0);
		vbox.getChildren().add(boardLabels);
		
		
	    hbox = new HBox(40);
		hbox.setPadding(new Insets(10, 0, 10, 0));
	    hbox.getChildren().addAll(createSpacer(), user.getBoard(), createSpacer(), opponent.getBoard(), createSpacer());
	    hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(hbox);
		
		//create lower part of screen
		Label col = new Label();
		col.setText("Column: ");
		col.setTextAlignment(TextAlignment.CENTER);
		col.setFont(new Font(15));	
		
		vertical = new TextField();
		vertical.setAlignment(Pos.CENTER);
		vertical.setPromptText("column selection");
		vertical.setPrefWidth(150);
		
		Label row = new Label();
		row.setText("Row: ");
		row.setTextAlignment(TextAlignment.CENTER);
		row.setFont(new Font(15));	
		
		horizontal = new TextField();
		horizontal.setAlignment(Pos.CENTER);
		horizontal.setPromptText("row selection");
		horizontal.setPrefWidth(150);
		
		Button applyMove = new Button("Attack");
		applyMove.setFont(new Font(15));
		applyMove.setOnAction(e -> {
			if(!end) playerMove();
			else {
				String result; 
				if(user.getActiveShips() == 0 || opponent.getActiveShips() == 0) result = user.getActiveShips() > 0 ? "Congratulations you have won by shinking all opponent's ships. You can restart if you want." : "Opponent has won by shinking all your ships. Better luck next time. You can restart if you want.";
				else result = user.getTotalPoints() > opponent.getTotalPoints() ? "Congratulations you have won by gaining more points. You can restart if you want." : "Opponent has won by gaining more points. Better luck next time. You can restart if you want.";
				Messages.showMessage("Game Has Ended", result);
			}
		});
		
		HBox moveBox = new HBox(20);
		moveBox.setAlignment(Pos.CENTER);
		moveBox.getChildren().addAll(col, vertical, row, horizontal, applyMove);
		vbox.getChildren().add(moveBox);
		
		//add counter to show the remaining shots
		shotsRemaining = new Text("Remaining Shots: 40");
	    shotsRemaining.setFont(Font.font(null,FontWeight.BOLD, 25));
	    vbox.setAlignment(Pos.CENTER);
	    vbox.getChildren().add(shotsRemaining);
	    
		p.setCenter(vbox);
		load("Nothing chosen. Choose from Application->load to play!");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene);
		window.show();
	}
	
	public static void main(final String[] args) {
        launch(args);
    }
	
	private Node createSpacer() {
	    final Region spacer = new Region();
	    HBox.setHgrow(spacer, Priority.ALWAYS);
	    return spacer;
	}
	
	private void appInfo() {
		Information.display("App Info", "Details About the app!");
	}
	
	private void closeProgram() {
		Boolean answer = ConfirmBox.display("Exit", "Do you want to exit?");
		if(answer) window.close();
	}
	
	private void enemyShips() {
		DisplayBox.displayShips("Enemy Ships", "States of the enemy ships", opponent.getShips());
	}
	
	private void recentShots(String title, String message, Deque<Shot> shots) {
		DisplayBox.displayShots(title, message, shots);
	}
	
	private void load(String message) {
		int answer = DecisionBox.display("Load Scenario", "Choose the id of the scenario!");
		if(answer == -1) {
			Messages.showError("File Not Found", "Please give a valid no of scenario");
			return;
		}
		else if (answer == -2) {
			Messages.showMessage("Continue", message);
			return;
		}
		Board temp_enemy = new Board(true), temp_player = new Board(false);
		try {
			File myObj = new File("src/medialab/player_" + String.valueOf(answer) + ".txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String[] data = myReader.nextLine().split(",");
				int typeShip = Integer.parseInt(data[0]), x_temp = Integer.parseInt(data[1]), y_temp = Integer.parseInt(data[2]), or_temp = Integer.parseInt(data[3]);
				temp_player.placeShips(typeShip, y_temp, x_temp, or_temp, sizeShip[typeShip-1]);
			}
			myReader.close();
			myObj = new File("src/medialab/enemy_" + String.valueOf(answer) + ".txt");
			myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String[] data = myReader.nextLine().split(",");
				int typeShip = Integer.parseInt(data[0]), x_temp = Integer.parseInt(data[1]), y_temp = Integer.parseInt(data[2]), or_temp = Integer.parseInt(data[3]);
				temp_enemy.placeShips(typeShip, y_temp, x_temp, or_temp, sizeShip[typeShip-1]);
			}
			myReader.close();
			restartVariables();
			user.setBoard(temp_player);
			opponent.setBoard(temp_enemy); 
			hbox.getChildren().clear();
		    hbox.getChildren().addAll(createSpacer(), temp_player, createSpacer(), temp_enemy, createSpacer());
			scenario_id = answer;
			startingPlayer = Math.random() > 0.5;
			String whoStarts = startingPlayer ?  "you are" : "the computer is";
			Messages.showMessage("New Game", "Starting new game, "+ whoStarts + " going first!");
			if(!startingPlayer) enemyMove();
		} catch (Exception e) {
			Messages.showError(e.getClass().getSimpleName(), e.getMessage());
			restartVariables();
			user.setBoard(new Board(false));
			opponent.setBoard(new Board(true)); 
			hbox.getChildren().clear();
		    hbox.getChildren().addAll(createSpacer(), new Board(false), createSpacer(), new Board(true), createSpacer());
			scenario_id = -1;
		}
	}
	
	private void start() {
		if(scenario_id == -1) {
			Messages.showError("No scenario", "Please select valid senario. Cannot restart.");
			return;
		}
		Boolean answer = ConfirmBox.display("Exit", "Do you want to restart scenario?");
		if(answer) {
			Board temp_enemy = new Board(true), temp_player = new Board(false);
			try {
				File myObj = new File("src/medialab/player_" + String.valueOf(scenario_id) + ".txt");
				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					String[] data = myReader.nextLine().split(",");
					int typeShip = Integer.parseInt(data[0]), x_temp = Integer.parseInt(data[1]), y_temp = Integer.parseInt(data[2]), or_temp = Integer.parseInt(data[3]);
					temp_player.placeShips(typeShip, y_temp, x_temp, or_temp, sizeShip[typeShip-1]);
				}
				myReader.close();
				myObj = new File("src/medialab/enemy_" + String.valueOf(scenario_id) + ".txt");
				myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					String[] data = myReader.nextLine().split(",");
					int typeShip = Integer.parseInt(data[0]), x_temp = Integer.parseInt(data[1]), y_temp = Integer.parseInt(data[2]), or_temp = Integer.parseInt(data[3]);
					temp_enemy.placeShips(typeShip, y_temp, x_temp, or_temp, sizeShip[typeShip-1]);
				}
				myReader.close();
				restartVariables();
				user.setBoard(temp_player);
				opponent.setBoard(temp_enemy); 
				hbox.getChildren().clear();
			    hbox.getChildren().addAll(createSpacer(), temp_player, createSpacer(), temp_enemy, createSpacer());
				startingPlayer = Math.random() > 0.5;
				String whoStarts = startingPlayer ?  "You" : "The computer";
				Messages.showMessage("Restarting", "Restart current scenario! "+ whoStarts + " will go first!");
				if(!startingPlayer) enemyMove();
			} catch (Exception e) {
				Messages.showError(e.getClass().getSimpleName(), e.getMessage()); 
			}
		}
	}
	
	private void restartVariables() {
		end = false;
		user.reset();
		opponent.reset();
		activeShipsOpp.setText(String.valueOf("5"));
		activeShipsYou.setText(String.valueOf("5"));
		totalPointsYou.setText(String.valueOf("0"));
		totalPointsOpp.setText(String.valueOf("0"));
		succShotsYou.setText(String.valueOf("0"));
		succShotsOpp.setText(String.valueOf("0"));
		shotsRemaining.setText("Remaining Shots: 40");
		totalShots = 0;
	}
	
	private void playerMove() {
		if (scenario_id == -1) {
			Messages.showError("No scenario selected", "Select a valid scenario from Application->Load to play!");
			return;
		}
		if (!horizontal.getText().equals("") && !vertical.getText().equals("")) {
			int x = Integer.parseInt(vertical.getText()), y = Integer.parseInt(horizontal.getText());
			if (x < 0 || x > 9 || y < 0 || y > 9) {
				Messages.showError("Wrong inputs", "Choose row and column between 0 and 9.");
				horizontal.clear();
				vertical.clear();
			}
			else {
				if (opponent.getBoard().isHit(x, y)) Messages.showError("Wrong Input", "Choose cell not yet shot!");
				else {
					user.move(opponent, x, y);
					if(startingPlayer) totalShots++;
					activeShipsOpp.setText(String.valueOf(opponent.getActiveShips()));
					totalPointsYou.setText(String.valueOf(user.getTotalPoints()));
					double s = user.getSuccesfulShots(), t = totalShots, res = s/t*100;
					succShotsYou.setText(String.format("%.2f",res));
					if (opponent.getActiveShips() == 0) {
						end = true;
						Messages.showMessage("Game Has Ended", "Congratulations you have won by shinking your opponent's ships. You can restart if you want.");
					}
					else {
						if(!startingPlayer) check_end();
						if (!end) enemyMove();
					}
				}
				horizontal.clear();
				vertical.clear();
			}
		}
	}
	
	private void enemyMove() {
		opponent.move(user);
		if(!startingPlayer) totalShots++;
		activeShipsYou.setText(String.valueOf(user.getActiveShips()));
		totalPointsOpp.setText(String.valueOf(opponent.getTotalPoints()));
		double s = opponent.getSuccesfulShots(), t = totalShots, res = s/t*100;
		succShotsOpp.setText(String.format("%.2f",res));
		if (user.getActiveShips() == 0) {
			end = true;
			Messages.showMessage("Game Has Ended", "Opponent has won by shinking your ships. Better luck next time. If you want you can restart.");
		}
		if (startingPlayer) check_end();
	}
	
	private void check_end() {
		shotsRemaining.setText("Remaining Shots: " + String.valueOf(40 - totalShots));
		if (totalShots == 40) {
			end = true;
			String result = user.getTotalPoints() > opponent.getTotalPoints() ? "Congratulations you have won by gaining more points. You can restart if you want." : "Opponent has won by gaining more points. Better luck next time. You can restart if you want.";
			Messages.showMessage("Game Has Ended", result);
		}
	}
}
