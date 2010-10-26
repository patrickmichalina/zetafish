package ZetaFish.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Patrick
 */
public class ZetaFishGUI extends JFrame implements ActionListener
{
    private BackgroundPanel backgroundPanel;
    private Panel           logoPanel;
    private MenuPanel       menuPanel;

    private ChatPanel   chatPanel;
    private InfoPanel       infoPanel;
    private GamePanel       gamePanel;

    private FlowLayout      windowLayout;

    /**
     *
     */
    public ZetaFishGUI()
    {
        super("ZetaFish");

        //start the game with the inital view
        initStartConditions();
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

    private void exitCommand()
    {
        dispose();
        System.exit(0);
    }

    private void playCommand()
    {
        //reset to blank window
        backgroundPanel.removeAll();
        backgroundPanel.repaint();

        //setup game screen
        backgroundPanel.setLayout(new BorderLayout());

        chatPanel = new ChatPanel();
        infoPanel = new InfoPanel();
        gamePanel = new GamePanel();
        
        backgroundPanel.add(infoPanel, BorderLayout.PAGE_START);
        backgroundPanel.add(gamePanel, BorderLayout.CENTER);
        backgroundPanel.add(chatPanel, BorderLayout.PAGE_END);

        backgroundPanel.repaint();
        backgroundPanel.validate();
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
        if("playCMD".equals(e.getActionCommand()))
        {
            playCommand();
        }

        if("instCMD".equals(e.getActionCommand()))
        {
            instructionCommand();
        }

        if("exitCMD".equals(e.getActionCommand()))
        {
            exitCommand();
        }
    }
}