package model;

import java.util.Date;
import java.util.List;

public class Comment extends AbstractModel implements Comparable<Comment>{
	private User author;
	private Date date;
	private String comment;
	private List<Hashtag> hashtags;

	public Comment() {
	}

	public Comment(User author, Date date, String comment, List<Hashtag> hashtags) {
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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

	public int compareTo(Comment o) {
		return date.compareTo(o.getDate());
	}
}
