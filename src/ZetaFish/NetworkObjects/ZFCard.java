package ZetaFish.NetworkObjects;

import java.io.Serializable;

/**
 * An object of type Card represents a playing card from a
 * standard Poker deck, including Jokers.  The card has a suit, which
 * can be spades, hearts, diamonds, clubs, or joker.  A space, heart,
 * diamond, or club has one of the 13 values: ace, 2, 3, 4, 5, 6, 7,
 * 8, 9, 10, jack, queen, or king.  Note that "ace" is considered to be
 * the smallest value.  A joker can also have an associated value; 
 * this value can be anything and can be used to keep track of several
 * different jokers.
 * 
 *  Taken in part from:  Introduction to Programming Using Java, Fifth Edition 
 *                       math.hws.edu/javanotes/source/Card.java
 *  
 *  Design 4.6.1 v1.5
 */                        
public class ZFCard implements Serializable, Comparable<ZFCard>
{      
	private static final long serialVersionUID = 1L;

	public enum Suits {SPADES, HEARTS, DIAMONDS, CLUBS, JOKER};	   
   
   public final static int ACE   = 1;          // Codes for the non-numeric cards.
   public final static int JACK  = 11;        //   Cards 2 through 10 have their
   public final static int QUEEN = 12;       //   numerical values for their codes.
   public final static int KING  = 13;
   
   /**
    * This card's suit, one of the constants SPADES, HEARTS, DIAMONDS,
    * CLUBS, or JOKER.  The suit cannot be changed after the card is
    * constructed.
    */
   private final Suits suit; 
   
   /**
    * The card's value.  For a normal cards, this is one of the values
    * 1 through 13, with 1 representing ACE.  For a JOKER, the value
    * can be anything.  The value cannot be changed after the card
    * is constructed.
    */
   private final int value;
   
   /**
    * Creates a Joker, with 1 as the associated value.  (Note that
    * "new Card()" is equivalent to "new Card(1,Card.JOKER)".)
    */
   public ZFCard() {
      suit = Suits.JOKER;
      value = 1;
   }
   
   /**
    * Creates a card with a specified suit and value.
    * @param theValue the value of the new card.  For a regular card (non-oker),
    * the value must be in the range 1 through 13, with 1 representing an Ace.
    * You can use the constants Card.ACE, Card.JACK, Card.QUEEN, and Card.KING.  
    * For a Joker, the value can be anything.
    * @param theSuit the suit of the new card.  This must be one of the values
    * Card.SPADES, Card.HEARTS, Card.DIAMONDS, Card.CLUBS, or Card.JOKER.
    * @throws IllegalArgumentException if the parameter values are not in the
    * permissable ranges
    */
   public ZFCard(int theValue, Suits theSuit) {
      if (theSuit != Suits.SPADES && theSuit != Suits.HEARTS && theSuit != Suits.DIAMONDS && 
            theSuit != Suits.CLUBS && theSuit != Suits.JOKER)
         throw new IllegalArgumentException("Illegal playing card suit");
      if (theSuit != Suits.JOKER && (theValue < 1 || theValue > 13))
         throw new IllegalArgumentException("Illegal playing card value");
      value = theValue;
      suit = theSuit;
   }

   /**
    * Returns the suit of this card.
    * @return the suit, which is one of the constants Card.SPADES, 
    * Card.HEARTS, Card.DIAMONDS, Card.CLUBS, or Card.JOKER
    */
   public Suits getSuit() {
      return suit;
   }
   
   /**
    * Returns the value of this card.
    * @return the value, which is one the numbers 1 through 13, inclusive for
    * a regular card, and which can be any value for a Joker.
    */
   public int getValue() {
      return value;
   }
   
   /**
    * Returns a String representation of the card's suit.
    * @return one of the strings "Spades", "Hearts", "Diamonds", "Clubs"
    * or "Joker".
    */
   public String getSuitAsString() {
      switch ( suit ) {
      case SPADES:   return "Spades";
      case HEARTS:   return "Hearts";
      case DIAMONDS: return "Diamonds";
      case CLUBS:    return "Clubs";
      default:       return "Joker";
      }
   }
   
   /**
    * Returns a String representation of the card's value.
    * @return for a regular card, one of the strings "Ace", "2",
    * "3", ..., "10", "Jack", "Queen", or "King".  For a Joker, the 
    * string is always a numerical.
    */
   public String getValueAsString() {
      if (suit == Suits.JOKER)
         return "" + value;
      else {
         switch ( value ) {
         case 1:   return "Ace";
         case 2:   return "2";
         case 3:   return "3";
         case 4:   return "4";
         case 5:   return "5";
         case 6:   return "6";
         case 7:   return "7";
         case 8:   return "8";
         case 9:   return "9";
         case 10:  return "10";
         case 11:  return "Jack";
         case 12:  return "Queen";
         default:  return "King";
         }
      }
   }
   
   /**
    * @return a string representation of this card, including both
    * its suit and its value (except that for a Joker with value 1,
    * the return value is just "Joker").  Sample reutrn values
    * are: "Queen of Hearts", "10 of Diamonds", "Ace of Spades",
    * "Joker", "Joker #2"
    */
   public String toString() {
      if (suit == Suits.JOKER) {
         if (value == 1)
            return "Joker";
         else
            return "Joker #" + value;
      }
      else
         return getValueAsString() + " of " + getSuitAsString();
   }
   

   /**
    * Compares two card values.
    * @param other
    * @return true if suit and value matches, false otherwise
    */
   public boolean equals(ZFCard other)
   {
	   boolean retval = ( (this.getSuit() == other.getSuit())
			   &&
			(this.getValue() == other.getValue()) );
	   
	   return retval;
   }

   /**
    * See description in Comparable<>
    */
	@Override
	public int compareTo(ZFCard arg0) {
	
		return this.getValue() - arg0.getValue();
	}

} // end class Card