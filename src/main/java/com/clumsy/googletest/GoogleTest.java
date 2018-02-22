package com.clumsy.googletest;

import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

import org.bytedeco.javacpp.opencv_core.IplImage;

public class GoogleTest {

	public static void main(String[] args) {
		
		final String inFile = args[0];
		final String outFile = args[1];
		
		IplImage image = cvLoadImage(inFile);
        if (image != null) {
            cvSmooth(image, image);
            cvSaveImage(outFile, image);
            cvReleaseImage(image);
        }
        
		/**
		
		// Prepare image for processing
		try {
			ImageUtils.convertForOCR(inFile, outFile);
		} catch (ImageProcessingException e) {
			e.printStackTrace();
			System.exit(1);
		}
		// Analyse the image and display results
		try {
			List<String> inFiles = new ArrayList<String>();
			inFiles.add(outFile);
			List<ImageRecognitionResult> matchingGyms = ImageRecognitionUtils.getGyms(inFiles);
			ImageUtils.drawBounds(inFile, outFile, matchingGyms);
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
		} catch (InvalidBadgeListException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		**/
	}

}
