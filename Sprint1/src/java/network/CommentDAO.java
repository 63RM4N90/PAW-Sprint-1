package network;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Comment;
import model.Hashtag;
import model.User;


public class CommentDAO {

	private static final String driver = "org.postgresql.Driver";
	private static final String connectionString = "jdbc:postgresql://localhost/paw";
	private static final String username = "paw";
	private static final String password = "paw";

	private final ConnectionManager manager;

	private static CommentDAO instance;

	public static CommentDAO getInstance() {
		if (instance == null) {
			instance = new CommentDAO();
		}
		return instance;
	}

	private CommentDAO() {
		manager = new ConnectionManager(driver, connectionString, username,
				password);
	}

	public List<Comment> getComments(User user) {
		List<Comment> comments = new ArrayList<Comment>();
		UserDAO userDao = UserDAO.getInstance();
		
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Comments WHERE username = ?");
			stmt.setString(1, String.valueOf(user.getUsername()));

			ResultSet commentResults = stmt.executeQuery();
			while (commentResults.next()) {
				Date commentDate = DateFormat.getInstance().parse(commentResults.getString(3));
				stmt = connection.prepareStatement("SELECT * FROM Hashtag WHERE Hashtag.id = (SELECT id FROM HashtagsInComments WHERE commentId = ?)");
				stmt.setString(1, String.valueOf(commentResults.getString(1)));
				ResultSet hashtagResults = stmt.executeQuery();
				List<Hashtag> hashtags = new ArrayList<Hashtag>();
				while (hashtagResults.next()) {
					User creator = userDao.getUser(hashtagResults.getString(3));
					Date hashtagDate = DateFormat.getInstance().parse(commentResults.getString(3));
					hashtags.add(new Hashtag(hashtagResults.getString(2), creator, hashtagDate));
				}
				Comment current = new Comment(user, commentDate, commentResults.getString(5), hashtags); 
				comments.add(current); 
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		} catch (ParseException pe){
			
		}
		return comments;
	}
	
	public void save(Comment comment) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt;
			stmt = connection.prepareStatement("INSERT INTO Comments(id, username, date, time, comment) values(?, ?, ?, ?, ?)");
			stmt.setString(1, comment.getId());
			stmt.setString(2, comment.getAuthor().getUsername());
			stmt.setString(3, comment.getDate());
			stmt.setString(4, user.getUsername());
			stmt.setString(5, user.getDescription());
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
