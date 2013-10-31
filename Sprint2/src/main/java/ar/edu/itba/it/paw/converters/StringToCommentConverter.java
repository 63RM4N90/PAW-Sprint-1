package ar.edu.itba.it.paw.converters;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.CommentRepo;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;

@Component
public class StringToCommentConverter implements Converter<String, Comment> {

	private CommentRepo commentRepo;
	private UserRepo userRepo;
	private HttpSession session;

	@Autowired
	public StringToCommentConverter(HttpSession session,
			CommentRepo commentRepository, UserRepo userRepo) {
		this.commentRepo = commentRepository;
		this.userRepo = userRepo;
		this.session = session;
	}

	@Override
	public Comment convert(String comment) {
		User author = userRepo.getUser((String) session
				.getAttribute("username"));
		return new Comment(author, new Date(), comment,
				commentRepo.getHashtagList(comment, author),
				commentRepo.getReferences(comment),null);
	}
}
