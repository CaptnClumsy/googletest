package com.clumsy.googletest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class GymMatcher {

	public static List<String> getBestMatches(final String scannedText) {
		List<String> matches = new ArrayList<String>();
		// Try to find an exact match
		for (String gym : GymList.ALL_STALBANS_GYMS) {
			if (gym.equalsIgnoreCase(scannedText)) {
				matches.add(gym);
			}
		}
		// If we found ANY exact matches return them now
		if (matches.size()>0) {
			return matches;
		}
		// No exact match so start guessing...
		LevenshteinDistance d = new LevenshteinDistance();
		String bestMatch = "";
		double lowestDifference = 50.0;
		for (String gym : GymList.ALL_STALBANS_GYMS) {
			int longest = Math.max(scannedText.length(), gym.length());
			int distance = d.apply(scannedText, gym);
			double percentdiff = ((double)distance/(double)longest)*100;
			if (percentdiff <= lowestDifference) {
				lowestDifference = percentdiff;
				bestMatch = gym;
				matches.add(bestMatch);
			}
		}
		if (bestMatch.length() != 0) {
			// Ensure the best match comes first
			matches.remove(bestMatch);
			matches.add(0, bestMatch);
		}
		return matches;
	}
}
