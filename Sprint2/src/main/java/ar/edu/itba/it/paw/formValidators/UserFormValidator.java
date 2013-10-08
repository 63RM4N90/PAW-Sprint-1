package ar.edu.itba.it.paw.formValidators;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.model.User;

public class UserFormValidator implements Validator {

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
	//@Equals(property = "confirmPassword")
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

		User user = (User) target;

		if (!(user.getPassword().equals())) {
			errors.rejectValue("password", "notmatch.password");
		}
	}
}
