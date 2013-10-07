package ar.edu.itba.it.paw.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends AbstractModel {
	public static final int MIN_USERNAME_LENGTH = 8;
	public static final int MAX_USERNAME_LENGTH = 16;
	public static final int MIN_PASSWORD_LENGTH = 8;
	public static final int MAX_PASSWORD_LENGTH = 16;
	@Column(nullable=false)
	private String name;
	@Column(nullable=false)
	private String surname;
	@Column(nullable=false)
	private String username;
	@Column(nullable=false)
	private String description;
	@Column(nullable=false)
	private String password;
	private byte[] picture;
	@Column(nullable=false)
	private String secretQuestion;
	@Column(nullable=false)
	private String secretAnswer;
	@Column(nullable=false)
	private Date registrationDate;

	User() {
	}

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
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public void setRegistrationDate(Date registrationDate) {
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
}
