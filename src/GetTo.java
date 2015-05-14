/*
    GUI class to create window interface for entering belly size
    There are buttons for entering each of the values within the acceptable range
    Which are from 15 cm to 46 cm
*/

package test;

import javax.swing.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.event.*;
 
public class GetTo extends JPanel implements ActionListener
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
    protected JButton button35;
    protected JButton button36;
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
        input = "N/A";
        input2 = "N/A";
        input3 = "N/A";
        input4 = "N/A";
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
        
        Panel panelQuery1 = new Panel(new FlowLayout());
        label = new JLabel("Select Belly Range");
        panelQuery1.add(label);

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
        
        Panel buttonDisplay1 = new Panel(new FlowLayout());      
        buttonDisplay1.add(button1);
        buttonDisplay1.add(button2);
        buttonDisplay1.add(button3);
        buttonDisplay1.add(button4);
        buttonDisplay1.add(button5);
        buttonDisplay1.add(button6);
        buttonDisplay1.add(button7);
        buttonDisplay1.add(button8);
        buttonDisplay1.add(button9);
        buttonDisplay1.add(button10);
        buttonDisplay1.add(button11);
        buttonDisplay1.add(button12);
        buttonDisplay1.add(button13);
        buttonDisplay1.add(button14);
        buttonDisplay1.add(button15);
        buttonDisplay1.add(button16);
        buttonDisplay1.add(button17);
        buttonDisplay1.add(button18);
        buttonDisplay1.add(button19);
        buttonDisplay1.add(button20);
        buttonDisplay1.add(button21);
        buttonDisplay1.add(button22);
        buttonDisplay1.add(button23);
        buttonDisplay1.add(button24);
        buttonDisplay1.add(button25);
        buttonDisplay1.add(button26);
        buttonDisplay1.add(button27);
        buttonDisplay1.add(button28);
        buttonDisplay1.add(button29);
        buttonDisplay1.add(button30);
        buttonDisplay1.add(button31);
        buttonDisplay1.add(button32);
        
        Panel panelQuery2 = new Panel(new FlowLayout());
        label1 = new JLabel("Cage: " + input3 + ", Belly Range: " + input + "-" + input2);
        
        button33 = new JButton("Confirm");
        button33.addActionListener(this);
        button33.setActionCommand("done");
        button33.setEnabled(false);
        
        button34 = new JButton("Cancel");
        button34.addActionListener(this);
        button34.setActionCommand("quit");
        
        panelQuery2.add(label1);
        panelQuery2.add(button33);
        panelQuery2.add(button34);
        
        Panel panelQuery3 = new Panel(new FlowLayout());
        label2 = new JLabel("Cage:");
        //text2 = new JTextField(10);
        scrollDown = new JComboBox(cages);
        scrollDown.setEditable(false);
        button35 = new JButton("Enter Cage");
        button35.addActionListener(this);
        button35.setActionCommand("cage");
        panelQuery3.add(label2);
        panelQuery3.add(scrollDown);
        panelQuery3.add(button35);
        
        Panel panelQuery4 = new Panel(new FlowLayout());
        label3 = new JLabel("Capacity:");
        text3 = new JTextField(10);
        button36 = new JButton("Enter Capacity");
        button36.addActionListener(this);
        button36.setActionCommand("capacity");
        panelQuery4.add(label3);
        panelQuery4.add(text3);
        panelQuery4.add(button36);
        
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
                input = "";
                input2 = "";
            }
            
        }
        else
        {
            input = "";
            input2 = "";
            count = 0;
            hasRange = false;
        }
        label1.setText("Cage: " + input3 + ", Belly Range: " + input + "-" + input2 + ", Capacity: " + input4);
        
        button33.setEnabled(hasCage && hasRange && hasCapacity);
    }
 
    public static String createAndShowGUI()
    {
        JFrame frame = new JFrame("Belly Size");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GetTo newContentPane = new GetTo();
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
        
        return input3 + ":" + input + "-" + input2 + ":" + input4;  
    }
 
    public static void main(String[] args)
    {
     
    }
}
