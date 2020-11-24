/*
 * ParseClass.java
 * Parses through a set of websites to get info about a movie
 * PPP (Gian S, Morgan S, Ben V.)
 * ICS4U
 * May 20, 2019
 */
package movieScraper;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import java.net.URL;
import java.net.URLConnection;
import java.io.*;

public class ParseClass implements Runnable {

	private String action;
	private int theaterAmount;
	static private String title;
	static String timeDesired = "05:00";
	private String theater;
	private int movieVal;

	// Needed for reading from a website and grabbing info
	static File website = new File("webPage.txt");
	static InputStream is;
	static BufferedReader readFile;
	//static String title = "John Wick";
	//static String timeDesired = "05:00";
	static int testCounter = 0;
	static ArrayList<Movie> movieList = new ArrayList<Movie>();

	// Important for theater queue
	static final String[] theaterLink = new String[] {
			"https://www.cineplex.com/Showtimes/any-movie/cineplex-cinemas-ottawa?Date=5/22/2019",
			"https://www.cineplex.com/Showtimes/any-movie/cineplex-cinemas-lansdowne-and-vip?Date=5/22/2019",
			"https://www.cineplex.com/Showtimes/any-movie/cineplex-odeon-south-keys-cinemas",
			"https://www.cineplex.com/Showtimes/any-movie/cineplex-odeon-barrhaven-cinemas?Date=5/22/2019",
			"https://www.cineplex.com/Showtimes/any-movie/cinema-starcite-gatineau?" };
	//Names of theaters
	static final String[] theaterNames = new String[] { "Cineplex Ottawa", "Cineplex Lansdowne and VIP",
			"Cineplex Odeon South Keys", "Cineplex Odean Barrhaven", "Cinema Starcite Gatineau" };
	static int nameIndex;
	static Queue<String> cinemasToUse;

	/**
	 * Constructor for searching for all movies
	 * pre: none
	 * post: none
	 * @param action
	 * @param theaterNum
	 */
	public ParseClass(String action, int theaterNum, String movie, String time) {
		this.action = action;
		theaterAmount = theaterNum;
		title = movie;
		timeDesired = time;
		
	}
	
	/**
	 * Constructor for limited Display
	 * pre: none
	 * post: none
	 * @param action
	 */
	public ParseClass(String action) {
		this.action = action;
	}

	/**
	 * Constructor for finding the theater
	 * pre: none
	 * post: none
	 * @param action
	 * @param theater
	 * @param val
	 */
	public ParseClass(String action, String theater, int val) {
		this.theater = theater;
		this.action = action;
		movieVal = val;
	}

	/**
	 * pre: User inputs number of cinemas desired
	 * post: A queue is made with that number of cinemas
	 */
	public void theaterQueue() {
		cinemasToUse = new Queue<String>();
		for (int i = 0; i < theaterAmount; i++) {
			// print("Adding cinema: " + theaterLink[i]);
			cinemasToUse.enqueue(theaterLink[i]);
		}
	}

	/**
	 * pre: theaterQueue() has run
	 * post: Initiates search for that movie on certain websites
	 * @throws IOException If user is not online
	 */
	public void startSearch() throws IOException {
		theaterQueue();

		int loopNum = cinemasToUse.size();
		for (int s = 0; s < loopNum; s++) {
			String webAddress = (String) cinemasToUse.dequeue();
			URL url = new URL(webAddress);
			URLConnection con = url.openConnection();
			is = con.getInputStream();
			readFile = new BufferedReader(new InputStreamReader(is));
			String line = "";

			while (((line = readFile.readLine()) != null)) {
				if (line.contains(title)) {
					if (nameIndex <= 3) {
						findInfo(58, 1);
					} else {
						findInfo(57, 1);
					}

					nameIndex++;
					break;
				}
			}
		}
		Sorts.mergesort(movieList, 0, movieList.size() - 1);
		readFile.close();

	}

