/**
 * For use with the PenApplication.java class
 * When the option to change which pens are quartered in that application, an object of this class is created
 * A list of current quartered pens is displayed, as well as a list of all pens on the farm
 * The user can then specify which new pens they want quartered or which existing quartered pens they want not quartered
 * 
 * @Phillip Dingler [phil50@ufl.edu]
 */

package test;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import com.healthmarketscience.jackcess.*;

public class ModifyQuarters extends JFrame
{
        //"this" object
    private ModifyQuarters frame;
    
        //list of pens on the farm
    private String[] penList;
    
        //Old list of quartered pens
    private final java.util.List<String> previousQuarteredList;
    
        //new list of quartered pens
    private final java.util.List<String> quarteredList;
    
        //interface components
    private JComboBox penComboBox;
    private JButton add;
    private JButton confirm;
    private JButton cancel;
    
        //font used by the components
    private final Font font;
    
        //database files
    private File penFile;
    private Table penTable;
    
    public ModifyQuarters()
    {
        super("Modify Quartered Pens");
            
            //initialize font
        font = new Font("Arial", Font.PLAIN, 40);
        
            //initialize static gui components
        add = new JButton("Add");
        add.setFont(font);
        confirm = new JButton("Confirm");
        confirm.setFont(font);
        cancel = new JButton("Cancel");
        cancel.setFont(font);
        
            //initialize with pen numbers
        penList = new String[149];
        int j = 0;
        for (int i = 101; i <= 127; i++)
        {
            penList[j] = "" + i;
            j++;
        }
        for (int i = 201; i <= 232; i++)
        {
            penList[j] = "" + i;
            j++;
        }
        for (int i = 301; i <= 326; i++)
        {
            penList[j] = "" + i;
            j++;
        }
        for (int i = 401; i <= 437; i++)
        {
            penList[j] = "" + i;
            j++;
        }
        for (int i = 801; i <= 816; i++)
        {
            penList[j] = "" + i;
            j++;
        }
        for (int i = 901; i <= 910; i++)
        {
            penList[j] = "" + i;
            j++;
        }
        
            //setup the output storage file, which records the current quartered pen list
            //as well as open the pen database
        BufferedReader reader;
        String temp = "";
        try
        {
            reader = new BufferedReader(new FileReader("QuarteredPens.txt"));
            temp = reader.readLine();
            
            penFile = new File("PenDatabase.accdb");
            penTable = DatabaseBuilder.open(penFile).getTable("Database");
        }
        catch (IOException e)
        {
            
        }
        
            //initialize both quartered pen lists
            //both are initially set to the old quartered pen list
        quarteredList = new ArrayList( Arrays.asList(temp.split(",")) );
        previousQuarteredList = new ArrayList( Arrays.asList(temp.split(",")) );
    }
    
        //add interface components to the frame
    public void addComponents()
    {
            //create a blank frame
        Container contentPane = frame.getContentPane();
        contentPane.removeAll();
        
        Panel panel = new Panel();
        panel.setLayout(new GridBagLayout());      
        GridBagConstraints c = new GridBagConstraints();
        
            //create a list of pens which are not quartered
        String[] minusQuartered = new String[penList.length - quarteredList.size()];
        int j = 0;
        for (int i = 0; i < 149; i++)
        {
            if (quarteredList.indexOf(penList[i]) == -1)
            {
                minusQuartered[j] = penList[i];
                j++;
            }
        }
        
        penComboBox = new JComboBox(minusQuartered);
        penComboBox.setFont(font);
        JLabel topLabel = new JLabel("Designate New Quartered Pen: ");
        topLabel.setFont(font);
        
        Panel topPanel = new Panel();
        topPanel.setLayout(new FlowLayout());
        topPanel.add(topLabel);
        topPanel.add(penComboBox);
        topPanel.add(add);
        
        Panel topMiddlePanel = new Panel();
        topMiddlePanel.setLayout(new FlowLayout());
        JLabel topMiddleLabel = new JLabel("Remove Quartered Pens");
        topMiddleLabel.setFont(font);
        topMiddlePanel.add(topMiddleLabel);
        
        Panel bottomMiddlePanel = new Panel();
        bottomMiddlePanel.setLayout(new FlowLayout());
        for (String temp : quarteredList)
        {
            JButton button = new JButton(temp);
            button.setFont(font);
            button.addActionListener(e -> {
                String pen = ((JButton) e.getSource()).getText();
                quarteredList.remove(quarteredList.indexOf(pen));
                addComponents();
            });
            
            bottomMiddlePanel.add(button);
        }
        
        Panel bottomPanel = new Panel();
        bottomPanel.add(cancel);
        bottomPanel.add(confirm);
        
        c.gridx = 0;
        c.gridy = 0;
        panel.add(topPanel, c);
        
        c.gridy = 1;
        panel.add(topMiddlePanel, c);
        
        c.gridy = 2;
        panel.add(bottomMiddlePanel, c);
        
        c.gridy = 3;
        panel.add(bottomPanel, c);
        
        add(panel);
        validate();
    }
    
