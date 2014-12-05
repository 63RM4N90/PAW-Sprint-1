package ar.edu.itba.it.paw.web.user;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.common.CommentWrapper;

@SuppressWarnings("serial")
public class FollowingMessagesPage extends BasePage {

	@SpringBean
	private UserRepo users;

	public FollowingMessagesPage() {

		IModel<List<CommentWrapper>> commentModel = new CommentWrapperModel() {
			@Override
			protected List<Comment> transformableLoad() {
				return SocialCthulhuSession.get().getUser()
						.getFollowingComments();
			}
		};
		add(new CommentsPanel("comments-panel", commentModel));
	}
}
