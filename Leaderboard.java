// The "Leaderboard" class.
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class Leaderboard extends JFrame implements ActionListener
{
    Container frame;
    JButton back, search, showBoard;
    JLabel lblBoard, lblResult, lblSearch;
    JTextField searchField;
    JTextArea board, result;
    ImageIcon arrow;
    Font largeFont, title;

    public Leaderboard () throws IOException
    {
	super ("Leaderboard");  // Set the frame's name
	frame = getContentPane ();
	Toolkit dir = Toolkit.getDefaultToolkit ();
	Color TEAL = new Color (121, 205, 204); //declares custom colour
	
	largeFont = new Font ("Comic Sans", Font.BOLD, 20);
	title = new Font ("Times Roman", Font.BOLD, 40);

	//declares GUI variables and adds them to frame
	arrow = new ImageIcon (dir.getImage ("arrow.png"));
	back = new JButton (arrow);
	back.setBounds (835, 0, 100, 75);
	back.setBackground (TEAL);
	search = new JButton ("Search for Scores");
	search.setBounds (400, 160, 150, 30);
	search.setBackground (TEAL);
	showBoard = new JButton ("Show Leaderboard");
	showBoard.setBounds (0, 0, 150, 30);
	showBoard.setBackground (TEAL);
	lblBoard = new JLabel ("Leaderboard");
	lblBoard.setBounds (10, 220, 400, 30);
	lblBoard.setFont(title);
	lblResult = new JLabel ("Search Result");
	lblResult.setBounds (480, 220, 400, 30);
	lblResult.setFont(title);
	lblSearch = new JLabel ("Type name to search:", SwingConstants.CENTER);
	lblSearch.setBounds (400, 50, 150, 30);
	board = new JTextArea ("");
	board.setBounds (0, 260, 475, 600);
	board.setFont(largeFont);
	searchField = new JTextField ("", 20);
	searchField.setBounds (400, 85, 150, 75);
	result = new JTextArea ("");
	result.setBounds (475, 260, 475, 600);
	result.setFont(largeFont);
	

	search.addActionListener (this);
	back.addActionListener (this);
	showBoard.addActionListener (this);

	frame.setLayout (null);
	frame.setBackground (Color.WHITE);
	frame.add (back);
	frame.add (search);
	frame.add (showBoard);
	frame.add (lblBoard);
	frame.add (lblResult);
	frame.add (lblSearch);
	frame.add (board);
	frame.add (result);
	frame.add (searchField);
	setSize (950, 850);     // Set the frame's size
	show ();                // Show the frame
    } // Constructor


    public static void main (String[] args) throws IOException
    {
	new Leaderboard ();     // Create a Leaderboard frame
    } // main method


    public void actionPerformed (ActionEvent v)
    {
	if (v.getSource () == back) //if user presses "back" button
	{
	    goBack (); //method to return to home screen
	}
	else if (v.getSource () == showBoard) // if user presses "Show Leaderboard" button
	{
	    try
	    {
		showLeaderboard (); //method to show leaderboard
	    }
	    catch (Exception e)
	    {
		JOptionPane.showMessageDialog (null, "error");
	    }
	}
	else if (v.getSource () == search) //if user wants to search for name (presses button)
	{
	    try
	    {
		searchMethod (); //method to find results for the name user entered to search for
	    }
	    catch (Exception a)
	    {
		JOptionPane.showMessageDialog (null, "error");
	    }
	}
    }


    public void goBack ()
    {
	try
	{
	    dispose (); //closes current frame
	    new homeScreen ().setVisible (true); //opens home screen
	}
	catch (Exception e)
	{
	    JOptionPane.showMessageDialog (null, "error");
	}
    }


    public void showLeaderboard () throws IOException
    {
	//allows program to read notepad file
	BufferedReader fr = new BufferedReader (new FileReader ("./Txt-files/scores.txt"));
	String lBoard = "";

	//runs method to find the number of lines in notepad file and puts it into a variable
	int count = findCount ();

	//declares arrays and uses count variable (# of lines)
	String nam[] = new String [count];
	int scores[] = new int [count];

	//loop splits each line into bits (each bit is put into an array according to corresponding info)
	for (int i = 0 ; i < nam.length ; i++)
	{
	    String bits[];
	    String lines = fr.readLine ();
	    bits = lines.split (", ");
	    nam [i] = bits [0];
	    scores [i] = Integer.parseInt (bits [1]);
	}

	fr.close (); //closes notepad file that was read from
	
	//bubble sort in descending order
	for (int g = 0 ; g < nam.length ; g++)
	{
	    for (int f = 0 ; f < nam.length - 1 ; f++)
	    {
		if (scores [f] > scores [f + 1])
		{
		    int scores2 = scores [f];
		    scores [f] = scores [f + 1];
		    scores [f + 1] = scores2;
		    String nam2 = nam [f];
		    nam [f] = nam [f + 1];
		    nam [f + 1] = nam2;
		}
	    }
	}
	
	//for loop to add to a String variable
	for (int j = 0; j < nam.length; j++)
	{
	    lBoard = nam[j] + " - " + scores[j] + "\n" + lBoard;
	}
	
	board.setText (lBoard); //outputs the leaderboard on JTextArea
    }


    public void searchMethod () throws IOException
    {
	//allows program to read notepad file
	BufferedReader fr = new BufferedReader (new FileReader ("./Txt-files/scores.txt"));

	//runs method to find the number of lines in notepad file and puts it into a variable
	int count = findCount ();

	//declares arrays and uses count variable (# of lines)
	String nam[] = new String [count];
	int scores[] = new int [count];

	//loop splits each line into bits (each bit is put into an array according to corresponding info)
	for (int i = 0 ; i < nam.length ; i++)
	{
	    String bits[];
	    String lines = fr.readLine ();
	    bits = lines.split (", ");
	    nam [i] = bits [0];
	    scores [i] = Integer.parseInt (bits [1]);
	}

	fr.close (); //closes notepad file that was read from

	String text = searchField.getText ();
	String searches = "";

	//loop that goes through all names in text file
	for (int n = 0 ; n < nam.length ; n++)
	{
	    // if the user's inputted name is found, that user's data is put into empty variable
	    if (text.equalsIgnoreCase (nam [n]) == true)
	    {
		searches = nam [n] + " - " + scores [n] + "\n" + searches;
	    }
	    else
	    {
		result.setText ("NAME NOT FOUND"); //message for if name is not found
	    }
	}

	result.setText (searches); //outputs the user's inputted name's past scores on JTextArea
    }


    public static int findCount () throws IOException
    {
	//method to find number of lines in text file being read from

	//allows program to read from file
	BufferedReader fr1 = new BufferedReader (new FileReader ("./Txt-files/scores.txt"));

	//declares variables
	String line = fr1.readLine ();
	int counter = 0;

	//loop that adds to count variable until a line in the text file is found to have no text
	do
	{
	    counter++;
	}
	while (fr1.readLine () != null);

	//returns the count (number of lines with info in text file)
	return (counter);
    }
} // Leaderboard class
