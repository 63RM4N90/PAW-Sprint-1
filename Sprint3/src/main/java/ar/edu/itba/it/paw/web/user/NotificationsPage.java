package ar.edu.itba.it.paw.web.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.Notification;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.SecuredPage;

public class NotificationsPage extends SecuredPage {

	private static final long serialVersionUID = 1L;
	@SpringBean
	private UserRepo users;

	public NotificationsPage() {
		add(new RefreshingView<Notification>("notification") {
			private static final long serialVersionUID = 1L;

			@Override
			protected Iterator<IModel<Notification>> getItemModels() {
				List<IModel<Notification>> ans = new ArrayList<IModel<Notification>>();
				List<Notification> notifications = users.getUser(
						new SocialCthulhuSession(getRequest()).getId())
						.getNotifications();
				for (Notification n : notifications) {
					ans.add(new EntityModel<Notification>(Notification.class, n));
				}
				return ans.iterator();
			}

			@Override
			protected void populateItem(Item<Notification> item) {
				Link<String> notificatorLink = new Link<String>("notificator") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
					}
				};

				notificatorLink.add(new Label("notificator_name",
						new PropertyModel<String>(item.getModel(),
								"originalAuthor.username")));
				item.add(new Label("notification_text",
						new PropertyModel<String>(item.getModel(),
								"notification")));
				item.add(new Label("notification_date",
						new PropertyModel<String>(item.getModel(), "date")));
				item.add(notificatorLink);
			}
		});
		add(new Label("notifications_title", getString("notifications_title")));
		add(new Label("notification_by", getString("notification_by")));
	}
}
