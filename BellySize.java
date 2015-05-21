package test;

import javax.swing.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
 
public class BellySize extends JPanel implements ActionListener
{
    protected JButton button;
    protected JButton button1;
    protected static String input;
    protected static boolean isDone;
    protected JTextField text;
 
    public BellySize()
    {       
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        
        Dimension size = new Dimension((int)(width/7), (int)(height/8));
        
        Font font1 = new Font("Arial", Font.PLAIN, 40);

        Panel buttonDisplay1 = new Panel(new FlowLayout());      

        for (int i = 15; i < 47; i++)
        {
            button = new JButton("" + i);
            button.addActionListener(this);
            button.setActionCommand("" + i);
            button.setPreferredSize(size);
            button.setFont(font1);
            buttonDisplay1.add(button);
        }
        
        button1 = new JButton("Done");
        button1.addActionListener(this);
        button1.setActionCommand("done");
        button1.setPreferredSize(size);
        button1.setFont(font1);
        
        Panel buttonDisplay2 = new Panel(new FlowLayout());
        buttonDisplay2.add(button1);
        
        setLayout(new BorderLayout());
        add(buttonDisplay1, BorderLayout.CENTER);
        add(buttonDisplay2, BorderLayout.SOUTH);
        
        isDone = false;
    }
 
    public void actionPerformed(ActionEvent e)
    {
        input = e.getActionCommand();
        isDone = true;
    }
 
    public static String createAndShowGUI()
    {
        JFrame frame = new JFrame("Belly Size");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        BellySize newContentPane = new BellySize();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
 
        frame.getContentPane().setPreferredSize( Toolkit.getDefaultToolkit().getScreenSize());
        
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