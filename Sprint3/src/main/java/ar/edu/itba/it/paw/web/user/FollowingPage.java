package ar.edu.itba.it.paw.web.user;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.base.BasePage;

public class FollowingPage extends BasePage {

	private static final long serialVersionUID = 1L;
	@SpringBean
	private UserRepo users;

	@SuppressWarnings("serial")
	public FollowingPage(final PageParameters params) {
		User user = users.getUser(params.get("username").toString());
		if (user == null)
			setResponsePage(ProfilePage.class);
		IModel<List<User>> following = new LoadableDetachableModel<List<User>>() {
			@Override
			protected List<User> load() {
				return users.getUser(params.get("username").toString())
						.getFollowing();
			}
		};
		add(new UsersPanel("followingListPanel", following));
	}
}
