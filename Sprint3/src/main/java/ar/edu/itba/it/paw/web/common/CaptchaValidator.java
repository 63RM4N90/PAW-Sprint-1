package ar.edu.itba.it.paw.web.common;

import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import com.google.code.kaptcha.Constants;

import ar.edu.itba.it.paw.web.SocialCthulhuSession;
 
public class CaptchaValidator implements IValidator<String> {
 
	private static final long serialVersionUID = 1L;
	//private String INVALID_CODE = "captcha.invalid";
 
	public void validate(IValidatable<String> validatable) {
		String kaptchaReceived = (String) validatable.getValue();
 
		SocialCthulhuSession session = SocialCthulhuSession.get();
		
		//Request request = RequestCycle.get().getRequest();
		//HttpServletRequest httpRequest = ((WebRequest) request)
			//	.getHttpServletRequest();
 
		ValidationError error = new ValidationError();
		
		String kaptchaExpected = (String) session
			.getAttribute(Constants.KAPTCHA_SESSION_CONFIG_KEY);
 
		System.out.println("El captcha recibido es " + kaptchaReceived);
		System.out.println("El captcha esperado es " + kaptchaExpected);
		
		if (kaptchaReceived == null
				|| !kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
			error.addKey("captcha_error");
			validatable.error(error);
		}
 
	}
 
//	// validate on numm value as well
//	@Override
//	public boolean validateOnNullValue() {
// 
//		return true;
// 
//	}
// 
//	@Override
//	protected String resourceKey() {
//		return INVALID_CODE;
//	}

}
