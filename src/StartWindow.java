/*
    Initial window displayed on program start
    Allows the user to specify output databases and add entries to them

    To-Do:
        Add ability to specify databases based on belly size        *check*
*/

package test;

import javax.swing.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

public class StartWindow extends JPanel implements ActionListener
{
    protected JButton button;
    protected JButton button2;
    protected JButton button3;
    protected JButton button4;
    protected JButton button5;
    protected JButton button6;
    protected static String input;
    protected static boolean isDone;
 
    public StartWindow(boolean hasDatabase, boolean hasFrom)
    {
        button = new JButton("Add Entry");
        button.addActionListener(this);
        button.setActionCommand("add");
        
        if (!hasDatabase || !hasFrom)
        {
            button.setEnabled(false);
        }
        
        button2 = new JButton("New Database");
        button2.addActionListener(this);
        button2.setActionCommand("new");
        
        button3 = new JButton("Specify From Cage");
        button3.addActionListener(this);
        button3.setActionCommand("from");
        
        button4 = new JButton("Add To Cage");
        button4.addActionListener(this);
        button4.setActionCommand("to");
        
        button5 = new JButton("Remove To Cage");
        button5.addActionListener(this);
        button5.setActionCommand("modify");
 
        button6 = new JButton("Quit");
        button6.addActionListener(this);
        button6.setActionCommand("quit");
        
        add(button);
        add(button2);
        add(button3);
        add(button4);
        add(button5);
        add(button6);
        
        isDone = false;
    }
        
    public void actionPerformed(ActionEvent e)
    {
        input = e.getActionCommand();
        isDone = true;
    }
 
    public static String createAndShowGUI(boolean hasDatabase, boolean hasFrom)
    {
        JFrame frame = new JFrame("Start");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        StartWindow newContentPane = new StartWindow(hasDatabase, hasFrom);
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        
        ImageIcon img = new ImageIcon("Logo1.jpg");
        frame.setIconImage(img.getImage());
        
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
