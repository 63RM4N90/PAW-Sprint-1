package ar.edu.itba.it.paw.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.services.UserService;

@Component
public class StringToUserConverter implements Converter<String, User> {

	private UserService userService;
	
	@Autowired
	public StringToUserConverter(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public User convert(String arg0) {
		return userService.getUser(arg0);
	}
}
