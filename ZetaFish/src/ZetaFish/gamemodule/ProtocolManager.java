package gamemodule;

import java.util.*;


/** 
 *  Description: ProtocolManager class instantiates the GameTracker class and the Player classes. Implements all game actions as pertains to the player status, game status, and game protocol. Detects the game secquence, and determines the player states of each player in the game. Responses from this class are used by the GameManager to direct the GameEngine. Protocol manager retrieves the deck status from the GameEngine class at the close of each game session and stores it via the GameTracker class. 
 *  Imports: none
 *  Exports: All Game Player management options as detailed in section 3.0 in the Requirements Document under the Player Subheading. 
 */
public class ProtocolManager {
  /* {author=Zeta Dragons, version=1.0, since=2010}*/


/** 
 *  Player ArrayList Holds array  of player objects. 
 * @element-type Player
 */
  public ArrayList<Player> players;

  /** 
   *  Game Tracker Object 
   */
  public GameTracker myGameTracker;
  
  /**
   * @param players
   * @param hands
   * @param scores
   */
  public ProtocolManager(String[] players, int[][] hands, int[] scores) {
	  this.myGameTracker = new GameTracker(players, hands,scores);
	  this.players = new ArrayList<Player>(); 
	  addPlayers(players, hands, scores);
	  
  }
  //constructor makes empty game tracker and empty players arraylist 
  public ProtocolManager() {
	  this.myGameTracker = new GameTracker();
	  this.players = new ArrayList<Player>(); 
	  	  
  }
  //adds provided players to the players arraylist . creates a new players arraylist with new info
  public void addPlayers(String[] players, int[][] hands, int[] scores) {
	  this.players = new ArrayList<Player>();
	  int x = players.length; 
	  for(int i =0; i<x; i++){
		  Player newP = new Player(players[i],scores[i],i+1,hands[i]);
		  this.players.add(newP); 
	  }
  }
  //returns true if player added false if player number already exists 
  public boolean addPlayer(int pn, String name) {
	  boolean complete = true; 
	  Player x = new Player(); 
	  x.setPlayerNumber(pn);
	  x.setName(name); 
	  complete = check(x); 
	  
	  if(complete)
	  this.players.add(x); 
	  
	  return complete; 
  }

//checks if a player of give player number already exists returns false if found , true of not found. 
private boolean check(Player x) {
	
	boolean nfound = true; 
	Iterator<Player> i = players.iterator(); 
	while(i.hasNext()){
		if(x.getPlayerNumber() == i.next().getPlayerNumber()) nfound = false; 
	}
	return nfound;
}
//removes player of given player number  
public boolean removePlayer(int pn) {
	
	boolean removed = false; 
	Iterator<Player> i = players.iterator(); 
	while(i.hasNext()){
		Player holder = i.next();
		if(pn == holder.getPlayerNumber()){
			removed = true; 
			this.players.remove(holder); 
			break; 
		}
	}
	
	return removed; 
  }
/**
 * calls set game and then calls save game on gametracker. 
 * preconditions: Game has been initiated and all players added, and hands delt. 
 */
  public void saveGame() {
	  setGame(); 
	  this.myGameTracker.saveGame(); 
	  
  }

  public String[] getSavedGames() {
	  String answers[] = this.myGameTracker.getSavedGames(); 
  return answers;
  }

  public void setScore(int pl, int scr) {
	  
	  Iterator<Player> i = players.iterator(); 
		while(i.hasNext()){
			Player holder = i.next();
			if(holder.getPlayerNumber() == pl){
				int y = this.players.indexOf(holder);
				this.players.get(y).setScore(scr); 
				
			}
			
		}
  }
//returns 0 if no score found 
  public int getScore(int pl) {
	  
	  int score = 0;
	  Iterator<Player> i = players.iterator(); 
		while(i.hasNext()){
			
			Player holder = i.next();
			if(holder.getPlayerNumber() == pl){
				int y = this.players.indexOf(holder);
				score = this.players.get(y).getScore(); 
				break; 
			}
			
		}
  return score;
  }

