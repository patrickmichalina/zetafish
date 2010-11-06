package ZetaFish.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ZetaFish.Interfaces.ICardButtonPush;
import ZetaFish.Interfaces.IStatusListener;
import ZetaFish.Interfaces.ITurnListener;
import ZetaFish.NetworkObjects.ZFCard;

public class CardSelectorPane extends JPanel implements ActionListener
{
 	private final String REQ_1_ACTION = "request_1_from_player";
    private final String REQ_2_ACTION = "request_2_from_player";
    private final String REQ_3_ACTION = "request_3_from_player";
    private final String REQ_4_ACTION = "request_4_from_player";
    private final String REQ_5_ACTION = "request_5_from_player";
    private final String REQ_6_ACTION = "request_6_from_player";
    private final String REQ_7_ACTION = "request_7_from_player";
    private final String REQ_8_ACTION = "request_8_from_player";
    private final String REQ_9_ACTION = "request_9_from_player";
    private final String REQ_10_ACTION = "request_10_from_player";
    private final String REQ_JACK_ACTION = "request_jack_from_player";
    private final String REQ_QUEEN_ACTION = "request_queen_from_player";
    private final String REQ_KING_ACTION = "request_king_from_player";
	    
	private JButton      btn1              = new JButton("A");
	private JButton      btn2              = new JButton("2");
	private JButton      btn3              = new JButton("3");
	private JButton      btn4              = new JButton("4");
	private JButton      btn5              = new JButton("5");
	private JButton      btn6              = new JButton("6");
	private JButton      btn7              = new JButton("7");
	private JButton      btn8              = new JButton("8");
	private JButton      btn9              = new JButton("9");
	private JButton      btn10             = new JButton("10");
	private JButton      btnjack           = new JButton("J");
	private JButton      btnqueen          = new JButton("Q");
	private JButton      btnking           = new JButton("K");
	    
	private JLabel       lblMyTurn         = new JLabel("My Turn!");
	
	private Dimension    dimButtonSize     = new Dimension(50,35);
	
	private transient Vector CardPushListeners;
	
	public CardSelectorPane()
	{
		Color DefaultForeColor = Color.WHITE;
		
		this.add(lblMyTurn);
		
		this.add(btn1);
		this.add(btn2);
		this.add(btn3);
		this.add(btn4);
		this.add(btn5);
		this.add(btn6);
		this.add(btn7);
		this.add(btn8);
		this.add(btn9);
		this.add(btn10);
		this.add(btnjack);
		this.add(btnqueen);
		this.add(btnking);      
		
		this.btn1.setPreferredSize(    dimButtonSize);
		this.btn2.setPreferredSize(    dimButtonSize);
		this.btn3.setPreferredSize(    dimButtonSize);
		this.btn4.setPreferredSize(    dimButtonSize);
		this.btn5.setPreferredSize(    dimButtonSize);
		this.btn6.setPreferredSize(    dimButtonSize);
		this.btn7.setPreferredSize(    dimButtonSize);
		this.btn8.setPreferredSize(    dimButtonSize);
		this.btn9.setPreferredSize(    dimButtonSize);
		this.btn10.setPreferredSize(   dimButtonSize);
		this.btnjack.setPreferredSize( dimButtonSize);
		this.btnqueen.setPreferredSize(dimButtonSize);
		this.btnking.setPreferredSize( dimButtonSize);
        
        this.lblMyTurn.setForeground(DefaultForeColor);
        this.lblMyTurn.setVisible(false);
        
        SetAllEnabledState(false);
        
        setButtonListeners();
	}
	
