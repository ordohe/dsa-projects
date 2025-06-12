/*
 * Olivia Doherty
 * 10/3/23
 * LinkedList.java
 * 
 * LinkedList.java holds all the agents for later use in a singly-linked list and can now be
 * used with the Sudoku game
 * 
 * To run from the terminal:
 * javac LinkedList.java
 * java LinkedList
 */

import java.util.Iterator;    
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class LinkedList<T> implements Stack<T>, Iterable<T> {

    // Inner class representing a node in the linked list
    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T item) {
            data = item;
            next = null;
        }

        public T getData() {
            return data;
        }

        public void setNext(Node<T> n) {
            next = n;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    // Inner class implementing the Iterator interface
    private class LLIterator implements Iterator<T> {
        private Node<T> current;

        public LLIterator(Node<T> head) {
            current = head;
        }

        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T data = current.getData();
            current = current.getNext();
            return data;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported.");
        }
    }

    private Node<T> head;
    private int size;

    public LinkedList() {
        head = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return head.getData(); // Assuming head is the top of the stack
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return remove(); // Remove and return the item at the top of the stack
    }

    @Override
    public void push(T item) {
        add(item); // Add the given item to the top of the stack
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        head = null;
        size = 0;
    }

    public void add(T item) {
        Node<T> newNode = new Node<T>(item);
        newNode.setNext(this.head);
        this.head = newNode;
        size++;
    }

    public T remove() {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty");
        }

        T removedItem = head.getData();
        head = head.getNext();
        size--;
        return removedItem;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        return current.getData();
    }

    public void add(int index, T item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        if (index == 0) {
            add(item);
        } else {
            Node<T> current = head;

            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            Node<T> newNode = new Node<T>(item);
            newNode.setNext(current.getNext());
            current.setNext(newNode);
            size++;
        }
    }

    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        if (index == 0) {
            return remove();
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }

            T removedItem = current.getNext().getData();
            current.setNext(current.getNext().getNext());
            size--;
            return removedItem;
        }
    }

    public boolean contains(Object o) {
        Node<T> current = head;
        while (current != null) {
            if (current.getData().equals(o)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LinkedList)) {
            return false;
        }

        LinkedList<?> oAsList = (LinkedList<?>) o;
        Node<T> current = head;
        int i = 0;
        while (current != null) {
            if (!current.getData().equals(oAsList.get(i))) {
                return false;
            }
            current = current.getNext();
            i++;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            stringBuilder.append(current.getData());
            if (i < size - 1) {
                stringBuilder.append(", ");
            }
            current = current.getNext();
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new LLIterator(head);
    }

    // New method to convert the linked list to an ArrayList
    public ArrayList<T> toArrayList() {
        ArrayList<T> arrayList = new ArrayList<>();
        Node<T> current = head;

        while (current != null) {
            arrayList.add(current.getData());
            current = current.getNext();
        }

        return arrayList;
    }
}