package ZetaFish.GUI;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Patrick
 */
public class MenuPanel extends Panel {
    /**
     *
     */
    public JButton playBtn = new JButton("Play");
    /**
     *
     */
    public JButton instBtn = new JButton("Instructions");
    /**
     *
     */
    public JButton exitBtn = new JButton("Exit");

    /**
     *
     */
    public MenuPanel() {
        super();
        initComponents();
    }

    /**
     * 
     * @param image
     */
    public MenuPanel(ImageIcon image) {
        super(image);
    }

    private void initComponents() {
        this.setLayout(new BoxLayout(this,1));
        this.add(playBtn);
        this.add(instBtn);
        this.add(exitBtn);
        
        playBtn.setActionCommand("playCMD");
        instBtn.setActionCommand("instCMD");
        exitBtn.setActionCommand("exitCMD");
    }
}
