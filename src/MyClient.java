import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

/* CSE 373 16su, 7/20/2016
 * Benjamin Jin
 * TA: Dan Butler
 * HW #3: TextAssociator
 * 
 * MyClient provides words that rhyme with each other to help the user write raps and poetry.
 * The program prompts the user for a standard format text file of rhyming words and a word to 
 * rhyme. It then outputs a list of rhyming words to the console or the word "none" if there are 
 * no words that rhyme with the inputted word.
 *  
 * This Client program is dependent on TextAssociator. 
 */
public class MyClient {
	public static void main(String[] args) throws IOException {
        giveIntro();
		Scanner input = new Scanner(System.in);
        String rhymeFile = input.nextLine();
        
        File file = new File(rhymeFile);
        TextAssociator rhymer = new TextAssociator();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String text = null;
        
        // fills the TextAssociator with words that rhyme
        while ((text = reader.readLine()) != null) {
            String[] words = text.split(",");
            String currentWord = words[0].trim();
            rhymer.addNewWord(currentWord);
            
            for (int i = 1; i < words.length; i++) {
                rhymer.addAssociation(currentWord, words[i].trim());
            }
        }
        System.out.println();
        
        String word = "";
        while(true) {
        	System.out.print("Please input the word you would like to find a rhyme for (enter \"exit\" to exit): ");
        	word = input.nextLine().toLowerCase();
        	if (word.equals("exit")) {
        		break;
        	}
        	Set<String> rhymingWords = rhymer.getAssociations(word);
        	System.out.print("Words that rhyme with \"" + word + "\": ");
        	if (rhymingWords == null) {
        		System.out.println("none");
        	} else {
        		System.out.println(rhymingWords);
        	}
        	System.out.println();
        }
        reader.close();
	}
	
	/* Gives a short introduction about the game and then prompts the user for a rhyme file
	 */
	public static void giveIntro() {
		System.out.println("This program gives takes a single word input and outputs a");
		System.out.println("list of words that rhyme with the input.");
        System.out.print("Please input the name of the rhyme file you want to use: ");
	}
}