package ar.edu.itba.it.paw.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.model.Hashtag;
import ar.edu.itba.it.paw.services.HashtagService;

@Component
public class StringToHashtagConverter implements Converter<String, Hashtag> {

	private HashtagService hashtagService;
	
	@Autowired
	public StringToHashtagConverter(HashtagService hashtagService) {
		this.hashtagService = hashtagService;
	}

	@Override
	public Hashtag convert(String hashtag) {
		return hashtagService.getHashtag(hashtag);
	}
}
