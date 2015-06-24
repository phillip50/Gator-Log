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
    private java.util.List<Row> rows;
    private File cageFile;
    private Table cageTable;
    private File gatorFile;
    private Table gatorTable;
    private final JTabbedPane tabbedPanel;
    private final java.util.List<JButton> doChange;
    private final java.util.List<JButton> doNotChange;
    private final java.util.List<String> waterChangeDates;
    private final java.util.List<JComboBox> temperatures;
    private final java.util.List<JComboBox> feeds;
    private final java.util.List<JTextField> amounts;
    private final java.util.List<JComboBox> classes;
    private final java.util.List<JTextField> comments;
    private final java.util.List<JButton> confirm;
    private final java.util.List<JButton> cancel;
    private final java.util.List<JLabel> label1;
    private final java.util.List<JLabel> label2;
    private final java.util.List<JLabel> label3;
    private final java.util.List<JLabel> label4;
    private final java.util.List<JLabel> label5;
    private final java.util.List<JLabel> label6;
    private final java.util.List<JLabel> label7;
    private final java.util.List<JLabel> label8;
    private Dimension screenSize;
    private boolean feedAmountValid;
    private Dimension size;
    private double width;
    private double height;
    private Font font;
    private final java.util.List<java.util.List<Row>> gatorList;
    private final java.util.List<java.util.List<String>> tagList;
    private Row selectedGator;
    
    public ModifyWindow(java.util.List<Row> inputRows, String penNumber)
    {
        super("View Pen " + penNumber);
        rows = inputRows;
        tabbedPanel = new JTabbedPane();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        feedAmountValid = true;
        waterChangeDates = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++)
        {
            waterChangeDates.add(rows.get(i).get("Water Change Date").toString());
        }
        width = screenSize.getWidth();
        height = screenSize.getHeight();
        font = new Font("Arial", Font.PLAIN, 25); 
        size = new Dimension((int)(width/6), (int)(height/4));
        temperatures = new ArrayList<>();
        feeds = new ArrayList<>();
        amounts = new ArrayList<>();
        classes = new ArrayList<>();
        comments = new ArrayList<>();
        doChange = new ArrayList<>();
        doNotChange = new ArrayList<>();
        confirm = new ArrayList<>();
        cancel = new ArrayList<>();
        
        gatorList = new ArrayList<>();
        tagList = new ArrayList<>();
        
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
        
        label1 = new ArrayList<>();
        label2 = new ArrayList<>();
        label3 = new ArrayList<>();
        label4 = new ArrayList<>();
        label5 = new ArrayList<>();
        label6 = new ArrayList<>();
        label7 = new ArrayList<>();
        label8 = new ArrayList<>();
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
            modifyPanel.add(label1.get(i), modc);
        
            modc.anchor = GridBagConstraints.LINE_START;
            modc.gridx = 1;
            modc.gridy = 0;
            modifyPanel.add(label2.get(i), modc);
        
            modc.insets = new Insets(50, 30, 10, 30);
        
            modc.gridx = 0;
            modc.gridy = 3;
            modifyPanel.add(label3.get(i), modc);
            modc.gridx = 1;
            modc.gridy = 3;
            modifyPanel.add(doNotChange.get(i), modc);
            modc.anchor = GridBagConstraints.CENTER;
            modifyPanel.add(doChange.get(i), modc);
        
            modc.insets = new Insets(10, 30, 10, 30);
        
            modc.anchor = GridBagConstraints.LINE_START;
            modc.gridx = 0;
            modc.gridy = 4;
            modifyPanel.add(label4.get(i), modc);
            modc.gridx = 1;
            modc.gridy = 4;
            modifyPanel.add(temperatures.get(i), modc);
        
            modc.gridx = 0;
            modc.gridy = 5;
            modifyPanel.add(label5.get(i), modc);
            modc.gridx = 1;
            modc.gridy = 5;
            modifyPanel.add(feeds.get(i), modc);
        
            modc.gridx = 0;
            modc.gridy = 6;
            modifyPanel.add(label6.get(i), modc);
            modc.gridx = 1;
            modc.gridy = 6;
            modc.fill = GridBagConstraints.HORIZONTAL;
            modifyPanel.add(amounts.get(i), modc);
        
            modc.fill = GridBagConstraints.NONE;
            modc.gridx = 0;
            modc.gridy = 7;
            modifyPanel.add(label7.get(i), modc);
            modc.gridx = 1;
            modc.gridy = 7;
            modifyPanel.add(classes.get(i), modc);
        
            modc.gridx = 0;
            modc.gridy = 8;
            modifyPanel.add(label8.get(i), modc);
            modc.gridx = 1;
            modc.gridy = 8;
            modc.fill = GridBagConstraints.BOTH;
            modifyPanel.add(comments.get(i), modc);
        
            modc.insets = new Insets(50, 30, 10, 30);
            modc.fill = GridBagConstraints.NONE;
            modc.anchor = GridBagConstraints.LINE_END;
            modc.gridx = 0;
            modc.gridy = 9;
            modifyPanel.add(confirm.get(i), modc);
            modc.anchor = GridBagConstraints.LINE_START;
            modc.gridx = 1;
            modc.gridy = 9;
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
                JButton tempButton = new JButton("test");
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
            JButton tempDoChange = new JButton("Yes");
            tempDoChange.setEnabled(true);
            tempDoChange.setPreferredSize(size);
            tempDoChange.setFont(font);
            tempDoChange.addActionListener(e -> {
                int j = tabbedPanel.getSelectedIndex();
                doNotChange.get(j).setEnabled(true);
                doChange.get(j).setEnabled(false);
                DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                Date date = new Date();
                waterChangeDates.remove(j);
                waterChangeDates.add(j, dateFormat.format(date));   
            });
            doChange.add(tempDoChange);
            
            JButton tempDoNotChange = new JButton("No");
            tempDoNotChange.setEnabled(false);
            tempDoNotChange.setPreferredSize(size);
            tempDoNotChange.setFont(font);
            tempDoNotChange.addActionListener(e -> {
                int j = tabbedPanel.getSelectedIndex();
                doNotChange.get(j).setEnabled(false);
                doChange.get(j).setEnabled(true);
                waterChangeDates.remove(j);
                waterChangeDates.add(j, rows.get(j).get("Water Change Date").toString());  
            });
            doNotChange.add(tempDoNotChange);
            
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
                    cageTable.addRow(0, rows.get(j).get("Pen Number"), rows.get(j).get("Pen Type"), rows.get(j).get("Square Footage"), rows.get(j).get("Gator Count"), waterChangeDates.get(j), temperatures.get(j).getSelectedItem().toString(), feeds.get(j).getSelectedItem().toString().charAt(1), amounts.get(j).getText(), classes.get(j).getSelectedItem().toString(), comments.get(j).getText());
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
    
    public void setLabels()
    {
        for (int i = 0; i < rows.size(); i++)
        {
            JLabel tempLabel1 = new JLabel("Pen: " + rows.get(i).get("Pen Number"));
            tempLabel1.setFont(font);
            label1.add(tempLabel1);
            
            JLabel tempLabel2 = new JLabel("Gator Count: " + gatorList.get(i).size());
            tempLabel2.setFont(font);
            label2.add(tempLabel2);
            
            JLabel tempLabel3 = new JLabel("Water Change Date: ");
            tempLabel3.setFont(font);
            label3.add(tempLabel3);
            
            JLabel tempLabel4 = new JLabel("Water Temperature: ");
            tempLabel4.setFont(font);
            label4.add(tempLabel4);
            
            JLabel tempLabel5 = new JLabel("Feed Type: ");
            tempLabel5.setFont(font);
            label5.add(tempLabel5);
            
            JLabel tempLabel6 = new JLabel("Feed Amount (in lbs.): ");
            tempLabel6.setFont(font);
            label6.add(tempLabel6);
            
            JLabel tempLabel7 = new JLabel("Size Class: ");
            tempLabel7.setFont(font);
            label7.add(tempLabel7);
            
            JLabel tempLabel8 = new JLabel("Any additional comments: ");
            tempLabel8.setFont(font);
            label8.add(tempLabel8);
        }
    }
}
