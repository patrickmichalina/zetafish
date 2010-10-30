package ZetaFish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ZetaFish.Interfaces.*;
import ZetaFish.NetworkObjects.*;

public class TestConsole implements IChatListener, IStatusListener, ITurnListener, ICardRequestResponseListener
{
	private static ZetaFishClient _client = new ZetaFishClient();
	private static INetworkManager nm = _client;
	private int gameID = -1;
		
	public final int OPEN_CONN_1 = 1;
	public final int OPEN_CONN_2 = 2;
	public final int SHOW_PLAYERS = 3;
	public final int START_GAME = 4;
		
	public final int SEND_MESSAGE = 5;
	
	public final int REQUEST_CARDS = 6;
	public final int PLAY_BOOK = 7;
	public final int END_TURN = 8;
	
	public final int SHOW_HAND = 9;


	public void DoCommand(String Command) throws Exception
	{
		
		try
		{
			int cmd = Integer.parseInt(Command);
			
			switch(cmd)
			{
				case OPEN_CONN_1:					
					openConnection("localhost", "zetafishai@gmail.com", "UIScsc479");
					break;
				
				case OPEN_CONN_2:					
					openConnection("localhost", "zetafishai2@gmail.com", "UIScsc479");					
					break;
					
					
				case SHOW_PLAYERS:
					ZFPlayer[] others = nm.getPlayers();
					if(others != null)
					{
						for(ZFPlayer other:others)
						{
							System.out.println(other.getPlayerName());
						}
					}
					else
					{
						System.out.println("Players array is null");
					}
					break;
				
				case START_GAME:
					nm.startGame();
					break;
								
					
				case SEND_MESSAGE:
					System.out.print("MESSAGE: ");
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					String msg=br.readLine();
					nm.sendMessage(msg);					
					break;
					
				case REQUEST_CARDS:
					int nextPlayer = (nm.getMyPlayerNumber() + 1) % nm.getNumberOfPlayers();
					int cardValue = 2;
					nm.RequestCards(nextPlayer, cardValue);
					break;		
					
				case PLAY_BOOK:
					ZFCard[] hand = nm.getHand();
					if(hand != null)
					{
						ZFCard[] book = new ZFCard[]{hand[0], hand[1], hand[2], hand[3]};						
						nm.PlayBook(book);
					}
					else
					{
						System.out.print("Invalid hand!");
					}
					break;		
				
				case END_TURN:	
					nm.DoneWithTurn();
					break;
					
				case SHOW_HAND:
					ZFCard[] hand2 = nm.getHand();
					if(hand2 != null)
					{
						for(ZFCard card: hand2)
						{
							System.out.print(card.toString() + " ");							
						}
					}
					break;	
										
				default:  // ignore
					break;			
			}
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}				
	}
	
	private void openConnection(String server, String user, String pass) throws Exception
	{
		nm.openConnection(server, user, pass);
		nm.addChatListener(this);
		nm.addStatusListener(this);
		nm.addTurnListener(this);
		nm.addCardRequestResponseListener(this);
	}
	
	private static void ShowMenu()
	{
		System.out.println("---------------------");
		System.out.println("ZetaFish console test");
		System.out.println("---------------------\n");
		System.out.println("1) Open Connection 1");
		System.out.println("2) Open Connection 2");
		System.out.println("3) Show other players");		
		System.out.println("4) Start Game");		
		System.out.println("5) Send Message");		
		System.out.println("6) Request Cards");
		System.out.println("7) Play book");
		System.out.println("8) End Turn");
		System.out.println("\n");
		System.out.println("Q) Quit");
		System.out.println("---------------------\n");				
		System.out.print(">> ");
	}
	
	/**
	 * @param args
	 */
	public static void main_CLI(String[] args)  throws IOException, Exception
	{		
		String msg;
		TestConsole test = new TestConsole();		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		ShowMenu();		
				
		while( !(msg=br.readLine()).toUpperCase().equals("Q"))
	    {
			test.DoCommand(msg);
			ShowMenu();
	    }			
			
		if(nm != null)			
			nm.closeConnection();		
		System.exit(0);
	}

	@Override
	public synchronized void OnNewMessage(String from, String msg) {
		System.out.println(from + ": " + msg);		
	}

	@Override
	public synchronized void OnGameStausChange(ZFStatus status) 
	{		
		System.out.println("STATUS CHANGE!");
		System.out.println("Status:" + status.getStatus());
		System.out.println("Current Player:" + status.getCurrentPlayer());
		System.out.println("Is Game Over?:" + status.getIsGameOver());
		System.out.println("Is Game Running?:" + status.getIsGameRunning());
		ZFPlayer[] players = status.getPlayers();
		System.out.println("Number of players:" + ((players == null) ? "null" : status.getPlayers().length));
		if(players != null)
		{
			for(ZFPlayer player: players)
			{
				System.out.println("\tName:" + player.getPlayerName());
				System.out.println("\tScore:" + player.getScore());
				System.out.println("\tCards in hand:" + player.getCardsInHand());				
			}
		}
	}

	@Override
	public synchronized void OnGameTurn() 
	{
		System.out.println("Your turn!");	
	}

	@Override
	public void OnCardRequestResponse(ZFCardRequestResponse response) 
	{
		System.out.println("CARD REQUEST RESPONSE");
		System.out.println("Result:" + response.getResult());
		System.out.println("Message:" + response.getMessage());
		System.out.println("Cards:");
		ZFCard[] cards = response.getCards();
		for(ZFCard card: cards)
		{
			System.out.println(card.toString());
		}
	}

}

