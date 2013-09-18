package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import database.HashtagDAO;
import model.Hashtag;

public class HashtagService {

	HashtagDAO hashtagDAO;

	private static HashtagService instance;

	public static HashtagService getInstance() {
		if (instance == null) {
			instance = new HashtagService();
		}
		return instance;
	}

	private HashtagService() {
		hashtagDAO = HashtagDAO.getInstance();
	}

	public void save(Hashtag hashtag) {
		hashtagDAO.save(hashtag);
	}

	public Hashtag getHashtag(String hashtag) {
		return hashtagDAO.getHashtag(hashtag);
	}

	public LinkedList<Hashtag> topHashtags(int days) {
		LinkedList<Hashtag> ranking = new LinkedList<Hashtag>();

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
						ranking.add(aux.get(index));
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
