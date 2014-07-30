package ar.edu.itba.it.paw.web.common;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.user.UsersPanel;

public class SearchPage extends BasePage {

	private static final long serialVersionUID = 1L;
	private String search;
	@SpringBean
	private UserRepo users;

	public SearchPage(String searchText) {
		super();
		search = searchText != null ? searchText : "";
		add(new Label("search", getString("searchTitle") + "\"" + search + "\""));

		List<User> userList;
		userList = search == null ? users.getAll() : users
				.getUsersWithName(search);
		add(new UsersPanel("users-panel", userList));
	}
}
