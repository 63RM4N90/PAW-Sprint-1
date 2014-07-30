package ar.edu.itba.it.paw.web.hashtag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.ocpsoft.prettytime.PrettyTime;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Hashtag;
import ar.edu.itba.it.paw.domain.HashtagRepo;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.user.CommentsPanel;

public class HashtagDetailPage extends BasePage {

	private static final long serialVersionUID = 1L;
	private transient Hashtag cthulhu;
	@SpringBean
	private transient UserRepo users;
	@SpringBean
	private transient HashtagRepo hashtags;

	public HashtagDetailPage(final PageParameters parameters) {
		Hashtag hashtag = hashtags.getHashtag(parameters.get("hashtag")
				.toString());
		if (hashtag == null) {
			setResponsePage(getApplication().getHomePage());
			return;
		}
		String username = SocialCthulhuSession.get().getUsername();
		int userId = -1;
		if (username != null) {
			userId = users.getUser(username).getId();
		}
		add(new Label("cthulhuName", hashtag.getHashtag()));
		add(new Label("cthulhuAuthor", hashtag.getAuthor().getName()));
		PrettyTime p = new PrettyTime();
		add(new Label("cthulhuCreationDate", p.format(hashtag.getDate())));
		cthulhu = hashtag;

		List<Comment> comments = new ArrayList<Comment>();
		Set<Comment> commentsHashtag = cthulhu.getComments();
		for (Comment c : commentsHashtag) {
			comments.add(c);
		}

		add(new CommentsPanel("comments-panel", userId, comments));
	}
}
