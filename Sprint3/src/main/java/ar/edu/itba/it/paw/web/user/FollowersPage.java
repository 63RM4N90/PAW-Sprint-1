package ar.edu.itba.it.paw.web.user;

import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.base.BasePage;

public class FollowersPage extends BasePage {

	private static final long serialVersionUID = 1L;
	
	public FollowersPage(int userId) {
		add(new FollowersListPanel("followersListPanel", userId));
	}
	

}
