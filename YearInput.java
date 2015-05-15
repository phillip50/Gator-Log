package test;

import javax.swing.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
 
public class YearInput extends JPanel
                        implements ActionListener {
    protected JButton button1;
    protected JButton button2;
    protected JButton button3;
    protected JButton button4;
    protected static String input;
    protected static boolean isDone;
    protected JTextField text;
    
        //previous 4 years
    private static String year1;
    private static String year2;
    private static String year3;
    private static String year4;
 
    public YearInput()
    {       
        text = new JTextField("Select Birth Year:", 10);
        text.setEditable(false);

        button1 = new JButton(year1);
        button1.setMnemonic(KeyEvent.VK_D);
        button1.addActionListener(this);
        button1.setActionCommand(year1);
        
        button2 = new JButton(year2);
        button2.setMnemonic(KeyEvent.VK_D);
        button2.addActionListener(this);
        button2.setActionCommand(year2);
        
        button3 = new JButton(year3);
        button3.setMnemonic(KeyEvent.VK_D);
        button3.addActionListener(this);
        button3.setActionCommand(year3);
        
        button4 = new JButton(year4);
        button4.setMnemonic(KeyEvent.VK_D);
        button4.addActionListener(this);
        button4.setActionCommand(year4);
        
        add(text);
        add(button1);
        add(button2);
        add(button3);
        add(button4);
        
        isDone = false;
    }
 
        //return the year value based on the button pressed
    public void actionPerformed(ActionEvent e)
    {
        if (year1.equals(e.getActionCommand()))
        {
            input = year1;
        }
        else if (year2.equals(e.getActionCommand()))
        {
            input = year2;
        }
        else if (year3.equals(e.getActionCommand()))
        {
            input = year3;
        }
        else
        {
            input = year4;
        }
        isDone = true;
    }
    
        //set the years attributes
        //must be done before the gui interface can be used
    public static void setYears(String s1, String s2, String s3, String s4)
    {
        year1 = s1;
        year2 = s2;
        year3 = s3;
        year4 = s4;
    }
 
    public static String createAndShowGUI()
    {
        JFrame frame = new JFrame("Cage Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        YearInput newContentPane = new YearInput();
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