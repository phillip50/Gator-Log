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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        
        Dimension size = new Dimension((int)(width/8), (int)(height/10));
        Dimension labelSize = new Dimension((int)(width/2), (int)(height/10));
        
        Font font1 = new Font("Arial", Font.PLAIN, 40);
        
        Panel panel = new Panel(new FlowLayout());
        
        label = new JLabel("Warning! " + message);
        label.setPreferredSize(labelSize);
        label.setFont(font1);
        panel.add(label);
        
        button = new JButton("Back");
        button.addActionListener(this);
        button.setPreferredSize(size);
        button.setFont(font1);
        
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
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