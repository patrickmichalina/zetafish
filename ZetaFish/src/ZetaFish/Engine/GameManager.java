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
 */
public class GameManager {
  /* {author=Zeta Dragons, version=1.0, since=2010}*/


  /** 
   *  set to true if the player is the initiator of the game. 
   */
  public boolean initiator;

  public int playerNumber;

  /** 
   *  GameEngine object instantiation of the GameEngine class.
   */
  public GameEngine myGameEngine;
  /** 
   *  ProtocolManager Object 
   */
  public ProtocolManager myProtocolManager;
  
  //constructor 
  
  public GameManager() {
  }
  
  public void deal() {
  }

  /** 
   *  Returns an int array of the cards in the specified players hand. 
   */
  public int getHand(int p) {
  return 0;
  }

  public int getScore(int p) {
  return 0;
  }

  /** 
   *  Retrieves cards from the ocean and adds them to the indicated player's hand. Takes two parameters, hm = int how many cards to get, and p= int the player number of the player who will recieve them. 
   */
  public void getCards(int hm, int p) {
  }

  /** 
   *  Transfers specified cards from one player to the other. Takes parameters as follows: wc= int card number, p1 = int player number of player sending cards, p2= int player number of player receiving cards. 
   */
  public void giveCards(int wc, int p1, int p2) {
  }

  /** 
   *  Adds player of the provided player number to the game. 
   */
  public void addPlayer(int pn, String name) {
  }

  /** 
   *  Removed indicated player from the game and returns their cards to the ocean. 
   */
  public void removePlayer(int pn) {
  }

  public void saveGame() {
  }

  public String getSavedGames() {
  return null;
  }

  public int getGameState() {
  return 0;
  }

  public void loadSavedGame(String game) {
  }

  public void newGame(boolean isInitiator, int pn) {
  }

 

  public void updateScore() {
  }

  public void setGameState(String state) {
  }

}