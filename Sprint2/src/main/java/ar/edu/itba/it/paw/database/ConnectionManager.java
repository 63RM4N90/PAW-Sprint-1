package ar.edu.itba.it.paw.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private String username;
	private String password;
	private String connectionString;

	public ConnectionManager(String driver, String connectionString,
			String username, String password) {
		this.connectionString = "jdbc:postgresql://localhost/paw4";
		this.username = "paw";
		this.password = "paw";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	public Connection getConnection() {
		try {
			Connection connection = DriverManager.getConnection(
					connectionString, username, password);
			connection.setAutoCommit(false);
			return connection;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
}