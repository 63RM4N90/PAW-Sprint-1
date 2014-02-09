package ar.edu.itba.it.paw.web;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.common.CookieService;

public class SocialCthulhuSession extends WebSession {

	private static final long serialVersionUID = 1L;
	private String username;
	private int userId;
	private CookieService cookieService = new CookieService();

	public static SocialCthulhuSession get() {
		return (SocialCthulhuSession) Session.get();
	}

	public SocialCthulhuSession(Request request) {
		super(request);
		String username = cookieService.getUsername(request, "usernameCookie");
		int userId = cookieService.getUserId(request, "userIdCookie");
		if (username != null && userId != -1) {
			this.username = username;
			this.userId = userId;
		}
	}

	public String getUsername() {
		return username;
	}

	public int getUserId() {
		return userId;
	}

	public CookieService getCookieService() {
		return cookieService;
	}

	public boolean signIn(String username, String password, UserRepo users) {
		User user = users.getUser(username);
		if (user != null && user.checkPassword(password)) {
			this.username = username;
			this.userId = user.getId();
			this.cookieService = new CookieService();
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