	/**
	 * Recursive depth-first-search method that finds all movie information on one website
	 * pre: Website title is found
	 * post: All information about the movie is found
	 * @param linesToSkip
	 * @param type
	 */
	public void findInfo(int linesToSkip, int type) {

		String line = "";
		String findThis = "content=\"";
		String time;
		int start;
		int end;
		boolean keepGoing = true;

		for (int i = 0; i < linesToSkip; i++) {
			try {
				if ((line = readFile.readLine()) != null) {

				} else {
					System.out.println("File is over after: " + i + " skips");
					i = linesToSkip;
					keepGoing = false;
					break;
				}
			} catch (Exception s) {
				System.out.println("Problem when skipping ahead...");
				System.err.println("Exception: " + s.getMessage());
			}
		}

		//Scrapes through html source code for information
		if (keepGoing) {
			if (type == 1 && line.contains("startDate")) {
				start = line.indexOf(findThis);
				end = line.indexOf(">");
				time = line.substring((start + findThis.length()), (end - 4));
				int recliner = 2;
				if (nameIndex > 0) {
					recliner = 1;
				}
				testCounter++;

				movieList.add(new Movie(title, time, timeDesired, theaterNames[nameIndex], recliner));
				findInfo(3, 2);
			} else if (type == 2) {
				if (line.contains("disabled")) {
					if (nameIndex <= 3) {
						findInfo(13, 1);
					} else {
						findInfo(10, 1);
					}

				} else {
					if (nameIndex <= 3) {
						findInfo(17, 1);
					} else {
						findInfo(13, 1);
					}
				}
			} else {
				// System.out.println("DEBUG***: Found all movie info!");
			}
		}

	}

	/**
	 * pre: Program has searched for movies
	 * post: Movie list has been returned
	 * @return
	 */
	public static ArrayList<Movie> getMovieList() {
		return movieList;
	}

	/**
	 * Debugging tool that prints certain information
	 * pre: none
	 * post: none
	 * @param text
	 */
	public static void print(String text) {
		System.out.println("DEBUG***: " + text);
	}

	@Override
	/**
	 * pre: Thread starts
	 * post: Certain action occurs
	 */
	public void run() {
		
		//Finds movie information from internet
		if (action.equals("Parse")) {
			try {
				startSearch();
			} catch (IOException s) {
				System.err.println("IOException: " + s.getMessage());
				JOptionPane.showMessageDialog(null, "Ensure you are connected to wifi!", "Can't connect", JOptionPane.ERROR_MESSAGE);
			}
			
			if (movieList.size() > 0) {
				FrameClass.recL.setText(movieList.size() + " movies found!");
				FrameClass.seeList.setEnabled(true);
				FrameClass.doSearch.setEnabled(true);
			} else {
				FrameClass.recL.setText("<html>Movie not found!<br>Please ensure movie is typed correctly</html>");

				try {
					Thread.sleep(1400);
				} catch (Exception s) {
					print("Problem sleeping");
				}
			}
		}

		//Displays a limited amount of information
		if (action.equals("limitedDisplay")) {
			int counter = 0;
			String text = "";
			for (Movie movie : movieList) {
				counter++;
				text += "\n\n" + counter + ". " + movie.getTitle() + " at " + movie.getCinema() + "\n--- Movie Value: "
						+ movie.getMovieValue();
			}
			FrameClass.info.setText(text);
		}

		//Uses interpolation searches to find specific movie
		if (action.equals("search")) {
			Movie goal = new Movie(movieVal, theater);
			int index = 0;
			if (movieVal <= 200) {
				index = Searches.binarySearch(movieList, 0, movieList.size() - 1, goal);
			} else {
				FrameClass.specificInfo.setText("Invalid Input!");
			}
			if (index != -1) {
				FrameClass.specificInfo.setText(movieList.get(index).getInfo());
			} else {
				FrameClass.specificInfo.setText("Movie not found!\n" + "Make sure you press the right location!");
			}
		}
	}
}
