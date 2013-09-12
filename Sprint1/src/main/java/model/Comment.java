package model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class Comment extends AbstractModel{
	private User author;
	private Timestamp date;
	private String comment;
	private List<Hashtag> hashtags;

	public Comment() {
	}

	public Comment(User author, Timestamp date, String comment, List<Hashtag> hashtags) {
		this.author = author;
		this.date = date;
		this.comment = comment;
		this.hashtags = hashtags;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
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
