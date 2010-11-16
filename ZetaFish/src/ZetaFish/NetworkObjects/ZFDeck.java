package ZetaFish.NetworkObjects;

import java.io.Serializable;
import java.util.*;

/**
 *  An object of type Deck represents a deck of playing cards.  The deck
 *  is a regulate poker deck that contains 52 regular cards and that can
 *  also optionally include two Jokers.
 */
public class ZFDeck  implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
   /**
    * An array of 52 or 54 cards.  A 54-card deck contains two Jokers,
    * in addition to the 52 cards of a regular poker deck.
    */
   private ZFCard[] deck;   
      
   private List<ZFCard> unused_deck = null;
   
   /**
    * Constructs a regular 52-card poker deck.  Initially, the cards
    * are in a sorted order.  The shuffle() method can be called to
    * randomize the order.  (Note that "new Deck()" is equivalent
    * to "new Deck(false)".)
    */
   public ZFDeck() {
      this(false);  // Just call the other constructor in this class.
   }
   
   /**
    * Constructs a poker deck of plaing cards, The deck contains
    * the ususal 52 cards and can optionally contain two Jokers
    * in addtion, for a total of 54 cards.    Initially the cards
    * are in a sorted order.  The shuffle() method can be called to
    * randomize the order.
    * @param includeJokers if true, two Jokers are included in the deck; if false,
    * there are no Jokers in the deck.
    */
   public ZFDeck(boolean includeJokers) {
      if (includeJokers)
         deck = new ZFCard[54];
      else
         deck = new ZFCard[52];
      int cardCt = 0; // How many cards have been created so far.

      for(ZFCard.Suits suit:ZFCard.Suits.values())
      {
    	  if(suit != ZFCard.Suits.JOKER)
    	  {
	         for ( int value = 1; value <= 13; value++ ) {
	            deck[cardCt] = new ZFCard(value,suit);
	            cardCt++;
	         }
    	  }
      }
      if (includeJokers) {
         deck[52] = new ZFCard(1,ZFCard.Suits.JOKER);
         deck[53] = new ZFCard(2,ZFCard.Suits.JOKER);
      }      
   }
   
   
   
   /**
    * Put all the used cards back into the deck (if any), and
    * shuffle the deck into a random order.
    */
   public void shuffle() {
      for ( int i = deck.length-1; i > 0; i-- ) {
         int rand = (int)(Math.random()*(i+1));
         ZFCard temp = deck[i];
         deck[i] = deck[rand];
         deck[rand] = temp;
      }
      unused_deck = new ArrayList<ZFCard>(Arrays.asList(deck));      
   }
   
   /**
    * As cards are dealt from the deck, the number of cards left
    * decreases.  This function returns the number of cards that
    * are still left in the deck.  The return value would be
    * 52 or 54 (depending on whether the deck includes Jokers)
    * when the deck is first created or after the deck has been
    * shuffled.  It decreases by 1 each time the dealCard() method
    * is called.
    */
   public int cardsLeft() {      
	   return unused_deck.size();
   }
   
   /**
    * Removes the next card from the deck and return it.  It is illegal
    * to call this method if there are no more cards in the deck.  You can
    * check the number of cards remaining by calling the cardsLeft() function.
    * @return the card which is removed from the deck.
    * @throws IllegalStateException if there are no cards left in the deck
    */
   public ZFCard dealCard() {
	   if(unused_deck == null)
		   this.shuffle();
	   
      if (cardsLeft() == 0)
         throw new IllegalStateException("No cards are left in the deck.");

      return unused_deck.remove(0);    
   }
   
   public void replaceCards(ZFCard[] cards)
   {
	   for(ZFCard card : cards)
	   {
		   this.replaceCard(card);
	   }
   }
   
   public void replaceCard(ZFCard card)
   {
	   if(unused_deck != null)
	   {
		   // add the card to the bottom of the deck
		   unused_deck.add(unused_deck.size() - 1, card);				   
	   }	
	   else
		   throw new IllegalStateException("Deck not initialized!");
   }
   
   
   public void mixUpRemainingCards()
   {
	   for ( int i = unused_deck.size()-1; i > 0; i-- ) {
	         int rand = (int)(Math.random()*(i+1));
	         ZFCard temp = unused_deck.get(i);
	         unused_deck.set(i, unused_deck.get(rand));
	         unused_deck.set(rand, temp);
	      }	   
   }
   
   
   /**
    * Test whether the deck contains Jokers.
    * @return true, if this is a 54-card deck containing two jokers, or false if
    * this is a 52 card deck that contains no jokers.
    */
   public boolean hasJokers() {
      return (deck.length == 54);
   }
   
} // end class Deck
