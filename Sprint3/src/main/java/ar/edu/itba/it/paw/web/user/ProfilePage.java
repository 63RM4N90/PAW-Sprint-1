package ar.edu.itba.it.paw.web.user;

import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.CommentRepo;
import ar.edu.itba.it.paw.domain.Notification;
import ar.edu.itba.it.paw.domain.NotificationRepo;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuApp;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.common.CommentWrapper;
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

	@SuppressWarnings("serial")
	public ProfilePage(final PageParameters parameters) {
		final User currentUser = users.getUser(parameters.get("username")
				.toString());
		User logged_in_user = SocialCthulhuSession.get().getUser();
		if (currentUser == null) {
			setResponsePage(getApplication().getHomePage());
			return;
		}
		boolean can_view_profile = logged_in_user != null
				|| currentUser != null && currentUser.isPublic();
		boolean has_blacklisted_you = logged_in_user != null
				&& logged_in_user.isBlacklistedBy(currentUser)
				&& currentUser.isPrivate();
		boolean isSameUser = logged_in_user != null
				&& loggedUserIsCurrentUser(logged_in_user, currentUser);

		add(new Label("blacklisted_you", getString("blacklisted_you"))
				.setVisible(has_blacklisted_you));

		add(new Label("private_user", getString("private_user"))
				.setVisible(!can_view_profile));

		add(new Image("profilePicture", getProfilePicture(currentUser, "1"))
				.setVisible(!has_blacklisted_you && can_view_profile));

		add(new Image("profileBackground", getProfileBackground(currentUser, "2"))
				.setVisible(!has_blacklisted_you && can_view_profile));

		add(new Label("profileNameTitle", getString("profileNameTitle"))
				.setVisible(!has_blacklisted_you && can_view_profile));

		add(new Label("profileSurnameTitle", getString("profileSurnameTitle"))
				.setVisible(!has_blacklisted_you && can_view_profile));

		if (!currentUser.getUsername().equals(
				SocialCthulhuSession.get().getUsername()))
			currentUser.visit();

		add(new Label("profileDescriptionTitle",
				getString("profileDescriptionTitle"))
				.setVisible(!has_blacklisted_you && can_view_profile));

		add(new Label("profileVisitsTitle", getString("profileVisitsTitle"))
				.setVisible(!has_blacklisted_you && can_view_profile));

		add(new Label("profileUsername", currentUser.getUsername())
				.setVisible(!has_blacklisted_you && can_view_profile));
		add(new Label("profileName", currentUser.getName())
				.setVisible(!has_blacklisted_you && can_view_profile));
		add(new Label("profileSurname", currentUser.getSurname())
				.setVisible(!has_blacklisted_you && can_view_profile));
		add(new Label("profileDescription", currentUser.getDescription())
				.setVisible(!has_blacklisted_you && can_view_profile));
		add(new Label("profileVisits", currentUser.getVisits())
				.setVisible(!has_blacklisted_you && can_view_profile));

		add(new UserActionsPanel("user_actions_panel", parameters)
				.setVisible(!has_blacklisted_you && can_view_profile));

		add(new FeedbackPanel("errorPanel").setVisible(isSameUser
				&& !has_blacklisted_you && can_view_profile));

		add(new Form<ProfilePage>("commentForm",
				new CompoundPropertyModel<ProfilePage>(this)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				User author = SocialCthulhuSession.get().getUser();
				List<User> referencedUsers = comments.getReferences(
						commentTextarea, author);
				for (User ref : referencedUsers) {
					Notification notification = new Notification(author,
							author.getUsername() + " "
									+ this.getString("mention_notif_text"));
					notifications.save(notification);
					ref.notify(notification);
				}

				Comment comment = new Comment(author, new Date(),
						commentTextarea, comments.getHashtagList(
								commentTextarea, author), referencedUsers,
						author);
				comments.addComment(comment);
				commentTextarea = "";
				setResponsePage(new ProfilePage(parameters));
			}
		}.add(new TextArea<String>("commentTextarea").setRequired(true))
				.setVisible(isSameUser && can_view_profile));

		IModel<List<CommentWrapper>> commentModel = new CommentWrapperModel() {
			@Override
			protected List<Comment> transformableLoad() {
				return users.getUser(parameters.get("username").toString())
						.getComments();
			}
		};
		add(new CommentsPanel("comments-panel", commentModel)
				.setVisible(!has_blacklisted_you && can_view_profile));
	}

	private boolean loggedUserIsCurrentUser(User logged_in_user,
			User currentUser) {
		return currentUser.getUsername().equals(logged_in_user.getUsername());
	}

	private ResourceReference getProfilePicture(User currentUser, String suffix) {
		if (currentUser.getPicture() != null) {
			return new ImageResourceReference(currentUser.getPicture(), suffix);
		} else {
			return SocialCthulhuApp.DEFAULT_IMAGE;
		}
	}

	private ResourceReference getProfileBackground(User currentUser, String suffix) {
		if (currentUser.getBackground() != null) {
			return new ImageResourceReference(currentUser.getBackground(), suffix);
		} else {
			return null;
		}
	}
}