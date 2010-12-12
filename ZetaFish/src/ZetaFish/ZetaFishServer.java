package ZetaFish;

import java.awt.BorderLayout;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ZetaFish.NetworkObjects.*;
import ZetaFish.NetworkObjects.ZFCardRequestResponse.CardRequestResult;


/**
 * Main class for ZetaFishServer.
 */
public class ZetaFishServer extends JFrame
{	
	private static final long serialVersionUID = 1L;

	private ZFServerThread serverThread = null;
	
	public static final int DEFAULT_PORT = 5000;
		
	private JTextArea output;
	private JScrollPane scrollPane;	
	
	/**
	 * Constructor
	 * @param args Command line arguments
	 * @param Visible Should the server window be visible?
	 */
	public ZetaFishServer(String args[], boolean Visible) 
	{		
		super("ZetaFish Server");
		output = new JTextArea();
		scrollPane = new JScrollPane(output);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		setSize(300,300);
		setVisible(Visible);
		int port = DEFAULT_PORT;
		
		if (args != null && args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			}
			catch(NumberFormatException e) {
				display(e.toString());
			}
		}
		serverThread = new ZFServerThread(this, port);
		serverThread.start();	
	}
			
	/**
	 * Write a string to the window.
	 * @param s String to write.
	 */
	public synchronized void display(String s) 
	{
		output.append(s + "\n");
	}
	
	
	public void dispose()
	{
		try
		{
			serverThread.end();
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		super.dispose();		
	}
	
	
	/**
	 * Returns a Set of ZFClientResponseHandler representing each the channel to each player.
	 * @return active players
	 */
	public synchronized Set<ZFClientResponseHandler> getActivePlayers()
	{
		return serverThread.getActivePlayers();		
	}
	
	/**
	 * Send a network object to all players.
	 * @param obj network object to send.
	 */
	public synchronized void BroadcastObject(Object obj)
	{
		serverThread.BroadcastObject(obj);		
	}
	
	/**
	 * @param args
	 */
	public static void main_SVR(String[] args) {
		new ZetaFishServer(args, true);
	}
}

/**
 * Private class used by ZetaFishServer to handle incoming network requests
 */
class ZFServerThread extends Thread 
{	
	private ZetaFishServer ZFserver;	
	private ZFGame game;
	private int port;
	private ServerSocket server = null;
	
	static protected Set<ZFClientResponseHandler> activePlayers = new HashSet<ZFClientResponseHandler>();

	/**
	 * Constructor
	 * @param server Instance of ZetaFishServer
	 * @param port Port to listen for clients on. 
	 */
	public ZFServerThread(ZetaFishServer server, int port)
	{
		this.ZFserver = server;
		this.port = port;		
	}
	
	/**
	 * Thread.run
	 */
	public void run()
	{		
		game = new ZFGame(ZFserver);
		try {
			server = new ServerSocket(port, 50, InetAddress.getByName("127.0.0.1"));			
			Socket connection;
			while (true) {
				ZFserver.display("Waiting for players to connect...");
				connection = server.accept();
				ZFClientResponseHandler newPlayer = new ZFClientResponseHandler(connection, game, ZFserver);
				activePlayers.add(newPlayer);
				newPlayer.start();
			}
		}
		catch(Exception e) 
		{
			ZFserver.display("ZFServerThread.run:" + e.toString());
		}		
	}
	
	/**
	 * Call to end the server
	 * @throws Exception
	 */
	public void end() throws Exception
	{
		server.close();
	}
	
	/**
	 * Returns a Set of ZFClientResponseHandler representing each the channel to each player.
	 * @return Set<ZFClientResponseHandler>
	 */
	public synchronized Set<ZFClientResponseHandler> getActivePlayers()
	{
		return ZFServerThread.activePlayers;
	}
	
	/**
	 * Send a network object to all players.
	 * @param obj Network object to send.
	 */
	public synchronized void BroadcastObject(Object obj)
	{
		ZFserver.display("BroadcastObject()");
		for(ZFClientResponseHandler prh: ZFServerThread.activePlayers)
		{	
			Player p = prh.getPlayer();
			if(p != null)
				p.sendObject(obj);								
		}
	}
}

