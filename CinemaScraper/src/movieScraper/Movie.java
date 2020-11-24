/*
 * Movie.java
 * Creates movie object
 * PPP (Gian S, Morgan S, Ben V.)
 * ICS4U
 * May 20, 2019
 */
package movieScraper;

public class Movie implements Comparable<Movie> {

	private String name;
	private String timeScheduled;
	private int minutesScheduled;
	private String timeDesired;
	private int minutesDesired;
	private int movieValue;
	private int recliner;
	private String cinema;
	
	/**
	 * pre: none
	 * post: New movie is created
	 * @param nm Name of movie
	 * @param tmS Time that the movie is scheduled to start
	 * @param tmD Time that the user desires
	 * @param Cinema Location of movie
	 * @param Recliner Recliner multiplier (1 if no, 2 if yes)
	 */
	public Movie(String nm, String tmS, String tmD, String Cinema,int Recliner) {
		name = nm;
		timeScheduled = tmS;
		timeDesired = tmD;
		recliner = Recliner;
		cinema = Cinema;
		convertTime();
		calculateMovieValue();
	}
	
	/**
	 * pre: none
	 * post: Movie for interpolation search is created
	 * @param value The movie value of the desired movie
	 * @param Cinema The location of the cinema
	 */
	public Movie(int value, String Cinema) {
		movieValue = value;
		cinema = Cinema;
	}
	
	/**
	 * Compares location and movie value
	 * pre: none
	 * post: Returns if one movie is equal to the other
	 * @param other The other movie
	 * @return true if they are equal, false if not
	 */
	public boolean equals(Movie other) {
		if(movieValue == other.getMovieValue() && cinema.equals(other.getCinema())) {
			return true;
		}
		return false;
	}
	
	/**
	 * pre: Time desired and time scheduled found
	 * post: Changes the time into minutes to allow for comparing to be easier
	 */
	public void convertTime() {
		if(timeScheduled.length() != 5) {
			timeScheduled = "0" + timeScheduled;
		}
		
		int hoursTemp = Integer.parseInt(timeScheduled.substring(0, 2));
		int minutesTemp = Integer.parseInt(timeScheduled.substring(3,5));
		minutesScheduled = (hoursTemp * 60) + minutesTemp;
		
		hoursTemp = Integer.parseInt(timeDesired.substring(0, 2));
		minutesTemp = Integer.parseInt(timeDesired.substring(3,5));
		minutesDesired = (hoursTemp * 60) + minutesTemp;
	}
	
	/**
	 * pre: Movie information has been found
	 * post: movieValue of the movie is found
	 */
	public void calculateMovieValue() {
		movieValue = (int)(100 * recliner - (0.25*(Math.abs(minutesDesired - minutesScheduled))));
	}
	
	/**
	 * pre: movie value is found
	 * post: movie value is returned
	 * @return
	 */
	public int getMovieValue() {
		return movieValue;
	}
	
	/**
	 * pre: Cinema is found
	 * post: Cinema is returned
	 * @return
	 */
	public String getCinema() {
		return cinema;
	}
	
	/**
	 * pre: Title of the movie is found
	 * post: Title of the movie is returned
	 * @return
	 */
	public String getTitle() {
		return name;
	}
	
	/**
	 * pre: Recliner value is found
	 * post: If movie has a recliner is stated in a string and returned
	 * @return
	 */
	public String getRecliner() {
		if(recliner == 2) {
			return "Yes";			
		}
		else {
			return "No";
		}
	}
	
	/**
	 * pre: 2 movies exist post: the 2 movies are compared
	 * 
	 * @param other is the movie that this current object will be compared to
	 * @return 1 if the original is of more value, -1 if lower and 0 if they are the same
	 */
	@Override
	public int compareTo(Movie other) {

		if (movieValue > other.getMovieValue()) {
			return 1;
		} else if (movieValue < other.getMovieValue()) {
			return -1;
		} else {
			return 0;
		}
	}
	
	/**
	 * pre: All information is found
	 * post: All information is displayed
	 * @return
	 */
	public String getInfo() {
		String text;
		text = "Title: " + name;
		text += "\nLocation: " + cinema;
		text += "\nTime: " + timeScheduled;
		text += "\nRecliner Seats: " + getRecliner();
		text += "\nMovie Value: " + movieValue;
		
		return text;
	}

	/**
	 * pre: none
	 * post: none
	 * Displays info to console
	 */
	public String toString() {
		return getInfo();
	}
}
