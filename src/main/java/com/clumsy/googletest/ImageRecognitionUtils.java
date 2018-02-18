package com.clumsy.googletest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Block;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.Page;
import com.google.cloud.vision.v1.Paragraph;
import com.google.cloud.vision.v1.Symbol;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.cloud.vision.v1.Word;
import com.google.protobuf.ByteString;

public class ImageRecognitionUtils {

	public static List<String> getGyms(final List<String> inputFiles) throws ImageProcessingException { 
		// Setup the requests to send to google vision
		final List<AnnotateImageRequest> requests = new ArrayList<>();
		try {
			for (String inFile : inputFiles) {
				final ByteString imgBytes = ByteString.readFrom(new FileInputStream(inFile));
				final Image img = Image.newBuilder().setContent(imgBytes).build();
				final Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
				final AnnotateImageRequest request =
						AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
				requests.add(request);
			}
		} catch (IOException e) {
			throw new ImageProcessingException("Unable to read temporary file on server: "+e);
		}
		
		try {
			// Send data to google vision and get response
			final ImageAnnotatorClient client = ImageAnnotatorClient.create();
			final BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
		    final List<AnnotateImageResponse> responses = response.getResponsesList();
		    client.close();
		    // Process the response for each image
		    final List<String> matchingGyms = new ArrayList<String>();
		    for (AnnotateImageResponse res : responses) {
		        if (res.hasError()) {
			        System.err.println("Error: "+res.getError().getMessage());
			        continue;
			    }
		        // Walk the elements of this images page and find the blocks of text
		        TextAnnotation annotation = res.getFullTextAnnotation();
		        for (Page page: annotation.getPagesList()) {
		        	LogFile.log(page.toString());
		            for (Block block : page.getBlocksList()) {
		                String blockText = "";
		                for (Paragraph para : block.getParagraphsList()) {
		                    String paraText = "";
		                    for (Word word: para.getWordsList()) {
		                        String wordText = "";
		                        for (Symbol symbol: word.getSymbolsList()) {
		                            wordText = wordText + symbol.getText();
		                        }
		                        if (!wordText.equals("'") && paraText.length()!=0) {
		                        	paraText = paraText + " " + wordText;
		                        } else {
		                        	paraText = paraText + wordText;
		                        }
		                    }
		                    blockText = blockText + paraText;
		                }
		                //System.out.println("Block: " + blockText);
		                final String matching = GymMatcher.getBestMatch(blockText);
		                //System.out.println("Found: "+matching);
		                if (matching!=null) {
		                	matchingGyms.add(matching);
		                }
		            }
		        }
		        LogFile.close();
		    } 
		    return matchingGyms;
		 } catch (Exception e) {
			 throw new ImageProcessingException("Unable to analyze image: "+e);
		}
	}
	
}
