package ar.edu.itba.it.paw.domain;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateNotificationRepo extends AbstractHibernateRepo implements
		NotificationRepo {

	@Autowired
	public HibernateNotificationRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
