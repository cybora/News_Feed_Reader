package com.newsfeeder.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import com.newsfeeder.util.NewsContants;

@Service
public class ImageServiceImpl implements ImageService {
	
	private static final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

	public ImageServiceImpl() {

	}
	

	public String saveImage(String imageURL) {

		String filePath = new File(NewsContants.IMAGE_PATH).getAbsolutePath() + "/";
		String fileName = imageURL.replaceAll("/.", ""); // replacing the problematic characters for path building
		fileName = fileName.replaceAll("//", "");
		fileName = fileName.replaceAll("/?", "");
		String fileLocation = filePath + fileName; // create path + filename for the full path

		// This will get input data from the server
		InputStream inputStream = null;
		// This will read the data from the server;
		OutputStream outputStream = null;

		try {
			// This will open a socket from client to server
			URL url = new URL(imageURL);
			// This socket type will allow to set user_agent
			URLConnection con = url.openConnection();
			// Setting the user agent
			con.setRequestProperty("User-Agent", NewsContants.USER_AGENT);
			// Requesting input data from server
			inputStream = con.getInputStream();
			// Open local file writer
			outputStream = new FileOutputStream(fileLocation);
			// Limiting byte written to file per loop
			byte[] buffer = new byte[10000];
			// Increments file size
			int length;
			// Looping until server finishes
			while ((length = inputStream.read(buffer)) != -1) {
				// Writing data
				outputStream.write(buffer, 0, length);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			outputStream.close();
			inputStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return fileLocation;
	}
	
	

	public String encodeFileToBase64Binary(File file) {
		String encodedfile = null;
		try {
			@SuppressWarnings("resource")
			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			fileInputStreamReader.read(bytes);
			encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return encodedfile;
	}

	public void deleteImage(String imagePath) {
		try {

			File file = new File(imagePath);

			if (file.delete()) {
				log.info(file.getName() + " is deleted!");
			} else {
				log.info("Delete operation is failed for: " + file.getName());
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
