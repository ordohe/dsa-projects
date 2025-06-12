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

    private class Node<K, V> extends KeyValuePair<K, V> {
        Node<K, V> next;
        public Node(K key, V value){
            super(key, value);
        }
    }

    private Node<K, V>[] buckets;
    private int size;
    private double maxLoadFactor;

    public HashMap(int initialCapacity, double maxLoadFactor){
        size = 0;
        this.maxLoadFactor = maxLoadFactor;
        buckets = (Node<K, V>[]) new Node[initialCapacity];
    }

    public HashMap(int initialCapacity){
        this(initialCapacity, .75);
    }

    public HashMap(){
        this(16);
    }

    private int capacity(){
        return buckets.length;
    }

    private int hash(K key){
        return Math.abs(key.hashCode() % capacity());
    }

    private void resize(int newCapacity) {
        Node<K, V>[] newBuckets = (Node<K, V>[]) new Node[newCapacity];
        for (int i = 0; i < buckets.length; i++) {
            Node<K, V> cur = buckets[i];
            while (cur != null) {
                Node<K, V> next = cur.next;
                int newIndex = Math.abs(cur.getKey().hashCode() % newCapacity);
                cur.next = newBuckets[newIndex];
                newBuckets[newIndex] = cur;
                cur = next;
            }
            buckets[i] = null;
        }
        buckets = newBuckets;
    }

    @Override
    public V put(K key, V value) {
        int index = hash(key);

        if (buckets[index] == null){
            buckets[index] = new Node<>(key, value);
            size++;
            if (size > maxLoadFactor * capacity()){
                resize(capacity() * 2);
            }
            return null;
        }
        else {
            for(Node<K, V> cur = buckets[index]; cur != null; cur = cur.next){
                if (cur.getKey().equals(key)){
                    V oldVal = cur.getValue();
                    cur.setValue(value);
                    return oldVal;
                }
            }

            Node<K, V> newNode = new Node<>(key, value);
            newNode.next = buckets[index];
            buckets[index] = newNode;
            size++;
            if (size > maxLoadFactor * capacity()){
                resize(capacity() * 2);
            }
            return null;
        }
    }

    @Override
    public boolean containsKey(K key) {
        int index = hash(key);

        if (buckets[index] == null){
            return false;
        }

        for(Node<K, V> curNode = buckets[index]; curNode != null; curNode = curNode.next){
            if (curNode.getKey().equals(key)){
                return true;
            } 
        }

        return false;
    }

    @Override
    public V get(K key) {
        int index = hash(key);
        if (buckets[index] == null){
            return null;
        }

        for(Node<K, V> curNode = buckets[index]; curNode != null; curNode = curNode.next){
            if (curNode.getKey().equals(key)){
                return curNode.getValue();
            } 
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int index = hash(key);
        Node<K, V> currentNode = buckets[index];

        // Search for the key in the linked list at the corresponding index
        Node<K, V> prevNode = null;
        while (currentNode != null) {
            if (currentNode.getKey().equals(key)) {
                if (prevNode == null) {
                    buckets[index] = currentNode.next;
                } else {
                    prevNode.next = currentNode.next;
                }
                size--;
                if (size < (capacity() * maxLoadFactor) / 4){
                    resize(capacity()/2);
                }
                return currentNode.getValue();
            }
            // size less than capacity times maxload  divide by 4
            // resize with capaticty divide by two
            prevNode = currentNode;
            currentNode = currentNode.next;
        }
        return null; // Key not found in the HashMap
    }

    @Override
    public ArrayList<K> keySet() {
        ArrayList<K> keys = new ArrayList<K>();
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                keys.add(current.getKey());
                current = current.next;
            }
        }
        return keys;
    }

    @Override
    public ArrayList<V> values() {
        ArrayList<V> values = new ArrayList<>();
        for (K key : keySet()) {
            values.add(get(key));
        }
        return values;
    }

    @Override
    public ArrayList<MapSet.KeyValuePair<K, V>> entrySet() {
        ArrayList<KeyValuePair<K, V>> entries = new ArrayList<>();
        for (K key : keySet()) {
            V value = get(key);
            entries.add(new KeyValuePair<>(key, value));
        }
        return entries;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        buckets = (Node<K, V>[]) new Node[capacity()];
    }

    @Override
    public int maxDepth() {
        int maxBucketSize = 0;
        for (int i = 0; i < buckets.length; i++) {
            int bucketSize = 0;
            for (Node<K, V> curNode = buckets[i]; curNode != null; curNode = curNode.next) {
                bucketSize++;
            }
            if (bucketSize > maxBucketSize) {
                maxBucketSize = bucketSize;
            }
        }
        return maxBucketSize;
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

    
    // public static void main(String[] args) {
    //     HashMap<String, Integer> map = new HashMap<>();

    //     // add some key-value pairs to the map
    //     map.put("apple", 1);
    //     map.put("banana", 2);
    //     map.put("orange", 3);
    //     map.put("peach", 4);
    //     map.put("pear", 5);

    //     // test get() method
    //     System.out.println(map.get("orange")); // output: 3

    //     // test containsKey() method
    //     System.out.println(map.containsKey("banana")); // output: true
    //     System.out.println(map.containsKey("grape")); // output: false

    //     // test keySet() method
    //     System.out.println(map.keySet()); // output: [apple, orange, banana, pear, peach]

    //     // test values() method
    //     System.out.println(map.values()); // output: [1, 3, 2, 5, 4]

    //     // test entrySet() method
    //     System.out.println(map.entrySet()); // output: [KeyValuePair{key=apple, value=1}, KeyValuePair{key=orange, value=3}, KeyValuePair{key=banana, value=2}, KeyValuePair{key=pear, value=5}, KeyValuePair{key=peach, value=4}]

    //     // test remove() method
    //     map.remove("pear");
    //     System.out.println(map.entrySet()); // output: [KeyValuePair{key=apple, value=1}, KeyValuePair{key=orange, value=3}, KeyValuePair{key=banana, value=2}, KeyValuePair{key=peach, value=4}]

    //     // test size() method
    //     System.out.println(map.size()); // output: 4

    //     // test clear() method
    //     map.clear();
    //     System.out.println(map.size()); // output: 0
    }
    //     // test size() method
    //     System.out.println(map.size()); // output: 4

    //     // test clear() method
    //     map.clear();
    //     System.out.println(map.size()); // output: 0
    // }