/**
 * Private class used by ZFServerThread as a threaded handler for client communication
 */
class ZFClientResponseHandler extends Thread {
	private Socket connection;
	private ZFGame game;
	private ZetaFishServer server;

	private ObjectOutputStream out;
	private ObjectInputStream in;

	private Player player;
		
	/**
	 * Constructor
	 * @param connection Client socket
	 * @param game Instance of ZFGame
	 * @param server Instance of ZetaFishServer
	 */
	public ZFClientResponseHandler(Socket connection, ZFGame game, ZetaFishServer server) 
	{
		this.connection = connection;
		this.game = game;
		this.server = server;
		server.display("A player has connected");
		try {	
		    out = new ObjectOutputStream(connection.getOutputStream());		    
		}
		catch(Exception e) 
		{
			HandleLocalException(e);
		}
	}
	
	/**
	 * Gets the Player object associated with this handler
	 * @return Player
	 */
	public Player getPlayer()
	{
		return this.player;
	}
	
	/**
	 * Thread.run
	 */
	public void run()
	{				
		try
		{
			player = game.addPlayer(out);			
			if (player != null)
			{
				server.display("Started thread for " + player.getToken());
				parsePlayerInput();
			}			
		}
		catch(Exception err)
		{
			server.display("ZFClientResponseHandler.run:" + err.getMessage());
			HandleLocalException(err);			
		}
		finally
		{			
			closeLink();				
		}			
	}
	
	/**
	 * Single location to handle exceptions 
	 * @param error Exception
	 */
	private void HandleLocalException(Exception error)
	{					
		server.display(error.toString());
		for(StackTraceElement ste : error.getStackTrace())
		{
			server.display("\t" + ste.toString());
		}
		
		error.printStackTrace();		
	}
	
	/**
	 * Waits for incoming client objects then parses them.
	 * @throws Exception
	 */
	private void parsePlayerInput() throws Exception 
	{		
		boolean done = false;
		in = new ObjectInputStream(connection.getInputStream());
		
		server.display("Listening for input from " + player.getToken());
		while (!done) {
			try
			{		
				Object oin = in.readObject();
				if(oin == null)
				{
					server.display("Client disconnect (" + player.getToken() + ")");
					done = true;				
				}
				else 
				{							
					server.display("Client --> Server: " + oin.toString());
					parseObjectIn(oin);
				}
			}
			catch(Exception e) {
				System.out.println("parsePlayerInput() exception!");
				System.out.println("STACK:");
				e.printStackTrace();
				server.display("Client disconnect (" + player.getToken() + ") Exception:" + e.toString());
				done = true;	
			}
		}
		in.close();
	}	
	
	/**
	 * Parse incoming client object.
	 * @param oin Network object to parse.
	 */
	private void parseObjectIn(Object oin) 		
	{	
		if(oin.getClass() == ZFChat.class)
		{
			parseChatCommand((ZFChat) oin);
		}
		else if(oin.getClass() == ZFCommand.class)
		{
			parsePlayerCommand((ZFCommand)oin);
		}
		else if(oin.getClass() == ZFCardRequest.class)
		{
			parsePlayerCardRequest((ZFCardRequest)oin);
		}
		else if(oin.getClass() == ZFPlayBook.class)
		{
			parsePlayerPlayBook((ZFPlayBook)oin);
		}
	
	}
	
	/**
	 * Parse incoming chat object.
	 * @param c ZFChat object
	 */
	private void parseChatCommand(ZFChat c)
	{		
		server.display("(Chat)" + c.from + ": " + c.msg);
		// set the token to the player name
		player.setToken(c.from);
		// broadcast chat message
		for(ZFClientResponseHandler prh: server.getActivePlayers())
		{						
			if (prh != this)
				prh.player.sendObject(c);								
		}
	}
	
	/**
	 * Parse incoming Command.
	 * @param cmd ZFCommand object
	 */
	private void parsePlayerCommand(ZFCommand cmd)
	{
		switch(cmd.getCommand())
		{
			case START_GAME:
				game.startGame();
				break;
				
			case NEW_GAME:
				// TODO: Handle this
				break;
				
			case TURN_DONE:
				game.doTurnDone(player.getPlayerNumber());
				break;
		}
		
	}	
	
