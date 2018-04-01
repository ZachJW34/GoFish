package gofish_assn;

/**
 * This class contains all information that defines a standard card in a 52 card deck. A card is defined by its rank
 * and its suit. For this particular implementation of a card, the ranking of cards go by [Ace, 2, 3, 4, 5, 6, 7, 8,
 * 9, 10, Jack, Queen, King]. Furthermore, the ranking of suits go by [Club, Diamond, Heart, Spade].
 * @author ZachJ
 */
public class Card {

    /**
     * Enum for the suit type in cards. There are 4 suits in a normal deck of 52 cards.
     */
	public enum Suits {
        /**
         * Club is the lowest rank of suits.
         */
	    club,

        /**
         * Diamond is the second lowest rank of suits.
         */
        diamond,

        /**
         * Heart is the second highest rank of suits.
         */
        heart,

        /**
         * Spade is the highest rank of suits.
         */
        spade}

    /**
     * Helper array to grab the string representation of a cards rank.
     */
    final static private String[] RANKTOSTRING = {"Ace","2","3","4","5","6","7","8","9","10","Jack","Queen","King"};

    /**
     * Rank is part of what identifies a card. Their are 4 cards in a 52 card deck with the same rank differentiated by their suit.
     */
    private int rank;

    /**
     * Suit is another identifier of a card. Their are 12 cards in a 52 deck with the same suit.
     */
    private Suits suit;

    /**
     * Empty constructor returns a card with a {@link #rank} of 1 (Ace) and a {@link #suit} of type Spade
     */
	public Card() {
		this.rank = 1;
		this.suit = Suits.spade;
	}

    /**
     * @param rank sets the {@link #rank} of the Card object
     * @param suit sets the {@link #suit} of the Card object
     * @see #toSuit(char)
     */
	public Card(int rank, char suit) {
        this.rank = rank;
        this.suit = toSuit(suit);
	}

    /**
     * @param rank sets the {@link #rank} of the Card object
     * @param suit sets the {@link #suit} of the Card object
     */
	public Card(int rank, Suits suit) {
        this.rank = rank;
        this.suit = suit;
	}

    /**
     * @param c is the char representation of a suit
     * @return a {@link #suit} that relates to the char passed in, or a Spade if the incorrent char is passed in
     */
	private Suits toSuit(char c) {
		switch (c) {
            case 'c': return Suits.club;
            case 'd': return Suits.diamond;
            case 'h': return Suits.heart;
            case 's': return Suits.spade;
        }
        System.out.println("No such card exists");
		return Suits.spade;
	}

    /**
     * @return the string representation of {@link #suit}
     */
	private String suitToString() {
		switch(suit){
            case club: return "Clubs";
            case diamond: return "Diamonds";
            case heart: return "Hearts";
            case spade: return "Spades";
        }
        return "Clubs";
	}

    /**
     * @return a string representation of the rank of the card using the {@link #RANKTOSTRING} helper array
     */
	public String rankToString() {
		return RANKTOSTRING[rank-1];
	}

	public int getRank() {
		return rank;
	}
	
	public Suits getSuit() {
		return suit;
	}

	public String toString() {
		return rankToString() + " of " + suitToString();
	}
}
