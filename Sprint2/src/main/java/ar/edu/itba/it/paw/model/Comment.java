package ar.edu.itba.it.paw.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Comment extends PersistentEntity implements Comparable<Comment> {

	@OneToOne
	private User author;
	@Column(nullable = false)
	private Date date;
	@Column(nullable = false)
	private String comment;
	@ManyToMany	
	@JoinColumn(name = "com_id")
	private List<Hashtag> hashtags;

	public Comment() {
	}

	public Comment(User author, Date date, String comment,
			List<Hashtag> hashtags) {
		this.author = author;
		this.date = date;
		this.comment = comment;
		this.hashtags = hashtags;
	}

	public User getAuthor() {
		return author;
	}

	public Date getDate() {
		return date;
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

	public int compareTo(Comment o) {
		return date.compareTo(o.getDate());
	}
}
