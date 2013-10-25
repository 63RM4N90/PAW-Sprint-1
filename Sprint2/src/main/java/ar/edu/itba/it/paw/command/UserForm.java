package ar.edu.itba.it.paw.command;

import java.util.Date;

import ar.edu.itba.it.paw.domain.User;

public class UserForm {

	private User user;
	private String name;
	private String surname;
	private String username;
	private String password;
	private String confirmPassword;
	private String description;
	private String secretQuestion;
	private String secretAnswer;
	
	public UserForm() {
	}

	public UserForm(User user) {
		this.user = user;
		this.name = user.getName();
		this.surname = user.getSurname();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.description = user.getDescription();
		this.secretQuestion = user.getSecretQuestion();
		this.secretAnswer = user.getSecretAnswer();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	public User build() {
		if (user == null) {
			//Idem a "EditUserForm"
			return new User(name, surname, username, description,
					confirmPassword, null, secretQuestion, secretAnswer,
					new Date(),false);
		} else {
			user.setName(name);
			user.setSurname(surname);
			user.setUsername(username);
			user.setPassword(password);
			user.setDescription(description);
			user.setSecretQuestion(secretQuestion);
			user.setSecretAnswer(secretAnswer);
			return user;
		}
	}
}
