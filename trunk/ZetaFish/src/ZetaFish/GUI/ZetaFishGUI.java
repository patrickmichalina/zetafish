package ZetaFish.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Patrick
 */
public class ZetaFishGUI extends JFrame implements ActionListener {
    private BackgroundPanel backgroundPanel;
    private Panel           logoPanel;
    private MenuPanel       menuPanel;

    private ChatInterface   chatPanel;
    private InfoPanel       infoPanel;
    private GamePanel       deckPanel;

    private FlowLayout      windowLayout;
    private BoxLayout       menuBtnLayout;

    /**
     *
     */
    public ZetaFishGUI() {
        super("ZetaFish");

        //start the game with the inital view
        initStartConditions();
    }
    
    /**
     *
     * @param args
     */
    public static void main(String args[]) {
        ZetaFishGUI ourWindow = new ZetaFishGUI();
}

    private void initStartConditions() {
        windowLayout    = new FlowLayout(0,220,80);

        backgroundPanel = new BackgroundPanel(new ImageIcon(getClass().getResource("/Resources/water.jpg" )));
        logoPanel       = new Panel(new ImageIcon(getClass().getResource("/Resources/logo.png" )));
        menuPanel       = new MenuPanel();

        

        backgroundPanel.setLayout(windowLayout);

       

        menuPanel.playBtn.addActionListener(this);
        menuPanel.instBtn.addActionListener(this);
        menuPanel.exitBtn.addActionListener(this);
        

        this.add(backgroundPanel);
        backgroundPanel.add(logoPanel);
        backgroundPanel.add(menuPanel);

        this.pack();
        this.setSize(800,600);
        this.isDoubleBuffered();
        this.isLightweight();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.validate();
    }

    private void exitCommand() {
        dispose();
        System.exit(0);
    }

    private void playCommand() {
        //reset to blank window
        backgroundPanel.removeAll();
        backgroundPanel.repaint();

        //setup game screen
        backgroundPanel.setLayout(new BorderLayout());

        chatPanel = new ChatInterface();
        infoPanel = new InfoPanel();
        deckPanel = new GamePanel();
        
        backgroundPanel.add(infoPanel, BorderLayout.PAGE_START);
        backgroundPanel.add(deckPanel, BorderLayout.CENTER);
        backgroundPanel.add(chatPanel, BorderLayout.PAGE_END);

        backgroundPanel.repaint();
        backgroundPanel.validate();
    }

    private void instructionCommand() {
        //opens instructions in a new JFrame or in .txt
    }

    public void actionPerformed(ActionEvent e) {
        if("playCMD".equals(e.getActionCommand())){
            playCommand();
        }

        if("instCMD".equals(e.getActionCommand())){
            instructionCommand();
        }

        if("exitCMD".equals(e.getActionCommand())){
            exitCommand();
        }
    }
}