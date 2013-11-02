package ar.edu.itba.it.paw.command;

import java.io.IOException;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

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
	private String privacy;
	private MultipartFile picture;

	public UserForm() {
	}

	public MultipartFile getPicture() {
		return picture;
	}

	public void setPicture(MultipartFile picture) throws IOException {
		System.out.println("SET PICTURE = " + picture.getBytes());
		this.picture = picture;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
		if (privacy.equals("T")) {
			user.makePrivate();
		} else {
			user.makePublic();
		}
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
		System.out.println("SET NAME");
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		System.out.println("SET SURNAME");
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		System.out.println("SET USERNAME");
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

	public User build() throws IOException {
		// Idem a "EditUserForm"
		byte[] bytePicture = picture.getBytes();
		return new User(name, surname, username, description, confirmPassword,
				bytePicture, secretQuestion, secretAnswer, new Date(), false);
	}
}
