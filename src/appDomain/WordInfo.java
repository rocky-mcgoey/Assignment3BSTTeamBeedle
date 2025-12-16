package appDomain;
import java.io.Serializable;
import java.util.*;

public class WordInfo implements Serializable, Comparable<WordInfo>
{
	private static final long serialVersionUID = 1L;

    private final String word;

    // filename (lineNumber count on that line)
    private final Map<String, TreeMap<Integer, Integer>> occurrences = new HashMap<>();

    private int totalFrequency = 0;

    public WordInfo(String word) {
        if (word == null || word.trim().isEmpty()) 
        	throw new IllegalArgumentException("word cannot be null/blank");
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    /** Adds 1 occurrence for this word for filename + lineNumber */
    public void addWordOccurrence(String filename, int lineNumber) {
        if (filename == null || filename.trim().isEmpty())
            throw new IllegalArgumentException("filename cannot be null or blank");
        if (lineNumber < 1)
            throw new IllegalArgumentException("lineNumber must be >= 1");

        TreeMap<Integer, Integer> lines = occurrences.get(filename);

        if (lines == null) {
            lines = new TreeMap<>();
            occurrences.put(filename, lines);
        }

        int count = lines.getOrDefault(lineNumber, 0);
        lines.put(lineNumber, count + 1);

        totalFrequency++;
    }


    /** Sorted list of files containing the word */
    public List<String> getFiles() {
        List<String> files = new ArrayList<>(occurrences.keySet());
        Collections.sort(files);
        return files;
    }

    /** Sorted list of line numbers in a file where the word appears */
    public List<Integer> getLineNumbers(String filename) {
        TreeMap<Integer, Integer> lines = occurrences.get(filename);
        if (lines == null) return Collections.emptyList();
        return new ArrayList<>(lines.keySet());
    }

    /** How many times the word happens on a line*/
    public int getCountPerLine(String filename, int lineNumber) {
        TreeMap<Integer, Integer> lines = occurrences.get(filename);
        if (lines == null) return 0;
        return lines.getOrDefault(lineNumber, 0);
    }

    public int getTotalFrequency() {
        return totalFrequency;
    }
    @Override
    public String toString() {
        return word;
    }
	@Override
	public int compareTo(WordInfo other)
	{
		
		// TODO Auto-generated method stub
		return this.word.compareToIgnoreCase(other.word);
	}

}
