package ar.edu.itba.it.paw.web;

import org.apache.wicket.Session;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;

public class SocialCthulhuSession extends WebSession {

	private static final long serialVersionUID = 1L;
	private String username;
	private IModel<User> user;

	public static SocialCthulhuSession get() {
		return (SocialCthulhuSession) Session.get();
	}

	public SocialCthulhuSession(Request request) {
		super(request);
	}

	public String getUsername() {
		return username;
	}

	public boolean signIn(String username, String password, UserRepo users) {
		if (this.username != null)
			return true;
		User user = users.getUser(username);
		if (user != null && user.checkPassword(password)) {
			this.username = username;
			this.user = new EntityModel<User>(User.class, user);
			return true;
		}
		return false;
	}

	public boolean isSignedIn() {
		return username != null;
	}

	public void signOut() {
		invalidate();
		clear();
	}

	protected void setUsernameFromCookies(String username) {
		this.username = username;
	}

	@Override
	public void detach() {
		super.detach();
		if (user != null)
			user.detach();
	}

	public User getUser() {
		if (user != null)
			return user.getObject();
		return null;
	}
}
