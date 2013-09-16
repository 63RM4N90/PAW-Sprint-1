package controller;

import javax.print.DocFlavor.BYTE_ARRAY;

import sun.awt.image.ByteArrayImageSource;

public class Image {
	private String path;
	private ByteArrayImageSource data;
	
	public Image(String path, ByteArrayImageSource data) {
		this.path = path;
		this.data = data;
	}
	
	public ByteArrayImageSource getData() {
		return data;
	}
	
	public String getPath() {
		return path;
	}
	
	
}
