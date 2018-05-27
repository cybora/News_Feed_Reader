package com.newsfeeder.services;

import java.io.File;

public interface ImageService {
	
	/**
	 * @param imageURL
	 * @return local path of the image
	 */
	public String saveImage(String imageURL);	
	
	/**
	 * @param local path of the image
	 */
	public void deleteImage(String imagePath);
	
	/**
	 * @param file
	 * @return base64 binary form of the image
	 */
	public String encodeFileToBase64Binary(File file);

}
