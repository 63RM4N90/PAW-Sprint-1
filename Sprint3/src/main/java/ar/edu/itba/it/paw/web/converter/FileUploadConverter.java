package ar.edu.itba.it.paw.web.converter;

import java.util.Locale;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

public class FileUploadConverter implements IConverter<FileUpload> {

	@Override
	public FileUpload convertToObject(String arg0, Locale arg1)
			throws ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String convertToString(FileUpload arg0, Locale arg1) {
		// TODO Auto-generated method stub
		return new String(arg0.getBytes());
	}
}
