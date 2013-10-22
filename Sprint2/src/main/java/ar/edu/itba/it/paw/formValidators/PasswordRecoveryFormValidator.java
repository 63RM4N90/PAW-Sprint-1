package ar.edu.itba.it.paw.formValidators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.model.User;

public class PasswordRecoveryFormValidator implements Validator {

	private static final int MAX_STRING_LENGTH = 32;
	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final int MAX_PASSWORD_LENGTH = 16;
	private static final int MAX_QUE_AND_ANS_LENGTH = 64;
	
	private String secretAnswer;
	private String password;
	private String confirmPassword;
	private String userToRecover;
	
	public PasswordRecoveryFormValidator(String secretAnswer, String password,
			String confirmPassword, String userToRecover) {
		this.secretAnswer = secretAnswer;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.userToRecover = userToRecover;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		checkLength(errors);
		checkEmpt(errors);
		if (secretAnswer != null) {
			boolean matches = u.getSecretAnswer().equals(secretAnswer);
			boolean passwordMatches = false;
			if (password != null && confirmPassword != null) {
				passwordMatches = password.equals(confirmPassword);
			}
			if (!matches) {
				mav.addObject("error", "Wrong secret Answer");
				mav.addObject("success",
						"Recovering password for " + u.getUsername());
				mav.addObject("user", u);
			} else {
				if (passwordMatches
						&& password.length() <= MAX_PASSWORD_LENGTH
						&& password.length() >= MIN_PASSWORD_LENGTH) {
					mav.addObject("passwordRecovered", true);
					mav.addObject("success",
							"Your password was changed successfully!");
					u.setPassword(password);
					userService.save(u);
				} else {
					mav.addObject("error",
							"Passwords dont match or have less than 8 characters or more than 16.");
					mav.addObject("success",
							"Recovering password for " + u.getUsername());
					mav.addObject("user", u);
				}
			}
		}
	}
	
	private void checkEmpt(Errors errors) {
		if (userToRecover == null) {
			errors.rejectValue("username", "empty.username");
		}
		if (password == null) {
			errors.rejectValue("password", "empty.password");
		}
		if (confirmPassword == null) {
			errors.rejectValue("confirmPassword", "empty.confirmPassword");
		}
		if (secretAnswer == null) {
			errors.rejectValue("secretAnswer", "empty.secretAnswer");
		}
	}
	
	private void checkLength(Errors errors) {
		if (userToRecover.length() > MAX_STRING_LENGTH || userToRecover.length() == 0) {
			errors.rejectValue("username", "length.username");
		}
		if (password.length() < MIN_PASSWORD_LENGTH
				|| password.length() > MAX_PASSWORD_LENGTH) {
			errors.rejectValue("password", "length.password");
		}
		if (confirmPassword.length() < MIN_PASSWORD_LENGTH
				|| confirmPassword.length() > MAX_PASSWORD_LENGTH) {
			errors.rejectValue("confirmPassword", "length.confirmPassword");
		}
		if (secretAnswer.length() > MAX_QUE_AND_ANS_LENGTH
				|| secretAnswer.length() == 0) {
			errors.rejectValue("secretAnswer", "length.secretAnswer");
		}
	}
}
