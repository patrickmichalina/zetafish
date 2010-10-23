public interface NetworkManager {

  public int player;
  //true if player = 1 
  public boolean initiator; 

  public GameEngine game;

  public void openConnection();

  public void closeConnection();

  public void newGame();

  /** 
   *  Deal only for initiating player. 
   */
  public void deal();

  /** 
   *  adds a player to the game. 
   */
  public void addPlayer();

  /** 
   *  Gets cards from the ocean. Takes two parameters. x = number of cards to get, and p= player number who recieves them. 
   */
  public void getCards(int x, int p);

  /** 
   *  Transfers cards from one player to another. Takes three parameters, x = card number (ie, 3's, 5's etc), p1 = player giving cards, and p2= player recieving cards. 
   */
  public void giveCards(int x, int p1, int p2);

  /** 
   *  removes player from game. Takes player number as parameter
   */
  public void removePlayer(int p);

  public void start();

  public void exitGame();

  /** 
   *  Sends message to the chat board of all players. Protocol player number then string packet. 
   */
  public void sendMessage(String arg2);

  /** 
   *  Gets messages from other players. 
   */
  public String getMessage();

  /** 
   *  Returns list of cards in player's hand. Takes parameter of the player  number. 
   */
  public int getPlayerHand(int p);

  /** 
   *  Returns player score. 
   */
  public int getScore(int p);

  /** 
   *  Returns number of player whose turn it is. 
   */
  public int getTurn();

  public void updateGameStatus();

}