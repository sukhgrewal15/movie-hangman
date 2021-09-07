// The "FinalProject" class.
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;
//import java.util.Random;

public class gameScreen extends JFrame implements ActionListener
{
    // declares variables
    JLabel type, image2, score, word, info, guessLet, leftLeg1, rightLeg1, rightArm1, leftArm1, body1, head1;
    ImageIcon arrow, pole, leftLeg, rightLeg, rightArm, leftArm, body, head;
    JButton back, entLet;
    JTextField txtLet;
    Font secretFont, lblFont, txtLetFont, scoreFont;
    Container frame2;

    String dicWord = randomWord (); // runs method to get a random movie title from text file and stores into variable
    String hidden = hideWord (dicWord); // puts the above word into method hide certain chars of the String
    int guess = 6; // sets maximum number of incorrec guesses
    String incGuess = "Incorrect Guesses: "; //String to be modified later depending on incorrect guesses
    static int scoreCount = 0; //a score counter that only resets when program manually does (not even if closed) 

    public gameScreen () throws IOException
    {
	super ("Hangman"); // Set the frame's name
	frame2 = getContentPane ();
	Toolkit dir = Toolkit.getDefaultToolkit ();
	Color TEAL = new Color (121, 205, 204); //declares custom colour
	
	//custom fonts
	txtLetFont = new Font ("Comic Sans", Font.BOLD, 105);
	secretFont = new Font ("Garamond", Font.BOLD, 23);
	lblFont = new Font ("Comic Sans", Font.BOLD, 30);
	scoreFont = new Font ("Comic Sans", Font.BOLD, 130);
	
	//hangman body images declared and visibility set to false
	head = new ImageIcon (dir.getImage ("head.png"));
	head1 = new JLabel (head);
	head1.setBounds (240, 320, 136, 136);
	head1.setVisible (false);
	body = new ImageIcon (dir.getImage ("body.png"));
	body1 = new JLabel (body);
	body1.setBounds (240, 456, 136, 136);
	body1.setVisible (false);
	leftLeg = new ImageIcon (dir.getImage ("leftleg.png"));
	leftLeg1 = new JLabel (leftLeg);
	leftLeg1.setBounds (210, 587, 100, 139);
	leftLeg1.setVisible (false);
	rightLeg = new ImageIcon (dir.getImage ("rightleg.png"));
	rightLeg1 = new JLabel (rightLeg);
	rightLeg1.setBounds (307, 585, 100, 139);
	rightLeg1.setVisible (false);
	rightArm = new ImageIcon (dir.getImage ("rightarm.png"));
	rightArm1 = new JLabel (rightArm);
	rightArm1.setBounds (295, 485, 100, 139);
	rightArm1.setVisible (false);
	leftArm = new ImageIcon (dir.getImage ("leftarm.png"));
	leftArm1 = new JLabel (leftArm);
	leftArm1.setBounds (215, 485, 100, 139);
	leftArm1.setVisible (false);
	
	//other GUI variable declaration and addition to frame
	pole = new ImageIcon (dir.getImage ("pole.png"));
	image2 = new JLabel (pole);
	image2.setBounds (1, 125, 387, 701);
	txtLet = new JTextField ("", 20);
	txtLet.setFont (txtLetFont);
	txtLet.setBounds (800, 350, 105, 150);
	arrow = new ImageIcon (dir.getImage ("arrow.png"));
	back = new JButton (arrow);
	back.setBounds (835, 0, 100, 75);
	entLet = new JButton ("Enter Letter");
	entLet.setBounds (800, 500, 105, 30);
	word = new JLabel (hidden, SwingConstants.CENTER);
	word.setBounds (325, 410, 500, 30);
	word.setFont (secretFont);
	score = new JLabel ();
	score.setBounds (0, 0, 500, 100);
	score.setText (scoreCount + "");
	score.setFont (scoreFont);
	info = new JLabel ("Let's get started!", SwingConstants.CENTER);
	info.setBounds (325, 210, 500, 40);
	info.setFont (lblFont);
	guessLet = new JLabel (incGuess, SwingConstants.CENTER);
	guessLet.setBounds (325, 610, 500, 40);
	guessLet.setFont (secretFont);
	type = new JLabel ("Type Letter:");
	type.setBounds (800, 320, 105, 30);

	entLet.addActionListener (this);
	back.addActionListener (this);

	frame2.setLayout (null);
	frame2.setBackground (Color.WHITE);
	frame2.add (score);
	score.setForeground (TEAL);
	frame2.add (back);
	back.setBackground (Color.RED);
	frame2.add (txtLet);
	frame2.add (entLet);
	entLet.setBackground (TEAL);
	frame2.add (type);
	frame2.add (image2);
	frame2.add (word);
	frame2.add (info);
	frame2.add (head1);
	frame2.add (body1);
	frame2.add (leftLeg1);
	frame2.add (rightLeg1);
	frame2.add (rightArm1);
	frame2.add (leftArm1);
	frame2.add(guessLet);
	setSize (950, 850);     // Set the frame's size
	show ();                // Show the frame
    } // Constructor


