package ar.edu.itba.it.paw.web.hashtag;

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
import ar.edu.itba.it.paw.domain.Hashtag;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.BasePage;

public class HashtagDetailPage extends BasePage {

	private static final long serialVersionUID = 1L;
	private transient Hashtag cthulhu;
	private transient User user;
	@SpringBean
	private transient UserRepo users;

	public HashtagDetailPage(Hashtag hashtag) {
		user = users.getUser(SocialCthulhuSession.get().getUsername());
		add(new Label("cthulhuName", hashtag.getHashtag()));
		add(new Label("cthulhuAuthor", hashtag.getAuthor().getName()));
		PrettyTime p = new PrettyTime();
		add(new Label("cthulhuCreationDate", p.format(hashtag.getDate())));
		cthulhu = hashtag;

		final IModel<List<Comment>> comments = new LoadableDetachableModel<List<Comment>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Comment> load() {
				List<Comment> comments = new ArrayList<Comment>();
				Set<Comment> commentsHashtag = cthulhu.getComments();
				for (Comment c : commentsHashtag) {
					comments.add(c);
				}
				return comments;
			}
		};

		add(new PropertyListView<Comment>("cthulhu", comments) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Comment> item) {

				Link<Comment> favouriteLink = new Link<Comment>("favourite",
						item.getModel()) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						Comment comment = getModelObject();
						user.addFavourite(comment);
					}
				};

				Link<String> recthulhuLink = new Link<String>("recthulhu") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
					}
				};

				Link<String> recthulhuedFromLink = new Link<String>(
						"recthulhuedFrom") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
					}
				};

				Link<String> createdByLink = new Link<String>("createdBy") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
					}
				};

				recthulhuLink.add(new Label("recthulhuText", "Recthulu"));
				recthulhuedFromLink.add(new Label("recthulhuedFromText",
						new PropertyModel<String>(item.getModel(), "author")));
				createdByLink.add(new Label("createdByText",
						new PropertyModel<String>(item.getModel(),
								"originalAuthor")));
				item.add(favouriteLink);
				item.add(recthulhuLink);
				item.add(recthulhuedFromLink);
				item.add(createdByLink);
			}
		});
	}
}
