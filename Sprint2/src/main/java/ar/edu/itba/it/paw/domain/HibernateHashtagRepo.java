package ar.edu.itba.it.paw.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateHashtagRepo extends AbstractHibernateRepo implements
		HashtagRepo {

	public HibernateHashtagRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Hashtag getHashtag(String hashtag) {
		return (Hashtag) find(" from Hashtag where hashtag = ?", hashtag)
				.get(0);
	}

	@Override
	public LinkedList<RankedHashtag> topHashtags(int days) {
		LinkedList<RankedHashtag> ranking = new LinkedList<RankedHashtag>();

		Date to = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.DAY_OF_YEAR, -days);
		Date from = calendar.getTime();

		TreeMap<Integer, ArrayList<Hashtag>> top = rankedHashtags(from, to);

		if (top != null) {
			ArrayList<Hashtag> aux;
			int count = 0;

			try {
				while (count < 10) {
					int key = top.lastKey();
					aux = top.get(key);
					int index = 0;
					while (count < 10 && index < aux.size()) {
						ranking.add(new RankedHashtag(aux.get(index), key));
						count++;
						index++;
					}
					top.remove(key);
				}
			} catch (NoSuchElementException e) {
				return ranking;
			}
		}
		return ranking;
	}

	private TreeMap<Integer, ArrayList<Hashtag>> rankedHashtags(Date from,
			Date to) {
		TreeMap<Integer, ArrayList<Hashtag>> rank = new TreeMap<Integer, ArrayList<Hashtag>>();

		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createQuery("select c.hashtags.hashtag, count(distinct com_id) as rank from Comment c join c.hastags group by c.hashtags.hashtag order by rank");

		ScrollableResults results = query.scroll();
		tx.commit();

		ArrayList<Hashtag> aux;

		while (results.next()) {
			Object[] row = results.get();
			Hashtag hashtag = (Hashtag) row[0];
			Integer ranking = results.getInteger(1);

			if (rank.containsKey(ranking)) {
				rank.get(ranking).add(hashtag);

			} else {
				aux = new ArrayList<Hashtag>();
				aux.add(hashtag);
				rank.put(ranking, aux);
			}
		}
		return rank;
	}
}
