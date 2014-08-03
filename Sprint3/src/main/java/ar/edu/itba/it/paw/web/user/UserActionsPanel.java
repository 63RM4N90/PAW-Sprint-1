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
		
		boolean isFollowing = loggedUserIsFollowing();
		
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
		}.add(new Label("notifications", currentUser.getUncheckedNotifications()))
				.setVisible(isSameUser));

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
		}.setVisible(SocialCthulhuSession.get().getUsername()
				.equals(users.getUser(currentUser.getId()).getUsername())));

		add(new Link<String>("editProfileLink") {

			@Override
			public void onClick() {
				setResponsePage(new EditProfilePage(
						users.getUser(SocialCthulhuSession.get().getUsername())));
			}
		}.add(new Label("edit", getString("edit"))).setVisible(isSameUser));

		add(new Link<User>("followLink", new EntityModel<User>(User.class,
				currentUser.getId())) {

			@Override
			public void onClick() {
				User user = users.getUser(SocialCthulhuSession.get()
						.getUsername());
				Notification notification = new Notification(user,
						user.getUsername() + " is following you :).");
				notifications.save(notification);
				user.follow(getModelObject(), notification);
				setResponsePage(new ProfilePage(new PageParameters().set(
						"username", getModelObject().getUsername())));
			}
		}.add(new Label("follow", getString("follow")).setVisible(!isFollowing
				&& !isSameUser)));

		add(new Link<User>("unfollowLink", new EntityModel<User>(User.class,
				currentUser.getId())) {

			@Override
			public void onClick() {
				User user = users.getUser(SocialCthulhuSession.get()
						.getUsername());
				user.unfollow(getModelObject());
				setResponsePage(new ProfilePage(new PageParameters().set(
						"username", getModelObject().getUsername())));
			}
		}.add(new Label("unfollow", getString("unfollow"))
				.setVisible(isFollowing)));
	}
	
	private boolean loggedUserIsFollowing() {
		return users.getUser(SocialCthulhuSession.get().getUsername())
				.getFollowing().contains(currentUser);
	}
}
