package network;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import model.Hashtag;
import model.User;

/*	protected static final String driver = "org.postgresql.Driver";
	protected static final String connectionString = "jdbc:postgresql://localhost/paw4";
	protected static final String username = "paw";
	protected static final String password = "paw";*/

public class HashtagDAOTest {

	public static void main(String[] args) {
		
		UserDAO manager = UserDAO.getInstance();
		
		User user = new User("German", "Romarion", "63RM4N90", "FACHAAA", "12345678", null, "que onda?", "to pio");
		
		//manager.save(user);
		
		Date date = new Date();
		
		HashtagDAO htdao = HashtagDAO.getInstance();
		
		Hashtag hashtag = new Hashtag("hashtag",user,date);
		Hashtag hashtag2 = new Hashtag("prueba",user,date);
		
		System.out.println("Intento guardar un hashtag");
		
		//htdao.save(hashtag);
		//htdao.save(hashtag2);
		
		System.out.println(date);
		
				
		
		
		//htdao.saveWithComment(hashtag, 2);
		//htdao.saveWithComment(hashtag, 3);		

		//htdao.saveWithComment(hashtag2,4);
		
		HashMap<Integer,ArrayList<Hashtag>> map = htdao.rankedHashTags(null, null);
		
		System.out.println(map);
	
		

	}

}
