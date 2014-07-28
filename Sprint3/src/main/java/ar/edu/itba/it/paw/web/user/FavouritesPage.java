package ar.edu.itba.it.paw.web.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.ocpsoft.prettytime.PrettyTime;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.CommentRepo;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.SecuredPage;

public class FavouritesPage extends SecuredPage {

	private static final long serialVersionUID = 1L;
	private transient User user;
	@SpringBean
	private UserRepo users;
	@SpringBean
	private CommentRepo comments;

	public FavouritesPage() {
		user = users.getUser(SocialCthulhuSession.get().getUsername());

		final IModel<List<Comment>> favourites = new LoadableDetachableModel<List<Comment>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Comment> load() {
				Set<Comment> favourites = users.getUser(
						SocialCthulhuSession.get().getUsername())
						.getFavourites();
				List<Comment> ans = new ArrayList<Comment>();
				for (Comment n : favourites) {
					ans.add(n);
				}
				return ans;
			}
		};

		add(new PropertyListView<Comment>("favourite_cthulhu", favourites) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Comment> item) {

				boolean equalUsers = item.getModelObject().getAuthor()
						.equals(item.getModelObject().getOriginalAuthor());

				boolean alreadyFavourited = users
						.getUser(SocialCthulhuSession.get().getUsername())
						.getFavourites()
						.contains(item.getModelObject().getComment());

				item.add(new MultiLineLabel("comment_text", item
						.getModelObject().getComment())
						.setEscapeModelStrings(false));
				Link<Comment> removeFavouriteLink = new Link<Comment>(
						"removeFavouriteLink", item.getModel()) {

					@Override
					public void onClick() {
						User user = users.getUser(SocialCthulhuSession.get()
								.getUsername());
						user.removeFavourite(getModelObject());
					}

				};
				removeFavouriteLink.add(new Label("removeFavourite",
						getString("remove_favourite")));
				removeFavouriteLink.setVisible(alreadyFavourited);
				item.add(removeFavouriteLink);
				Link<Comment> addFavouriteLink = new Link<Comment>(
						"addFavouriteLink", item.getModel()) {

					@Override
					public void onClick() {
						Comment comment = getModelObject();
						users.getUser(SocialCthulhuSession.get().getUsername())
								.addFavourite(comment);
					}
				};
				addFavouriteLink.add(new Label("addFavourite",
						getString("add_favourite")));
				addFavouriteLink.setVisible(!alreadyFavourited);
				item.add(addFavouriteLink);
				Link<Comment> recthulhuLink = new Link<Comment>(
						"recthulhuLink", item.getModel()) {

					@Override
					public void onClick() {
						setResponsePage(new ProfilePage(getModelObject()
								.getAuthor().getId()));
					}
				};
				recthulhuLink
						.add(new Label("recthulhu", getString("recthulhu")));
				recthulhuLink.setVisible(!equalUsers);
				item.add(recthulhuLink);
				Link<Comment> commentUsernameLink = new Link<Comment>(
						"commentUsernameLink", item.getModel()) {

					@Override
					public void onClick() {
						setResponsePage(new ProfilePage(getModelObject()
								.getAuthor().getId()));
					}
				};
				commentUsernameLink.add(new Label("comment_username", item
						.getModelObject().getAuthor()
						.getUsername()));
				item.add(commentUsernameLink);
				Label recthuledFrom = new Label("recthuled_from",
						getString("recthuled_from"));
				Link<Comment> authorUsernameLink = new Link<Comment>(
						"authorUsernameLink", item.getModel()) {

					@Override
					public void onClick() {
						setResponsePage(new ProfilePage(getModelObject()
								.getOriginalAuthor().getId()));
					}
				};
				authorUsernameLink.setVisible(!equalUsers);
				recthuledFrom.setVisible(!equalUsers);
				authorUsernameLink.add(new Label("comment_author", item
						.getModelObject().getAuthor()
						.getUsername()));
				item.add(authorUsernameLink);
				item.add(recthuledFrom);
				PrettyTime p = new PrettyTime();
				item.add(new Label("commentDate", p.format(item
						.getModelObject().getDate())));

				Link<Comment> deleteCommentLink = new Link<Comment>(
						"deleteCommentLink", item.getModel()) {

					@Override
					public void onClick() {
						comments.delete(getModelObject());
					}
				};
				deleteCommentLink.add(new Label("deleteComment",
						getString("delete_comment")));
				item.add(deleteCommentLink);
			}
		});
		Label noFavourites = new Label("no_favourites",
				getString("no_favourites"));
		if (favourites != null && !favourites.getObject().isEmpty()) {
			noFavourites.setVisible(false);
		}
		add(noFavourites);
		add(new Label("username", user.getUsername()));
		add(new Label("username_suffix", getString("username_suffix")));
	}
}
