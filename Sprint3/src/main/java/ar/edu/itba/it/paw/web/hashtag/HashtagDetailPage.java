package ar.edu.itba.it.paw.web.hashtag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Hashtag;
import ar.edu.itba.it.paw.domain.HashtagRepo;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.common.CommentWrapper;
import ar.edu.itba.it.paw.web.user.CommentWrapperModel;
import ar.edu.itba.it.paw.web.user.CommentsPanel;

public class HashtagDetailPage extends BasePage {

	private static final long serialVersionUID = 1L;
	@SpringBean
	private UserRepo users;
	@SpringBean
	private HashtagRepo hashtags;

	@SuppressWarnings("serial")
	public HashtagDetailPage(final PageParameters parameters) {
		final String hashtagName = parameters.get("hashtag").toString();
		IModel<List<CommentWrapper>> commentWrapperModel = new CommentWrapperModel() {
			
			@Override
			protected List<Comment> transformableLoad() {
				List<Comment> comments = new ArrayList<Comment>();
				Set<Comment> commentsHashtag = hashtags.getHashtag(hashtagName).getComments();
				for (Comment c : commentsHashtag) {
					comments.add(c);
				}
				return comments;
			}
		};
		if (hashtagName == null) {
			setResponsePage(getApplication().getHomePage());
			return;
		}
		IModel<Hashtag> hashtagModel = new LoadableDetachableModel<Hashtag>() {
			@Override
			protected Hashtag load() {
				return hashtags.getHashtag(hashtagName);
			}
		};
		add(new Label("cthulhuName", new PropertyModel<Hashtag>(hashtagModel, "hashtag")));
		add(new Label("cthulhuAuthor", new PropertyModel<Hashtag>(hashtagModel, "author.name")));
//		PrettyTime p = new PrettyTime();
//		add(new Label("cthulhuCreationDate", p.format(hashtag.getDate())));
		add(new Label("cthulhuCreationDate", new PropertyModel<Hashtag>(hashtagModel, "date")));
		add(new CommentsPanel("comments-panel", commentWrapperModel));
	}
}
