package test;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
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
        
        button1 = new JButton("15");
        button1.addActionListener(this);
        button1.setActionCommand("15");
        
        button2 = new JButton("16");
        button2.addActionListener(this);
        button2.setActionCommand("16");
        
        button3 = new JButton("17");
        button3.addActionListener(this);
        button3.setActionCommand("17");
        
        button4 = new JButton("18");
        button4.addActionListener(this);
        button4.setActionCommand("18");
        
        button5 = new JButton("19");
        button5.addActionListener(this);
        button5.setActionCommand("19");
        
        button6 = new JButton("20");
        button6.addActionListener(this);
        button6.setActionCommand("20");
        
        button7 = new JButton("21");
        button7.addActionListener(this);
        button7.setActionCommand("21");
        
        button8 = new JButton("22");
        button8.addActionListener(this);
        button8.setActionCommand("22");
        
        button9 = new JButton("23");
        button9.addActionListener(this);
        button9.setActionCommand("23");
        
        button10 = new JButton("24");
        button10.addActionListener(this);
        button10.setActionCommand("24");
        
        button11 = new JButton("25");
        button11.addActionListener(this);
        button11.setActionCommand("25");
        
        button12 = new JButton("26");
        button12.addActionListener(this);
        button12.setActionCommand("26");
        
        button13 = new JButton("27");
        button13.addActionListener(this);
        button13.setActionCommand("27");
        
        button14 = new JButton("28");
        button14.addActionListener(this);
        button14.setActionCommand("28");
        
        button15 = new JButton("29");
        button15.addActionListener(this);
        button15.setActionCommand("29");
        
        button16 = new JButton("30");
        button16.addActionListener(this);
        button16.setActionCommand("30");
        
        button17 = new JButton("31");
        button17.addActionListener(this);
        button17.setActionCommand("31");
        
        button18 = new JButton("32");
        button18.addActionListener(this);
        button18.setActionCommand("32");
        
        button19 = new JButton("33");
        button19.addActionListener(this);
        button19.setActionCommand("33");
        
        button20 = new JButton("34");
        button20.addActionListener(this);
        button20.setActionCommand("34");
        
        button21 = new JButton("35");
        button21.addActionListener(this);
        button21.setActionCommand("35");
        
        button22 = new JButton("36");
        button22.addActionListener(this);
        button22.setActionCommand("36");
        
        button23 = new JButton("37");
        button23.addActionListener(this);
        button23.setActionCommand("37");
        
        button24 = new JButton("38");
        button24.addActionListener(this);
        button24.setActionCommand("38");
        
        button25 = new JButton("39");
        button25.addActionListener(this);
        button25.setActionCommand("39");
        
        button26 = new JButton("40");
        button26.addActionListener(this);
        button26.setActionCommand("40");
        
        button27 = new JButton("41");
        button27.addActionListener(this);
        button27.setActionCommand("41");
        
        button28 = new JButton("42");
        button28.addActionListener(this);
        button28.setActionCommand("42");
        
        button29 = new JButton("43");
        button29.addActionListener(this);
        button29.setActionCommand("43");
        
        button30 = new JButton("44");
        button30.addActionListener(this);
        button30.setActionCommand("44");
        
        button31 = new JButton("45");
        button31.addActionListener(this);
        button31.setActionCommand("45");
        
        button32 = new JButton("46");
        button32.addActionListener(this);
        button32.setActionCommand("46");
        
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
        
        scrollDown = new JComboBox(cages);
        scrollDown.setEditable(false);
        
        String[] years = {"2012", "2013", "2014", "2015"};
        
        label2 = new JLabel("Birth year?");
        
        scrollDown2 = new JComboBox(years);
        scrollDown2.setEditable(false);
                    
        Panel panel2 = new Panel(new FlowLayout());
        panel2.add(label);
        panel2.add(scrollDown);
        panel2.add(label2);
        panel2.add(scrollDown2);
        
        label3 = new JLabel("Cage: " + scrollDown.getSelectedItem().toString() + ", Year: " + scrollDown2.getSelectedItem().toString());
  
        button33 = new JButton("Cancel");
        button33.addActionListener(this);
        button33.setActionCommand("cancel");
        
        button34 = new JButton("Confirm");
        button34.addActionListener(this);
        button34.setActionCommand("confirm");
        
        Panel panel3 = new Panel(new FlowLayout());
        panel3.add(label3);
        panel3.add(button33);
        panel3.add(button34);
        
        setLayout(new BorderLayout());
        add(panel1, BorderLayout.NORTH);
        add(panel2, BorderLayout.CENTER);
        add(panel3, BorderLayout.SOUTH);
        
        isDone = false;
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