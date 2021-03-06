package model;

import java.util.Date;

public class User extends AbstractModel {
	public static final int MIN_USERNAME_LENGTH = 8;
	public static final int MAX_USERNAME_LENGTH = 16;
	public static final int MIN_PASSWORD_LENGTH = 8;
	public static final int MAX_PASSWORD_LENGTH = 16;
	private String name;
	private String surname;
	private String username;
	private String description;
	private String password;
	private byte[] picture;
	private String secretQuestion;
	private String secretAnswer;
	private Date registrationDate;

	public User(String name, String surname, String username,
			String description, String password, byte[] picture,
			String secretQuestion, String secretAnswer, Date registrationDate)
			throws IllegalArgumentException {
		if (name == null || surname == null || username == null
				|| password == null || secretQuestion == null
				|| secretAnswer == null) {
			throw new IllegalArgumentException(
					"One or more required fields have not been completed.");
		}
		if (username.length() < MIN_USERNAME_LENGTH
				&& username.length() > MAX_USERNAME_LENGTH) {
			throw new IllegalArgumentException(
					"The user name must contain between " + MIN_USERNAME_LENGTH
							+ " and " + MAX_USERNAME_LENGTH + " characters.");
		}
		if (password.length() < MIN_PASSWORD_LENGTH
				&& password.length() > MAX_PASSWORD_LENGTH) {
			throw new IllegalArgumentException(
					"The password must contain between " + MIN_USERNAME_LENGTH
							+ " and " + MAX_USERNAME_LENGTH + " characters.");
		}
		setId(-1);
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.description = description;
		this.password = password;
		this.picture = picture;
		this.secretQuestion = secretQuestion;
		this.secretAnswer = secretAnswer;
		this.registrationDate = registrationDate;
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

	public byte[] getPicture() {
		return picture;
	}

	public void setFile(byte[] picture) {
		this.picture = picture;
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

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
}
