package gamemodule; 


import java.util.*;


/** 
 *  Summary: DeckManager Class instantiates an array of card objects that represent all 
 *  cards in the playing deck. It maintains the state of the deck at all times, and 
 *  relays that information to the GameEngine. 
 *  Imports: Java.Util, Java.lang
 *  Exports: Deck operations as detailed in the Requirements Documents section 3.0. under the sub heading Playing Cards. 
 *    {author=Zeta Dragons, version=1.0, since=2010}
 *    
 *    @author Melanie
 */
public class DeckManager {
  /** 
   *  int players holds number of players in the game. 
  */
  private int players;
  /** 
   *  Card ArrayList Holds the cards of the current deck. 
   * @element-type Card
   */
  private ArrayList<Card> deck;
  /** 
   *  boolean gameOver holds true if all cards have been used. 
   */
  private boolean gameOver;

 
//constructor
  /**
 * Constructor sets all variables to 0 or false. Sets up deck by 
 * calling setDeck(). 
 */
public DeckManager() {
	  this.players = 0;
	  this.deck = new ArrayList<Card>(); 
	  setDeck(); 
	  this.gameOver = false; 
  }
  
  
/**
 * sets 52 cards to the deck numbered 1 to 52 starting with 
 * the ace of each suit and ending with the king. 
 * Suit order is hearts, diamonds,clubs, spades.
 */
public void setDeck(){
	int i = 0;
	for(i=0; i<52; i++){
		Card card = new Card(); 
		card.setNum(i+1);
		card.setinPlay(false);
		card.setPlayer(-1);
		card.setWon(false);
		deck.add(card); 
	}
	
}
/** 
 *  Sets game to the players and hands provided in the passed array. 
 *   resets the deck variable. calls getBooks to set all won cards. 
 *   calls setDeck().
*/
public void setExistingGame(int[][] gameState){
	this.deck = new ArrayList<Card>(); 
	this.setDeck(); 
	 
	for(int j=0;j<gameState.length; j++){
		
		for(int u=0; u<gameState[j].length; u++){
			Iterator<Card> i = deck.iterator();
			while(i.hasNext()){
				//get card 
				Card card = i.next(); 
				//set card 
				if(card.getNum() == gameState[j][u]){
					
					int y = deck.indexOf(card);
					deck.get(y).setinPlay(true);
					deck.get(y).setPlayer(j+1);
					deck.get(y).setWon(false);
					
				}
			}
		}
		//set the books
		this.getBooks(j+1); 
	}//end outer for 
	this.players = gameState.length;  
}
//
  /**
   * all players must be added before deal is called. 
   * Deck Manager has been initiated. returns false if no deal
   * @param newGame
   * @return boolean true if deal was successful
   */
public boolean deal(boolean newGame) { 
	  
	  boolean complete = false; 
	  
	  if(newGame)shuffle(); 
	  
		  //check for players added check for 4 or less players
	  if((players > 0)&&(players < 5)){
			int hand = 0;
		  	Iterator<Card> x = deck.iterator(); 
		  //each player is dealt one card nine times.
		  	while(hand < 9){
		  		hand++; 
		  		for(int i = 0; i<players; i++){
		  			Card c = x.next(); 
		  			c.setinPlay(true);
		  			c.setPlayer(i+1); 
		  		}
		  	}
		  	complete = true; 
		  }
		  
	  //check for players added check for 5 or more players
	  else if(players > 4){
		  int hand = 0;
		  	Iterator<Card> x = deck.iterator(); 
		  	while(hand < 7){
		  		hand++; 
		  		//each player is dealt one card seven times.
		  		for(int i = 0; i<players; i++){
		  			Card c = x.next(); 
		  			c.setinPlay(true);
		  			c.setPlayer(i+1); 
		  		}
		  	}
		     
		  	complete = true;  
		  }
	 return complete;  
  }

  /**
 * shuffles deck 
 */
public void shuffle() {
	  Collections.shuffle(deck);
  }
//must be called at least twice before deal. 
  /**
 * must be called at least twice before deal. 
 * adds player 
 */
public void addPlayer() {
	  players++; 
  }
//removes player and returns their cards to the ocean. assumes all players have been added and deal() has been called. 
  /**
   * removes player and returns their cards to the ocean. assumes all players have been added and deal() has been called.
   * @param nm player number 
   * @return boolean
   */
public boolean removePlayer(int nm) {
	  
	  boolean removed = false; 
	  //check player number 
	  if(nm <= this.players) removed = true; 
	  if(nm < 1) removed = false; 
	  Iterator<Card> x = deck.iterator(); 
	  //reset the player's cards. 
	  while(x.hasNext()){
		  Card c = x.next(); 
		  if(c.getPlayer() == nm){
			  c.setinPlay(false);
			  c.setPlayer(-1);
			  c.setWon(false);
		  }
	  }	  
	  //remove player from players
	  this.players--; 
	  return removed; 
	  
  }
//returns an int array of the given player's hand. returns null if no player exists. 
  /**
   * returns an int array of the given player's hand. returns null if no player exists. 
   * @param pl player number 
   * @return int []
   */
public int[] getHand(int pl) {
	  int hn[]; 
	  int i = 0;
	//check player number 
	  if((pl < 1)||(pl > this.players)) hn = null; 
	  else{
		int test[] = new int[52];
	  	Iterator<Card> x = deck.iterator(); 
	  //get the player's cards. 
	  	  while(x.hasNext()){
	  		  Card c = x.next(); 
		  	  if(c.getPlayer() == pl){
			     test[i] = c.getNum();
			     i++;
		  	  }
		      
	  	  }	
	  	//trim the array 
	  	hn = new int[i];
	  	for(int j = 0; j<i;j++){
	  		hn[j] = test[j];
	  	}
	  }
  return hn;
  }

