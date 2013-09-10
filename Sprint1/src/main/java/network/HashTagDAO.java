package network;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import model.Hashtag;

public class HashTagDAO extends AbstractDAO{

	private final ConnectionManager manager;

	private static HashTagDAO instance;

	public static HashTagDAO getInstance() {
		if (instance == null) {
			instance = new HashTagDAO();
		}
		return instance;
	}

	private HashTagDAO() {
		manager = new ConnectionManager(driver, connectionString, username,
				password);
	}

	public Hashtag getHashTag (String hashtag) {
		Hashtag hashtagAux = null;
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Users WHERE username = ?");
			stmt.setString(1, username);

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				hashtagAux = new Hashtag(results.getString(2),UserDAO.getInstance().getUser(results.getString(5)),results.getDate(4),results.getTime(5));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return hashtagAux;
	}

	//	Este método guarda en la tabla "hashtags" los datos de su creación.
	public void save(Hashtag hashtag) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt;
			
				stmt = connection
						.prepareStatement("INSERT INTO hashtags(hashtag,creator,date,time) VALUES(?, ?, ?, ?)");
				stmt.setString(1, hashtag.getHashtag());
				stmt.setString(2,hashtag.getAuthor().getUsername());
				stmt.setDate(3, hashtag.getDate());
				stmt.setTime(4, hashtag.getTime());
			
			stmt.executeUpdate();
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
	//Este método es para el CommentDAO para guardar en la tabla hashtagsincomments con el commentId.
	void saveWithComment(Hashtag hashtag, int commentId){
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt;
			
				stmt = connection
						.prepareStatement("INSERT INTO hashtagsincomments(commentid,hashtag)");
				stmt.setInt(1, commentId);
				stmt.setString(2, hashtag.getHashtag());
			
			stmt.executeUpdate();
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}		
		
	}

	
	//FALTA TERMINAR
	public HashMap<Integer,ArrayList<Hashtag>> rankedHashTags(Date from, Date to) throws SQLException{
		HashMap<Integer,ArrayList<Hashtag>> rank = new HashMap<Integer,ArrayList<Hashtag>>();
		
		String query = "SELECT hashtags.hashtag,hashtags., count(hashtagsincomments.commentId) AS RANK, count(hashtags.hashtag) "
				+ "FROM hashtags AS H1,hashtagsincomments AS H2,comments AS C "
				+ "WHERE H1.hashtag = H2.hashtag AND H2.commentId = C.id "
				+ "GROUP BY hashtags.hashtag "
				+ "ORDER BY RANK DESC";
		
		Connection connection = manager.getConnection();
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		
		int total =0;
		
		for(int i=0; i < total; i++){
			
			if(results.next()){
				int ranking = results.getInt(2);
				String hashtag = results.getString(1);
				if(rank.containsKey(ranking)){
					//(rank.put(ranking, rank.get(ranking).add(hashtag));
				} else{
					rank.put(ranking, new ArrayList<Hashtag>());
				}
					
			}
			
		}
		
		return rank;
		
	}
	
}
