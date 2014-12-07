package ar.edu.itba.it.paw.web.common;

import java.util.Random;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

@SuppressWarnings("serial")
public class ImageResourceReference extends ResourceReference {

	private byte[] img;

	public ImageResourceReference(byte[] img, String suffix) {
		super(ImageResourceReference.class, "Social_cthulhus_horocrux_"
				+ new Random().nextLong() + suffix);
		this.img = img;
	}

	@Override
	public IResource getResource() {
		return new ImageResource(img);
	}

}
