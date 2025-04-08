package application;
	
import javafx.application.Application;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.util.*;
import models.*;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import exceptions.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.*;



public class Main extends Application {
	Stage window;
	Text successChoices, availableWords, totalPoints;
	TextField letter, position;
	LinkedList<Round> rounds = new LinkedList<Round>();
	List<Label> lettersOutput;
	ImageView imageLives;
	GridPane wordGuesses;
	VBox midRight;
	boolean active = false, solved = false;
	int length = 0, currentPoints = 0, lives = 6, totalGuesses = 0;
	String word;
	List<Integer> solution;
	ArrayList<Map<Character, Integer>> possibilities;
	Set<String> set = new HashSet<String>(), possibleSet = new HashSet<String>();
	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		window.setTitle("Medialab Hangman");
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: #FFFFFF;");	
		Scene scene = new Scene(root, 1400,800);
		
		MenuBar menuBar = new MenuBar();
		
		Menu appMenu = new Menu("Application");
		MenuItem startApp = new MenuItem("Start");
	    startApp.setOnAction(e -> start());
	    
	    MenuItem loadApp = new MenuItem("Load");
	    loadApp.setOnAction(e -> load("Resuming Game!"));
	    
	    MenuItem create = new MenuItem("Create");
	    create.setOnAction(e -> create());
	    
	    MenuItem exitApp = new MenuItem("Exit");
	    exitApp.setOnAction(e -> closeProgram());
		appMenu.getItems().addAll(startApp, loadApp, create, exitApp);
		
		Menu detailsMenu = new Menu("Details");
		MenuItem dictionaryDisp = new MenuItem("Dictionary");
	    dictionaryDisp.setOnAction(e -> DictInfo.display(set));
	    
	    MenuItem roundsDisp= new MenuItem("Rounds");
	    roundsDisp.setOnAction(e -> Round.displayRounds("Most Recent rounds played", "Last ? rounds (most recent on top)", rounds));
	    
	    MenuItem solutionDisp = new MenuItem("Solution");
	    solutionDisp.setOnAction(e -> solutionDisp(false));
	    
		detailsMenu.getItems().addAll(dictionaryDisp, roundsDisp, solutionDisp);
		
		root.setTop(menuBar);
		menuBar.getMenus().addAll(appMenu, detailsMenu);
		
		VBox vbox = new VBox(15);
	    vbox.setPadding(new Insets(30, 20, 20, 20));
		
		GridPane displaying = new GridPane();
		displaying.setStyle("-fx-background-color: #ffc0cb;");	
		displaying.setMaxWidth(500);
		ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(60);
	    ColumnConstraints column2 = new ColumnConstraints();
	    column2.setPercentWidth(40);
	    displaying.getColumnConstraints().addAll(column1, column2);
	    displaying.setGridLinesVisible(true);
	    
	    Text availableWordsText = new Text("Available words: ");
	    availableWordsText.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(availableWordsText, VPos.CENTER);
		GridPane.setHalignment(availableWordsText, HPos.CENTER);
		displaying.add(availableWordsText, 0, 0);
	    
	    Text totalPointsText = new Text("Total points: ");
	    totalPointsText.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(totalPointsText, VPos.CENTER);
		GridPane.setHalignment(totalPointsText, HPos.CENTER);
		displaying.add(totalPointsText, 0, 1);
	    
	    Text successChoicesText = new Text("Succesful Choices: ");
	    successChoicesText.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(successChoicesText, VPos.CENTER);
		GridPane.setHalignment(successChoicesText, HPos.CENTER);
		displaying.add(successChoicesText, 0, 2);
		
		availableWords = new Text(String.valueOf(set.size()));
		availableWords.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(availableWords, VPos.CENTER);
		GridPane.setHalignment(availableWords, HPos.CENTER);
		displaying.add(availableWords, 1, 0);
	    
	    totalPoints = new Text("0");
	    totalPoints.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(totalPoints, VPos.CENTER);
		GridPane.setHalignment(totalPoints, HPos.CENTER);
		displaying.add(totalPoints, 1, 1);
	    
	    successChoices = new Text("0");
	    successChoices.setFont(Font.font(null,FontWeight.BOLD, 20));
	    GridPane.setValignment(successChoices, VPos.CENTER);
		GridPane.setHalignment(successChoices, HPos.CENTER);
		displaying.add(successChoices, 1, 2);
		
