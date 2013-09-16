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

	public List<Comment> getComments(String hashtag) {
		List<Comment> comments = new ArrayList<Comment>();
		HashtagDAO hDAO = HashtagDAO.getInstance();
		UserDAO uDAO = UserDAO.getInstance();

		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM hashtagsincomments,comments"
					+ " WHERE commentid = comments.id AND hashtag = ?");
			stmt.setString(1, hashtag);			
			ResultSet results = stmt.executeQuery();

			while (results.next()) {
				List<Hashtag> hashtags = new ArrayList<Hashtag>();
				stmt = connection
						.prepareStatement("SELECT commentid,hashtag FROM hashtagsincomments "
								+ "WHERE commentid = ?");
				stmt.setInt(1, results.getInt(1));
				ResultSet resultsH = stmt.executeQuery();
				while (resultsH.next()) {
					hashtags.add(hDAO.getHashTag(resultsH.getString(2)));
				}
				User author = uDAO.getUser(results.getString(4));
				Comment comment = new Comment(author, results.getTimestamp(5),
						results.getString(6), hashtags);
				comments.add(comment);
			}

			connection.close();

		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}

		return comments;
	}

	public List<Comment> getComments(User user) {
		List<Comment> comments = new ArrayList<Comment>();
		HashtagDAO hashtagDAO = HashtagDAO.getInstance();

		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Comments WHERE username = ?");
			stmt.setString(1, user.getUsername());
			ResultSet results = stmt.executeQuery();

			while (results.next()) {
				stmt = connection
						.prepareStatement("SELECT hashtag FROM hashtagsincomments WHERE commentid = ?");
				stmt.setInt(1, results.getInt(1));
				ResultSet hashtags = stmt.executeQuery();
				List<Hashtag> list = new ArrayList<Hashtag>();
				while (hashtags.next()) {
					list.add(hashtagDAO.getHashTag(hashtags.getString(1)));
				}
				Comment comment = new Comment(user, results.getTimestamp(3),
						results.getString(4), list);
				comments.add(comment);
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
			stmt = connection
					.prepareStatement("INSERT INTO Comments(username, date, comment) values(?, ?, ?)");
			stmt.setString(1, comment.getAuthor().getUsername());
			stmt.setTimestamp(2, new Timestamp(comment.getDate().getTime()));
			stmt.setString(3, comment.getComment());
			stmt.executeUpdate();
			connection.commit();

			int id = getCommentId(comment, connection);
			connection.close();

			HashtagDAO hashtagDAO = HashtagDAO.getInstance();
			for (Hashtag each : comment.getHashtags()) {
				hashtagDAO.saveWithComment(each, id);
			}

		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	private int getCommentId(Comment comment, Connection connection) {
		int cID = -1;

		try {
			PreparedStatement stmt = connection
					.prepareStatement("SELECT id FROM comments WHERE username = ? AND date = ?");
			stmt.setString(1, comment.getAuthor().getUsername());
			stmt.setTimestamp(2, new Timestamp(comment.getDate().getTime()));

			ResultSet result = stmt.executeQuery();
			if (result.next())
				cID = result.getInt(1);

		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}

		return cID;

	}

	public void removeComment(Comment comment) {
		Connection connection = manager.getConnection();
		PreparedStatement stmt;
		try {
			stmt = connection
					.prepareStatement("DELETE FROM Comments WHERE id = ?");
			stmt.setInt(1, comment.getId());
			stmt.execute();
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

}
