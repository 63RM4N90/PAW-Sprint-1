package network;

import java.sql.Timestamp;
import java.util.ArrayList;

import model.Comment;
import model.Hashtag;
import model.User;

public class CommentDAOTest {
	public static void main(String[] args) {
		CommentDAO manager = CommentDAO.getInstance();
		UserDAO umanager = UserDAO.getInstance();
		User u = new User("German", "Romarion", "63RM4N90", "FACHAAA", "12345678", null, "que onda?", "to pio");
//		u.setId(1);
		//umanager.save(u);
		Comment c = new Comment(u, new Timestamp(10), "ger topu", new ArrayList<Hashtag>());
		c.setId(1);
		//manager.save(c);
		manager.removeComment(c);
//		System.out.println(umanager.getUser(u.getUsername()).getUsername());
//		System.out.println(manager.getComments(u));
	}
}
