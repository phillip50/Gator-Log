package test;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class InputFrom extends JPanel implements ActionListener
{
    protected JButton button;
    protected JButton button2;
    protected static String input;
    protected static boolean isDone;
    protected JTextField text;
    protected JLabel label;
    protected JComboBox scrollDown;
 
    public InputFrom()
    {
        String[] cages = new String[148];
        for (int i = 0; i < 99; i++)
        {
            int j = i + 1;
            cages[i] = "" + j;
        }
        for (int i = 0; i < 16; i++)
        {
            int j = i + 1;
            cages[i + 99] = "" + j + "A";
        }
        for (int i = 0; i < 33; i++)
        {
            int j = i + 1;
            cages[i + 99 + 16] = "" + j + "B";
        }
        
        label = new JLabel("From?");
        
        scrollDown = new JComboBox(cages);
        scrollDown.setEditable(false);

        button = new JButton("Enter");
        button.addActionListener(this);
        button.setActionCommand("enter");
 
        Panel panel1 = new Panel(new FlowLayout());
        panel1.add(label);
        panel1.add(scrollDown);
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
            input = scrollDown.getSelectedItem().toString();
            isDone = true;
        }
        else
        {
            input = "";
            isDone = true;
        }
    }
 
    public static String createAndShowGUI()
    {
        JFrame frame = new JFrame("Cage Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        InputFrom newContentPane = new InputFrom();
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