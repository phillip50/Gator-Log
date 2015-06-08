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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

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
    private JButton confirm;
    private JButton cancel;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private Dimension screenSize;
    private boolean feedAmountValid;
    private Dimension size;
    private double width;
    private double height;
    private Font font;
    
    public ModifyWindow(Row inputRow)
    {
        super("View Pen");
        row = inputRow;
        contentPane = getContentPane();
        tabbedPanel = new JTabbedPane();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        feedAmountValid = true;
        
        width = screenSize.getWidth();
        height = screenSize.getHeight();
        font = new Font("Arial", Font.PLAIN, 25); 
        size = new Dimension((int)(width/6), (int)(height/4));
        
        try
        {
            cageFile = new File("CageDatabase.accdb");
            cageTable = DatabaseBuilder.open(cageFile).getTable("Database");
        }
        catch (IOException e1)
        {
                            
        }
        
        label1 = new JLabel("Pen: " + row.get("Pen Number"));
        label1.setFont(font);
        label2 = new JLabel("Gator Count: " + row.get("Gator Count"));
        label2.setFont(font);
        label3 = new JLabel("Water Change Date: ");
        label3.setFont(font);
        label4 = new JLabel("Water Temperature: ");
        label4.setFont(font);
        label5 = new JLabel("Feed Type: ");
        label5.setFont(font);
        label6 = new JLabel("Feed Amount (in lbs.): ");
        label6.setFont(font);
        label7 = new JLabel("Size Class: ");
        label7.setFont(font);
        label8 = new JLabel("Any additional comments: ");
        label8.setFont(font);
        
        doChange = new JButton("Yes");
        doChange.setEnabled(true);
        doChange.setPreferredSize(size);
        doChange.setFont(font);
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
        doNotChange.setPreferredSize(size);
        doNotChange.setFont(font);
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
        temperatures.setPrototypeDisplayValue("Any Additional Commen");
        temperatures.setFont(font);
        temperatures.setSelectedItem(row.get("Water Temperature"));
        temperatures.addPopupMenuListener(new PopupMenuListener()
        {
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
            
            public void popupMenuCanceled(PopupMenuEvent e)
            {
                
            }
            
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
            {
                
            }
        });
        
        String[] feedList = {"(R) - Regular", "(H) - Hatchling", "(I) - Intermediate"};
        feeds = new JComboBox(feedList);
        feeds.setEditable(false);
        feeds.setPrototypeDisplayValue("Any Additional Commen");
        feeds.setPreferredSize(size);
        feeds.setFont(font);
        if (row.get("Feed Type").toString().equals("R"))
        {
            feeds.setSelectedItem("(R) - Regular");
        }
        else if (row.get("Feed Type").toString().equals("H"))
        {
            feeds.setSelectedItem("(H) - Hatchling");
        }
        else
        {
            feeds.setSelectedItem("(I) - Intermediate");
        }
        
        amount = new JTextField(10);
        amount.setFont(font);
        amount.setText(row.get("Feed Amount").toString());
        amount.getDocument().addDocumentListener(new DocumentListener()
        {
            public void changedUpdate(DocumentEvent e)
            {
                warn();
            }
            public void removeUpdate(DocumentEvent e)
            {
                warn();
            }
            public void insertUpdate(DocumentEvent e)
            {
                warn();
            }

            public void warn()
            {
                feedAmountValid = isInteger(amount.getText()) && Integer.parseInt(amount.getText()) >= 0;
                confirm.setEnabled(feedAmountValid);
            }
        });
        
        String[] classList = {"Empty", "Hatchling", "Family", "15-18", "19-23", "24-28", "29-33", "34-36", "37-38", "39+"};
        classes = new JComboBox(classList);
        classes.setEditable(false);
        classes.setPrototypeDisplayValue("Any Additional Commen");
        classes.setFont(font);
        classes.setSelectedItem(row.get("Size Class"));
        classes.addPopupMenuListener(new PopupMenuListener()
        {
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
            
            public void popupMenuCanceled(PopupMenuEvent e)
            {
                
            }
            
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
            {
                
            }
        });
        
        comments = new JTextField(10);
        comments.setFont(font);

        confirm = new JButton("Confirm");
        confirm.setEnabled(true);
        confirm.setPreferredSize(size);
        confirm.setFont(font);
        confirm.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {                
                try
                {
                    cageTable.addRow(0, row.get("Pen Number"), row.get("Pen Type"), row.get("Square Footage"), row.get("Gator Count"), waterChangeDate, temperatures.getSelectedItem().toString(), feeds.getSelectedItem().toString(), amount.getText(), classes.getSelectedItem().toString(), comments.getText());
                }
                catch (IOException e1)
                {
                    
                }
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        
        cancel = new JButton("Cancel");
        cancel.setEnabled(true);
        cancel.setPreferredSize(size);
        cancel.setFont(font);
        cancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }
    
    public void addComponents()
    {
        JComponent modifyPanel = new JPanel();
        modifyPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 30, 10, 30);
        c.anchor = GridBagConstraints.LINE_END;
        
        c.gridx = 0;
        c.gridy = 0;
        modifyPanel.add(label1, c);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 1;
        c.gridy = 0;
        modifyPanel.add(label2, c);
        
        c.insets = new Insets(50, 30, 10, 30);
        
        c.gridx = 0;
        c.gridy = 3;
        modifyPanel.add(label3, c);
        c.gridx = 1;
        c.gridy = 3;
        modifyPanel.add(doNotChange, c);
        c.anchor = GridBagConstraints.CENTER;
        modifyPanel.add(doChange, c);
        
        c.insets = new Insets(10, 30, 10, 30);
        
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 4;
        modifyPanel.add(label4, c);
        c.gridx = 1;
        c.gridy = 4;
        modifyPanel.add(temperatures, c);
        
        c.gridx = 0;
        c.gridy = 5;
        modifyPanel.add(label5, c);
        c.gridx = 1;
        c.gridy = 5;
        modifyPanel.add(feeds, c);
        
        c.gridx = 0;
        c.gridy = 6;
        modifyPanel.add(label6, c);
        c.gridx = 1;
        c.gridy = 6;
        c.fill = GridBagConstraints.HORIZONTAL;
        modifyPanel.add(amount, c);
        
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 7;
        modifyPanel.add(label7, c);
        c.gridx = 1;
        c.gridy = 7;
        modifyPanel.add(classes, c);
        
        c.gridx = 0;
        c.gridy = 8;
        modifyPanel.add(label8, c);
        c.gridx = 1;
        c.gridy = 8;
        c.fill = GridBagConstraints.BOTH;
        modifyPanel.add(comments, c);
        
        c.insets = new Insets(50, 30, 10, 30);
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 0;
        c.gridy = 9;
        modifyPanel.add(confirm, c);
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 1;
        c.gridy = 9;
        modifyPanel.add(cancel, c);
        
        JComponent viewPanel = new JPanel();

        
        tabbedPanel.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Modify Pen</body></html>", modifyPanel);
        tabbedPanel.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>View Gators</body></html>", viewPanel);
        
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
}
