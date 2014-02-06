package ar.edu.itba.it.paw.web.user;

import java.util.ArrayList;
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

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuApp;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.common.CommentWrapper;
import ar.edu.itba.it.paw.web.common.ImageResourceReference;

public class ProfilePage extends BasePage {

	private static final long serialVersionUID = 1L;
	@SpringBean
	private UserRepo users;

	@SuppressWarnings("serial")
	public ProfilePage(final int userId)  {
		IModel<User> userModel = new EntityModel<User>(User.class, userId);
		User profileUser = userModel.getObject();
		if(profileUser.getPicture() != null) {
			add(new Image("profilePicture", new ImageResourceReference(profileUser.getPicture())));
		} else {
			add(new Image("profilePicture", SocialCthulhuApp.DEFAULT_IMAGE));
		}
		add(new Label("profileUsername", new PropertyModel<String>(userModel, "username")));
		add(new Label("profileName", new PropertyModel<String>(userModel, "name")));
		add(new Label("profileSurname", new PropertyModel<String>(userModel, "surname")));
		add(new Label("profileDescription", new PropertyModel<String>(userModel, "description")));
		add(new Label("profileVisits", new PropertyModel<String>(userModel, "visits")));
		add(new Link<String>("followersLink") {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				
			}
		}).add(new Label("followersAmount", new PropertyModel<String>(userModel, "followers.length")));
		add(new Link<String>("followingLink") {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				
			}
		}).add(new Label("followingAmount", new PropertyModel<String>(userModel, "following.length")));
		add(new Link<String>("notificationsLink") {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				
			}
		}).add(new Label("notifications", new PropertyModel<String>(userModel, "notifications.length")));
		add(new Link<String>("suggestedUsersLink") {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				
			}
		}).add(new Image("suggestedUsers", SocialCthulhuApp.SUGGESTED_USERS));

		add(new Link<String>("favouritesLink") {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				
			}
		}).add(new Image("favourites", SocialCthulhuApp.FAVOURITES));
		add(new Link<String>("editProfileLink") {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				
			}
		}).add(new Label("edit", getString("edit")));
		add(new Link<String>("followLink") {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				
			}
		}).add(new Label("follow", getString("follow")));
		add(new Link<String>("unfollowLink") {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				
			}
		}).add(new Label("unfollow", getString("unfollow")));
		add(new FeedbackPanel("errorPanel"));
		Form<ProfilePage> form = new Form<ProfilePage>(
				"commentForm",
				new CompoundPropertyModel<ProfilePage>(this)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {

			}
		};
		form.add(new TextArea<String>("commentTextArea").setRequired(true));
		
		add(new RefreshingView<CommentWrapper>("wrapperComment") {
			@Override
			protected Iterator<IModel<CommentWrapper>> getItemModels() {
				IModel<User> userModel = new EntityModel<User>(User.class, userId);
				User commenter = userModel.getObject();
				
				List<IModel<CommentWrapper>> result = new ArrayList<IModel<CommentWrapper>>();
				List<Comment> commentList = commenter.getComments();
				List<CommentWrapper> transformedComments = transformComments(commentList, commenter);
				for (CommentWrapper c : transformedComments) {
					result.add(new CompoundPropertyModel<CommentWrapper>(new EntityModel<CommentWrapper>(CommentWrapper.class, c)));
				}
				return result.iterator();
			}

			@Override
			protected void populateItem(Item<CommentWrapper> item) {
				item.add(new Label("transformedComment"));
				Link<String> removeFavouriteLink = new Link<String>("removeFavouriteLink") {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						
					}
					
				};
				removeFavouriteLink.add(new Label("removeFavourite", getString("remove_favourite")));
				item.add(removeFavouriteLink);
				Link<String> addFavouriteLink = new Link<String>("addFavouriteLink") {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						
					}
					
				};
				addFavouriteLink.add(new Label("addFavourite", getString("add_favourite")));
				item.add(addFavouriteLink);
				Link<String> recuthulhuLink = new Link<String>("recuthulhuLink") {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						
					}
					
				};
				recuthulhuLink.add(new Label("recthulhu", getString("recthulhu")));
				item.add(recuthulhuLink);
				Link<String> commentUsernameLink = new Link<String>("commentUsernameLink") {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						
					}
					
				};
				commentUsernameLink.add(new Label("comment.author.username"));
				item.add(commentUsernameLink);
				Link<String> authorUsernameLink = new Link<String>("authorUsernameLink") {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						
					}
					
				};
				authorUsernameLink.add(new Label("comment.originalAuthor.username"));
				item.add(authorUsernameLink);
				
				item.add(new Label("comment.date"));
				
				Link<String> deleteCommentLink = new Link<String>("deleteCommentLink") {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						
					}
					
				};
				deleteCommentLink.add(new Label("deleteComment", getString("delete_comment")));
				item.add(deleteCommentLink);
			}
		});
		
	}
	
	private List<CommentWrapper> transformComments(List<Comment> commentList, User u) {
		List<CommentWrapper> result = new ArrayList<CommentWrapper>();
		for(Comment c : commentList) {
			result.add(new CommentWrapper(c, getProcessedComment(c.getComment()), u));
		}
		return result;
	}
}