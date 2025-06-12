/*
file name:      Hand.java
Name:        Olivia Doherty
last modified:  9/14/2023
Purpose: This file represents a hand of cards in a card game like Blackjack. 
It has methods for adding cards to the hand, calculating the total value of the cards, and resetting the hand, among others.
How to run:     java -ea CardTests
*/


import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards; // ArrayList to store the cards in the hand

    /**
     * Creates an empty hand as an ArrayList of Cards.
     */
    public Hand() {
        cards = new ArrayList<Card>();
    }

    /**
     * Removes any cards currently in the hand.
     */
    public void reset() {
        cards.clear();
    }

    /**
     * Adds the specified card to the hand.
     * 
     * @param card the card to be added to the hand
     */
    public void add(Card card) {
        cards.add(card);
    }

    /**
     * Returns the number of cards in the hand.
     * 
     * @return the number of cards in the hand
     */
    public int size() {
        return cards.size();
    }

    /**
     * Returns the card in the hand specified by the given index.
     * 
     * @param index the index of the card in the hand.
     * @return the card in the hand at the specified index.
     */
    public Card getCard(int index) {
        if (index >= 0 && index < cards.size()) {
            return cards.get(index);
        }
        return null; // Return null if the index is out of bounds
    }

    /**
     * Returns the summed value over all cards in the hand.
     * 
     * @return the summed value over all cards in the hand
     */
    public int getTotalValue() {
        int totalValue = 0;
        for (Card card : cards) {
            totalValue += card.getValue();
        }
        return totalValue;
    }

    /**
     * Returns a string representation of the hand.
     * 
     * @return a string representation of the hand
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hand: ");
        for (Card card : cards) {
            sb.append(card.toString()).append(" ");
        }
        return sb.toString().trim();
    }
}
