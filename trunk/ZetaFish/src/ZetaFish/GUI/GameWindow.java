package ZetaFish.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ZetaFish.ZetaFishClient;
import ZetaFish.ZetaFishServer;
import ZetaFish.Interfaces.*;
import ZetaFish.NetworkObjects.ZFPlayer;
import ZetaFish.NetworkObjects.ZFStatus;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *  Summary:
 *  Imports:
 *  Exports:
 *
 *  @author Patrick Michalina
 *  @author Chad A
 *  @author Melanie
 *
 *  @version 0.9
 *
 */

public class GameWindow extends JFrame implements ActionListener {
    /**
     * These local fields create instances of panels that wrap other panels.
     * BackgroundPanel is the primary panel that holds the main BG image.
     * logoPanel holds the image of the ZetaFish logo.
     * menuPanel holds the buttons that first appear on game creation.
     * infoPanel is holds status information for the game
     * gamePanel is the panel that contains several more game related panels
     */
    private static GameWindow ourGameWindow;

    private BackgroundPanel backgroundPanel;
    private Panel           logoPanel;
    private GamePanel       gamePanel;
    private JButton serverBtn = new JButton("Start New Game");
    private JButton playBtn   = new JButton("Join Game");
    //private JButton playNewBtn   = new JButton("Create Game");
    private JButton instBtn   = new JButton("Instructions");
    private JButton exitBtn   = new JButton("Exit");
    private JPanel  menuPanel;

    private FlowLayout windowLayout = new FlowLayout(0,220,80);
    
    private INetworkManager networkManager;
    private ZetaFishServer  server = null;

    /**
     *
     */
    public GameWindow() {
        super("ZetaFish"); //give the window a title
        initStartConditions(); //start the game with the inital view
        networkManager = new ZetaFishClient(); // start the network manager
    }
    
    /**
     *
     * @param args[]
     */
    public static void main(String args[]) {
         ourGameWindow = new GameWindow();
    }

    private void initStartConditions() {
        //create the baclground, logo, and menu panels
        backgroundPanel = new BackgroundPanel(new ImageIcon(getClass().getResource("/Resources/water.jpg")));
        logoPanel       = new Panel(new ImageIcon(getClass().getResource("/Resources/logo.png" )));

        setMenuPanel();

        //attach the logoPanel and menuPanel to the primary panel and set layout
        backgroundPanel.setLayout(windowLayout);
        backgroundPanel.add(logoPanel);
        backgroundPanel.add(menuPanel);

        //finally, attach the panel to the current JFrame
        this.add(backgroundPanel);

        //Below are aesthetic calls to the JFrame
        this.pack();
        this.setSize(800,600);
        this.isDoubleBuffered();
        this.isLightweight();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        //set visibility to on and refresh
        this.setVisible(true);
        this.validate();
    }

    private void serverCommand() {
        if(server == null) {
            server = new ZetaFishServer(null);
            
            String servername = "localhost";
            String playername = "Player Server";
            JoinGame(servername, playername);
        }
    }
    
    private void exitCommand() {
    	try {
            networkManager.closeConnection();
    	}
    	catch(Exception err) {
            HandleException(err);
    	}    	

        dispose();
        System.exit(0);
    }

    private void playCommand() {
        // TODO: Pop up a box asking for player name and server.
        String servername = "localhost";
        String playername = "Player Other";
        JoinGame(servername, playername);
    }
    
    private void JoinGame(String serverName, String playerName)
    {
    	try {
            //reset to blank window
            backgroundPanel.removeAll();
            backgroundPanel.repaint();

            //setup game screen
            backgroundPanel.setLayout(new BorderLayout());            

            gamePanel = new GamePanel(networkManager, serverName, playerName);
	        	        	        
	        backgroundPanel.add(gamePanel, BorderLayout.CENTER);
	
	        backgroundPanel.repaint();
	        backgroundPanel.validate();
                this.setSize(1024,768);
                this.setLocationRelativeTo(null);
    	}
    	catch(Exception err) {
            HandleException(err);
    	}	
    }
    
   

    private void setMenuPanel() {
        menuPanel       = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel,1));
        menuPanel.setOpaque(false);

        int pref_height = 25;
        int pref_width = 150;
        
        // TODO: Should center buttons on screen
        
        serverBtn.setPreferredSize(new Dimension(pref_width, pref_height));
        serverBtn.setMaximumSize(new Dimension(Short.MAX_VALUE,
                Short.MAX_VALUE));

        playBtn.setPreferredSize(new Dimension(pref_width, pref_height));
        playBtn.setMaximumSize(new Dimension(Short.MAX_VALUE,
                Short.MAX_VALUE));
        
        instBtn.setPreferredSize(new Dimension(pref_width, pref_height));
        instBtn.setMaximumSize(new Dimension(Short.MAX_VALUE,
                Short.MAX_VALUE));
        
        exitBtn.setPreferredSize(new Dimension(pref_width, pref_height));
        exitBtn.setMaximumSize(new Dimension(Short.MAX_VALUE,
                Short.MAX_VALUE));
        
        menuPanel.add(serverBtn);
        menuPanel.add(playBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        menuPanel.add(instBtn);
        menuPanel.add(exitBtn);
        
        serverBtn.setActionCommand("serverCMD");
        playBtn.setActionCommand(  "playCMD"  );
        instBtn.setActionCommand(  "instCMD"  );
        exitBtn.setActionCommand(  "exitCMD"  );

        //attach the buttons to the menu panel
        serverBtn.addActionListener(this);
        playBtn.addActionListener(this);
        instBtn.addActionListener(this);
        exitBtn.addActionListener(this);
    }

    private void setGamePanel() {

    }

    private void setChatPanel() {
        
    }


    /**
     * Single spot to determine how to handle exceptions
     * @param err
     */
    private void HandleException(Exception err) {
    	err.printStackTrace();    	
    }

    private void instructionCommand() {
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

    public void actionPerformed(ActionEvent e) {
    	if("serverCMD".equals(e.getActionCommand())) {
            serverCommand();
        }
    	else if("playCMD".equals(e.getActionCommand())) {
            playCommand();
        }

        else if("instCMD".equals(e.getActionCommand())) {
            instructionCommand();
        }

        else if("exitCMD".equals(e.getActionCommand())) {
            exitCommand();
        }
    }      
}