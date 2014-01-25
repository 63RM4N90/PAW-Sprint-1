package ar.edu.itba.it.paw.web;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class SocialCthulhuSession extends WebSession {

	private String username;

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
		User user = users.get(username);
		if (user != null && user.checkPassword(password)) {
			this.username = username;
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
}
