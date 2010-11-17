package ZetaFish;

import java.util.*;
import java.io.*;
import java.net.*;

import ZetaFish.Interfaces.*;
import ZetaFish.NetworkObjects.*;

public class ZetaFishClient extends Thread implements INetworkManager
{
	public static final int SERVER_PORT = 5000;
	private Socket client = null;
		
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private transient Vector<IChatListener> ChatListeners;
	private transient Vector<IStatusListener> StatusListeners;
	private transient Vector<ITurnListener> TurnListeners;
	private transient Vector<ICardRequestResponseListener> CardRequestResponseListeners;
	private transient Vector<IRemovePlayerListener> RemovePlayerListeners;
	private transient Vector<IServerErrorListener> ServerErrorListeners;
	
	private String WhoAmI;
	private int myPlayerNumber = -1;
	
	private ZFStatus lastStatus = null;
	
	
	public ZetaFishClient() 
	{		
	}
	
	/**
	 * Thread.run
	 * Entry point for the thread
	 */
	public void run()
	{				
		boolean done = false;
		boolean serverExited = false;		
		try
		{
			String msg = "";
			this.sendMessage("(Joined the game)");
			in = new ObjectInputStream(client.getInputStream());			
			while (!done) 
			{
				try
				{					
					Object oin = in.readObject();
					if(oin == null)
						done = true;
					else 
					{
						parseObjectIn(oin);					
					}						
				}
				catch(RuntimeException rex)
				{
					done = true;					
					msg = rex.getMessage();
					if(msg == null)
						serverExited = true;
				}
				catch(Exception err)
				{
					msg = err.getMessage();
					System.out.println("CLIENT EXCEPTION(1):" + msg);
					err.printStackTrace();
					if((msg == "Connection reset") || (msg == "socket closed"))
					{
						done = true;
						serverExited = true;
					}
				}			
			}
			in.close();
			client.close();			
			if(serverExited)
				NotifyServerErrorListeners("Game server exited.");
			else 
				NotifyServerErrorListeners(msg);
		}
		catch(Exception err)
		{
			System.out.print("CLIENT EXCEPTION(2):" + err.getMessage());
			done = true;
		}		
	}
	
	private void parseObjectIn(Object oin) 		
	{	
		System.out.print("parseObjectIn() oin.getClass=" + oin.getClass());
		if(oin.getClass() == ZFChat.class)
		{
			parseChatCommand((ZFChat) oin);
		}	
		else if(oin.getClass() == ZFStatus.class)
		{
			parseStatus((ZFStatus)oin);
		}
		else if(oin.getClass() == ZFCardRequestResponse.class)
		{
			parseCardRequestResponse((ZFCardRequestResponse)oin);
		}
		else if(oin.getClass() == ZFRemovePlayer.class)
		{
			parseRemovePlayer((ZFRemovePlayer)oin);
		}
		else if(oin.getClass() == RuntimeException.class)
		{
			parseRuntimeException((RuntimeException)oin);
		}
	}
	
	private void parseRuntimeException(RuntimeException exception)
	{
		System.out.print("RuntimeException from server:" + exception.getMessage());
		throw(exception);					
	}

	private void parseChatCommand(ZFChat c)
	{	
		// Notify all event listeners
		Vector<IChatListener> targets;
	    synchronized (this) {
	    	targets =  new Vector<IChatListener>(ChatListeners);
	    }
	    Enumeration<IChatListener> e = targets.elements();
	    while (e.hasMoreElements()) 
	    {
	    	IChatListener l = (IChatListener) e.nextElement();
	        l.OnNewMessage(c.from, c.msg);
	    }
	}
	
	private void parseStatus(ZFStatus status)
	{
		this.lastStatus = status;
		switch(status.getStatus())
		{		
			case GAME_STARTED:
				break;
			case GAME_OVER:
				break;
			case TURN_CHANGE:
				break;
		
			case ASSIGN_PLAYER_NUMBER:
				myPlayerNumber = status.getCurrentPlayer();
				break;
				
			case NONE:
			default:
				break;
		}
		NotifyStatusListeners();
	    
	    if((status.getIsGameRunning()) && (myPlayerNumber == this.lastStatus.getCurrentPlayer()))
	    	NotifyTurnListeners();
	}
	
