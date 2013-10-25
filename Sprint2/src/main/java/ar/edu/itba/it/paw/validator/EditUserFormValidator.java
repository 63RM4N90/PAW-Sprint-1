package ar.edu.itba.it.paw.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.command.EditUserForm;

public class EditUserFormValidator implements Validator {

	private static final int MAX_STRING_LENGTH = 32;
	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final int MAX_PASSWORD_LENGTH = 16;
	private static final int MAX_DESCRIPTION_LENGTH = 140;
	private static final int MAX_QUE_AND_ANS_LENGTH = 64;

	@Override
	public boolean supports(Class<?> clazz) {
		return EditUserForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		EditUserForm userForm = (EditUserForm) target;
		checkEmptyInputs(userForm, errors);
		checkInputLength(userForm, errors);
		if (userForm.getPassword().length() > MIN_PASSWORD_LENGTH
				&& userForm.getPassword().length() < MAX_PASSWORD_LENGTH) {
			checkPasswordConfirm(userForm, errors);
		}
	}

	private void checkPasswordConfirm(EditUserForm editUserForm, Errors errors) {
		if (!editUserForm.getPassword().equals(
				editUserForm.getConfirmPassword())) {
			errors.rejectValue("password", "nonmatch");
		}
	}

	private void checkInputLength(EditUserForm editUserForm, Errors errors) {
		if (editUserForm.getName().length() > MAX_STRING_LENGTH
				|| editUserForm.getName().length() == 0) {
			errors.rejectValue("name", "length");
		}
		if (editUserForm.getSurname().length() > MAX_STRING_LENGTH
				|| editUserForm.getSurname().length() == 0) {
			errors.rejectValue("surname", "length");
		}
		if (editUserForm.getPassword().length() < MIN_PASSWORD_LENGTH
				|| editUserForm.getPassword().length() > MAX_PASSWORD_LENGTH) {
			errors.rejectValue("password", "length");
		}
		if (editUserForm.getConfirmPassword().length() < MIN_PASSWORD_LENGTH
				|| editUserForm.getConfirmPassword().length() > MAX_PASSWORD_LENGTH) {
			errors.rejectValue("confirmPassword", "length");
		}
		if (editUserForm.getDescription().length() > MAX_DESCRIPTION_LENGTH
				|| editUserForm.getDescription().length() == 0) {
			errors.rejectValue("description", "length");
		}
		if (editUserForm.getSecretQuestion().length() > MAX_QUE_AND_ANS_LENGTH
				|| editUserForm.getSecretQuestion().length() == 0) {
			errors.rejectValue("secretQuestion", "length");
		}
		if (editUserForm.getSecretAnswer().length() > MAX_QUE_AND_ANS_LENGTH
				|| editUserForm.getSecretAnswer().length() == 0) {
			errors.rejectValue("secretAnswer", "length");
		}
	}

	private void checkEmptyInputs(EditUserForm editUserForm, Errors errors) {
		if (editUserForm.getName() == null) {
			errors.rejectValue("name", "empty");
		}
		if (editUserForm.getSurname() == null) {
			errors.rejectValue("surname", "empty");
		}
		if (editUserForm.getPassword() == null) {
			errors.rejectValue("password", "empty");
		}
		if (editUserForm.getConfirmPassword() == null) {
			errors.rejectValue("confirmPassword", "empty");
		}
		if (editUserForm.getDescription() == null) {
			errors.rejectValue("description", "empty");
		}
		if (editUserForm.getSecretQuestion() == null) {
			errors.rejectValue("secretQuestion", "empty");
		}
		if (editUserForm.getSecretAnswer() == null) {
			errors.rejectValue("secretAnswer", "empty");
		}
	}
}
