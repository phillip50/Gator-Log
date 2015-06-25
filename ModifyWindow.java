//TODO: finish commenting

package test;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import com.healthmarketscience.jackcess.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.util.*;

public class ModifyWindow extends JFrame
{
    private ModifyWindow frame;
    
        //screen size if the size of the monitor, size is the preferred size of interface components
    private Dimension screenSize;
    private Dimension size;
    private double width;
    private double height;
    private Font font;
    
    private String currentDate;
    
        //list of input rows in the cage database
    private java.util.List<Row> rows;
    
        //input databases
    private File cageFile;
    private Table cageTable;
    private File gatorFile;
    private Table gatorTable;
    
        //list of unique tags in database
    private final java.util.List<java.util.List<String>> tagList;
        //list of most recent entry in database for each tag
    private final java.util.List<java.util.List<Row>> gatorList;
        //specific gator tag selected in interface
    private Row selectedGator;
        
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
    
    public ModifyWindow(java.util.List<Row> inputRows, String penNumber)
    {
        super("View Pen " + penNumber);
        rows = inputRows;
            
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
            cageFile = new File("CageDatabase.accdb");
            cageTable = DatabaseBuilder.open(cageFile).getTable("Database");
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
    
    public void addComponents()
    {
        tabbedPanel.removeAll();
        
        //modify panel(s)
        for (int i = 0; i < rows.size(); i++)
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
            
            tabbedPanel.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=30 marginheight=5>Modify Pen</body></html>", modifyPanel);
        }
        
        //view gators panel
        for (int i = 0; i < rows.size(); i++)
        {
            JComponent viewPanel = new JPanel();
            viewPanel.setLayout(new GridBagLayout());
            GridBagConstraints viewc = new GridBagConstraints();
            viewc.insets = new Insets(10, 30, 10, 30);
            viewc.weightx = 1;
            viewc.weighty = 0;
            int j = 0;
        
            for (Row tempRow : gatorList.get(i))
            {
            
                viewc.gridy = j;
                viewc.anchor = GridBagConstraints.LINE_START;
                viewc.gridx = 0;
                JLabel tempLabel = new JLabel("Gator ID: " + tempRow.get("Tag Number").toString());
                tempLabel.setFont(font);
                viewPanel.add(tempLabel, viewc);
            
                viewc.anchor = GridBagConstraints.LINE_END;
                viewc.gridx = 1;
                JButton tempButton = new JButton("View Gator History");
                tempButton.setFont(font);
                tempButton.addActionListener(e -> {
                    selectedGator = tempRow;
                    addComponents();
                });
                viewPanel.add(tempButton, viewc);
            
                j++;
            }
        
            viewc.gridy = j;
            viewc.weighty = 1;
            JLabel dummyLabel = new JLabel("");
            viewPanel.add(dummyLabel, viewc);
            
            tabbedPanel.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=30 marginheight=5>View Gators</body></html>", viewPanel);
        }
        tabbedPanel.setSelectedIndex(0);
        if (selectedGator != null)
        {
            java.util.List<Row> allSelectedGatorEntries = new ArrayList<>();
            try
            {
                com.healthmarketscience.jackcess.Cursor cursor = CursorBuilder.createCursor(gatorTable);
                cursor.beforeFirst();
                while (cursor.moveToNextRow())
                {
                    Row currentRow = cursor.getCurrentRow();
                    if (currentRow.get("Tag Number").toString().equals(selectedGator.get("Tag Number").toString()))
                    {
                        allSelectedGatorEntries.add(currentRow);
                    }
                }
            }
            catch (IOException e)
            {
            
            }
            
            JComponent gatorPanel = new JPanel();
            gatorPanel.setLayout(new GridBagLayout());
            GridBagConstraints gatorc = new GridBagConstraints();
            gatorc.insets = new Insets(10, 30, 10, 30);
            gatorc.weightx = 1;
            gatorc.weighty = 0;
            int i = 0;
            
            for (Row tempRow : allSelectedGatorEntries)
            {           
                gatorc.gridy = i;
                
                gatorc.anchor = GridBagConstraints.LINE_START;
                gatorc.gridx = 0;
                JLabel tempLabel = new JLabel("" + (i+1));
                tempLabel.setFont(font);
                gatorPanel.add(tempLabel, gatorc);
                
                gatorc.gridx = 1;
                JLabel tempLabel2 = new JLabel(tempRow.get("Date").toString());
                tempLabel2.setFont(font);
                gatorPanel.add(tempLabel2, gatorc);
            
                gatorc.gridx = 2;
                JLabel tempLabel3 = new JLabel(tempRow.get("From").toString());
                tempLabel3.setFont(font);
                gatorPanel.add(tempLabel3, gatorc);
                
                gatorc.gridx = 3;
                JLabel tempLabel4 = new JLabel(tempRow.get("To").toString());
                tempLabel4.setFont(font);
                gatorPanel.add(tempLabel4, gatorc);
            
                i++;
            }
        
            gatorc.gridy = i;
            gatorc.weighty = 1;
            JLabel dummyLabel2 = new JLabel("");
            gatorPanel.add(dummyLabel2, gatorc);
            
            tabbedPanel.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=30 marginheight=5>Gator " + selectedGator.get("Tag Number").toString() + "</body></html>", gatorPanel);
            tabbedPanel.setSelectedComponent(gatorPanel);
        }
        
        add(tabbedPanel);
    }
    
