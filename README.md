# Swiggy Card Game 
## Problem Statement
The objective is to design a multiplayer card game that can support up to 4 players and different types of cards, such as number cards and action cards. The game should follow the following rules:

1. Each player starts with a hand of 5 cards.
2. The game starts with a deck of 52 cards, a standard deck of playing cards.
3. Players take turns playing cards from their hand, following a set of rules that define what cards can be played when.
4. A player can only play a card if it matches either the suit or the rank of the top card on the discard pile.
5. If a player cannot play a card, they must draw a card from the draw pile. If the draw pile is empty, the game ends in a draw, and no player is declared a winner.
6. The game ends when one player runs out of cards who is declared the winner.
Bonus: Aces, Kings, Queens and Jacks are action cards. When one of these is played, the following actions occur:

- Ace (A): Skip the next player in turn.
- Kings (K): Reverse the sequence of who plays next.
- Queens (Q): +2.
- Jacks (J): +4.
Note: actions are not stackable. For example, if Q is played by player 1, then player two draws two cards and cannot play a Q from his hand on that turn even if available.

## How to Run the Code
To run the code, follow these steps:

1. Clone the repository to your local machine.
2. Ensure that JDK version 1.8 or higher is installed on your machine.
3. Compile the code using the following command: javac Main.java
