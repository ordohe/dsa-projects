/*
*
* filename: BTSMap.java
* name: Olivia Doherty
* purpose: implement a binary search tree to go through reddit comments
* date (last modified): 10/30/23
* run: javac BSTMap.java -> java BSTMap
*/

import java.util.ArrayList;
import java.util.Comparator;

// The BSTMap class implements a Binary Search Tree-based map.
public class BSTMap<K, V> implements MapSet<K, V> {

    // Inner class representing a node in the BST
    private static class Node<K, V> extends KeyValuePair<K, V> {
        private Node<K, V> left;
        private Node<K, V> right;

        // Constructor to create a new Node
        public Node(K key, V value) {
            super(key, value);
            left = null;
            right = null;
        }
    }
    // Fields of the BSTMap class
    private Node<K, V> root; // Root of the BST
    private int size; // Size of the map
    private Comparator<K> comparator; // Comparator to compare keys


    // Constructor with a comparator as a parameter.
    public BSTMap(Comparator<K> comparator) {
        this.root = null;
        this.size = 0;
        // If a comparator is provided, use it. Otherwise, use natural ordering.
        if (comparator != null){
            this.comparator = comparator;
        }
        else {
            this.comparator = new Comparator<K>() {
                public int compare(K o1, K o2) {
                    return ((Comparable<K>) o1).compareTo(o2);
                }
            };
            
        }
        
    }

    // Default constructor that uses natural ordering
    public BSTMap() {
        this(null);
    }

    // Returns the size of the map
    public int size() {
        return size;
    }

    // Clears the map by setting root to null and size to 0
    public void clear() {
        root = null;
        size = 0;
    }

    // Returns true if the map is empty, false otherwise
    public boolean isEmpty() {
        return size() == 0;
    }

    // Returns true if the map contains the given key
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    // Associates the specified value with the specified key in the map.
    @Override
    public V put(K key, V value) {
        // If the map is empty, create a new root node
        if (size == 0){
            root = new Node<K, V>(key, value);
            size++;
            return null;
        } else {
            // Otherwise, call the recursive helper function
            return put(key, value, root);
        }
    }

    // Recursive helper function for put
    private V put(K key, V value, Node<K, V> curNode){
        // Compare the key with the current node's key
        if (comparator.compare(key, curNode.getKey()) < 0){
            // If the key is less than the current node's key, recurse left
            if (curNode.left == null){
                curNode.left = new Node<K, V>(key, value);
                size++;
                return null;
            } else {
                return put(key, value, curNode.left);
            }
        } else if (comparator.compare(key, curNode.getKey()) > 0){
            // If the key is greater than the current node's key, recurse right
            if (curNode.right == null){
                curNode.right = new Node<K, V>(key, value);
                size++;
                return null;
            } else {
                return put(key, value, curNode.right);
            }
        } else {
            // If the key is equal to the current node's key, replace the value
            V oldVal = curNode.getValue();
            curNode.setValue(value);
            return oldVal;
        }
    }

    // Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
    public V get(K key) {
        // If the map is empty, return null
        if (size == 0){
            return null;
        }
        // Otherwise, call the recursive helper function
        return get(key, root);
    }

    // Recursive helper function for get
    private V get(K key, Node<K, V> cur) {
        if (cur == null) {
            return null;
        } else if (comparator.compare(key, cur.getKey()) < 0) {
            return get(key, cur.left);
        } else if (comparator.compare(key, cur.getKey()) > 0) {
            return get(key, cur.right);
        } else {
            return cur.getValue();
        }
    }
    
    // Returns a list of all keys in the map in sorted order
    public ArrayList<K> keySet() {
        ArrayList<K> output = new ArrayList<>();
        // Call the recursive helper function
        keySet(root, output);
        return output;
    }
    
    // Recursive helper function for keySet
    private void keySet(Node<K, V> cur, ArrayList<K> output) {
        // If the current node is null, return
        if (cur == null) {
            return;
        }
        // Recurse left, add the current node's key, then recurse right
        keySet(cur.left, output);
        output.add(cur.getKey());
        keySet(cur.right, output);
    }

    // Returns a list of all values in the map
    public ArrayList<V> values() {
        ArrayList<V> output = new ArrayList<>();
         // Iterate over all keys and add their corresponding values to the list
        for (K key : keySet()) {
            output.add(get(key));
        }
        return output;
    }
    
    // Returns a list of all key-value pairs in the map
    public ArrayList<KeyValuePair<K, V>> entrySet() {
        ArrayList<KeyValuePair<K, V>> output = new ArrayList<>();
        // Iterate over all keys and add their corresponding key-value pairs to the list
        for (K key : keySet()) {
            output.add(new KeyValuePair<>(key, get(key)));
        }
        return output;
    }
    
