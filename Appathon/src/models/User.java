package models;

public class User {
	protected String username;
	protected String name;
	protected String surname;
	protected String date;
	protected String password;
	
	public User() {
	}
	
	public User(String username, String name, String surname, String date, String password) {
		super();
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.date = date;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}	
	public String getPassword() {
		return password;
	}
	public void setPassword(String pass) {
		this.password = pass;
	}
}

