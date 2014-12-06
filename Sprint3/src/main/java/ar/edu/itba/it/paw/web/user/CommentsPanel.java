package ar.edu.itba.it.paw.web.user;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.ocpsoft.prettytime.PrettyTime;

import ar.edu.itba.it.paw.domain.CommentRepo;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.common.CommentWrapper;
import ar.edu.itba.it.paw.web.common.ImageResourceReference;

@SuppressWarnings("serial")
public class CommentsPanel extends Panel {

	@SpringBean
	private UserRepo users;
	@SpringBean
	private CommentRepo comments;

	public CommentsPanel(String id, IModel<List<CommentWrapper>> listOfComments) {
		super(id);

		final Component successfully_recthulhued_label = new Label(
				"successfully_recthulhued",
				getString("successfully_recthulhued")).setVisible(false);
		add(successfully_recthulhued_label);

		add(new PropertyListView<CommentWrapper>("wrapperComment",
				listOfComments) {

			@Override
			protected void populateItem(ListItem<CommentWrapper> item) {
				boolean userIsLogged = SocialCthulhuSession.get().getUsername() != null;

				boolean canShowRecthulhuedFrom = !item
						.getModelObject()
						.getComment()
						.getAuthor()
						.equals(item.getModelObject().getComment()
								.getOriginalAuthor());

				boolean userCanRecthulhu = userIsLogged
						&& !SocialCthulhuSession
								.get()
								.getUsername()
								.equals(item.getModelObject().getComment()
										.getAuthor().getUsername());

				boolean alreadyFavourited = userIsLogged
						&& users.getUser(
								SocialCthulhuSession.get().getUsername())
								.getFavourites()
								.contains(item.getModelObject().getComment());
				item.add(new Image("thumbnail", new ImageResourceReference(item
						.getModelObject().getComment().getAuthor()
						.getThumbnailPicture())));
				item.add(new MultiLineLabel("transformedComment", item
						.getModelObject().getTransformedComment())
						.setEscapeModelStrings(false));
				Link<CommentWrapper> favouriteLink = new Link<CommentWrapper>(
						"favouriteLink", item.getModel()) {

					@Override
					public void onClick() {
						User logged_user = SocialCthulhuSession.get().getUser();
						if (logged_user.getFavourites().contains(
								getModelObject().getComment())) {
							logged_user.removeFavourite(getModelObject()
									.getComment());
						} else {
							logged_user.addFavourite(getModelObject()
									.getComment());
						}
					}

				};
				favouriteLink.add(new Label("removeFavourite",
						getString("remove_favourite")).setVisible(userIsLogged
						&& alreadyFavourited));
				favouriteLink.add(new Label("addFavourite",
						getString("add_favourite")).setVisible(userIsLogged
						&& !alreadyFavourited));
				item.add(favouriteLink);

				Link<CommentWrapper> recthulhuLink = new Link<CommentWrapper>(
						"recthulhuLink", item.getModel()) {

					@Override
					public void onClick() {
						comments.recthulhu(getModelObject().getComment(), users
								.getUser(SocialCthulhuSession.get()
										.getUsername()));
						successfully_recthulhued_label.setVisible(true);
					}
				};
				recthulhuLink
						.add(new Label("recthulhu", getString("recthulhu")));
				recthulhuLink.setVisible(userCanRecthulhu);
				item.add(recthulhuLink);
				Link<CommentWrapper> commentUsernameLink = new Link<CommentWrapper>(
						"commentUsernameLink", item.getModel()) {

					@Override
					public void onClick() {
						setResponsePage(new ProfilePage(
								new PageParameters().set("username",
										getModelObject().getComment()
												.getAuthor().getUsername())));
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
						setResponsePage(new ProfilePage(
								new PageParameters().set("username",
										getModelObject().getComment()
												.getOriginalAuthor()
												.getUsername())));
					}
				};
				authorUsernameLink.setVisible(canShowRecthulhuedFrom);
				recthuledFrom.setVisible(canShowRecthulhuedFrom);
				authorUsernameLink.add(new Label("comment_author", item
						.getModelObject().getComment().getOriginalAuthor()
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
						String logged_in_username = SocialCthulhuSession.get()
								.getUsername();
						if (logged_in_username != null
								&& getModelObject().getComment().getAuthor()
										.getUsername()
										.equals(logged_in_username))
							comments.delete(getModelObject().getComment());
					}
				};
				deleteCommentLink.add(new Label("deleteComment",
						getString("delete_comment")).setVisible(userIsLogged
						&& users.getUser(
								item.getModelObject().getComment().getAuthor()
										.getUsername())
								.getUsername()
								.equals(SocialCthulhuSession.get()
										.getUsername())));
				item.add(deleteCommentLink);
			}
		});
	}
}
