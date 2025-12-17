package appDomain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import implementations.BSTree;
import utilities.Iterator;

public class WordTracker {
	
	public static BSTree<String> tree = new BSTree<String>();
	private static String BSTSerialFile = "res/repository.ser";

	private static void loadWordsIntoBSTFromFile(String fileName) {
		File file = new File(fileName);
		try {
			Scanner in = new Scanner(file);
			int lineCount = 1;
			while (in.hasNext()) {
				String line = in.nextLine();
				String[] words = new String[line.length()];
				words = line.split(" ");
				for (String word : words) {
					String cleanWord = word.replaceAll("[^a-zA-Z0-9\\s']", "");
					String toAdd = nodeStringBuilder(cleanWord, lineCount, fileName);
					tree.add(toAdd);
				}
				lineCount++;
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String nodeStringBuilder(String word, int line, String file) {
		String returnString = String.format("==%s== %s %d", word, file, line);
		return returnString;
	}

	public static void serializeBSTToFile() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BSTSerialFile));
			Iterator<String> it = tree.inorderIterator();
			ArrayList<String> array = new ArrayList<String>();
			while (it.hasNext()) array.add(it.next()); 
			oos.writeObject(array);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void deserializeBSTFromFile()
	{
		try
		{
			ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(BSTSerialFile));
			
			ArrayList<String> input = (ArrayList<String>) ois.readObject();
			ois.close();
			for (String line : input) {
				tree.add(line);
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public static ArrayList<WordInfo> optionOutputArrayBuidlerMakerDoer() {
		ArrayList<WordInfo> output = new ArrayList<WordInfo>();
		Iterator<String> it = tree.inorderIterator();
		String last = null;
		while (it.hasNext()) {
			String wordData = it.next();
			String[] wordDataArray = wordData.split(" ");
			if (wordDataArray[0].equals(last)) {
				output.get(output.size() - 1).addWordOccurrence(wordDataArray[1], Integer.parseInt(wordDataArray[2])); 
			}
			else {
				WordInfo newWord = new WordInfo(wordDataArray[0].toLowerCase());
				newWord.addWordOccurrence(wordDataArray[1], Integer.parseInt(wordDataArray[2]));
				output.add(newWord);
			}
			last = wordDataArray[0];
		}
		return output;
	}
	
	public static String pfStringBuilder(WordInfo word) {
		String output = word.getWord() + " appears in the files: ";
		for (String file: word.getFiles()) output += file + ", ";
		output = output.substring(0, output.length() - 2);
		return output;
	}
	
	public static String plStringBuilder(WordInfo word) {
		String output = word.getWord() + " appears in the files: ";
		for (String file: word.getFiles()) {
			output += file + " on lines: ";
			for (Integer line : word.getLineNumbers(file)) {
				output += line + ", ";
			}
		}
		output = output.substring(0, output.length() - 2);
		return output;
	}

	public static String poStringBuilder(WordInfo word) {
		String output = word.getWord() + " appears " + word.getTotalFrequency() + " times in the files: ";
		for (String file: word.getFiles()) {
			output += file + " on lines: ";
			for (Integer line : word.getLineNumbers(file)) {
				output += line + ", ";
			}
		}
		output = output.substring(0, output.length() - 2);
		return output;
	}

	public static void main(String[] args) {
		deserializeBSTFromFile();
		loadWordsIntoBSTFromFile(args[0]);
		ArrayList<WordInfo> wordInfo = optionOutputArrayBuidlerMakerDoer();
		
		if (args[1].equals("-pf")) {
			for (WordInfo word : wordInfo) {
				String output = pfStringBuilder(word);
				System.out.println(output);
			}
		}
		else if (args[1].equals("-pl")) {
			for (WordInfo word : wordInfo) {
				String output = plStringBuilder(word);
				System.out.println(output);
			} 
		}
		else if (args[1].equals("-po")) {
			for (WordInfo word : wordInfo) {
				String output = poStringBuilder(word);
				System.out.println(output);
			} 
		}
		
		if (args.length > 2) {
		        try (FileWriter writer = new FileWriter(args[2].substring(2))) {
		        	if (args[1].equals("-pf")) {
		        		for (WordInfo word : wordInfo) {
		        			String output = pfStringBuilder(word);
		        			writer.write(output + "\n");
		        		}
		        	}
		        	else if (args[1].equals("-pl")) {
		        		for (WordInfo word : wordInfo) {
		        			String output = plStringBuilder(word);
		        			writer.write(output + "\n");
		        		} 
		        	}
		        	else if (args[1].equals("-po")) {
		        		for (WordInfo word : wordInfo) {
		        			String output = poStringBuilder(word);
		        			writer.write(output + "\n");
		        		} 
		        	}
		        } catch (IOException e) {
		            System.err.println("Error writing to file: " + e.getMessage());
		        }
		}
		
		serializeBSTToFile();
	}
}
