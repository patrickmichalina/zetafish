package ZetaFish;

import java.awt.BorderLayout;
import java.io.IOException;
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
 * Main class for ZetaFishServer
 * 
 * @author calbrecht
 *
 */
public class ZetaFishServer extends JFrame
{
	private ZFServerThread serverThread = null;
	
	public static final int DEFAULT_PORT = 5000;
	public static final boolean INCLUDE_JOKERS = false;
	
	private JTextArea output;
	private JScrollPane scrollPane;	
	
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
		serverThread = new ZFServerThread(this, port, this.INCLUDE_JOKERS);
		serverThread.start();
	}
	
	public synchronized void display(String s) 
	{
		output.append(s + "\n");
	}
	
	public synchronized Set<ZFClientResponseHandler> getActivePlayers()
	{
		return serverThread.activePlayers;
	}
	
	public synchronized void BroadcastObject(Object obj)
	{
		serverThread.BroadcastObject(obj);		
	}
	
	/**
	 * @param args
	 */
	public static void main_SVR(String[] args) {
		// TODO Auto-generated method stub
		new ZetaFishServer(args, true);
	}
}

class ZFServerThread extends Thread 
{	
	private ZetaFishServer ZFserver;
	private boolean includeJokers;
	private ZFGame game;
	private int port;
	
	static protected Set<ZFClientResponseHandler> activePlayers = new HashSet<ZFClientResponseHandler>();
	
	public ZFServerThread(ZetaFishServer server, int port, boolean includeJokers)
	{
		this.ZFserver = server;
		this.port = port;
		this.includeJokers = includeJokers;
	}
	
	public void run()
	{		
		game = new ZFGame(ZFserver, this.includeJokers);
		try {
			ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName("127.0.0.1"));
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
			ZFserver.display(e.toString());
		}		
	}
	
	public synchronized Set<ZFClientResponseHandler> getActivePlayers()
	{
		return this.activePlayers;
	}
	
	public synchronized void BroadcastObject(Object obj)
	{
		for(ZFClientResponseHandler prh: this.activePlayers)
		{						
			prh.getPlayer().sendObject(obj);								
		}
	}
}

/**
 * ZFClientResponseHandler: Threaded handler for client communication
 * @author calbrecht
 *
 */
class ZFClientResponseHandler extends Thread {
	private Socket connection;
	private ZFGame game;
	private ZetaFishServer server;

	private ObjectOutputStream out;
	private ObjectInputStream in;

	private Player player;
		
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
	
	public Player getPlayer()
	{
		return this.player;
	}
	
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
			else
			{
				player.sendObject(new RuntimeException("Player limit reached"));			
			}
		}
		catch(Exception err)
		{
			// TODO: Implement better exception handling
			HandleLocalException(err);
		}
		finally
		{
			closeLink();
		}	
	}
	
	private void HandleLocalException(Exception error)
	{
		//System.out.println(err.getMessage());
		server.display(error.toString());
	}
	
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
					done = true;
				else 
				{							
					server.display("Client --> Server: " + oin.toString());
					parseObjectIn(oin);
				}
				
			}
			catch(IOException e) {
				server.display(e.toString());
				done = true;
			}
		}
		in.close();
	}	
	
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
	
	private void parsePlayerCardRequest(ZFCardRequest cr)
	{
		game.doCardRequest(player.getPlayerNumber(), cr);		
	}
	
	private void parsePlayerPlayBook(ZFPlayBook play)
	{
		game.doPlayBook(player.getPlayerNumber(), play);		
	}
	
	private void closeLink() {
		try {
			game.delPlayer(player.getPlayerNumber());
			if((connection != null) && (!connection.isClosed()))
					connection.close();
			server.display("Client connection closed by " + player.getToken() + "\n");
		}
		catch(Exception e) 
		{
			server.display(e.toString());
		}
	}
}
/**
 * ZFGame: Game Manager
 * @author calbrecht
 *
 */
class ZFGame {
	public final int MAX_PLAYERS = 6;
	public final int INITIAL_HAND_SIZE = 7;
	
	private List<Player> players;
	private int numPlayers;
	private boolean gameEnabled, isGameOver;
	private int currentPlayerNumber;
	private ZFDeck deck;
	
	private ZetaFishServer server;
	
	public ZFGame(ZetaFishServer server, boolean includeJokers) {
		this.server = server;
		deck = new ZFDeck(includeJokers);	
		players = new ArrayList<Player>();		
		numPlayers = 0;
		gameEnabled = false;
		isGameOver = false;
	}
	
	public boolean getGameEnabled()
	{
		return this.gameEnabled;
	}
	
	public boolean getIsGameOver()
	{
		return this.isGameOver;
	}
		
