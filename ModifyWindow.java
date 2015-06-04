package test;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import com.healthmarketscience.jackcess.*;
import java.io.*;
import java.util.Collections;
import java.awt.Graphics;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModifyWindow extends JFrame
{
    private ModifyWindow frame;
    private Container contentPane;
    private Row row;
    private File cageFile;
    private Table cageTable;
    private JTabbedPane tabbedPanel;
    private JButton doChange;
    private JButton doNotChange;
    private String waterChangeDate;
    private JComboBox temperatures;
    private JComboBox feeds;
    private JTextField amount;
    private JComboBox classes;
    private JTextField comments;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    
    public ModifyWindow(Row inputRow)
    {
        super("View Pen");
        row = inputRow;
        contentPane = getContentPane();
        tabbedPanel = new JTabbedPane();
        waterChangeDate = row.get("Water Change Date").toString();
          
        
        try
        {
            cageFile = new File("CageDatabase.accdb");
            cageTable = DatabaseBuilder.open(cageFile).getTable("Database");
        }
        catch (IOException e1)
        {
                            
        }
        
        label1 = new JLabel("Gator Count: " + row.get("Gator Count"));
        label2 = new JLabel("Water Change Date: " + row.get("Water Change Date"));
        label3 = new JLabel("Water Temperature: " + row.get("Water Temperature"));
        label4 = new JLabel("Feed Type: " + row.get("Feed Type"));
        label5 = new JLabel("Feed Amount: " + row.get("Feed Amount"));
        label6 = new JLabel("Size Class: " + row.get("Size Class"));
        label7 = new JLabel("Any additional comments: ");
        
        doChange = new JButton("Yes");
        doChange.setEnabled(true);
        doChange.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                doNotChange.setEnabled(true);
                doChange.setEnabled(false);
                DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                Date date = new Date();
                waterChangeDate = dateFormat.format(date);   
            }
        });
        
        doNotChange = new JButton("No");
        doNotChange.setEnabled(false);
        doNotChange.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                doNotChange.setEnabled(false);
                doChange.setEnabled(true);
                waterChangeDate = row.get("Water Change Date").toString();  
            }
        });
        
        String[] temperatureList = new String[10];
        for (int i = 0; i < 10; i++)
        {
            temperatureList[i] = "" + (i + 85);
        }
        temperatures = new JComboBox(temperatureList);
        temperatures.setEditable(false);
        temperatures.setSelectedItem(row.get("Water Temperature"));
        
        String[] feedList = {"Regular", "Hatchling", "Intermediate"};
        feeds = new JComboBox(feedList);
        feeds.setEditable(false);
        if (row.get("Feed Type").toString().equals("R"))
        {
            feeds.setSelectedItem("Regular");
        }
        else if (row.get("Feed Type").toString().equals("H"))
        {
            feeds.setSelectedItem("Hatchling");
        }
        else
        {
            feeds.setSelectedItem("Intermediate");
        }
        
        amount = new JTextField(10);
        amount.setText(row.get("Feed Amount").toString());
        
        String[] classList = {"Empty", "Hatchling", "Family", "15-18", "19-23", "24-28", "29-33", "34-36", "37-38", "39+"};
        classes = new JComboBox(classList);
        classes.setEditable(false);
        classes.setSelectedItem(row.get("Size Class"));
        
        comments = new JTextField(10);
    }
    
    public void addComponents()
    {
        JComponent modifyPanel = new JPanel();
        modifyPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        
        c.gridx = 0;
        c.gridy = 0;
        modifyPanel.add(label1, c);
        c.gridx = 1;
        c.gridy = 0;
        modifyPanel.add(new JLabel(""), c);
        c.gridx = 2;
        c.gridy = 0;
        modifyPanel.add(new JLabel(""), c);
        c.gridx = 0;
        c.gridy = 1;
        modifyPanel.add(label2, c);
        c.gridx = 1;
        c.gridy = 1;
        modifyPanel.add(doNotChange, c);
        c.gridx = 2;
        c.gridy = 1;
        modifyPanel.add(doChange, c);
        c.gridx = 0;
        c.gridy = 2;
        modifyPanel.add(label3, c);
        c.gridx = 1;
        c.gridy = 2;
        modifyPanel.add(temperatures, c);
        c.gridx = 2;
        c.gridy = 2;
        modifyPanel.add(new JLabel(""), c);
        c.gridx = 0;
        c.gridy = 3;
        modifyPanel.add(label4, c);
        c.gridx = 1;
        c.gridy = 3;
        modifyPanel.add(feeds, c);
        c.gridx = 2;
        c.gridy = 3;
        modifyPanel.add(new JLabel(""), c);
        c.gridx = 0;
        c.gridy = 4;
        modifyPanel.add(label5, c);
        c.gridx = 1;
        c.gridy = 4;
        modifyPanel.add(amount, c);
        c.gridx = 2;
        c.gridy = 4;
        modifyPanel.add(new JLabel("lbs"), c);
        c.gridx = 0;
        c.gridy = 5;
        modifyPanel.add(label6, c);
        c.gridx = 1;
        c.gridy = 5;
        modifyPanel.add(classes, c);
        c.gridx = 2;
        c.gridy = 5;
        modifyPanel.add(new JLabel(""), c);
        c.gridx = 0;
        c.gridy = 6;
        modifyPanel.add(label7, c);
        c.gridx = 1;
        c.gridy = 6;
        modifyPanel.add(comments, c);
        c.gridx = 2;
        c.gridy = 6;
        modifyPanel.add(new JLabel(""), c);
        
        JComponent viewPanel = new JPanel();

        
        tabbedPanel.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Modify</body></html>", modifyPanel);
        tabbedPanel.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>View Gators</body></html>", viewPanel);
        
        add(tabbedPanel);
    }
}
