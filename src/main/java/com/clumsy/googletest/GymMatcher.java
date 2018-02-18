package com.clumsy.googletest;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class GymMatcher {

	public static String getBestMatch(final String scannedText) {
		// Try to find an exact match
		for (String gym : GymList.ALL_STALBANS_GYMS) {
			if (gym.equalsIgnoreCase(scannedText)) {
				return gym;
			}
		}
		// No exact match so start guessing...
		LevenshteinDistance d = new LevenshteinDistance();
		String bestMatch = "";
		double lowestDifference = 50.0;
		for (String gym : GymList.ALL_STALBANS_GYMS) {
			int longest = Math.max(scannedText.length(), gym.length());
			int distance = d.apply(scannedText, gym);
			double percentdiff = ((double)distance/(double)longest)*100;
			if (percentdiff<=lowestDifference) {
				lowestDifference=percentdiff;
				bestMatch = gym;
			}
		}
		if (bestMatch.length()!=0) {
			return bestMatch;
		}
		return null;
	}
}
