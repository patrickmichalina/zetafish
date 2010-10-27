package ZetaFish;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ZetaFish.NetworkObjects.*;


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
	public static final boolean INCLUDE_JOKERS = true;
	
	private JTextArea output;
	private JScrollPane scrollPane;	
	
	public ZetaFishServer(String args[]) 
	{		
		super("ZetaFish Server");
		output = new JTextArea();
		scrollPane = new JScrollPane(output);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		setSize(300,300);
		setVisible(true);
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
		new ZetaFishServer(args);
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
			ServerSocket server = new ServerSocket(port);
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
	private Deck deck;
	
	private ZetaFishServer server;
	
	public ZFGame(ZetaFishServer server, boolean includeJokers) {
		this.server = server;
		deck = new Deck(includeJokers);	
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
			newPlayer.sendMessageFromAdmin("Player connected " + token);
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
			
			for(Player player:players)
			{
				for(int i=0; i<INITIAL_HAND_SIZE; i++)
				{
					try
					{
						Card newCard = deck.dealCard();
						player.addCardToHand(newCard);
					}
					catch(Exception err)
					{
						server.display("!!! Deck Exception");	
					}
				}			
			}		
			UpdateGameStatus();
						
			Player current = players.get(currentPlayerNumber); 
			current.sendMessageFromAdmin("Game is ready to begin");	
		}
		else
		{
			server.display("startGame: Not enough players");		
		}
	}
	
	private void UpdateGameStatus()	
	{
		int num_players = players.size();
		ZFPlayer zps[] = new ZFPlayer[num_players];		
		
		for(int i=0; i< num_players; i++)
		{			
			Player player = players.get(i);
			zps[i] = new ZFPlayer(player.getToken(), player.getScore(), player.getCardsInHand(), player.getHand(), player.getBooks());			
		}
		
		ZFStatus status = new ZFStatus(ZFStatus.StatusType.GAME_STARTED,
				currentPlayerNumber, zps, gameEnabled, isGameOver);		
		
		this.server.BroadcastObject(status);
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
			UpdateGameStatus();
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
			Player opponent = players.get(cr.getOpponentNumber());
			Card[] hand = opponent.getHand();
			Set<Card> passback = new HashSet<Card>();
			for(Card card: hand)
			{
				if(card.getValue() == cr.getCardValue())
				{
					passback.add(card);
					opponent.removeCardFromHand(card);					
				}
			}
			if(passback.size() > 0)
			{
				Card[] cards = new Card[passback.size()]; 
				cards = passback.toArray(cards);
				response = new ZFCardRequestResponse(ZFCardRequestResponse.CardRequestResult.FROM_PLAYER, 
						cards, "From player!");
			}
			else
			{
				passback.add(deck.dealCard());
				Card[] cards = new Card[passback.size()];
				cards = passback.toArray(cards);
				response = new ZFCardRequestResponse(ZFCardRequestResponse.CardRequestResult.FROM_OCEAN, 
						cards, "From ocean!");
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
			this.UpdateGameStatus();
		}	
		else
		{
			// TODO: Should handle "not your turn" in a generic way
		}
	}
	
	synchronized public void doGameOver()
	{		
		isGameOver = true;
		UpdateGameStatus();
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
	private List<Card> hand;
	private List<Card[]> books;
			
	public Player(int PlayerNumber, String token) {
		this.PlayerNumber = PlayerNumber;
		this.token = token;
		out = null;		
		hand = new ArrayList<Card>();
		books = new ArrayList<Card[]>();
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
	public void disable() {
		out = null;
	}
	
	public void addCardToHand(Card card)
	{
		hand.add(card);
	}
	
	public boolean removeCardFromHand(Card card)
	{
		boolean removed = false;
		for(Card inhand: hand)
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
	
	public Card[] getHand()
	{
		Card[] h = new Card[hand.size()];
		h = hand.toArray(h);
		return h;		
	}
	
	public int getCardsInHand()
	{
		return hand.size();
	}
	
	public List<Card[]> getBooks()
	{		
		return books;
	}
	
	public void playBook(Card[] book)
	{
		books.add(book);
		for(Card bookcard: book)
		{		
			for(int i=0; i<hand.size(); i++)
			{
				Card handcard = hand.get(i);
				if(handcard.equals(bookcard))					
					hand.remove(handcard);
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
		ZFChat chat = new ZFChat("GameAdmin", msg);
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
