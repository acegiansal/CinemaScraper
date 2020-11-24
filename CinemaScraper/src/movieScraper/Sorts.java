/*
 * Sorts.java
 * Sorts a double array list by numerical order
 * PPP (Gian S, Morgan S, Ben V.)
 * ICS4U
 * May 7, 2019
 */
package movieScraper;

import java.util.ArrayList;

public class Sorts {
	/**
	 * Merges two sorted portion of items array pre: items[start .. mid] is sorted.
	 * items[mid+1 .. end] sorted. start <= mid <= end post: items[start .. end] is
	 * sorted.
	 */
	private static void merge(ArrayList<Movie> items, int start, int mid, int end) {
		Movie[] temp = new Movie[items.size()];
		int pos1 = start;
		int pos2 = mid + 1;
		int spot = start;

		while (!(pos1 > mid && pos2 > end)) {
			if ((pos1 > mid) || ((pos2 <= end) && items.get(pos2).compareTo(items.get(pos1)) < 0)) {
				temp[spot] = items.get(pos2);
				pos2 += 1;
			} else {
				temp[spot] = items.get(pos1);
				pos1 += 1;
			}
			spot += 1;
		}

		/* copy values from temp back to items */
		for (int i = start; i <= end; i++) {
			items.set(i, temp[i]);
		}
	}

	/**
	 * Sorts items [start .. end] pre: start > 0, end > 0 post: items[start .. end]
	 * is sorted low to high
	 */

	public static void mergesort(ArrayList<Movie> items, int start, int end) {
		if (start < end) {
			int mid = (start + end) / 2;
			mergesort(items, start, mid);
			mergesort(items, mid + 1, end);
			merge(items, start, mid, end);
		}
	}
}


