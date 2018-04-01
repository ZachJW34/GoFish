package gofish_assn;

import java.util.logging.Logger;

/**
 * <p>GoFishGame incorporates all of the methods to implement the famous game of Go Fish. The rules of the game are simple:
 * Two players receive 7 cards from a standard 52 card deck. If the players have any pairs (a pair is defined as any two cards with the same rank
 * e.g. 7 of Clubs, 7 of Diamonds), they book them (cards are no longer in circulation). Player 1 randomly chooses a card from their hand and asks
 * Player 2 if they have a card with the same rank (Do you have a 7?). If yes, Player 2 must give Player 1 that card and Player 1, Player 1 must
 * book the pair, and Player 1 gets to go again. A turn ends when a player asks the other player for a card that the other player does not have. They
 * then must Go Fish (take a card from the deck). The turn then goes to Player 2 who does the same thing. The Game ends when all 52 cards are booked by
 * either of the players, and whoever has the most books wins. Important Edge cases are:
 *      <ul>
 *          <li>Player 1 asks Player 2 for a card and Player 2 has that card, but its Player 2's only card left. Player 2 must then
 *              draw a card from the deck so that Player 1 can continue his turn.
 *          </li>
 *          <li>
 *              Player 1 asks Player 2 for a card and Player 2 has that card, but after Player 1 receives the card and books it, he has no cards left.
 *              Player 1 must then draw a card from the deck and pass the turn to Player 2
 *          </li>
 *          <li>
 *              Player 1 and Player 2 only have 1 card. Player 1 asks Player 2 for a card and Player 2 has the card. Both players must then pick up a card
 *              and the turn passes to player 2.
 *          </li>
 *      </ul>
 *      @see Player
 *      @see Deck
 */
public class GoFishGame {


    /**
     * @see {@link Deck}
     */
    private Deck deck;
    private String namePlayer1;
    private String namePlayer2;
    /**
     * @see {@link Logger}
     */
    private Logger logger;

    /**
     * Instantiates the logger object so that file writing can occur, names both players, creates a new deck, and shuffles the deck
     * @param player1 is a string for player 1's name
     * @param player2 is a string for player 2's name
     * @see Deck#Deck()
     * @see Deck#shuffle()
     */
	public GoFishGame(String player1, String player2) {
        logger = Logger.getLogger("MyLog");
        this.namePlayer1 = player1;
        this.namePlayer2 = player2;
        deck = new Deck();
        deck.shuffle();
    }

    /**
     * Implementation of the GoFish game. Tow players are created and assigned names based of the constructor. The players are both given
     * seven cards each, and they take turns playing until all of 52 cards are booked.
     */
	public void playGame(){
	    //Initialize players
	    Player player1 = new Player(namePlayer1);
	    populateDeck(player1);
	    Player player2 = new Player(namePlayer2);
	    populateDeck(player2);
	    logger.info("\n");

	    //Check for any books before game starts
        while(player1.checkHandForBook());
        while(player2.checkHandForBook());
        logger.info("\n");

        //Play Game
        //Will continue until all 52 cards are gone which satisfies condition below
        while(deck.sizeofDeck() != 0 || player1.getHandSize() != 0 || player2.getHandSize() != 0){
            playerTurn(player1, player2);
            logger.info("\n");
            if (player1.getHandSize() != 0 && player2.getHandSize() != 0) //In case game ends after player 1's turn
                playerTurn(player2, player1);
            logger.info("\n");
        }

        //Check to see who wins
        if (player1.getBookSize() > player2.getBookSize()){
            printWinner(player1, player2);
        } else if (player2.getBookSize() > player1.getBookSize()){
            printWinner(player2, player1);
        } else {
            printTie(player1, player2);
        }
    }

    /**
     * Gives each {@link Player} seven cards from a shuffled deck
     * @param player is a {@link Player} that gets assigned 7 cards
     */
    private void populateDeck(Player player){
	    Card card;
	    for (int i = 0; i < 7; i++){
            card = deck.dealCard();
            logger.info(player.getName() + " draws a " + card.rankToString());
            player.addCardToHand(card);
        }
    }

