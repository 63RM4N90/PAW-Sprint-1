package database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Comment;
import model.Hashtag;
import model.User;

public class HashtagDAOTest {

	public static void main(String[] args) throws Exception{
		HashtagDAO hashtagDAO = HashtagDAO.getInstance();
		UserDAO userDAO = UserDAO.getInstance();
		CommentDAO commentDAO = CommentDAO.getInstance();
		
		//User user = new User("Aioria","Besteiro","aio","Soy el aio mas kpo","caracteres",null,"que pregunta?","esta rta");
		//User user2 = new User("Florencia","Besteiro","florbest89","florchexxx","password",null,"que onda?","llueve");
		//userDAO.save(user);
		//userDAO.save(user2);
		
		User user = userDAO.getUser("aio");
		User user2 = userDAO.getUser("florbest89");
		
		
		
		
		Date date = new Date();
		//Hashtag prueba = new Hashtag("florchen",user2,date);
		//Hashtag prueba2 = new Hashtag("puppi",user,date);
		//hashtagDAO.save(prueba);
		//hashtagDAO.save(prueba2);
		
		Hashtag prueba = hashtagDAO.getHashtag("florchen");
		Hashtag prueba2 = hashtagDAO.getHashtag("puppi");
		
		Hashtag h1 = new Hashtag("h1",user,date);
		Hashtag h2 = new Hashtag("h2",user,date);
		Hashtag h3 = new Hashtag("h3",user,date);
		Hashtag h4 = new Hashtag("h4",user,date);
		Hashtag h6 = new Hashtag("h6",user,date);
		Hashtag h5 = new Hashtag("h5",user,date);
		Hashtag h7 = new Hashtag("h7",user,date);
		Hashtag h8 = new Hashtag("h8",user,date);
		Hashtag h9 = new Hashtag("h9",user,date);
		
		/*hashtagDAO.save(h1);
		//hashtagDAO.save(h2);
		hashtagDAO.save(h3);
		hashtagDAO.save(h4);
		hashtagDAO.save(h5);
		hashtagDAO.save(h6);
		hashtagDAO.save(h7);
		hashtagDAO.save(h8);
		hashtagDAO.save(h9);*/
		
		List<Hashtag> list1 = new ArrayList<Hashtag>();
		list1.add(prueba);
		list1.add(prueba2);
		list1.add(h1);
		Comment c1 = new Comment(user2,new Date(),"comentariooooooo",list1);
		
		list1.add(h2);
		Comment c2 = new Comment(user,date,"alsjdlajdakld",list1);
		
		List<Hashtag> list2 = new ArrayList<Hashtag>();
		list2.add(h2);
		list2.add(h3);
		list2.add(prueba);
		Comment c3 = new Comment(user2,new Date(),"asldjalskdjakls",list2);
		
		list2.add(h8);
		Comment c4 = new Comment(user,new Date(),"sdfjsldfjl",list2);
		
		List<Hashtag> list3 = new ArrayList<Hashtag>();
		list3.add(h4);
		list3.add(h7);
		list3.add(prueba2);
		list3.add(h5);
		Comment c5 = new Comment(user2,new Date(),"aslc,??llsk",list3);
		
		list3.add(h1);
		Comment c6 = new Comment(user,new Date(),"fpsdoifosk",list3);
		
		list1.add(h6);
		list1.add(h9);
		Comment c7 = new Comment(user2,new Date(),"podifhf",list1);
		
		list2.add(h9);
		Comment c8 = new Comment(user2,new Date(),"podifhf",list2);
				
		
		
		List<Hashtag> list = new ArrayList<Hashtag>();
		list.add(prueba);
		list.add(prueba2);
		list.add(h7);
		Comment comment = new Comment(user,new Date(),"comentario super fantastico3",list);
		
		commentDAO.save(c1);
		commentDAO.save(c2);
		commentDAO.save(c3);
		commentDAO.save(c4);
		commentDAO.save(c5);
		commentDAO.save(c6);
		commentDAO.save(c7);
		commentDAO.save(c8);
		
	
	}
	
}
