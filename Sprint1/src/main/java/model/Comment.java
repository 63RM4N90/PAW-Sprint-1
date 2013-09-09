package model;

import java.sql.Time;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

public class Comment extends AbstractModel{
	private User author;
	private Date date;
	private	Time time;
	private String comment;
	private List<Hashtag> hashtags;

	public Comment() {
	}

	public Comment(User author, Date date, Time time, String comment, List<Hashtag> hashtags) {
		this.author = author;
		this.date = date;
		this.time = time;
		this.comment = comment;
		this.hashtags = hashtags;
	}

	public List<Hashtag> getHashtagList() {
		List<String> hashtags = Arrays.asList(comment.split("#*[A-Za-z0-9]"));
		List<Hashtag> ans = new ArrayList<Hashtag>();
		for (String hashtag : hashtags) {
			//ans.add(new Hashtag(hashtag, null));
		}
		return ans;
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
	
	public Time getTime() {
		return time;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setTime(Time time) {
		this.time = time;
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