	/**
	 * Handle incoming card request
	 * @param cr ZFCardRequest
	 */
	private void parsePlayerCardRequest(ZFCardRequest cr)
	{
		game.doCardRequest(player.getPlayerNumber(), cr);		
	}
	
	/**
	 * Handle incoming play book command
	 * @param play ZFPlayBook
	 */
	private void parsePlayerPlayBook(ZFPlayBook play)
	{
		game.doPlayBook(player.getPlayerNumber(), play);		
	}
		
	/**
	 * Close the communications link to this player gracefully.
	 */
	private void closeLink() {
		try {
			/* Design 7.1.9.2 v1.5 */
			if(player != null)
				game.delPlayer(player.getPlayerNumber());
			
			if((connection != null) && (!connection.isClosed()))				
					connection.close();
			
			if(player != null)
				server.display("Client connection closed by " + player.getToken() + "\n");
		}
		catch(Exception e) 
		{
			server.display(e.toString());
		}
	}
}


/**
 * Private class used by ZFServerThread as a Game Manager for the game of ZetaFish
 */
class ZFGame {
	public final int MAX_PLAYERS = 6;
	//public final int MAX_PLAYERS = 2;
	public final int INITIAL_HAND_SIZE = 7;
	
	private LinkedList<Player> players;
	private int numPlayers;
	private boolean gameEnabled, isGameOver;
	private int currentPlayerNumber;
	private ZFDeck deck;
	
	private ZetaFishServer server;
	
	/**
	 * Constructor
	 * @param server Instance of ZetaFishServer
	 */
	public ZFGame(ZetaFishServer server) {
		this.server = server;
		deck = new ZFDeck();	
		players = new LinkedList<Player>();		
		numPlayers = 0;
		gameEnabled = false;
		isGameOver = false;
	}
	
	/**
	 * Is the game enabled?  (In progress)
	 * @return boolean
	 */
	public boolean getGameEnabled()
	{
		return this.gameEnabled;
	}
	
	/**
	 * Is the game over?
	 * @return boolean
	 */
	public boolean getIsGameOver()
	{
		return this.isGameOver;
	}
		
	/**
	 * Add a new player to the game.
	 * @param out Player's ObjectOutputStream
	 * @return new Player object
	 */
	synchronized public Player addPlayer(ObjectOutputStream out)
	{
		Player newPlayer = null;				
		
		/* Design 7.1.4 v1.5 */
		if(gameEnabled)
		{
			String error = "Game already enabled.  Player rejected.";
			server.display(error);
			Player p = new Player(Integer.MAX_VALUE, "Player" + Integer.MAX_VALUE);
			p.setOutput(out);
			p.sendObject(new RuntimeException(error));			
		}
		/* Design 7.1.2.2 v1.5 */
		else if (numPlayers >= MAX_PLAYERS)
		{
			String error = "Too many players.  Player rejected.";
			server.display(error);
			Player p = new Player(Integer.MAX_VALUE, "Player" + Integer.MAX_VALUE);
			p.setOutput(out);
			p.sendObject(new RuntimeException(error));			
		}
		else
		{			
			newPlayer = new Player(numPlayers, "Player" + numPlayers);
			newPlayer.setOutput(out);
			players.add(newPlayer);			

            String token = newPlayer.getToken();
			server.display("Server --> Client: Player connected " + token);			
			newPlayer.sendObject(new ZFStatus(ZFStatus.StatusType.ASSIGN_PLAYER_NUMBER, numPlayers, null, gameEnabled, isGameOver));			
			numPlayers++;		
		}
		
		return newPlayer;
	}	
	
