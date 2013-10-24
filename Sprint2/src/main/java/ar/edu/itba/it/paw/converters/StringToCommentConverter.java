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

	private HttpSession session;
	private UserRepo userRepo;
	private CommentRepo commentRepo;

	@Autowired
	public StringToCommentConverter(UserRepo userService,
			CommentRepo commentService, HttpSession session) {
		this.session = session;
		this.userRepo = userService;
		this.commentRepo = commentService;
	}

	@Override
	public Comment convert(String arg0) {
		User author = userRepo.getUser((String) session
				.getAttribute("username"));
		return new Comment(author, new Date(), arg0,
				commentRepo.getHashtagList(arg0, author));
	}

}
