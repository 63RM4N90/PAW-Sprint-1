package ar.edu.itba.it.paw.domain;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.wicket.request.resource.ResourceReference;

import ar.edu.itba.it.paw.web.SocialCthulhuApp;
import ar.edu.itba.it.paw.web.common.ImageResourceReference;

public class RenderedPublicity {

	private String _client_name;
	private byte[] _image;
	private String _redirection_url;

	public RenderedPublicity(Publicity publicity) {
		_client_name = publicity.get_client_name();
		_image = fetch_image(publicity.get_image_url());
		_redirection_url = publicity.get_redirection_url();
	}

	public String get_client_name() {
		return _client_name;
	}

	public byte[] get_image() {
		return _image;
	}

	public String get_redirection_url() {
		return _redirection_url;
	}

	private byte[] fetch_image(String image_url) {
		byte[] ans = null;
		try {
			URL imageURL = new URL(image_url);
			BufferedImage originalImage = ImageIO.read(imageURL);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			String image_extension = image_url.substring(image_url
					.lastIndexOf("."));
			image_extension = image_extension.substring(1);
			ImageIO.write(originalImage, image_extension, baos);
			ans = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ans;
	}
	
	public ResourceReference fetch_resource_reference() {
		if (_image != null) {
			return new ImageResourceReference(_image);
		} else {
			return SocialCthulhuApp.DEFAULT_IMAGE;
		}
	}
}
