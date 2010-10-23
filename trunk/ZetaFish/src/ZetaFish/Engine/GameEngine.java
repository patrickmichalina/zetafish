package gamemodule;
/** 
 *  Description: GameEngine class serves as the game actions part of the Game Module. 
 *  Provides all game activity including deck management and state, and all game 
 *  activities as detailed in the Game Subheading in Section 3.0 of the Requirements 
 *  Document. Is Class instantiated by the GameManager to maintain the game status, and activity. 
 *  
 *  Imports:  Java.util
 *  Exports: All Game actions. 
 *  
 *  author=Zeta Dragons, version=1.0, since=2010		
 */
public class GameEngine {
   
  /** 
   *  DeckManager Object 
   */
  public DeckManager myDeckManager;
  
  
  //constructor 
  public GameEngine() {
	  myDeckManager = new DeckManager(); 
  }
  /** 
   *  Assumes the number of players has already been set. deals current hand does not shuffle. 
   *  for new game/shuffle use newGame(); 
   */
  public void deal() {
	  this.myDeckManager.deal(false);
  }

  /** 
   *  Returns an int array of the cards in the specified players hand. 
   */
  public int[] getHand(int pn) {
	  int x[] = this.myDeckManager.getHand(pn);
  return x;
  }

  /** 
   *  Retrieves cards from the ocean and adds them to the indicated player's hand. 
   *  Takes two parameters, hm = int how many cards to get, and p= int the player 
   *  number of the player who will receive them. 
   */
  public boolean getCards(int nm, int p1) {
	  boolean x = this.myDeckManager.getCards(nm, p1); 
	  return x; 
  }

  /** 
   *  transfers cards of given number from one player to another.  Takes parameters nm= int card number, p1= int player giving cards number, and p2= int number getting cards player. Transfers all cards of given number in player one's hand to player 2.
   */
  public boolean giveCards(int nm, int p1, int p2) {
	  
	  boolean x = this.myDeckManager.giveCards(nm, p1, p2);
	  return x; 
  }

  public void addPlayer() {
	  this.myDeckManager.addPlayer(); 
  }

  public boolean removePlayer(int p) {
	  
	  boolean x = this.myDeckManager.removePlayer(p);
	  return x; 
  }


//starts new game with new deck shuffle 
  public boolean newGame() {
	  boolean x = this.myDeckManager.deal(true);
	  return x; 
  }
/**
 * returns an int[][] array of game state by player number as index 
 * and the following are the card numbers in the hand. assumes game has been
 * initiated and players added and deal called. 
 */
  public int[][] getGame() {
	  
	  int i = this.myDeckManager.getPlayers(); 
	  int [][] x = new int[i][];	  
	  for(int j = 1; j<=i;j++){
		  x[j]= this.myDeckManager.getHand(j);
	  }
	  
  return x;
  }

  /** 
   *  returns total number of books for the player, or -1 for no books. 
   */
  public int checkForBooks(int pl) {
	  
	  //get any new books
	  int x = this.myDeckManager.getBooks(pl);
	  //get all books 
	  x = this.myDeckManager.getAllBooks(pl);
	  
  return x;
  }

}