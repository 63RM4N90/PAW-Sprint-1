package ar.edu.itba.it.paw.domain;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class HashtagTest {

	private User author;

	@Before
	public void setUp() {
		author = new User("test", "test", "test", "test", "12345678", null,
				null, "png", "test", "test", new Date(), false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void followUserTest() {
		new Hashtag(
				"testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest",
				author, new Date());
	}
}
