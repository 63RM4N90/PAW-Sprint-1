package ar.edu.itba.it.paw.formValidators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.form.UserForm;

public class UserFormValidator implements Validator {

	private static final int MAX_STRING_LENGTH = 32;
	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final int MAX_PASSWORD_LENGTH = 16;
	private static final int MAX_DESCRIPTION_LENGTH = 140;
	private static final int MAX_QUE_AND_ANS_LENGTH = 64;

	public UserFormValidator() {
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return UserForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserForm userForm = (UserForm) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"required.password", "Field name is required.");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
				"required.confirmPassword", "Field name is required.");

		// User user = (User) target;

		if (!(userForm.getPassword().equals(userForm.getConfirmPassword()))) {
			errors.rejectValue("password", "notmatch.password");
		}
		checkEmptyInputs(userForm, errors);
		checkInputLength(userForm, errors);
		checkPasswordConfirm(userForm, errors);
	}

	private void checkPasswordConfirm(UserForm userForm, Errors errors) {
		if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
			errors.rejectValue("password", "distinct.password");
		}
	}

	private void checkInputLength(UserForm userForm, Errors errors) {
		if (userForm.getName().length() > MAX_STRING_LENGTH
				|| userForm.getName().length() == 0) {
			errors.rejectValue("name", "length.name");
		}
		if (userForm.getSurname().length() > MAX_STRING_LENGTH
				|| userForm.getSurname().length() == 0) {
			errors.rejectValue("surname", "length.surname");
		}
		if (userForm.getUsername().length() > MAX_STRING_LENGTH
				|| userForm.getUsername().length() == 0) {
			errors.rejectValue("username", "length.username");
		}
		if (userForm.getPassword().length() < MIN_PASSWORD_LENGTH
				|| userForm.getPassword().length() > MAX_PASSWORD_LENGTH) {
			errors.rejectValue("password", "length.password");
		}
		if (userForm.getConfirmPassword().length() < MIN_PASSWORD_LENGTH
				|| userForm.getConfirmPassword().length() > MAX_PASSWORD_LENGTH) {
			errors.rejectValue("confirmPassword", "length.confirmPassword");
		}
		if (userForm.getDescription().length() > MAX_DESCRIPTION_LENGTH
				|| userForm.getDescription().length() == 0) {
			errors.rejectValue("description", "length.description");
		}
		if (userForm.getSecretQuestion().length() > MAX_QUE_AND_ANS_LENGTH
				|| userForm.getSecretQuestion().length() == 0) {
			errors.rejectValue("secretQuestion", "length.secretQuestion");
		}
		if (userForm.getSecretAnswer().length() > MAX_QUE_AND_ANS_LENGTH
				|| userForm.getSecretAnswer().length() == 0) {
			errors.rejectValue("secretAnswer", "length.secretAnswer");
		}
	}

	private void checkEmptyInputs(UserForm userForm, Errors errors) {
		if (userForm.getName() == null) {
			errors.rejectValue("name", "empty.name");
		}
		if (userForm.getSurname() == null) {
			errors.rejectValue("surname", "empty.surname");
		}
		if (userForm.getUsername() == null) {
			errors.rejectValue("username", "empty.username");
		}
		if (userForm.getPassword() == null) {
			errors.rejectValue("password", "empty.password");
		}
		if (userForm.getConfirmPassword() == null) {
			errors.rejectValue("confirmPassword", "empty.confirmPassword");
		}
		if (userForm.getDescription() == null) {
			errors.rejectValue("description", "empty.description");
		}
		if (userForm.getSecretQuestion() == null) {
			errors.rejectValue("secretQuestion", "empty.secretQuestion");
		}
		if (userForm.getSecretAnswer() == null) {
			errors.rejectValue("secretAnswer", "empty.secretAnswer");
		}
	}
}
