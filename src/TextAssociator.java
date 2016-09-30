import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* CSE 373 16su, 7/20/2016
 * Benjamin Jin
 * TA: Dan Butler
 * HW #3: TextAssociator
 * 
 * TextAssociator represents a collection of associations between words.
 */
public class TextAssociator {
	private WordInfoSeparateChain[] table;
	private int size;
	private int index;	// the current table size being used
	private final int[] RESIZE_PRIMES = {23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853, 
			25717, 51437, 102877, 205759, 411527, 823117, 1646237, 3292489, 6584983, 13169977};
	
	/* INNER CLASS
	 * Represents a separate chain in your implementation of your hashing
	 * A WordInfoSeparateChain is a list of WordInfo objects that have all
	 * been hashed to the same index of the TextAssociator
	 */
	private class WordInfoSeparateChain {
		private List<WordInfo> chain;
		
		/* Creates an empty WordInfoSeparateChain without any WordInfo
		 */
		public WordInfoSeparateChain() {
			this.chain = new ArrayList<WordInfo>();
		}
		
		/* Adds a WordInfo object to the SeparateCahin
		 * Returns true if the WordInfo was successfully added, false otherwise
		 */
		public boolean add(WordInfo wi) {
			if (!chain.contains(wi)) {
				chain.add(wi);
				return true;
			}
			return false;
		}
		
		/* Removes the given WordInfo object from the separate chain
		 * Returns true if the WordInfo was successfully removed, false otherwise
		 */
		public boolean remove(WordInfo wi) {
			if (chain.contains(wi)) {
				chain.remove(wi);
				return true;
			}
			return false;
		}
		
		// Returns the size of this separate chain
		public int size() {
			return chain.size();
		}
		
		// Returns the String representation of this separate chain
		public String toString() {
			return chain.toString();
		}
		
		// Returns the list of WordInfo objects in this chain
		public List<WordInfo> getElements() {
			return chain;
		}
	}
	
	
	/* Creates a new TextAssociator of size 23 without any associations 
	 */
	public TextAssociator() {
		index = 0;
		size = 0;
		table = new WordInfoSeparateChain[RESIZE_PRIMES[index]];
	}
	
	
	/* Adds a word with no associations to the TextAssociator 
	 * Returns False if this word is already contained in your TextAssociator ,
	 * Returns True if this word is successfully added
	 */
	public boolean addNewWord(String word) {
		/*
		 * get the index that the word belongs at
		 * check if the bucket at that index is null
		 * 		if it is create a new bucket
		 * add the word to the bucket
		 * resize if necessary
		 */
		int bucketNumber = getBucket(word);
		
		// check if the bucket exists. If it does, check if it already has the word.
		if (table[bucketNumber] == null) {
			table[bucketNumber] = new WordInfoSeparateChain();
		} else if (findWordInfo(word, bucketNumber) != null){
			return false;
		}
		
		// resize if necessary
		if ((double)size % table.length >= 0.75) {
			size++;
			expand();
		}
		
		return table[bucketNumber].add(new WordInfo(word));
	}
	
	
	/* Adds an association between the given words. Returns true if association correctly added, 
	 * returns false if first parameter does not already exist in the TextAssociator or if 
	 * the association between the two words already exists
	 */
	public boolean addAssociation(String word, String association) {
		int bucketNumber = getBucket(word);
		if (table[bucketNumber] == null) {
			return false;
		} else {
			WordInfo wi = findWordInfo(word, bucketNumber);
			return wi.addAssociation(association);
		}
	}
	
	
	/* Remove the given word from the TextAssociator, returns false if word 
	 * was not contained, returns true if the word was successfully removed.
	 * Note that only a source word can be removed by this method, not an association.
	 */
	public boolean remove(String word) {
		int bucketNumber = getBucket(word);
		WordInfo wi = findWordInfo(word, bucketNumber);
		// WordInfo contained
		if (wi != null) {
			size--;
			return table[bucketNumber].remove(wi);
		}
		return false;
	}
	
	
	/* Returns a set of all the words associated with the given String  
	 * Returns null if the given String does not exist in the TextAssociator
	 */
	public Set<String> getAssociations(String word) {
		int bucketNumber = getBucket(word);
		WordInfo wi = findWordInfo(word, bucketNumber);
		if (wi != null) {
			return wi.getAssociations();
		}
		return null;
	}
	
	
	/* Prints the current associations between words being stored
	 * to System.out
	 */
	public void prettyPrint() {
		System.out.println("Current number of elements : " + size);
		System.out.println("Current table size: " + table.length);
		
		//Walk through every possible index in the table
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				WordInfoSeparateChain bucket = table[i];
				
				//For each separate chain, grab each individual WordInfo
				for (WordInfo curr : bucket.getElements()) {
					System.out.println("\tin table index, " + i + ": " + curr);
				}
			}
		}
		System.out.println();
	}
	
	/* Expands the size of the table to the next size and then rehashes the WordInfos
	 */
	private void expand() {
		index++;
		WordInfoSeparateChain[] temp = new WordInfoSeparateChain[RESIZE_PRIMES[index]];
		for (WordInfoSeparateChain chain: table) {
			if (chain != null) {
				// Gets a new bucket for each WordInfo
				for (WordInfo wi: chain.getElements()) {
					int bucketNumber = getBucket(wi.getWord());
					if (temp[bucketNumber] == null) {
						temp[bucketNumber] = new WordInfoSeparateChain();
					}
					temp[bucketNumber].add(wi);
				}
			}
		}
		table = temp;
	}
	
	/* Returns a bucket index for the desired word
	 */
	private int getBucket(String word) {
		int hashCode = Math.abs(word.hashCode());
		return hashCode % table.length;
	}
	
	/* Searches for a WordInfo in a bucket given the word representation. It returns the WordInfo
	 * if found, otherwise returning null. If the bucket is null it will also return null.
	 */
	private WordInfo findWordInfo(String word, int bucketNumber) {
		WordInfoSeparateChain chain = table[bucketNumber];
		if (chain != null) {
			for (WordInfo wi: chain.getElements()) {
				String current = wi.getWord();
				if (current.equalsIgnoreCase(word)) {
					return wi;
				}
			}
		}
		return null;
	}
}
