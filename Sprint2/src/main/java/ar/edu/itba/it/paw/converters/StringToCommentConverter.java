package ar.edu.itba.it.paw.converters;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.services.CommentService;
import ar.edu.itba.it.paw.services.UserService;

@Component
public class StringToCommentConverter implements Converter<String, Comment> {

	private CommentRepository commentRepository;
	
	@Autowired
	public StringToCommentConverter(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	@Override
	public Comment convert(String arg0) {
		int commentId = Integer.parseInt(arg0);
		return commentRepository.get(commentId);
	}

}
