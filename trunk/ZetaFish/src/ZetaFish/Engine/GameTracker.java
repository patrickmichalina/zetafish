package gamemodule; 

import java.io.*;
import java.text.*;
import java.util.Date;
/** 
 *  Summary: GameTracker class holds all game data and saves and retrieves the game state before and after a game. 
 *  Imports: Java.IO, Java.Util
 *  Exports: game state documents. 
 */
public class GameTracker {
  /* {author=Zeta Dragons, version=1.0, since=2010}*/


  public String[] players;

  /** 
   *  hands by player number index. 
   */
  public int[][] hands;

  public int[] scores;

//constructor sets all variables to null
  public GameTracker() {
	  
	    this.players = null;
		this.hands = null;
		this.scores = null;
  }

  /**
 * @param players
 * @param hands
 * @param scores
 */
public GameTracker(String[] players, int[][] hands, int[] scores) {
	
	this.players = players;
	this.hands = hands;
	this.scores = scores;
}
//assumes all variables have been initiated 
public void saveGame() {
	File file = new File("ZetaGames");
	boolean exists = file.exists(); 
	if(!exists){
		String directo = "ZetaGames";
		boolean made = (new File(directo)).mkdir();
		if(made)System.out.println("ZetaGames directory made");
		}
		//make game file in directory 
		Date tdate = new Date(); 
		SimpleDateFormat dateformat = new SimpleDateFormat("MMddyyyy");
		StringBuilder now = new StringBuilder( dateformat.format( tdate ) );
		File dfile = new File("ZetaGames/" + (now.toString()) + ".txt");
		boolean dexists = dfile.exists();
		
		try {
			BufferedWriter outputStream;
			//check for file 
			if(!dexists)outputStream = new BufferedWriter(new FileWriter("ZetaGames/" + (now.toString()) + ".txt"));
			else outputStream = new BufferedWriter(new FileWriter("ZetaGames/1" + (now.toString()) + ".txt"));
			//write number of players 
			outputStream.write(players.length);
			outputStream.newLine();
			//write each player 
			for(int i=0; i<players.length; i++){
				outputStream.write(i+1);
				outputStream.newLine();
				outputStream.write(players[i]);
				outputStream.newLine();
				outputStream.write(scores[i]);
				outputStream.newLine();
				//write card numbers 
				outputStream.write(hands[i].length);
				outputStream.newLine();
				for(int j = 0; j<hands[i].length; j++){
					outputStream.write(hands[i][j]);
					outputStream.newLine();
				}
			}
			outputStream.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					
	
  }
/**
 * 
 * returns null if no games found.
 */
  public String[] getSavedGames() {
	  
	  String answer[] = null; 
	  File folder = new File("ZetaGames");
	  boolean exists = folder.exists();
	  if(exists){
		  File list[] = folder.listFiles(); 
		  answer = new String[list.length];
		  for(int i=0; i<list.length; i++){
			  answer[i] = list[i].getName(); 
		  }
		  
	  }
	  
  return answer;
  }
//updates current saved game State
  public void updateGameState( String[] players, int[][] hands, int[] scores) {
	    this.players = players;
		this.hands = hands;
		this.scores = scores;
  }

  /** 
   *  returns the status array of the specified saved game, and saves it as 
   *  the current game state. 
   *  @param String game is a saved game name string as provided by 
   *  the getSavedGames method
   */
  public int[][] loadSavedGame(String game) {
	  int answer[][] = null;
	  int test =0;
	  try{
	  BufferedReader inputStream = new BufferedReader(new FileReader("ZetaGames/" + game));
	     //get the number of players
		test = Integer.parseInt(inputStream.readLine().trim());
		//reset the arrays 
	    this.players = new String[test];
		this.hands = new int[test][];
		this.scores = new int[test];
		
		for(int i=0; i<this.players.length; i++){
			//get player number
			test = Integer.parseInt(inputStream.readLine().trim());
			//get player name
			players[i]= (inputStream.readLine()).trim();
			//get score 
			scores[i] = Integer.parseInt((inputStream.readLine()).trim()); 
			//get the number of cards
			test = Integer.parseInt((inputStream.readLine()).trim()); 
			//set current index 
			hands[i] = new int[test];
			//get card numbers 
			for(int j = 0; j<hands[i].length; j++){
				hands[i][j] = Integer.parseInt(inputStream.readLine().trim());				
			}
		}
		
		inputStream.close();
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}catch (NumberFormatException e){
		System.out.println("Number Format Exception");
	}
	answer = hands; 
	  
   return answer;
  }

/**
 * @return the players
 */
public String[] getPlayers() {
	return players;
}

/**
 * @param players the players to set
 */
public void setPlayers(String[] players) {
	this.players = players;
}

/**
 * @return the hands
 */
public int[][] getHands() {
	return hands;
}

/**
 * @param hands the hands to set
 */
public void setHands(int[][] hands) {
	this.hands = hands;
}

/**
 * @return the scores
 */
public int[] getScores() {
	return scores;
}

/**
 * @param scores the scores to set
 */
public void setScores(int[] scores) {
	this.scores = scores;
}



}