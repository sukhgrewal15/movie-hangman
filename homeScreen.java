// The "FinalProject" class.
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class homeScreen extends JFrame implements ActionListener
{
    JLabel title, image;
    ImageIcon hang;
    JButton start, board;
    Container frame;

    public homeScreen () throws IOException
    {
	super ("Hangman"); // Set the frame's name
	frame = getContentPane ();
	Toolkit dir = Toolkit.getDefaultToolkit ();
	Color TEAL = new Color (121, 205, 204); // declares custom colour

	title = new JLabel ("HANGMAN", SwingConstants.CENTER);
	start = new JButton ("Start");
	board = new JButton ("Leaderboards");
	hang = new ImageIcon (dir.getImage ("hangman.png"));
	image = new JLabel (hang);

	start.addActionListener (this);
	board.addActionListener (this);
	
	// sets frame layout
	frame.setLayout (new GridLayout (3, 1, 10, 10));
	
	// adds elements to GUI and sets colours as well
	frame.setBackground (Color.WHITE);
	frame.add (image);
	frame.add (start);
	start.setBackground (TEAL);
	frame.add (board);
	board.setBackground (TEAL);
	setSize (950, 850);     // Set the frame's size
	show ();                // Shows the frame
    } // Constructor


    public void actionPerformed (ActionEvent v)
    {
	// if user press starts button, home screen closes and game screen program runs
	if (v.getSource () == start)
	{
	    try
	    {
		dispose (); // closes home screen
		new gameScreen ().setVisible (true); // opens game screen
	    }
	    catch (Exception e)
	    {
		JOptionPane.showMessageDialog (null, "error");
	    }
	}
	// if user press leaderboard button
	else if (v.getSource () == board)
	{
	    try
	    {
		dispose ();// closes home screen
		new Leaderboard ().setVisible (true); // opens leaderboard program and runs
	    }
	    catch (Exception e)
	    {
		JOptionPane.showMessageDialog (null, "error");
	    }
	}
    }

    public static void main (String[] args) throws IOException
    {
	new homeScreen ();    // Create a FinalProject frame
    } // main method
} // homeScreen class


