package test;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
 
public class InputPanel extends JPanel implements ActionListener
{
    protected JButton button;
    protected JButton button2;
    protected static String input;
    protected static boolean isDone;
    protected JTextField text;
    protected JLabel label;
 
    public InputPanel(String s)
    {
        text = new JTextField(10);
        
        label = new JLabel(s);

        button = new JButton("Enter");
        button.addActionListener(this);
        button.setActionCommand("enter");
 
        Panel panel1 = new Panel(new FlowLayout());
        panel1.add(label);
        panel1.add(text);
        panel1.add(button);
        
        button2 = new JButton("Cancel");
        button2.addActionListener(this);
        button2.setActionCommand("cancel");
        
        setLayout(new BorderLayout());
        add(panel1, BorderLayout.NORTH);
        add(button2, BorderLayout.CENTER);
        
        isDone = false;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("enter"))
        {
            input = text.getText();
            isDone = true;
        }
        else
        {
            input = "";
            isDone = true;
        }
    }
 
    public static String createAndShowGUI(String s)
    {
        JFrame frame = new JFrame("Cage Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        InputPanel newContentPane = new InputPanel(s);
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
 
        
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null); 

        while (isDone == false)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                
            }
        }
        frame.setVisible(false);
        frame.dispose();       
        
        return input;      
    }
 
    public static void main(String[] args)
    {
     
    }
}
