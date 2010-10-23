package gamemodule; 
/** 
 *  Summary: Class Card is a basic data class that holds individual card status and information. 
 *  Imports: none
 *  Exports: Card Object. 
 *  {author= Zeta Dragons, version=1.0, since=2010}
 */
public class Card {
  
//card number from 1  to 52
  private int num;
//card has been dealt of picked up
  private boolean inPlay;
//player number of person holding this card 
  private int player;
//card is part of a book 
  private boolean won;

//constructors
  public Card() {
	  this.num = -1;
	  this.inPlay = false; 
	  this.player = -1;
	  this.won = false; 
  }
  public Card(int nm, boolean inp, int player, boolean won) {
	  this.num = nm;
	  this.inPlay = inp; 
	  this.player = player;
	  this.won = won; 
  }
  public boolean isWon() {
	return this.won;
}

public void setWon(boolean won) {
	this.won = won;
}
/** 
 *  Returns number held in card object 
*/
public int getNum() {
	  return this.num;
  }

  public void setNum(int number) {
	  this.num = number; 
  }

  public boolean isinPlay() {
	  return this.inPlay;
  }

  public void setinPlay(boolean play) {
	  this.inPlay = play; 
  }

  public int getPlayer() {
	  return this.player;
  }

  public void setPlayer(int plNum) {
	  this.player = plNum; 
  }

}