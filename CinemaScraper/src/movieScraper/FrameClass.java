/*
 * FrameClass.java
 * Creates a GUI that searches for movies
 * ICS4U
 * PPP (Gian S, Morgan S, Ben V.)
 * May 20, 2019
 */
package movieScraper;

import java.awt.*;

import javax.swing.text.NumberFormatter;
import javax.swing.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.ArrayList;

public class FrameClass extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	static JPanel contentPane; // sets up the main panel of the frame
	JPanel searchPane; // search pane (for user selecting their own)
	JPanel resultPane; // pane after opening the program
	JPanel startPane; // first pane with user choices
	JPanel buttonPane; // a pane to hold buttons
	JPanel recPane; // pane for the recommended section

	static JTextField inMyEyes;
	static JLabel timeLabel;
	//Array of possible times of a movie
	String[] possibleTimes = { "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00", "05:30",
			"06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
			"12:00" };

	// declares labels
	JLabel instructions, number;
	static JLabel searchL, recL;
	JLabel recNum;

	Font loadingFont = new Font("Times New Roman", Font.BOLD + Font.ITALIC, 40);
	Font entranceFont = new Font("Times New Roman", Font.BOLD, 18);
	Font infoFont = new Font("Times New Roman", Font.BOLD, 16);
	Font specificFont = new Font("Impact", Font.PLAIN, 20);
	Color logoColour = new Color(89, 183, 255);

	// creates text area
	static JTextArea recommends;

	// creates text field
	JTextField search;

	// creates buttons
	JButton increase, decrease, incTime, decTime;
	JButton go;
	static JButton seeList, doSearch;
	JButton s1, s2, s3, s4, s5;

	static JTextArea info, specificInfo;
	JFormattedTextField inputValue;

	ArrayList<Movie> movieList = new ArrayList<Movie>();

	// creates integer variables
	int cinemas = 1;
	int nextNum = 0;
	int timeIndex = 0;

	Stack<Movie> top3;
	int recommendCounter = 0;
	static JButton next;

	String screen;

	/**
	 * pre: none
	 * post: GUI is created and components are defined
	 */
	public FrameClass() {

		// set up frame title
		super("Unit 5 Assignment!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Main content panel with Border Layout
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		contentPane.setBackground(Color.pink);

		/* Elements of Frame 1 (How many cinemas to use) */

		startPane = new JPanel();
		startPane.setLayout(new BoxLayout(startPane, BoxLayout.PAGE_AXIS));
		startPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		startPane.setBackground(Color.pink);
		contentPane.add(startPane, BorderLayout.NORTH);

		JLabel welcome = new JLabel("Movie Finder Ottawa");
		welcome.setFont(loadingFont);
		welcome.setBorder(BorderFactory.createEmptyBorder(5, 5, 30, 5));
		welcome.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		startPane.add(welcome);

		JPanel cinemaNumPane = new JPanel();
		cinemaNumPane.setLayout(new FlowLayout());
		cinemaNumPane.setBackground(Color.pink);
		cinemaNumPane.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		startPane.add(cinemaNumPane);

		inMyEyes = new JTextField("Input Movie Name", 30);
		// inMyEyes.setAlignmentX(JTextField.CENTER_ALIGNMENT);
		cinemaNumPane.add(inMyEyes);

		// set up a label
		instructions = new JLabel("How many cinemas will you search? (Max 5)");
		// instructions.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		instructions.setFont(entranceFont);
		cinemaNumPane.add(instructions);

		number = new JLabel("1");
		number.setFont(entranceFont);
		number.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		number.setForeground(Color.blue);
		number.setHorizontalAlignment(JLabel.CENTER);
		cinemaNumPane.add(number);

		// sets up a button
		increase = new JButton("+");
		increase.setActionCommand("+");
		increase.addActionListener(this);
		cinemaNumPane.add(increase);

		decrease = new JButton("-");
		decrease.setActionCommand("-");
		decrease.addActionListener(this);
		decrease.setEnabled(false);
		cinemaNumPane.add(decrease);

		JPanel timeStartPane = new JPanel();
		timeStartPane.setLayout(new FlowLayout());
		timeStartPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		timeStartPane.setBackground(Color.pink);
		startPane.add(timeStartPane);

		JLabel timeLabelSetup = new JLabel("Time: ");
		timeLabelSetup.setFont(entranceFont);
		timeStartPane.add(timeLabelSetup);

		timeLabel = new JLabel(possibleTimes[0]);
		timeLabel.setFont(entranceFont);
		timeStartPane.add(timeLabel);

		incTime = new JButton("+");
		incTime.setActionCommand("+Time");
		incTime.addActionListener(this);
		timeStartPane.add(incTime);

		decTime = new JButton("-");
		decTime.setActionCommand("-Time");
		decTime.addActionListener(this);
		decTime.setEnabled(false);
		timeStartPane.add(decTime);

		go = new JButton("               Go!               ");
		go.setBackground(Color.white);
		go.setActionCommand("go");
		go.setAlignmentX(JButton.CENTER_ALIGNMENT);
		go.addActionListener(this);
		startPane.add(go);

		/* Elements for Frame 2 (Results were found) */

		resultPane = new JPanel();
		resultPane.setLayout(new BorderLayout());
		resultPane.setBackground(logoColour);

		recL = new JLabel("Loading...");
		recL.setFont(loadingFont);
		recL.setHorizontalAlignment(JLabel.CENTER);
		resultPane.add(recL, BorderLayout.CENTER);

		JPanel buttonResultPane = new JPanel();
		buttonResultPane.setLayout(new FlowLayout());
		resultPane.add(buttonResultPane, BorderLayout.SOUTH);

		doSearch = new JButton("Search for my own!");
		doSearch.setActionCommand("look");
		doSearch.addActionListener(this);
		doSearch.setBackground(Color.white);
		buttonResultPane.add(doSearch);

		seeList = new JButton("See the recommended!");
		seeList.setActionCommand("list");
		seeList.addActionListener(this);
		seeList.setBackground(Color.white);
		buttonResultPane.add(seeList);

		seeList.setEnabled(false);
		doSearch.setEnabled(false);

		/* The Elements for Frame 3 (Search for your own) */

		searchPane = new JPanel();
		searchPane.setLayout(new BoxLayout(searchPane, BoxLayout.PAGE_AXIS));
		searchPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		searchPane.setBackground(Color.pink);

		s1 = new JButton("Ottawa");
		s1.setActionCommand("s1*");
		s1.addActionListener(this);
		s1.setBackground(Color.white);

		s2 = new JButton("Landsdowne");
		s2.setActionCommand("s2*");
		s2.addActionListener(this);
		s2.setBackground(Color.white);

		s3 = new JButton("South Keys");
		s3.setActionCommand("s3*");
		s3.addActionListener(this);
		s3.setBackground(Color.white);

		s4 = new JButton("Barrhaven");
		s4.setActionCommand("s4*");
		s4.addActionListener(this);
		s4.setBackground(Color.white);

		s5 = new JButton("Gatineau");
		s5.setActionCommand("s5*");
		s5.addActionListener(this);
		s5.setBackground(Color.white);

		JPanel textPanelRec = new JPanel();
		textPanelRec.setLayout(new GridLayout(1, 2, 10, 10));
		searchPane.add(textPanelRec);

		info = new JTextArea(" ", 5, 5);
		info.setFont(infoFont);
		info.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		info.setBackground(logoColour);
		info.setAlignmentX(JTextArea.CENTER_ALIGNMENT);

		JScrollPane areaScrollPane = new JScrollPane(info);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(400, 400));
		textPanelRec.add(areaScrollPane);

		specificInfo = new JTextArea("Input movie's value and press \nlocation button to search"
				+ "\n\nMovie values are found by"
				+ "\nusing the following equation"
				+ "\n\n100 x 2(if recliner) - 0.25 x |time desired - time of movie|");
		specificInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		specificInfo.setBackground(logoColour);
		specificInfo.setFont(specificFont);
		specificInfo.setAlignmentX(JTextArea.CENTER_ALIGNMENT);

		JScrollPane specificPane = new JScrollPane(specificInfo);
		specificPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		specificPane.setPreferredSize(new Dimension(100, 100));
		textPanelRec.add(specificPane);

		JPanel fieldPane = new JPanel();
		fieldPane.setLayout(new FlowLayout());
		fieldPane.setBackground(logoColour);
		searchPane.add(fieldPane);

		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);

		inputValue = new JFormattedTextField(formatter);
		inputValue.setColumns(8);
		inputValue.setValue(100);
		fieldPane.add(inputValue);

		buttonPane = new JPanel();
		buttonPane.setLayout(new GridLayout(1, 0, 10, 5));
		buttonPane.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		buttonPane.setBackground(Color.pink);
		searchPane.add(buttonPane);

		/* Elements for Frame 4 (Recommendations) */

		recPane = new JPanel();
		recPane.setLayout(new BorderLayout());
		recPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		recPane.setBackground(Color.pink);

		recommends = new JTextArea(" ", 5, 5);
		recommends.setLineWrap(true);
		recommends.setFont(specificFont);
		recommends.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		recommends.setBackground(Color.pink);
		recPane.add(recommends, BorderLayout.CENTER);
		
		recNum = new JLabel("Top 3");
		recNum.setFont(loadingFont);
		recNum.setHorizontalAlignment(JLabel.CENTER);
		recPane.add(recNum, BorderLayout.NORTH);

		/*
		 * JScrollPane recommendScroll = new JScrollPane(recommends);
		 * recommendScroll.setVerticalScrollBarPolicy(JScrollPane.
		 * VERTICAL_SCROLLBAR_ALWAYS); recommendScroll.setPreferredSize(new
		 * Dimension(250, 250)); recPane.add(recommendScroll, BorderLayout.CENTER);
		 */
		
		next = new JButton("Next");
		next.setBackground(Color.white);
		next.setHorizontalAlignment(JButton.CENTER);
		next.setActionCommand("next");
		next.addActionListener(this);
		recPane.add(next, BorderLayout.SOUTH);

		// Add content to frame
		setContentPane(contentPane);

		/* Size and then display the frame. */
		setSize(700, 350);
		setVisible(true);
	}

	/*
	 * pre: none post: clears content pane and adds a new pane
	 */
	public void newScreen(String screen) {

		if (screen.equals("choices")) {
			contentPane.removeAll();
			contentPane.add(resultPane, BorderLayout.SOUTH);
			setVisible(true);
		} else if (screen.equals("search")) {

			contentPane.removeAll();

			Thread getData = new Thread(new ParseClass("limitedDisplay"));
			getData.start();

			if (cinemas == 1) {
				buttonPane.add(s1);

			} else if (cinemas == 2) {
				buttonPane.add(s1);
				buttonPane.add(s2);
			} else if (cinemas == 3) {
				buttonPane.add(s1);
				buttonPane.add(s2);
				buttonPane.add(s3);

			} else if (cinemas == 4) {
				buttonPane.add(s1);
				buttonPane.add(s2);
				buttonPane.add(s3);
				buttonPane.add(s4);

			} else if (cinemas == 5) {
				buttonPane.add(s1);
				buttonPane.add(s2);
				buttonPane.add(s3);
				buttonPane.add(s4);
				buttonPane.add(s5);

			}
			contentPane.add(searchPane, BorderLayout.SOUTH);
			setVisible(true);
		}

		if (screen.equals("recommend")) {
			contentPane.removeAll();
			contentPane.add(recPane, BorderLayout.SOUTH);
			setVisible(true);
		}

	}

	/**
	 * pre: Movies have been found
	 * Post: Recommends 3 or less movies to user
	 */
	public void recommendSetUp() {
		top3 = new Stack<Movie>();
		int topLimit;
		if (!movieList.isEmpty()) {
			if (movieList.size() <= 3) {
				topLimit = movieList.size()+1;
			} else {
				topLimit = 4;
			}

			for (int i = movieList.size() - 1; i > movieList.size() - topLimit; i--) {
				top3.push(movieList.get(i));
			}
		}
		if(movieList.size() < 2) {
			next.setEnabled(false);
		}
	}

	/*
	 * pre: none post: performs actions based off of a button the user presses
	 */
	public void actionPerformed(ActionEvent e) {

		String event = e.getActionCommand(); // turns the button input into a string

		/* For Frame 1 */
		if (event.equals("go")) { // when the user chooses to search their selected number of films

			Thread parser = new Thread(new ParseClass("Parse", cinemas, inMyEyes.getText(), timeLabel.getText()));
			parser.start();
			screen = "choices";
			newScreen(screen);
		}
		if (event.equals("+")) { // allows user to increment the number of films
			cinemas++;

			if (cinemas == 5) {
				increase.setEnabled(false);
			} else if (cinemas > 0) {
				decrease.setEnabled(true);
			}

			number.setText(Integer.toString(cinemas));
		}
		if (event.equals("-")) { // decreases the number of films
			cinemas--;
			if (cinemas == 1) {
				decrease.setEnabled(false);
			} else if (cinemas < 5) {
				increase.setEnabled(true);
			}

			number.setText(Integer.toString(cinemas));
		}

		if (event.equals("+Time")) { // allows user to increment the number of films
			timeIndex++;

			if (timeIndex == (possibleTimes.length - 1)) {
				incTime.setEnabled(false);
			} else if (cinemas > 0) {
				decTime.setEnabled(true);
			}

			timeLabel.setText(possibleTimes[timeIndex]);
		}
		if (event.equals("-Time")) { // decreases the number of films
			timeIndex--;
			if (timeIndex == 1) {
				decTime.setEnabled(false);
			} else if (timeIndex < (possibleTimes.length - 1)) {
				incTime.setEnabled(true);
			}

			timeLabel.setText(possibleTimes[timeIndex]);
		}

		/* For frame 2 */
		if (event.equals("look")) { // brings user to the search menu

			screen = "search";
			newScreen(screen);

		}
		if (event.equals("list")) { // brings user to the recommends menu

			movieList = ParseClass.getMovieList();
			recommendSetUp();
			recNum.setText("Top " + Integer.toString(top3.size()));
			String text = ((Movie) top3.pop()).getInfo();
			SwingUtilities.invokeLater(() -> recommends.setText(text));
			recommendCounter++;
			screen = "recommend";
			newScreen(screen);

		}

		/* For Search Frame */
		if (event.contains("*")) {
			int movieVal = Integer.parseInt(inputValue.getText());
			String cinema = "";
			if (event.contains("1")) {
				cinema = "Cineplex Ottawa";
			} else if (event.contains("2")) {
				cinema = "Cineplex Lansdowne and VIP";
			} else if (event.contains("3")) {
				cinema = "Cineplex Odeon South Keys";
			} else if (event.contains("4")) {
				cinema = "Cineplex Odean Barrhaven";
			} else if (event.contains("5")) {
				cinema = "Cinema Starcite Gatineau";
			}
			(new Thread(new ParseClass("search", cinema, movieVal))).start();
		}
		
		/* For Recommendation Frame */
		if (event.equals("next")) {
			recNum.setText("Top " + Integer.toString(top3.size()));
			if (recommendCounter < 2) {
				String text = ((Movie) top3.pop()).getInfo();
				SwingUtilities.invokeLater(() -> recommends.setText(text));
				recommendCounter++;
			} else {
				String text = ((Movie) top3.pop()).getInfo();
				SwingUtilities.invokeLater(() -> recommends.setText(text));
				recommendCounter++;
				next.setEnabled(false);
			}
		}

	}

	/*
	 * pre: none post: begins program Main method
	 */
	public static void main(String[] arguments) {

		new FrameClass();
	}
}
