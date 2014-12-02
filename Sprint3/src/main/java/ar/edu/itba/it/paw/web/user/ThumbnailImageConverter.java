package ar.edu.itba.it.paw.web.user;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

public class ThumbnailImageConverter {

	public static byte[] thumbnailPicture(byte[] original_picture,
			String image_extension) {
		try {
			byte[] user_picture;
			user_picture = original_picture != null ? original_picture
					: default_picture();
			InputStream in = new ByteArrayInputStream(user_picture);
			BufferedImage bImageFromConvert;
			bImageFromConvert = null;
			try {
				bImageFromConvert = ImageIO.read(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
			BufferedImage thumbImg = Scalr.resize(bImageFromConvert, 32, 32);
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			try {
				ImageIO.write(thumbImg, image_extension, os);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return os.toByteArray();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	private static byte[] default_picture() throws IOException {
		File file = new File(
				"src/main/resources/ar/edu/itba/it/paw/web/resources/default_picture.png");
		BufferedImage originalImage = ImageIO.read(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, "png", baos);
		return baos.toByteArray();
	}
}