		vbox.getChildren().addAll(displaying, createVerticalSpacer());
		
		//create middle part of screen
		VBox midLeft = new VBox(20);
		midLeft.setAlignment(Pos.CENTER);
		wordGuesses = new GridPane();
		wordGuesses.setPrefHeight(90);
		wordGuesses.setVgap(5); 
		wordGuesses.setHgap(5);
		wordGuesses.setAlignment(Pos.CENTER);

		
		imageLives = new ImageView();
		File file = new File("src/images/6.png");
	    Image image = new Image(file.toURI().toString());
	    imageLives.setImage(image);
	    imageLives.setFitWidth(200);
	    imageLives.setPreserveRatio(true);
		
	    
	    midLeft.getChildren().addAll(createHorizontalSpacer(),wordGuesses, createHorizontalSpacer(), imageLives, createHorizontalSpacer());
	    
	    
	    midRight = new  VBox(5);
	    //midRight.setVgap(5); 
	    //midRight.setHgap(10);
	    midRight.setAlignment(Pos.CENTER);

		GridPane middle = new GridPane();
		ColumnConstraints c1 = new ColumnConstraints();
	    c1.setPercentWidth(50);
	    ColumnConstraints c2 = new ColumnConstraints();
	    c2.setPercentWidth(50);
	    middle.getColumnConstraints().addAll(c1, c2);
	    
	    ScrollPane s1 = new ScrollPane();
		GridPane.setValignment(midLeft, VPos.CENTER);
	    GridPane.setHalignment(midLeft, HPos.CENTER);
	    middle.add(midLeft, 0, 0);
	    GridPane.setValignment(midRight, VPos.CENTER);
	    GridPane.setHalignment(midRight, HPos.CENTER);
	    s1.setContent(midRight);
	    s1.setFitToWidth(true);
	    s1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

	    middle.add(s1, 1, 0);
		vbox.getChildren().addAll(middle, createVerticalSpacer());
		
		
		//create lower part of screen
		Label first = new Label();
		first.setText("Position: ");
		first.setTextAlignment(TextAlignment.CENTER);
		first.setFont(new Font(15));	
				
		position = new TextField();
		position.setAlignment(Pos.CENTER);
		position.setPromptText("position selection");
		position.setPrefWidth(150);
		position.textProperty().addListener((observable, oldValue, newValue) -> {
	        if (!newValue.matches("\\d*")) {
	        	position.setText(newValue.replaceAll("[^\\d]", ""));
	        }
	    });
				
		Label second = new Label();
		second.setText("Letter: ");
		second.setTextAlignment(TextAlignment.CENTER);
		second.setFont(new Font(15));	
				
		letter = new TextField();
		letter.setAlignment(Pos.CENTER);
		letter.setPromptText("letter selection");
		letter.setPrefWidth(150);
		letter.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
		letter.textProperty().addListener((observable, oldValue, newValue) -> {
	        if (newValue.length()>1) {
	        	letter.setText(newValue.substring(0, 1));
	        }
	        if(!newValue.matches("^[A-Z]*$")){
	        	letter.setText("");
	        }
	    });
	    
		Button guess = new Button("Guess");
		guess.setFont(new Font(15));
		guess.setOnAction(e -> {
			if(solved) Message.showMessage("Ended", "Previous game has ended and you won!");
			else if(lives == 0) Message.showMessage("Ended", "Previous game has ended and you lost! Better luck next time!");
			else guessLetter();
		});
		
		HBox guessBox = new HBox(20);
		guessBox.setAlignment(Pos.CENTER);
		guessBox.getChildren().addAll(first, position, second, letter, guess);
		vbox.getChildren().add(guessBox);
		
		Button next = new Button("Next Word");
		next.setFont(new Font(25));
		next.setOnAction(e -> {
			solutionDisp(true);
		});
		
	    vbox.setAlignment(Pos.CENTER);
	    vbox.getChildren().add(next);
		
