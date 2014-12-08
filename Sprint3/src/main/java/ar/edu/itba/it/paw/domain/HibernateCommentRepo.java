package ar.edu.itba.it.paw.domain;

import java.util.ArrayList;
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

	private HashtagRepo hashtagRepo;
	private UserRepo userRepo;

	@Autowired
	public HibernateCommentRepo(SessionFactory sessionFactory,
			HashtagRepo hibernateHashtagRepo, UserRepo hibernateUserRepo,
			NotificationRepo notificationRepo) {
		super(sessionFactory);
		this.hashtagRepo = hibernateHashtagRepo;
		this.userRepo = hibernateUserRepo;
	}

	@Override
	public List<Comment> getAll() {
		return find("from Comment order by Comment.date asc");
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
			Hashtag tag = hashtagRepo.getHashtag(hashtag);
			if (tag == null) {
				tag = new Hashtag(hashtag, author, new Date());
				hashtagRepo.addHashtag(tag);
			}
			ans.add(tag);
		}
		return ans;
	}

	@Override
	public List<User> getReferences(String comment, User sessionUser) {
		List<User> ans = new ArrayList<User>();
		String patternStr = "@([A-Za-z0-9-_]+)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(comment);
		pattern = Pattern.compile(patternStr);
		matcher = pattern.matcher(comment);
		String result = "";
		while (matcher.find()) {
			result = matcher.group();
			String username = result.substring(1);
			User user = userRepo.getUser(username);
			if (user != null) {
				ans.add(user);
			}
		}
		return ans;
	}

	@Override
	public void delete(Comment comment) {
		comment.getAuthor().removeFavourite(comment);
		comment.getAuthor().deleteComment(comment);
		super.delete(comment);
	}

	@Override
	public Comment getComment(int id) {
		return super.get(Comment.class, id);
	}

	@Override
	public void addComment(Comment comment) {
		super.save(comment);
	}

	@Override
	public void recthulhu(Comment comment, User user) {
		User originalAuthor = comment.getOriginalAuthor();
		Comment rechtulhu = new Comment(user, new Date(), comment.getComment(),
				getHashtagList(comment.getComment(), user), getReferences(
						comment.getComment(), user), originalAuthor);
		if (!user.getComments().contains(rechtulhu)) {
			addComment(rechtulhu);
		}
	}
}
