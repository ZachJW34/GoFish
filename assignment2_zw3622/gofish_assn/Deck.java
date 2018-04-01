package gofish_assn;

import java.util.ArrayList;
import gofish_assn.Card.Suits;
import java.util.Collections;

/**
 * This class contains all the information that defines a standard deck of 52 cards. A deck contains 13 different ranks
 * of cards, with each rank having 4 different suits (13*4 = 52).
 * @see Card
 * @author ZachJ
 *
 */
public class Deck {

    /**
     * Container for storing a number of {@link Card} objects
     * @see ArrayList
     */
    private ArrayList<Card> deck = new ArrayList<Card> ();

    /**
     * Constructor
     *
     * Creates a {@link #deck} of 52 sorted cards (Ace of Clubs, Ace of Diamonds... King of Spades)
     */
	public Deck() {
	    for (int rank =0; rank<13; rank++) {
            for (int suit = 0; suit < 4; suit++) {
                switch (suit) {
                    case 0:
                        deck.add(new Card(rank + 1, Suits.club));
                        continue;
                    case 1:
                        deck.add(new Card(rank + 1, Suits.diamond));
                        continue;
                    case 2:
                        deck.add(new Card(rank + 1, Suits.heart));
                        continue;
                    case 3:
                        deck.add(new Card(rank + 1, Suits.spade));
                }
            }
        }
	}

    /**
     * Public Method
     *
     * Shuffles the {@link #deck}
     * @see Collections
     */
	public void shuffle() {
        Collections.shuffle(deck);
	}

    /**
     * @return the number of {@link Card} objects in the deck
     */
	public int sizeofDeck(){
	    return deck.size();
    }

    /**
     * @return a {@link Card} object while also removing it from the deck. Basically a pop
     */
	public Card dealCard() {
		return deck.remove(deck.size()-1);
	}

}
