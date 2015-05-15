
package test;

import javax.swing.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
 
public class SelectCage extends JPanel implements ActionListener
{
    protected JButton button;
    protected ArrayList<JButton> buttons;
    protected static String input;
    protected static boolean isDone;
    
    public SelectCage()
    {
        buttons = new ArrayList<JButton>();
        
        Panel panel = new Panel(new FlowLayout());
        /*
        for (int i = 0; i < 100; i++)
        {
            System.out.println(i);
            button = new JButton("" + i);
            button.addActionListener(this);
            button.setActionCommand("" + i);
            buttons.add(button);
            panel.add(button);
        }
        */
        
        button = new JButton("Cancel");
        button.addActionListener(this);
        button.setActionCommand("cancel");
        //buttons.add(button);

        setLayout(new BorderLayout());
        add(button, BorderLayout.NORTH);
        
        isDone = false;
        
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if ("cancel".equals(e.getActionCommand()))
        {
            input = "-1";
        }
        else
        {
            input = e.getActionCommand();
        }
        isDone = true;
    }
    
    public static String createAndShowGUI()
    {
        JFrame frame2 = new JFrame("Cage Panel");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        SelectCage pane = new SelectCage();
        pane.setOpaque(true);
        frame2.setContentPane(pane);
 
        
        frame2.setVisible(true);
        frame2.pack();
        frame2.setLocationRelativeTo(null); 
        while (isDone == false)
        {
            System.out.println(isDone);
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                
            }
        }
        System.out.println("hi");
        frame2.setVisible(false);
        frame2.dispose();       
        
        return input;      
    }
    
}