    /**
     * Heavy lifting of the game functionality. A lot of edge cases to check, so the code is a bit long.
     * @param asker is the {@link Player} who's turn it is
     * @param teller is the {@link Player} who's getting asked if he has any of the asker's cards
     */
    private void playerTurn(Player asker, Player teller){
        //Temp storage for asking teller if he has a randomly picked card from askers hand
	    Card toCheck = asker.chooseCardFromHand();
	    //Temp storage for getting a card from the deck. Easier to print card to output file
        Card card;
        logger.info(asker.getName() + " asks: Do you have a " + toCheck.rankToString() + "?");
        //while teller has the card the asker asks for, it continues to be askers turn
        while(teller.rankInHand(toCheck)){
            asker.addCardToHand(teller.removeCardFromHand(toCheck));
            asker.checkHandForBook();
            //Edge case where askers hand size becomes zero and the deck still has cards in it
            if (asker.getHandSize() == 0 && deck.sizeofDeck() != 0){
                card = deck.dealCard();
                logger.info(asker.getName() + " ran out of cards. He draws a " + card.rankToString());
                asker.addCardToHand(card);
                return;
            //Edge case for when teller gives up a card is is hand size reaches zero. Must draw card from deck if deck still has cards
            } else if (teller.getHandSize() == 0 && deck.sizeofDeck() != 0){
                card = deck.dealCard();
                logger.info(teller.getName() + " ran out of cards. He draws a " + card.rankToString());
                teller.addCardToHand(card);
            //Edge case where all cards have been depleted.
            } else if (asker.getHandSize() == 0 && teller.getHandSize() == 0 && deck.sizeofDeck() ==0){
                return;
            }
            toCheck = asker.chooseCardFromHand();
            logger.info(asker.getName() + " asks: Do you have a " + toCheck.rankToString() + "?");
        }
        //After player draws card (Go Fish), must check for book if the deck still has cards in it
        if (deck.sizeofDeck() != 0) {
            card = deck.dealCard();
            logger.info(asker.getName() + " draws a " + card.rankToString());
            asker.addCardToHand(card);
            asker.checkHandForBook();
            //Repeat of edge case
            if (asker.getHandSize() == 0 && deck.sizeofDeck() != 0) {
                card = deck.dealCard();
                logger.info(asker.getName() + " ran out of cards. He draws a " + card.rankToString());
                asker.addCardToHand(card);
                //Edge case where both players hands reach zero and both must draw from deck
                if (teller.getHandSize() == 0){
                    card = deck.dealCard();
                    logger.info(teller.getName() + " ran out of cards. He draws a " + card.rankToString());
                    teller.addCardToHand(card);
                }
            }
        } else{
            logger.info("There are no cards in the deck...");
        }
    }

    /**
     * Prints who won and the player's books, prints who lost and the player's books
     * @param winner is the {@link Player} who won
     * @param loser is the {@link Player} who lost
     */
    private void printWinner(Player winner, Player loser){
        logger.info(winner.getName() + " wins with a book size of " + winner.getBookSize()/2 + "!\n"
                + winner.getName() +  "'s books are...\n" + winner.bookToString());
        logger.info(loser.getName() + " loses with a book size of " + loser.getBookSize()/2 + ".\n"
                +  loser.getName() +  "'s books are...\n" + loser.bookToString());
    }

    /**
     * Prints that both players tied and their books.
     * @param player1 is a {@link Player} who tied
     * @param player2 is a {@link Player} who tied
     */
    private void printTie(Player player1, Player player2){
	    logger.info(player1.getName() + " and " + player2.getName() + " tie with a book sizes of " + player1.getBookSize()/2 + ".");
	    logger.info(player1.getName() + "'s books are...\n" + player1.bookToString());
	    logger.info(player2.getName()+ "'s books are...\n" + player2.bookToString());
    }
}
