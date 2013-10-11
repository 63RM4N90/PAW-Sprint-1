package ar.edu.itba.it.paw.form;


public class UserForm {

	private String name;
	private String surname;
	private String username;
	private String password;
	private String confirmPassword;
	private String description;
	private String secretQuestion;
	private String secretAnswer;
	
	public UserForm(String name, String surname, String username,
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
}