    public void actionPerformed (ActionEvent v)
    {
	if (v.getSource () == back)
	{
	    goBack (); // runs method to return to home screen if user presses "back" button
	}
	else if (v.getSource () == entLet) // if user presses "Enter Letter" button
	{
	    try
	    {
		guess = reveal (guess); // runs method to reveal user letters and returns number of guesses remaining
		
		//if number of incorrect guesses remaining decreases, a body part appears depending on the number of guesses
		if (guess == 5)
		{
		    head1.setVisible (true);
		}
		else if (guess == 4)
		{
		    body1.setVisible (true);
		}
		else if (guess == 3)
		{
		    leftArm1.setVisible (true);
		}
		else if (guess == 2)
		{
		    rightArm1.setVisible (true);
		}
		else if (guess == 1)
		{
		    rightLeg1.setVisible (true);
		}
		else if (guess == 0) //no guesses remain
		{
		    leftLeg1.setVisible (true);
		    info.setText ("GAME OVER"); //label says "GAME OVER"
		    word.setText(dicWord);
		    JOptionPane.showMessageDialog (null, "Your Score Was: " + scoreCount); //shows user their score
		    String nam = JOptionPane.showInputDialog ("Enter Your Name:"); // asks user for their name

		    writeToFile (nam, scoreCount); // method to write user name and score into file for leaderboard access and inputs user name and their score

		    scoreCount = 0; //score count resets when the user loses
		    goBack (); //return to home screen
		}
		
		//if the user reveals the whole hidden String
		if (hidden.equals (dicWord.toUpperCase ()))
		{
		    JOptionPane.showMessageDialog (null, "You guessed the word! Let's see if you can keep this up!"); //message to inform user of success
		    scoreCount = scoreCount + 1; //user score increases
		    dispose (); // frame is disposed of
		    new gameScreen ().setVisible (true); //new game screen runs
		}

	    }
	    catch (Exception e)
	    {
		JOptionPane.showMessageDialog (null, "Error");
	    }
	}
    }

    
    public static void main (String[] args) throws IOException
    {
	new gameScreen ();    // Create a FinalProject frame
    } // main method


    public static String randomWord () throws IOException
    {
	//method to find random word from text file and returning it
    
	//allows program to read from file
	BufferedReader fr = new BufferedReader (new FileReader ("./Txt-files/words.txt"));
	//declares array
	String wrd[] = new String [2554];
	
	//reads each line from file into array
	for (int i = 0 ; i < wrd.length ; i++)
	{
	    wrd [i] = fr.readLine ();
	}
	
	//randomizes number
	Random rand = new Random ();
	int num = rand.nextInt (2554);
     
	//returns random element from the array
	return (wrd [num]);
    }


