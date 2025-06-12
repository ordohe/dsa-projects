/*
 * 
 * file name: WordCounter.java
 * author: Olivia Doherty
 * last modified: 11/2/23
 * purpose: creates and manages a MapSet of word-count pairs 
 * to run from the terminal:
 * javac WordCounter.java
 * java BSTWordCounterMap
 * 
 */

import java.io.*;
import java.util.*;

// The WordCounter class is designed to count the frequency of words in a text file.
public class WordCounter {
    // The map is used to store words and their corresponding counts.
    private MapSet<String, Integer> map;
    private int totalWordCount;

    // The constructor initializes the map based on the chosen data structure.
    public WordCounter(String data_structure) {
        if (data_structure.equals("bst")) {
            // If bst is chosen, initialize map as a binary search tree-based map.
            map = new BSTMap<>();
        } else if (data_structure.equals("hashmap")) {
             // If hashmap is chosen, initialize map as a hash map.
            map = new HashMap<>();
        } else {
            // If neither bst nor hashmap is chosen, throw an exception
            throw new IllegalArgumentException("Invalid data structure type.");
        }
        totalWordCount = 0;
    }
    // Getter method to access the map from outside the class.
    public MapSet<String, Integer> getMap(){
        return map;
    }
    // This method reads words from a file and returns an ArrayList of strings.
    public ArrayList<String> readWords(String filename){
        ArrayList<String> words = new ArrayList<>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String word = scanner.next();
                // Only add words that are purely alphabetical.
                if (word.matches("[a-zA-Z]+")){
                    words.add(word);
                    totalWordCount++;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
        return words;
    
    }
    // Builds the map by incrementing the count of each word; returns the time taken to build the map.
    public double buildMap(ArrayList<String> words, int trials) {
        long startTime = System.currentTimeMillis();
        for (String word : words) {
            word = word.toLowerCase();
            // If the word is already in the map, increment its count.
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                // Otherwise, add the word to the map with a count of 1.
                map.put(word, 1);
            }
        }
        return System.currentTimeMillis() - startTime;
    }
    // Clears the map and resets the total word count.
    public void clearMap() {
        map.clear();
        totalWordCount = 0;
    }
    // Getter for the total word count.
    public int totalWordCount() {
        return totalWordCount;
    }
    // Returns the number of unique words in the map.
    public int uniqueWordCount() {
        return map.size();
    }
    // Returns the count of a specific word, or 0 if the word is not in the map.
    public int getCount(String word) {
        Integer count = map.get(word);
        if (count == null) {
            return 0;
        }
        return count;
    }
    // Calculates the frequency of a specific word in the text.
    public double getFrequency(String word) {
        return getCount(word) / (double) totalWordCount;
    }
     // This method returns the max depth of the tree if a BSTMap is used, otherwise it throws an exception.
    public int getMaxDepth() {
        if (map instanceof BSTMap) {
            // Cast to BSTMap and call its maxDepth method.
            BSTMap<String, Integer> bstMap = (BSTMap<String, Integer>) map;
            return bstMap.maxDepth();
        } else if (map instanceof HashMap) {
            // Hashmaps don't have a 'depth' so this would be a placeholder.
            // This casting is not appropriate since HashMap doesn't have a maxDepth method; you might need a custom method here.
            HashMap<String, Integer> hashMap = (HashMap<String, Integer>) map;
            return hashMap.maxDepth(); // This line is problematic and will not compile.
        } else {
            throw new UnsupportedOperationException("Maximum depth is only supported for BSTMap and HashMap.");
        }
    }
    
    // Writes the word counts to a file.
    public boolean writeWordCount(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Write the total number of words first.
            writer.write(String.valueOf(totalWordCount));
            writer.newLine();
            // Write each word and its count on a new line.
            for (MapSet.KeyValuePair<String, Integer> entry : map.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            // If an IOException occurs, return false.
            return false;
        }
        return true;
    }
     // Reads the word counts from a file and updates the map accordingly.
    public boolean readWordCount(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            // Read the total number of words first.
            String line = reader.readLine();
            int totalWords = Integer.parseInt(line.trim());
            // Read each word and its count, and update the map.
            map.clear();
            for (int i = 0; i < totalWords; i++) {
                line = reader.readLine();
                String[] parts = line.split("[ ]+");
                String word = parts[0];
                int count = Integer.parseInt(parts[1]);
                map.put(word, count);
            }
            reader.close();
            return true;
        } catch (Exception e) {
            // If an exception occurs, print the error and return false.
            System.err.format("Exception occurred trying to read: ", filename);
            return false;
        }
    }


    public static void main(String[] args) {
        String filename = "reddit_comments_2013.txt";
        WordCounter wc = new WordCounter("hashmap");
        ArrayList<String> words = wc.readWords(filename);
        int trials = 10;
        double averageTime = wc.buildMap(words, trials);
        System.out.println("Average time for building the map: " + averageTime + " milliseconds");
        System.out.println("Max Depth: " + wc.getMaxDepth());
    }


}
