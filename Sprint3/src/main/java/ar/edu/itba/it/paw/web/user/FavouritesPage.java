package ar.edu.itba.it.paw.web.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.BasePage;

public class FavouritesPage extends BasePage {

	private static final long serialVersionUID = 1L;
	private transient User user;
	@SpringBean
	private UserRepo users;

	public FavouritesPage() {
		user = users.getUser(new SocialCthulhuSession(getRequest()).getId());
		add(new RefreshingView<Comment>("favourite_cthulhu") {
			private static final long serialVersionUID = 1L;

			@Override
			protected Iterator<IModel<Comment>> getItemModels() {
				List<IModel<Comment>> ans = new ArrayList<IModel<Comment>>();
				Set<Comment> comments = user.getFavourites();
				for (Comment c : comments) {
					ans.add(new EntityModel<Comment>(Comment.class, c));
				}
				return ans.iterator();
			}

			@Override
			protected void populateItem(Item<Comment> item) {
				Link<String> authorLink = new Link<String>("author") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
					}
				};

				Link<String> unfavouriteLink = new Link<String>("unfavourite") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
					}
				};

				Link<String> deleteCommentLink = new Link<String>(
						"delete_comment") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
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
				item.add(new Label("notification_date",
						new PropertyModel<String>(item.getModel(), "date")));
				unfavouriteLink
						.add(new Label("unfavourite_text", getString("unfavourite")));
				item.add(deleteCommentLink);
				item.add(authorLink);
				item.add(unfavouriteLink);
			}
		});
		add(new Label("username", user.getUsername()));
		add(new Label("username_suffix", getString("username_suffix")));
	}
}
