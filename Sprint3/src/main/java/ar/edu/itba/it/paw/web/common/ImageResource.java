package ar.edu.itba.it.paw.web.common;

import java.io.IOException;
import java.io.InputStream;

import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.io.IOUtils;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;

import ar.edu.itba.it.paw.web.SocialCthulhuApp;

public class ImageResource extends DynamicImageResource {
	
	private byte[] img;

	public ImageResource(byte[] img) {
		if (img == null) {
			try {
				InputStream is = ((PackageResourceReference) SocialCthulhuApp.DEFAULT_IMAGE)
						.getResource().getResourceStream().getInputStream();
				this.img = IOUtils.toByteArray(is);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ResourceStreamNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			this.img = img;
		}
	}
	 
    @Override
    protected byte[] getImageData(Attributes attributes) {
        return img;
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof ImageResource;
    }
}