    public static String hideWord (String shownWord)
    {
	//method to hide certain characters of a String

	String hiddenWord = "";
	
	//for loop to go through the chars of the original String
	for (int n = 0 ; n < shownWord.length () ; n++)
	{
	    char let3 = shownWord.charAt (n); //declares a char variable for the character at original word (for loop int variable)
	    int ascii = (int) let3; //finds ascii variable

	    if (ascii >= 32 && ascii <= 63) // if ascii variable is in a certain range, letter stays the same
	    {
		hiddenWord = hiddenWord + let3;
	    }
	    else
	    {
		hiddenWord = hiddenWord + "*"; //if it is any other character, star replaces it
	    }
	}
	return (hiddenWord); //returns the newly formed String
    }


    public int reveal (int guess1) throws IOException
    {
	//method to reveal hidden String chars and returning the remaining number of incorrect guesses
    
	String let1 = txtLet.getText ().toUpperCase (); //converts user input String into upper case
	char let = let1.charAt (0); //declares the user's first letter as the char to be used as the guess
	String dicWord1 = dicWord.toUpperCase (); //converts the original word to upper case (to be able to compare easier

	if (containLet (dicWord1, let) == true) // if original word contains user's char
	{
	    for (int b = 0 ; b < dicWord.length () ; b++) //for loop  to go through each letter of original word
	    {
		if (let == (dicWord1.charAt (b))) //if the user's letter equals the letter at a certain location in the original String
		{
		    hidden = hidden.substring (0, b) + let + hidden.substring (b + 1); //replaces hidden String's char at the location with user's entry forming a modified String
		    word.setText (hidden); //sets the label as the new modified variable
		    
		    //different congratulatory messages to display in a label
		    if (info.getText ().equals ("Aw shucks, wrong letter") || info.getText ().equals ("Just a slump. You'll pick it up!"))
		    {
			info.setText ("You got a letter. Good Job!");
		    }
		    else if (info.getText ().equals ("You're on a roll!"))
		    {
			info.setText ("Can't touch this!");
		    }
		    else
		    {
			info.setText ("You're on a roll!");
		    }
		}
	    }
	}
	else //letter is not found in original String
	{
	    //different encouraging/pity messages
	    if (info.getText ().equals ("You got a letter. Good Job!") || info.getText ().equals ("You're on a roll!"))
	    {
		info.setText ("Aw shucks, wrong letter");
	    }
	    else if (info.getText ().equals ("Just a slump. You'll pick it up!"))
	    {
		info.setText ("Do not panic. I believe in you!");
	    }
	    else
	    {
		info.setText ("Just a slump. You'll pick it up!");
	    }
	    guess1 = guess1 - 1; //number of incorrect guesses available decreases because of incorrect guess
	    
	    incGuess = incGuess + let1 + "  "; //adds the user's incorrect letter to String
	    guessLet.setText(incGuess); //displays the String on frame to inform the user of the guesses they already got wrong
	}
	return (guess1); //returns the number of incorrect guesses available
    }


    public void goBack ()
    {
	//method to go back to home screen
	try
	{
	    dispose (); //closes this class
	    scoreCount = 0;//resets score
	    new homeScreen ().setVisible (true);//runs home screen class
	}
	catch (Exception e)
	{
	    JOptionPane.showMessageDialog (null, "error");
	}
    }


    public static boolean containLet (String realWord, char letter)
    {
	//method to check for if a word contains a certain letter
	
	//for loop goes through word 
	for (int b = 0 ; b < realWord.length () ; b++)
	{
	    if (letter == (realWord.charAt (b))) //if any letter at a certain location equals the certain letter, method returns true
	    {
		return true;
	    }
	}

	return false; //otherwise, returns false
    }

    public static void writeToFile (String userName, int userScore) throws IOException
    {
	//method to write user's name and score to file without overwriting previous scores  
	
	//allows writing to file without overwriting it
	PrintWriter output = new PrintWriter (new FileWriter("./Txt-files/scores.txt", true));

	output.println (userName + ", " + userScore); //adds the recent user's name and score
	output.close (); // closes output file
    }

} // FinalProject class
