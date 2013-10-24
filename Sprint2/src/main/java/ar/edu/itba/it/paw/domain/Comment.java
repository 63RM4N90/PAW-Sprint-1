package ar.edu.itba.it.paw.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
	@OneToMany
	private List<User> references;

	public Comment() {
	}

	public Comment(User author, Date date, String comment,
			List<Hashtag> hashtags, List<User> references) {
		this.author = author;
		this.date = date;
		this.comment = comment;
		this.hashtags = hashtags;
		this.references = references;
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

	public List<User> getReferences() {
		return references;
	}

	public int compareTo(Comment o) {
		return date.compareTo(o.getDate());
	}
}