    // Returns a string representation of the BST
    public String toString() {
        if (size == 0) {
            return "No Tree to Return";
        }
        // Call the recursive helper function
        StringBuilder treeStr = toString(root, "root: ", 0);
        return treeStr.delete(treeStr.length() - 1, treeStr.length()).toString();
    }
    
    // Recursive helper function for toString
    private StringBuilder toString(Node<K, V> cur, String direction, int depth) {
        // If the current node is null, return an empty StringBuilder
        if (cur == null) {
            return new StringBuilder();
        }
        
        // Recurse right, append the current node's information, then recurse left
        return toString(cur.right, "right: ", depth + 1)
            .append(" ".repeat(depth)).append(direction).append(cur)
            .append("\n").append(toString(cur.left, "left: ", depth + 1));
    
    }
    
    // Returns the maximum depth of the BST
    public int maxDepth() {
        // Call the recursive helper function
        return maxDepth(root);
    }
    
    // Recursive helper function for maxDepth
    private int maxDepth(Node<K, V> node) {
        // If the current node is null, return 0
        if (node == null) {
            return 0;
        } else {
            // Otherwise, calculate the depth of the left and right subtrees and return the greater value + 1
            int leftDepth = maxDepth(node.left);
            int rightDepth = maxDepth(node.right);
            return 1 + Math.max(leftDepth, rightDepth);
        }
    }

    // Removes the mapping for the specified key from this map if present.
    public V remove(K key) {
        // If the map is empty, return null
        if (root == null) {
            return null;
        }
        // Find the node to delete and its parent
        Node<K, V> toDelete = root;
        Node<K, V> toDeleteParent = null;
    
        while (toDelete != null && !toDelete.getKey().equals(key)) {
            toDeleteParent = toDelete;

            if(comparator.compare(toDelete.getKey(), key) > 0){
                toDelete = toDelete.left;
            } else {
                toDelete = toDelete.right;
            }
        }
        // If the key is not found, return null
        if (toDelete == null) {
            return null;
        }
        // Save the value to be returned
        V value = toDelete.getValue();
        // Handle the replacement of the node to delete
        handleReplacement(toDelete, toDeleteParent);
        // Decrement the size of the map
        size--;
        // Return the value that was associated with the key
        return value;
    }
    // Helper function for remove to handle replacing the node to delete
    private void handleReplacement(Node<K, V> toDelete, Node<K, V> toDeleteParent) {
        Node<K, V> replacement;
        // If the node to delete has no left child, the replacement is the right child
        if (toDelete.left == null) {
            replacement = toDelete.right;
        } 
        // If the node to delete has no right child, the replacement is the left child
        else if (toDelete.right == null) {
            replacement = toDelete.left;
        } 
        // If the node to delete has two children, find the in-order successor
        else {
            replacement = toDelete.right;
            Node<K, V> replacementParent = toDelete;
            while (replacement.left != null) {
                replacementParent = replacement;
                replacement = replacement.left;
            }
            handleReplacement(replacement, replacementParent);
            replacement.left = toDelete.left;
            replacement.right = toDelete.right;
        }
        // Update the parent of the node to delete to point to the replacement
        if (toDeleteParent == null) {
            root = replacement;
        } 
        else if (toDeleteParent.left == toDelete) {
            toDeleteParent.left = replacement;
        } 
        else {
            toDeleteParent.right = replacement;
        }
    }

    // public static void main(String[] args) {
    //     // this will sort the strings lexicographically (dictionary-order)
    //     BSTMap<String, Integer> words = new BSTMap<>();
    //     words.put("ten", 10);
    //     words.put("five", 5);
    //     words.put("three", 3);
    //     System.out.println("Start");
    //     System.out.println(words);
    
    //     // this will sort the strings in reverse lexicographic order
    //     BSTMap<String, Integer> wordsReverse = new BSTMap<>(new Comparator<String>() {
    
    //         @Override
    //         public int compare(String o1, String o2) {
    //             return o2.compareTo(o1);
    //         }
    
    //     });
    //     wordsReverse.put("ten", 10);
    //     wordsReverse.put("five", 5);
    //     wordsReverse.put("three", 3);
    //     System.out.println(wordsReverse);

    //     // wordsReverse.remove("ten");
    //     // System.out.println(wordsReverse);

    //     wordsReverse.remove("five");
    //     System.out.println(wordsReverse);

    //     int depth = 0;
    //     depth = wordsReverse.maxDepth();
    //     System.out.println(depth);
    // }
    
}