	synchronized public Player addPlayer(ObjectOutputStream out)
	{
		Player newPlayer = null;
		
		if(gameEnabled)
		{
			String error = "Game already enabled.  Player rejected.";
			server.display(error);
			Player p = new Player(numPlayers, "Player" + numPlayers);
			p.setOutput(out);
			p.sendObject(new RuntimeException(error));
		}
		else if (numPlayers < MAX_PLAYERS)
		{			
			newPlayer = new Player(numPlayers, "Player" + numPlayers);
			newPlayer.setOutput(out);
			players.add(newPlayer);			

                        String token = newPlayer.getToken();
			server.display("Server --> Client: Player connected " + token);
			//newPlayer.sendMessageFromAdmin("Player connected " + token);
			newPlayer.sendObject(new ZFStatus(ZFStatus.StatusType.ASSIGN_PLAYER_NUMBER, numPlayers, null, gameEnabled, isGameOver));			
			numPlayers++;		
		}
		return newPlayer;
	}
	
	synchronized public void startGame() 
	{
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
//			Player current = players.get(currentPlayerNumber); 
//			current.sendMessageFromAdmin("Game is ready to begin");	
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
	
	private void UpdateGameStatus(ZFStatus.StatusType status)	
	{
		int num_players = players.size();
		ZFPlayer zps[] = new ZFPlayer[num_players];		
		
		for(int i=0; i< num_players; i++)
		{			
			Player player = players.get(i);
			zps[i] = new ZFPlayer( player.getToken(), 
					               player.getPlayerNumber(), 
					               player.getScore(), 
					               player.getCardsInHand(), 
					               player.getHand(), 
					               player.getBooks());			
		}
		
		ZFStatus status_out = new ZFStatus(status, currentPlayerNumber, zps, gameEnabled, isGameOver);		
		
		this.server.BroadcastObject(status_out);
	}
	
	synchronized public void delPlayer(int playerNumber) {
		players.get(playerNumber).disable();
		players.set(playerNumber,null);
		numPlayers--;
		
		if (numPlayers == 0) 
		{
			gameEnabled = false;
			isGameOver = true;
		}
	}
	
	synchronized public void doTurnDone(int playerNumber)
	{
		if(playerNumber == this.currentPlayerNumber)
		{
			currentPlayerNumber = (currentPlayerNumber + 1) % numPlayers;
			UpdateGameStatus(ZFStatus.StatusType.TURN_CHANGE);
		}
		else
		{
			// TODO: Should handle "not your turn" in a generic way
		}
	}
	
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
			for(ZFCard card: hand)
			{
				if(card.getValue() == cr.getCardValue())
				{
					passback.add(card);					
					opponent.removeCardFromHand(card);					
				}
			}
			if(passback.size() == 0)
			{
				result = CardRequestResult.FROM_OCEAN;
				passback.add(deck.dealCard());			
			}
			
			// add cards to player's hand
			for(ZFCard card: passback)
			{
				player.addCardToHand(card);
			}
			
			// send new cards back
			ZFCard[] cards = new ZFCard[passback.size()]; 
			cards = passback.toArray(cards);								
			response = new ZFCardRequestResponse(result,cards, result.toString());
			
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
			// TODO: Should handle "not your turn" in a generic way
		}
	}
	
	synchronized public void doGameOver()
	{		
		isGameOver = true;
		UpdateGameStatus(ZFStatus.StatusType.GAME_OVER);
	}
	
	
}

/**
 * Player in a ZFGame  (private)
 * @author calbrecht
 *
 */
class Player {
	private String token;
	private ObjectOutputStream out;
	private int PlayerNumber;
	private List<ZFCard> hand;
	private List<ZFCard[]> books;
			
	public Player(int PlayerNumber, String token) {
		this.PlayerNumber = PlayerNumber;
		this.token = token;
		out = null;		
		hand = new ArrayList<ZFCard>();
		books = new ArrayList<ZFCard[]>();
	}		
	public void setOutput(ObjectOutputStream out)
	{
		this.out = out;
	}
	public int getPlayerNumber() {
		return PlayerNumber;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token){
		this.token = token;
	}
	
	public void disable() {
		out = null;
	}
	
	public void addCardToHand(ZFCard card)
	{
		hand.add(card);
	}
	
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
	
	public void removeAllCardsFromHand()
	{
		this.hand.clear();
	}
	
	public void removeAllBooks()
	{
		this.books.clear();
	}
	
	public void reset()
	{
		this.removeAllCardsFromHand();
		this.removeAllBooks();
	}
	
	public ZFCard[] getHand()
	{
		ZFCard[] h = new ZFCard[hand.size()];
		h = hand.toArray(h);
		return h;		
	}
	
	public int getCardsInHand()
	{
		return hand.size();
	}
	
	public ZFCard[][] getBooks()
	{		
		ZFCard[][] bks = new ZFCard[books.size()][];
		
		bks = books.toArray(bks);
		
		return bks;
	}
	
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
	 * (Requirement 3.1.8)
	 * @return Number of books at 1 point each
	 */
	public int getScore()
	{
		return this.books.size();
	}
	
	
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
