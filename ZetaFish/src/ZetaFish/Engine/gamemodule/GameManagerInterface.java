package ZetaFish.Engine.gamemodule;

public interface GameManagerInterface {

	/** 
	 *  Re deals the current shuffle to existing players. Must call update hand to get new hands. 
	 */
	public abstract void reDeal();

	/** 
	 *  assumes all players have been added. and initiator and playernumber variable have been set. .
	 */
	public abstract void deal();

	/** 
	 *  resets the class variables. 
	 */
	public abstract void reset(boolean isInitiator, int pn);

	/** 
	 *  Returns an int array of the cards in the specified players hand. 
	 *  ONLY used by initiator. 
	 */
	public abstract int[] getHand(int p);

	/** 
	 *  Returns an int  the number of books of the specified player. returns -1 if no points earned. 
	 *  Only used by initiator 
	 */
	public abstract int getScore(int p);

	/** 
	 *  Retrieves cards from the ocean and adds them to the indicated 
	 *  player's hand. Takes two parameters, hm = int how many cards to 
	 *  get, and p= int the player number of the player who will receive
	 *   them. 
	 *   ONLY used by initiator. 
	 */
	public abstract void getCards(int hm, int p);

	/** 
	 *  Transfers specified cards from one player to the other. Takes 
	 *  parameters as follows: wc= int card number, p1 = int player number 
	 *  of player sending cards, p2= int player number of player receiving cards. 
	 *  ONLY used by initiator. 
	 */
	public abstract void giveCards(int wc, int p1, int p2);

	/** 
	 *  Adds player of the provided player number and name to the game. 
	 *  Modifies game engine if isInitiator, else modifies protocolManager. 
	 */
	public abstract void addPlayer(int pn, String name);

	/** 
	 *  Removed indicated player from the game and returns their cards to the ocean.
	 *  Modifies game engine if isInitiator, else modifies protocolManager. 
	 */
	public abstract void removePlayer(int pn);

	/** 
	 *  Updates all player hands as stored in the ProtocolManager with hands passed
	 *  @param int[][] hands. 
	 */
	public abstract void updateProtocolHands(int hands[][]);

	/** 
	 *  Saves game state to GameTracker. Updates Protocol game state if is initiator. 
	 */
	public abstract void saveGame();

	/** 
	 *  Returns a string array of saved games. 
	 */
	public abstract String[] getSavedGames();

	/** 
	 *  Returns a string representation of the current game state saved in the 
	 *  ProtocolManager  
	 */
	public abstract String getGameState();

	/** 
	 *  Loads the given game name in the protocolManager as given
	 *  by the getSavedGames method. Updates game engine if 
	 *  isInitiator. 
	 */
	public abstract void loadSavedGame(String game);

	/** 
	 *  Updates the score of the given player number
	 *  in protocol manager 
	 */
	public abstract void updateScore(int pn, int sc);

	/** 
	 *  sets protocol to given game state. if owner is initiator sets the game engine to
	 *  state by calling setexistingGame() method.  
	 */
	public abstract void setGameState(String state);

	/** 
	 *  Sets game engine to current state held in the protocol manager  
	 */
	public abstract void setGameEngine();

	/**
	 * @return the initiator
	 */
	public abstract boolean isInitiator();

	/**
	 * @param initiator the initiator to set
	 */
	public abstract void setInitiator(boolean initiator);

	/**
	 * @return the playerNumber
	 */
	public abstract int getPlayerNumber();

	/**
	 * @param playerNumber the playerNumber to set
	 */
	public abstract void setPlayerNumber(int playerNumber);

}