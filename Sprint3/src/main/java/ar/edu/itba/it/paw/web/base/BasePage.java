package ar.edu.itba.it.paw.web.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.it.paw.web.SocialCthulhuApp;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.common.SearchPage;
import ar.edu.itba.it.paw.web.user.RecoverPasswordPage;

public class BasePage extends WebPage {
	
	private static final long serialVersionUID = 1L;
	private String searchText;
	
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
				SearchPage responsePage = new SearchPage(searchText);
				setResponsePage(responsePage);
			}
		};
		form.add(new TextField<String>("searchField", new PropertyModel<String>(this, "searchText")));
		form.add(new Button("searchButton"));
		add(form);
		add(new Image("socialCthulhuTitle", SocialCthulhuApp.TITLE));

		/*session related info*/
		
		SocialCthulhuSession session = (SocialCthulhuSession) getSession();
		Link logout = new Link<Void>("logout") {
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
