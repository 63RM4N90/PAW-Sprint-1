package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import model.Comment;
import model.Hashtag;
import model.User;

public class HashtagDAO {

	private final ConnectionManager manager;

	private static HashtagDAO instance;

	public static HashtagDAO getInstance() {
		if (instance == null) {
			instance = new HashtagDAO();
		}
		return instance;
	}

	private HashtagDAO() {
		manager = new ConnectionManager(DatabaseInfo.driver,
				DatabaseInfo.connectionString, DatabaseInfo.username,
				DatabaseInfo.password);
	}

	public Hashtag getHashtag(String hashtag) {
		Hashtag hashtagAux = null;
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM hashtags WHERE hashtag = ?");
			stmt.setString(1, hashtag);

			ResultSet results = stmt.executeQuery();
			if (results.next()) {

				hashtagAux = new Hashtag(results.getString(1), UserDAO
						.getInstance().getUser(results.getString(2)),
						results.getTimestamp(3));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return hashtagAux;
	}

	public void save(Hashtag hashtag) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt;

			stmt = connection
					.prepareStatement("INSERT INTO hashtags(hashtag,creator,date) VALUES(?, ?, ?)");
			stmt.setString(1, hashtag.getHashtag());
			stmt.setString(2, hashtag.getAuthor().getUsername());
			stmt.setTimestamp(3, new Timestamp(hashtag.getDate().getTime()));

			stmt.executeUpdate();
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	void saveWithComment(Hashtag hashtag, int commentId) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt;
			stmt = connection
					.prepareStatement("INSERT INTO hashtagsincomments(commentid,hashtag) VALUES(?,?)");
			stmt.setInt(1, commentId);
			stmt.setString(2, hashtag.getHashtag());

			stmt.executeUpdate();
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}

	}

	public List<Comment> getComments(String hashtag) {
		CommentDAO commentDAO = CommentDAO.getInstance();
		return commentDAO.getComments(hashtag);
	}

	public TreeMap<Integer, ArrayList<Hashtag>> rankedHashtags(Date from,
			Date to) {
		TreeMap<Integer, ArrayList<Hashtag>> rank = new TreeMap<Integer, ArrayList<Hashtag>>();

		String query = "SELECT H1.hashtag, H1.date, count(H2.commentId) AS RANK, U.id, U.name, U.surname,U.username, U.password, U.description,U.secretquestion,U.secretanswer,U.picture,U.registrationDate "
				+ "FROM hashtags AS H1,hashtagsincomments AS H2,comments AS C,users AS U "
				+ "WHERE H1.hashtag = H2.hashtag AND H2.commentId = C.id AND H1.creator = U.username AND C.date >= ? AND C.date <= ? "
				+ "GROUP BY H1.hashtag, H1.date, U.id, U.name, U.surname, U.password, U.username, U.description,U.secretquestion,U.secretanswer, U.picture, U.registrationDate "
				+ "ORDER BY RANK DESC";

		Connection connection = manager.getConnection();
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(query);
			stmt.setTimestamp(1, new Timestamp(from.getTime()));
			stmt.setTimestamp(2, new Timestamp(to.getTime()));
			ResultSet results = stmt.executeQuery();

			Hashtag hashtag;
			User creator;
			int ranking;

			while (results.next()) {
				ranking = results.getInt(3);
				creator = new User(results.getString(5), results.getString(6),
						results.getString(7), results.getString(9),
						results.getString(8), results.getBytes(12),
						results.getString(10), results.getString(11),
						results.getTimestamp(13));
				creator.setId(results.getInt(4));
				hashtag = new Hashtag(results.getString(1), creator,
						results.getTimestamp(2));

				ArrayList<Hashtag> aux;
				if (rank.containsKey(ranking)) {
					rank.get(ranking).add(hashtag);

				} else {
					aux = new ArrayList<Hashtag>();
					aux.add(hashtag);
					rank.put(ranking, aux);
				}

			}

			connection.close();
			return rank;

		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
}
