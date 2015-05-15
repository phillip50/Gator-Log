package test;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class ErrorWindow extends JPanel implements ActionListener
{
    protected JButton button;
    protected static boolean isDone;
    protected JLabel label;
 
    public ErrorWindow(String message)
    {
        label = new JLabel("Warning! " + message);
        
        button = new JButton("Back");
        button.addActionListener(this);
        
        setLayout(new BorderLayout());
        add(label, BorderLayout.NORTH);
        add(button, BorderLayout.SOUTH);
        
        isDone = false;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        isDone = true;
    }
 
    public static void createAndShowGUI(String message)
    {
        JFrame frame = new JFrame("Cage Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        ErrorWindow newContentPane = new ErrorWindow(message);
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
    }
 
    public static void main(String[] args)
    {
     
    }
}