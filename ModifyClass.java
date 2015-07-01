package test;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.*;
import java.io.*;

public class ModifyClass extends JFrame
{
    private ModifyClass frame;
    private final JButton confirm;
    private final JButton cancel;
    private final JButton add;
    private final java.util.List<String> classes;
    private final String[] sizeRange;
    private JComboBox sizes;
    private int start;
    private final Font font;
    
    public ModifyClass()
    {
        super("Modify Class Ranges");
        
        font = new Font("Arial", Font.PLAIN, 40);
        
        confirm = new JButton("Confirm");
        confirm.setFont(font);
        cancel = new JButton("Cancel");
        cancel.setFont(font);
        add = new JButton("Add");
        add.setFont(font);
        
        classes = new ArrayList<>();
        classes.add("Empty");
        classes.add("Hatchling");
        classes.add("Family");
        
        sizeRange = new String[31];
        for (int i = 16; i < 47; i++)
        {
            sizeRange[i-16] = "" + i;
        }
        
        sizes = new JComboBox(sizeRange);
        sizes.setEditable(false);
        sizes.setFont(font);
        
        start = 15;
    }
    
    public void addComponents()
    {
        Container contentPane = frame.getContentPane();
        contentPane.removeAll();
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        int yPos = 0;
        
        if (classes.size() > 9)
        {
            Panel topPanel1 = new Panel();
            Panel topPanel2 = new Panel();
            
            topPanel1.setLayout(new FlowLayout());
            topPanel2.setLayout(new FlowLayout());
            
            JLabel topLabel1 = new JLabel("Classes: ");
            topLabel1.setFont(font);
            topPanel1.add(topLabel1);
            for (int i = 0; i < 9; i++)
            {
                JLabel topLabel2 = new JLabel("" + classes.get(i) + ", ");
                topLabel2.setFont(font);
                topPanel1.add(topLabel2);
            }
            for (int i = 9; i < classes.size(); i++)
            {
                JLabel topLabel2 = new JLabel("" + classes.get(i) + ", ");
                topLabel2.setFont(font);
                topPanel2.add(topLabel2);
            }
            JLabel topLabel3 = new JLabel("" + start + "+");
            topLabel3.setFont(font);
            topPanel2.add(topLabel3);
            
            c.gridx = 0;
            c.gridy = yPos;
            panel.add(topPanel1, c);
            yPos++;
            
            c.gridy = yPos;
            panel.add(topPanel2, c);
            yPos++;
        }
        else
        {
            Panel topPanel = new Panel();
        
            topPanel.setLayout(new FlowLayout());
            JLabel topLabel1 = new JLabel("Classes: ");
            topLabel1.setFont(font);
            topPanel.add(topLabel1);
            for (String temp : classes) {
                JLabel topLabel2 = new JLabel("" + temp + ", ");
                topLabel2.setFont(font);
                topPanel.add(topLabel2);
            }
            JLabel topLabel3 = new JLabel("" + start + "+");
            topLabel3.setFont(font);
            topPanel.add(topLabel3);
            
            c.gridx = 0;
            c.gridy = yPos;
            panel.add(topPanel, c);
            yPos++;
        }
        
        Panel middlePanel = new Panel();
        middlePanel.setLayout(new FlowLayout());
        
        if (start == 47)
        {
            
        }
        else 
        {
            String[] tempRange = new String[46 - start];
            for (int i = start + 1; i < 47; i++)
            {
                tempRange[i - start - 1] = sizeRange[i - 16];
            }
            sizes = new JComboBox(tempRange);
            sizes.setFont(font);
            
            JLabel middleLabel = new JLabel("" + start + "  -  ");
            JLabel spaceLabel = new JLabel("aaa");
            spaceLabel.setOpaque(false);
            spaceLabel.setForeground(new Color(0, 0, 0, 0));
            spaceLabel.setFont(font);
            middleLabel.setFont(font);
            middlePanel.add(middleLabel);
            middlePanel.add(sizes);
            middlePanel.add(spaceLabel);
            middlePanel.add(add);
            
            c.gridy = yPos;
            panel.add(middlePanel, c);
            yPos++;
        }

        Panel bottomPanel = new Panel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(cancel);
        bottomPanel.add(confirm);
        
        c.gridy = yPos;
        c.insets = new Insets(100, 0, 100, 0);
        panel.add(bottomPanel, c);
        
        add(panel);
        validate();
    }
    
    public void setFrame(ModifyClass f)
    {
        frame = f;
    }
    
    public void initialize()
    {
        confirm.addActionListener(e -> {
            BufferedWriter writer = null;
            try
            {
                writer = new BufferedWriter(new FileWriter("ClassSizes.txt", false));
                
                for (String temp : classes)
                {
                    writer.write(temp + ",");
                    System.out.println(temp + ",");
                }
                writer.write("" + start + "+");
                System.out.println("" + start + "+");
                
                writer.close();
            }
            catch (IOException e1)
            {
                
            }

            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        
        cancel.addActionListener(e -> {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        
        add.addActionListener(e -> {
            int begin = start;
            int end = Integer.parseInt( sizes.getSelectedItem().toString() );
            classes.add("" + begin + "-" + end);
            start = end + 1;
            addComponents();
        });
    }
}
