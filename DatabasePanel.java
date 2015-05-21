package test;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
 
public class DatabasePanel extends JPanel implements ActionListener
{
    protected JButton button;
    protected JButton button1;
    protected JButton button2;
    protected static String input;
    protected static String input2;
    protected static String input3;
    protected static String input4;
    protected static int count;
    protected static boolean isDone;
    protected static boolean hasRange;
    protected JTextField text;
    protected JLabel label;
    protected JLabel label2;
    protected JLabel label3;
    protected JLabel label4;
    protected JComboBox scrollDown;
    protected JComboBox scrollDown2;
 
    public DatabasePanel()
    {
        input = "";
        input2 = "";
        input3 = "";
        input4 = "";
        count = 0;
        
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
        Dimension labelSize = new Dimension((int)(width/3), (int)(height/10));
        
        Font font1 = new Font("Arial", Font.PLAIN, 40);
        Font font2 = new Font("Arial", Font.PLAIN, 25);      
        
        Panel panelQuery1 = new Panel(new FlowLayout());
        label4 = new JLabel("Select Belly Range");
        label4.setFont(font2);
        panelQuery1.add(label4);
        
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
        
        label = new JLabel("From?");
        label.setPreferredSize(size);
        label.setFont(font1);
        
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
        
        String[] years = {"2012", "2013", "2014", "2015"};
        
        label2 = new JLabel("Birth year?");
        label2.setPreferredSize(size);
        label2.setFont(font1);
        
        scrollDown2 = new JComboBox(years);
        scrollDown2.setEditable(false);
        scrollDown2.setPreferredSize(size);
        scrollDown2.setFont(font1);
        scrollDown2.addPopupMenuListener(new PopupMenuListener()
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
                    Dimension scrollBarDim = new Dimension(100, scrollBar.getPreferredSize().height);
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
           
        
        Panel panel2 = new Panel(new FlowLayout());
        panel2.add(label);
        panel2.add(scrollDown);
        panel2.add(label2);
        panel2.add(scrollDown2);
        
        label3 = new JLabel("Cage: " + scrollDown.getSelectedItem().toString() + ", Year: " + scrollDown2.getSelectedItem().toString());
        label3.setPreferredSize(labelSize);
        label3.setFont(font1);
        
        button1 = new JButton("Cancel");
        button1.addActionListener(this);
        button1.setActionCommand("cancel");
        button1.setPreferredSize(size);
        button1.setFont(font1);
        
        button2 = new JButton("Confirm");
        button2.addActionListener(this);
        button2.setActionCommand("confirm");
        button2.setPreferredSize(size);
        button2.setFont(font1);
        
        Panel panel3 = new Panel(new FlowLayout());
        panel3.add(label3);
        panel3.add(button1);
        panel3.add(button2);
        
        Panel panel4 = new Panel();
        panel4.setLayout(new BorderLayout());
        panel4.add(panel2, BorderLayout.NORTH);
        panel4.add(panel3, BorderLayout.SOUTH);
        
        setLayout(new BorderLayout());
        add(panelQuery1, BorderLayout.NORTH);
        add(buttonDisplay1, BorderLayout.CENTER);
        add(panel4, BorderLayout.SOUTH);
        
        isDone = false;
    }
    
    public void popupMenuWillBecomeVisible(PopupMenuEvent e)
      {
         JComboBox comboSbBox = (JComboBox) e.getSource();
         Object popup = comboSbBox.getUI().getAccessibleChild(comboSbBox , 0);
         Component c = ((Container) popup).getComponent(0);
         if (c instanceof JScrollPane)
         {
            JScrollPane spane = (JScrollPane) c;
            JScrollBar scrollBar = spane .getVerticalScrollBar();
            Dimension scrollBarDim = new Dimension(20, scrollBar
                  .getPreferredSize().height);
            scrollBar.setPreferredSize(scrollBarDim);
         }
      }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("confirm"))
        {
            isDone = true;
            input = scrollDown.getSelectedItem().toString();
            input2 = scrollDown2.getSelectedItem().toString();
        }
        else if (e.getActionCommand().equals("cancel"))
        {
            input = "";
            input2 = "";
            isDone = true;
        }
        else if (count == 0)
        {
            input3 = e.getActionCommand();
            count++;
        }
        else if (count == 1)
        {
            input4 = e.getActionCommand();
            if (Integer.parseInt(input4) >= Integer.parseInt(input3))
            {
                count++;
                hasRange = true;
            }
            else
            {
                count = 0;
                input3 = "";
                input4 = "";
            }
            
        }
        else
        {
            input3 = "";
            input4 = "";
            count = 0;
            hasRange = false;
        }
        if (count == 0)
        {
            label3.setText("Cage: " + scrollDown.getSelectedItem().toString() + ", Year: " + scrollDown2.getSelectedItem().toString());
        }
        else
        {
            label3.setText("Cage: " + scrollDown.getSelectedItem().toString() + ", Year: " + scrollDown2.getSelectedItem().toString() + ", " + input3 + "-" + input4);
        }
    }
 
    public static String createAndShowGUI()
    {
        JFrame frame = new JFrame("Cage Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        DatabasePanel newContentPane = new DatabasePanel();
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
        
        return input + "_" + input2 + "_" + input3 + "-" + input4;      
    }
 
    public static void main(String[] args)
    {
     
    }
}