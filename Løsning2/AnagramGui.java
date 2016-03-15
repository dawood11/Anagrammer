import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;

class AnagramGui{
	public static void main(String[] args) {
		/*
		 *	Creates the GUI interface and sets simple settings to the interface
		*/
		final Gui g = new Gui();
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g.setSize(675, 475);
		g.setResizable(false);
		g.setLocationRelativeTo(null);
		g.setVisible(true);
	}
}
/*
 *	This Test class contains 3 simple methods which reads a .txt file
 *	and add every word in an ArrayList.
 *	The words get grouped by their specific anagram matcheed word
 *	which is is decided by the method sortlist(String word1, String word2).
 *	sortlist() sorts each word in alphabetical ord by each character
 *	and compares if there is a match.
 *	(just the way the hint in the task proposes.)
 *	These words are sent back (grouping()) to the GUI interface which prints out
 *	in a JTextArea. This part is explained in the Gui class in Gui.java file.
*/
class Test{
	ArrayList<Word> wordList;
	public Test(){
		wordList = new ArrayList<Word>();
	}

	public String readFile(File file){
		String s = "";
		try{
			Scanner scanFile = new Scanner(file);
			while(scanFile.hasNext()){
				String readWord = scanFile.nextLine();
				wordList.add(new Word(readWord));
				s += readWord + "\n";
			}
			scanFile.close();
		} catch(FileNotFoundException fnfe){
			System.err.println("ERROR!!!\n\t-> COULD NOT READ FILE!");
			return null;
			//System.exit(0);
		}
		return s;
	}

	public boolean sortList(String word1, String word2){
		char[] charFromWord1 = word1.toLowerCase().toCharArray(); 
		char[] charFromWord2 = word2.toLowerCase().toCharArray();
		
		Arrays.sort(charFromWord1);
		Arrays.sort(charFromWord2);

		return Arrays.equals(charFromWord1, charFromWord2);
	}

	public String grouping(){
		for (int i = 0; i < wordList.size(); i++) {
			for (int j = i+1; j < wordList.size(); j++) {
				if (sortList(wordList.get(i).wordName, wordList.get(j).wordName)) {
					wordList.get(j).match = true;
					wordList.get(i).matchWordList.add(wordList.get(j).wordName);
				}
			}
		}
		String a = "";
		for (Word resultWord : wordList) {
			
			if(!resultWord.match){
				if (!resultWord.matchWordList.isEmpty()) {
					a += resultWord.printMatches();
				}
			}
		}
		return a;
	}
}

/*
 *	The class Word cointains an ArrayList of every matching
 *	anagram word, that occurs througout the list for that specific word.
*/
class Word{
	String wordName;
	boolean match = false;
	ArrayList<String> matchWordList;

	public Word(String wordName){
		this.wordName = wordName;
		matchWordList = new ArrayList<String>();
	}

	public String printMatches(){
		String s = wordName + ", ";
		if (!matchWordList.isEmpty()) {
			for (String matchWord : matchWordList) {
				s += matchWord + ", ";
			}
		}
		s += "\n";
		return s;
	}
}