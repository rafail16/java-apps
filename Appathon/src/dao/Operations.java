package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import models.User;
import models.Product;
import models.Country;

public class Operations {
	private String jdbcURL = "jdbc:mysql://localhost:3300/computers?useSSL=false"; //put your mysql url here
	private String jdbcUsername = "root"; //put your mysql server username here
	private String jdbcPassword = "password"; //put your mysql server password here
	
	private static final String INSERT_USERS_SQL = "INSERT INTO users (username, name, surname, date_of_birth, password) VALUES "
			+ " (?, ?, ?, ?, ?);";
	private static final String USER_EXISTS = "select * from users where username = ?";
	private static final String USER_LOGIN = "select * from users where username = ? and password = ?";
	private static final String UPDATE_USERS_SQL = "update users set name = ?, surname = ?, date_of_birth = ? where username = ?;";
	private static final String SELECT_PRODUCTS = "select id, name, image, price from products";
	private static final String SELECT_COUNTRIES = "select country, value from vat";
	private static final String INSERT_ORDER = "INSERT INTO orders (username, totalSum) VALUES " + " (?, ?);";
	private static final String INSERT_ELEMENT = "INSERT INTO orderelement (order_id, product_id, quantity) VALUES (?, ?, ?);";

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	public void insertUser(User user) throws ClassNotFoundException {
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getName());
			preparedStatement.setString(3, user.getSurname());
			preparedStatement.setString(4, user.getDate());
			preparedStatement.setString(5, user.getPassword());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}
	
	public boolean login(String username, String password) throws ClassNotFoundException {
		boolean status = false;
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(USER_LOGIN)) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			status = rs.next();
		} catch (SQLException e) {
			printSQLException(e);
		}
		return status;
	}
	
	public boolean existingUser(String username) throws ClassNotFoundException {
		boolean status = false;
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(USER_EXISTS)) {
			preparedStatement.setString(1, username);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			status = rs.next();
		} catch (SQLException e) {
			printSQLException(e);
		}
		return status;
	}
	
	public boolean updateUser(String name, String surname, String date, String username) throws ClassNotFoundException {
		boolean rowUpdated = false;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
			statement.setString(1, name);
			statement.setString(2, surname);
			statement.setString(3, date);
			statement.setString(4, username);
			rowUpdated = statement.executeUpdate() > 0;
		}catch(SQLException e) {
			printSQLException(e);
		}
		return rowUpdated;
	}
	
	public List<Product> selectProducts() throws ClassNotFoundException {
	    List<Product> products = new ArrayList<Product>();
	    try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTS)) {
				System.out.println(preparedStatement);
				ResultSet rs = preparedStatement.executeQuery();
				while(rs.next()) {
					Product product = new Product();
					product.setId(rs.getString("id"));
					product.setName(rs.getString("name"));
					product.setImage(rs.getString("image"));
					product.setPrice(rs.getString("price"));
					products.add(product);
				}
			} catch (SQLException e) {
				printSQLException(e);
			}
	    return products;
	}
	
	public List<Country> selectCountries() throws ClassNotFoundException {
	    List<Country> countries = new ArrayList<Country>();
	    try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNTRIES)) {
				System.out.println(preparedStatement);
				ResultSet rs = preparedStatement.executeQuery();
				while(rs.next()) {
					Country country = new Country();
					country.setName(rs.getString("country"));
					country.setVat(rs.getDouble("value"));
					countries.add(country);
				}
			} catch (SQLException e) {
				printSQLException(e);
			}
	    return countries;
	}
	public int insertOrder(String username, double total) throws ClassNotFoundException {
		int id = 0;
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER, new String[] {"id"})) {
			preparedStatement.setString(1, username);
			preparedStatement.setDouble(2, total);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
			   id = rs.getInt(1);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return id;
	}
	
	public void insertElement(int order, int product, int quantity) throws ClassNotFoundException {
		try (Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ELEMENT)) {
			preparedStatement.setInt(1, order);
			preparedStatement.setInt(2, product);
			preparedStatement.setInt(3, quantity);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}
	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}
