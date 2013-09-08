package model;

import java.util.Date;

public class Hashtag {
	private String hashtag;
	private User author;
	private Date dateAndTime;

	public Hashtag() {
	}

	public Hashtag(String hashtag, User author, Date dateAndTime) {
		this.hashtag = hashtag;
		this.author = author;
		this.dateAndTime = dateAndTime;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public User getAuthor() {
		return author;
	}
	
	public Date getDate(){
		return dateAndTime;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
}
