package ar.edu.itba.it.paw.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Notification extends PersistentEntity {

	private String notification;
	private Date date;
	@ManyToOne
	private User notificator;
	private boolean checked = false;

	public Notification() {
	}

	public Notification(User notificator, String notification) {
		this.notificator = notificator;
		this.notification = notification;
	}

	public User getNotificator() {
		return notificator;
	}

	public String getNotification() {
		return notification;
	}

	public Date getDate() {
		return date;
	}

	public void check() {
		this.checked = true;
	}

	public boolean isChecked() {
		return checked;
	}
}
