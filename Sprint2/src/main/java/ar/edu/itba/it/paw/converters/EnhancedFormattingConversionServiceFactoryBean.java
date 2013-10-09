package ar.edu.itba.it.paw.converters;

import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

public class EnhancedFormattingConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	@SuppressWarnings("deprecation")
	protected void installFormatters(FormatterRegistry registry){
		super.installFormatters(registry);
		registry.addConverter(new StringToIntegerConverter());
	}
}
