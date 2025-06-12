/*
 * 
 * file name: HashMap.java
 * author: Olivia Doherty
 * last modified: 11/5/23
 * purpose: maps a set of keys to specific values
 * o run from the terminal:
 * javac HashMap.java
 * java HashMap
 * 
 */

import java.util.ArrayList;

public class HashMap<K, V> implements MapSet<K, V>{

    // Inner class representing a node in the hash map, which is an extension of KeyValuePair
    private class Node<K, V> extends KeyValuePair<K, V> {
        Node<K, V> next; // reference to the next node in the linked list

        public Node(K key, V value){
            super(key, value); // call to the KeyValuePair constructor
        }
    }

    private Node<K, V>[] buckets; // Array of nodes that represents the buckets of the hash map
    private int size; // the number of key-value pairs in the hash map
    private double maxLoadFactor; // the load factor beyond which rehashing occurs

    // Constructor with initial capacity and max load factor
    public HashMap(int initialCapacity, double maxLoadFactor){
        size = 0; // initialize size to 0
        this.maxLoadFactor = maxLoadFactor; // set the max load factor
        // Create an array of nodes with the specified initial capacity
        buckets = (Node<K, V>[]) new Node[initialCapacity];
    }

    // Constructor with initial capacity only, using default load factor
    public HashMap(int initialCapacity){
        this(initialCapacity, 0.75); // default load factor is 0.75
    }

    // Default constructor with default initial capacity and load factor
    public HashMap(){
        this(16); // default initial capacity is 16
    }

    // Returns the current capacity (size of the buckets array)
    private int capacity(){
        return buckets.length;
    }

    // Resizes the array of buckets to the new capacity
    private void resize(int newCapacity) {
        Node<K, V>[] newBuckets = (Node<K, V>[]) new Node[newCapacity];
        // Rehash all existing entries into the new buckets array
        for (int i = 0; i < buckets.length; i++) {
            Node<K, V> cur = buckets[i];
            while (cur != null) {
                Node<K, V> next = cur.next;
                int newIndex = Math.abs(cur.getKey().hashCode() % newCapacity);
                cur.next = newBuckets[newIndex];
                newBuckets[newIndex] = cur;
                cur = next;
            }
        }
        buckets = newBuckets; // set the new buckets as the current bucket array
    }

    // Implementation of the put method from the MapSet interface
    @Override
    public V put(K key, V value) {
        int index = hash(key); // calculate the index for the key

        if (buckets[index] == null){
            // If no node exists at the index, create a new node with the key-value pair
            buckets[index] = new Node<>(key, value);
            size++;
            // Resize the array if the load factor threshold is exceeded
            if (size > maxLoadFactor * capacity()){
                resize(capacity() * 2);
            }
            return null; // Since it's a new key, return null
        }
        else {
            // If nodes exist at the index, search for the key
            for(Node<K, V> cur = buckets[index]; cur != null; cur = cur.next){
                if (cur.getKey().equals(key)){
                    V oldVal = cur.getValue(); // If found, replace the value
                    cur.setValue(value);
                    return oldVal; // Return the old value
                }
            }

            // If the key was not found, create a new node and insert it at the beginning of the list
            Node<K, V> newNode = new Node<>(key, value);
            newNode.next = buckets[index];
            buckets[index] = newNode;
            size++;
            // Resize the array if the load factor threshold is exceeded
            if (size > maxLoadFactor * capacity()){
                resize(capacity() * 2);
            }
            return null; // Since it's a new key, return null
        }
    }

    // Checks if a key is present in the map
    @Override
    public boolean containsKey(K key) {
        int index = hash(key); // calculate the index for the key

        // Iterate over the linked list at the calculated bucket index
        for(Node<K, V> curNode = buckets[index]; curNode != null; curNode = curNode.next){
            if (curNode.getKey().equals(key)){
                return true; // return true if the key is found
            } 
        }

        return false; // return false if the key is not found
    }

    // Gets the value associated with the specified key
    @Override
    public V get(K key) {
        int index = hash(key); // calculate the index for the key
        // Iterate over the linked list at the calculated bucket index
        for(Node<K, V> curNode = buckets[index]; curNode != null; curNode = curNode.next){
            if (curNode.getKey().equals(key)){
                return curNode.getValue(); // return the value if the key is found
            } 
        }
        return null; // return null if the key is not found
    }

