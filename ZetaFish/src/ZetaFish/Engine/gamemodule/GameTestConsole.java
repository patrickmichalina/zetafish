package ZetaFish.Engine.gamemodule;

import java.util.*;
/**
 * Tests every method in the GameManager Class. Simulates game functions. 
 * Simple console application. 
 * @author Melanie
 *
 */
public class GameTestConsole {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		 GameManager myManager = new GameManager(true,1); 
		 myManager.addPlayer(1, "Melanie");  
		 myManager.addPlayer(2, "Chad");
		 /*myManager.addPlayer(3, "Patrick");
		 myManager.addPlayer(4, "Noor");
		 myManager.addPlayer(5, "Mettee");
		 myManager.addPlayer(6, "Stan");
		  */
		 myManager.deal();
		System.out.println("Look ma I'm a runnin!"); 
		printHands(myManager);
		//myManager.removePlayer(6); 
		//System.out.println("Player 6 removed");
		//printHands(myManager);
		myManager.saveGame(); 
		System.out.println("Saving game.");
		System.out.println("Finding saved games.");
		String games[] = myManager.getSavedGames(); 
		System.out.println("These saved games found:");
		for(int i = 0; i<games.length; i++)
			System.out.println(games[i]);
		System.out.println("Resetting game.");
		myManager.reset(true, 1); 
		System.out.println("Loading saved game: " + games[0]);
		myManager.loadSavedGame(games[0]);
		printHands(myManager);
		System.out.println("Loading saved game: " + games[1]);
		myManager.loadSavedGame(games[1]);
		printHands(myManager);

		System.out.println("Let's play a little");
		boolean inplay = true; 
		System.out.println("ZetaFish Menu:"+'\n'+"Press 1 to pick up cards, 2 to ask for cards, 3 to check score, and 4 show hands. 5 Quit");
		Scanner in = new Scanner(System.in);
		while(inplay){
			String input = in.nextLine(); 
			int i = Integer.parseInt(input.trim()); 
			if(i == 1){
				System.out.println("Please enter player number");
				input = in.nextLine(); 
				int n = Integer.parseInt(input.trim());
				System.out.println("How many cards do you want to pick up?");
				input = in.nextLine(); 
				int s = Integer.parseInt(input.trim());
				boolean done = myManager.getCards(s, n);
				if(done)
				System.out.println("Player "+ n + " has picked up "+s +" cards"); 
				else System.out.println("Soryy no more cards to pick up."); 
				System.out.println("ZetaFish Menu:"+'\n'+"Press 1 to pick up cards, 2 to ask for cards, 3 to check score, and 4 show hands. 5 Quit");
			}
			if(i == 2){
				System.out.println("Please enter player number");
				input = in.nextLine(); 
				int n = Integer.parseInt(input.trim());
				System.out.println("Please enter the player number of the person you want to ask.");
				input = in.nextLine(); 
				int p2 = Integer.parseInt(input.trim());
				System.out.println("Please enter the card number you want to ask for.");
				input = in.nextLine(); 
				int c = Integer.parseInt(input.trim());
				boolean done = myManager.giveCards(c, p2, n);
				if(done)
				System.out.println("Player "+ p2 + " has given his "+c +"'s to player " +n); 
				else System.out.println("Player "+p2+" says GO FISH!"); 
				System.out.println("ZetaFish Menu:"+'\n'+"Press 1 to pick up cards, 2 to ask for cards, 3 to check score, and 4 show hands. 5 Quit");
			}
			if(i == 3){
				System.out.println("The score so far is:");
				int p1 = myManager.getScore(1);
				int p2 = myManager.getScore(2);
				System.out.println("Player 1: "+p1);
				System.out.println("Player 2: "+p2);
				System.out.println("ZetaFish Menu:"+'\n'+"Press 1 to pick up cards, 2 to ask for cards, 3 to check score, and 4 show hands. 5 Quit");
			}
			if(i==4){
				printActiveHands(myManager); 
				System.out.println("ZetaFish Menu:"+'\n'+"Press 1 to pick up cards, 2 to ask for cards, 3 to check score, and 4 show hands. 5 Quit");
			}
			if(i==5){
				inplay = false; 
				System.out.println("Goodbye!");
			}
		}//end game test 
		String stateGame = myManager.getGameState();
		System.out.println("Game state from getGameState is: "+stateGame);
		myManager.setGameState(stateGame);
		System.out.println("Game state set");
		printHands(myManager); 
	}
	
	
   public static void printActiveHands(GameManager myManager){
	    int y=1;
	    int stop = myManager.getNumberofPlayers();
	    System.out.println("Number of Players loaded:"+stop);
	    int x[]; 
	    while(y<=stop){
	    	x = myManager.getActiveHand(y);
	    	System.out.println("Player "+ y + " has hand:"); 
	    	if(x != null)
	    		for(int i = 0; i<x.length; i++)
	    			System.out.println(x[i]);
			
			else System.out.println("No Player " +y +" or this player has no cards."); 	
	    	
	    	y++;
	    }
		
   }
   public static void printHands(GameManager myManager){
	    int y=1;
	    int stop = myManager.getNumberofPlayers();
	    System.out.println("Number of Players loaded:"+stop);
	    int x[]; 
	    while(y<=stop){
	    	x = myManager.getHand(y);
	    	System.out.println("Player "+ y + " has hand:"); 
	    	if(x != null)
	    		for(int i = 0; i<x.length; i++)
	    			System.out.println(x[i]);
			
			else System.out.println("No Player " +y +" or this player has no cards."); 	
	    	
	    	y++;
	    }
		
  }
}
