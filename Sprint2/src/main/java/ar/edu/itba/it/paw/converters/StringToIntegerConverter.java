package ar.edu.itba.it.paw.converters;

import org.springframework.core.convert.converter.Converter;

public class StringToIntegerConverter implements Converter<String, Integer> {

	@Override
	public Integer convert(String arg0) {
		return Integer.parseInt(arg0);
	}
}
