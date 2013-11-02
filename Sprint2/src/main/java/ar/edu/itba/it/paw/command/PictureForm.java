package ar.edu.itba.it.paw.command;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class PictureForm {

	private CommonsMultipartFile picture;
    
    public CommonsMultipartFile getPicture() {
            return picture;
    }
    
    public void setFile(CommonsMultipartFile picture) {
            this.picture = picture;
    }
}
