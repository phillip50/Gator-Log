/**
 * For use with the PenApplication.java class
 * When a pen is specified in that application, an object of this class if created to generate a window specific to that pen
 * In this window, the user can modify attributes of the pen and view gators contained within it
 * If a single gator is selected, all previous entries of the gator in the database are displayed
 * 
 * @Phillip Dingler [phil50@ufl.edu]
 */

//TODO: Load all rows at beginning of application

package test;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import com.healthmarketscience.jackcess.*;
import java.io.*;
import java.text.*;
import javax.swing.event.*;
import java.util.*;

public class ModifyWindow extends JFrame
{
        //"this" object
    private ModifyWindow frame;
    
        //screen size if the size of the monitor, size is the preferred size of interface components
    private Dimension screenSize;
    private Dimension size;
    private double width;
    private double height;
    private Font font;
    
    private String currentDate;
    
        //list of input rows in the pen database
    private java.util.List<Row> penRows;
    
    private java.util.List<Gator> gatorRows;
    
        //input databases
    private File penFile;
    private Table penTable;
    private File gatorFile;
    private Table gatorTable;
    
        //list of unique tags in database
    private final java.util.List<java.util.List<String>> tagList;
        //list of most recent entry in database for each tag
    private final java.util.List<java.util.List<Gator>> gatorList;
        //specific gator tag selected in interface
    private Gator selectedGator;
        
        //components in interface contained in 3 panels:
        //1. Modify panel to change attributes about the pen
        //2. Gator panel to view all gators in the selected pen
        //3. Gator History to view all entries in the gator database for a selected gator
    private final JTabbedPane tabbedPanel;
    
        /*Change water option in the interface
            Has 2 parts, a button to signify whether the water has been changed
            And a text field to input a water change date
            The text field is only enabled in the button has been pressed
        */
    private final java.util.List<JButton> doChange;
    private final java.util.List<Boolean> changeWater;
    private final java.util.List<String> waterChangeDates;
    private final java.util.List<JTextField> waterChangeDateFields;
    
        //Temperature in pen
    private final java.util.List<JComboBox> temperatures;
    
        //Type of food used in the pen
    private final java.util.List<JComboBox> feeds;
    
        //Amount of food in the pen, in pounds
        //boolean check to verify the entered String is a number
    private final java.util.List<JTextField> amounts;
    private final java.util.List<Boolean> feedAmountValid;
    
        //class range of the pen
    private final java.util.List<JComboBox> classes;
    
        //additional comments
    private final java.util.List<JTextField> comments;
    
        //confirm to record entry into database
        //both options close the frame
    private final java.util.List<JButton> confirm;
    private final java.util.List<JButton> cancel;
    
        //labels for each component
    private final java.util.List<JLabel> penLabel;
    private final java.util.List<JLabel> countLabel;
    private final java.util.List<JLabel> changeLabel;
    private final java.util.List<JLabel> temperatureLabel;
    private final java.util.List<JLabel> feedLabel;
    private final java.util.List<JLabel> amountLabel;
    private final java.util.List<JLabel> classLabel;
    private final java.util.List<JLabel> commentLabel;
    
    
    
