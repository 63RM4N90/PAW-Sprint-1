package dao.impl;

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
import dao.CommentDAO;
import database.ConnectionManager;
import database.DatabaseException;
import database.DatabaseInfo;

public class DbCommentDAOImpl implements CommentDAO {

	private final ConnectionManager manager;

	private static DbCommentDAOImpl instance;

	public static DbCommentDAOImpl getInstance() {
		if (instance == null) {
			instance = new DbCommentDAOImpl();
		}
		return instance;
	}

	private DbCommentDAOImpl() {
		manager = new ConnectionManager(DatabaseInfo.getDriver(),
				DatabaseInfo.getConnectionString(), DatabaseInfo.getUsername(),
				DatabaseInfo.getPassword());
	}

	public List<Comment> getComments(String hashtag) {
		List<Comment> comments = new ArrayList<Comment>();
		DbHashtagDAOImpl hDAO = DbHashtagDAOImpl.getInstance();
		DbUserDAOImpl uDAO = DbUserDAOImpl.getInstance();

		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM hashtagsincomments,comments"
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
					hashtags.add(hDAO.getHashtag(resultsH.getString(2)));
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
		DbHashtagDAOImpl hashtagDAO = DbHashtagDAOImpl.getInstance();

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
					list.add(hashtagDAO.getHashtag(hashtags.getString(1)));
				}
				Comment comment = new Comment(user, results.getTimestamp(3),
						results.getString(4), list);
				comment.setId(results.getInt(1));
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

			DbHashtagDAOImpl hashtagDAO = DbHashtagDAOImpl.getInstance();
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

	public void removeComment(int commentId) {
		Connection connection = manager.getConnection();
		PreparedStatement stmt;
		try {
			stmt = connection
					.prepareStatement("DELETE FROM hashtagsincomments WHERE commentid = ?");
			stmt.setInt(1, commentId);
			stmt.execute();

			stmt = connection
					.prepareStatement("DELETE FROM Comments WHERE id = ?");
			stmt.setInt(1, commentId);
			stmt.execute();

			connection.commit();

			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
}