    public void setFrame(ModifyQuarters f)
    {
        frame = f;
    }
    
        //add action listeners to buttons
    public void initialize()
    {
            //when confirm is clicked: close the frame, write new quartered pen list to file, and add new pen entries to database designated the pens as quartered or not
        confirm.addActionListener(e -> {
            BufferedWriter writer;
            try
            {
                writer = new BufferedWriter(new FileWriter("QuarteredPens.txt", false));
                
                    //write new quartered pen list to text file for storage
                Iterator<String> it = quarteredList.iterator();
                if (it.hasNext())
                {
                    writer.write(it.next());
                }
                while (it.hasNext())
                {
                    writer.write("," + it.next());
                }
                
                writer.close();
                
                    //get each pen in new quartered pen list and check if it was part of the old one
                    //if it wasn't, designate it so in the database
                for (String temp : quarteredList)
                {
                    if (previousQuarteredList.indexOf(temp) == -1)
                    {
                        System.out.println("New: " + temp);
                        com.healthmarketscience.jackcess.Cursor cursor = CursorBuilder.createCursor(penTable);
                        cursor.afterLast();
                        boolean isDone = false;
                        
                        while (!isDone)
                        {
                            Row row = cursor.getPreviousRow();
                            if (row != null && row.get("Pen Number").toString().equals(temp))
                            {
                                penTable.addRow(0, temp + ".1", "Quartered", 150, row.get("Water Change Date"), row.get("Water Temperature"), row.get("Feed Type"), row.get("Feed Amount"), row.get("Size Class"), "Made quartered pen");
                                penTable.addRow(0, temp + ".2", "Quartered", 150, row.get("Water Change Date"), row.get("Water Temperature"), row.get("Feed Type"), row.get("Feed Amount"), row.get("Size Class"), "Made quartered pen");
                                penTable.addRow(0, temp + ".3", "Quartered", 150, row.get("Water Change Date"), row.get("Water Temperature"), row.get("Feed Type"), row.get("Feed Amount"), row.get("Size Class"), "Made quartered pen");
                                penTable.addRow(0, temp + ".4", "Quartered", 150, row.get("Water Change Date"), row.get("Water Temperature"), row.get("Feed Type"), row.get("Feed Amount"), row.get("Size Class"), "Made quartered pen");
                                isDone = true;
                            }
                        }
                    }
                }
            
                    //get each pen in old quartered pen list and check if it is in the new quartered pen list
                    //if it isn't, designate it not quartered in the database
                for (String temp : previousQuarteredList)
                {
                    if (quarteredList.indexOf(temp) == -1)
                    {
                        com.healthmarketscience.jackcess.Cursor cursor = CursorBuilder.createCursor(penTable);
                        cursor.afterLast();
                        boolean isDone = false;
                        
                        while (!isDone)
                        {
                            Row row = cursor.getPreviousRow();
                            if (row != null)
                            {
                                String penNumber = row.get("Pen Number").toString();
                                if (penNumber.equals(temp + ".1") || penNumber.equals(temp + ".2") || penNumber.equals(temp + ".3") || penNumber.equals(temp + ".4"))
                                {
                                    isDone = true;
                                    penTable.addRow(0, temp, "Large", 600, row.get("Water Change Date"), row.get("Water Temperature"), row.get("Feed Type"), row.get("Feed Amount"), row.get("Size Class"), "Made normal pen");
                                }
                            }
                        }
                    }
                }
            }
            catch (IOException e1)
            {
                
            }
                //close the frame
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        
            //close the frame when cancel is clicked and ignore all inputs
        cancel.addActionListener(e -> {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        
            //add selected pen to the quartered pen list when add is clicked
        add.addActionListener(e -> {
            quarteredList.add(penComboBox.getSelectedItem().toString());
            addComponents();
        });
    }
}
