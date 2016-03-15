import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

class Anagram{
	public static void main(String[] args) {
		new Test();
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
*/
class Test{
	ArrayList<Word> wordList;
	public Test(){
		wordList = new ArrayList<Word>();
		readFile();
		grouping();
	}

	public void readFile(){
		try{
			Scanner scanFile = new Scanner(new File("Ordliste.txt"));
			while(scanFile.hasNext()){
				wordList.add(new Word(scanFile.nextLine()));
			}
			scanFile.close();
		} catch(FileNotFoundException fnfe){
			System.err.println("ERROR!!!\n\t-> COULD NOT READ FILE!");
			System.exit(0);
		}
	}

	public boolean sortList(String word1, String word2){
		char[] charFromWord1 = word1.toCharArray(); 
		char[] charFromWord2 = word2.toCharArray();
		
		Arrays.sort(charFromWord1);
		Arrays.sort(charFromWord2);

		return Arrays.equals(charFromWord1, charFromWord2);
	}

	public void grouping(){
		for (int i = 0; i < wordList.size(); i++) {
			for (int j = i+1; j < wordList.size(); j++) {
				if (sortList(wordList.get(i).wordName, wordList.get(j).wordName)) {
					wordList.get(j).match = true;
					wordList.get(i).matchWordList.add(wordList.get(j).wordName);
				}
			}
		}
		for (Word resultWord : wordList) {
			if(!resultWord.match){
				if (!resultWord.matchWordList.isEmpty()) {
					resultWord.printMatches();
				}
			}
		}
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

	public void printMatches(){
		System.out.print(wordName + ", ");
		if (!matchWordList.isEmpty()) {
			for (String matchWord : matchWordList) {
				System.out.print(matchWord + ", ");
			}
		}
		System.out.println();
	}
}