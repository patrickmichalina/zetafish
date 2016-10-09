package ZetaFish.NetworkObjects;

import java.io.Serializable;
import java.util.*;

/**
 *  An object of type Deck represents a deck of playing cards.  The deck
 *  is a regulate poker deck that contains 52 regular cards and that can
 *  also optionally include two Jokers.
 *  
 *  Taken in part from:  Introduction to Programming Using Java, Fifth Edition 
 *                       math.hws.edu/javanotes/source/Deck.java
 *                       
 *  Design 4.6.6 v1.5                       
 */
public class ZFDeck  implements Serializable 
{
	private static final long serialVersionUID = 1L;
	   
	private ZFCard[] deck;   
      
	private List<ZFCard> unused_deck = null;
   
   /**
    * Constructs a regular 52-card poker deck.  Initially, the cards
    * are in a sorted order.  The shuffle() method can be called to
    * randomize the order.
    * 
    * Design 7.1.15.1 v1.5
    */
   public ZFDeck() 
   {      	  
	  /* Design 7.1.15 v1.5 */
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
    * are still left in the deck.  
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
   
   /**
    * Returns cards to the deck.
    * @param cards The cards to return to the deck
    */
   public void replaceCards(ZFCard[] cards)
   {
	   for(ZFCard card : cards)
	   {
		   this.replaceCard(card);
	   }
   }
   
   /**
    * Returns a single card to the deck.
    * @param card Card to return to the deck.
    */
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
         
} // end class Deck
