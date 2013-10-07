package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Comment;
import model.Hashtag;
import model.User;

import org.springframework.stereotype.Service;

import dao.impl.DbCommentDAOImpl;
import dao.impl.DbHashtagDAOImpl;
@Service
public class CommentService {

	private DbCommentDAOImpl commentDao;
	private DbHashtagDAOImpl hashtagDao;

	private static CommentService instance;

	public static CommentService getInstance() {
		if (instance == null) {
			instance = new CommentService();
		}
		return instance;
	}

	private CommentService() {
		commentDao = DbCommentDAOImpl.getInstance();
		hashtagDao = DbHashtagDAOImpl.getInstance();
	}

	public List<Hashtag> getHashtagList(String comment, User author) {

		List<Hashtag> ans = new ArrayList<Hashtag>();
		String patternStr = "#([A-Za-z0-9_]+)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(comment);
		String result = "";

		while (matcher.find()) {
			result = matcher.group();
			String hashtag = result.substring(1);
			Hashtag tag = hashtagDao.getHashtag(hashtag);
			if (tag == null) {
				tag = new Hashtag(hashtag, author, new Date());
				hashtagDao.save(tag);
			}
			ans.add(tag);
		}
		return ans;
	}

	public void save(Comment comment) {
		commentDao.save(comment);
	}

	public List<Comment> getComments(User user) {
		List<Comment> comments = commentDao.getComments(user);
		sortComments(comments);
		return comments;
	}
	
	public void removeComment(int commentId) {
		commentDao.removeComment(commentId);
	}

	public List<Comment> getComments(String hashtag){
		List<Comment> comments = hashtagDao.getComments(hashtag);
		sortComments(comments);
		return comments;
	}
	
	private void sortComments(List<Comment> comments) {
		Collections.sort(comments, new Comparator<Comment>() {
			public int compare(Comment o1, Comment o2) {
				return -1 * o1.getDate().compareTo(o2.getDate());
			}
		});
	}
}