    public ModifyWindow(java.util.List<Row> inputPenRows, String penNumber, java.util.List<Gator> inputGatorRows)
    {
        super("View Pen " + penNumber);
        penRows = inputPenRows;
        gatorRows = inputGatorRows;
        
            //get screen size and set component size to be fraction of it
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.getWidth();
        height = screenSize.getHeight();
        font = new Font("Arial", Font.PLAIN, 25); 
        size = new Dimension((int)(width/6), (int)(height/4));
        
            //get current date
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        currentDate = dateFormat.format(date); 
        
            //declare 2d arraylists for the unique tags and gator entries
        gatorList = new ArrayList<>();
        tagList = new ArrayList<>();
        
            //open the databases
        try
        {
            penFile = new File("PenDatabase.accdb");
            penTable = DatabaseBuilder.open(penFile).getTable("Database");
            gatorFile = new File("AnimalDatabase.accdb");
            gatorTable = DatabaseBuilder.open(gatorFile).getTable("Database");
        }
        catch (IOException e1)
        {
                            
        }
        
            //Declare the components
        tabbedPanel = new JTabbedPane();
        
        doChange = new ArrayList<>();
        changeWater = new ArrayList<>();
        waterChangeDates = new ArrayList<>();
        waterChangeDateFields = new ArrayList<>();
        
        temperatures = new ArrayList<>();
        
        feeds = new ArrayList<>();
        
        amounts = new ArrayList<>();
        feedAmountValid = new ArrayList<>();
        
        classes = new ArrayList<>();
        
        comments = new ArrayList<>();
        
        confirm = new ArrayList<>();
        cancel = new ArrayList<>();
        
        penLabel = new ArrayList<>();
        countLabel = new ArrayList<>();
        changeLabel = new ArrayList<>();
        temperatureLabel = new ArrayList<>();
        feedLabel = new ArrayList<>();
        amountLabel = new ArrayList<>();
        classLabel = new ArrayList<>();
        commentLabel = new ArrayList<>();
    }
    
    
        //Add components to the interface
        //can be called again to rewrite the components
    public void addComponents()
    {
        tabbedPanel.removeAll();
        
        //modify panel(s)
        for (int i = 0; i < penRows.size(); i++)
        {
            JComponent modifyPanel = new JPanel();
            modifyPanel.setLayout(new GridBagLayout());
            GridBagConstraints modc = new GridBagConstraints();
            modc.insets = new Insets(10, 30, 10, 30);
            modc.anchor = GridBagConstraints.LINE_END;
        
            modc.gridx = 0;
            modc.gridy = 0;
            modifyPanel.add(penLabel.get(i), modc);
        
            modc.anchor = GridBagConstraints.LINE_START;
            modc.gridx = 1;
            modc.gridy = 0;
            modifyPanel.add(countLabel.get(i), modc);
        
            modc.insets = new Insets(50, 30, 10, 30);
        
            modc.gridx = 0;
            modc.gridy = 1;
            modifyPanel.add(changeLabel.get(i), modc);
            modc.fill = GridBagConstraints.HORIZONTAL;
            modc.gridx = 1;
            modc.gridy = 1;
            modifyPanel.add(doChange.get(i), modc);
            
            modc.insets = new Insets(10, 30, 10, 30);
            
            modc.gridx = 0;
            modc.gridy = 2;
            modifyPanel.add(new JLabel(""), modc);
            modc.gridx = 1;
            modc.gridy = 2;
            modifyPanel.add(waterChangeDateFields.get(i), modc);
        
            modc.fill = GridBagConstraints.NONE;
        
            modc.anchor = GridBagConstraints.LINE_START;
            modc.gridx = 0;
            modc.gridy = 3;
            modifyPanel.add(temperatureLabel.get(i), modc);
            modc.gridx = 1;
            modc.gridy = 3;
            modifyPanel.add(temperatures.get(i), modc);
        
            modc.gridx = 0;
            modc.gridy = 4;
            modifyPanel.add(feedLabel.get(i), modc);
            modc.gridx = 1;
            modc.gridy = 4;
            modifyPanel.add(feeds.get(i), modc);
        
            modc.gridx = 0;
            modc.gridy = 5;
            modifyPanel.add(amountLabel.get(i), modc);
            modc.gridx = 1;
            modc.gridy = 5;
            modc.fill = GridBagConstraints.HORIZONTAL;
            modifyPanel.add(amounts.get(i), modc);
        
            modc.fill = GridBagConstraints.NONE;
            modc.gridx = 0;
            modc.gridy = 6;
            modifyPanel.add(classLabel.get(i), modc);
            modc.gridx = 1;
            modc.gridy = 6;
            modifyPanel.add(classes.get(i), modc);
        
            modc.gridx = 0;
            modc.gridy = 7;
            modifyPanel.add(commentLabel.get(i), modc);
            modc.gridx = 1;
            modc.gridy = 7;
            modc.fill = GridBagConstraints.BOTH;
            modifyPanel.add(comments.get(i), modc);
        
            modc.insets = new Insets(50, 30, 10, 30);
            modc.fill = GridBagConstraints.NONE;
            modc.anchor = GridBagConstraints.LINE_END;
            modc.gridx = 0;
            modc.gridy = 8;
            modifyPanel.add(confirm.get(i), modc);
            modc.anchor = GridBagConstraints.LINE_START;
            modc.gridx = 1;
            modc.gridy = 8;
            modifyPanel.add(cancel.get(i), modc);
            
            tabbedPanel.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=30 marginheight=5>Modify Pen " + penRows.get(i).get("Pen Number") + "</body></html>", modifyPanel);
        }
        
        //view gators panel
        for (int i = 0; i < penRows.size(); i++)
        {
            JComponent viewPanel = new JPanel();
            viewPanel.setLayout(new GridBagLayout());
            GridBagConstraints viewc = new GridBagConstraints();
            viewc.insets = new Insets(10, 30, 10, 30);
            viewc.weightx = 1;
            viewc.weighty = 0;
            int j = 0;
        
            for (Gator gator : gatorList.get(i))
            {
            
                viewc.gridy = j;
                viewc.anchor = GridBagConstraints.LINE_START;
                viewc.gridx = 0;
                JLabel tempLabel = new JLabel("Gator ID: " + gator.tagNumber);
                tempLabel.setFont(font);
                viewPanel.add(tempLabel, viewc);
            
                viewc.anchor = GridBagConstraints.LINE_END;
                viewc.gridx = 1;
                JButton tempButton = new JButton("View Gator History");
                tempButton.setFont(font);
                tempButton.addActionListener(e -> {
                    selectedGator = gator;
                    addComponents();
                });
                viewPanel.add(tempButton, viewc);
            
                j++;
            }
        
            viewc.gridy = j;
            viewc.weighty = 1;
            JLabel dummyLabel = new JLabel("");
            viewPanel.add(dummyLabel, viewc);
            
            JScrollPane viewScroll = new JScrollPane(viewPanel);
            viewScroll.getVerticalScrollBar().setPreferredSize(new Dimension(20, 0));
            viewScroll.getVerticalScrollBar().setUnitIncrement(15);
            
            tabbedPanel.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=30 marginheight=5>View Gators in " + penRows.get(i).get("Pen Number") + "</body></html>", viewScroll);
        }
        
        tabbedPanel.setSelectedIndex(0);
        
        if (selectedGator != null)
        {
            java.util.List<Gator> allSelectedGatorEntries = new ArrayList<>();

            for (Gator gator : gatorRows)
            {
                if (gator.tagNumber - selectedGator.tagNumber == 0)
                {
                    allSelectedGatorEntries.add(gator);
                }
            }
            
            JComponent gatorPanel = new JPanel();
            gatorPanel.setLayout(new GridBagLayout());
            GridBagConstraints gatorc = new GridBagConstraints();
            gatorc.insets = new Insets(10, 30, 10, 30);
            gatorc.weightx = 1;
            gatorc.weighty = 0;
            gatorc.anchor = GridBagConstraints.LINE_START;
            
            gatorc.gridx = 0;
            gatorc.gridy = 0;
            
            JLabel topLabel1 = new JLabel("Entry");
            topLabel1.setFont(font);
            gatorPanel.add(topLabel1, gatorc);
            
            gatorc.gridx = 1;
            JLabel topLabel2 = new JLabel("Date");
            topLabel2.setFont(font);
            gatorPanel.add(topLabel2, gatorc);
            
            gatorc.gridx = 2;
            JLabel topLabel3 = new JLabel("From Pen");
            topLabel3.setFont(font);
            gatorPanel.add(topLabel3, gatorc);
            
            gatorc.gridx = 3;
            JLabel topLabel4 = new JLabel("To Pen");
            topLabel4.setFont(font);
            gatorPanel.add(topLabel4, gatorc);
            
            int i = 1;
            
            for (Gator gator : allSelectedGatorEntries)
            {           
                gatorc.gridy = i;
                
                gatorc.gridx = 0;
                JLabel tempLabel = new JLabel("" + i);
                tempLabel.setFont(font);
                gatorPanel.add(tempLabel, gatorc);
                
                gatorc.gridx = 1;
                JLabel tempLabel2 = new JLabel(gator.date);
                tempLabel2.setFont(font);
                gatorPanel.add(tempLabel2, gatorc);
            
                gatorc.gridx = 2;
                JLabel tempLabel3 = new JLabel(gator.from);
                tempLabel3.setFont(font);
                gatorPanel.add(tempLabel3, gatorc);
                
                gatorc.gridx = 3;
                JLabel tempLabel4 = new JLabel(gator.to);
                tempLabel4.setFont(font);
                gatorPanel.add(tempLabel4, gatorc);
            
                i++;
            }
        
            gatorc.gridy = i;
            gatorc.weighty = 1;
            JLabel dummyLabel2 = new JLabel("");
            gatorPanel.add(dummyLabel2, gatorc);
            
            JScrollPane gatorScroll = new JScrollPane(gatorPanel);
            gatorScroll.getVerticalScrollBar().setPreferredSize(new Dimension(20, 0));
            gatorScroll.getVerticalScrollBar().setUnitIncrement(15);
            
            tabbedPanel.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=30 marginheight=5>Gator " + selectedGator.tagNumber + "</body></html>", gatorScroll);
            tabbedPanel.setSelectedComponent(gatorScroll);
        }
        
        add(tabbedPanel);
    }
    
