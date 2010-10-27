package ZetaFish.Engine.gamemodule;
/** 
 *  Description: GameEngine class serves as the game actions part of the Game Module. 
 *  Provides all game activity including deck management and state, and all game 
 *  activities as detailed in the Game Subheading in Section 3.0 of the Requirements 
 *  Document. Is Class instantiated by the GameManager to maintain the game status, and activity. 
 *  
 *  Imports:  Java.util
 *  Exports: All Game actions. 
 *  @author Melanie
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
   *  Assumes the number of players has already been set. 
   *  deals current hand does not shuffle. 
   *  for new game/shuffle use newGame(); 
   */
  public void deal() {
	  this.myDeckManager.deal(false);
  }
  /** 
   *  Sets deckManager to provided game state
   */
  public void setExistingGame(int[][] gameState) {
	  
	  this.myDeckManager.setExistingGame(gameState);
  }
  /** 
   *  Returns an int array of the cards in the specified players hand. 
   */
  public int[] getHand(int pn) {
	  int x[] = this.myDeckManager.getHand(pn);
  return x;
  }
  /** 
   *  Returns an int array of the active cards in the specified players hand. 
   *  No won cards are returned. 
   */
  public int[] getActiveHand(int pn) {
	  int x[] = this.myDeckManager.getActiveHand(pn);
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
   *  transfers cards of given number from one player to another.  
   *  Takes parameters nm= int card number, p1= int player giving cards
   *   number, and p2= int number getting cards player. Transfers all 
   *   cards of given number in player one's hand to player 2.
   */
  public boolean giveCards(int nm, int p1, int p2) {
	  
	  boolean x = this.myDeckManager.giveCards(nm, p1, p2);
	  return x; 
  }

  /**
 * Adds a player to the deckmanager object.
 */
public void addPlayer() {
	  this.myDeckManager.addPlayer(); 
  }

  /**
   * Returns true if the player is removed succesfully.
   * @param p player number 
   * @return boolean
   */
public boolean removePlayer(int p) {
	  
	  boolean x = this.myDeckManager.removePlayer(p);
	  return x; 
  }


//starts new game with new deck shuffle 
  /**
   * starts new game with new deck shuffle 
   * @return boolean
   */
public boolean newGame() {
	  boolean x = this.myDeckManager.deal(true);
	  return x; 
  }
/**
 * returns an int[][] array of game state by player number as index 
 * and the following are the card numbers in the hand. assumes game has been
 * initiated and players added and deal called. Returns all cards in the players hand.
 */
  public int[][] getGame() {
	  System.out.println("Getting game.");
	  int i = this.myDeckManager.getPlayers(); 
	  int [][] x = new int[i][];	  
	  for(int j = 0; j<x.length;j++){
		  int z[] = this.myDeckManager.getHand(j+1);
		  x[j]= new int[z.length]; 
		  for(int p =0; p<z.length; p++){
			  x[j][p] = z[p]; 
			  System.out.println(z[p]); 
		  }
	  }
	  System.out.println("Done getting game"); 
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
/**
 * @return int number of players 
 */
public int getNumberofPlayers(){
	return this.myDeckManager.getPlayers(); 
}
}