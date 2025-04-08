package models;

import java.util.*;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class DictInfo {
	
	public static void display(Set<String> set) {
		int[] arr = new int[]{0,0,0};
		int len = set.size();
		for(String s : set) {
			int l = s.length();
			if(l == 6) arr[0]++;
			else if (l < 10) arr[1]++;
			else arr[2]++;
		}
		String result = "The current dictionary has: \n \t->  " + arr[0]*100/len + "% 6-letter words \n\t->  " + arr[1]*100/len + "% 7 to 9-letter words \n\t->  " + arr[2]*100/len + "% 10 or more-letter words \n\t";
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Dictionary Information");
		Label label = new Label();
		label.setText(result);
		label.setPrefHeight(200);
		label.setPrefWidth(500);        	
		label.setAlignment(Pos.CENTER);
		label.setFont(Font.font(null,FontWeight.BOLD, 25));
		Scene scene = new Scene(label);
		window.setScene(scene);
		window.showAndWait();
	}
}
