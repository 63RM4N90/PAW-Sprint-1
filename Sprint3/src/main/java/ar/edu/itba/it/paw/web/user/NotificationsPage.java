package ar.edu.itba.it.paw.web.user;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.ocpsoft.prettytime.PrettyTime;

import ar.edu.itba.it.paw.domain.Notification;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.SecuredPage;

public class NotificationsPage extends SecuredPage {

	private static final long serialVersionUID = 1L;
	@SpringBean
	private UserRepo users;

	public NotificationsPage() {

		final IModel<List<Notification>> notifications = new LoadableDetachableModel<List<Notification>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Notification> load() {
				return SocialCthulhuSession.get().getUser().getNotifications();
			}
		};

		add(new PropertyListView<Notification>("notification", notifications) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Notification> item) {
				Link<Notification> notificatorLink = new Link<Notification>(
						"notificator", item.getModel()) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						setResponsePage(new ProfilePage(
								new PageParameters().set("username",
										getModelObject().getNotificator()
												.getUsername())));
					}
				};

				notificatorLink
						.add(new Label("notificator_name", item.getModel()
								.getObject().getNotificator().getUsername()));
				item.add(new Label("notification_text",
						new PropertyModel<String>(item.getModel(),
								"notification")));
				PrettyTime p = new PrettyTime();
				item.add(new Label("notification_date", p.format(item
						.getModelObject().getDate())));
				item.add(new Label("notification_by",
						getString("notification_by")));
				item.add(notificatorLink);
			}
		});
		add(new Label("notifications_title", getString("notifications_title")));
		Label noNotifications = new Label("no_notifications",
				getString("no_notifications"));
		if (notifications != null && !notifications.getObject().isEmpty()) {
			noNotifications.setVisible(false);
		}
		add(noNotifications);
	}
}
