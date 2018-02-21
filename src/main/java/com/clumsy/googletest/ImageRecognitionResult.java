package com.clumsy.googletest;

import java.awt.Rectangle;
import java.util.List;

import lombok.Data;

@Data
public class ImageRecognitionResult {
	private List<String> gymNames;
	private Rectangle bounds;
	
	public ImageRecognitionResult(final List<String> gymNames, final Rectangle bounds) {
		this.gymNames=gymNames;
		this.bounds=bounds;
	}
}
