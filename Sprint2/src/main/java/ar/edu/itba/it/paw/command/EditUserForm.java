package ar.edu.itba.it.paw.command;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import ar.edu.itba.it.paw.domain.User;

public class EditUserForm {

	private int id;
	private String name;
	private String surname;
	private String password;
	private String confirmPassword;
	private String description;
	private String privacy;
	private MultipartFile picture;

	public EditUserForm() {
	}

	public EditUserForm(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.password = user.getPassword();
		this.confirmPassword = user.getPassword();
		this.description = user.getDescription();
		this.privacy = user.isPrivate() ? "T" : "F";
	}

	public MultipartFile getPicture() {
		return picture;
	}

	public void setPicture(MultipartFile picture) {
		this.picture = picture;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
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

	public void update(User user) throws IOException {
		user.setName(name);
		user.setSurname(surname);
		user.setDescription(description);
		user.setPassword(password);
		if (picture.getSize() != 0) {
			user.setPicture(picture.getBytes());
		}
		if (privacy.equals("T")) {
			user.makePrivate();
		} else {
			user.makePublic();
		}
	}
}
