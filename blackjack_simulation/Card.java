/*
file name:      Card.java
Name:        Olivia Doherty
last modified:  9/14/2023
Purpose: This file keeps track of the card's numeric value, 
making sure it's a valid number for the game. 
You can ask it for the card's value or get a string showing the card's value.
How to run:     java -ea CardTests

*/



public class Card {
    private int value; // Private instance variable to store the numeric value of the card.

    // Constructor with parameter 'v' to initialize the card's value.
    public Card(int v) {
        // Check if the provided value is within the valid range (2-11).
        if (v < 2 || v > 11) {
            throw new IllegalArgumentException("Card value must be in the range 2-11.");
        }
        this.value = v; // Initialize the 'value' variable with the provided value.
    }

    // Getter method to retrieve the numeric value of the card.
    public int getValue() {
        return value;
    }

    // Override the default toString method to return the string representation of the card's value.
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