	/**
	 * Remove a player from the game.
	 * @param playerNumber Player number to remove.
	 */
	synchronized public void delPlayer(int playerNumber) 
	{
		boolean sendFinalTurnChange = false;
		
		Player player = players.get(playerNumber);
		server.display("delPlayer: " + player.getToken());
		player.disable();

		/* Design 7.1.9.4 v1.5 */
		deck.replaceCards(player.getHand());
		
		player.removeAllCardsFromHand();				
		
		numPlayers--;
		/* end game if not enough players */
		if (numPlayers <= 1) 
		{
			gameEnabled = false;
			isGameOver = true;
		}
		/* move to the next player if the current player is exiting */
		else if (playerNumber == currentPlayerNumber)
		{
			moveToNextPlayer();
			sendFinalTurnChange = true;
		}
		
		players.remove(player);
		/* Design 7.1.9.3 v1.5 */
		sendRemovePlayer(player);
		UpdateGameStatus(ZFStatus.StatusType.PLAYER_DELETE);
		
		/* Make sure we have a contiguous block of player numbers */ 
		reassignAllPlayerNumbers();		
		
		if(sendFinalTurnChange)
			UpdateGameStatus(ZFStatus.StatusType.TURN_CHANGE);
			
	}
	
	/**
	 * At the end of a turn, move to the next player
	 */
	synchronized public void moveToNextPlayer()
	{
		currentPlayerNumber = (currentPlayerNumber + 1) % numPlayers;	
		server.display("moveToNextPlayer() - " + currentPlayerNumber);
	}
	
	/**
	 * Reassigns all player numbers when a player exits
	 */
	synchronized public void reassignAllPlayerNumbers()
	{
		int playerNumber = 0;
		for(Player player:players)
		{
			player.setPlayerNumber(playerNumber);
			player.sendObject(new ZFStatus(ZFStatus.StatusType.ASSIGN_PLAYER_NUMBER, playerNumber, null, gameEnabled, isGameOver));
			playerNumber++;
		}
	}
	
	/**
	 * Send a RemovePlayer object to all clients.
	 * @param player Player to remove
	 */
	private void sendRemovePlayer(Player player)
	{
		ZFPlayer zp = new ZFPlayer( player.getToken(), 
	               player.getPlayerNumber(), 
	               player.getScore(), 
	               player.getCardsInHand(), 
	               player.getHand(), 
	               player.getBooks());	
		ZFRemovePlayer remove = new ZFRemovePlayer(zp);
		this.server.BroadcastObject(remove);
	}
	
	/**
	 * Start the game.
	 */
	synchronized public void startGame() 
	{
		/* Design 7.1.2.1 v1.5 */
		if(numPlayers > 1)		
		{
			deck.shuffle();
			gameEnabled = true;
			currentPlayerNumber = 0;
			isGameOver = false;
			
			for(Player player:players)
			{
				player.reset();
				for(int i=0; i<INITIAL_HAND_SIZE; i++)
				{
					try
					{
						ZFCard newCard = deck.dealCard();
						player.addCardToHand(newCard);
					}
					catch(Exception err)
					{
						server.display("!!! Deck Exception");	
					}
				}			
			}		
			UpdateGameStatus(ZFStatus.StatusType.GAME_STARTED);
				
			String msg = "Game is ready to begin";
			for(Player player:players)
			{
				player.sendMessageFromAdmin(msg);
			}
		}
		else
		{
			String msg = "startGame: Not enough players";
			server.display(msg);
			for(Player player:players)
			{
				player.sendMessageFromAdmin(msg);
			}
		}
	}
	
	/**
	 * Update game status. (Turn change, card change, score change, etc.)
	 * @param status
	 */
	private void UpdateGameStatus(ZFStatus.StatusType status)	
	{
		server.display("Server --> Clients: UpdateGameStatus " + status);		
		ZFPlayer zps[] = new ZFPlayer[numPlayers];		
						
		for(int i=0; i< numPlayers; i++)
		{			
			Player player = players.get(i);
			if(player != null)
			{
				zps[i] = new ZFPlayer( player.getToken(), 
						player.getPlayerNumber(), 						
						player.getScore(), /* Design 7.1.7 v1.5 */ 
						player.getCardsInHand(), 
						player.getHand(), 
						player.getBooks());
			}
		}
		
		ZFStatus status_out = new ZFStatus(status, currentPlayerNumber, zps, gameEnabled, isGameOver);		
		
		this.server.BroadcastObject(status_out);
	}
	
