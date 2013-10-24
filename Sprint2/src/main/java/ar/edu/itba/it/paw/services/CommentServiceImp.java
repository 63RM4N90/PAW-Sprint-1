package ar.edu.itba.it.paw.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.it.paw.dao.CommentDAO;
import ar.edu.itba.it.paw.dao.HashtagDAO;
import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Hashtag;
import ar.edu.itba.it.paw.domain.User;

@Service
public class CommentServiceImp implements CommentService {

	private HashtagDAO hashtagDAO;
	private CommentDAO commentDAO;

	@Autowired
	public CommentServiceImp(HashtagDAO hashtagDAO,
			CommentDAO commentDAO) {
		this.hashtagDAO = hashtagDAO;
		this.commentDAO = commentDAO;
	}

	@Override
	public List<Hashtag> getHashtagList(String comment, User author) {
		List<Hashtag> ans = new ArrayList<Hashtag>();
		String patternStr = "#([A-Za-z0-9_]+)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(comment);
		String result = "";

		while (matcher.find()) {
			result = matcher.group();
			String hashtag = result.substring(1);
			Hashtag tag = hashtagDAO.getHashtag(hashtag);
			if (tag == null) {
				tag = new Hashtag(hashtag, author, new Date());
				hashtagDAO.store(tag);
			}
			ans.add(tag);
		}
		return ans;
	}

	@Override
	public void save(Comment comment) {
		commentDAO.store(comment);
	}

	@Override
	public List<Comment> getComments(User user) {
		List<Comment> comments = commentDAO.getComments(user);
		sortComments(comments);
		return comments;
	}

	@Override
	public List<Comment> getComments(String hashtag) {
		List<Comment> comments = hashtagDAO.getComments(hashtag);
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
