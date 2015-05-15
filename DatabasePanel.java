package test;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class DatabasePanel extends JPanel implements ActionListener
{
    protected JButton button;
    protected JButton button2;
    protected JButton button3;
    protected JButton button4;
    protected static String input;
    protected static String input2;
    protected static boolean isDone;
    protected JTextField text;
    protected JLabel label;
    protected JLabel label2;
    protected JLabel label3;
    protected JComboBox scrollDown;
    protected JComboBox scrollDown2;
 
    public DatabasePanel()
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
        
        String[] years = {"2012", "2013", "2014", "2015"};
        
        label2 = new JLabel("Birth year?");
        
        scrollDown2 = new JComboBox(years);
        scrollDown2.setEditable(false);
        
        button2 = new JButton("Enter");
        button2.addActionListener(this);
        button2.setActionCommand("enter2");
        
        Panel panel2 = new Panel(new FlowLayout());
        panel2.add(label2);
        panel2.add(scrollDown2);
        panel2.add(button2);
        
        label3 = new JLabel("Database Name: ");
        
        button3 = new JButton("Cancel");
        button3.addActionListener(this);
        button3.setActionCommand("cancel");
        
        button4 = new JButton("Confirm");
        button4.addActionListener(this);
        button4.setActionCommand("confirm");
        
        Panel panel3 = new Panel(new FlowLayout());
        panel3.add(button3);
        panel3.add(button4);
        
        setLayout(new BorderLayout());
        add(panel1, BorderLayout.NORTH);
        add(panel2, BorderLayout.CENTER);
        add(panel3, BorderLayout.SOUTH);
        
        isDone = false;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("enter"))
        {
            input = scrollDown.getSelectedItem().toString();
        }
        else if (e.getActionCommand().equals("enter2"))
        {
            input2 = scrollDown2.getSelectedItem().toString();
        }
        else if (e.getActionCommand().equals("confirm"))
        {
            isDone = true;
        }
        else
        {
            input = "";
            input2 = "";
            isDone = true;
        }
    }
 
    public static String createAndShowGUI()
    {
        JFrame frame = new JFrame("Cage Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        DatabasePanel newContentPane = new DatabasePanel();
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
        
        return input + "_" + input2;      
    }
 
    public static void main(String[] args)
    {
     
    }
}