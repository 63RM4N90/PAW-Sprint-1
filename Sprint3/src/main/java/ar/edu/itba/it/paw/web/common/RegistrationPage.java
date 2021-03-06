package ar.edu.itba.it.paw.web.common;

import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.BasePage;

public class RegistrationPage extends BasePage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private UserRepo users;

	private transient String name;
	private transient String surname;
	private transient String username;
	private transient String password;
	private transient String confirmPassword;
	private transient String description;
	private transient String secretQuestion;
	private transient String secretAnswer;
	private String captchaInput;
	private transient List<FileUpload> picture;
	private transient List<FileUpload> background;

	private static transient final int MIN_NAME_LENGTH = 1;
	private static transient final int MAX_NAME_LENGTH = 32;
	private static transient final int MIN_SURNAME_LENGTH = 1;
	private static transient final int MAX_SURNAME_LENGTH = 32;
	private static transient final int MIN_USERNAME_LENGTH = 1;
	private static transient final int MAX_USERNAME_LENGTH = 32;
	private static transient final int MIN_PASSWORD_LENGTH = 8;
	private static transient final int MAX_PASSWORD_LENGTH = 16;
	private static transient final int MIN_DESCRIPTION_LENGTH = 1;
	private static transient final int MAX_DESCRIPTION_LENGTH = 140;
	private static transient final int MIN_SECRET_QUESTION_LENGTH = 1;
	private static transient final int MAX_SECRET_QUESTION_LENGTH = 64;
	private static transient final int MIN_SECRET_ANSWER_LENGTH = 1;
	private static transient final int MAX_SECRET_ANSWER_LENGTH = 64;

	public RegistrationPage() {
		add(new FeedbackPanel("errorPanel"));
		Form<RegistrationPage> form = new Form<RegistrationPage>(
				"registrationForm",
				new CompoundPropertyModel<RegistrationPage>(this)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				if (checkFieldLength() && checkPasswords() && checkNotEmptyCaptcha()) {
					byte[] selectedPicture = getImageBytes(picture);
					String picture_extension = getImageExtension(picture);
					byte[] selectedBackground = getImageBytes(background);
					User newUser = new User(name, surname, username,
							description, password, selectedPicture, selectedBackground,
							selectedPicture, picture_extension, secretQuestion,
							secretAnswer, new Date(), false);
					users.registerUser(newUser);
					SocialCthulhuSession session = SocialCthulhuSession.get();
					session.signIn(username, password, users);
					continueToOriginalDestination();
					setResponsePage(getApplication().getHomePage());
				}
			}
			
			private boolean checkPasswords() {
				if (!password.equals(confirmPassword)) {
					error(getString("password_nonmatch"));
					return false;
				}
				return true;
			}
			
			private boolean checkNotEmptyCaptcha() {
				if (captchaInput == null || captchaInput == "") {
					error(getString("empty_captcha"));
					return false;
				}
				return true;
			}

			private boolean checkFieldLength() {
				if (name.length() < MIN_NAME_LENGTH
						|| name.length() > MAX_NAME_LENGTH) {
					error(getString("name_length"));
					return false;
				}
				if (surname.length() < MIN_SURNAME_LENGTH
						|| surname.length() > MAX_SURNAME_LENGTH) {
					error(getString("surname_length"));
					return false;
				}
				if (username.length() < MIN_USERNAME_LENGTH
						|| username.length() > MAX_USERNAME_LENGTH) {
					error(getString("username_length"));
					return false;
				}
				if (password.length() < MIN_PASSWORD_LENGTH
						|| password.length() > MAX_PASSWORD_LENGTH) {
					error(getString("password_length"));
					return false;
				}
				if (confirmPassword.length() < MIN_PASSWORD_LENGTH
						|| confirmPassword.length() > MAX_PASSWORD_LENGTH) {
					error(getString("confirmPassword_length"));
					return false;
				}
				if (description.length() < MIN_DESCRIPTION_LENGTH
						|| description.length() > MAX_DESCRIPTION_LENGTH) {
					error(getString("description_length"));
					return false;
				}
				if (secretQuestion.length() < MIN_SECRET_QUESTION_LENGTH
						|| secretQuestion.length() > MAX_SECRET_QUESTION_LENGTH) {
					error(getString("secretQuestion_length"));
					return false;
				}
				if (secretAnswer.length() < MIN_SECRET_ANSWER_LENGTH
						|| secretAnswer.length() > MAX_SECRET_ANSWER_LENGTH) {
					error(getString("secretAnswer_length"));
					return false;
				}
				return true;
			}
		};

		final CaptchaImage captchaImage = new CaptchaImage("kaptchaImage");
		captchaImage.setOutputMarkupId(true);

		TextField<String> captchaTF = new TextField<String>("captcha",
				new PropertyModel<String>(this, "captchaInput"));
		captchaTF.add(new CaptchaValidator());

		form.setMultiPart(true);
		form.add(new TextField<String>("name").setRequired(true));
		form.add(new TextField<String>("surname").setRequired(true));
		form.add(new TextField<String>("username").setRequired(true));
		form.add(new PasswordTextField("password"));
		form.add(new PasswordTextField("confirmPassword"));
		form.add(new TextField<String>("description").setRequired(true));
		form.add(new TextField<String>("secretQuestion").setRequired(true));
		form.add(new TextField<String>("secretAnswer").setRequired(true));
		form.add(new BookmarkablePageLink<Void>("alreadyRegistered",
				LoginPage.class));
		form.add(new FileUploadField("picture"));
		form.add(new FileUploadField("background"));
		form.add(new Button("register", new ResourceModel("register")));
		form.add(captchaImage);
		form.add(captchaTF.setRequired(true));
		add(form);
	}
	
	private byte[] getImageBytes(List<FileUpload> picture) {
		byte[] selectedPicture = null;
		if (picture != null) {
			selectedPicture = picture.get(0).getBytes();
		}
		return selectedPicture;
	}
	
	private String getImageExtension(List<FileUpload> picture) {
		String picture_extension = "png";
		if (picture != null) {
			picture_extension = picture
					.get(0)
					.getClientFileName()
					.substring(
							picture.get(0).getClientFileName()
							.lastIndexOf(".")).substring(1);
		}		
		return picture_extension;
	}
}