	private void parseCardRequestResponse(ZFCardRequestResponse response)
	{
		NotifyCardRequestResponseListeners(response);
	}
	
	private void parseRemovePlayer(ZFRemovePlayer remove)
	{
		NotifyRemovePlayerListeners(remove);
	}
		
	private void NotifyStatusListeners()
	{
		// Notify all status event listeners
		Vector<IStatusListener> targets;
	    synchronized (this) {
	    	targets = new Vector<IStatusListener>(StatusListeners);
	    }
	    Enumeration<IStatusListener> e = targets.elements();
	    while (e.hasMoreElements()) 
	    {
	    	IStatusListener l = (IStatusListener) e.nextElement();
	        l.OnGameStausChange(this.lastStatus);
	    }
	}
	
	private void NotifyTurnListeners()
	{
		// Notify all turn event listeners
		Vector<ITurnListener> targets;
	    synchronized (this) {
	    	targets = new Vector<ITurnListener>(TurnListeners);
	    }
	    Enumeration<ITurnListener> e = targets.elements();
	    while (e.hasMoreElements()) 
	    {
	    	ITurnListener l = (ITurnListener) e.nextElement();
	        l.OnGameTurn();
	    }
	}
	
	private void NotifyRemovePlayerListeners(ZFRemovePlayer remove)
	{
		// Notify all remove player event listeners
		Vector<IRemovePlayerListener> targets;
	    synchronized (this) {
	    	targets =  new Vector<IRemovePlayerListener>(RemovePlayerListeners);
	    }
	    Enumeration<IRemovePlayerListener> e = targets.elements();
	    while (e.hasMoreElements()) 
	    {
	    	IRemovePlayerListener l = (IRemovePlayerListener) e.nextElement();
	        l.OnRemovePlayer(remove);
	    }
	}
	
	private void NotifyCardRequestResponseListeners(ZFCardRequestResponse response)
	{
		// Notify all turn event listeners
		Vector<ICardRequestResponseListener> targets;
	    synchronized (this) {
	    	targets =  new Vector<ICardRequestResponseListener>(CardRequestResponseListeners);
	    }
	    Enumeration<ICardRequestResponseListener> e = targets.elements();
	    while (e.hasMoreElements()) 
	    {
	    	ICardRequestResponseListener l = (ICardRequestResponseListener) e.nextElement();
	        l.OnCardRequestResponse(response);
	    }
	}
	
	private void NotifyServerErrorListeners(String msg)
	{
		// Notify all turn event listeners
		Vector<IServerErrorListener> targets;
	    synchronized (this) {
	    	targets =  new Vector<IServerErrorListener>(ServerErrorListeners);
	    }
	    Enumeration<IServerErrorListener> e = targets.elements();
	    while (e.hasMoreElements()) 
	    {
	    	IServerErrorListener l = (IServerErrorListener) e.nextElement();
	        l.OnServerError(msg);
	    }
	}
	
	/**
	 * Sends a given object to the server
	 * @param obj Object to send
	 */
	private void sendObject(Object obj) throws Exception
	{
		if (out != null) 
		{			
			out.writeObject(obj);
			out.flush();								
		}		
	}
	
	// ==================================================	
	// Begin INetworkManager implementation
	// ==================================================
	@Override
	public synchronized void openConnection(String Server, String myUserName, String myPassword)	throws Exception 
	{
		this.client = new Socket(Server, SERVER_PORT);
		this.client.setKeepAlive(true);
		this.out = new ObjectOutputStream(client.getOutputStream());

		this.WhoAmI = myUserName;

		new Thread(this).start();
	}
	
