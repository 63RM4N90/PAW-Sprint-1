package ar.edu.itba.it.paw.web.hashtag;

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

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.Hashtag;
import ar.edu.itba.it.paw.web.base.BasePage;

public class HashtagDetailPage extends BasePage {

	private static final long serialVersionUID = 1L;
	private transient Hashtag cthulhu;

	public HashtagDetailPage(Hashtag hashtag) {  
		add(new Label("cthulhuName", hashtag.getHashtag()));
		add(new Label("cthulhuAuthor", hashtag.getAuthor().getName()));
		add(new Label("cthulhuCreationDate", hashtag.getDate().toString()));
		cthulhu = hashtag;
		
		add(new RefreshingView<Comment>("cthulhu") {
			private static final long serialVersionUID = 1L;
			@Override
			protected Iterator<IModel<Comment>> getItemModels() {
				List<IModel<Comment>> comments = new ArrayList<IModel<Comment>>();
				Set<Comment> commentsHashtag = cthulhu.getComments();
				for (Comment c : commentsHashtag) {
					comments.add(new EntityModel<Comment>(Comment.class, c));
				}
				return comments.iterator();
			}

			@Override
			protected void populateItem(Item<Comment> item) {
				Link<String> favouriteLink = new Link<String>("favourite") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
					}
				};
				
				Link<String> recthulhuLink = new Link<String>("recthulhu") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
					}
				};
				
				Link<String> recthulhuedFromLink = new Link<String>("recthulhuedFrom") {
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
				recthulhuedFromLink.add(new Label("recthulhuedFromText", new PropertyModel<String>(item.getModel(), "author")));
				createdByLink.add(new Label("createdByText", new PropertyModel<String>(item.getModel(), "originalAuthor")));
				item.add(favouriteLink);
				item.add(recthulhuLink);
				item.add(recthulhuedFromLink);
				item.add(createdByLink);
			}
		});
	}
}
