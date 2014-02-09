package ar.edu.itba.it.paw.web.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.ocpsoft.prettytime.PrettyTime;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.CommentRepo;
import ar.edu.itba.it.paw.domain.EntityModel;
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
	private String commentTextarea;

	@SuppressWarnings("serial")
	public ProfilePage(final int userId) {
		IModel<User> userModel = new EntityModel<User>(User.class, userId);
		User profileUser = userModel.getObject();
		if (profileUser.getPicture() != null) {
			add(new Image("profilePicture", new ImageResourceReference(
					profileUser.getPicture())));
		} else {
			add(new Image("profilePicture", SocialCthulhuApp.DEFAULT_IMAGE));
		}
		add(new Label("profileUsername", new PropertyModel<String>(userModel,
				"username")));
		add(new Label("profileName", new PropertyModel<String>(userModel,
				"name")));
		add(new Label("profileSurname", new PropertyModel<String>(userModel,
				"surname")));
		add(new Label("profileDescription", new PropertyModel<String>(
				userModel, "description")));
		add(new Label("profileVisits", new PropertyModel<String>(userModel,
				"visits")));
		add(new Link<String>("followersLink") {

			@Override
			public void onClick() {
				setResponsePage(new FollowersPage(userId));
			}
		}.add(new Label("followersAmount", new PropertyModel<String>(userModel,
				"followers.size"))));
		add(new Link<String>("followingLink") {

			@Override
			public void onClick() {
				setResponsePage(new FollowingPage(userId));
			}
		}.add(new Label("followingAmount", new PropertyModel<String>(userModel,
				"following.size"))));

		Link<String> notificationsLink = new Link<String>("notificationsLink") {

			@Override
			public void onClick() {
				setResponsePage(new NotificationsPage());
			}
		};
		notificationsLink.add(new Label("notifications",
				new PropertyModel<String>(userModel, "notifications.size")));

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
		suggestedUsersLink.add(new Image("suggestedUsers",
				SocialCthulhuApp.SUGGESTED_USERS));
		add(suggestedUsersLink);
		add(new Link<String>("favouritesLink") {

			@Override
			public void onClick() {
				setResponsePage(new FavouritesPage());
			}
		}.add(new Image("favourites", SocialCthulhuApp.FAVOURITES)));

		Link<String> editProfileLink = new Link<String>("editProfileLink") {

			@Override
			public void onClick() {
				setResponsePage(new EditProfilePage(
						users.getUser(SocialCthulhuSession.get().getUsername())));
			}
		};
		editProfileLink.add(new Label("edit", getString("edit")));
		add(editProfileLink);
		Link<String> followLink = new Link<String>("followLink") {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
			}
		};
		followLink.add(new Label("follow", getString("follow")));
		add(followLink);
		Link<String> unfollowLink = new Link<String>("unfollowLink") {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub

			}
		};
		unfollowLink.add(new Label("unfollow", getString("unfollow")));
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
						comments.getReferences(commentTextarea), author);
				comments.addComment(comment);
			}
		};
		form.add(new TextArea<String>("commentTextarea").setRequired(true));
		add(form);
		add(new RefreshingView<CommentWrapper>("wrapperComment") {
			@Override
			protected Iterator<IModel<CommentWrapper>> getItemModels() {
				IModel<User> userModel = new EntityModel<User>(User.class,
						userId);
				User commenter = userModel.getObject();
				System.out.println("COMMENTER USERNAME = "
						+ commenter.getUsername());
				List<IModel<CommentWrapper>> result = new ArrayList<IModel<CommentWrapper>>();
				List<Comment> commentList = commenter.getComments();
				List<CommentWrapper> transformedComments = transformComments(
						commentList, commenter);
				for (CommentWrapper c : transformedComments) {
					result.add(new CompoundPropertyModel<CommentWrapper>(
							new EntityModel<CommentWrapper>(
									CommentWrapper.class, c)));
				}
				return result.iterator();
			}

			@Override
			protected void populateItem(Item<CommentWrapper> item) {
				item.add(new Label("transformedComment"));
				Link<CommentWrapper> removeFavouriteLink = new Link<CommentWrapper>(
						"removeFavouriteLink", item.getModel()) {

					@Override
					public void onClick() {
						User user = users.getUser(SocialCthulhuSession.get()
								.getUsername());
						user.removeFavourite(getModelObject().getComment());
					}

				};
				removeFavouriteLink.add(new Label("removeFavourite",
						getString("remove_favourite")));
				item.add(removeFavouriteLink);
				Link<CommentWrapper> addFavouriteLink = new Link<CommentWrapper>(
						"addFavouriteLink", item.getModel()) {

					@Override
					public void onClick() {
						User author = users.getUser(SocialCthulhuSession.get()
								.getUsername());
						Comment comment = new Comment(author, new Date(),
								commentTextarea, comments.getHashtagList(
										commentTextarea, author),
								comments.getReferences(commentTextarea), author);
						users.getUser(SocialCthulhuSession.get().getUsername()).addFavourite(comment);
					}

				};
				addFavouriteLink.add(new Label("addFavourite",
						getString("add_favourite")));
				item.add(addFavouriteLink);
				Link<CommentWrapper> recthulhuLink = new Link<CommentWrapper>(
						"recthulhuLink", item.getModel()) {

					@Override
					public void onClick() {
						setResponsePage(new ProfilePage(getModelObject()
								.getComment().getAuthor().getId()));
					}

				};
				recthulhuLink
						.add(new Label("recthulhu", getString("recthulhu")));
				item.add(recthulhuLink);
				if (!item.getModelObject().getComment().isRecuthulu()) {
					recthulhuLink.setVisible(false);
				}
				Link<String> commentUsernameLink = new Link<String>(
						"commentUsernameLink") {

					@Override
					public void onClick() {
						// setResponsePage(new
						// ProfilePage(item.getModelObject().getComment().getAuthor().getId()));
					}

				};
				commentUsernameLink.add(new Label("comment.author.username"));
				item.add(commentUsernameLink);
				Link<String> authorUsernameLink = new Link<String>(
						"authorUsernameLink") {

					@Override
					public void onClick() {
						// setResponsePage(new
						// ProfilePage(item.getModelObject().getComment().getOriginalAuthor().getId()));
					}

				};
				authorUsernameLink.add(new Label(
						"comment.originalAuthor.username"));
				item.add(authorUsernameLink);

				PrettyTime p = new PrettyTime();
				item.add(new Label("commentDate", p.format(item
						.getModelObject().getComment().getDate())));

				Link<String> deleteCommentLink = new Link<String>(
						"deleteCommentLink") {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub

					}

				};
				deleteCommentLink.add(new Label("deleteComment",
						getString("delete_comment")));
				item.add(deleteCommentLink);
			}
		});

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

	private List<CommentWrapper> transformComments(List<Comment> commentList,
			User u) {
		List<CommentWrapper> result = new ArrayList<CommentWrapper>();
		for (Comment c : commentList) {
			result.add(new CommentWrapper(c,
					getProcessedComment(c.getComment()), u));
		}
		return result;
	}
}