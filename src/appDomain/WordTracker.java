package appDomain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import implementations.BSTree;
import utilities.Iterator;

public class WordTracker {
	
	public static BSTree<String> tree = new BSTree<String>();
	public static HashMap<String, Integer> wordLocations = new HashMap<String, Integer>();
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
	
	public static void main(String[] args) {
		deserializeBSTFromFile();
		loadWordsIntoBSTFromFile(args[0]);
		Iterator<String> it = tree.inorderIterator();
		while (it.hasNext()) System.out.println(it.next());
		serializeBSTToFile();
	}
}
