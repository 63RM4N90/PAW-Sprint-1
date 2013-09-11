package network;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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
		
		Calendar aux = Calendar.getInstance();
		aux.set(2013, aux.AUGUST, 1);
		
		Date from = aux.getTime();
		aux.set(2013, aux.SEPTEMBER,5);
		Date to = aux.getTime();
		
		
		
		HashtagDAO htdao = HashtagDAO.getInstance();
		
		Hashtag hashtag = new Hashtag("hashtag",user,date);
		Hashtag hashtag2 = new Hashtag("prueba",user,date);
		
		System.out.println("Intento guardar un hashtag");
		
		//htdao.save(hashtag);
		//htdao.save(hashtag2);
		
		System.out.println(date);
		System.out.println(from);
		System.out.println(to);
		
				
		
		
		//htdao.saveWithComment(hashtag, 2);
		//htdao.saveWithComment(hashtag, 3);		

		//htdao.saveWithComment(hashtag2,4);
		
		HashMap<Integer,ArrayList<Hashtag>> map = htdao.rankedHashTags(from,to);
		
		for(Integer each: map.keySet()){
			System.out.println("Con " + each + " estan los hashtags:");
			for(Hashtag auxi: map.get(each)){
				System.out.println(auxi.getHashtag());
			}
		}
	
		

	}

}
