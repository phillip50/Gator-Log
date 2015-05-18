package test;

import javax.swing.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;


public class StartWindow extends JPanel implements ActionListener
{
    protected JButton button;
    protected JButton button2;
    protected JButton button3;
    protected JButton button4;
    protected JButton button5;
    protected static String input;
    protected static boolean isDone;
 
    public StartWindow(boolean hasDatabase, boolean hasFrom)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        
        Dimension buttonSize = new Dimension((int)(width/6), (int)(height/4));
        
        JPanel container = new JPanel();
        
        JButton button = new JButton("Start");
        button.addActionListener(this);
        button.setActionCommand("add");
        button.setPreferredSize(buttonSize);
        container.add(button);

        if (!hasDatabase || !hasFrom)
        {
            button.setEnabled(false);
        }
        
        JButton button2 = new JButton("Set Up Database");
        button2.addActionListener(this);
        button2.setActionCommand("new");
        button2.setPreferredSize(buttonSize);
        container.add(button2);
        
        JButton button3 = new JButton("Add To Cage");
        button3.addActionListener(this);
        button3.setActionCommand("to");
        button3.setPreferredSize(buttonSize);
        container.add(button3);
        
        JButton button4 = new JButton("Remove To Cage");
        button4.addActionListener(this);
        button4.setActionCommand("modify");
        button4.setPreferredSize(buttonSize);
        container.add(button4);
 
        JButton button5 = new JButton("Quit");
        button5.addActionListener(this);
        button5.setActionCommand("quit");
        button5.setPreferredSize(buttonSize);
        container.add(button5);

        add(container);
        
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
        
        frame.getContentPane().setPreferredSize( Toolkit.getDefaultToolkit().getScreenSize());
        
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