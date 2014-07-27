package ar.edu.itba.it.paw.web.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
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
import ar.edu.itba.it.paw.domain.Notification;
import ar.edu.itba.it.paw.domain.NotificationRepo;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuApp;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.common.CommentWrapper;
import ar.edu.itba.it.paw.web.common.CommentWrapperROM;
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
	public ProfilePage(final int userId) {
		IModel<User> userModel = new EntityModel<User>(User.class, userId);
		boolean isFollowing = users
				.getUser(SocialCthulhuSession.get().getUsername())
				.getFollowing().contains(userModel.getObject());
		System.out.println("IS FOLLOWING = " + isFollowing);
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
		add(suggestedUsersLink);
		add(new Link<String>("favouritesLink") {

			@Override
			public void onClick() {
				setResponsePage(new FavouritesPage());
			}
		});

		Link<String> editProfileLink = new Link<String>("editProfileLink") {

			@Override
			public void onClick() {
				setResponsePage(new EditProfilePage(
						users.getUser(SocialCthulhuSession.get().getUsername())));
			}
		};
		editProfileLink.add(new Label("edit", getString("edit")));
		add(editProfileLink);
		Link<User> followLink = new Link<User>("followLink", userModel) {

			@Override
			public void onClick() {
				User user = users.getUser(SocialCthulhuSession.get()
						.getUsername());
				Notification notification = new Notification(user,
						user.getUsername() + " is following you :).");
				notifications.save(notification);
				user.follow(getModelObject(), notification);
			}
		};
		followLink.add(new Label("follow", getString("follow")));
		followLink.setVisible(!isFollowing);
		add(followLink);
		Link<User> unfollowLink = new Link<User>("unfollowLink", userModel) {

			@Override
			public void onClick() {
				User user = users.getUser(SocialCthulhuSession.get()
						.getUsername());
				user.unfollow(getModelObject());
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
						comments.getReferences(commentTextarea), author);
				comments.addComment(comment);
				commentTextarea = "";
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
				List<IModel<CommentWrapper>> result = new ArrayList<IModel<CommentWrapper>>();
				List<Comment> commentList = commenter.getComments();
				List<CommentWrapper> transformedComments = transformComments(
						commentList, commenter);
				for (CommentWrapper c : transformedComments) {
					result.add(new CommentWrapperROM(new EntityModel<Comment>(
							Comment.class, c.getComment()), c
							.getTransformedComment(), commenter));
				}
				return result.iterator();
			}

			@Override
			protected void populateItem(Item<CommentWrapper> item) {

				boolean equalUsers = item
						.getModelObject()
						.getComment()
						.getAuthor()
						.equals(item.getModelObject().getComment()
								.getOriginalAuthor());

				boolean alreadyFavourited = users
						.getUser(SocialCthulhuSession.get().getUsername())
						.getFavourites()
						.contains(item.getModelObject().getComment());

				item.add(new MultiLineLabel("transformedComment", item
						.getModelObject().getTransformedComment())
						.setEscapeModelStrings(false));
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
				removeFavouriteLink.setVisible(alreadyFavourited);
				item.add(removeFavouriteLink);
				Link<CommentWrapper> addFavouriteLink = new Link<CommentWrapper>(
						"addFavouriteLink", item.getModel()) {

					@Override
					public void onClick() {
						CommentWrapper comment = getModelObject();
						users.getUser(SocialCthulhuSession.get().getUsername())
								.addFavourite(comment.getComment());
					}
				};
				addFavouriteLink.add(new Label("addFavourite",
						getString("add_favourite")));
				addFavouriteLink.setVisible(!alreadyFavourited);
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
				recthulhuLink.setVisible(!equalUsers);
				item.add(recthulhuLink);
				Link<CommentWrapper> commentUsernameLink = new Link<CommentWrapper>(
						"commentUsernameLink", item.getModel()) {

					@Override
					public void onClick() {
						setResponsePage(new ProfilePage(getModelObject()
								.getComment().getAuthor().getId()));
					}
				};
				commentUsernameLink.add(new Label("comment_username", item
						.getModelObject().getComment().getAuthor()
						.getUsername()));
				item.add(commentUsernameLink);
				Label recthuledFrom = new Label("recthuled_from",
						getString("recthuled_from"));
				Link<CommentWrapper> authorUsernameLink = new Link<CommentWrapper>(
						"authorUsernameLink", item.getModel()) {

					@Override
					public void onClick() {
						setResponsePage(new ProfilePage(getModelObject()
								.getComment().getOriginalAuthor().getId()));
					}
				};
				authorUsernameLink.setVisible(!equalUsers);
				recthuledFrom.setVisible(!equalUsers);
				authorUsernameLink.add(new Label("comment_author", item
						.getModelObject().getComment().getAuthor()
						.getUsername()));
				item.add(authorUsernameLink);
				item.add(recthuledFrom);
				PrettyTime p = new PrettyTime();
				item.add(new Label("commentDate", p.format(item
						.getModelObject().getComment().getDate())));

				Link<CommentWrapper> deleteCommentLink = new Link<CommentWrapper>(
						"deleteCommentLink", item.getModel()) {

					@Override
					public void onClick() {
						CommentWrapper cw = getModelObject();
						comments.delete(cw.getComment());
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
					getProcessedComment(c.getComment()), c.favouritedBy(u)));
		}
		return result;
	}
}