package ar.edu.itba.it.paw.web.base;

import org.apache.wicket.markup.html.link.Link;

import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.common.LoginPage;

public abstract class SecuredPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public SecuredPage() {
		SocialCthulhuSession session = getDemoWicketSession();
		if (!session.isSignedIn()) {
			redirectToInterceptPage(new LoginPage());
			
		}

		add(new Link<Void>("logout") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				getDemoWicketSession().signOut();
				setResponsePage(getApplication().getHomePage());
			}
		});
	}

	protected SocialCthulhuSession getDemoWicketSession() {
		return (SocialCthulhuSession) getSession();
	}
}