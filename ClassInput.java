package test;

import javax.swing.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
 
public class ClassInput extends JPanel implements ActionListener
{
    
    protected JButton button1;
    protected JButton button2;
    protected JButton button3;
    protected JButton button4;
    protected JButton button5;
    protected JButton button6;
    protected JButton button7;
    protected JButton button8;
    protected JButton button9;
    protected static String input;
    protected static boolean isDone;
    protected JTextField text;
 
    public ClassInput()
    {       
        text = new JTextField("Select Class:", 10);
        text.setEditable(false);

        button1 = new JButton("HATCH");
        button1.setMnemonic(KeyEvent.VK_D);
        button1.addActionListener(this);
        button1.setActionCommand("HATCH");
        
        button2 = new JButton("15-18");
        button2.setMnemonic(KeyEvent.VK_D);
        button2.addActionListener(this);
        button2.setActionCommand("15-18");
        
        button3 = new JButton("19-23");
        button3.setMnemonic(KeyEvent.VK_D);
        button3.addActionListener(this);
        button3.setActionCommand("19-23");
        
        button4 = new JButton("24-28");
        button4.setMnemonic(KeyEvent.VK_D);
        button4.addActionListener(this);
        button4.setActionCommand("24-28");
        
        button5 = new JButton("29-33");
        button5.setMnemonic(KeyEvent.VK_D);
        button5.addActionListener(this);
        button5.setActionCommand("29-33");
        
        button6 = new JButton("34-35");
        button6.setMnemonic(KeyEvent.VK_D);
        button6.addActionListener(this);
        button6.setActionCommand("34-35");
        
        button7 = new JButton("36-37");
        button7.setMnemonic(KeyEvent.VK_D);
        button7.addActionListener(this);
        button7.setActionCommand("36-37");
        
        button8 = new JButton("38-40");
        button8.setMnemonic(KeyEvent.VK_D);
        button8.addActionListener(this);
        button8.setActionCommand("38-40");
        
        button9 = new JButton("41+");
        button9.setMnemonic(KeyEvent.VK_D);
        button9.addActionListener(this);
        button9.setActionCommand("41+");
        
        add(text);
        add(button1);
        add(button2);
        add(button3);
        add(button4);
        add(button5);
        add(button6);
        add(button7);
        add(button8);
        add(button9);
        
        isDone = false;
    }
 
    public void actionPerformed(ActionEvent e)
    {
        if ("HATCH".equals(e.getActionCommand()))
        {
            input = "HATCH";
        }
        else if ("15-18".equals(e.getActionCommand()))
        {
            input = "15-18";
        }
        else if ("19-23".equals(e.getActionCommand()))
        {
            input = "19-23";
        }
        else if ("24-28".equals(e.getActionCommand()))
        {
            input = "24-28";
        }
        else if ("29-33".equals(e.getActionCommand()))
        {
            input = "29-33";
        }
        else if ("34-35".equals(e.getActionCommand()))
        {
            input = "34-35";
        }
        else if ("36-37".equals(e.getActionCommand()))
        {
            input = "36-38";
        }
        else if ("38-40".equals(e.getActionCommand()))
        {
            input = "38-40";
        }
        else
        {
            input = "41+";  
        }
        isDone = true;
    }
 
    public static String createAndShowGUI()
    {
        JFrame frame = new JFrame("Cage Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        ClassInput newContentPane = new ClassInput();
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