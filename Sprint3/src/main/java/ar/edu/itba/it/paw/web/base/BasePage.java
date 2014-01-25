package ar.edu.itba.it.paw.web.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.it.paw.web.SocialCthulhuSession;

public class BasePage extends WebPage {
	
	private static final long serialVersionUID = 1L;

	public BasePage() {
		add(new Label("username", new PropertyModel<String>(SocialCthulhuSession.get(), "username")));
	}
}
