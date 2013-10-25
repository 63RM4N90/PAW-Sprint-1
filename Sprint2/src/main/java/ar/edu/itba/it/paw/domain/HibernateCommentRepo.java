package ar.edu.itba.it.paw.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateCommentRepo extends AbstractHibernateRepo implements
		CommentRepo {

	private HibernateHashtagRepo hibernateHashtagRepo;
	private HibernateUserRepo hibernateUserRepo;

	@Autowired
	public HibernateCommentRepo(SessionFactory sessionFactory,
			HibernateHashtagRepo hibernateHashtagRepo,
			HibernateUserRepo hibernateUserRepo) {
		super(sessionFactory);
		this.hibernateHashtagRepo = hibernateHashtagRepo;
		this.hibernateUserRepo = hibernateUserRepo;
	}

	@Override
	public List<Comment> getAll() {
		return find("from Comment");
	}

	@Override
	public Set<Hashtag> getHashtagList(String comment, User author) {
		Set<Hashtag> ans = new HashSet<Hashtag>();
		String patternStr = "#([A-Za-z0-9_]+)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(comment);
		String result = "";

		while (matcher.find()) {
			result = matcher.group();
			String hashtag = result.substring(1);
			Hashtag tag = hibernateHashtagRepo.getHashtag(hashtag);
			if (tag == null) {
				tag = new Hashtag(hashtag, author, new Date());
				hibernateHashtagRepo.save(tag);
			}
			ans.add(tag);
		}

		return ans;
	}

	@Override
	public Set<User> getReferences(String comment) {
		Set<User> ans = new HashSet<User>();
		String patternStr = "(?:\\s|\\A)[@]+([A-Za-z0-9-_]+)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(comment);
		String result = "";
		pattern = Pattern.compile(patternStr);
		matcher = pattern.matcher(comment);
		while (matcher.find()) {
			result = matcher.group();
			result = result.replace(" ", "");
			ans.add(hibernateUserRepo.getUser(result));
			String userHTML = "<a href='http://twitter.com/${rawName}'>"
					+ result + "</a>";
			comment = comment.replace(result, userHTML);
		}
		return ans;
	}

}
