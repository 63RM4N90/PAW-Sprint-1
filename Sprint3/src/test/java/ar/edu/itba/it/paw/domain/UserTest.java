package ar.edu.itba.it.paw.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

	private User testUser;

	@Before
	public void setUp() {
		testUser = new User("test", "test", "test", "test", "12345678", null, null,
				null, "png", "test", "test", new Date(), false);
	}

	@Test
	public void followUserTest() {
		User follow = new User("follow", "follow", "follow", "follow",
				"12345678", null, null, null, "png", "follow", "follow", new Date(),
				false);

		Assert.assertEquals(testUser.getFollowing().size(), 0);
		testUser.follow(follow, new Notification(follow, "notification"));
		Assert.assertEquals(testUser.following(), 1);
	}

	@Test
	public void unfollowUserTest() {
		User follow = new User("follow", "follow", "follow", "follow",
				"12345678", null, null, null, "png", "follow", "follow", new Date(),
				false);

		testUser.follow(follow, new Notification(follow, "notification"));
		Assert.assertEquals(testUser.getFollowing().size(), 1);
		testUser.unfollow(follow);
		Assert.assertEquals(testUser.getFollowing().size(), 0);
	}

	@Test
	public void notifyUserTest() {
		User notificator = new User("notificator", "notificator",
				"notificator", "notificator", "12345678", null, null, null, "png",
				"notificator", "notificator", new Date(), false);
		Notification notification = new Notification(notificator,
				"notification");
		Assert.assertEquals(testUser.getUncheckedNotifications(), 0);
		testUser.notify(notification);
		Assert.assertEquals(testUser.getUncheckedNotifications(), 1);
	}

	@Test
	public void checkNotificationTest() {
		User notificator = new User("notificator", "notificator",
				"notificator", "notificator", "12345678", null, null, null, "png",
				"notificator", "notificator", new Date(), false);
		Notification notification = new Notification(notificator,
				"notification");
		testUser.notify(notification);
		Assert.assertEquals(testUser.getUncheckedNotifications(), 1);
		testUser.getNotifications();
		Assert.assertEquals(testUser.getUncheckedNotifications(), 0);
	}

	@Test
	public void addFavouriteTest() {
		Comment favourite = new Comment(testUser, new Date(), "test comment",
				new HashSet<Hashtag>(), new ArrayList<User>(), testUser);
		Assert.assertEquals(testUser.getFavourites().size(), 0);
		testUser.addFavourite(favourite);
		Assert.assertEquals(testUser.getFavourites().size(), 1);
	}

	@Test
	public void removeFavouriteTest() {
		Comment favourite = new Comment(testUser, new Date(), "test comment",
				new HashSet<Hashtag>(), new ArrayList<User>(), testUser);
		testUser.addFavourite(favourite);
		Assert.assertEquals(testUser.getFavourites().size(), 1);
		testUser.removeFavourite(favourite);
		Assert.assertEquals(testUser.getFavourites().size(), 0);
	}
}
