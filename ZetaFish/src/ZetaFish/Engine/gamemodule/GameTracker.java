package ZetaFish.Engine.gamemodule; 

import java.io.*;
import java.text.*;
import java.util.Date;
/** 
 *  Summary: GameTracker class holds all game data and saves and retrieves the game state before and after a game. 
 *  Imports: Java.IO, Java.Util
 *  Exports: game state documents. 
 *  
 *  @author Melanie
 */
public class GameTracker {
  /* {author=Zeta Dragons, version=1.0, since=2010}*/


  /**
 *  String[] players names 
 */
public String[] players;

  /** 
   *  hands by player number index. 
   */
  public int[][] hands;

  /**
 * int[] scores 
 */
public int[] scores;

  /**
 * constructor sets all variables to null
 */
public GameTracker() {
	  
	    this.players = null;
		this.hands = null;
		this.scores = null;
  }

  /**
   * Constructor
 * @param players
 * @param hands
 * @param scores
 */
public GameTracker(String[] players, int[][] hands, int[] scores) {
	
	this.players = players;
	this.hands = hands;
	this.scores = scores;
}

/**
 * assumes all variables have been initiated save game stats held 
 * currently in the class variables to file. Creates a folder "ZetaGames"
 * and saves the game state by date. 
 */
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
			PrintWriter outputStream;
			FileOutputStream stream; 
			//check for file 
			if(!dexists){
				stream = new FileOutputStream("ZetaGames/" + (now.toString()) + ".txt");
				outputStream = new PrintWriter(stream,true);
			}
			else{
				stream = new FileOutputStream("ZetaGames/1" + (now.toString()) + ".txt");
				outputStream = new PrintWriter(stream,true);
			}
			//write number of players 
			outputStream.println(players.length);
			
			//write each player 
			for(int i=0; i<players.length; i++){
				outputStream.println(i+1);
				String name = players[i].trim();
				outputStream.println(name);
				
				outputStream.println(scores[i]);
				
				//write card numbers 
				int m = hands[i].length;
				outputStream.println(m);
				
				for(int j = 0; j<hands[i].length; j++){
					outputStream.println(hands[i][j]);
					
				}
			}
			stream.close();
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
 
  /**
   * updates current saved game State 
 * @param players
 * @param hands
 * @param scores
 */
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
	  FileInputStream stream = new FileInputStream("ZetaGames/" + game);
	  InputStreamReader input = new InputStreamReader(stream);
	  BufferedReader inputStream = new BufferedReader(input);
	     //get the number of players
		test = Integer.parseInt(inputStream.readLine().trim());
		System.out.println("read: " + test);
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
		stream.close();
		input.close();
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