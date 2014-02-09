package ar.edu.itba.it.paw.web.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.ocpsoft.prettytime.PrettyTime;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.SecuredPage;

public class FavouritesPage extends SecuredPage {

	private static final long serialVersionUID = 1L;
	private transient User user;
	@SpringBean
	private UserRepo users;

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
				Link<Comment> authorLink = new Link<Comment>("author",
						item.getModel()) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						User author = getModelObject().getAuthor();
						setResponsePage(new ProfilePage(author.getId()));
					}
				};

				Link<Comment> unfavouriteLink = new Link<Comment>(
						"unfavourite", item.getModel()) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						Comment comment = getModelObject();
						user.removeFavourite(comment);
					}
				};

				Link<Comment> deleteCommentLink = new Link<Comment>(
						"delete_comment", item.getModel()) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						user.getComments().remove(getModelObject());
					}
				};

				if (!item.getModel().getObject().getOriginalAuthor()
						.equals(user)) {
					deleteCommentLink.setVisible(false);
				}

				if (item.getModel().getObject().isRecuthulu()) {
					add(new Label("created_or_recthulued_from",
							"Recthulhued from:"));
				} else {
					add(new Label("created_or_recthulued_from",
							getString("created_by")));
				}

				item.add(new Label("comment_text", new PropertyModel<String>(
						item.getModel(), "comment")));
				PrettyTime p = new PrettyTime();
				item.add(new Label("creation_date", p.format(item
						.getModelObject().getDate())));
				unfavouriteLink.add(new Label("unfavourite_text",
						getString("unfavourite")));
				item.add(deleteCommentLink);
				item.add(authorLink);
				item.add(unfavouriteLink);
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
