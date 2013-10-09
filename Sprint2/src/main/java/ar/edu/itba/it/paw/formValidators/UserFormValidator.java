package ar.edu.itba.it.paw.formValidators;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.model.User;

public class UserFormValidator implements Validator {

	private static final int MAX_STRING_LENGTH = 32;
	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final int MAX_PASSWORD_LENGTH = 16;
	private static final int MAX_DESCRIPTION_LENGTH = 140;
	private static final int MAX_QUE_AND_ANS_LENGTH = 64;

	@NotNull
	@Size(min = 1, max = 32)
	private String name;
	@NotNull
	@Size(min = 1, max = 32)
	private String surname;
	@NotNull
	@Size(min = 1, max = 32)
	private String username;
	@NotNull
	@Size(min = 8, max = 16)
	private String password;
	@NotNull
	@Size(min = 8, max = 16)
	// @Equals(property = "confirmPassword")
	private String confirmPassword;
	@NotNull
	@Size(min = 1, max = 140)
	private String description;
	@NotNull
	@Size(min = 1, max = 64)
	private String secretQuestion;
	@NotNull
	@Size(min = 1, max = 64)
	private String secretAnswer;

	public UserFormValidator(String name, String surname, String username,
			String password, String confirmPassword, String description,
			String secretQuestion, String secretAnswer) {
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.description = description;
		this.secretQuestion = secretQuestion;
		this.secretAnswer = secretAnswer;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"required.password", "Field name is required.");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
				"required.confirmPassword", "Field name is required.");

		//User user = (User) target;

		if (!(password.equals(confirmPassword))) {
			errors.rejectValue("password", "notmatch.password");
		}
		checkEmptyInputs(errors);
		checkInputLength(errors);
		checkPasswordConfirm(errors);
	}
	
	private void checkPasswordConfirm(Errors errors) {
		if (!password.equals(confirmPassword)) {
			errors.rejectValue("password", "distinct.password");
		}
	}

	private void checkInputLength(Errors errors) {
		if (name.length() > MAX_STRING_LENGTH || name.length() == 0) {
			errors.rejectValue("name", "length.name");
		}
		if (surname.length() > MAX_STRING_LENGTH || surname.length() == 0) {
			errors.rejectValue("surname", "length.surname");
		}
		if (username.length() > MAX_STRING_LENGTH || username.length() == 0) {
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
		if (description.length() > MAX_DESCRIPTION_LENGTH
				|| description.length() == 0) {
			errors.rejectValue("description", "length.description");
		}
		if (secretQuestion.length() > MAX_QUE_AND_ANS_LENGTH
				|| secretQuestion.length() == 0) {
			errors.rejectValue("secretQuestion", "length.secretQuestion");
		}
		if (secretAnswer.length() > MAX_QUE_AND_ANS_LENGTH
				|| secretAnswer.length() == 0) {
			errors.rejectValue("secretAnswer", "length.secretAnswer");
		}
	}

	private void checkEmptyInputs(Errors errors) {
		if (name == null) {
			errors.rejectValue("name", "empty.name");
		}
		if (surname == null) {
			errors.rejectValue("surname", "empty.surname");
		}
		if (username == null) {
			errors.rejectValue("username", "empty.username");
		}
		if (password == null) {
			errors.rejectValue("password", "empty.password");
		}
		if (confirmPassword == null) {
			errors.rejectValue("confirmPassword", "empty.confirmPassword");
		}
		if (description == null) {
			errors.rejectValue("description", "empty.description");
		}
		if (secretQuestion == null) {
			errors.rejectValue("secretQuestion", "empty.secretQuestion");
		}
		if (secretAnswer == null) {
			errors.rejectValue("secretAnswer", "empty.secretAnswer");
		}
	}
}
