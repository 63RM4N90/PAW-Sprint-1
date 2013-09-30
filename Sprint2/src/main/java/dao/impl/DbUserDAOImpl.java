package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.UserDAO;
import database.ConnectionManager;
import database.DatabaseException;
import database.DatabaseInfo;
import model.User;

public class DbUserDAOImpl implements UserDAO {

	private final ConnectionManager manager;
	private static DbUserDAOImpl instance;

	public static DbUserDAOImpl getInstance() {
		if (instance == null) {
			instance = new DbUserDAOImpl();
		}
		return instance;
	}

	private DbUserDAOImpl() {
		manager = new ConnectionManager(DatabaseInfo.getDriver(),
				DatabaseInfo.getConnectionString(), DatabaseInfo.getUsername(),
				DatabaseInfo.getPassword());
	}

	public User authenticate(String username, String password) {
		try {
			User user = null;
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ?");
			stmt.setString(1, username);
			stmt.setString(2, password);

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				user = constructUser(results);
			}
			connection.close();
			return user;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
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
				user = constructUser(results);
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
				if (user.getPicture() != null) {
					stmt = connection
							.prepareStatement("INSERT INTO Users(name, surname, password, username, description, secretquestion, secretanswer, picture, registrationDate) values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
				} else {
					stmt = connection
							.prepareStatement("INSERT INTO Users(name, surname, password, username, description, secretquestion, secretanswer, registrationDate) values(?, ?, ?, ?, ?, ?, ?, ?)");
				}
			} else {
				if (user.getPicture() != null) {
					stmt = connection
							.prepareStatement("UPDATE Users SET name = ?, surname = ?, password = ?, username = ?, description = ?, secretquestion = ?, secretanswer = ?, picture = ?, registrationDate = ? WHERE username = ?");
				} else {
					stmt = connection
							.prepareStatement("UPDATE Users SET name = ?, surname = ?, password = ?, username = ?, description = ?, secretquestion = ?, secretanswer = ?, registrationDate = ? WHERE username = ?");
				}
			}
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getSurname());
			stmt.setString(3, user.getPassword());
			stmt.setString(4, user.getUsername());
			stmt.setString(5, user.getDescription());
			stmt.setString(6, user.getSecretQuestion());
			stmt.setString(7, user.getSecretAnswer());
			if (user.isNew()) {
				if (user.getPicture() != null) {
					stmt.setBytes(8, user.getPicture());
					stmt.setTimestamp(9, new Timestamp(user
							.getRegistrationDate().getTime()));
				} else {
					stmt.setTimestamp(8, new Timestamp(user
							.getRegistrationDate().getTime()));
				}
				stmt.execute();
			} else {
				if (user.getPicture() != null) {
					stmt.setBytes(8, user.getPicture());
					stmt.setTimestamp(9, new Timestamp(user
							.getRegistrationDate().getTime()));
					stmt.setString(10, user.getUsername());
				} else {
					stmt.setTimestamp(8, new Timestamp(user
							.getRegistrationDate().getTime()));
					stmt.setString(9, user.getUsername());
				}
				stmt.executeUpdate();
			}

			connection.commit();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	public List<User> getUsersWithName(String name) {
		List<User> users = new ArrayList<User>();
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Users WHERE username LIKE '%"
							+ name
							+ "%' OR (name LIKE '%"
							+ name
							+ "%') OR (surname LIKE '%" + name + "%')");

			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				User user = constructUser(results);
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
					.prepareStatement("DELETE FROM Users WHERE username = ?");
			stmt.setString(1, user.getUsername());
			stmt.execute();
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	private User constructUser(ResultSet results) throws SQLException {
		int id = results.getInt(1);
		String name = results.getString(2);
		String surname = results.getString(3);
		String password = results.getString(4);
		String username = results.getString(5);
		String description = results.getString(6);
		String secretQuestion = results.getString(7);
		String secretAnswer = results.getString(8);
		byte[] file = results.getBytes(9);
		Date registrationDate = results.getDate(10);
		User user = new User(name, surname, username, description, password,
				file, secretQuestion, secretAnswer, registrationDate);
		user.setId(id);

		return user;
	}
}
