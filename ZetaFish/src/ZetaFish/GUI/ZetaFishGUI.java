package ZetaFish.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ZetaFish.ZetaFishClient;
import ZetaFish.ZetaFishServer;
import ZetaFish.Interfaces.*;
import ZetaFish.NetworkObjects.ZFPlayer;
import ZetaFish.NetworkObjects.ZFStatus;


/**
 *
 * @author Patrick
 */
public class ZetaFishGUI extends JFrame implements ActionListener, IStatusListener
{
    private BackgroundPanel backgroundPanel;
    private Panel           logoPanel;
    private MenuPanel       menuPanel;

    private ChatPanel   chatPanel;
    private InfoPanel       infoPanel;
    private GamePanel       gamePanel;

    private FlowLayout      windowLayout;
    
    private INetworkManager networkManager;
    private ZetaFishServer  server = null;

    /**
     *
     */
    public ZetaFishGUI()
    {
        super("ZetaFish");

        //start the game with the inital view
        initStartConditions();
        
        networkManager = new ZetaFishClient();
    }
    
    /**
     *
     * @param args
     */
    public static void main(String args[])
    {
        ZetaFishGUI ourWindow = new ZetaFishGUI();
    }

    private void initStartConditions() {
        windowLayout    = new FlowLayout(0,220,80);

        backgroundPanel = new BackgroundPanel(new ImageIcon(getClass().getResource("/Resources/water.jpg" )));
        logoPanel       = new Panel(new ImageIcon(getClass().getResource("/Resources/logo.png" )));
        menuPanel       = new MenuPanel();

        

        backgroundPanel.setLayout(windowLayout);

       
        menuPanel.serverBtn.addActionListener(this);
        menuPanel.playBtn.addActionListener(this);
        menuPanel.instBtn.addActionListener(this);
        menuPanel.exitBtn.addActionListener(this);
        

        this.add(backgroundPanel);
        backgroundPanel.add(logoPanel);
        backgroundPanel.add(menuPanel);

        this.pack();
        this.setSize(800,650);
        this.isDoubleBuffered();
        this.isLightweight();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.validate();
    }

    private void serverCommand()
    {
    	if(server == null)
    		server = new ZetaFishServer(null);    	
    }
    
    private void exitCommand()
    {
    	try
    	{
    		networkManager.closeConnection();
    	}
    	catch(Exception err)
    	{
    		HandleException(err);
    	}    	
        dispose();                      
        System.exit(0);
    }

    private void playCommand()
    {
    	try
    	{
	    	networkManager.openConnection("localhost", "My User Name", "My Password");
	    		    	
	        //reset to blank window
	        backgroundPanel.removeAll();
	        backgroundPanel.repaint();
	
	        //setup game screen
	        backgroundPanel.setLayout(new BorderLayout());
	
	        chatPanel = new ChatPanel(networkManager);
	        infoPanel = new InfoPanel();
	        gamePanel = new GamePanel();
	        
	        networkManager.addChatListener(chatPanel);
	        
	        backgroundPanel.add(infoPanel, BorderLayout.PAGE_START);
	        backgroundPanel.add(gamePanel, BorderLayout.CENTER);
	        backgroundPanel.add(chatPanel, BorderLayout.PAGE_END);
	
	        backgroundPanel.repaint();
	        backgroundPanel.validate();
    	}
    	catch(Exception err)
    	{
    		HandleException(err);
    	}
    }
    /**
     * Single spot to determine how to handle exceptions
     * @param err
     */
    private void HandleException(Exception err)
    {
    	err.printStackTrace();    	
    }

    private void instructionCommand()
    {
        JFrame instructionFrame = new JFrame("Instructions");
        JTextArea text = new JTextArea("Instructions go here... I wonder how long instructions are for GoFish??");
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);

        instructionFrame.add(new JScrollPane(text));

        instructionFrame.pack();
        instructionFrame.setSize(400,400);
        instructionFrame.isDoubleBuffered();
        instructionFrame.isLightweight();
        instructionFrame.setLocationRelativeTo(null);
        instructionFrame.setResizable(false);
        instructionFrame.setVisible(true);
        instructionFrame.validate();
    }

    public void actionPerformed(ActionEvent e)
    {
    	if("serverCMD".equals(e.getActionCommand()))
        {
            serverCommand();
        }
    	else if("playCMD".equals(e.getActionCommand()))
        {
            playCommand();
        }

        else if("instCMD".equals(e.getActionCommand()))
        {
            instructionCommand();
        }

        else if("exitCMD".equals(e.getActionCommand()))
        {
            exitCommand();
        }
    }

	@Override
	public void OnGameStausChange(ZFStatus status) 
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
}