    public void setFrame(ModifyWindow f)
    {
        frame = f;
    }
    
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
    
    public void Initialize()
    {
        String[] temperatureList = new String[10];
        for (int i = 0; i < 10; i++)
        {
            temperatureList[i] = "" + (i + 85);
        }
        String[] feedList = {"(R) - Regular", "(H) - Hatchling", "(I) - Intermediate"};
        String[] classList = {"Empty", "Hatchling", "Family", "15-18", "19-23", "24-28", "29-33", "34-36", "37-38", "39+"};
        
        for (int i = 0; i < rows.size(); i++)
        {
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
                    waterChangeDates.add(j, rows.get(j).get("Water Change Date").toString());
                }
            });
            doChange.add(tempDoChange);
            
            changeWater.add(false);
            waterChangeDates.add(rows.get(i).get("Water Change Date").toString());
            
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
            
            JComboBox tempTemperature = new JComboBox(temperatureList);
            tempTemperature.setEditable(false);
            tempTemperature.setPrototypeDisplayValue("Any Additional Commen");
            tempTemperature.setFont(font);
            tempTemperature.setSelectedItem(rows.get(i).get("Water Temperature"));
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
            
            JComboBox tempFeed = new JComboBox(feedList);
            tempFeed.setEditable(false);
            tempFeed.setPrototypeDisplayValue("Any Additional Commen");
            tempFeed.setPreferredSize(size);
            tempFeed.setFont(font);
            
            switch (rows.get(i).get("Feed Type").toString())
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
            
            JTextField tempAmount = new JTextField();
            tempAmount.setFont(font);
            tempAmount.setText(rows.get(i).get("Feed Amount").toString());
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
            
            feedAmountValid.add(true);
            
            JComboBox tempClass = new JComboBox(classList);
            tempClass.setEditable(false);
            tempClass.setPrototypeDisplayValue("Any Additional Commen");
            tempClass.setFont(font);
            tempClass.setSelectedItem(rows.get(i).get("Size Class"));
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
            
            JTextField tempComment = new JTextField(10);
            tempComment.setFont(font);
            comments.add(tempComment);
            
            java.util.List<Row> tempGatorRow = new ArrayList();
            gatorList.add(tempGatorRow);
            java.util.List<String> tempTagRow = new ArrayList();
            tagList.add(tempTagRow);
        
            JButton tempConfirm = new JButton("Confirm");
            tempConfirm.setEnabled(true);
            tempConfirm.setPreferredSize(size);
            tempConfirm.setFont(font);
            tempConfirm.addActionListener(e -> {
                int j = tabbedPanel.getSelectedIndex();
                try
                {
                    cageTable.addRow(0, rows.get(j).get("Pen Number"), rows.get(j).get("Pen Type"), rows.get(j).get("Square Footage"), waterChangeDates.get(j), temperatures.get(j).getSelectedItem().toString(), feeds.get(j).getSelectedItem().toString().charAt(1), amounts.get(j).getText(), classes.get(j).getSelectedItem().toString(), comments.get(j).getText());
                }
                catch (IOException e1)
                {
                    
                }
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            });
            confirm.add(tempConfirm);
            
            JButton tempCancel = new JButton("Cancel");
            tempCancel.setEnabled(true);
            tempCancel.setPreferredSize(size);
            tempCancel.setFont(font);
            tempCancel.addActionListener(e -> {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            });
            cancel.add(tempCancel);
            
            JLabel tempLabel1 = new JLabel("Pen: " + rows.get(i).get("Pen Number"));
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
    
    public void addGators()
    {
        try
        {
            for (int i = 0; i < rows.size(); i++)
            {
                com.healthmarketscience.jackcess.Cursor cursor = CursorBuilder.createCursor(gatorTable);
                cursor.afterLast();
                while (cursor.moveToPreviousRow())
                {
                    Row currentRow = cursor.getCurrentRow();
                    if (tagList.get(i).indexOf(currentRow.get("Tag Number").toString()) == -1)
                    {
                        if (currentRow.get("To").toString().equals(rows.get(i).get("Pen Number").toString()))
                        {
                            gatorList.get(i).add(currentRow);
                        }
                        tagList.get(i).add(currentRow.get("Tag Number").toString());
                    }
                }
            }
        }
        catch (IOException e)
        {
            
        }
    }
}