package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Comment extends AbstractModel{
	private User author;
	private Date dateAndTime;
	private String comment;
	private List<Hashtag> hashtags;

	public Comment() {
	}

	public Comment(User author, Date dateAndTime, String comment,
			List<Hashtag> hashtags) {
		this.author = author;
		this.dateAndTime = dateAndTime;
		this.comment = comment;
		this.hashtags = hashtags;
	}

	public List<Hashtag> getHashtagList() {
		List<String> hashtags = Arrays.asList(comment.split("#*[A-Za-z0-9]"));
		List<Hashtag> ans = new ArrayList<Hashtag>();
		for (String hashtag : hashtags) {
			ans.add(new Hashtag(hashtag, null));
		}
		return ans;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Hashtag> getHashtags() {
		return hashtags;
	}

	public void setHashtags(List<Hashtag> hashtags) {
		this.hashtags = hashtags;
	}
}
