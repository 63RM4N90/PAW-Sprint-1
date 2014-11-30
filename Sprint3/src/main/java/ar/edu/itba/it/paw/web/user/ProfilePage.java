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
	private transient User currentUser;

	@SuppressWarnings("serial")
	public ProfilePage(final PageParameters parameters) {
		currentUser = users.getUser(parameters.get("username").toString());
		User logged_in_user = users.getUser(SocialCthulhuSession.get()
				.getUsername());
		if (currentUser == null) {
			setResponsePage(getApplication().getHomePage());
			return;
		}
		boolean has_blacklisted_you = logged_in_user
				.isBlacklistedBy(currentUser) && currentUser.isPrivate();
		boolean isSameUser = loggedUserIsCurrentUser(logged_in_user);

		add(new Label("blacklisted_you", getString("blacklisted_you"))
				.setVisible(has_blacklisted_you));

		add(new Image("profilePicture", getProfilePicture())
				.setVisible(!has_blacklisted_you));

		add(new Label("profileNameTitle", getString("profileNameTitle"))
				.setVisible(!has_blacklisted_you));

		add(new Label("profileSurnameTitle", getString("profileSurnameTitle"))
				.setVisible(!has_blacklisted_you));

		add(new Label("profileDescriptionTitle",
				getString("profileDescriptionTitle"))
				.setVisible(!has_blacklisted_you));

		add(new Label("profileVisitsTitle", getString("profileVisitsTitle"))
				.setVisible(!has_blacklisted_you));

		if (!currentUser.getUsername().equals(
				SocialCthulhuSession.get().getUsername()))
			currentUser.visit();

		add(new Label("profileUsername", currentUser.getUsername())
				.setVisible(!has_blacklisted_you));
		add(new Label("profileName", currentUser.getName())
				.setVisible(!has_blacklisted_you));
		add(new Label("profileSurname", currentUser.getSurname())
				.setVisible(!has_blacklisted_you));
		add(new Label("profileDescription", currentUser.getDescription())
				.setVisible(!has_blacklisted_you));
		add(new Label("profileVisits", currentUser.getVisits())
				.setVisible(!has_blacklisted_you));

		add(new UserActionsPanel("user_actions_panel", currentUser, isSameUser)
				.setVisible(!has_blacklisted_you));

		add(new FeedbackPanel("errorPanel").setVisible(isSameUser
				&& !has_blacklisted_you));

		add(new Form<ProfilePage>("commentForm",
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
		}.add(new TextArea<String>("commentTextarea").setRequired(true))
				.setVisible(isSameUser));

		IModel<List<CommentWrapper>> commentModel = new CommentWrapperModel() {
			@Override
			protected List<Comment> transformableLoad() {
				return users.getUser(parameters.get("username").toString())
						.getComments();
			}
		};
		add(new CommentsPanel("comments-panel", currentUser.getId(),
				commentModel).setVisible(isSameUser && !has_blacklisted_you));
	}

	private boolean loggedUserIsCurrentUser(User logged_in_user) {
		return currentUser.getUsername().equals(logged_in_user.getUsername());
	}

	private ResourceReference getProfilePicture() {
		if (currentUser.getPicture() != null) {
			return new ImageResourceReference(currentUser.getPicture());
		} else {
			return SocialCthulhuApp.DEFAULT_IMAGE;
		}
	}
}