	private void setButtonListeners() 
	{
		this.btn1.setActionCommand(REQ_1_ACTION);
        this.btn1.addActionListener(this);

        this.btn2.setActionCommand(REQ_2_ACTION);
        this.btn2.addActionListener(this);

        this.btn3.setActionCommand(REQ_3_ACTION);
        this.btn3.addActionListener(this);

        this.btn4.setActionCommand(REQ_4_ACTION);
        this.btn4.addActionListener(this);

        this.btn5.setActionCommand(REQ_5_ACTION);
        this.btn5.addActionListener(this);

        this.btn6.setActionCommand(REQ_6_ACTION);
        this.btn6.addActionListener(this);

        this.btn7.setActionCommand(REQ_7_ACTION);
        this.btn7.addActionListener(this);

        this.btn8.setActionCommand(REQ_8_ACTION);
        this.btn8.addActionListener(this);

        this.btn9.setActionCommand(REQ_9_ACTION);
        this.btn9.addActionListener(this);

        this.btn10.setActionCommand(REQ_10_ACTION);
        this.btn10.addActionListener(this);

        this.btnjack.setActionCommand(REQ_JACK_ACTION);
        this.btnjack.addActionListener(this);

        this.btnqueen.setActionCommand(REQ_QUEEN_ACTION);
        this.btnqueen.addActionListener(this);

        this.btnking.setActionCommand(REQ_KING_ACTION);
        this.btnking.addActionListener(this);			
	}
	
		
	public void SetAllEnabledState(boolean isEnabled)
	{
		btn1.setEnabled(isEnabled);
		btn2.setEnabled(isEnabled);
		btn3.setEnabled(isEnabled);
		btn4.setEnabled(isEnabled);
		btn5.setEnabled(isEnabled);
		btn6.setEnabled(isEnabled);
		btn7.setEnabled(isEnabled);
		btn8.setEnabled(isEnabled);
		btn9.setEnabled(isEnabled);
		btn10.setEnabled(isEnabled);
		btnjack.setEnabled(isEnabled);
		btnqueen.setEnabled(isEnabled);
		btnking.setEnabled(isEnabled);
	}	
	
	public void ShowMyTurn(boolean isShown)
	{
		lblMyTurn.setVisible(isShown);
	}
	
	public void EnableCardRequestChoiceByHand(ZFCard[] hand)
	{
		List<Integer> handvals = new ArrayList<Integer>();
		for(ZFCard card : hand)
		{
			handvals.add(card.getValue());
		}

		btn1.setEnabled(handvals.contains(1));
		btn2.setEnabled(handvals.contains(2));
		btn3.setEnabled(handvals.contains(3));
		btn4.setEnabled(handvals.contains(4));
		btn5.setEnabled(handvals.contains(5));
		btn6.setEnabled(handvals.contains(6));
		btn7.setEnabled(handvals.contains(7));
		btn8.setEnabled(handvals.contains(8));
		btn9.setEnabled(handvals.contains(9));
		btn10.setEnabled(handvals.contains(10));
		btnjack.setEnabled(handvals.contains(11));
		btnqueen.setEnabled(handvals.contains(12));
		btnking.setEnabled(handvals.contains(13));
	}
	
	/**
	 * Add a subscriber to push events
	 * @param listener
	 */
	public void addCardButtonPushListener(ICardButtonPush listener)
	{
		if (CardPushListeners == null)
			CardPushListeners = new Vector();
		CardPushListeners.addElement(listener);	
	}
	
	private void NotifyCardPushListeners(int cardValue)
	{
		// Notify all turn event listeners
		Vector targets;
	    synchronized (this) {
	    	targets = (Vector) CardPushListeners.clone();
	    }
	    Enumeration e = targets.elements();
	    while (e.hasMoreElements()) 
	    {
	    	ICardButtonPush l = (ICardButtonPush) e.nextElement();
	        l.OnCardButtonSelect(cardValue);
	    }
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		String action = ae.getActionCommand();
		
	    if(action.equals(REQ_1_ACTION))
        {
	    	NotifyCardPushListeners(1);
        }
        else if(action.equals(REQ_2_ACTION))
        {
        	NotifyCardPushListeners(2);
        }
        else if(action.equals(REQ_3_ACTION))
        {
        	NotifyCardPushListeners(3);
        }
        else if(action.equals(REQ_4_ACTION))
        {
        	NotifyCardPushListeners(4);
        }
        else if(action.equals(REQ_5_ACTION))
        {
        	NotifyCardPushListeners(5);
        }
        else if(action.equals(REQ_6_ACTION))
        {
        	NotifyCardPushListeners(6);
        }
        else if(action.equals(REQ_7_ACTION))
        {
        	NotifyCardPushListeners(7);
        }
        else if(action.equals(REQ_8_ACTION))
        {
        	NotifyCardPushListeners(8);
        }
        else if(action.equals(REQ_9_ACTION))
        {
        	NotifyCardPushListeners(9);
        }
        else if(action.equals(REQ_10_ACTION))
        {
        	NotifyCardPushListeners(10);
        }
        else if(action.equals(REQ_JACK_ACTION))
        {
        	NotifyCardPushListeners(11);
        }
        else if(action.equals(REQ_QUEEN_ACTION))
        {
        	NotifyCardPushListeners(12);
        }
        else if(action.equals(REQ_KING_ACTION))
        {
        	NotifyCardPushListeners(13);
        }
	}

}
