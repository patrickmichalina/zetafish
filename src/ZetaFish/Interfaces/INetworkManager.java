package ZetaFish.Interfaces;

import ZetaFish.NetworkObjects.*;

/**
 * Interface to Network Manager implementation. Allows multiple implementations (Sockets, XMPP, HTTP, etc.) to 
 * be used with minimal code changes.
 */
public interface INetworkManager 
{ 
	/**
	 * Open a connection to the game server given by the Server param.
	 * Design 7.1.3.1 v1.5
	 * 
	 * @param Server server name to connect to
	 * @param myUserName player's name 
	 * @param myPassword used to login to server (if necessary)
	 * @throws Exception
	 */   
  public void openConnection(String Server, String myUserName, String myPassword) throws Exception;

  /**
   * Close the connection to the current game sever.
   * Design 7.1.3.2 v1.5
   * 
   * @throws Exception
   */     
  public void closeConnection() throws Exception;

      
  /**
   * Starts the current game
   * @throws Exception
   */
  public void startGame() throws Exception;

  /**
   * Exits the current game
   * @throws Exception
   */
  public void exitGame() throws Exception;  
  
  /**
   * Is the game state valid and running?
   *  
   */
  public boolean getIsGameRunning();
   

  /**
   * Sends message to the chat board of all players
   * @param msg Message to send
   * @throws Exception
   */
  public void sendMessage(String msg) throws Exception;

  /**
   * Adds a listener for chat messages  
   * @param listener
   */
  public void addChatListener(IChatListener listener);

  
  /**
   * Add a subscriber to status events
   * @param listener
   */
  public void addStatusListener(IStatusListener listener);
  
  /**
   * Add a subscriber to turn events
   * @param listener
   */
  public void addTurnListener(ITurnListener listener);
  
  
  /**
   * Add a subscriber to remove player events
   * @param listener
   */
  public void addRemovePlayerListener(IRemovePlayerListener listener);
  
  /**
   * Add a subscriber to server error/exit events
   * @param listener
   */
  public void addServerErrorListener(IServerErrorListener listener);
  
  /**
   * Returns a player's score
   * @param p Player to get score for
   * @return score
   */
  public int getScore(int p);


  /**
   * Signals that the we are done with our turn
   * @throws Exception
   */
  public void DoneWithTurn() throws Exception;
  
  /**
   * Add a subscriber to the CardRequestResponse event
   * @param listener
   */
  public void addCardRequestResponseListener(ICardRequestResponseListener listener);

  /**
   * Ask another player for cards (or draw from the ocean)
   * @param From Player to ask
   * @param CardValue Value to ask for
   * @throws Exception
   */
  public void RequestCards(int From, int CardValue) throws Exception;
  
  /**
   * Play cards from hand into a book 
   * @param book cards to play
   * @throws Exception
   */
  public void PlayBook(ZFCard[] book) throws Exception;
  
  /**
   * Cards in your hand
   * @return cards
   */
  public ZFCard[] getHand();
  
  /**
   * Is the game over?
   * @return true if the game is over, false otherwise
   */
  public boolean getIsGameOver();
  
  /**
   * Your player number
   * @return int
   */
  public int getMyPlayerNumber();
  
  /**
   * Number of players in current game
   * @return int
   */
  public int getNumberOfPlayers();
  
  /**
   * Get information on other players.  (name, score, etc.)
   * @return ZFPlayer[]
   */
  public ZFPlayer[] getPlayers();
}

