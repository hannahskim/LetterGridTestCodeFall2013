import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Searches words using the traditional word search rules or by changing directions on the grid
 * @author Hannah Kim
 * @version October 2013
 */
public class LetterGrid
{
	private char [][] grid;
	
	/** Constructs a new LetterGrid object from the given text file
	 * @param fileName the name of the file with the letter grid
	 */
	public LetterGrid (String fileName)
	{
		// Since opening a file can throw a FileNotFoundException
		// we will include a try...catch to deal with this
		try
		{
		// Read in the file data and store in an ArrayList
		ArrayList <String> lines = new ArrayList <String> ();
		Scanner inFile = new Scanner (new File(fileName));
		
		while (inFile.hasNext())
			lines.add(inFile.nextLine());
		
		grid = new char [lines.size()][];
		int row = 0;
		
		for (String nextLine : lines)
		{
			grid [row] = nextLine.toCharArray();
			row++;
		}
		inFile.close();
		} // end try
		
		catch (FileNotFoundException exp)
		{
			grid = new char [][] {{'B','A','D',' '},
			{'F','I','L','E'},
			{'N','A','M','E'}};
		} // end catch
	} // Letter Grid     
	
	/** Displays grid
	 *  @return displayed string
	 */
	public String toString()
	{
		StringBuilder gridStr = new StringBuilder(grid.length * (grid[0].length));
		for (int row = 0; row < grid.length; row ++)
		{
			for (int col=0; col < grid[row].length; col ++)
				gridStr.append(grid[row][col]);
			gridStr.append('\n');
		}
		return gridStr.toString();
	}
	
	/** Checks if the gird is out of bounds
	 * @param row the given row to check if it is out of bounds
	 * @param col the given column
	 * @return true if word is on the grid or false if the word is not on the grid
	 */
	public boolean isOnGrid (int row, int col)
	{
		if (row >= 0 && col >=0 && grid.length > row && grid[row].length > col)
			return true;
		return false;
	}
	
	/** Looks for a word in the letter grid using the traditional word search rules
	 * @param word the given word to find
	 * @return true if word is on the grid or false if the word is not on the grid
	 */
	public boolean wordSearch (String word)
	{
		char firstLetter = word.charAt(0);
		
		// Find the first letter of the word
		for (int row = 0; row < grid.length; row++)
		{
			for (int col = 0; col < grid[row].length; col++)
			{
				if (grid[row][col] == firstLetter)
					// Search the rest of the letters in eight directions if the first letter is found
					if (searchWord(word, row, col, 1, 1) ||
							searchWord (word, row, col, 1, 0) ||
							searchWord (word, row, col, 1, -1) ||
							searchWord (word, row, col, -1, -1) ||
							searchWord (word, row, col, -1, 1) ||
							searchWord (word, row, col, -1, 0) ||
							searchWord (word, row, col, 0, 1) ||
							searchWord (word, row, col, 0, -1))
						return true;
				
			}
		}

		return false;
	}
	
	/** Looks for a word in the letter grid using the traditional word search rules
	 * @param word the given word to find in the grid
	 * @param row the given row to start find the word
	 * @param col the given column to start find the word
	 * @param rowDir the direction of the row
	 * @param colDir the direction of the column
	 * @return true if word is on the grid or false if the word is not on the grid
	 */
	public boolean searchWord (String word, int row, int col, int rowDir, int colDir)
	{
		int pos = 1;
		int nextRow = row + rowDir;
		int nextCol = col + colDir;
		
		// Find the word in one direction
		while (isOnGrid (nextRow, nextCol) && pos < word.length() && word.charAt(pos) == grid[nextRow][nextCol])
		{
			pos++;
			nextRow += rowDir;
			nextCol += colDir;
		}
		
		if (pos == word.length())
			return true;
		else
		return false; 
	}
	
	/** Finds and returns a List of all the words that can be found on this LetterGrid using the searching method
	 * @param wordList list of words to check for
	 * @return a List of all the words that can be found on LetterGrid
	 */
	public List<String> wordsOnGrid (List<String> wordList)
	{
		// Make an ArrayList to store all the words that can be found on LetterGrid
		ArrayList<String> words = new ArrayList<String>();
		
		// Find words and put them in the ArrayList if it is found
		for (String word : wordList)
		{
			word = word.toUpperCase();
			if (wordSearch(word))
				words.add(word);
		}
		
		return words;
	}
	
	/** Looks for a word in the letter grid using the Boggle rules
	 * @param word the word to find in the grid
	 * @return true if the word is found or false if the word is not found
	 */
	public boolean boggleSearch (String word)
	{       
		// Search the word
		for (int row = 0; row < grid.length; row++)
		{
			for (int col = 0; col < grid[row].length; col++)
			{
				if (word.charAt(0) == grid[row][col])
				{
					if (searchAround(word, row, col))
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/** Search the word in the grid by changing directions
	 * @param word the word to find in the grid
	 * @param row the row to find a letter
	 * @param col the column to find a letter
	 * @return true if the word is found or false if the word is not found
	 */
	public boolean searchAround (String word, int row, int col)
	{
		if (!isOnGrid (row, col))
			return false;
		
		if (word.charAt(0) != grid[row][col])
		      return false;
		
		if (word.length() == 1)
			return true;
		
		
		// Mark the current letter to avoid reusing the same letter
		grid[row][col] = Character.toLowerCase(grid[row][col]);
		
		String nextWord = word.substring(1);
		
		// Search all the eight directions to find a letter
		if (searchAround(nextWord, row, col-1) ||
				searchAround(nextWord, row, col+1) ||
				searchAround(nextWord, row+1, col) ||
				searchAround(nextWord, row+1, col+1) ||
				searchAround(nextWord, row+1, col-1) ||
				searchAround(nextWord, row-1, col) ||
				searchAround(nextWord, row-1, col+1) ||
				searchAround(nextWord, row-1, col-1))
		{
			// Unmark the letter
			grid[row][col] = Character.toUpperCase(grid[row][col]);
				return true;
		}
		// Unmark the letter
		grid[row][col] = Character.toUpperCase(grid[row][col]);
		
		return false; 
	}
	
	/** Finds the maximum score of a LetterGrid, based on the rules of the game of Boggle
	 * @param wordList a list of valid words
	 * @return the total integer score
	 */
	public int boggleScore (List<String> wordList)
	{
		// Different scores for different lengths of words (for 4X4 board)
		int [] scores = { 0, 0, 0, 1, 1, 2, 3, 5, 11};
		if (grid.length == 5)
			scores[3] = 0;

		// Variable to keep track of total score
		int totalScore = 0;

		for (String word : wordList)
		{
			// Add each score to the totalScore every time you find a word
			if (boggleSearch(word))
			{
				int length = Math.min(word.length(), 8);
					totalScore += scores[length];
			}
		}
		return totalScore;
	}
}