	    root.setCenter(vbox);
	    load("Nothing chosen. Choose from Application->load to play!");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private Node createVerticalSpacer() {
	    final Region spacer = new Region();
	    VBox.setVgrow(spacer, Priority.ALWAYS);
	    return spacer;
	}
	private Node createHorizontalSpacer() {
	    final Region spacer = new Region();
	    HBox.setHgrow(spacer, Priority.ALWAYS);
	    return spacer;
	}
	private void closeProgram() {
		Boolean answer = ConfirmBox.display("Exit", "Do you want to exit?");
		if(answer) window.close();
	}
	
	private void load(String message) {
		Pair<Integer, String> answer = DecisionBox.display("Load Scenario", "Choose the id of the dictionary!", true);
		if(answer.getKey() == -1) {
			Message.showError("File Not Found", "Please give a valid id of dictionary\n or create a dictionary with that id");
			return;
		}
		else if (answer.getKey() == -2) {
			Message.showMessage("Continue", message);
			return;
		}
		loadDictionary(answer.getValue());
	}
	
	private void loadDictionary(String id) {
		try {
			File myObj = new File("src/medialab/hangman_" + id + ".txt");
			Scanner myReader = new Scanner(myObj);
			int counter = 0;
			set.clear();
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				if(data.length() == 0) continue;
				if(data.length() <6) throw new InvalidRangeException();
				if (data.length() >= 9) counter++;
				if(set.contains(data)) throw new InvalidCountExeception();
				set.add(data);
			}
			myReader.close();
			if(set.size() < 20) throw new UndersizeException();
			if(100*counter/set.size() < 20) throw new UnbalancedException();
			active = true;
			restart();
			rounds = new LinkedList<Round>();
			startGame();
		} catch (Exception e) {
			Message.showError(e.getClass().getSimpleName(), e.getMessage());
			active = false;
			restart();
			rounds = new LinkedList<Round>();
		}
	}
	
	private void start() {
		if(active) {
			boolean answer = ConfirmBox.display("Restart Game", "Want to restart the game with current dictionary");
			if(answer) {
				restart();
				startGame();
				rounds = new LinkedList<Round>();
			}
		}
		else {
			Message.showError("Need dictionary", "You need to load or create dictionary first");
		}
	}
	
	private void create() {
		try {
			Pair<Integer, String> answer = DecisionBox.display("Create Scenario", "Choose the id of the dictionary to create!", false);
			if (answer.getKey() == -2) {
				Message.showMessage("Continue", "Continue Game");
				return;
			}
			set = CreateDictionary.create(answer.getValue());
			if(set.isEmpty()) {
				Message.showError("Error in parsing", "Url not exist, bad format of json object in url given\n or errors in parsing");
				active = false;
				restart();
				rounds = new LinkedList<Round>();
			}
			else {
				active = true;
				restart();
				rounds = new LinkedList<Round>();
				startGame();
			}
		} catch (Exception e) {
			Message.showError(e.getClass().getSimpleName(), e.getMessage());
			active = false;
			restart();
			rounds = new LinkedList<Round>();
		} 
	}
	
	private void startGame() {
		Random rndm = new Random();
        int rndmNumber = rndm.nextInt(set.size());
        word = set.toArray(new String[set.size()])[rndmNumber];
        length = word.length();
        solution = new ArrayList<Integer>();
        possibilities = new ArrayList<Map<Character, Integer>>();
        lettersOutput = new ArrayList<Label>();
        for (int i = 0; i < length; i++) {
        	possibilities.add(new HashMap<Character, Integer>());
        	Label text1 = new Label(String.valueOf(i+1));
        	text1.setPrefWidth(40);
        	text1.setPrefHeight(40);
        	text1.setFont(new Font(25));
        	text1.setStyle("-fx-background-color: yellow; -fx-font-weight: bold; -fx-border-color:black;-fx-border-width: 2;");
        	text1.setAlignment(Pos.CENTER);
        	lettersOutput.add(new Label());
        	lettersOutput.get(i).setPrefWidth(40);
        	lettersOutput.get(i).setPrefHeight(40);
        	lettersOutput.get(i).setFont(new Font(25));
        	lettersOutput.get(i).setStyle("-fx-background-color: #CBC3E3; -fx-font-weight: bold; -fx-border-color:black;-fx-border-width: 2;");
        	lettersOutput.get(i).setAlignment(Pos.CENTER);
        	wordGuesses.add(lettersOutput.get(i), i, 0);
        	wordGuesses.add(text1, i, 1);
        }
        for (String s : set) {
        	if(s.length() == length) {
        		possibleSet.add(s);
        		for (int i = 0; i < s.length(); i++) {
        			Character c = s.charAt(i);
        			if(possibilities.get(i).containsKey(c)) possibilities.get(i).put(c, possibilities.get(i).get(c)+1);
        			else possibilities.get(i).put(c, 1);
                }
        	}
        	
        }
        placePercentages();
//        System.out.println(word);
//        System.out.println(possibleSet);
//        System.out.println(possibilities);
//        System.out.println(possibilities.size());
        
	}
	private void guessLetter() {
		//System.out.println(possibleSet);
		if(active) {
			if(!letter.getText().equals("") && !position.getText().equals("")) {
				int x = Integer.parseInt(position.getText())-1;
				char c = letter.getText().charAt(0);
				if(x > length) Message.showError("Select Valid Number", "Please select a valid number!");
				else if(solution.contains(x)) Message.showError("Position Guessed", "Position already guessed!");
				else if(!possibilities.get(x).containsKey(c)) Message.showError("Select Valid Letter", "Please select a letter from the possibilities!");
				else {
					totalGuesses++;
					if(c != word.charAt(x)) {
						currentPoints = Math.max(0, currentPoints - 15);
						File file = new File("src/images/"+ --lives+ ".png");
					    Image image = new Image(file.toURI().toString());
					    imageLives.setImage(image);
						if(lives == 0) {
							Message.showMessage("Game End", "You have lost the game, better luck next time. The word was " + word);
							rounds.addFirst(new Round("Computer", word, totalGuesses));
							if(rounds.size() > 5) rounds.removeFirst();
						}
						removeWords(x, c);
						placePercentages();
					}
					else {
						lettersOutput.get(x).setText(c+"");
						lettersOutput.get(x).setStyle("-fx-background-color: #90EE90;-fx-font-weight: bold; -fx-border-color:black;-fx-border-width: 2;");
						int percent = possibilities.get(x).get(c)*100/possibleSet.size();
						if(percent< 25) currentPoints = currentPoints + 30;
						else if(percent< 40) currentPoints = currentPoints + 15;
						else if(percent< 60) currentPoints = currentPoints + 10;
						else currentPoints = currentPoints + 5;
						totalPoints.setText(String.valueOf(currentPoints));
						solution.add(x);
						findPossibleWords();
						placePercentages();
						
					}
					totalPoints.setText(String.valueOf(currentPoints));
				}
			}
			else Message.showError("Make a Guess", "You need to guess a letter first!");
		}
		else Message.showError("Select Dictionary", "You need to load or create a dictionary first");
		letter.setText("");
		position.setText("");
		
	}
	
	private void removeWords(int x, char c) {
		possibleSet.removeIf(p -> p.charAt(x)==c);
		possibilities = new ArrayList<Map<Character, Integer>>();
		for (int i = 0; i < length; i++) possibilities.add(new HashMap<Character, Integer>());
		for (String s : possibleSet) {
        	for (int i = 0; i < s.length(); i++) {
        		Character l = s.charAt(i);
        		if(possibilities.get(i).containsKey(l)) possibilities.get(i).put(l, possibilities.get(i).get(l)+1);
        		else possibilities.get(i).put(l, 1);
        	}
		}
//		System.out.println(possibleSet);
//		System.out.println(possibilities);
		
	}

	private void findPossibleWords() {
		successChoices.setText(String.valueOf(solution.size()));
		if(solution.size()==length) {
			solved = true;
			Message.showMessage("Congratulations", "You found the correct word " + word + " in " + String.valueOf(lives) + " lives left!");
			rounds.addFirst(new Round("Player", word, totalGuesses));
			if(rounds.size() > 5) rounds.removeFirst();
			return;
		}
		Set<String> tempS = new HashSet<String>();
		boolean ok;
		for(String s : possibleSet) {
			ok = true;
			for(int pos : solution) {
				if(s.charAt(pos) != word.charAt(pos)) {
					ok = false;
					break;
				}
			}
			if(ok) tempS.add(s);
		}
		
		possibilities = new ArrayList<Map<Character, Integer>>();
		for (int i = 0; i < length; i++) possibilities.add(new HashMap<Character, Integer>());
		for (String s : tempS) {
        	for (int i = 0; i < s.length(); i++) {
        		Character c = s.charAt(i);
        		if(possibilities.get(i).containsKey(c)) possibilities.get(i).put(c, possibilities.get(i).get(c)+1);
        		else possibilities.get(i).put(c, 1);
        	}
		}
		possibleSet = tempS;
//        System.out.println(tempS);
//        System.out.println(possibilities);
//        System.out.println(solution);
	}
	// TODO fix with lives = 0 //
	private void solutionDisp(boolean state) {
		boolean answer;
		if(solved || (state && lives == 0)) answer = ConfirmBox.display("New Game", "Want to start a new game?");
		else answer = ConfirmBox.display("Give up", "Are you sure you want to give up?");
		if(answer && !solved) {
			active = !set.isEmpty();
			if(active && lives != 0) {
				Message.showMessage("Lost Game", "The word you were searching was: "+ word);
				rounds.addFirst(new Round("Computer", word, totalGuesses));
				restart();
				startGame();
				if(rounds.size() > 5) rounds.removeFirst();
			}
			else if (active) {
				Message.showMessage("New Game", "Starting new game");
				restart();
				startGame();
			}
			else {
				Message.showMessage("Load Dictionary", "Load or create dicitonary first");
			}
		}
		else if(answer && solved) {
			restart();
			startGame();
		}
	}
	
	private void placePercentages( ) {
		midRight.getChildren().clear();
		int size = possibleSet.size();
//		System.out.println(possibleSet);
//		System.out.println("ok");
		for(int i = 0; i < length; i++) {
			if(!solution.contains(i)) {
				HBox h = new HBox(5);
				
				Label text1 = new Label(String.valueOf(i+1));
	        	text1.setMinWidth(40);
	        	text1.setMinHeight(40);
	        	text1.setFont(new Font(15));
	        	text1.setStyle("-fx-background-color: yellow; -fx-font-weight: bold; -fx-border-color:black;-fx-border-width: 2;");
	        	text1.setAlignment(Pos.CENTER);
	        	
	        	ScrollPane s = new ScrollPane();
	        	s.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	        	s.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
	            s.setFitToWidth(true);
	            s.setPannable(true);
	            
	        	HBox h2 = new HBox(5);
				h2.setAlignment(Pos.CENTER);
				Map<Character, Integer> temp = sortByValue(possibilities.get(i));
				for (Map.Entry<Character,Integer> entry : temp.entrySet()) {
					Label text2 = new Label(entry.getKey()+ " : " + String.valueOf(entry.getValue()*100/size)+"%");
					text2.setMinWidth(80);
					text2.setMinHeight(40);
					text2.setFont(new Font(15));
					text2.setStyle("-fx-background-color: orange; -fx-font-weight: bold; -fx-border-color:black;-fx-border-width: 2;");
					text2.setAlignment(Pos.CENTER);
					h2.getChildren().add(text2);
		        	
				}
				s.setContent(h2);
				h.getChildren().addAll(text1, s);
				midRight.getChildren().add(h);
			}
		}
	}
	

	 private static Map<Character, Integer> sortByValue(Map<Character, Integer> unsortMap){
		 List<Entry<Character, Integer>> list = new LinkedList<>(unsortMap.entrySet());
		 // Sorting the list based on values
		 list.sort((o1, o2) ->  o2.getValue().compareTo(o1.getValue()) == 0
	                ? o2.getKey().compareTo(o1.getKey())
	                : o2.getValue().compareTo(o1.getValue()));
		 return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));
	}

	
	private void restart() {
		wordGuesses.getChildren().clear();
		midRight.getChildren().clear();
		availableWords.setText(String.valueOf(set.size()));
		totalPoints.setText("0");
		successChoices.setText("0");
		currentPoints = 0;
		length = 0;
		lives = 6;
		totalGuesses = 0;
		solved = false;
		solution = new ArrayList<Integer>();
		possibleSet = new HashSet<String>();
		File file = new File("src/images/6.png");
	    Image image = new Image(file.toURI().toString());
	    imageLives.setImage(image);
		if(!active) set = new HashSet<String>();
	}
}
