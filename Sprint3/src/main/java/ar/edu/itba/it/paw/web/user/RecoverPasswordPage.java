package ar.edu.itba.it.paw.web.user;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import ar.edu.itba.it.paw.web.base.BasePage;

public class RecoverPasswordPage extends BasePage {
	private static final long serialVersionUID = 1L;
	
	public RecoverPasswordPage(String secretQuestion, String username) {
		add(new Label("success", " "));
		add(new FeedbackPanel("errorPanel"));
		add(new RecoverPasswordPanel("recoverPasswordPanel", secretQuestion, username));
	}
}
