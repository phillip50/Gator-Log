package test;

import javax.swing.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
 

    //class that creates 2 buttons, M or F, and returns a String value to represent the button pressed
public class GenderInput extends JPanel
                        implements ActionListener {
    protected JButton button1;
    protected JButton button2;
    protected static String input;
    protected static boolean isDone;
    protected JTextField text;
 
    public GenderInput()
    {       
        text = new JTextField("Select Gender:", 10);
        text.setEditable(false);

        button1 = new JButton("M");
        button1.setMnemonic(KeyEvent.VK_D);
        button1.addActionListener(this);
        button1.setActionCommand("M");
        
        button2 = new JButton("F");
        button2.setMnemonic(KeyEvent.VK_D);
        button2.addActionListener(this);
        button2.setActionCommand("F");
        
        add(text);
        add(button1);
        add(button2);
        
        isDone = false;
    }
    
        //return the gender
    public void actionPerformed(ActionEvent e)
    {
        if ("M".equals(e.getActionCommand()))
        {
            input = "M";
        }
        else
        {
            input = "F";
        }
        isDone = true;
    }
 
    public static String createAndShowGUI()
    {
        JFrame frame = new JFrame("Cage Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        GenderInput newContentPane = new GenderInput();
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
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
     
    }
}