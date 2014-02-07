package ar.edu.itba.it.paw.web.user;

import java.util.Set;

import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;

@SuppressWarnings("serial")
public class FollowersListPanel extends FollowListPanel{

	@SpringBean
	private UserRepo users;
	
	public FollowersListPanel(String id, int userId) {
		super(id, userId);
	}
	
	protected Set<User> getFollows(int userId) {
		return users.getUser(userId).getFollowers();
	}

}
