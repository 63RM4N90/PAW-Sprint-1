package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
				hashtagDao.save(tag);
			}
			ans.add(tag);
		}
		return ans;
	}

	public void save(Comment comment) {
		commentDao.save(comment);
	}

	public String getProcessedComment(String comment) {
		// Search for URLs
		if (comment != null && comment.contains("http:")) {
			int indexOfHttp = comment.indexOf("http:");
			int endPoint = (comment.indexOf(' ', indexOfHttp) != -1) ? comment
					.indexOf(' ', indexOfHttp) : comment.length();
			String url = comment.substring(indexOfHttp, endPoint);
			String targetUrlHtml = "<a href='" + url + "' target='_blank'>"
					+ url + "</a>";
			comment = comment.replace(url, targetUrlHtml);
		}

		String patternStr = "#([A-Za-z0-9_]+)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(comment);
		String result = "";

		// Search for Hashtags
		while (matcher.find()) {
			result = matcher.group();
			result = result.replace(" ", "");
			String search = result.replace("#", "");
			String searchHTML = "<a href='/hashtag?tag=" + search + "'>"
					+ result + "</a>";
			comment = comment.replace(result, searchHTML);
		}
		System.out.println(comment);
		return comment;
	}

	public List<Comment> getComments(User user) {
		return commentDao.getComments(user);
	}
}
