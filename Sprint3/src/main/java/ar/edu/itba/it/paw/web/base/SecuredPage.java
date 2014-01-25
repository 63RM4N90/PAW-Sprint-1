package ar.edu.itba.it.paw.web.base;

import org.apache.wicket.markup.html.link.Link;

import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.common.LoginPage;

public abstract class SecuredPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public SecuredPage() {
		SocialCthulhuSession session = getSocialCthulhuSession();
		if (!session.isSignedIn()) {
			redirectToInterceptPage(new LoginPage());
			
		}
	}

	protected SocialCthulhuSession getSocialCthulhuSession() {
		return (SocialCthulhuSession) getSession();
	}
}