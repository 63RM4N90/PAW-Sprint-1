package ar.edu.itba.it.paw.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateCommentRepo extends AbstractHibernateRepo implements
		CommentRepo {

	private HibernateHashtagRepo hibernateHashtagRepo;

	@Autowired
	public HibernateCommentRepo(SessionFactory sessionFactory,
			HibernateHashtagRepo hibernateHashtagRepo) {
		super(sessionFactory);
		this.hibernateHashtagRepo = hibernateHashtagRepo;
	}

	@Override
	public List<Comment> getComments(User user) {
		return find("from Comment where author = ?", user);
	}

	@Override
	public List<Comment> getAll() {
		return find("from Comment");
	}

	@Override
	public Comment getComment(int id) {
		return get(Comment.class, id);
	}

	@Override
	public List<Hashtag> getHashtagList(String comment, User author) {
		List<Hashtag> ans = new ArrayList<Hashtag>();
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
	public List<Comment> getComments(String hashtag) {
		// Deberian ordenarse los comentarios por fecha desde la query hql
		return find(
				"from Comment c inner join c.hashtags where c.hashtags = ?",
				hashtag);
	}
}
