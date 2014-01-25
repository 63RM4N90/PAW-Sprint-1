package ar.edu.itba.it.paw.web.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;

import ar.edu.itba.it.paw.web.SocialCthulhuApp;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;

public class BasePage extends WebPage {
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("serial")
	public BasePage() {
		add(new Link<Void>("logout") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				((SocialCthulhuSession) getSession()).signOut();
				setResponsePage(getApplication().getHomePage());
			}
		});
		add(new Label("user", ((SocialCthulhuSession)getSession()).getUsername()));
		add(new Image("separator", SocialCthulhuApp.SEPARTOR));
		add(new Link<Void>("home") {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				
			}
			
		}.add(new Image("home", SocialCthulhuApp.HOME)));
		add(new Link<Void>("top10hashtags"){

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				
			}
			
		}.add(new Image("home", SocialCthulhuApp.TOP_10_HASHTAGS)));
		Form<BasePage> form = new Form<BasePage>("searchForm", new CompoundPropertyModel<BasePage>(this)) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {

			}
		};

		form.add(new TextField<String>("searchField"));
		form.add(new Button("searchButton"));
		add(form);
		add(new Image("socialCthulhuTitle", SocialCthulhuApp.TITLE));
	}
}
