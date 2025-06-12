/*
file name:      Blackjack.java
Name:        Olivia Doherty
last modified:  9/14/2023
Purpose: This file implements the Blackjack game using the Card, Hand, and Deck classes.
How to run:     java -ea CardTests
*/


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

//public class Blackjack includes shuffle cutoff, method to reset the hands, 
public class Blackjack {
    private Deck deck; // deck of cards for the game
    private Hand playerHand; // player's hand
    private Hand dealerHand; // dealer's hand
    private int reshuffleCutoff; // number of cardds remaining in the deck to trigger a reshuffle
    
    // constructor for setting reshuffle cutoff and initializing a game
    public Blackjack(int reshuffleCutoff){ 
        this.reshuffleCutoff = reshuffleCutoff;
        reset(); // initialize new game
    }

    // default constructor with a reshuffle cutoff of 30 cards
    public Blackjack(){
        this(30); 
    }

    // reset game by creating new hands and shuffling deck if needed
    public void reset(){
        playerHand = new Hand(); 
        dealerHand = new Hand(); 
        if (deck == null || deck.size() < reshuffleCutoff) {
            deck = new Deck(); // create new deck
            deck.shuffle(); // shuffle deck
        }
    }

    // deal cards to the player and the dealer
    public void deal(){
        playerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
    }

    // player's turn
    public boolean playerTurn() {
        while (playerHand.getTotalValue() < 16) {
            Card card = deck.drawCard();
            playerHand.addCard(card);
            if (playerHand.getTotalValue() > 21) {
                return false; // Player busts
            }
        }
        return true;
    }

    // dealer's turn
    public boolean dealerTurn() {
        while (dealerHand.getTotalValue() < 17) {
            Card card = deck.drawCard();
            dealerHand.addCard(card);
            if (dealerHand.getTotalValue() > 21) {
                return false; // Dealer busts
            }
        }
        return true;
    }

    // set the reshuffle cutoff
    public void setReshuffleCutoff(int cutoff) {
        reshuffleCutoff = cutoff;
    }

    // get the reshuffle cutoff
    public int getReshuffleCutoff() {
        return reshuffleCutoff;
    }

    // provide a string representation of the game state
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Player Hand: ").append(playerHand).append("\n");
        sb.append("Player Total: ").append(playerHand.getTotalValue()).append("\n");
        sb.append("Dealer Hand: ").append(dealerHand).append("\n");
        sb.append("Dealer Total: ").append(dealerHand.getTotalValue()).append("\n");
        return sb.toString();
    }
    
    public Hand getPlayerHand() {
        return playerHand;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    // simulate a single game of Blackjack
    public int game(boolean verbose) {
        reset(); // reset game
        deal(); // deal initial cards

        if (verbose) {
            System.out.println("Initial State:");
            System.out.println(this);
        }

        boolean playerResult = playerTurn(); // player's turn
        boolean dealerResult = dealerTurn(); // dealer's turn

        // determine winner or tie
        if (playerResult && dealerResult) {
            int playerTotal = playerHand.getTotalValue();
            int dealerTotal = dealerHand.getTotalValue();

            if (playerTotal > dealerTotal) {
                if(verbose){
                    System.out.println("Player wins!");
                    System.out.println("Final State:");
                    System.out.println(this);
                }
                return 1; // Player wins
            } 
            else if (dealerTotal > playerTotal) {
                if (verbose) {
                    System.out.println("Dealer wins!");
                    System.out.println("Final State:");
                    System.out.println(this);
                }
                return -1; // Dealer wins
            }
            else {
                if (verbose) {
                    System.out.println("Push!");
                    System.out.println("Final State:");
                    System.out.println(this);
                }
                return 0; // Push (tie)
            }
        } 
        else {
            if (verbose) {
                System.out.println("Player or dealer busted!");
                System.out.println("Final State:");
                System.out.println(this);
            }
            return dealerResult ? 1 : -1; // Player or dealer busted, opposite wins
        }
    }

    // main method to run a specified number of games and display the results 
    public static void main(String[] args) {
        int playerWins = 0;
        int dealerWins = 0;
        int pushes = 0;
        int gamesToPlay = 3; // number of games to simulate

        // simulate specified number of games
        for (int i = 0; i < gamesToPlay; i++) {
            Blackjack blackjack = new Blackjack();
            int result = blackjack.game(false); // simulate game without verbose output
            if (result == 1) {
                playerWins++;
            } else if (result == -1) {
                dealerWins++;
            } else {
                pushes++;
            }
        }

        // display game results
        System.out.println("Player Wins: " + playerWins);
        System.out.println("Dealer Wins: " + dealerWins);
        System.out.println("Pushes: " + pushes);
    }

    // inner class representing a deck of cards
    class Deck {
    private List<Card> cards;

    // constructor to create a standard deck of cards
    public Deck() {
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    // shuffle deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // draw a card from the deck
    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        }
        return null;
    }

    // get the size of the deck
    public int size() {
        return cards.size();
    }
}

//class Hand to generate a set of cards for a hand
class Hand {
    private List<Card> cards;

    // constructor to initialize an empty hand
    public Hand() {
        cards = new ArrayList<>();
    }

    // add a card to the hand
    public void addCard(Card card) {
        if (card != null) {
            cards.add(card);
        }
    }

    // get total value of the hand, handling aces appropriately
    public int getTotalValue() {
        int total = 0;
        int numAces = 0;

        for (Card card : cards) {
            total += card.getValue();
            if (card.getRank() == Rank.Ace) {
                numAces++;
            }
        }

        // Handle aces as 1 or 11 based on the total value
        while (numAces > 0 && total + 10 <= 21) {
            total += 10;
            numAces--;
        }

        return total;
    }

    // provide string representation of the hand
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card).append(", ");
        }
        if (!cards.isEmpty()) {
            sb.setLength(sb.length() - 2); // Remove trailing comma and space
        }
        return sb.toString();
    }
}

// different suit types: referenced W3 schools java enum page
enum Suit {
    Spades, Hearts, Diamonds, Clubs
}

// different ranks for each card: referenced W3 schools java enum page
enum Rank {
    Two(2), Three(3), Four(4), Five(5), Six(6), Seven(7), Eight(8), Nine(9), Ten(10), Jack(10), Queen(10), King(10), Ace(11);

    private final int value;

    Rank(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

// class representing a card
class Card {
    private Rank rank;
    private Suit suit;

    // constructor to create a card with a specific rank and suit
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // get rank of the card
    public Rank getRank() {
        return rank;
    }

    // get suit of the card
    public Suit getSuit() {
        return suit;
    }

    // get numeric value of the card
    public int getValue() {
        return rank.getValue();
    }

    // provide string representation of the card
    @Override
    public String toString() {
        return rank + " of " + suit;
    }

}
public boolean playerTurnInteractive(Scanner scanner) {
    while (true) {
        System.out.println("Player Hand: " + playerHand);
        System.out.println("Player Total: " + playerHand.getTotalValue());
        System.out.print("Do you want to hit or stand? (h/s): ");
        String choice = scanner.nextLine().toLowerCase();

        if (choice.equals("h")) {
            Card card = deck.drawCard();
            playerHand.addCard(card);
            if (playerHand.getTotalValue() > 21) {
                return false; // Player busts
            }
        } else if (choice.equals("s")) {
            return true; // Player stands
        } else {
            System.out.println("Invalid choice. Please enter 'h' for hit or 's' for stand.");
        }
    }
}


} 
