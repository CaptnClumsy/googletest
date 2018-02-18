package com.clumsy.googletest;

import java.util.ArrayList;
import java.util.List;

public class GoogleTest {

	//private static final String DEFAULT_FILE_PATH = "d:\\projects\\ScanBadge\\images\\IMG_5665.PNG";
	private static final String DEFAULT_FILE_PATH = "C:\\Users\\petray\\git\\ScanBadge\\images\\IMG_5665.PNG";
	private static final String DEFAULT_OUTPUT_FILE_PATH = "c:\\Users\\petray\\Desktop\\newimage.png";
	
	public static void main(String[] args) {
		// Prepare image for processing
		try {
			ImageUtils.convertForOCR(DEFAULT_FILE_PATH, DEFAULT_OUTPUT_FILE_PATH);
		} catch (ImageProcessingException e) {
			e.printStackTrace();
			System.exit(1);
		}
		// Analyse the image and display results
		try {
			List<String> inFiles = new ArrayList<String>();
			inFiles.add(DEFAULT_OUTPUT_FILE_PATH);
			List<String> gyms = ImageRecognitionUtils.getGyms(inFiles);
			for (String gym : gyms) {
				System.out.println(gym);
			}
		} catch (ImageProcessingException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
