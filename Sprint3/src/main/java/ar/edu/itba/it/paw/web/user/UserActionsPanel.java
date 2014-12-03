package ar.edu.itba.it.paw.web.user;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.Notification;
import ar.edu.itba.it.paw.domain.NotificationRepo;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;

public class UserActionsPanel extends Panel {

	private static final long serialVersionUID = -4235379871556971356L;
	private transient User currentUser;
	@SpringBean
	private UserRepo users;
	@SpringBean
	private NotificationRepo notifications;

	@SuppressWarnings("serial")
	public UserActionsPanel(String id, User userPage, final boolean isSameUser) {
		super(id);
		this.currentUser = userPage;
		final String logged_in_username = SocialCthulhuSession.get().getUsername();
		User logged_in_user = users.getUser(logged_in_username);
		boolean isFollowing = loggedUserIsFollowing(logged_in_user);
		boolean can_black_list = canBlackList(logged_in_user);
		boolean can_unblack_list = canUnblackList(logged_in_user);

		add(new Link<String>("followersLink") {

			@Override
			public void onClick() {
				setResponsePage(new FollowersPage(currentUser.getId()));
			}
		}.add(new Label("followersAmount", currentUser.getFollowersAmount())));

		add(new Link<String>("followingLink") {

			@Override
			public void onClick() {
				setResponsePage(new FollowingPage(currentUser.getId()));
			}
		}.add(new Label("followingAmount", currentUser.getFollowingAmount())));

		add(new Link<String>("notificationsLink") {

			@Override
			public void onClick() {
				setResponsePage(new NotificationsPage());
			}
		}.add(new Label("notifications", currentUser
				.getUncheckedNotifications())).setVisible(isSameUser));

		add(new Link<String>("suggestedUsersLink") {

			@Override
			public void onClick() {
				try {
					setResponsePage(new SuggestedFriendsPage());
				} catch (InvalidPropertiesFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.setVisible(isSameUser));

		add(new Link<String>("favouritesLink") {

			@Override
			public void onClick() {
				setResponsePage(new FavouritesPage());
			}
		}.setVisible(logged_in_username != null
				&& logged_in_username.equals(users.getUser(currentUser.getId())
						.getUsername())));

		add(new Link<String>("followingMessagesLink") {

			@Override
			public void onClick() {
				setResponsePage(new FollowingMessagesPage());
			}
		}.setVisible(logged_in_username != null
				&& logged_in_username.equals(users.getUser(currentUser.getId())
						.getUsername())));

		add(new Link<String>("editProfileLink") {

			@Override
			public void onClick() {
				setResponsePage(new EditProfilePage(
						users.getUser(logged_in_username)));
			}
		}.add(new Label("edit", getString("edit"))).setVisible(
				isSameUser && logged_in_username != null));

		add(new Link<User>("followLink", new EntityModel<User>(User.class,
				currentUser.getId())) {

			@Override
			public void onClick() {
				User user = users.getUser(logged_in_username);
				Notification notification = new Notification(user,
						user.getUsername() + " is following you :).");
				notifications.save(notification);
				user.follow(getModelObject(), notification);
				setResponsePage(new ProfilePage(new PageParameters().set(
						"username", getModelObject().getUsername())));
			}
		}.add(new Label("follow", getString("follow")).setVisible(!isFollowing
				&& !isSameUser && logged_in_user != null)));

		add(new Link<User>("unfollowLink", new EntityModel<User>(User.class,
				currentUser.getId())) {

			@Override
			public void onClick() {
				User user = users.getUser(logged_in_username);
				user.unfollow(getModelObject());
				setResponsePage(new ProfilePage(new PageParameters().set(
						"username", getModelObject().getUsername())));
			}
		}.add(new Label("unfollow", getString("unfollow"))
				.setVisible(isFollowing)));

		add(new Link<User>("blacklistLink", new EntityModel<User>(User.class,
				currentUser.getId())) {

			@Override
			public void onClick() {
				User user = users.getUser(SocialCthulhuSession.get()
						.getUsername());
				user.addBlacklistedUser(getModelObject());
				setResponsePage(new ProfilePage(new PageParameters().set(
						"username", getModelObject().getUsername())));
			}
		}.add(new Label("blacklist", getString("blacklist"))
				.setVisible(can_black_list)));

		add(new Link<User>("unblacklistLink", new EntityModel<User>(User.class,
				currentUser.getId())) {

			@Override
			public void onClick() {
				User user = users.getUser(SocialCthulhuSession.get()
						.getUsername());
				user.removeBlacklistedUser(getModelObject());
				setResponsePage(new ProfilePage(new PageParameters().set(
						"username", getModelObject().getUsername())));
			}
		}.add(new Label("unblacklist", getString("unblacklist"))
				.setVisible(can_unblack_list)));
		add(new Link<String>("blacklisted_users") {

			@Override
			public void onClick() {
				setResponsePage(new BlacklistedUsersPage());
			}
		}.add(new Label("blacklisted_users_label",
				getString("blacklisted_users_label")).setVisible(isSameUser)));
	}

	private boolean loggedUserIsFollowing(User logged_in_user) {
		return logged_in_user != null
				&& logged_in_user.getFollowing().contains(currentUser);
	}

	private boolean canBlackList(User logged_in_user) {
		return logged_in_user != null && !logged_in_user.equals(currentUser)
				&& logged_in_user.isPrivate()
				&& !logged_in_user.hasBlacklistedUser(currentUser);
	}

	private boolean canUnblackList(User logged_in_user) {
		return logged_in_user != null && !logged_in_user.equals(currentUser)
				&& logged_in_user.isPrivate()
				&& logged_in_user.hasBlacklistedUser(currentUser);
	}
}
