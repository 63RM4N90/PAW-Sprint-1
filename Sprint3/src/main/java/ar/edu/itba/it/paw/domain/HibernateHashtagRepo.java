package ar.edu.itba.it.paw.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateHashtagRepo extends AbstractHibernateRepo implements
		HashtagRepo {

	@Autowired
	public HibernateHashtagRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Hashtag getHashtag(String hashtag) {
		List<Hashtag> result = find(" from Hashtag where hashtag = ?", hashtag);
		return result.size() > 0 ? result.get(0) : null;
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
		Query query = session
				.createQuery("select h,count(distinct c) from Comment c inner join c.hashtags h where c.date <= ? and c.date >= ? group by h order by count(distinct c)");

		query.setTimestamp(0,new Timestamp(to.getTime()));
		query.setTimestamp(1,new Timestamp(from.getTime()));
		ScrollableResults results = query.scroll();

		ArrayList<Hashtag> aux;

		while (results.next()) {
			Object[] row = results.get();
			Hashtag hashtag = (Hashtag) row[0];
			Integer ranking = ((Long) row[1]).intValue();

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

	@Override
	public void addHashtag(Hashtag hashtag) {
		super.save(hashtag);

	}
	
	@Override
	public List<User> mostFollowed(int maxResults) {
		Session session = getSession();
		Query query = session.createQuery("select u,count(uf) from User u inner join u.followers uf group by u order by count(uf) desc");
		query.setMaxResults(maxResults);
		
		ScrollableResults results = query.scroll();
		List<User> mostFollowed = new ArrayList<User>();
		
		
		while(results.next()){
			Object[] row = results.get();
			mostFollowed.add((User)row[0]);
		}
				
		
		return mostFollowed;
	}
}