	@Override
	public synchronized void closeConnection() throws Exception
	{
		out.close();
		if(!client.isClosed())
			client.close();	
	}
	
	@Override
	public synchronized void startGame() throws Exception
	{
		ZFCommand cmd = new ZFCommand(ZFCommand.CommandType.START_GAME);
		sendObject(cmd);		
	}

	@Override
	public synchronized void exitGame() throws Exception
	{
		this.closeConnection();		
	}
	
	@Override
	public synchronized ZFPlayer[] getPlayers() 
	{
		return (lastStatus == null) ? null : lastStatus.getPlayers();
	}
	
	@Override
	public synchronized int getMyPlayerNumber()
	{
		return this.myPlayerNumber;
	}
	
	@Override
	public synchronized int getNumberOfPlayers()
	{
		return (lastStatus == null) || (lastStatus.getPlayers() == null) ? 0 : lastStatus.getPlayers().length;
	}

	@Override
	public synchronized void addChatListener(IChatListener listener)
	{
		if (ChatListeners == null)
			ChatListeners = new Vector<IChatListener>();
		ChatListeners.addElement(listener);		
	}

	@Override
	public synchronized void sendMessage(String msg) throws Exception 
	{
		ZFChat chat = new ZFChat(WhoAmI, msg); 
		sendObject(chat);
	}
	
	@Override
	public synchronized void addStatusListener(IStatusListener listener)
	{
		if (StatusListeners == null)
			StatusListeners = new Vector<IStatusListener>();
		StatusListeners.addElement(listener);
	}
		
	@Override
	public synchronized void addTurnListener(ITurnListener listener)
	{
		if (TurnListeners == null)
			TurnListeners = new Vector<ITurnListener>();
		TurnListeners.addElement(listener);
	}
	
	@Override
	public synchronized void addRemovePlayerListener(IRemovePlayerListener listener)
	{
		if (RemovePlayerListeners == null)
			RemovePlayerListeners = new Vector<IRemovePlayerListener>();
		RemovePlayerListeners.addElement(listener);
	}
	
	@Override
	public synchronized void addServerErrorListener(IServerErrorListener listener)
	{
		if (ServerErrorListeners == null)
			ServerErrorListeners = new Vector<IServerErrorListener>();
		ServerErrorListeners.addElement(listener);
	}
	
	@Override
	public synchronized void DoneWithTurn() throws Exception
	{
		ZFCommand cmd = new ZFCommand(ZFCommand.CommandType.TURN_DONE);
		sendObject(cmd);
	}
	
	@Override
	public synchronized void addCardRequestResponseListener(ICardRequestResponseListener listener)
	{
		if (CardRequestResponseListeners == null)
			CardRequestResponseListeners = new Vector<ICardRequestResponseListener>();
		CardRequestResponseListeners.addElement(listener);
	}
	
	@Override
	public synchronized void RequestCards(int From, int CardValue) throws Exception
	{
		ZFCardRequest request = new ZFCardRequest(this.myPlayerNumber, From, CardValue);
		sendObject(request);		
	}	
	
	@Override
	public synchronized void PlayBook(ZFCard[] book) throws Exception
	{
		ZFPlayBook play = new ZFPlayBook(book);
		sendObject(play);		
	}	
	
	@Override
	public synchronized ZFCard[] getHand() 
	{
		return (lastStatus == null) ? null : lastStatus.getPlayers()[myPlayerNumber].getHand();
	}
		
	@Override
	public synchronized int getScore(int p) 
	{	
		return (lastStatus == null) ? null : lastStatus.getPlayers()[p].getScore();
	}
	 
	@Override
	public synchronized boolean getIsGameRunning() 
	{	
		return (lastStatus == null) ? false : lastStatus.getIsGameRunning();
	}
	
	@Override
	public synchronized boolean getIsGameOver() 
	{	
		return (lastStatus == null) ? false : lastStatus.getIsGameOver();
	}
	// ==================================================	
	// End INetworkManager implementation
	// ==================================================		
}




