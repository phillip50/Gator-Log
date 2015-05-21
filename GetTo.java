package test;

import javax.swing.*; 
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
 
public class GetTo extends JPanel implements ActionListener
{
    protected JButton button;
    protected JButton button1;
    protected JButton button2;
    protected JButton button3;
    protected JButton button4;
    protected static String input;
    protected static String input2;
    protected static String input3;
    protected static String input4;
    protected static int count;
    protected static boolean isDone;
    protected JTextField text;
    protected JTextField text2;
    protected JTextField text3;
    protected JComboBox scrollDown;
    protected JLabel label;
    protected JLabel label1;
    protected JLabel label2;
    protected JLabel label3;
    protected static boolean hasCage;
    protected static boolean hasRange;
    protected static boolean hasCapacity;
 
    public GetTo()
    {  
        isDone = false;
        count = 0;
        input = "15";
        input2 = "46";
        input3 = "1";
        input4 = "0";
        hasCage = false;
        hasRange = false;
        hasCapacity = false;
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
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        
        Dimension size = new Dimension((int)(width/8), (int)(height/10));
        Dimension size2 = new Dimension((int)(width/6), (int)(height/10));
        Dimension buttonSize = new Dimension((int)(width/17), (int)(height/10));
        Dimension labelSize = new Dimension((int)(width/2), (int)(height/10));
        
        Font font1 = new Font("Arial", Font.PLAIN, 40);
        Font font2 = new Font("Arial", Font.PLAIN, 25); 
        
        Panel panelQuery1 = new Panel(new FlowLayout());
        label = new JLabel("Select Belly Range");
        label.setFont(font2);
        panelQuery1.add(label);
        
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
        
        Panel panelQuery2 = new Panel(new FlowLayout());
        label1 = new JLabel("Cage: " + input3 + ", Belly Range: " + input + "-" + input2 + ", Capacity: " + input4);
        label1.setPreferredSize(labelSize);
        label1.setFont(font1);
        
        button1 = new JButton("Confirm");
        button1.addActionListener(this);
        button1.setActionCommand("done");
        button1.setPreferredSize(size);
        button1.setFont(font1);
        
        button2 = new JButton("Cancel");
        button2.addActionListener(this);
        button2.setActionCommand("quit");
        button2.setPreferredSize(size);
        button2.setFont(font1);
        
        panelQuery2.add(label1);
        panelQuery2.add(button2);
        panelQuery2.add(button1);
        
        Panel panelQuery3 = new Panel(new FlowLayout());
        label2 = new JLabel("Cage:");
        label2.setPreferredSize(size);
        label2.setFont(font1);
        scrollDown = new JComboBox(cages);
        scrollDown.setEditable(false);
        scrollDown.setPreferredSize(size);
        scrollDown.setFont(font1);
        scrollDown.addPopupMenuListener(new PopupMenuListener()
        {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e)
            {
                JComboBox comboBox = (JComboBox) e.getSource();
                Object popup = comboBox.getUI().getAccessibleChild(comboBox, 0);
                Component c = ((Container) popup).getComponent(0);
                if (c instanceof JScrollPane)
                {
                    JScrollPane scrollpane = (JScrollPane) c;
                    JScrollBar scrollBar = scrollpane.getVerticalScrollBar();
                    Dimension scrollBarDim = new Dimension((int)(width / 48), scrollBar.getPreferredSize().height);
                    scrollBar.setPreferredSize(scrollBarDim);
                }
            }
            
            public void popupMenuCanceled(PopupMenuEvent e)
            {
                
            }
            
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
            {
                
            }
        });
        button3 = new JButton("Enter Cage");
        button3.addActionListener(this);
        button3.setActionCommand("cage");
        button3.setPreferredSize(size);
        button3.setFont(font1);
        panelQuery3.add(label2);
        panelQuery3.add(scrollDown);
        panelQuery3.add(button3);
        
        Panel panelQuery4 = new Panel(new FlowLayout());
        label3 = new JLabel("Capacity:");
        label3.setPreferredSize(size);
        label3.setFont(font1);
        text3 = new JTextField(10);
        text3.setPreferredSize(buttonSize);
        text3.setFont(font1);
        button4 = new JButton("Enter Capacity");
        button4.addActionListener(this);
        button4.setActionCommand("capacity");
        button4.setPreferredSize(size2);
        button4.setFont(font1);
        panelQuery4.add(label3);
        panelQuery4.add(text3);
        panelQuery4.add(button4);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(panelQuery3, BorderLayout.NORTH);
        bottomPanel.add(panelQuery4, BorderLayout.CENTER);
        bottomPanel.add(panelQuery2, BorderLayout.SOUTH);
        setLayout(new BorderLayout());
        add(panelQuery1, BorderLayout.NORTH);
        add(buttonDisplay1, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
 
    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        
        if (s.equals("done"))
        {
            isDone = true;
        }
        else if (s.equals("quit"))
        {
            isDone = true;
            input = "";
            input2 = "";
            input3 = "";
            input4 = "";
        }
        else if (s.equals("cage"))
        {
            input3 = scrollDown.getSelectedItem().toString();
            hasCage = true;
        }
        else if (s.equals("capacity"))
        {
            hasCapacity = true;
            input4 = text3.getText();
        }
        else if (count == 0)
        {
            input = s;
            input2 = "";
            count++;
        }
        else if (count == 1)
        {
            input2 = s;
            if (Integer.parseInt(input2) >= Integer.parseInt(input))
            {
                count++;
                hasRange = true;
            }
            else
            {
                count = 0;
                input = "15";
                input2 = "46";
            }
            
        }
        else
        {
            input = "15";
            input2 = "46";
            count = 0;
            hasRange = false;
        }
        label1.setText("Cage: " + input3 + ", Belly Range: " + input + "-" + input2 + ", Capacity: " + input4);
    }
 
    public static String createAndShowGUI()
    {
        JFrame frame = new JFrame("Belly Size");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GetTo newContentPane = new GetTo();
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
        
        return input3 + ":" + input + "-" + input2 + ":" + input4;  
    }
 
    public static void main(String[] args)
    {
     
    }
}