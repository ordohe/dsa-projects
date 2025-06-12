/*
file name:      Simulation.java
Name:        Olivia Doherty
last modified:  9/14/2023
Purpose:  This file executes 1000 games of Blackjack using a single Blackjack object and keeps track of wins, 
losses, and pushes, printing out the results as raw numbers and percentages
How to run:     java -ea CardTests
*/


public class Simulation {
    public static void main(String[] args) {
        int totalGames = 1000;
        int playerWins = 0;
        int dealerWins = 0;
        int pushes = 0;

        Blackjack blackjack = new Blackjack();

        for (int i = 0; i < totalGames; i++) {
            int result = blackjack.game(false); // Simulate a game without verbose output

            if (result == 1) {
                playerWins++;
            } else if (result == -1) {
                dealerWins++;
            } else {
                pushes++;
            }

            blackjack.reset(); // Reset the game for the next round
        }

        // Print the results as raw numbers
        System.out.println("Total Games: " + totalGames);
        System.out.println("Player Wins: " + playerWins);
        System.out.println("Dealer Wins: " + dealerWins);
        System.out.println("Pushes: " + pushes);

        // Calculate and print the results as percentages
        double playerWinPercentage = (double) playerWins / totalGames * 100;
        double dealerWinPercentage = (double) dealerWins / totalGames * 100;
        double pushPercentage = (double) pushes / totalGames * 100;

        System.out.println("Player Win Percentage: " + playerWinPercentage + "%");
        System.out.println("Dealer Win Percentage: " + dealerWinPercentage + "%");
        System.out.println("Push Percentage: " + pushPercentage + "%");
    }
}


