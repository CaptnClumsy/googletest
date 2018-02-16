package com.clumsy.googletest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Block;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.Page;
import com.google.cloud.vision.v1.Paragraph;
import com.google.cloud.vision.v1.Symbol;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.cloud.vision.v1.Word;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.protobuf.ByteString;

public class GoogleTest {

	private static final String DEFAULT_FILE_PATH = "d:\\projects\\ScanBadge\\images\\IMG_5665.PNG";
	
	public static void main(String[] args) {
		// Convert image to greyscale?
		/** 
		 BufferedImage image = new BufferedImage(width, height,  
		    BufferedImage.TYPE_BYTE_GRAY);  
			Graphics g = image.getGraphics();  
			g.drawImage(colorImage, 0, 0, null);  
			g.dispose();  
		**/
		
		final List<AnnotateImageRequest> requests = new ArrayList<>();
		try {
			final ByteString imgBytes = ByteString.readFrom(new FileInputStream(DEFAULT_FILE_PATH));
			final Image img = Image.newBuilder().setContent(imgBytes).build();
			final Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
			final AnnotateImageRequest request =
			    AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			requests.add(request);
		} catch (IOException e) {
			System.err.println("Unable to read temporary file on server: "+e);
			return;
		}
		try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
		    final BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
		    final List<AnnotateImageResponse> responses = response.getResponsesList();
		    client.close();
		    for (AnnotateImageResponse res : responses) {
		        if (res.hasError()) {
			        System.err.println("Error: "+res.getError().getMessage());
			        continue;
			    }
		        File f = new File("c:\\temp\\log.txt");
		        FileOutputStream fos = new FileOutputStream(f);
		        TextAnnotation annotation = res.getFullTextAnnotation();
		        for (Page page: annotation.getPagesList()) {
		        	fos.write(page.toString().getBytes(), 0, page.toString().getBytes().length);
		            String pageText = "";
		            for (Block block : page.getBlocksList()) {
		                String blockText = "";
		                for (Paragraph para : block.getParagraphsList()) {
		                    String paraText = "";
		                    for (Word word: para.getWordsList()) {
		                        String wordText = "";
		                        for (Symbol symbol: word.getSymbolsList()) {
		                            wordText = wordText + symbol.getText();
		                        }
		                        if (!wordText.equals("'")) {
		                        	paraText = paraText + " " + wordText;
		                        } else {
		                        	paraText = paraText + wordText;
		                        }
		                    }
		                    blockText = blockText + paraText;
		                }
		                System.out.println("Block: " + blockText);
		                List<String> matching = GymMatcher.getMatches(blockText);
		                for (String match: matching) {
		                	System.out.println(match);
		                }
		                pageText = pageText + blockText;
		            }
		        }
		        fos.close();
		    } 
		 } catch (IOException e) {
			 System.err.println("Unable to process temporary file on server: "+e);
			 return;
		 } catch (Exception e) {
			 System.err.println("Unable to analyze image: "+e);
			 return;
		}
	}

}
