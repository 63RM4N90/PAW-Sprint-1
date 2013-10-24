package ar.edu.itba.it.paw.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.domain.Hashtag;
import ar.edu.itba.it.paw.domain.HashtagRepo;

@Component
public class StringToHashtagConverter implements Converter<String, Hashtag> {

	private HashtagRepo hashtagRepo;

	@Autowired
	public StringToHashtagConverter(HashtagRepo hashtagService) {
		this.hashtagRepo = hashtagService;
	}

	@Override
	public Hashtag convert(String hashtag) {
		return hashtagRepo.getHashtag(hashtag);
	}
}
