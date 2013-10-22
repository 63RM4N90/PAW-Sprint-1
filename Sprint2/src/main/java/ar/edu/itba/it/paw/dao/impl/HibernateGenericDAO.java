package ar.edu.itba.it.paw.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import ar.edu.itba.it.paw.dao.GenericDAO;

public class HibernateGenericDAO<T> implements GenericDAO<T> {

	private Class<T> domainClass = getDomainClass();

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getDomainClass() {
		if (domainClass == null) {
			ParameterizedType thisType = (ParameterizedType) getClass()
					.getGenericSuperclass();
			domainClass = (Class<T>) thisType.getActualTypeArguments()[0];
		}
		return domainClass;
	}

	@Override
	public void delete(T obj) {
		Session session = getSession();
		session.delete(obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T find(int id) {
		Session session = getSession();
		T result = (T) session.get(domainClass, id);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		Session session = getSession();
		List<T> result = (List<T>) session.createQuery(
				"from " + domainClass.getSimpleName()).list();
		return result;
	}

	@Override
	public T store(T obj) {
		Session session = getSession();
		session.save(obj);
		return obj;
	}
}
