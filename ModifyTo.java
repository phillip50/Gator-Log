package test;

import javax.swing.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.*;
 
public class ModifyTo extends JPanel implements ActionListener
{
    protected JButton button;
    protected JButton button2;
    protected static String input;
    protected static boolean isDone;
    protected JTextField text;
    protected JLabel label;
    ArrayList<JButton> buttons;
    ArrayList<JLabel> labels;
 
    public ModifyTo(String[] toCages, String[] toRanges, int[] capacities)
    {
        buttons = new ArrayList<JButton>();
        labels = new ArrayList<JLabel>();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        
        Dimension size = new Dimension((int)(width/8), (int)(height/10));
        Dimension labelSize = new Dimension((int)(width/3), (int)(height/10));
        
        Font font1 = new Font("Arial", Font.PLAIN, 40);
        
        Box box = Box.createVerticalBox();
        
        Panel panel;
        Panel bottomPanel = new Panel(new FlowLayout());
        
        for (int i = 0; i < toCages.length; i++)
        {
            if (toCages[i] != null)
            {
                panel = new Panel(new FlowLayout());
                
                label = new JLabel("Cage " + toCages[i] + ": " + toRanges[i] + ", Capacity: " + capacities[i]);
                label.setPreferredSize(labelSize);
                label.setFont(font1);
                panel.add(label);
                labels.add(label);
                
                button = new JButton("Remove");
                button.addActionListener(this);
                button.setActionCommand("" + i);
                button.setPreferredSize(size);
                button.setFont(font1);
                panel.add(button);
                buttons.add(button);
                
                box.add(panel);
            }
        }
        
        button2 = new JButton("Cancel");
        button2.addActionListener(this);
        button2.setActionCommand("cancel");
        button2.setPreferredSize(size);
        button2.setFont(font1);
        
        bottomPanel.add(button2);
        
        setLayout(new BorderLayout());
        add(box, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        
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
 
    public static String createAndShowGUI(String[] toCages, String[] toRanges, int[] capacities)
    {
        JFrame frame = new JFrame("Cage Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        ModifyTo newContentPane = new ModifyTo(toCages, toRanges, capacities);
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