package ar.edu.itba.it.paw.domain;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateUserRepo extends AbstractHibernateRepo implements
		UserRepo {

	public HibernateUserRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	@SuppressWarnings("unchecked")
	public User authenticate(String username, String password) {
		Session session = getSession();
		Query query = session
				.createQuery(" from User where username = ? and password = ?");
		query.setParameter(0, username);
		query.setParameter(1, password);
		List<User> result = (List<User>) query.list();
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public User getUser(String username) {
		Session session = getSession();
		Query query = session.createQuery(" from User where username = ?");
		query.setParameter(0, username);
		List<User> result = (List<User>) query.list();
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getUsersWithName(String name) {
		Session session = getSession();
		Query query = session.createQuery(" from User where username like '%"
				+ name + "%' or (name like '%" + name
				+ "%') or (surname like '%" + name + "%')");
		return (List<User>) query.list();
	}

}
