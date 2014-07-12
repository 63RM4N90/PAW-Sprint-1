package ar.edu.itba.it.paw.web.common;

import org.apache.wicket.model.IModel;

public class CommentWrapperROM<T> implements IModel<T> {

	private static final long serialVersionUID = 5312924914928923754L;
	private transient T commentWrapper;
	
	public CommentWrapperROM(T commentWrapper) {
		this.commentWrapper = commentWrapper;
	}
	
	@Override
	public void detach() {
	}

	@Override
	public T getObject() {
		return commentWrapper;
	}

	@Override
	public void setObject(T object) {
		commentWrapper = object;
	}
}