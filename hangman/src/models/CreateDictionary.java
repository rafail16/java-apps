package models;

import java.net.*;
import java.io.*;
import java.util.*;
import exceptions.*;


import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class CreateDictionary {
	
	public static Set<String> create (String id) throws UndersizeException, UnbalancedException {
		
		String url = "https://openlibrary.org/works/" + id + ".json";
		StringBuffer response = new StringBuffer();
		Set<String> set = new HashSet<String>();
	    int count = 0;
		try {
		    URL req = new URL(url);
		    HttpURLConnection con = (HttpURLConnection) req.openConnection();
		    int responseCode = con.getResponseCode();
		    if (responseCode != 200) {
		        return new HashSet<String>();
		    }
		    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		    String inputLine;
		    while ((inputLine = in.readLine()) != null) {
		    	response.append(inputLine);
		    }
		    in.close();
		    JSONParser parser = new JSONParser();
		    Object obj = parser.parse(response.toString());
		    JSONObject response_object = (JSONObject) obj;
		    JSONObject response_status = (JSONObject) response_object.get("description");
		    if (response_status.toString().equals("{}")) {
		    	return new HashSet<String>();
		    }
		    File myObj = new File("src/medialab/hangman_" + id + ".txt");
		    myObj.createNewFile();
		    FileWriter myWriter = new FileWriter("src/medialab/hangman_" + id + ".txt");
		    String value = (String) response_status.get("value");
		    for (String s: value.split("[^\\p{L}]+")) {
		    	if (s.length() >= 6) {
		    	    if(!set.contains(s.toUpperCase())) {
		    	    	if (s.length() >= 9) count++;
		    	        set.add(s.toUpperCase());
		    	        myWriter.write(s.toUpperCase()+'\n');    	        
		    	    }
		    	}
		    }
		    myWriter.close();
		} catch (Exception e) {
		    return new HashSet<String>();
		}
		int sz = set.size();
		if (sz < 20) throw new UndersizeException();
		else if (count*100/sz < 20) throw new UnbalancedException();
		else {
			return set;
		}
	}
	
}
