package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Comment;
import model.Hashtag;
import model.User;
import network.CommentDAO;
import network.HashtagDAO;

public class CommentService {

	private CommentDAO commentDao;
	private HashtagDAO hashtagDao;

	private static CommentService instance;

	public static CommentService getInstance() {
		if (instance == null) {
			instance = new CommentService();
		}
		return instance;
	}

	private CommentService() {
		commentDao = CommentDAO.getInstance();
		hashtagDao = HashtagDAO.getInstance();
	}

	public List<Hashtag> getHashtagList(String comment, User author) {
		String[] aux = comment.split("#[A-Za-z0-9]");
		List<Hashtag> ans = new ArrayList<Hashtag>();
		for (String string : aux) {
			Hashtag tag = hashtagDao.getHashTag(string);
			if (tag == null) {
				tag = new Hashtag(string, author, new Date());
			}
			ans.add(tag);
		}
		return ans;
	}
	
	public void save(Comment comment) {
		commentDao.save(comment);
	}

	public String getProcessedComment(String comment) {
		int i = 0;
		String ans = "";
		String auxTag = "";
		boolean tagFound = false;
		while (i < comment.length()) {
			if (comment.charAt(i) == ' ') {
				if (tagFound) {
					ans += ">" + auxTag + "</a>";
					auxTag = "";
					tagFound = false;
				} else {
					ans += comment.charAt(i);
					auxTag += comment.charAt(i);
				}
			} else if (comment.charAt(i) == '#') {
				ans += "<a href=\"/hashtag?tag=";
				tagFound = true;
			} else {
				ans += comment.charAt(i);
				if (tagFound) {
					auxTag += comment.charAt(i);
				}
			}
			i++;
		}
		return ans;
	}

	public List<Comment> getComments(User user) {
		return commentDao.getComments(user);
	}
}
