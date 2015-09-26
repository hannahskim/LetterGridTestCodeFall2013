import java.io.*;
import java.util.*;

/** A simple program to test the LetterGrid class with Boogle
  * @author Ridout
  * @version October 2013
  */
public class BoogleTest
{
    public static void main (String[] args) throws IOException
    {
	// Load up the boggle grid
	String bgFileName = "boggle.txt";
	System.out.println ("Boggle Grid: " + bgFileName + "\n");
	LetterGrid grid = new LetterGrid (bgFileName);
	System.out.println (grid);
	
	// Look for words on this grid to help test your code
	// You can comment out this code later
	Scanner keyboard = new Scanner (System.in);
	String word;
	do
	{
	    System.out.print ("Enter a word to search for (q to quit): ");
	    word = keyboard.nextLine ().toUpperCase ();
	    if (grid.boggleSearch(word))
		System.out.println (word + " is on this grid");
	    else
		System.out.println (word + " is not on this grid");
	}
	while (!word.equals ("Q"));
	keyboard.close();
	
	 // Read in the list of words and put them into upper case
	Scanner file = new Scanner (new File ("wordlist.txt"));
	ArrayList <String> wordList = new ArrayList < String > ();
	while (file.hasNext ())
	    wordList.add (file.nextLine ().toUpperCase ());
	file.close();
	    
	// Find the boggle score for this grid
	long startTime = System.nanoTime ();
	System.out.println ("Score: " + grid.boggleScore (wordList));
	long stopTime = System.nanoTime ();
	System.out.printf ("Time: %.1f ms%n", (stopTime - startTime) / 1000000.0);
	System.out.println ("Done");
    }
}