    // Removes the mapping for the specified key from this map if present
    @Override
    public V remove(K key) {
        int index = hash(key); // calculate the index for the key
        Node<K, V> currentNode = buckets[index]; // get the first node in the list at the bucket index
        Node<K, V> prevNode = null; // keep track of the previous node

        while (currentNode != null) {
            if (currentNode.getKey().equals(key)) {
                // If the key is found, unlink the current node from the list
                if (prevNode == null) {
                    // If the node to remove is the first node in the list
                    buckets[index] = currentNode.next;
                } else {
                    // If the node to remove is not the first node
                    prevNode.next = currentNode.next;
                }
                size--; // decrement the size of the map
                // Resize the array if the size is less than a quarter of the capacity times max load factor
                if (size < (capacity() * maxLoadFactor) / 4){
                    resize(capacity()/2);
                }
                return currentNode.getValue(); // return the removed value
            }
            prevNode = currentNode; // move to the next node in the list
            currentNode = currentNode.next;
        }
        return null; // return null if the key was not found
    }

    // Returns a set of keys contained in this map
    @Override
    public ArrayList<K> keySet() {
        ArrayList<K> keys = new ArrayList<>();
        // Iterate over all the buckets
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            // Iterate over the linked list in each bucket
            while (current != null) {
                keys.add(current.getKey()); // add each key to the list
                current = current.next;
            }
        }
        return keys; // return the list of keys
    }

    // Returns a list of values contained in this map
    @Override
    public ArrayList<V> values() {
        ArrayList<V> values = new ArrayList<>();
        // Iterate over the set of keys and collect their corresponding values
        for (K key : keySet()) {
            values.add(get(key));
        }
        return values; // return the list of values
    }

    // Returns a set of key-value pairs contained in this map
    @Override
    public ArrayList<MapSet.KeyValuePair<K, V>> entrySet() {
        ArrayList<KeyValuePair<K, V>> entries = new ArrayList<>();
        // Iterate over the set of keys and add the key-value pairs to the list
        for (K key : keySet()) {
            V value = get(key);
            entries.add(new KeyValuePair<>(key, value));
        }
        return entries; // return the list of entries
    }

    // Returns the number of key-value mappings in this map
    @Override
    public int size() {
        return size;
    }

    // Removes all of the mappings from this map
    @Override
    public void clear() {
        size = 0; // reset the size to 0
        buckets = (Node<K, V>[]) new Node[capacity()]; // create a new array of nodes
    }

    // Returns the depth of the deepest bucket (longest linked list)
    @Override
    public int maxDepth() {
        int maxBucketSize = 0;
        // Iterate over all buckets to find the longest linked list
        for (Node<K, V> curNode : buckets) {
            int bucketSize = 0;
            while (curNode != null) {
                bucketSize++; // count the number of nodes in the current linked list
                curNode = curNode.next;
            }
            if (bucketSize > maxBucketSize) {
                maxBucketSize = bucketSize; // update the maximum bucket size if current is larger
            }
        }
        return maxBucketSize; // return the max bucket size (deepest linked list)
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < buckets.length; i++) {
            Node<K, V> curNode = buckets[i];
            while (curNode != null) {
                sb.append(curNode.getKey());
                sb.append("=");
                sb.append(curNode.getValue());
                if (curNode.next != null) {
                    sb.append(", ");
                }
                curNode = curNode.next;
            }
            if (i < buckets.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();

        // add some key-value pairs to the map
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("orange", 3);
        map.put("peach", 4);
        map.put("pear", 5);

        // test get() method
        System.out.println(map.get("orange")); // output: 3

        // test containsKey() method
        System.out.println(map.containsKey("banana")); // output: true
        System.out.println(map.containsKey("grape")); // output: false

        // test keySet() method
        System.out.println(map.keySet()); // output: [apple, orange, banana, pear, peach]

        // test values() method
        System.out.println(map.values()); // output: [1, 3, 2, 5, 4]

        // test entrySet() method
        System.out.println(map.entrySet()); // output: [KeyValuePair{key=apple, value=1}, KeyValuePair{key=orange, value=3}, KeyValuePair{key=banana, value=2}, KeyValuePair{key=pear, value=5}, KeyValuePair{key=peach, value=4}]

        // test remove() method
        map.remove("pear");
        System.out.println(map.entrySet()); // output: [KeyValuePair{key=apple, value=1}, KeyValuePair{key=orange, value=3}, KeyValuePair{key=banana, value=2}, KeyValuePair{key=peach, value=4}]

        // test size() method
        System.out.println(map.size()); // output: 4

        // test clear() method
        map.clear();
        System.out.println(map.size()); // output: 0

        // test getMaxDepth() method
        System.out.println("Max Depth: " + map.maxDepth());
    }
}