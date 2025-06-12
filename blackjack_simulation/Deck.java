/*
file name:      Deck.java
Name:        Olivia Doherty
last modified:  9/14/2023
Purpose: This file represents and manages a standard deck of playing cards, including creating a new deck,
shuffling it, dealing cards from it, and providing a string representation of the deck.
How to run:     java -ea CardTests
*/


import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards; // ArrayList to store the cards in the deck

    /**
     * Creates the underlying deck as an ArrayList of Card objects. Calls build() as
     * a subroutine to build the deck itself.
     */
    public Deck() {
        cards = new ArrayList<Card>();
        build(); // Call the build() method to initialize the deck
    }

    /**
     * Builds the underlying deck as a standard 52 card deck. Replaces any current
     * deck stored.
     */
    public void build() {
        cards.clear(); // Clear any existing cards in the deck

        // Create a standard 52 card deck with values from 2 to 11
        for (int value = 2; value <= 11; value++) {
            Card card = new Card(value);
            cards.add(card);
        }
    }

    /**
     * Returns the number of cards left in the deck.
     * 
     * @return the number of cards left in the deck
     */
    public int size() {
        return cards.size();
    }

    /**
     * Returns and removes the first card of the deck.
     * 
     * @return the first card of the deck
     */
    public Card deal() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        }
        return null; // Return null if the deck is empty
    }

    /**
     * Shuffles the cards currently in the deck.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Returns a string representation of the deck.
     * 
     * @return a string representation of the deck
     */
    @Override
    public String toString() {
        // Implement a method to convert the entire deck to a string representation
        // You can customize this based on your needs
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card.toString()).append(" ");
        }
        return sb.toString().trim();
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.shuffle();

        // Deal and print cards from the deck
        while (deck.size() > 0) {
            Card card = deck.deal();
            System.out.print(card + " ");
        }
    }
}
