package gamemodule; 
/** 
 *  Summary: Class Card is a basic data class that holds individual card status and information. 
 *  Imports: none
 *  Exports: Card Object. 
 *  {author= Zeta Dragons, version=1.0, since=2010}
 *  
 *  @author Melanie
 */

/**
 * @author Melanie
 *
 */
/**
 * @author Melanie
 *
 */
public class Card {
  

	 /**
	 * card number from 1  to 52
	 */
  private int num;

  /**
   * true if card has been dealt or picked
   *
   */
  private boolean inPlay;

  /**
   * player number of person holding this card 
   */
  private int player;

  /**
 * set to true if card is part of a book
 */
private boolean won;

//constructors
  public Card() {
	  this.num = -1;
	  this.inPlay = false; 
	  this.player = -1;
	  this.won = false; 
  }
  
  /**
   * constructor
 * @param nm card number 
 * @param inp is in play
 * @param player player number 
 * @param won is won 
 */
public Card(int nm, boolean inp, int player, boolean won) {
	  this.num = nm;
	  this.inPlay = inp; 
	  this.player = player;
	  this.won = won; 
  }

  /**
   * Returns true if the card has been won
 * @return boolean 
 */
public boolean isWon() {
	return this.won;
}

/**
 * Sets won variable 
 * @param won
 */
public void setWon(boolean won) {
	this.won = won;
}
/** 
 *  Returns number held in card object 
*/
public int getNum() {
	  return this.num;
  }

  /**
   * sets num variable 
 * @param number
 */
public void setNum(int number) {
	  this.num = number; 
  }

  /**
   * Returns true if the card is in play 
 * @return boolean 
 */
public boolean isinPlay() {
	  return this.inPlay;
  }

  /**
   * Sets inPlay variable 
 * @param play
 */
public void setinPlay(boolean play) {
	  this.inPlay = play; 
  }

  /**
   * Returns player variable 
 * @return int
 */

public int getPlayer() {
	  return this.player;
  }

  /**
   * Sets player variable 
 * @param plNum
 */
public void setPlayer(int plNum) {
	  this.player = plNum; 
  }

}