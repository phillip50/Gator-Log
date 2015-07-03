/*
 * For use with the PenApplication.java class
 * When the option to select new class sizes is slected in that class, an object of this class is created
 * "Empty", "Hatching" and "Family" are the default classes included in every list
 * From there, the user selects numerical class size ranges to be added
 * The new class list, if the user confirms, is then stored in a text file
 *
 * @Phillip Dingler [phil50@ufl.edu]
 */

package test;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class ModifyClass extends JFrame
{
        //"this" object
    private ModifyClass frame;
    
        //list of new class sizes
    private final java.util.List<String> classes;
    
        //list of belly sizes
    private final String[] sizeRange;
    
        //next unused belly size
        //combobox sizes only displays unused belly sizes, this int represents the first index to use in the sizeRange array
    private int start;
    
        //interface components
    private final JButton confirm;
    private final JButton cancel;
    private final JButton add;
    private JComboBox sizes;
    
        //font used by the components
    private final Font font;
    
    
    public ModifyClass()
    {
        super("Modify Class Ranges");
        
            //initialize the font
        font = new Font("Arial", Font.PLAIN, 40);
        
            //initialize the buttons and set the font
        confirm = new JButton("Confirm");
        confirm.setFont(font);
        cancel = new JButton("Cancel");
        cancel.setFont(font);
        add = new JButton("Add");
        add.setFont(font);
        
            //initialize class sizes arraylist and add the default classes
        classes = new ArrayList<>();
        classes.add("Empty");
        classes.add("Hatchling");
        classes.add("Family");
        
            //create list of all possible belly sizes
        sizeRange = new String[31];
        for (int i = 16; i < 47; i++)
        {
            sizeRange[i-16] = "" + i;
        }
        
            //initialize the size JComboBox with the entire list of belly sizes
        sizes = new JComboBox(sizeRange);
        sizes.setEditable(false);
        sizes.setFont(font);
        
            //first index to use
        start = 15;
    }
    
        //Add gui components to interface
    public void addComponents()
    {
            //remove all previous components on the frame
        Container contentPane = frame.getContentPane();
        contentPane.removeAll();
        
            //set up a new panel to place on the frame
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
            //represents the gridy variable in GridBagConstraints
            //increments everytime a components is added to the frame
        int yPos = 0;
        
            //Display list of current classes in the class sizes arraylist to the user
            //If there are more than 9 elements in the arraylist, display them on 2 lines
            //otherwise display them all on 1
        if (classes.size() > 9)
        {
            Panel topPanel1 = new Panel();
            Panel topPanel2 = new Panel();
            
            topPanel1.setLayout(new FlowLayout());
            topPanel2.setLayout(new FlowLayout());
            
            JLabel topLabel1 = new JLabel("Classes: ");
            topLabel1.setFont(font);
            topPanel1.add(topLabel1);
                //Display first 9 elements on the first line
            for (int i = 0; i < 9; i++)
            {
                JLabel topLabel2 = new JLabel("" + classes.get(i) + ", ");
                topLabel2.setFont(font);
                topPanel1.add(topLabel2);
            }
                //Display the rets on the second
            for (int i = 9; i < classes.size(); i++)
            {
                JLabel topLabel2 = new JLabel("" + classes.get(i) + ", ");
                topLabel2.setFont(font);
                topPanel2.add(topLabel2);
            }
                //The last element is the every belly size larger than the next unused belly size
            JLabel topLabel3 = new JLabel("" + start + "+");
            topLabel3.setFont(font);
            topPanel2.add(topLabel3);
            
            
                //Add the 2 lines to the panel
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
        
                //Display every element on 1 line
            topPanel.setLayout(new FlowLayout());
            JLabel topLabel1 = new JLabel("Classes: ");
            topLabel1.setFont(font);
            topPanel.add(topLabel1);
            for (String temp : classes)
            {
                JLabel topLabel2 = new JLabel("" + temp + ", ");
                topLabel2.setFont(font);
                topPanel.add(topLabel2);
            }
            JLabel topLabel3 = new JLabel("" + start + "+");
            topLabel3.setFont(font);
            topPanel.add(topLabel3);
            
                //Add the line to the panel
            c.gridx = 0;
            c.gridy = yPos;
            panel.add(topPanel, c);
            yPos++;
        }
        
            //middle panel allows option to add new class size
            //the new class size, should the user want one, will go from the start value until the the user-selected item in the JComboBox
            //if the start value is 47, every possible belly size has been used, so dont allow any more classes
        Panel middlePanel = new Panel();
        middlePanel.setLayout(new FlowLayout());
        
        if (start == 47)
        {
            
        }
        else 
        {
                //create a new array contained only unused belly sizes, and use that to create a new combo box
            String[] tempRange = new String[46 - start];
            for (int i = start + 1; i < 47; i++)
            {
                tempRange[i - start - 1] = sizeRange[i - 16];
            }
            sizes = new JComboBox(tempRange);
            sizes.setFont(font);
            
                //Show the start value to the user for reference, and create an invisible label to place between 2 components, to create some distance
            JLabel middleLabel = new JLabel("" + start + "  -  ");
            JLabel spaceLabel = new JLabel("aaa");
            spaceLabel.setOpaque(false);
            spaceLabel.setForeground(new Color(0, 0, 0, 0));
            spaceLabel.setFont(font);
            middleLabel.setFont(font);
            
                //Add the components to the panel
            middlePanel.add(middleLabel);
            middlePanel.add(sizes);
            middlePanel.add(spaceLabel);
            middlePanel.add(add);
            
                //Add that panel to the main panel
            c.gridy = yPos;
            panel.add(middlePanel, c);
            yPos++;
        }

            //Add the cancel and confirm buttons to a new panel
        Panel bottomPanel = new Panel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(cancel);
        bottomPanel.add(confirm);
            
            //Add that panel to the main panel
        c.gridy = yPos;
        c.insets = new Insets(100, 0, 100, 0);
        panel.add(bottomPanel, c);
        
            //Add the main panel to the frame
        add(panel);
        validate();
    }
    
    public void setFrame(ModifyClass f)
    {
        frame = f;
    }
    
        //Add action listeners to the buttons
    public void initialize()
    {
            //When confirm is clicked, store the user-entered class size list into a text file and close the frame
        confirm.addActionListener(e -> {
            BufferedWriter writer;
            try
            {
                writer = new BufferedWriter(new FileWriter("ClassSizes.txt", false));
                
                for (String temp : classes)
                {
                    writer.write(temp + ",");
                }
                writer.write("" + start + "+");
                
                writer.close();
            }
            catch (IOException e1)
            {
                
            }

            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        
            //When cancel is clicked, close the frame and ignore any user-entered data
        cancel.addActionListener(e -> {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        
            //When add is clicked, add the new class from the start value to the selected item in the combo box to the class sizes list
            //update the new start value to the next number after the user-entered number
        add.addActionListener(e -> {
            int begin = start;
            int end = Integer.parseInt( sizes.getSelectedItem().toString() );
            classes.add("" + begin + "-" + end);
            start = end + 1;
            addComponents();
        });
    }
}
