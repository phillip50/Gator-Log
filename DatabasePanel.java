package test;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
 
public class DatabasePanel extends JPanel implements ActionListener
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
    protected JButton button10;
    protected JButton button11;
    protected JButton button12;
    protected JButton button13;
    protected JButton button14;
    protected JButton button15;
    protected JButton button16;
    protected JButton button17;
    protected JButton button18;
    protected JButton button19;
    protected JButton button20;
    protected JButton button21;
    protected JButton button22;
    protected JButton button23;
    protected JButton button24;
    protected JButton button25;
    protected JButton button26;
    protected JButton button27;
    protected JButton button28;
    protected JButton button29;
    protected JButton button30;
    protected JButton button31;
    protected JButton button32;
    protected JButton button33;
    protected JButton button34;
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
        
        button1 = new JButton("15");
        button1.addActionListener(this);
        button1.setActionCommand("15");
        button1.setPreferredSize(size);
        button1.setFont(font1);
        
        button2 = new JButton("16");
        button2.addActionListener(this);
        button2.setActionCommand("16");
        button2.setPreferredSize(size);
        button2.setFont(font1);
        
        button3 = new JButton("17");
        button3.addActionListener(this);
        button3.setActionCommand("17");
        button3.setPreferredSize(size);
        button3.setFont(font1);
        
        button4 = new JButton("18");
        button4.addActionListener(this);
        button4.setActionCommand("18");
        button4.setPreferredSize(size);
        button4.setFont(font1);
        
        button5 = new JButton("19");
        button5.addActionListener(this);
        button5.setActionCommand("19");
        button5.setPreferredSize(size);
        button5.setFont(font1);
        
        button6 = new JButton("20");
        button6.addActionListener(this);
        button6.setActionCommand("20");
        button6.setPreferredSize(size);
        button6.setFont(font1);
        
        button7 = new JButton("21");
        button7.addActionListener(this);
        button7.setActionCommand("21");
        button7.setPreferredSize(size);
        button7.setFont(font1);
        
        button8 = new JButton("22");
        button8.addActionListener(this);
        button8.setActionCommand("22");
        button8.setPreferredSize(size);
        button8.setFont(font1);
        
        button9 = new JButton("23");
        button9.addActionListener(this);
        button9.setActionCommand("23");
        button9.setPreferredSize(size);
        button9.setFont(font1);
        
        button10 = new JButton("24");
        button10.addActionListener(this);
        button10.setActionCommand("24");
        button10.setPreferredSize(size);
        button10.setFont(font1);
        
        button11 = new JButton("25");
        button11.addActionListener(this);
        button11.setActionCommand("25");
        button11.setPreferredSize(size);
        button11.setFont(font1);
        
        button12 = new JButton("26");
        button12.addActionListener(this);
        button12.setActionCommand("26");
        button12.setPreferredSize(size);
        button12.setFont(font1);
        
        button13 = new JButton("27");
        button13.addActionListener(this);
        button13.setActionCommand("27");
        button13.setPreferredSize(size);
        button13.setFont(font1);
        
        button14 = new JButton("28");
        button14.addActionListener(this);
        button14.setActionCommand("28");
        button14.setPreferredSize(size);
        button14.setFont(font1);
        
        button15 = new JButton("29");
        button15.addActionListener(this);
        button15.setActionCommand("29");
        button15.setPreferredSize(size);
        button15.setFont(font1);
        
        button16 = new JButton("30");
        button16.addActionListener(this);
        button16.setActionCommand("30");
        button16.setPreferredSize(size);
        button16.setFont(font1);
        
        button17 = new JButton("31");
        button17.addActionListener(this);
        button17.setActionCommand("31");
        button17.setPreferredSize(size);
        button17.setFont(font1);
        
        button18 = new JButton("32");
        button18.addActionListener(this);
        button18.setActionCommand("32");
        button18.setPreferredSize(size);
        button18.setFont(font1);
        
        button19 = new JButton("33");
        button19.addActionListener(this);
        button19.setActionCommand("33");
        button19.setPreferredSize(size);
        button19.setFont(font1);
        
        button20 = new JButton("34");
        button20.addActionListener(this);
        button20.setActionCommand("34");
        button20.setPreferredSize(size);
        button20.setFont(font1);
        
        button21 = new JButton("35");
        button21.addActionListener(this);
        button21.setActionCommand("35");
        button21.setPreferredSize(size);
        button21.setFont(font1);
        
        button22 = new JButton("36");
        button22.addActionListener(this);
        button22.setActionCommand("36");
        button22.setPreferredSize(size);
        button22.setFont(font1);
        
        button23 = new JButton("37");
        button23.addActionListener(this);
        button23.setActionCommand("37");
        button23.setPreferredSize(size);
        button23.setFont(font1);
        
        button24 = new JButton("38");
        button24.addActionListener(this);
        button24.setActionCommand("38");
        button24.setPreferredSize(size);
        button24.setFont(font1);
        
        button25 = new JButton("39");
        button25.addActionListener(this);
        button25.setActionCommand("39");
        button25.setPreferredSize(size);
        button25.setFont(font1);
        
        button26 = new JButton("40");
        button26.addActionListener(this);
        button26.setActionCommand("40");
        button26.setPreferredSize(size);
        button26.setFont(font1);
        
        button27 = new JButton("41");
        button27.addActionListener(this);
        button27.setActionCommand("41");
        button27.setPreferredSize(size);
        button27.setFont(font1);
        
        button28 = new JButton("42");
        button28.addActionListener(this);
        button28.setActionCommand("42");
        button28.setPreferredSize(size);
        button28.setFont(font1);
        
        button29 = new JButton("43");
        button29.addActionListener(this);
        button29.setActionCommand("43");
        button29.setPreferredSize(size);
        button29.setFont(font1);
        
        button30 = new JButton("44");
        button30.addActionListener(this);
        button30.setActionCommand("44");
        button30.setPreferredSize(size);
        button30.setFont(font1);
        
        button31 = new JButton("45");
        button31.addActionListener(this);
        button31.setActionCommand("45");
        button31.setPreferredSize(size);
        button31.setFont(font1);
        
        button32 = new JButton("46");
        button32.addActionListener(this);
        button32.setActionCommand("46");
        button32.setPreferredSize(size);
        button32.setFont(font1);
        
        Panel panel1 = new Panel(new FlowLayout());      
        panel1.add(button1);
        panel1.add(button2);
        panel1.add(button3);
        panel1.add(button4);
        panel1.add(button5);
        panel1.add(button6);
        panel1.add(button7);
        panel1.add(button8);
        panel1.add(button9);
        panel1.add(button10);
        panel1.add(button11);
        panel1.add(button12);
        panel1.add(button13);
        panel1.add(button14);
        panel1.add(button15);
        panel1.add(button16);
        panel1.add(button17);
        panel1.add(button18);
        panel1.add(button19);
        panel1.add(button20);
        panel1.add(button21);
        panel1.add(button22);
        panel1.add(button23);
        panel1.add(button24);
        panel1.add(button25);
        panel1.add(button26);
        panel1.add(button27);
        panel1.add(button28);
        panel1.add(button29);
        panel1.add(button30);
        panel1.add(button31);
        panel1.add(button32);
        
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
        
        button33 = new JButton("Cancel");
        button33.addActionListener(this);
        button33.setActionCommand("cancel");
        button33.setPreferredSize(size);
        button33.setFont(font1);
        
        button34 = new JButton("Confirm");
        button34.addActionListener(this);
        button34.setActionCommand("confirm");
        button34.setPreferredSize(size);
        button34.setFont(font1);
        
        Panel panel3 = new Panel(new FlowLayout());
        panel3.add(label3);
        panel3.add(button33);
        panel3.add(button34);
        
        Panel panel4 = new Panel();
        panel4.setLayout(new BorderLayout());
        panel4.add(panel2, BorderLayout.NORTH);
        panel4.add(panel3, BorderLayout.SOUTH);
        
        setLayout(new BorderLayout());
        add(panelQuery1, BorderLayout.NORTH);
        add(panel1, BorderLayout.CENTER);
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