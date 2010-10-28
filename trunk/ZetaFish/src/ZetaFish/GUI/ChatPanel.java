package ZetaFish.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ZetaFish.Interfaces.*;

/**
 *
 * @author Patrick
 */
public class ChatPanel extends Panel implements Runnable, IChatListener, ActionListener {
    private BoxLayout  chatLayout;
    private Dimension  dim;
    private JTextArea  outputText;
    private JTextField inputTest;
    private INetworkManager nm;
    
    private JButton sendButton;
    private final String SEND_ACTION = "send"; 

    ChatPanel(INetworkManager nm) {
        super(600,130);
        
        this.nm = nm;

        sendButton = new JButton("Send");
        sendButton.setActionCommand(SEND_ACTION);
        sendButton.addActionListener(this);

        outputText = new JTextArea(6,2);
        inputTest  = new JTextField();

        outputText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        outputText.setLineWrap(true);
        inputTest.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        this.setLayout(new BorderLayout(3,3));
        this.add(new JScrollPane(outputText), BorderLayout.PAGE_START);
        this.add(inputTest, BorderLayout.CENTER);
        this.add(sendButton, BorderLayout.LINE_END);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }



    public void run() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void actionPerformed(ActionEvent e) 
    {
    	if(e.getActionCommand() == SEND_ACTION)
        {
    		try
    		{
    			this.nm.sendMessage(inputTest.getText());
    			inputTest.setText("");
    		}
    		catch(Exception err)
    		{    			
    			HandleException(err);
    		}
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


	@Override
	public void OnNewMessage(String from, String msg) {
		outputText.setText(outputText.getText() + from + ": " + msg + "\n");		
	}

}