        //get "this" instance
    public void setFrame(ModifyWindow f)
    {
        frame = f;
    }
    
        //helper method to check if a given string can be parsed as an integer
    public static boolean isInteger(String str)
    {
	if (str == null)
        {
		return false;
	}
	int length = str.length();
	if (length == 0)
        {
		return false;
	}
	int i = 0;
	if (str.charAt(0) == '-')
        {
		if (length == 1)
                {
			return false;
		}
		i = 1;
	}
	for (; i < length; i++)
        {
            char c = str.charAt(i);
            if (c <= '/' || c >= ':')
            {
                return false;
            }
	}
	return true;
    }
    
        //define all components in the interface and add listeners
    public void Initialize()
    {
            //initialize lists to be used in the drop down menus
        String[] temperatureList = {"85", "86", "87", "88", "89", "90", "91", "92", "93", "94"};
        String[] feedList = {"(R) - Regular", "(H) - Hatchling", "(I) - Intermediate"};
        String[] classList = null;
        
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("ClassSizes.txt"));
            String temp = reader.readLine();
            classList = temp.split(",");
        }
        catch (IOException e)
        {
            
        }
        
        
        for (int i = 0; i < penRows.size(); i++)
        {            
                //Button used to enabled and disable the waterChangeDateField text entry
                //if enabled, the new record's water change date will be whats entered in that field
                //if disabled, the new record's water change date will be the date from the previous record
            JButton tempDoChange = new JButton("Didn't change");
            tempDoChange.setEnabled(true);
            tempDoChange.setPreferredSize(size);
            tempDoChange.setFont(font);
            tempDoChange.addActionListener(e -> {
                int j = tabbedPanel.getSelectedIndex();
                boolean temp = changeWater.get(j);
                changeWater.remove(j);
                changeWater.add(j, !temp);
                if (doChange.get(j).getText().equals("Didn't change"))
                {
                    doChange.get(j).setText("Did Change");
                    waterChangeDateFields.get(j).setEnabled(true);
                    waterChangeDates.remove(j);
                    waterChangeDates.add(j, waterChangeDateFields.get(j).getText());
                }
                else
                {
                    doChange.get(j).setText("Didn't change");
                    waterChangeDateFields.get(j).setEnabled(false);
                    waterChangeDates.remove(j);
                    waterChangeDates.add(j, penRows.get(j).get("Water Change Date").toString());
                }
            });
            doChange.add(tempDoChange);
            
            changeWater.add(false);
            waterChangeDates.add(penRows.get(i).get("Water Change Date").toString());
            
            
                //text field to record the date of a water change, if the water has been changed
                //if the doChange button has not been clicked, this field is disabled
            JTextField tempWaterChangeField = new JTextField(10);
            tempWaterChangeField.setFont(font);
            tempWaterChangeField.setText(currentDate);
            tempWaterChangeField.setEnabled(false);
            tempWaterChangeField.getDocument().addDocumentListener(new DocumentListener()
            {
                @Override
                public void changedUpdate(DocumentEvent e)
                {
                    check();
                }
            
                @Override
                public void removeUpdate(DocumentEvent e)
                {
                    check();
                }
            
                @Override
                public void insertUpdate(DocumentEvent e)
                {
                    check();
                }

                public void check()
                {
                    int j = tabbedPanel.getSelectedIndex();
                    waterChangeDates.remove(j);
                    waterChangeDates.add(j, waterChangeDateFields.get(j).getText());
                }
            });
            waterChangeDateFields.add(tempWaterChangeField);
            
            
            
                //drop down list to enter the temperature of the pen
            JComboBox tempTemperature = new JComboBox(temperatureList);
            tempTemperature.setEditable(false);
            tempTemperature.setPrototypeDisplayValue("Any Additional Commen");
            tempTemperature.setFont(font);
            tempTemperature.setSelectedItem(penRows.get(i).get("Water Temperature"));
            tempTemperature.addPopupMenuListener(new PopupMenuListener()
            {
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent e)
                {
                    JComboBox comboBox = (JComboBox) e.getSource();
                    Object popup = comboBox.getUI().getAccessibleChild(comboBox, 0);
                    Component c = ((Container) popup).getComponent(0);
                    if (c instanceof JScrollPane)
                    {
                        JScrollPane scrollpane = (JScrollPane) c;
                        JScrollBar scrollBar = scrollpane.getVerticalScrollBar();
                        Dimension scrollBarDim = new Dimension((int)(width / 60), scrollBar.getPreferredSize().height);
                        scrollBar.setPreferredSize(scrollBarDim);
                    }
                }
            
                @Override
                public void popupMenuCanceled(PopupMenuEvent e)
                {
                
                }
            
                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
                {
                
                }
            });
            temperatures.add(tempTemperature);
            
            
            
                //drop down list to select the food type of the pen
            JComboBox tempFeed = new JComboBox(feedList);
            tempFeed.setEditable(false);
            tempFeed.setPrototypeDisplayValue("Any Additional Commen");
            tempFeed.setPreferredSize(size);
            tempFeed.setFont(font);
            
            switch (penRows.get(i).get("Feed Type").toString())
            {
                case "R":   
                    tempFeed.setSelectedItem("(R) - Regular");
                    break;
                
                case "H":   
                    tempFeed.setSelectedItem("(H) - Hatchling");
                    break;
                
                case "I":   
                    tempFeed.setSelectedItem("(I) - Intermediate");
                    break;
            }
            
            feeds.add(tempFeed);
            
            
                //text field to enter the amount of food, in pounds
            JTextField tempAmount = new JTextField();
            tempAmount.setFont(font);
            tempAmount.setText(penRows.get(i).get("Feed Amount").toString());
            tempAmount.getDocument().addDocumentListener(new DocumentListener()
            {
                @Override
                public void changedUpdate(DocumentEvent e)
                {
                    check();
                }
            
                @Override
                public void removeUpdate(DocumentEvent e)
                {
                    check();
                }
            
                @Override
                public void insertUpdate(DocumentEvent e)
                {
                    check();
                }

                public void check()
                {
                    //feedAmountValid = isInteger(amount.getText()) && Integer.parseInt(amount.getText()) >= 0;
                    //confirm.setEnabled(feedAmountValid);
                }
            });
            amounts.add(tempAmount);
            
            
                //boolean check to verify that the feed amount is an integer
            feedAmountValid.add(true);
            
            
                //drop down menu to select the size class of the pen
            JComboBox tempClass = new JComboBox(classList);
            tempClass.setEditable(false);
            tempClass.setPrototypeDisplayValue("Any Additional Commen");
            tempClass.setFont(font);
            tempClass.setSelectedItem(penRows.get(i).get("Size Class"));
            tempClass.addPopupMenuListener(new PopupMenuListener()
            {
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent e)
                {
                    JComboBox comboBox = (JComboBox) e.getSource();
                    Object popup = comboBox.getUI().getAccessibleChild(comboBox, 0);
                    Component c = ((Container) popup).getComponent(0);
                    if (c instanceof JScrollPane)
                    {
                        JScrollPane scrollpane = (JScrollPane) c;
                        JScrollBar scrollBar = scrollpane.getVerticalScrollBar();
                        Dimension scrollBarDim = new Dimension((int)(width / 60), scrollBar.getPreferredSize().height);
                        scrollBar.setPreferredSize(scrollBarDim);
                    }
                }
            
                @Override
                public void popupMenuCanceled(PopupMenuEvent e)
                {
                
                }
            
                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
                {
                
                }
            });
            classes.add(tempClass);
            
            
                //simple text field to add additional comments about the record
            JTextField tempComment = new JTextField(10);
            tempComment.setFont(font);
            comments.add(tempComment);
        
            
                //when confirm is click, record the entry into the database and close the frame
            JButton tempConfirm = new JButton("Confirm");
            tempConfirm.setEnabled(true);
            tempConfirm.setPreferredSize(size);
            tempConfirm.setFont(font);
            tempConfirm.addActionListener(e -> {
                int j = tabbedPanel.getSelectedIndex();
                try
                {
                    penTable.addRow(0, penRows.get(j).get("Pen Number"), penRows.get(j).get("Pen Type"), penRows.get(j).get("Square Footage"), waterChangeDates.get(j), temperatures.get(j).getSelectedItem().toString(), feeds.get(j).getSelectedItem().toString().charAt(1), amounts.get(j).getText(), classes.get(j).getSelectedItem().toString(), comments.get(j).getText());
                }
                catch (IOException e1)
                {
                    
                }
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            });
            confirm.add(tempConfirm);
            
            
                //when cancel is clicked, close the frame
            JButton tempCancel = new JButton("Cancel");
            tempCancel.setEnabled(true);
            tempCancel.setPreferredSize(size);
            tempCancel.setFont(font);
            tempCancel.addActionListener(e -> {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            });
            cancel.add(tempCancel);
            
            
                //initialize the labels for each components
            JLabel tempLabel1 = new JLabel("Pen: " + penRows.get(i).get("Pen Number"));
            tempLabel1.setFont(font);
            penLabel.add(tempLabel1);
            
            JLabel tempLabel2 = new JLabel("Gator Count: " + gatorList.get(i).size());
            tempLabel2.setFont(font);
            countLabel.add(tempLabel2);
            
            JLabel tempLabel3 = new JLabel("Water Change Date: ");
            tempLabel3.setFont(font);
            changeLabel.add(tempLabel3);
            
            JLabel tempLabel4 = new JLabel("Water Temperature: ");
            tempLabel4.setFont(font);
            temperatureLabel.add(tempLabel4);
            
            JLabel tempLabel5 = new JLabel("Feed Type: ");
            tempLabel5.setFont(font);
            feedLabel.add(tempLabel5);
            
            JLabel tempLabel6 = new JLabel("Feed Amount (in lbs.): ");
            tempLabel6.setFont(font);
            amountLabel.add(tempLabel6);
            
            JLabel tempLabel7 = new JLabel("Size Class: ");
            tempLabel7.setFont(font);
            classLabel.add(tempLabel7);
            
            JLabel tempLabel8 = new JLabel("Any additional comments: ");
            tempLabel8.setFont(font);
            commentLabel.add(tempLabel8);
        }
    }
    
        //add all unique gators in the specified pen to gatorList
        //tagList is used as a check to verify that only the most recent entry in the gator database is used for each gator tag
    public void addGators()
    {        
        for (int i = 0; i < penRows.size(); i++)
        {
                //declare each inner array in the 2d arrays
            java.util.List<Gator> tempGatorRow = new ArrayList();
            gatorList.add(tempGatorRow);
            java.util.List<String> tempTagRow = new ArrayList();
            tagList.add(tempTagRow);
                
            /*for (int j = gatorRows.size() - 1; j >= 0; j--)
            {
                Gator gator = gatorRows.get(j);
                if (tagList.get(i).indexOf(gator.tagNumber) == -1)
                {
                    if (gator.to.equals(penRows.get(i).get("Pen Number").toString()) && !("Yes".equals(gator.harvested)) )
                    {
                        gatorList.get(i).add(gator);
                    }
                    tagList.get(i).add("" + gator.tagNumber);
                }
            }*/
            int lastTag = -1;
            for (int j = gatorRows.size() - 1; j >= 0; j--)
            {
                Gator gator = gatorRows.get(j);
                int thisTag = gator.tagNumber;
                
                if (thisTag - lastTag != 0)
                {
                    if ( gator.to.equals(penRows.get(i).get("Pen Number").toString()) && !("Yes".equals(gator.harvested)) )
                    {
                        gatorList.get(i).add(gator);
                    }
                }
                lastTag = thisTag;
            }
        }
    }
}