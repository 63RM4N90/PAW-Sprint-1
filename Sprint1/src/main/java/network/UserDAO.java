package network;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;


public class UserDAO extends AbstractDAO{

	private final ConnectionManager manager;

	private static UserDAO instance;

	public static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}

	private UserDAO() {
		manager = new ConnectionManager(driver, connectionString, username,
				password);
	}

	public User getUser(String username) {
		User user = null;
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Users WHERE username = ?");
			stmt.setString(1, username);

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				user = new User(results.getString(2), results.getString(3),
						username, results.getString(5), results.getString(6),
						null, results.getString(7), results.getString(8));
				user.setId(results.getInt(1));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return user;
	}

	public void save(User user) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt;
			if (user.isNew()) {
				stmt = connection
						.prepareStatement("INSERT INTO Users(name, surname, password, username, description, secretquestion, secretanswer) values(?, ?, ?, ?, ?, ?, ?)");
				stmt.setString(1, user.getName());
				stmt.setString(2, user.getSurname());
				stmt.setString(3, user.getPassword());
				stmt.setString(4, user.getUsername());
				stmt.setString(5, user.getDescription());
				stmt.setString(6, user.getSecretQuestion());
				stmt.setString(7, user.getSecretAnswer());
			} else {
				stmt = connection
						.prepareStatement("UPDATE Users SET name = ?, surname = ?, password = ?, username = ?, description = ?, secretquestion = ?, secretanswer = ? WHERE username = ?");
				stmt.setString(1, user.getName());
				stmt.setString(2, user.getSurname());
				stmt.setString(3, user.getPassword());
				stmt.setString(4, user.getUsername());
				stmt.setString(5, user.getDescription());
				stmt.setString(6, user.getSecretQuestion());
				stmt.setString(7, user.getSecretAnswer());
				stmt.setString(8, user.getUsername());
			}
			stmt.executeUpdate();
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		User user;
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Users WHERE username = ?");
			stmt.setString(1, username);

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				user = new User(results.getString(2), results.getString(3),
						username, results.getString(5), results.getString(6),
						null, results.getString(7), results.getString(8));
				user.setId(results.getInt(1));
				users.add(user);
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return users;
	}

	public void removeUser(User user) {
		Connection connection = manager.getConnection();
		PreparedStatement stmt;
		try {
			stmt = connection
					.prepareStatement("DELETE FROM TABLE Users WHERE username = ?");
			stmt.setString(1, user.getUsername());
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}

	}
}