package ar.edu.itba.it.paw.web.user;

import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.lang.Bytes;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.SecuredPage;
import ar.edu.itba.it.paw.web.common.DescriptionValidator;
import ar.edu.itba.it.paw.web.common.NameValidator;
import ar.edu.itba.it.paw.web.common.PasswordValidator;
import ar.edu.itba.it.paw.web.common.SurnameValidator;

public class EditProfilePage extends SecuredPage {

	private static final long serialVersionUID = 1L;
	private List<FileUpload> fileUpload;
	private List<FileUpload> fileUploadBackground;

	public EditProfilePage(User user) {
		add(new FeedbackPanel("errorPanel"));
		Form<User> form = new Form<User>("editProfileForm",
				new CompoundPropertyModel<User>(new EntityModel<User>(
						User.class, user)));
		form.setMultiPart(true);
		form.add(new Button("submit", new ResourceModel("submit")) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				User user = (User) getForm().getModelObject();
				if (fileUpload != null) {
					user.setPicture(fileUpload.get(0).getBytes());
					String file_name = fileUpload.get(0).getClientFileName();
					String image_extension = file_name.substring(file_name
							.lastIndexOf("."));
					image_extension = image_extension.substring(1);
					user.setPictureExtension(image_extension);
					user.setThumbnailPicture(ThumbnailImageConverter
							.thumbnailPicture(fileUpload.get(0).getBytes(),
									image_extension));
				}
				if (fileUploadBackground != null) {
					user.setBackground(fileUploadBackground.get(0).getBytes());
				}
				setResponsePage(new ProfilePage(new PageParameters().set(
						"username", SocialCthulhuSession.get().getUsername())));
			}
		});

		TextField<String> nameField = new TextField<String>("name");
		nameField.setRequired(true);
		nameField.add(new NameValidator());
		form.add(nameField);

		TextField<String> surnameField = new TextField<String>("surname");
		surnameField.setRequired(true);
		surnameField.add(new SurnameValidator());
		form.add(surnameField);

		PasswordTextField passwordField = new PasswordTextField("password");
		passwordField.setRequired(true);
		passwordField.add(new PasswordValidator());
		form.add(passwordField);

		PasswordTextField confirmPasswordField = new PasswordTextField(
				"confirmPassword");
		confirmPasswordField.setRequired(true);
		confirmPasswordField.add(new PasswordValidator());
		form.add(confirmPasswordField);

		TextField<String> descriptionField = new TextField<String>(
				"description");
		descriptionField.setRequired(true);
		descriptionField.add(new DescriptionValidator());
		form.add(descriptionField);

		form.setMaxSize(Bytes.kilobytes(1024));

		FileUploadField fileUploadField = new FileUploadField("picture",
				new PropertyModel<List<FileUpload>>(EditProfilePage.this,
						"fileUpload"));
		form.add(fileUploadField);
		FileUploadField fileUploadFieldBackground = new FileUploadField(
				"background", new PropertyModel<List<FileUpload>>(
						EditProfilePage.this, "fileUploadBackground"));
		form.add(fileUploadFieldBackground);
		form.add(new CheckBox("isPrivate"));
		form.add(new EqualPasswordInputValidator(passwordField,
				confirmPasswordField));
		add(form);
	}
}
