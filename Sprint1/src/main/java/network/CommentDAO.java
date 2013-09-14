package network;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Comment;
import model.Hashtag;
import model.User;


public class CommentDAO extends AbstractDAO {

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
				stmt = connection.prepareStatement("SELECT * FROM Hashtags WHERE Hashtags.id = (SELECT id FROM HashtagsInComments WHERE commentId = ?)");
				stmt.setInt(1, commentResults.getInt(1));
				ResultSet hashtagResults = stmt.executeQuery();
				List<Hashtag> hashtags = new ArrayList<Hashtag>();
				while (hashtagResults.next()) {
					User creator = userDao.getUser(hashtagResults.getString(3));
					hashtags.add(new Hashtag(hashtagResults.getString(2), creator, commentResults.getDate(3)));
				}
				Comment current = new Comment(user, commentResults.getTimestamp(3), commentResults.getString(4), hashtags); 
				comments.add(current); 
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return comments;
	}
	
	public void save(Comment comment) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt;
			stmt = connection.prepareStatement("INSERT INTO Comments(username, date, comment) values(?, ?, ?)");
			stmt.setString(1, comment.getAuthor().getUsername());
			stmt.setTimestamp(2, new Timestamp(comment.getDate().getTime()));
			stmt.setString(3, comment.getComment());
			stmt.executeUpdate();
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	public void removeComment(Comment comment) {
		Connection connection = manager.getConnection();
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement("DELETE FROM Comments WHERE id = ?");
			stmt.setInt(1, comment.getId());
			stmt.execute();
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
}