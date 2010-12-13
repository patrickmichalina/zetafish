package ZetaFish.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ZetaFish.VersionInfo;
import ZetaFish.ZetaFishClient;
import ZetaFish.ZetaFishServer;
import ZetaFish.Interfaces.INetworkManager;
import ZetaFish.Interfaces.IServerErrorListener;

/**
 * Main ZetaFish application window. 
 */
public class GameWindow extends JFrame implements IServerErrorListener, ActionListener 
{    
	private static final long serialVersionUID = 1L;
	
	private static final boolean SHOW_SERVER_WINDOW = false;

	/**
     * These local fields create instances of panels that wrap other panels.
     * BackgroundPanel is the primary panel that holds the main BG image.
     * logoPanel holds the image of the ZetaFish logo.
     * menuPanel holds the buttons that first appear on game creation.
     * infoPanel is holds status information for the game
     * gamePanel is the panel that contains several more game related panels
     */
    @SuppressWarnings("unused")
	private static GameWindow ourGameWindow;

    private BackgroundPanel backgroundPanel;
    private Panel           logoPanel;
    private GamePanel       gamePanel;
    private JButton serverBtn = new JButton("Start New Game");
    private JButton playBtn   = new JButton("Join Game");
    private JButton instBtn   = new JButton("Instructions");
    private JButton exitBtn   = new JButton("Exit");
    private JPanel  menuPanel;

    private FlowLayout windowLayout = new FlowLayout(FlowLayout.CENTER,220,80);
        
    private INetworkManager networkManager;
    private ZetaFishServer  server = null;
    private String 			args[] = null;

    /**
     * Constructor
     * @param args Command line arguments.
     */
    public GameWindow(String args[]) {
        super(); 
        
        setCustomTitle(""); //give the window a title
        
        this.args = args;
        initStartConditions(); //start the game with the inital view
        this.networkManager = new ZetaFishClient(); // start the network manager
        this.networkManager.addServerErrorListener(this);
    }
    
    /**
     * Main application entry point.
     * @param args Command line arguments.
     */
    public static void main(String args[]) {
         ourGameWindow = new GameWindow(args);
    }

    /**
     * Initializes the menu window at startup.
     */
    private void initStartConditions() {
        //create the background, logo, and menu panels
        backgroundPanel = new BackgroundPanel(new ImageIcon(getClass().getResource("/Resources/water.jpg")));
        logoPanel       = new Panel(new ImageIcon(getClass().getResource("/Resources/logo.png" )));
        setMenuPanel();

        showMenu();

        //finally, attach the panel to the current JFrame
        this.add(backgroundPanel);

        //Below are aesthetic calls to the JFrame
        this.pack();        
        this.isDoubleBuffered();
        this.isLightweight();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
//        backgroundPanel.setBorder(BorderFactory.createTitledBorder("bg"));
//        logoPanel.setBorder(BorderFactory.createTitledBorder("logo"));
//        menuPanel.setBorder(BorderFactory.createTitledBorder("menu"));
        
        //set visibility to on and refresh
        this.setVisible(true);
        this.validate();
    }
    
    private void setCustomTitle(String PlayerName)
    {
    	String title = "ZetaFish";
    	if(PlayerName.length() > 0)
    		title = "ZetaFish - " + PlayerName + " - " + VersionInfo.version();
    	else
    		title = "ZetaFish - " + VersionInfo.version();
    	
    	this.setTitle(title);
    	
    }
    
    /**
     * Shows the Menu window.
     */
    private void showMenu()
    {
    	/* Design 7.1.16 v1.5 */
    	this.setSize(800,600);
    	
    	backgroundPanel.removeAll();
    	
    	 //attach the logoPanel and menuPanel to the primary panel and set layout
        backgroundPanel.setLayout(windowLayout);
        backgroundPanel.add(logoPanel);
        backgroundPanel.add(menuPanel);
        
        backgroundPanel.repaint();
        
        if(server != null)
        {
        	server.dispose();
        	server = null;
        	try
        	{
        		this.networkManager.closeConnection();
        	}
        	catch(Exception err)
        	{
        		err.printStackTrace();
        	}
        }
    }
    
    /**
     * Layout the Menu window.
     */
    private void setMenuPanel() {
        menuPanel       = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel,BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);

        int pref_height = 25;
        int pref_width = 150;
               
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
    
    /**
     * Prompts the user for a game server to connect to.
     * @return server name
     */
    private String getServerName() {
    	String servername = "";
        try {
            //our little fishy
            ImageIcon image = new ImageIcon(getClass().getResource("/Resources/fishtest.png"));

            servername = (String)JOptionPane.showInputDialog(this,
                    "Please enter server",
                    "Server Entry",
                    JOptionPane.PLAIN_MESSAGE,
                    image,
                    null,
                    "localhost");
        } catch(Exception e) {
        	HandleException(e);
        }

        return servername;
    }
    
