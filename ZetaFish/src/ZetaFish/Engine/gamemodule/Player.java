package ZetaFish.Engine.gamemodule; 
/** 
 *  Summary: Player class provides a player object that holds player data for each player. 
 *  Is used to monitor and store player status and state during the game.
 *  Imports: none
 *  Exports: Player Object.
 *  
 *   @author Melanie
 */
public class Player {
  /* {author=Zeta Dragons, version=1.0, since=2010}*/


  private String name;

  private int score;

  private int playerNumber;

  private int hand [];

 //constructors sets all variables to null 
  public Player() {
		this.name = "";
		this.score = 0;
		this.playerNumber = 0;
		this.hand = null;
  }

  /**
 * @param name
 * @param score
 * @param playerNumber
 * @param hand
 */
public Player(String name, int score, int playerNumber, int[] hand) {
	
	this.name = name;
	this.score = score;
	this.playerNumber = playerNumber;
	this.hand = hand;
}

/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}

/**
 * @param score the score to set
 */
public void setScore(int score) {
	this.score = score;
}

/**
 * @param playerNumber the playerNumber to set
 */
public void setPlayerNumber(int playerNumber) {
	this.playerNumber = playerNumber;
}

/**
 * @param hand the hand to set
 */
public void setHand(int[] hand) {
	this.hand = hand;
}

/**
 * @return the name
 */
public String getName() {
	return name;
}

/**
 * @return the score
 */
public int getScore() {
	return score;
}

/**
 * @return the playerNumber
 */
public int getPlayerNumber() {
	return playerNumber;
}

/**
 * @return the hand
 */
public int[] getHand() {
	return hand;
}

/**
 * @return number of cards in player hand
 */
public int getNumberOfCards() {
	
	return this.hand.length;
}



}