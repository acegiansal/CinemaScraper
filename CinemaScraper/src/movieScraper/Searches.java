/*
 * Searches.java
 * Searches array for a certain element
 * PPP (Gian S, Morgan S, Ben V.)
 * ICS4U
 * May 8, 2019
 */
package movieScraper;

import java.util.ArrayList;

public class Searches {

	
	/**
	 * Searches items array for goal pre: items is sorted from low to high post:
	 * Position of goal has been returned, or -1 has been returned if goal not
	 * found.
	 */
	public static int binarySearch(ArrayList<Movie> items, int start, int end, Movie goal) {
		if (start > end) {
			//ParseClass.print("Returning not found!");
			return (-1);
		} else {
			int mid;
			
			//The first index that is searched depends on the percent value
			if(start == 0) {
				// Percent value is found by taking the highest value of the array and dividing the goal element by that value
				float percentValue = ((float)goal.getMovieValue() / (float)(items.get(items.size()-1).getMovieValue()));
				//Finds the total size of the array and multiplies it by the percent value to get the index of first search
				mid = (int)((start + end) * percentValue);
			} else {
				mid = (start + end) / 2;
			}

			if (goal.equals(items.get(mid))) {
				return (mid);
			} else if (goal.compareTo(items.get(mid)) < 0) {
				return (binarySearch(items, start, mid - 1, goal));
			} else {
				return (binarySearch(items, mid + 1, end, goal));
			}
		}
	}
}