    /**
     * Prompts the user for a name to use in the game.
     * @return user name
     */
    private String getPlayerName() {
        try {
            //our little fishy
            ImageIcon image = new ImageIcon(getClass().getResource("/Resources/fishtest.png"));

            String playername = (String)JOptionPane.showInputDialog(this,
                    "Please enter your name:",
                    "Name Entry",
                    JOptionPane.PLAIN_MESSAGE,
                    image,
                    null,
                    "Your Name");
            
            //check if name is too short, if so, inform user
            if (playername.length() < 2) {
                JOptionPane.showMessageDialog(this,
                        "Name must contain more than two characters.",
                        "Error",
                        JOptionPane.INFORMATION_MESSAGE,
                        image);
            }
            else {
                //TODO: Maybe add a confirmation dialog
                return playername;
            }
        } catch(Exception e) {
        	HandleException(e);
        }

        return null;
    }

    /**
     * Start a new server and join it.
     */
    private void serverCommand() {

        String playername = getPlayerName();
        
        boolean ShowServerWindow = SHOW_SERVER_WINDOW;
        
        if((args != null) && (args.length > 0))
        {
        	if(args[0].toUpperCase().equals("SHOWSERVER"))
        		ShowServerWindow = true;
        }

        if(server == null && playername != null) {
            server = new ZetaFishServer(null, ShowServerWindow);
            
            String servername = "localhost";
            JoinGame(servername, playername, true);
        }
    }

    /**
     * Join a game running on an existing server.
     */
    private void playCommand() {
        String playerName = getPlayerName();

        if(playerName != null) {
            JoinGame(getServerName(), playerName, false);
        }
        
    }
    
    /**
     * Display in game instructions. (help)
     */
    private void instructionCommand() {
        JFrame instructionFrame = new JFrame("Instructions");
        
        String instructions = "";
        try {
        	InputStream is = getClass().getResourceAsStream("/Resources/instructions.txt");
        	BufferedReader br = new BufferedReader(new InputStreamReader(is));
        	String line;
        	while ((line = br.readLine()) != null) {
        		instructions += line + "\n";
            }
		} catch (IOException e) {
			e.printStackTrace();
			instructions = "Go Fish Instructions";
		}
        
        JTextArea text = new JTextArea(instructions);		
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);        
        instructionFrame.add(new JScrollPane(text));
        instructionFrame.pack();
        instructionFrame.setSize(800,600);
        instructionFrame.isDoubleBuffered();
        instructionFrame.isLightweight();
        instructionFrame.setLocationRelativeTo(null);
        instructionFrame.setResizable(false);
        instructionFrame.setVisible(true);
        instructionFrame.validate();
    }
    
    /**
     *  Exit the ZetaFish application.
     */
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
    
    /**
     * Connect to the game.
     * @param serverName Server to connect to.
     * @param playerName Local player name to use.
     * @param canStartGame Allow this player to start the game?
     */
    private void JoinGame(String serverName, String playerName, boolean canStartGame)
    {
    	boolean started = false;
    	try {
            //reset to blank window
            backgroundPanel.removeAll();
            backgroundPanel.repaint();
            
            // Add player name to title
            setCustomTitle(playerName);
            
            //setup game screen
            backgroundPanel.setLayout(new BorderLayout());            

            gamePanel = new GamePanel(networkManager, serverName, playerName, canStartGame);
	        	        	        
	        backgroundPanel.add(gamePanel, BorderLayout.CENTER);	               
	
	        backgroundPanel.repaint();
	        
	        backgroundPanel.validate();
            
	        /* Design 7.1.16 v1.5 */
	        this.setSize(1024,768);	        
            this.setLocationRelativeTo(null);
            
            started = true;
    	}
    	catch(Exception err) {
            HandleException(err);
            String msg = "Could not connect to game server\n" + err.getMessage();
            JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.OK_OPTION);
    	}
    	finally
    	{
    		if(!started)
    			showMenu();
    	}
    }    

    /**
     * See description in ActionListener
     */
    @Override
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
        
    /**
     * See description in IServerErrorListener
     */
	@Override
	public void OnServerError(String msg, boolean abortGame) {		
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.OK_OPTION);
        if(abortGame)
        	showMenu();	
	}
	
	 /**
     * Single spot to determine how to handle exceptions
     * @param err Exception to handle
     */
    private void HandleException(Exception err) {
    	err.printStackTrace();    	
    }    
}