package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Comment;
import model.Hashtag;
import model.User;
import network.CommentDAO;
import network.HashTagDAO;

public class CommentService {

private CommentDAO commentDao;
private HashTagDAO hashtagDao;
	
	private static CommentService instance;

	public static CommentService getInstance() {
		if (instance == null) {
			instance = new CommentService();
		}
		return instance;
	}

	private CommentService() {
		commentDao = CommentDAO.getInstance();
		hashtagDao = HashTagDAO.getInstance();
	}
	
	public List<Hashtag> getHashtags(Comment comment) {
		List<Hashtag> ans = new ArrayList<Hashtag>();
		String[] aux = comment.getComment().split("#[A-Za-z0-9]");
		for (String string : aux) {
			Hashtag hashtag = hashtagDao.getHashTag(string);
			if (hashtag == null) {
				//hashtag = new Hashtag(string, comment.getAuthor(), new Date());
			}
			ans.add(hashtag);
		}
		return ans;
	}
	
	public List<Comment> getComments(User user) {
		return commentDao.getComments(user);
	}
}
