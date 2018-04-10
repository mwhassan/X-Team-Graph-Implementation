import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * This class contains some utility helper methods
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class WordProcessor {
	
	/**
	 * Gets a Stream of words from the filepath.
	 * 
	 * The Stream should only contain trimmed, non-empty and UPPERCASE words.
	 * 
	 * @see <a href="http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html">java8 stream blog</a>
	 * 
	 * @param filepath file path to the dictionary file
	 * @return Stream<String> stream of words read from the filepath
	 * @throws IOException exception resulting from accessing the filepath
	 */
	public static Stream<String> getWordStream(String filepath) throws IOException {
		/**
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html">java.nio.file.Files</a>
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Paths.html">java.nio.file.Paths</a>
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html">java.nio.file.Path</a>
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html">java.util.stream.Stream</a>
		 * 
		 * class Files has a method lines() which accepts an interface Path object and 
		 * produces a Stream<String> object via which one can read all the lines from a file as a Stream.
		 * 
		 * class Paths has a method get() which accepts one or more strings (filepath),  
		 * joins them if required and produces a interface Path object
		 * 
		 * Combining these two methods:
		 *     Files.lines(Paths.get(<string filepath>))
		 *     produces
		 *         a Stream of lines read from the filepath
		 * 
		 * Once this Stream of lines is available, you can use the powerful operations available for Stream objects to combine 
		 * multiple pre-processing operations of each line in a single statement.
		 * 
		 * Few of these features:
		 * 		1. map( )      [changes a line to the result of the applied function. Mathematically, line = operation(line)]
		 * 			-  trim all the lines
		 * 			-  convert all the lines to UpperCase
		 * 			-  example takes each of the lines one by one and apply the function toString on them as line.toString() 
		 * 			   and returns the Stream:
		 * 			        streamOfLines = streamOfLines.map(String::toString) 
		 * 
		 * 		2. filter( )   [keeps only lines which satisfy the provided condition]  
		 *      	-  can be used to only keep non-empty lines and drop empty lines
		 *      	-  example below removes all the lines from the Stream which do not equal the string "apple" 
		 *                 and returns the Stream:
		 *      			streamOfLines = streamOfLines.filter(x -> x != "apple");
		 *      			 
		 * 		3. collect( )  [collects all the lines into a java.util.List object]
		 * 			-  can be used in the function which will invoke this method to convert Stream<String> of lines to List<String> of lines
		 * 			-  example below collects all the elements of the Stream into a List and returns the List:
		 * 				List<String> listOfLines = streamOfLines.collect(Collectors::toList); 
		 * 
		 * Note: since map and filter return the updated Stream objects, they can chained together as:
		 * 		streamOfLines.map(...).filter(a -> ...).map(...) and so on
		 */
		Stream<String> wordStream = Files.lines(Paths.get(filepath));
		wordStream.map(String::trim);
		wordStream.filter(x -> x!=null && !x.equals(""));
		wordStream.map (String::toUpperCase);
		return wordStream;
	}
	
	/**
	 * Adjacency between word1 and word2 is defined by:
	 * if the difference between word1 and word2 is of
	 * 	1 char replacement
	 *  1 char addition
	 *  1 char deletion
	 * then 
	 *  word1 and word2 are adjacent
	 * else
	 *  word1 and word2 are not adjacent
	 *  
	 * Note: if word1 is equal to word2, they are not adjacent
	 * 
	 * @param word1 first word
	 * @param word2 second word
	 * @return true if word1 and word2 are adjacent else false
	 */
	public static boolean isAdjacent(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();
		
		// if same length, check for replacement
		if(len1 == len2) return replacement(word1, word2);
		// if word1 is 1 character longer than word2, check for deletion
		if(len1 == len2 + 1) return deletion(word1, word2);
		// if word2 is 1 character longer than word1, check for addition
		if(len2 == len1 + 1) return addition(word1, word2);
		return false;
	}

	/** 
	 * Checks if word2 can be obtained from word1 by single character replacement
	 * Precondition: Words have the same length and are not equal
	 */
	private static boolean replacement(String word1, String word2) {
		char[] array1 = word1.toCharArray();
		char[] array2 = word2.toCharArray();
		int counter = 0; // counts number of discrepancies between words
		
		for(int i = 0; i < array1.length; i++) {
			if(array1[i] != array2[i]) {
				counter++;
				if(counter > 1) return false; // if more than 1 discrepancy, return false
			}
		}
		
		return counter == 1; // if counter is still 0, return false
	}

	/** 
	 * Checks if word2 can be obtained from word1 by single character deletion
	 * Precondition: word1 is one character longer than word2
	 */
	private static boolean deletion(String word1, String word2) {
		char[] array1 = word1.toCharArray();
		char[] array2 = word2.toCharArray();
		int len = array2.length;
		
		for(int i = 0; i < len; i++) {
			// check if a discrepancy is found
			if(array1[i] != array2[i]) {
				// check that the remaining arrays are identical
				for(int j = i; j < len; j++) {
					if(array1[j+1] != array2[j]) return false;
				}
				return true;
			}
		}
		
		return true; // if this is reached, words are identical except for last letter of word1
	}

	/** 
	 * Checks if word2 can be obtained from word1 by single character addition
	 * Precondition: word2 is once character longer than word1
	 */
	private static boolean addition(String word1, String word2) {
		return deletion(word2, word1);
	}
	
}
