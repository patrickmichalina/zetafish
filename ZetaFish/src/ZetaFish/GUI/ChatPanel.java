package ZetaFish.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Patrick
 */
public class ChatPanel extends Panel implements Runnable {

    private BoxLayout  chatLayout;
    private Dimension  dim;
    private JTextArea  outputText;
    private JTextField inputTest;

    ChatPanel() {
        super(600,130);

        JButton test = new JButton("Send");

        outputText = new JTextArea(6,2);
        inputTest  = new JTextField();

        outputText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        outputText.setLineWrap(true);
        inputTest.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        this.setLayout(new BorderLayout(3,3));
        this.add(new JScrollPane(outputText), BorderLayout.PAGE_START);
        this.add(inputTest, BorderLayout.CENTER);
        this.add(test, BorderLayout.LINE_END);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }



    public void run() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

}
