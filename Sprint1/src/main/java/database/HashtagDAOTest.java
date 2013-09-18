package database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Comment;
import model.Hashtag;
import model.User;

public class HashtagDAOTest {

	public static void main(String[] args) throws Exception{
		CommentDAO commentDAO = CommentDAO.getInstance();
		
		commentDAO.removeComment(20);
		
		
	
	}
	
}
