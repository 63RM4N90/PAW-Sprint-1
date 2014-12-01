package ar.edu.itba.it.paw.domain;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernatePublicityRepo extends AbstractHibernateRepo implements
		PublicityRepo {

	@Autowired
	public HibernatePublicityRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Publicity fetch_any_publicity() {
		List<Publicity> result = find("from Publicity order by random()");
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public RenderedPublicity fetch_any_rendered_publicity() {
		Publicity publicity = fetch_any_publicity();
		if (publicity != null)
			return new RenderedPublicity(publicity);
		else
			return null;
	}
}
