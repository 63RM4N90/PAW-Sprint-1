package ar.edu.itba.it.paw.web.user;

import java.util.Date;
import java.util.List;

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

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.CommentRepo;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.common.CommentWrapper;
import ar.edu.itba.it.paw.web.common.ImageResourceReference;

public class CommentsPanel extends Panel {

	private static final long serialVersionUID = 8914010631219544701L;
	private int userId;
	@SpringBean
	private UserRepo users;
	@SpringBean
	private CommentRepo comments;

	@SuppressWarnings("serial")
	public CommentsPanel(String id, int user,
			IModel<List<CommentWrapper>> listOfComments) {
		super(id);
		this.userId = user;
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
				item.add(new Image("thumbnail",
						new ImageResourceReference(item.getModelObject()
								.getComment().getAuthor().getThumbnailPicture())));
				item.add(new MultiLineLabel("transformedComment", item
						.getModelObject().getTransformedComment())
						.setEscapeModelStrings(false));
				Link<CommentWrapper> removeFavouriteLink = new Link<CommentWrapper>(
						"removeFavouriteLink", item.getModel()) {

					private static final long serialVersionUID = -6657885277633930092L;

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

					private static final long serialVersionUID = 3039554452059752457L;

					@Override
					public void onClick() {
						CommentWrapper comment = getModelObject();
						users.getUser(SocialCthulhuSession.get().getUsername())
								.addFavourite(comment.getComment());
					}
				};
				addFavouriteLink.add(new Label("addFavourite",
						getString("add_favourite")));
				addFavouriteLink.setVisible(userIsLogged && !alreadyFavourited);
				item.add(addFavouriteLink);
				Link<CommentWrapper> recthulhuLink = new Link<CommentWrapper>(
						"recthulhuLink", item.getModel()) {

					private static final long serialVersionUID = -7382250969064495012L;

					@Override
					public void onClick() {
						String comment = getModelObject().getComment()
								.getComment();
						User originalAuthor = getModelObject().getComment()
								.getOriginalAuthor();
						User author = users.getUser(SocialCthulhuSession.get()
								.getUsername());
						Comment rechtulhu = new Comment(author, new Date(),
								comment, comments.getHashtagList(comment,
										author), comments.getReferences(
										comment, author), originalAuthor);
						if (!author.getComments().contains(rechtulhu))
							comments.addComment(rechtulhu);
					}
				};
				recthulhuLink
						.add(new Label("recthulhu", getString("recthulhu")));
				recthulhuLink.setVisible(userCanRecthulhu);
				item.add(recthulhuLink);
				Link<CommentWrapper> commentUsernameLink = new Link<CommentWrapper>(
						"commentUsernameLink", item.getModel()) {

					private static final long serialVersionUID = 5817420657291774864L;

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

					private static final long serialVersionUID = -8279779646272929857L;

					@Override
					public void onClick() {
						setResponsePage(new ProfilePage(
								new PageParameters().set("username",
										getModelObject().getComment()
												.getOriginalAuthor().getUsername())));
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

					private static final long serialVersionUID = -6955584208310539151L;

					@Override
					public void onClick() {
						comments.delete(getModelObject().getComment());
					}
				};
				deleteCommentLink.add(new Label("deleteComment",
						getString("delete_comment")));
				deleteCommentLink.setVisible(userIsLogged
						&& users.getUser(userId)
								.getUsername()
								.equals(SocialCthulhuSession.get()
										.getUsername()));
				item.add(deleteCommentLink);
			}
		});
	}
}
