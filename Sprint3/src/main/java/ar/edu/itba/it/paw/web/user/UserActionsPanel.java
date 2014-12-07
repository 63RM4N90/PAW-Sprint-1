package ar.edu.itba.it.paw.web.user;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.Notification;
import ar.edu.itba.it.paw.domain.NotificationRepo;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;

@SuppressWarnings("serial")
public class UserActionsPanel extends Panel {

	@SpringBean
	private UserRepo users;
	@SpringBean
	private NotificationRepo notifications;
	private transient User user;
	private transient User logged_in_user;

	public UserActionsPanel(final String id, final PageParameters params) {
		super(id);
		user = users.getUser(params.get("username").toString());
		logged_in_user = SocialCthulhuSession.get().getUser();
		boolean is_same_user = logged_in_user != null
				&& logged_in_user.getUsername().equals(user.getUsername());
		boolean isFollowing = loggedUserIsFollowing(logged_in_user);
		boolean can_black_list = canBlackList(logged_in_user);
		boolean can_unblack_list = canUnblackList(logged_in_user);

		IModel<User> user_model = new EntityModel<User>(User.class, user);

		add(new Link<Void>("followersLink") {
			@Override
			public void onClick() {
				setResponsePage(new FollowersPage(params));
			}
		}.add(new Label("followersAmount", user.getFollowersAmount())));

		add(new Link<Void>("followingLink") {
			@Override
			public void onClick() {
				setResponsePage(new FollowingPage(params));
			}
		}.add(new Label("followingAmount", user.getFollowingAmount())));

		add(new Link<Void>("notificationsLink") {
			@Override
			public void onClick() {
				setResponsePage(new NotificationsPage());
			}
		}.add(new Label("notifications", user.getUncheckedNotifications()))
				.setVisible(is_same_user));

		add(new Link<Void>("suggestedUsersLink") {
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
		}.setVisible(is_same_user));

		add(new Link<Void>("favouritesLink") {
			@Override
			public void onClick() {
				setResponsePage(new FavouritesPage());
			}
		}.setVisible(logged_in_user != null));

		add(new Link<Void>("followingMessagesLink") {
			@Override
			public void onClick() {
				setResponsePage(new FollowingMessagesPage());
			}
		}.setVisible(logged_in_user != null));

		add(new Link<Void>("editProfileLink") {
			@Override
			public void onClick() {
				setResponsePage(new EditProfilePage(SocialCthulhuSession.get()
						.getUser()));
			}
		}.add(new Label("edit", getString("edit"))).setVisible(
				is_same_user && logged_in_user != null));

		add(new Link<User>("followLink", user_model) {
			@Override
			public void onClick() {
				Notification notification = new Notification(logged_in_user,
						logged_in_user.getUsername() + " " + this.getString("follow_notif_text"));
				notifications.save(notification);
				SocialCthulhuSession.get().getUser()
						.follow(getModelObject(), notification);
				setResponsePage(new ProfilePage(new PageParameters().set(
						"username", getModelObject().getUsername())));
			}
		}.add(new Label("follow", getString("follow")).setVisible(!isFollowing
				&& !is_same_user && logged_in_user != null)));

		add(new Link<User>("unfollowLink", user_model) {
			@Override
			public void onClick() {
				SocialCthulhuSession.get().getUser().unfollow(getModelObject());
				setResponsePage(new ProfilePage(new PageParameters().set(
						"username", getModelObject().getUsername())));
			}
		}.add(new Label("unfollow", getString("unfollow"))
				.setVisible(isFollowing)));

		Link<User> blacklist_link = new Link<User>("blacklistLink", user_model) {
			@Override
			public void onClick() {
				User logged_user = SocialCthulhuSession.get().getUser();
				if (logged_user.getBlacklistedUsers()
						.contains(getModelObject())) {
					logged_user.removeBlacklistedUser(getModelObject());
				} else {
					logged_user.addBlacklistedUser(getModelObject());
				}
				setResponsePage(new ProfilePage(new PageParameters().set(
						"username", getModelObject().getUsername())));
			}
		};
		System.out.println("can blacklist = " + can_black_list);
		System.out.println("can unblacklist = " + can_unblack_list);
		blacklist_link.add(new Label("blacklist", getString("blacklist"))
				.setVisible(can_black_list));
		blacklist_link.add(new Label("unblacklist", getString("unblacklist"))
				.setVisible(can_unblack_list));
		add(blacklist_link.setVisible(can_unblack_list || can_black_list));

		add(new Link<Void>("blacklisted_users") {
			@Override
			public void onClick() {
				setResponsePage(new BlacklistedUsersPage());
			}
		}.add(new Label("blacklisted_users_label",
				getString("blacklisted_users_label")).setVisible(is_same_user)));

		add(new Link<User>("userListsLink", user_model) {
			@Override
			public void onClick() {
				setResponsePage(new UserListsPage());
			}
		}.setVisible(is_same_user));
	}

	private boolean loggedUserIsFollowing(User logged_in_user) {
		return logged_in_user != null
				&& logged_in_user.getFollowing().contains(user);
	}

	private boolean canBlackList(User logged_in_user) {
		return logged_in_user != null && !logged_in_user.equals(user)
				&& logged_in_user.isPrivate()
				&& !logged_in_user.hasBlacklistedUser(user);
	}

	private boolean canUnblackList(User logged_in_user) {
		return logged_in_user != null && !logged_in_user.equals(user)
				&& logged_in_user.isPrivate()
				&& logged_in_user.hasBlacklistedUser(user);
	}
}
