package com.clumsy.googletest;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {

	private static BufferedImage convertToGreyScale(final String inFile) throws ImageProcessingException {
	    // Load the original image
		BufferedImage originalImg = null;
		try {
			originalImg = ImageIO.read(new File(inFile));
		} catch (IOException e) {
			throw new ImageProcessingException("Failed to read image file: "+e.getMessage());
		}
		// Convert image to greyscale?
		BufferedImage image = new BufferedImage(originalImg.getWidth(), originalImg.getHeight(),  
			BufferedImage.TYPE_BYTE_GRAY);  
		Graphics2D g = image.createGraphics();  
		g.drawImage(originalImg, 0, 0, null);
		// draw dividing lines to help OCR
		g.setStroke(new BasicStroke(2f));
        g.setColor(Color.BLACK);
        g.draw(new Line2D.Double(originalImg.getWidth()/3, 0, originalImg.getWidth()/3, originalImg.getHeight()));
        g.draw(new Line2D.Double((originalImg.getWidth()/3)*2, 0, (originalImg.getWidth()/3)*2, originalImg.getHeight()));
		g.dispose();
		return image;
	}
	
	public static void convertForOCR(final String inFile, final String outFile) throws ImageProcessingException {
		BufferedImage image = convertToGreyScale(inFile);
		try {
		    File outputfile = new File(outFile);
		    ImageIO.write(image, "png", outputfile);
		} catch (IOException ex) {
			throw new ImageProcessingException("Failed to write image file: "+ex.getMessage());
	    }
	}
		    
}
