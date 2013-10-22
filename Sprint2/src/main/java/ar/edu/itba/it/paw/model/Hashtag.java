package ar.edu.itba.it.paw.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Entity;

@Entity
public class Hashtag {
	private String hashtag;
	
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private Integer id;
	@OneToOne
	private User author;
	@Column(nullable=false)
	private Date date;

	public Hashtag() {
	}
	
	public Hashtag(String hashtag, User author, Date date) {
		this.hashtag = hashtag;
		this.author = author;
		this.date = date;
	}

	public String getHashtag() {
		return hashtag;
	}

	public User getAuthor() {
		return author;
	}

	public Date getDate() {
		return date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
