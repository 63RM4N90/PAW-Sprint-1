package model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Comment extends AbstractModel implements Comparable<Comment>{
	@OneToOne
	@Column(nullable=false)
	private User author;
	@Column(nullable=false)
	private Date date;
	@Column(nullable=false)
	private String comment;
	@OneToMany
	@JoinColumn(name="com_id")
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
