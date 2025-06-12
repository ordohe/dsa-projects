/*
 * Olivia Doherty
 * Project 3
 * 
 * This is the lab which is the linkedlist which holds all the agents
 */

import java.util.ArrayList;
import java.util.Iterator;

public class LinkedList<T> implements Iterable<T>{


    private static class Node<T> {
        T data;
        Node<T> next;

        public Node(T item){
            data = item;
            next = null;
        }

        public Node(T item, Node<T> next){
            data = item;
            this.next = next;
        }

        public T getData(){
            return data;
        }

        public void setNext(Node<T> newNext){
            next = newNext;
        }

        public Node<T> getNext(){
            return next;
        }
    }
    /*
     * The number of items in this list
     */
    private int size;

    /*
     * The very first node in the list
     */
    private Node<T> head;

    public LinkedList(){
        size = 0;
        head = null;
    }

    public void add(T item){
        Node<T> newNode = new Node<T>(item);
        newNode.setNext(head);
        head = newNode;
        size++;
    }

    public T get(int index){
        if (index < 0 || index >= size){
            System.out.println("Index out of bounds");
            return null;
        }
        Node<T> walker = head;
        for(int i=0; i < index; i++){
            walker = walker.getNext();
        } //at the end of the for loop walker is the
        //indexth node in the list

        return walker.getData();
    }

    public T remove(){
        T toReturn = head.getData();
        head = head.getNext();
        size--;
        return toReturn;

    }

    public T remove(int index){
        if (index == 0){
            return remove();
        }
        if (index < 0 || index >= size){
            System.out.println("Index out of bounds");
            return null;
        }
        Node<T> walker = head;
        for(int i=0; i < index - 1; i++){
            walker = walker.getNext();
        } // right now, walker is the node at position index - 1

        // we want to get rid of the node immediately following walker
        Node<T> toDelete = walker.getNext();
        // which means walker's next should be the node following the one we want to delete
        Node<T> walkersNewNext = walker.getNext().getNext();
        walker.setNext(walkersNewNext);
        size--;
        return toDelete.getData();

    }

    public boolean contains(Object o){
        Node<T> walker = head;
        for(int i=0; i < size; i++){
            if (walker.getData().equals(o)){
                return true;
            }
            walker = walker.getNext();
        }
        return false;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        if (size==0){
            return true;
        }
        return false;
    }

    public void clear(){
        size = 0;
        head = null;
    }

    public void add(int index, T item){
        // System.out.println("hello");
        if (index == 0){
            add(item);
            return;
        }
        if (index < 0 || index > size){
            System.out.println("Cannot add at index " + index + ": index out of bounds");
        }
        Node<T> walker = head;
        for(int i=0; i < index-1; i++){
            walker = walker.getNext();
        }
        Node<T> newNode = new Node<T>(item);
        newNode.setNext(walker.getNext());
        walker.setNext(newNode);
        size++;

    }

    public boolean equals(Object o){
        if (!(o instanceof LinkedList)){
            return false;
        }
        // If I have reached this line, o must be a LinkedList
        LinkedList oAsALinkedList = (LinkedList) o;
        // Now I have a reference to something Java knows is a LinkedList!
        Node<T> walker = head;
        for(int i=0; i<size; i++){
            if(walker.getData() != oAsALinkedList.get(i)){
                return false;
            }
            walker = walker.getNext();
        }
        return true;
    }

    private class LLIterator implements Iterator<T> {
        
        private Node<T> current;

        public LLIterator(Node<T> head){
            current = head;
        }

        public boolean hasNext(){
            return current != null;
        }

        public T next(){
            T data = current.getData();
            current = current.getNext();
            return data;
        }

        
    }

    // Return a new LLIterator pointing to the head of the list
    public Iterator<T> iterator() {
        return new LLIterator( this.head );
    }

    public ArrayList<T> toArrayList(){
        ArrayList<T> arrayList = new ArrayList<T>();
        Node<T> walker = head;
        while (walker != null){
            arrayList.add(walker.getData());
            walker = walker.getNext();

        }
        return arrayList;
    }

    public String toString(){
        String string = "[";
        for(int i=0; i<size; i++){
            string += get(i);
            if(i<size-1){
                string += ", ";
            }
        }
        return string + "]";
    }

    public static void main(String[] args) {
        LinkedList<Integer> first = new LinkedList<Integer>();
        first.add(1);
        first.add(2);
        first.add(3);
        first.add(0, 10);
        System.out.println(first.toString());
        first.remove(1);
        System.out.println("hello");
        System.out.println(first.equals(first));
        System.out.println(first.toString());
    }
}
