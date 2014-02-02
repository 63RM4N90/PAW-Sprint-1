package ar.edu.itba.it.paw.web.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;

import ar.edu.itba.it.paw.web.SocialCthulhuApp;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;

public class BasePage extends WebPage {
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("serial")
	public BasePage() {
		add(new Image("separator1", SocialCthulhuApp.SEPARTOR));
		add(new Link<Void>("home") {

			@Override
			public void onClick() {
				setResponsePage(getApplication().getHomePage());
			}
			
		}.add(new Image("home_image", SocialCthulhuApp.HOME)));
		Form<BasePage> form = new Form<BasePage>("searchForm") {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {

			}
		};
		form.add(new TextField<String>("searchField"));
		form.add(new Button("searchButton"));
		add(form);
		add(new Image("socialCthulhuTitle", SocialCthulhuApp.TITLE));

		/*session related info*/
		
		SocialCthulhuSession session = (SocialCthulhuSession) getSession();
		Link<Void> logout = new Link<Void>("logout") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onClick() {
				((SocialCthulhuSession) getSession()).signOut();
				setResponsePage(getApplication().getHomePage());
			}
		};
		add(logout);
		Image separator2 = new Image("separator2", SocialCthulhuApp.SEPARTOR); 
		add(separator2);
		Label user = new Label("user", session.getUsername());
		add(user);
		if(!session.isSignedIn()) {
			logout.setVisible(false);
			separator2.setVisible(false);
			user.setVisible(false);
		}
	}
}
