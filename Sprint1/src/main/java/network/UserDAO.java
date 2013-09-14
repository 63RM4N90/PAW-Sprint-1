package network;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserDAO extends AbstractDAO {

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
							.prepareStatement("INSERT INTO Users(name, surname, password, username, description, secretquestion, secretanswer, picture) values(?, ?, ?, ?, ?, ?, ?, ?)");
				} else {
					stmt = connection
							.prepareStatement("INSERT INTO Users(name, surname, password, username, description, secretquestion, secretanswer) values(?, ?, ?, ?, ?, ?, ?)");
				}
			} else {
				if (user.getPicture() != null) {
					stmt = connection
							.prepareStatement("UPDATE Users SET name = ?, surname = ?, password = ?, username = ?, description = ?, secretquestion = ?, secretanswer = ?, picture = ? WHERE username = ?");
				} else {
					stmt = connection
							.prepareStatement("UPDATE Users SET name = ?, surname = ?, password = ?, username = ?, description = ?, secretquestion = ?, secretanswer = ? WHERE username = ?");
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
					stmt.setBytes(8, fileToByte(user.getPicture()));
				}
				stmt.execute();
			} else {
				if (user.getPicture() != null) {
					stmt.setBytes(8, fileToByte(user.getPicture()));
					stmt.setString(9, user.getUsername());
				} else {
					stmt.setString(8, user.getUsername());
				}
				stmt.executeUpdate();
			}

			connection.commit();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		} catch (FileNotFoundException e) {
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
			while (results.next()) {
				user = constructUser(results);
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

	private static byte[] fileToByte(File file) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		try {
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum);
			}
			fis.close();
		} catch (IOException ex) {
		}
		byte[] bytes = bos.toByteArray();

		return bytes;
	}

	private static void byteToFile(byte[] image, String fileName) {
		File someFile = new File(fileName);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(someFile);
			fos.write(image);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
		String pictureName = username + ".jpg";
		File file = null;
		if (results.getBytes(9) != null) {
			byteToFile(results.getBytes(9), pictureName);
			file = new File(pictureName);
		}
		User user = new User(name, surname, username, description, password,
				file, secretQuestion, secretAnswer);
		user.setId(id);

		return user;
	}
}
