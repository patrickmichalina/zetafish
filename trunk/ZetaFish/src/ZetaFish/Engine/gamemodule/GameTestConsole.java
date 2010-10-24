
package gamemodule;
import java.util.*;
/**
 * Testes every method in the GameManager Class. Simulates game functions. 
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
		//myManager.removePlayer(6); 
		//System.out.println("Player 6 removed");
		//printHands(myManager);
		System.out.println("Let's play a little");
		boolean inplay = true; 
		System.out.println("ZetaFish Menu:/n"+"Press 1 to pick up cards, 2 to ask for cards, 3 to check score, and 4 show hands. 5 Quit");
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
				
			}
			if(i == 3){
				System.out.println("The score so far is:");
				int p1 = myManager.getScore(1);
				int p2 = myManager.getScore(2);
				System.out.println("Player 1: "+p1);
				System.out.println("Player 2: "+p2);
				
			}
			if(i==4){
				printHands(myManager); 
			}
			if(i==5){
				inplay = false; 
				System.out.println("Goodbye!");
			}
		}
		System.out.println("Player 1 says does player 4 have any 1's?");
		boolean yes = myManager.giveCards(1, 4, 1);
		if(yes){
			System.out.println("Yes. Player 4 gave all his 1's to Player 1");
		     printHands(myManager);
		     
		     
		     int x = 2;
		     boolean pick = myManager.getCards(x, 4);
		     if(pick){System.out.println("Player 4 has picked up cards.");
		     printHands(myManager);}
		     else System.out.println("Sorry no more cards in the ocean");
		}
		else System.out.println("Player 4 says Go Fish!");
		
		
		System.out.println("Player 2 says does player 1 have any 3's?");
		yes = myManager.giveCards(3, 1, 2);
		if(yes){
			System.out.println("Yes. Player 1 gave all his 3's to Player 2");
		     printHands(myManager);
		     
		    
		     int x = 2;
		     boolean pick = myManager.getCards(x, 1);
		     if(pick){System.out.println("Player 1 has picked up 2 cards.");
		     printHands(myManager);}
		     else System.out.println("Sorry no more cards in the ocean");
		}
		else System.out.println("Player 1 says Go Fish!");
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
			
			else System.out.println("No Player " +y); 	
	    	
	    	y++;
	    }
		
   }
}
