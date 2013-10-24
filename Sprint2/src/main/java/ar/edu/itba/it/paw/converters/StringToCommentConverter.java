package ar.edu.itba.it.paw.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.CommentRepo;

@Component
public class StringToCommentConverter implements Converter<String, Comment> {

	private CommentRepo commentRepo;

	@Autowired
	public StringToCommentConverter(CommentRepo commentRepository) {
		this.commentRepo = commentRepository;
	}

	@Override
	public Comment convert(String arg0) {
		int commentId = Integer.parseInt(arg0);
		return commentRepo.get(Comment.class, commentId);
	}
}