	/**
	 * Complete the current player's turn and move to the next player.
	 * @param playerNumber Player number making the request.
	 */
	synchronized public void doTurnDone(int playerNumber)
	{
		if(playerNumber == this.currentPlayerNumber)
		{
			moveToNextPlayer();
			UpdateGameStatus(ZFStatus.StatusType.TURN_CHANGE);
		}
		else
		{
			server.display("doTurnDone() from invalid player!!!");
		}
	}
	
	/**
	 * Make a card request. ("do you have any eights?")
	 * @param playerNumber Player number making the request.
	 * @param cr ZFCardRequest object
	 */
	synchronized public void doCardRequest(int playerNumber, ZFCardRequest cr)
	{
		ZFCardRequestResponse response = null;
				
		if(playerNumber == this.currentPlayerNumber)
		{
			Player player = players.get(playerNumber);
			Player opponent = players.get(cr.getOpponentNumber());
			ZFCard[] hand = opponent.getHand();
			Set<ZFCard> passback = new HashSet<ZFCard>();
			CardRequestResult result = CardRequestResult.FROM_PLAYER;
			/* Design 7.1.12.1 v1.5 */
			for(ZFCard card: hand)
			{
				if(card.getValue() == cr.getCardValue())
				{					
					passback.add(card);	 /* Design 7.1.12.2 v1.5 */				
					opponent.removeCardFromHand(card);					
				}
			}
			/* Design 7.1.13 v1.5 */
			if((passback.size() == 0) && (deck.cardsLeft() > 0))
			{
				result = CardRequestResult.FROM_OCEAN;
				passback.add(deck.dealCard());			
			}
			else
			{
				result = CardRequestResult.NO_CARDS_IN_DECK;
			}

			if(passback.size() > 0)
			{
				// add cards to player's hand
				for(ZFCard card: passback)
				{
					player.addCardToHand(card);
				}

				// send new cards back
				ZFCard[] cards = new ZFCard[passback.size()]; 
				cards = passback.toArray(cards);								
				response = new ZFCardRequestResponse(result,cards, result.toString());
			}
			else
			{
				response = new ZFCardRequestResponse(result, null, result.toString());
			}
			
			UpdateGameStatus(ZFStatus.StatusType.CARDS_CHANGE);
			
			if(opponent.getCardsInHand() == 0)  // opponent out?
			{
				doGameOver();
			}	
		}
		else
		{
			response = new ZFCardRequestResponse(ZFCardRequestResponse.CardRequestResult.ERROR, null, "Not your turn!");						
		}
		players.get(playerNumber).sendObject(response);
	}
	
	/**
	 * Play a book.
	 * @param playerNumber Player number making the request.
	 * @param play ZFPlayBook object
	 */
	synchronized public void doPlayBook(int playerNumber, ZFPlayBook play)
	{
		if(playerNumber == this.currentPlayerNumber)
		{
			// TODO: Should validate book
			Player player = players.get(playerNumber);
			player.playBook(play.getBook());
			if(player.getCardsInHand() == 0)  // out?
			{
				doGameOver();
			}		
			else
				this.UpdateGameStatus(ZFStatus.StatusType.BOOK_PLAY);
		}	
		else
		{
			server.display("doPlayBook() from invalid player!!!");
		}
	}
	
	/**
	 * Handle the game over state.
	 */
	synchronized public void doGameOver()
	{		
		isGameOver = true;
		UpdateGameStatus(ZFStatus.StatusType.GAME_OVER);
	}
}

/**
 * Player in a ZFGame  (private)
 */
class Player {
	private String token;
	private ObjectOutputStream out;
	private int PlayerNumber;
	private List<ZFCard> hand;
	private List<ZFCard[]> books;
	
	/**
	 * Constructor
	 * @param PlayerNumber Player number in game
	 * @param token String used to represent this player. (name)
	 */
	public Player(int PlayerNumber, String token) {
		this.PlayerNumber = PlayerNumber;
		this.token = token;
		out = null;		
		hand = new ArrayList<ZFCard>();
		books = new ArrayList<ZFCard[]>();
	}		
	
