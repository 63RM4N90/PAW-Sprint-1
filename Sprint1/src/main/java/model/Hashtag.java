package model;

import java.sql.Date;
import java.sql.Time;


public class Hashtag{
	private String hashtag;
	private User author;
	private Date date;
	private Time time;

	public Hashtag() {
	}

	public Hashtag(String hashtag, User author, Date date, Time time) {
		this.hashtag = hashtag;
		this.author = author;
		this.date = date;
		this.time = time;
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
		return date;
	}
	
	public Time getTime(){
		return time;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
	
}
