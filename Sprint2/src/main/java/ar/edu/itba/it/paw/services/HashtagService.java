package ar.edu.itba.it.paw.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.it.paw.dao.impl.DbHashtagDAOImpl;
import ar.edu.itba.it.paw.model.Hashtag;
import ar.edu.itba.it.paw.model.RankedHashtag;
@Service
public class HashtagService {

	private DbHashtagDAOImpl hashtagDAO;
	@Autowired
	public HashtagService(DbHashtagDAOImpl hashtagDAO) {
		this.hashtagDAO = hashtagDAO;
	}

	public void save(Hashtag hashtag) {
		hashtagDAO.save(hashtag);
	}

	public Hashtag getHashtag(String hashtag) {
		return hashtagDAO.getHashtag(hashtag);
	}

	public LinkedList<RankedHashtag> topHashtags(int days) {
		LinkedList<RankedHashtag> ranking = new LinkedList<RankedHashtag>();

		Date to = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.DAY_OF_YEAR, -days);
		Date from = calendar.getTime();

		TreeMap<Integer, ArrayList<Hashtag>> top = hashtagDAO.rankedHashtags(
				from, to);

		if (top != null) {

			ArrayList<Hashtag> aux;
			int count = 0;

			try {
				while (count < 10) {
					int key = top.lastKey();
					aux = top.get(key);
					int index = 0;
					while (count < 10 && index < aux.size()) {
						ranking.add(new RankedHashtag(aux.get(index),key));
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
}
