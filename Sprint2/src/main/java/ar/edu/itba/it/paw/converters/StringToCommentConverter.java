package ar.edu.itba.it.paw.converters;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.services.CommentService;
import ar.edu.itba.it.paw.services.UserService;

@Component
public class StringToCommentConverter implements Converter<String, Comment> {

	private HttpSession session;
	private UserService userService;
	private CommentService commentService;

	@Autowired
	public StringToCommentConverter(UserService userService,
			CommentService commentService, HttpSession session) {
		this.session = session;
		this.userService = userService;
		this.commentService = commentService;
	}

	@Override
	public Comment convert(String arg0) {
		User author = userService.getUser((String) session
				.getAttribute("username"));
		return new Comment(author, new Date(), arg0,
				commentService.getHashtagList(arg0, author));
	}

}
