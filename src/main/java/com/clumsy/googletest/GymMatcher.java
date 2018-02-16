package com.clumsy.googletest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class GymMatcher {

	public static List<String> getMatches(final String scannedText) {
		List<String> matches = new ArrayList<String>();
		LevenshteinDistance d = new LevenshteinDistance();
		for (String gym : GymList.ALL_STALBANS_GYMS) {
			if (gym.equalsIgnoreCase(scannedText)) {
				matches.add(gym);
				break;
			}
			int longest = Math.max(scannedText.length(), gym.length());
			int distance = d.apply(scannedText, gym);
			double percentdiff = ((double)distance/(double)longest)*100;
			if (percentdiff<=50) {
				matches.add(gym);
			}
		}
		return matches;
	}
}