  /** 
   *  Returns number of players in the game. 
   */
  public int getPlayers() {
	  
	  return this.players.size();
  }
//accepts a string representation of the game state of the format: #of players space player# space player name space score space
// #of cards space cards list -> next player. 
public void updateGame(String gameState) {
	
	StringTokenizer tokenizer = new StringTokenizer(gameState);  
	try{
		int numberOfPlayers = Integer.parseInt(tokenizer.nextToken().trim()); 
		String players[] = new String[numberOfPlayers]; 
		int hands[][] = new int[numberOfPlayers][];
		int scores[] = new int[numberOfPlayers];
		int i = 0; 
		while(tokenizer.hasMoreTokens()){
		//get player number =0-6
			i = Integer.parseInt(tokenizer.nextToken().trim()); 
		//get player name
			players[i] = tokenizer.nextToken().trim(); 
		//get score
			scores[i] = Integer.parseInt(tokenizer.nextToken().trim()); 
		//get number of cards
			int j = Integer.parseInt(tokenizer.nextToken().trim());
			hands[i] = new int[j];
			//get cards 
			for(int t =0; t<hands[i].length; t++)
				hands[i][t]= Integer.parseInt(tokenizer.nextToken().trim());
		}
		//add players to protocol manager
		this.addPlayers(players, hands, scores);
		//add players to game tracker
		this.myGameTracker.updateGameState(players, hands, scores);  
		
	}catch(NumberFormatException e){
		
		System.out.println("Number format exception update game method"); 
		
	}
	  
  }
/**
 * returns a string representation of the current game state in format: 
 * #of players space player# space player name space score space
 * #of cards space cards list -> next player. 
 * 
 * @return
 */
public String getGameState(){
	String gameState = null; 
	//set number of players
	gameState += this.getPlayers(); 
	gameState += " ";  
	Iterator<Player> i = players.iterator(); 
	while(i.hasNext()){
		Player holder = i.next();
	//set player number =0-6
		gameState += (holder.getPlayerNumber()-1);  
		gameState += " ";
	//set player name
		gameState += holder.getName();  
		gameState += " "; 
	//set score
		gameState += holder.getScore();  
		gameState += " "; 
	//set number of cards
		gameState += holder.getNumberOfCards();  
		gameState += " ";
		//set cards 
		int hand[] = holder.getHand(); 
		for(int t =0; t<hand.length; t++){
			gameState += hand[t]; 
			gameState += " ";
		}
	}
	return gameState;
	
}
  /** 
   *  sets game tracker variables to the contents of the players arraylist. ready to save to file.
   *  assumes players arraylist has been initialized and players added. 
   */
  public void setGame() {
	  
	    int numberOfPlayers = this.getPlayers(); 
	    String players[] = new String[numberOfPlayers]; 
		int hands[][] = new int[numberOfPlayers][];
		int scores[] = new int[numberOfPlayers];
		int j = 0; 
		Iterator<Player> i = this.players.iterator(); 
		while(i.hasNext()){
			Player holder = i.next();
		  //set player name
			players[j] += holder.getName();  
		//set score
			scores[j] += holder.getScore();  	
		//set cards 
			hands[j] = holder.getHand(); 
			j++;
		}
		this.myGameTracker.updateGameState(players, hands, scores);  
  }

  /** 
   *  sets the provided hand to the player number indicated. 
   */
  public boolean setHand(int pl, int hand [ ]) {
	  boolean found = false; 
	  Iterator<Player> i = players.iterator(); 
		while(i.hasNext()){
			Player holder = i.next();
			if(holder.getPlayerNumber() == pl){
				int y = this.players.indexOf(holder);
				this.players.get(y).setHand(hand);
				found = true; 
				break; 
			}
			
		}
		return found; 
  }

}