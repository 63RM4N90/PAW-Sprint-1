package ar.edu.itba.it.paw.web;

import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.web.common.HibernateRequestCycleListener;
import ar.edu.itba.it.paw.web.hashtag.HashtagDetailPage;

@Component
public class SocialCthulhuApp extends WebApplication {

	private final SessionFactory sessionFactory;
	public static final ResourceReference SEPARTOR = new PackageResourceReference(SocialCthulhuApp.class, "resources/topbar_separator.png");
	public static final ResourceReference HOME = new PackageResourceReference(SocialCthulhuApp.class, "resources/home.png");
	public static final ResourceReference TOP_10_HASHTAGS = new PackageResourceReference(SocialCthulhuApp.class, "resources/t10h.png");
	public static final ResourceReference TITLE = new PackageResourceReference(SocialCthulhuApp.class, "resources/title.png");

	
	@Autowired
	public SocialCthulhuApp(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Class<? extends Page> getHomePage() {
		return HashtagDetailPage.class;
	}
	
	@Override
	protected void init() {
		super.init();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		getRequestCycleListeners().add(new HibernateRequestCycleListener(sessionFactory));
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new SocialCthulhuSession(request);
	}

	@Override
	protected IConverterLocator newConverterLocator() {
		ConverterLocator converterLocator = new ConverterLocator();
		return converterLocator;
	}
}
