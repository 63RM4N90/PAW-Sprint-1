package ar.edu.itba.it.paw.web.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

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

		List<Comment> favouriteComments = new ArrayList<Comment>();
		for (Comment comment : user.getFavourites()) {
			favouriteComments.add(comment);
		}

		add(new CommentsPanel("comments-panel", user.getId(), favouriteComments));
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
