package ar.edu.itba.it.paw.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ar.edu.itba.it.paw.web.user.ThumbnailImageConverter;

@Entity
@Table(name = "userss")
public class User extends PersistentEntity {

	public static final int MIN_USERNAME_LENGTH = 8;
	public static final int MAX_USERNAME_LENGTH = 16;
	public static final int MIN_PASSWORD_LENGTH = 8;
	public static final int MAX_PASSWORD_LENGTH = 16;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String surname;
	@Column(nullable = false)
	private String username;
	private String description;
	@Column(nullable = false)
	private String password;
	@Column
	private String token;
	private byte[] picture;
	private byte[] thumbnail_picture;
	private String picture_extension;
	@Column(nullable = false)
	private String secretQuestion;
	@Column(nullable = false)
	private String secretAnswer;
	@Column(nullable = false)
	private Date registrationDate;
	private boolean isPrivate;
	private int visits;
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<Comment>();
	@OneToMany
	private List<Notification> notifications = new ArrayList<Notification>();
	@ManyToMany
	private Set<User> following = new HashSet<User>();
	@ManyToMany(mappedBy = "following")
	private Set<User> followers = new HashSet<User>();
	@ManyToMany
	private Set<Comment> favourites = new HashSet<Comment>();
	@ManyToMany
	@JoinTable(name = "userss_blacklists")
	private Set<User> blacklisted_users = new HashSet<User>();
	@ManyToMany(mappedBy = "blacklisted_users")
	private Set<User> blacklisted_by = new HashSet<User>();

	protected User() {
	}

	public User(String name, String surname, String username,
			String description, String password, byte[] picture,
			byte[] thumbnail_picture, String picture_extension,
			String secretQuestion, String secretAnswer, Date registrationDate,
			boolean isPrivate) throws IllegalArgumentException {
		if (name == null || surname == null || username == null
				|| password == null || secretQuestion == null
				|| secretAnswer == null) {
			throw new IllegalArgumentException(
					"One or more required fields have not been completed.");
		}
		if (username.length() < MIN_USERNAME_LENGTH
				&& username.length() > MAX_USERNAME_LENGTH) {
			throw new IllegalArgumentException(
					"The user name must contain between " + MIN_USERNAME_LENGTH
							+ " and " + MAX_USERNAME_LENGTH + " characters.");
		}
		if (password.length() < MIN_PASSWORD_LENGTH
				&& password.length() > MAX_PASSWORD_LENGTH) {
			throw new IllegalArgumentException(
					"The password must contain between " + MIN_USERNAME_LENGTH
							+ " and " + MAX_USERNAME_LENGTH + " characters.");
		}
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.description = description;
		this.password = password;
		this.picture = picture;
		this.thumbnail_picture = ThumbnailImageConverter.thumbnailPicture(
				picture, picture_extension);
		this.picture_extension = picture_extension;
		this.secretQuestion = secretQuestion;
		this.secretAnswer = secretAnswer;
		this.registrationDate = registrationDate;
		this.isPrivate = isPrivate;
	}

	public Set<User> getBlacklistedUsers() {
		return blacklisted_users;
	}

	public boolean hasBlacklistedUser(User user) {
		return blacklisted_users.contains(user);
	}

	public void notify(Notification notification) {
		notifications.add(notification);
	}

	public List<Notification> getNotifications() {
		for (Notification notification : notifications) {
			notification.check();
		}
		return notifications;
	}

	public int getNotificationsAmount() {
		return notifications.size();
	}

	public void addFavourite(Comment comment) {
		favourites.add(comment);
	}

	public int favourites() {
		return favourites.size();
	}

	public Set<Comment> getFavourites() {
		return favourites;
	}

	public void removeFavourite(Comment comment) {
		favourites.remove(comment);
	}

	public void addBlacklistedUser(User user) {
		blacklisted_users.add(user);
	}
	
	public void removeBlacklistedUser(User user) {
		blacklisted_users.remove(user);
	}
	
	public boolean isBlacklistedBy(User user) {
		return blacklisted_by.contains(user);
	}

	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}

	public String getPictureExtension() {
		return picture_extension;
	}
	
	public void setPictureExtension(String extension) {
		picture_extension = extension;
	}

	public int getUncheckedNotifications() {
		int ans = 0;
		for (Notification notification : notifications) {
			if (!notification.isChecked()) {
				ans++;
			}
		}
		return ans;
	}

	public int getVisits() {
		return visits;
	}

	public void visit() {
		this.visits++;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public int getFollowersAmount() {
		return followers.size();
	}

	public Set<User> getFollowing() {
		return following;
	}

	public int getFollowingAmount() {
		return following.size();
	}

	public Notification follow(User user, Notification notification) {
		user.notify(notification);
		following.add(user);
		return notification;
	}

	public void unfollow(User user) {
		following.remove(user);
	}
	
	public int following() {
		return following.size();
	}

	public boolean isFollowing(User user) {
		return following.contains(user);
	}

	public boolean isAFollower(User user) {
		return followers.contains(user);
	}

	public void makePrivate() {
		this.isPrivate = true;
	}

	public void makePublic() {
		this.isPrivate = false;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	
	public void setThumbnailPicture(byte[] picture) {
		this.thumbnail_picture = picture;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFullName() {
		return name + " " + surname;
	}

	public String getUsername() {
		return username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte[] getPicture() {
		return picture;
	}

	public byte[] getThumbnailPicture() {
		return thumbnail_picture;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public String getConfirmPassword() {
		return password;
	}

	public void setConfirmPassword(String password) {
		this.password = password;
	}

	public void deleteComment(Comment comment) {
		comments.remove(comment);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public boolean validateToken(String token) {
		return this.token != null && this.token.equals(token);
	}
	
	public void resetToken() {
		this.token = null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}