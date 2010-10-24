package gamemodule; 
/** 
 *  Summary: Provides a manager class for the Game module. Game Manager manages and 
 *  monitors all game objects and actions. Receives requests from the NetworkManager 
 *  Module for game actions, and data sent from other game peers to be stored in the 
 *  GameTracker object. Holds instances of the GameEngine class, and the ProtocolManager 
 *  class and instigates all actions to be performed by the module. 
 *  Imports: none 
 *  Exports: All viable game actions as detailed in the Game subheading and Player 
 *  subheading of Section 3 of the Requirements document. 
 *  
 *  @author Melanie
 */
public class GameManager {
  /* {author=Zeta Dragons, version=1.0, since=2010}*/


  /** 
   *  set to true if the player is the initiator of the game. 
   */
  public boolean initiator;

  /** 
   *  set to the player number of the owner . 
   */
  public int playerNumber;

  /** 
   *  GameEngine object instantiation of the GameEngine class.
   */
  public GameEngine myGameEngine;
  /** 
   *  ProtocolManager Object 
   */
  public ProtocolManager myProtocolManager;
  
  //constructors
  
  public GameManager() {
	  this.myGameEngine = new GameEngine(); 
	  this.myProtocolManager = new ProtocolManager(); 
	  this.initiator = false;
	  this.playerNumber = -1; 
  }
  /**
 * @param init boolean true if player is game initiator
 * @param playernm player number 
 */
public GameManager(boolean init, int playernm) {
	  
	  this.myGameEngine = new GameEngine(); 
	  this.myProtocolManager = new ProtocolManager(); 
	  this.initiator = init;
	  this.playerNumber = playernm; 
  }
  /** 
   *  Re deals the current shuffle to existing players. Must call update hand to get new hands.
   *  //NOT USED 
   */
    public void reDeal() {
	  //this.myGameEngine.deal(); 
  }
    /** 
     *  assumes all players have been added. and initiator and playernumber variable have been set. .
     */
  public void deal() {
	  this.myGameEngine.newGame();  
  }
  /** 
   *  resets the class variables. 
   */
  public void reset(boolean isInitiator, int pn) {
	  
	  this.myGameEngine = new GameEngine(); 
	  this.myProtocolManager = new ProtocolManager(); 
	  this.initiator = isInitiator;
	  this.playerNumber = pn; 
  }
  /** 
   *  Returns an int array of the cards in the specified players hand. 
   *  ONLY used by initiator. 
   */
  public int[] getHand(int p) {
	  int hand[] = this.myGameEngine.getHand(p);
  return hand;
  }
  /** 
   *  Returns an int  the number of books of the specified player. returns -1 if no points earned. 
   *  Only used by initiator 
   */
  public int getScore(int p) {
	  int x = this.myGameEngine.checkForBooks(p); 
  return x;
  }

  /** 
   *  Retrieves cards from the ocean and adds them to the indicated 
   *  player's hand. Takes two parameters, hm = int how many cards to 
   *  get, and p= int the player number of the player who will receive
   *   them. 
   *   ONLY used by initiator. 
   */
  public boolean getCards(int hm, int p) {
	  return this.myGameEngine.getCards(hm, p);
	  
  }

  /** 
   *  Transfers specified cards from one player to the other. Takes 
   *  parameters as follows: wc= int card number, p1 = int player number 
   *  of player sending cards, p2= int player number of player receiving cards. 
   *  ONLY used by initiator. 
   */
  public boolean giveCards(int wc, int p1, int p2) {
	  boolean trade = this.myGameEngine.giveCards(wc, p1, p2);
	  return trade; 
  }

  /** 
   *  Adds player of the provided player number and name to the game. 
   *  Modifies game engine if isInitiator, else modifies protocolManager. 
   */
  public void addPlayer(int pn, String name) {
	  if(this.isInitiator()){
		  this.myGameEngine.addPlayer(); 
	  }
	  this.myProtocolManager.addPlayer(pn, name);
  }

  /** 
   *  Removed indicated player from the game and returns their cards to the ocean.
   *  Modifies game engine if isInitiator, else modifies protocolManager. 
   */
  public void removePlayer(int pn) {
	  if(this.isInitiator()){
		  this.myGameEngine.removePlayer(pn);
	  }
	  this.myProtocolManager.removePlayer(pn);
  }
  /** 
   *  Updates all player hands as stored in the ProtocolManager with hands passed
   *  @param int[][] hands. 
   */
  public void updateProtocolHands(int hands[][]) {
	  this.myProtocolManager.setAllHands(hands);
  }
  /** 
   *  Saves game state to GameTracker. Updates Protocol game state if is initiator. 
   */
  public void saveGame() {
	  
	  if(this.isInitiator()){
		  int x[][] = this.myGameEngine.getGame();
		  this.myProtocolManager.setAllHands(x);
	  }
	  this.myProtocolManager.saveGame(); 
  }
  /** 
   *  Returns a string array of saved games. 
   */
  public String[] getSavedGames() {	  
	  return this.myProtocolManager.getSavedGames(); 
  }
  /** 
   *  Returns a string representation of the current game state saved in the 
   *  ProtocolManager  
   */
  public String getGameState() {
	  return this.myProtocolManager.getGameState();
  }
  /** 
   *  Loads the given game name in the protocolManager as given
   *  by the getSavedGames method. Updates game engine if 
   *  isInitiator. 
   */
  public void loadSavedGame(String game) {
	  int x[][] = this.myProtocolManager.loadSavedGame(game);
	  if(this.isInitiator()){
		  this.myGameEngine.setExistingGame(x); 
		  System.out.println("Saved game loaded to game engine");
	  }

  }

  /** 
   *  Updates the score of the given player number
   *  in protocol manager 
   */
  public void updateScore(int pn, int sc) {
	  this.myProtocolManager.setScore(pn, sc);
  }
  /** 
   *  sets protocol to given game state. if owner is initiator sets the game engine to
   *  state by calling setexistingGame() method.  
  */
  public void setGameState(String state) {
	  
	  this.myProtocolManager.updateGame(state);
	  if(this.isInitiator()){
		  int x[][] = this.myProtocolManager.getGame();
		  this.myGameEngine.setExistingGame(x); 
	  }
  }
  /** 
   *  Sets game engine to current state held in the protocol manager  
  */
  public void setGameEngine() {
	  int gameState[][] = this.myProtocolManager.getGame(); 
	  this.myGameEngine.setExistingGame(gameState);
  } 
/**
 * @return the initiator
 */
public boolean isInitiator() {
	return initiator;
}
/**
 * @param initiator the initiator to set
 */
public void setInitiator(boolean initiator) {
	this.initiator = initiator;
}
/**
 * @return the playerNumber
 */
public int getPlayerNumber() {
	return playerNumber;
}
/**
 * @param playerNumber the playerNumber to set
 */
public void setPlayerNumber(int playerNumber) {
	this.playerNumber = playerNumber;
}
/**
 * @return number of players in game engine 
 */
public int getNumberofPlayers(){
	return this.myGameEngine.getNumberofPlayers(); 
}
}