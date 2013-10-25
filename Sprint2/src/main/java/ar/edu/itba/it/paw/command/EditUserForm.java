package ar.edu.itba.it.paw.command;

import java.util.Date;

import ar.edu.itba.it.paw.domain.User;

public class EditUserForm {

	private User user;
	private String name;
	private String surname;
	private String password;
	private String confirmPassword;
	private String description;
	private String secretQuestion;
	private String secretAnswer;
	//Falta agregar las cosas para la visibilidad del perfil

	public EditUserForm() {
	}

	public EditUserForm(User user) {
		this.user = user;
		this.name = user.getName();
		this.surname = user.getSurname();
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
			//El "false" del final refiere a la visibilidad del perfil.
			//Hasta que se agregue el campo, es false. Después será
			//opcional desde la registración
			return new User(name, surname, user.getUsername(), description,
					confirmPassword, null, secretQuestion, secretAnswer,
					new Date(),false);
		} else {
			user.setName(name);
			user.setSurname(surname);
			user.setPassword(password);
			user.setDescription(description);
			user.setSecretQuestion(secretQuestion);
			user.setSecretAnswer(secretAnswer);
			return user;
		}
	}
}
