package ar.edu.itba.it.paw.domain;

import java.util.Date;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class CommentTest {

	private User author;

	@Before
	public void setUp() {
		author = new User("test", "test", "test", "test", "12345678", null,
				null, "png", "test", "test", new Date(), false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void followUserTest() {
		new Comment(
				author,
				new Date(),
				"testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest",
				new HashSet<Hashtag>(), new HashSet<User>(), author);
	}
}
