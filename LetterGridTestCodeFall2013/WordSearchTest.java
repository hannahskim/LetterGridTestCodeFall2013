import java.io.*;
import java.util.*;

/** Simple Program to test the LetterGrid class
  * @author Ridout
  * @version October 2013
  */
public class WordSearchTest
{
    public static void main (String[] args) throws IOException
    {
	// Create a grid from a file and display
	String letterGridFile = "wordgrid.txt";
	System.out.println ("Letter Grid: " + letterGridFile + "\n");
	LetterGrid grid = new LetterGrid (letterGridFile);
	System.out.println (grid);

	// Look for words on this grid to help test your code
	// You can comment out this code later
	Scanner keyboard = new Scanner (System.in);
	String word;
	do
	{
	    System.out.print ("Enter a word to search for (q to quit): ");
	    word = keyboard.nextLine ().toUpperCase ();
	    if (grid.wordSearch (word))
		System.out.println (word + " is on this grid");
	    else
		System.out.println (word + " is not on this grid");
	}
	while (!word.equals ("Q"));
	keyboard.close();

	// Load up a list of words to check for from a file
	Scanner wordFile = new Scanner (new File ("wordlist.txt"));
	ArrayList < String > wordlist = new ArrayList < String > ();
	while (wordFile.hasNext ())
	    wordlist.add (wordFile.next ().toUpperCase ());
	wordFile.close();
	
	// Find and display how many words in the wordlist are on the grid
	long startTime = System.nanoTime ();
	List <String> wordsOnGrid = grid.wordsOnGrid (wordlist);
	long stopTime = System.nanoTime ();
	System.out.println ("There are " + wordsOnGrid.size () +
		" words on this grid");
	System.out.println("The first 10 words are: " + wordsOnGrid.subList(0, 10));
	System.out.printf ("Time: %.1f ms%n", (stopTime - startTime) / 1000000.0);
	System.out.println ("Done");
    }
}
