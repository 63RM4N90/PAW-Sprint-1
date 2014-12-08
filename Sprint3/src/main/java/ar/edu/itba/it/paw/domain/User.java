package ar.edu.itba.it.paw.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
	private byte[] background;
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
	private List<User> following = new ArrayList<User>();
	@ManyToMany(mappedBy = "following")
	private List<User> followers = new ArrayList<User>();
	@ManyToMany
	private List<Comment> favourites = new ArrayList<Comment>();
	@ManyToMany
	@JoinTable(name = "userss_blacklists")
	private List<User> blacklisted_users = new ArrayList<User>();
	@ManyToMany(mappedBy = "blacklisted_users")
	private List<User> blacklisted_by = new ArrayList<User>();
	@OneToMany(mappedBy = "_owner", cascade = CascadeType.ALL)
	private List<UserList> user_lists = new ArrayList<UserList>();
	@ManyToMany
	private Set<UserList> lists = new HashSet<UserList>();

	protected User() {
	}

	public User(String name, String surname, String username,
			String description, String password, byte[] picture, byte[] background,
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
		this.background = background;
		this.thumbnail_picture = ThumbnailImageConverter.thumbnailPicture(
				picture, picture_extension);
		this.picture_extension = picture_extension;
		this.secretQuestion = secretQuestion;
		this.secretAnswer = secretAnswer;
		this.registrationDate = registrationDate;
		this.isPrivate = isPrivate;
	}

	public List<User> getBlacklistedUsers() {
		return blacklisted_users;
	}

	public boolean hasBlacklistedUser(User user) {
		return blacklisted_users.contains(user);
	}

	public void notify(Notification notification) {
		notifications.add(notification);
	}
	
	public List<Comment> getFollowingComments() {
		SortedSet<Comment> comments = new TreeSet<Comment>();
		List<Comment> ans = new ArrayList<Comment>();
		for (User user : following)
			comments.addAll(user.getComments());

		ans.addAll(comments);
		
		return ans;
	}
	
	public List<UserList> userlists() {
		return user_lists;
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

	public List<Comment> getFavourites() {
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
	
	public List<Comment> getOrderedComments() {
		Collections.reverse(comments);
		return comments;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public int getFollowersAmount() {
		return followers.size();
	}

	public List<User> getFollowing() {
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
	
	public boolean isPublic() {
		return !isPrivate();
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	
	public void setThumbnailPicture(byte[] picture) {
		this.thumbnail_picture = picture;
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

	public byte[] getBackground() {
		return background;
	}

	public void setBackground(byte[] background) {
		this.background = background;
	}

	public byte[] getThumbnailPicture() {
		return thumbnail_picture;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}
	
	public String getConfirmPassword() {
		return password;
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

	public void addBelongingList(UserList userList) {
		lists.add(userList);
	}

	public void removeFromBelongingList(UserList userList) {
		lists.remove(userList);
	}
}