	/**
	 * Sets the ObjectOutputStream used to write objects to the player.
	 * @param out ObjectOutputStream object
	 */
	public void setOutput(ObjectOutputStream out)
	{
		this.out = out;
	}
	
	/**
	 * Sets the player's number
	 * @param playerNumber Player's number
	 */
	public void setPlayerNumber(int playerNumber)
	{
		this.PlayerNumber = playerNumber;
	}
	
	/**
	 * Gets the player's number 
	 * @return int
	 */
	public int getPlayerNumber() {
		return PlayerNumber;
	}
	/**
	 * Gets the player's token
	 * @return String
	 */
	public String getToken() {
		return token;
	}
	/**
	 * Sets the player's token. (name)
	 * @param token String
	 */
	public void setToken(String token){
		this.token = token;
	}
	
	/**
	 * Mark this player disabled.
	 */
	public void disable() {
		out = null;
	}
	
	/**
	 * Add a card to this player's hand..
	 * @param card Card to add
	 */
	public void addCardToHand(ZFCard card)
	{
		hand.add(card);
	}
	
	/**
	 * Remove a card from this player's hand.
	 * @param card Card to remove.
	 * @return true if removed, false otherwise.
	 */
	public boolean removeCardFromHand(ZFCard card)
	{
		boolean removed = false;
		for(ZFCard inhand: hand)
		{
			if(inhand.equals(card))
			{
				hand.remove(inhand);
				removed = true;
				break;
			}
		}		
		return removed;
	}
	
	/**
	 * Remove all cards from this player's hand.
	 */
	public void removeAllCardsFromHand()
	{
		this.hand.clear();
	}
	
	/**
	 * Remove all books owned by this player.
	 */
	public void removeAllBooks()
	{
		this.books.clear();
	}
	
	/**
	 * clears the players hand and books.
	 */
	public void reset()
	{
		this.removeAllCardsFromHand();
		this.removeAllBooks();
	}
	
	/**
	 * Is this player enabled in the game?
	 * @return true is so, false otherwise.
	 */
	public boolean getEnabled()
	{
		return (out != null);
	}
	
	/**
	 * Returns the cards in this player's hand.
	 * @return ZFCard[]
	 */
	public ZFCard[] getHand()
	{
		ZFCard[] h = new ZFCard[hand.size()];
		h = hand.toArray(h);
		return h;		
	}
	
	/**
	 * Gets the number of cards in this player's hand.
	 * @return int
	 */
	public int getCardsInHand()
	{
		return hand.size();
	}
	
	/**
	 * Get the current books owned by this player.
	 * @return ZFCard[][]
	 */
	public ZFCard[][] getBooks()
	{		
		ZFCard[][] bks = new ZFCard[books.size()][];
		
		bks = books.toArray(bks);
		
		return bks;
	}
	
	/**
	 * Plays a book for this player.
	 * @param book Book to play
	 */
	public void playBook(ZFCard[] book)
	{
		books.add(book);
		for(ZFCard bookcard: book)
		{		
			for(int i=0; i<hand.size(); i++)
			{
				ZFCard handcard = hand.get(i);
				if(handcard.equals(bookcard))
				{
					hand.remove(handcard);
					break;
				}
			}
		}
	}
	
	/**
	 * Returns the player's score 
	 * @return Number of books at 1 point each
	 * 
	 * Design 7.1.8 v1.5
	 */
	public int getScore()
	{
		return this.books.size();
	}
	
	/**
	 * Send a private message to this player from the game admin.
	 * @param msg Message to send.
	 */
	public void sendMessageFromAdmin(String msg)
	{
		ZFChat chat = new ZFChat("Server ", msg);
		try
		{
			out.writeObject(chat);
			out.flush();
		}
		catch(Exception err)
		{		
			System.out.println(err.getMessage());
		}
	}
	
	/**
	 * Send an object only to this player.
	 * @param obj Network object to send.
	 */
	public void sendObject(Object obj)
	{
		try
		{
			out.writeObject(obj);
			out.flush();
		}
		catch(Exception err)
		{		
			System.out.println(err.getMessage());
		}
	}
}