  /** 
   *  returns true if cards were retrieved from the ocean, false if no cards are available. 
   *  Sets gameOver variable to true when there are no more cards left in the ocean. 
   *  @param int number of cards to get
   *  @param int player number to receive cards. 
   */
  public boolean getCards(int nm, int pl) {
	  boolean game = true; 
	  int i = 0;
	  //check player number 
	  if((pl < 1)||(pl > this.players)) game = false;
	  else{
	  Iterator<Card> x = deck.iterator(); 
	  //get the player's cards from the ocean. 
	  	  while((x.hasNext())&& (i < nm)){
	  		  Card c = x.next(); 
		  	  if(!c.isinPlay()){
		  		  i++; 
		  		  c.setPlayer(pl);
		  		  c.setinPlay(true);
			    }
	  	  }
	  	  //check if no cards were found 
	  	  if(i==0){
	  		  game = false; 
	  		  this.gameOver = true; 
	  	  
	  	  }
	  }
  return game;
  }

  /** 
   * Method: giveCards() 
   *  Returns true if cards were successfully transfered. False if not.
   *  Moves all cards of the given number (nm) from player p1 to player p2.  
   *  @param int nm corresponds to the card number of the cards to be transfered ( 1-13) from ace to king. 
   *  @param int p1 is the player giving the cards.
   *  @param int p2 is the player receiving the cards. 
   */
  public boolean giveCards(int nm, int p1, int p2) {
	  
	  boolean game = false; 
	  int i = nm; 
	  //check player numbers 
	  //if((p1 < 1)||(p1 > this.players)) game = false;
	  //else if((p2 < 1)||(p2 > this.players)) game = false;
	  //else{
	   int inter = 0;
	  
	  	  while(inter < 4){
	  		  inter++; 
	  		  System.out.println("Checking for card #:" + i);
	  		  Card c = new Card(i, true, p1, false);  
		  	  int index = deck.indexOf(c);
		  	  if(index != -1){
		  		  deck.get(index).setPlayer(p2);
		  		  game = true; 
			    }
		  	  //check for next card. 
		  	i = i + 13; 
	  	  }
	  	  
	  //}
  return game;
  }

  
 
  /** 
   *  returns number of new books found in the player's hand, returns -1 if no books found. 
   *  Sets any cards found in a book to won = true. 
   */
  public int getBooks(int pl) {
	  int i = 0;
	  int j = 0;
	  boolean book = true; 
	  int k[] = {1,14,27,40}; 
	  if((pl < 1)||(pl > this.players)) i = -1;
	  else{
	  while(j<13){
		  j++; 
		  for(int z =0; z<k.length; z++){
			  Card thisCard = new Card(k[z],true, pl, false);
			  int p = deck.indexOf(thisCard);
			  if(p==-1){
				  book = false; 
				  break; 
			  }
		  }//end for1
		  if(book){
			  i++; 
			  //set books to won
			  for(int z =0; z<k.length; z++){
				  Card thisCard = new Card(k[z],true, pl, false);
				  int p = deck.indexOf(thisCard);
				  deck.get(p).setWon(true);
			  }
		  }
		  //reset book
		  book = true; 
		  //increment k
		  for(int e=0; e<k.length; e++)k[e]+=1;
	  }//end while
	  //check for books
	  if(i==0)i=-1;
	  } 
  return i;
  }
  /** 
   *  returns number of  books found in the player's hand, returns -1 if no books found or no player found. 
   *  does not set any books to won. Only counts the number of books already won in a players hand. 
   */
  public int getAllBooks(int pl) {
	  int i = 0;
	  int j = 0;
	  boolean book = true; 
	  int k[] = {1,14,27,40}; 
	  if((pl < 1)||(pl > this.players)) i = -1;
	  else{
	  while(j<13){
		  j++; 
		  for(int z=0; z<k.length; z++){
			  Card thisCard = new Card(k[z],true, pl, true);
			  int p = deck.indexOf(thisCard);
			  if(p==-1){
				  book = false; 
				  break; 
			  }
		  }//end for1
		  if(book){
			  i++; 
		  }
		  //reset book
		  book = true; 
		  //increment k
		  for(int e=0; e<k.length; e++)k[e]+=1;
	  }//end while
	  //check for books
	  if(i==0)i=-1;
	  } 
  return i;
  }
/**
 * @param players the players to set
 */
public void setPlayers(int players) {
	this.players = players;
}

/**
 * @return the players
 */
public int getPlayers() {
	return players;
}

/**
 * @param gameOver the gameOver to set
 */
public void setGameOver(boolean gameOver) {
	this.gameOver = gameOver;
}

/**
 * 
 *  Returns True if all cards in the deck have been used i.e. are in play. 
 *
 * @return the gameOver
 */
public boolean isGameOver() {
	return gameOver;
}

}