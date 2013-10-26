package ar.edu.itba.it.paw.command;

import java.util.Date;

import ar.edu.itba.it.paw.domain.User;

public class EditUserForm {

	private int id;
	private String name;
	private String surname;
	private String username;
	private String password;
	private String confirmPassword;
	private String description;
	private String secretQuestion;
	private String secretAnswer;
	private Date registrationDate;

	public EditUserForm() {
	}

	public EditUserForm(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.confirmPassword = user.getPassword();
		this.description = user.getDescription();
		this.secretQuestion = user.getSecretQuestion();
		this.secretAnswer = user.getSecretAnswer();
		this.registrationDate = user.getRegistrationDate();
	}

	public boolean userIsNew() {
		return id == 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void update(User user) {
		user.setName(name);
		user.setSurname(surname);
		user.setDescription(description);
		user.setPassword(password);
	}

	public User build() {
		return new User(name, surname, username, description, password, null,
				secretQuestion, secretAnswer, registrationDate, false);
	}
}
