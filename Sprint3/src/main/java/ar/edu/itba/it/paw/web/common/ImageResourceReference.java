package ar.edu.itba.it.paw.web.common;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

@SuppressWarnings("serial")
public class ImageResourceReference extends ResourceReference{
	
	private byte[] img;
	
	 public ImageResourceReference(byte[] img) {
	        super(ImageResourceReference.class, "Social_cthulhus_horocrux_" + System.currentTimeMillis());
	        this.img = img;
	    }

	    @Override
	    public IResource getResource() {
	        return new ImageResource(img);
	    }

}
