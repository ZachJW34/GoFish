package gofish_assn;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Player class defines how a player manages the {@link Card} objects given to it in the scope of a game of go fish. It also helps in
 * printing information to an output file to show the flow of the game.
 * @author ZachJ
 * @see Card
 * @see Logger
 */
public class Player {

    /**
     * hand stores an array of {@link Card} objects that the player receives at the beginning of the {@link GoFishGame}, receives by
     * requesting the other player for a card, and gives away either by putting them in the book or giving them to another player
     */
	private ArrayList<Card> hand = new ArrayList<>();

    /**
     * book stores multiple pairs of {@link Card} objects. A pair is defined as any two cards that have the same rank e.g. Ace of Spaced, Ace of Diamonds
     */
	private ArrayList<Card> book = new ArrayList<>();
	private String name;

    /**
     * Used for randomly picking a card from the {@link #hand}
     * @see Random
     */
	private Random seed;

    /**
     * @see Logger
     */
	private Logger logger;

    /**
     * Creates a Player object based on the input string. Also creates a RNG and defines the logger file
     * @param name is a string that defines a player
     */
	public Player(String name) {
	    this.seed = new Random();
		this.name = name;
		logger = Logger.getLogger("MyLog");
	}

    /**
     * Pushes a card into the players hand
     * @param c is a {@link Card} object that defines the card that is pushed into the players hand
     */
	public void addCardToHand(Card c) {
        hand.add(c);
	}

    /**
     * Method that will remove a card from the players hand and return it.
     * @param c is a {@link Card} object that defines the card that is popped out of the players hand
     * @return a {@link Card} that matches the **rank of the card that is passed in, or null if not found.
     */
	public Card removeCardFromHand(Card c) {
	    Card returnCard = null;
	    for (int i = 0; i < hand.size(); i++){
	        if (hand.get(i).getRank() == c.getRank()){
	            returnCard = hand.remove(i);
	            break;
            }
        }
        return returnCard;
	}

	
	public String getName() {
		return name;
	}

    /**
     * Method that converts all of the {@link Card} objects in the players hand to strings using {@link Card#toString()}
     * @return a string of all the cards in the hand, formatted with a newline after each card.
     * @see StringBuilder
     */
	public String handToString() {
	    StringBuilder stringBuilder = new StringBuilder();
		for (Card card: hand)
		    stringBuilder.append(card.toString() + "\n");
		return stringBuilder.toString();
	}

    /**
     * Method that converts all of the {@link Card} objects in the players book to strings using {@link Card#toString()}
     * @return a string of all the cards in the book, formatted with a newline after each card.
     * @see StringBuilder
     */
	public String bookToString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Card card: book)
		    stringBuilder.append(card.toString() + "\n");
		return stringBuilder.toString();
	}
	
	public int getHandSize() {
		return hand.size();
	}

	public int getBookSize() {
		return book.size();
	}

    /**
     * Method that checks to see if a pair of {@link Card} objects exists in the players hand (A pair is defined as
     * two cards with the same rank). If true, it will remove these cards and add them to the players book. Also helps logging information
     * @return true if a pair is in the players hand, false in not
     */
    public boolean checkHandForBook() {
    	for (int i = 0; i < hand.size()-1; i++){
    	    for (int j = (i+1); j < hand.size(); j++){
    	        if (hand.get(i).getRank() == hand.get(j).getRank()){
                    logger.info(name + " books a " + hand.get(i).rankToString());
                    book.add(hand.remove(i));
    	            book.add(hand.remove(j-1));
    	            return true;
                }
            }
        }
        return false;
    }

    /**
     * Method checks if the {@link Card} passed in has a match in the players hand. (A pair is defined as two cards with the same rank).
     * @param c is a {@link Card}
     * @return a boolean: true if match exists, false if not
     */
    public boolean rankInHand(Card c) {
    	for (Card card: hand){
    	    if (card.getRank() == c.getRank()) {
                logger.info(name + " says: Yes, I have a " + c.rankToString());
                return true;
            }
        }
        logger.info(name + " says: Go Fish");
        return false;
    }

    /**
     * Chooses a card randomly from the players hand
     * @return a {@link Card}
     * @see Random
     * @see Math
     */
    public Card chooseCardFromHand() {
        int random = Math.abs(seed.nextInt()%hand.size());
        return hand.get(random);
    }


}
