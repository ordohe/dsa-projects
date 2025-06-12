import java.util.Scanner;

public class Interactive {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int gamesToPlay = 1; // Set the number of games to play

        for (int i = 0; i < gamesToPlay; i++) {
            Blackjack blackjack = new Blackjack();

            // Deal initial cards
            blackjack.deal();

            // Player's turn
            boolean playerResult = blackjack.playerTurnInteractive(scanner);
            if (!playerResult) {
                System.out.println("Player loses!");
                continue; // Player loses this round
            }

            // Dealer's turn
            boolean dealerResult = blackjack.dealerTurn();

            // Determine the winner or tie
            if (dealerResult) {
                int playerTotal = blackjack.getPlayerHand().getTotalValue();
                int dealerTotal = blackjack.getDealerHand().getTotalValue();

                if (playerTotal > dealerTotal) {
                    System.out.println("Player wins!");
                } else if (dealerTotal > playerTotal) {
                    System.out.println("Dealer wins!");
                } else {
                    System.out.println("Push!");
                }
            } else {
                System.out.println("Dealer loses! Player wins!");
            }
        }

        scanner.close();
    }
}
