package ar.edu.itba.it.paw.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Publicity extends PersistentEntity {

	@Column(nullable = false)
	private String _client_name;
	@Column(nullable = false)
	private String _image_url; 
	@Column(nullable = false)
	private String _redirection_url;
	
	public Publicity() {
	}
	
	public Publicity(String client_name, String image_url, String redirection_url) {
		_client_name = client_name;
		_image_url = image_url;
		_redirection_url = redirection_url;
	}

	public String get_client_name() {
		return _client_name;
	}

	public String get_image_url() {
		return _image_url;
	}

	public String get_redirection_url() {
		return _redirection_url;
	}	
}
