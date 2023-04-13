package org.example;

import java.util.*;

public class Game {
    private final List<Player> players;
    private final List<Card> drawPile;
    private final List<Card> discardPile;
    private int currentPlayerIndex;
    private boolean reverseOrder;
    private boolean skipNextPlayer;
    private int cardsToDraw;

    public Game(String[] playerNames) {
        this.players = new ArrayList<>();
        for (String playerName : playerNames) {
            this.players.add(new Player(playerName));
        }
        this.drawPile = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                this.drawPile.add(new Card(suit, rank));
            }
        }
        this.discardPile = new ArrayList<>();
        this.reverseOrder = false;
        this.skipNextPlayer = false;
        this.cardsToDraw = 0;
    }

    public void start() {
        Collections.shuffle(drawPile);
        for (Player player : players) {
            for (int i = 0; i < 5; i++) {
                drawCardForPlayer(player);
            }
        }
        discardPile.add(drawPile.remove(drawPile.size() - 1));
        playGame();
    }

    private void playGame() {
        Map<String, Integer> cardValues = new HashMap<>();
        cardValues.put("ACE", 1);
        cardValues.put("TWO", 2);
        cardValues.put("THREE", 3);
        cardValues.put("FOUR", 4);
        cardValues.put("FIVE", 5);
        cardValues.put("SIX", 6);
        cardValues.put("SEVEN", 7);
        cardValues.put("EIGHT", 8);
        cardValues.put("NINE", 9);
        cardValues.put("TEN", 10);
        cardValues.put("JACK", 11);
        cardValues.put("QUEEN", 12);
        cardValues.put("KING", 13);
        Scanner scanner = new Scanner(System.in);
        while (!isGameOver()) {
            Player currentPlayer = players.get(currentPlayerIndex);
            System.out.println(currentPlayer.getName() + "'s turn.");
            if (skipNextPlayer) {
                System.out.println("Skipping next player.");
                skipNextPlayer = false;
            } else {
                if (cardsToDraw > 0) {
                    System.out.println("Drawing " + cardsToDraw + " cards...");
                    for (int i = 0; i < cardsToDraw; i++) {
                        drawCardForPlayer(currentPlayer);
                    }
                    cardsToDraw = 0;
                }
                System.out.println(currentPlayer.getName() + "'s hand: " + currentPlayer.getHand());
                System.out.println("Top card on discard pile: " + discardPile.get(discardPile.size() - 1));
                System.out.println("What card do you want to play? (Enter -1 to draw a card)");
                int cardIndex = scanner.nextInt();

                if (cardIndex == -1) {
                    drawCardForPlayer(currentPlayer);
                } else {
                    Card card = currentPlayer.getHand().get(cardIndex);
                    if (canPlayCard(card)) {
                        currentPlayer.playCard(cardIndex);
                        discardPile.add(card);
                        handleActionCard(card);
                    } else {
                        System.out.println("Invalid card selection. You must play a card that matches either the suit or the rank of the top card on the discard pile.");
                    }
                }
            }
            printGameState();
            advanceToNextPlayer();
        }
        System.out.println("Game over.");
    }

    public boolean isGameOver() {
        for (Player player : players) {
            if (player.getHand().isEmpty()) {
                System.out.println(player.getName() + " wins!");
                return true;
            }
        }
        if (drawPile.isEmpty()) {
            System.out.println("The draw pile is empty. The game ends in a draw.");
            return true;
        }
        return false;
    }

    public boolean canPlayCard(Card card) {
        Card topCardOnDiscardPile = discardPile.get(discardPile.size() - 1);
        return card.getSuit() == topCardOnDiscardPile.getSuit() || card.getRank() == topCardOnDiscardPile.getRank();
    }

    private void handleActionCard(Card card) {
        switch (card.getRank()) {
            case ACE -> skipNextPlayer = true;
            case KING -> reverseOrder = !reverseOrder;
            case QUEEN -> cardsToDraw += 2;
            case JACK -> cardsToDraw += 4;
            default -> {
            }
        }
    }

    private void advanceToNextPlayer() {
        int increment = reverseOrder ? -1 : 1;
        currentPlayerIndex += increment;
        if (currentPlayerIndex < 0) {
            currentPlayerIndex = players.size() - 1;
        } else if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }
    }

    private void drawCardForPlayer(Player player) {
        if (drawPile.isEmpty()) {
            System.out.println("The draw pile is empty.");
        } else {
            Card card = drawPile.remove(drawPile.size() - 1);
            player.addToHand(card);
        }
    }

    private void printGameState() {
        System.out.println("Players:");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getHand().size() + " cards");
        }
        System.out.println("Draw pile: " + drawPile.size() + " cards");
        System.out.println("Discard pile: " + discardPile.get(discardPile.size() - 1));
    }

    private static int getNumberOfPlayers() {
        System.out.println("Enter number of players or press -1 to end the game: ");
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static void main(String[] args) {

        int numberOfPlayers = getNumberOfPlayers();

        while ((numberOfPlayers < 2 || numberOfPlayers > 4) && numberOfPlayers != -1) {

            numberOfPlayers = getNumberOfPlayers();
        }
        if (numberOfPlayers != -1) {

            String[] playerNames = {"Player 1", "Player 2", "Player 3", "Player 4"};
            String[] players = Arrays.copyOf(playerNames, numberOfPlayers);
            Game game = new Game(players);
            game.start();
        } else {
            System.out.println("Thanks for Playing the game!!");
        }
    }
}
