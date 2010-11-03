package ZetaFish.GUI;

import java.awt.GridLayout;
import javax.swing.*;
//import javax.swing.JFrame;

//public class GameStartDialog extends JFrame 
public class GameStartDialog extends JDialog
{
	private JTextField server = new JTextField("localhost");
	private JTextField player = new JTextField("player");
	
	public GameStartDialog()
	{
		this.setLayout(new GridLayout(2,2));
		this.add(new JLabel("Server Name:"));
		this.add(server);		
		this.add(new JLabel("Player Name:"));
		this.add(player);

	}

}
