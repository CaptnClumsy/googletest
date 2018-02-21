package com.clumsy.googletest;

import java.util.ArrayList;
import java.util.List;

public class GoogleTest {

	//private static final String DEFAULT_FILE_PATH = "d:\\projects\\ScanBadge\\images\\IMG_5665.PNG";
	private static final String DEFAULT_FILE_PATH = "d:\\projects\\ScanBadge\\images\\James1.jpg";
	//private static final String DEFAULT_FILE_PATH = "C:\\Users\\petray\\git\\ScanBadge\\images\\IMG_5665.PNG";
	//private static final String DEFAULT_OUTPUT_FILE_PATH = "c:\\Users\\petray\\Desktop\\newimage.png";
	private static final String DEFAULT_OUTPUT_FILE_PATH = "c:\\Users\\praymond\\Desktop\\newimage.png";
	
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
			List<ImageRecognitionResult> matchingGyms = ImageRecognitionUtils.getGyms(inFiles);
			for (ImageRecognitionResult result : matchingGyms) {
				System.out.println("FOUND GYM AT: "+result.getBounds().toString());
				if (result.getGymNames()!=null && result.getGymNames().size()>0) {
					for (int i=0; i<result.getGymNames().size(); i++) {
						String gym = result.getGymNames().get(i);
						if (i==0) {
							System.out.println("* " + gym);
						} else {
							System.out.println("  " + gym);
						}
					}
					System.out.println();
				}
			}
		} catch (ImageProcessingException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
