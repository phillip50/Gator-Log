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
    private Row row;
    private File cageFile;
    private Table cageTable;
    private File gatorFile;
    private Table gatorTable;
    private final JTabbedPane tabbedPanel;
    private final JButton doChange;
    private final JButton doNotChange;
    private String waterChangeDate;
    private final JComboBox temperatures;
    private final JComboBox feeds;
    private final JTextField amount;
    private final JComboBox classes;
    private final JTextField comments;
    private final JButton confirm;
    private final JButton cancel;
    private final JLabel label1;
    private final JLabel label2;
    private final JLabel label3;
    private final JLabel label4;
    private final JLabel label5;
    private final JLabel label6;
    private final JLabel label7;
    private final JLabel label8;
    private Dimension screenSize;
    private boolean feedAmountValid;
    private Dimension size;
    private double width;
    private double height;
    private Font font;
    private final java.util.List<Row> gatorList;
    private final java.util.List<String> tagList;
    private Row selectedGator;
    
    public ModifyWindow(Row inputRow)
    {
        super("View Pen " + inputRow.get("Pen Number"));
        row = inputRow;
        tabbedPanel = new JTabbedPane();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        feedAmountValid = true;
        waterChangeDate = row.get("Water Change Date").toString();
        
        width = screenSize.getWidth();
        height = screenSize.getHeight();
        font = new Font("Arial", Font.PLAIN, 25); 
        size = new Dimension((int)(width/6), (int)(height/4));
        
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
        
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        label7 = new JLabel();
        label8 = new JLabel();
        
        doChange = new JButton("Yes");
        doChange.setEnabled(true);
        doChange.setPreferredSize(size);
        doChange.setFont(font);
        
        doNotChange = new JButton("No");
        doNotChange.setEnabled(false);
        doNotChange.setPreferredSize(size);
        doNotChange.setFont(font);
        
        String[] temperatureList = new String[10];
        for (int i = 0; i < 10; i++)
        {
            temperatureList[i] = "" + (i + 85);
        }
        temperatures = new JComboBox(temperatureList);
        temperatures.setEditable(false);
        temperatures.setPrototypeDisplayValue("Any Additional Commen");
        temperatures.setFont(font);
        temperatures.setSelectedItem(row.get("Water Temperature"));
        
        String[] feedList = {"(R) - Regular", "(H) - Hatchling", "(I) - Intermediate"};
        feeds = new JComboBox(feedList);
        feeds.setEditable(false);
        feeds.setPrototypeDisplayValue("Any Additional Commen");
        feeds.setPreferredSize(size);
        feeds.setFont(font);
        
        switch (row.get("Feed Type").toString())
        {
            case "R":   
                feeds.setSelectedItem("(R) - Regular");
                break;
                
            case "H":   
                feeds.setSelectedItem("(H) - Hatchling");
                break;
                
            case "I":   
                feeds.setSelectedItem("(I) - Intermediate");
                break;
        }

        amount = new JTextField(10);
        amount.setFont(font);
        amount.setText(row.get("Feed Amount").toString());
        
        String[] classList = {"Empty", "Hatchling", "Family", "15-18", "19-23", "24-28", "29-33", "34-36", "37-38", "39+"};
        classes = new JComboBox(classList);
        classes.setEditable(false);
        classes.setPrototypeDisplayValue("Any Additional Commen");
        classes.setFont(font);
        classes.setSelectedItem(row.get("Size Class"));
        
        comments = new JTextField(10);
        comments.setFont(font);

        confirm = new JButton("Confirm");
        confirm.setEnabled(true);
        confirm.setPreferredSize(size);
        confirm.setFont(font);
        
        cancel = new JButton("Cancel");
        cancel.setEnabled(true);
        cancel.setPreferredSize(size);
        cancel.setFont(font);
        
        gatorList = new ArrayList<>();
        tagList = new ArrayList<>();
    }
    
    public void addComponents()
    {
        tabbedPanel.removeAll();
        
        //modify panel
        
        JComponent modifyPanel = new JPanel();
        modifyPanel.setLayout(new GridBagLayout());
        GridBagConstraints modc = new GridBagConstraints();
        modc.insets = new Insets(10, 30, 10, 30);
        modc.anchor = GridBagConstraints.LINE_END;
        
        modc.gridx = 0;
        modc.gridy = 0;
        modifyPanel.add(label1, modc);
        
        modc.anchor = GridBagConstraints.LINE_START;
        modc.gridx = 1;
        modc.gridy = 0;
        modifyPanel.add(label2, modc);
        
        modc.insets = new Insets(50, 30, 10, 30);
        
        modc.gridx = 0;
        modc.gridy = 3;
        modifyPanel.add(label3, modc);
        modc.gridx = 1;
        modc.gridy = 3;
        modifyPanel.add(doNotChange, modc);
        modc.anchor = GridBagConstraints.CENTER;
        modifyPanel.add(doChange, modc);
        
        modc.insets = new Insets(10, 30, 10, 30);
        
        modc.anchor = GridBagConstraints.LINE_START;
        modc.gridx = 0;
        modc.gridy = 4;
        modifyPanel.add(label4, modc);
        modc.gridx = 1;
        modc.gridy = 4;
        modifyPanel.add(temperatures, modc);
        
        modc.gridx = 0;
        modc.gridy = 5;
        modifyPanel.add(label5, modc);
        modc.gridx = 1;
        modc.gridy = 5;
        modifyPanel.add(feeds, modc);
        
        modc.gridx = 0;
        modc.gridy = 6;
        modifyPanel.add(label6, modc);
        modc.gridx = 1;
        modc.gridy = 6;
        modc.fill = GridBagConstraints.HORIZONTAL;
        modifyPanel.add(amount, modc);
        
        modc.fill = GridBagConstraints.NONE;
        modc.gridx = 0;
        modc.gridy = 7;
        modifyPanel.add(label7, modc);
        modc.gridx = 1;
        modc.gridy = 7;
        modifyPanel.add(classes, modc);
        
        modc.gridx = 0;
        modc.gridy = 8;
        modifyPanel.add(label8, modc);
        modc.gridx = 1;
        modc.gridy = 8;
        modc.fill = GridBagConstraints.BOTH;
        modifyPanel.add(comments, modc);
        
        modc.insets = new Insets(50, 30, 10, 30);
        modc.fill = GridBagConstraints.NONE;
        modc.anchor = GridBagConstraints.LINE_END;
        modc.gridx = 0;
        modc.gridy = 9;
        modifyPanel.add(confirm, modc);
        modc.anchor = GridBagConstraints.LINE_START;
        modc.gridx = 1;
        modc.gridy = 9;
        modifyPanel.add(cancel, modc);
        
        
        //view gators panel
        
        JComponent viewPanel = new JPanel();
        viewPanel.setLayout(new GridBagLayout());
        GridBagConstraints viewc = new GridBagConstraints();
        viewc.insets = new Insets(10, 30, 10, 30);
        viewc.weightx = 1;
        viewc.weighty = 0;
        int i = 0;
        
        for (Row tempRow : gatorList)
        {
            
            viewc.gridy = i;
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
            
            i++;
        }
        
        viewc.gridy = i;
        viewc.weighty = 1;
        JLabel dummyLabel = new JLabel("");
        viewPanel.add(dummyLabel, viewc);
        
        
        //putting the two panels together
        tabbedPanel.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=30 marginheight=5>Modify Pen</body></html>", modifyPanel);
        tabbedPanel.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=30 marginheight=5>View Gators</body></html>", viewPanel);
        
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
            i = 0;
            
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
    
    public void addListeners()
    {
        doChange.addActionListener(e -> {
            doNotChange.setEnabled(true);
            doChange.setEnabled(false);
            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            Date date = new Date();
            waterChangeDate = dateFormat.format(date);   
        });
        
        doNotChange.addActionListener(e -> {
            doNotChange.setEnabled(false);
            doChange.setEnabled(true);
            waterChangeDate = row.get("Water Change Date").toString();  
        });
        
        temperatures.addPopupMenuListener(new PopupMenuListener()
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
        
        amount.getDocument().addDocumentListener(new DocumentListener()
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
                feedAmountValid = isInteger(amount.getText()) && Integer.parseInt(amount.getText()) >= 0;
                confirm.setEnabled(feedAmountValid);
            }
        });
        
        classes.addPopupMenuListener(new PopupMenuListener()
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
        
        confirm.addActionListener(e -> {
            try
            {
                cageTable.addRow(0, row.get("Pen Number"), row.get("Pen Type"), row.get("Square Footage"), row.get("Gator Count"), waterChangeDate, temperatures.getSelectedItem().toString(), feeds.getSelectedItem().toString().charAt(1), amount.getText(), classes.getSelectedItem().toString(), comments.getText());
            }
            catch (IOException e1)
            {
                    
            }
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        
        cancel.addActionListener(e -> {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
    }
    
    public void addGators()
    {
        try
        {
            com.healthmarketscience.jackcess.Cursor cursor = CursorBuilder.createCursor(gatorTable);
            cursor.afterLast();
            while (cursor.moveToPreviousRow())
            {
                Row currentRow = cursor.getCurrentRow();
                if (tagList.indexOf(currentRow.get("Tag Number").toString()) == -1)
                {
                    if (currentRow.get("To").toString().equals(row.get("Pen Number").toString()))
                    {
                        gatorList.add(currentRow);
                    }
                    tagList.add(currentRow.get("Tag Number").toString());
                }
            }
        }
        catch (IOException e)
        {
            
        }
    }
    
    public void setLabels()
    {
        label1.setText("Pen: " + row.get("Pen Number"));
        label1.setFont(font);
        label2.setText("Gator Count: " + gatorList.size());
        label2.setFont(font);
        label3.setText("Water Change Date: ");
        label3.setFont(font);
        label4.setText("Water Temperature: ");
        label4.setFont(font);
        label5.setText("Feed Type: ");
        label5.setFont(font);
        label6.setText("Feed Amount (in lbs.): ");
        label6.setFont(font);
        label7.setText("Size Class: ");
        label7.setFont(font);
        label8.setText("Any additional comments: ");
        label8.setFont(font);
    }
}
