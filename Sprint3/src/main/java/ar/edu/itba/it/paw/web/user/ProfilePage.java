package ar.edu.itba.it.paw.web.user;

import java.io.IOException;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.CommentRepo;
import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.Notification;
import ar.edu.itba.it.paw.domain.NotificationRepo;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuApp;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.common.ImageResourceReference;

public class ProfilePage extends BasePage {

	private static final long serialVersionUID = 1L;
	@SpringBean
	private UserRepo users;
	@SpringBean
	private CommentRepo comments;
	@SpringBean
	private NotificationRepo notifications;
	private String commentTextarea;
	private transient User currentUser;

	@SuppressWarnings("serial")
	public ProfilePage(final PageParameters parameters) {
		final String username = parameters.get("username").toString();
		currentUser = users.getUser(username);
		if (currentUser == null) {
			setResponsePage(getApplication().getHomePage());
			return;
		}
		boolean isFollowing = users
				.getUser(SocialCthulhuSession.get().getUsername())
				.getFollowing().contains(currentUser);
		User profileUser = currentUser;
		if (profileUser.getPicture() != null) {
			add(new Image("profilePicture", new ImageResourceReference(
					profileUser.getPicture())));
		} else {
			add(new Image("profilePicture", SocialCthulhuApp.DEFAULT_IMAGE));
		}
		if (!currentUser.getUsername().equals(
				SocialCthulhuSession.get().getUsername()))
			currentUser.visit();
		add(new Label("profileUsername", currentUser.getUsername()));
		add(new Label("profileName", currentUser.getName()));
		add(new Label("profileSurname", currentUser.getSurname()));
		add(new Label("profileDescription", currentUser.getDescription()));
		add(new Label("profileVisits", currentUser.getVisits()));
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

		Link<String> notificationsLink = new Link<String>("notificationsLink") {

			@Override
			public void onClick() {
				setResponsePage(new NotificationsPage());
			}
		};
		notificationsLink.add(new Label("notifications", currentUser
				.getNotificationsAmount()));

		add(notificationsLink);
		Link<String> suggestedUsersLink = new Link<String>("suggestedUsersLink") {

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
		};
		add(suggestedUsersLink);
		add(new Link<String>("favouritesLink") {

			@Override
			public void onClick() {
				setResponsePage(new FavouritesPage());
			}
		}.setVisible(SocialCthulhuSession.get().getUsername()
				.equals(users.getUser(currentUser.getId()).getUsername())));

		Link<String> editProfileLink = new Link<String>("editProfileLink") {

			@Override
			public void onClick() {
				setResponsePage(new EditProfilePage(
						users.getUser(SocialCthulhuSession.get().getUsername())));
			}
		};
		editProfileLink.add(new Label("edit", getString("edit")));
		add(editProfileLink);
		Link<User> followLink = new Link<User>("followLink",
				new EntityModel<User>(User.class, currentUser.getId())) {

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
		};
		followLink.add(new Label("follow", getString("follow")));
		followLink.setVisible(!isFollowing);
		add(followLink);
		Link<User> unfollowLink = new Link<User>("unfollowLink",
				new EntityModel<User>(User.class, currentUser.getId())) {

			@Override
			public void onClick() {
				User user = users.getUser(SocialCthulhuSession.get()
						.getUsername());
				user.unfollow(getModelObject());
				setResponsePage(new ProfilePage(new PageParameters().set(
						"username", getModelObject().getUsername())));
			}
		};
		unfollowLink.add(new Label("unfollow", getString("unfollow")));
		unfollowLink.setVisible(isFollowing);
		add(unfollowLink);
		FeedbackPanel errorPanel = new FeedbackPanel("errorPanel");
		add(errorPanel);
		Form<ProfilePage> form = new Form<ProfilePage>("commentForm",
				new CompoundPropertyModel<ProfilePage>(this)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				User author = users.getUser(SocialCthulhuSession.get()
						.getUsername());
				Comment comment = new Comment(author, new Date(),
						commentTextarea, comments.getHashtagList(
								commentTextarea, author),
						comments.getReferences(commentTextarea, author), author);
				comments.addComment(comment);
				commentTextarea = "";
				setResponsePage(new ProfilePage(parameters));
			}
		};
		form.add(new TextArea<String>("commentTextarea").setRequired(true));
		add(form);
		add(new CommentsPanel("comments-panel", currentUser.getId(),
				currentUser.getComments()));

		if (profileUser.getUsername().equals(
				((SocialCthulhuSession) getSession()).getUsername())) {
			followLink.setVisible(false);
			unfollowLink.setVisible(false);
		} else {
			notificationsLink.setVisible(false);
			suggestedUsersLink.setVisible(false);
			form.setVisible(false);
			errorPanel.setVisible(false);
			editProfileLink.setVisible(false);

			if (users
					.getUser(
							((SocialCthulhuSession) getSession()).getUsername())
					.getFollowing().contains(profileUser)) {
				followLink.setVisible(false);
			} else {
				unfollowLink.setVisible(false);
			}
		}
	}
}