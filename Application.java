package test;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import com.healthmarketscience.jackcess.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Collections;
import javax.swing.event.*;
import java.util.ArrayList;

public class Application extends JFrame
{
    private static Application frame;
    private Container contentPane;
    private JButton[] bellyButtons;
    private JButton setUpDatabase;
    private JButton addToCage;
    private JButton removeToCage;
    private JButton addEntry;
    private JButton quitButton;
    private JComboBox cageList;
    private JTextField input;
    private ArrayList<String> cages;
    private JComboBox yearList;
    private String[] years;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private boolean start;
    private boolean setUp;
    private boolean addTo;
    private boolean removeTo;
    private boolean add;
    private boolean quit;
    private JButton confirm;
    private JButton cancel;
    private int count; //0, 1, 2
    private String fromCage;
    private int bellySize;
    private String fromYear;
    private int fromCount;
    private String fromClass;
    private int tempFromLowerBound;
    private int tempFromUpperBound;
    private int fromLowerBound;
    private int fromUpperBound;
    private int tempToLowerBound;
    private int tempToUpperBound;
    private String[] toCages;
    private int[] toUpperBounds;
    private int[] toLowerBounds;
    private String[] toClassSizes;
    private int[] capacities;
    private int[] capacityCounters;
    private int toCounter; //number of to cages
    private String[] cagesAtCapacity;
    private int[] cagesAtCapacityAmount;
    private String[] cagesAtCapacityRange;
    private int cagesAtCapacityCounter;
    private boolean cageValid;
    private boolean hasFrom;
    private boolean hasToCage;
    private boolean cageTaken;
    private File gatorFile;
    private Table gatorTable;
    private File outputFile;
    private File cageFile;
    private Table cageTable;
    private String currentDate;
    private Dimension screenSize;
    private double width;
    private double height;
    private Font font1;
    private Font font2;
    private String errorMessage;
    
    public Application()
    {
        super("Application");
        
        start = true;
        setUp = false;
        addTo = false;
        removeTo = false;
        add = false;
        quit = false;
        count = 0;
        fromCage = "";
        fromCount = 0;
        bellySize = 0;
        tempFromLowerBound = 0;
        tempFromUpperBound = 0;
        fromLowerBound = 0;
        fromUpperBound = 0;
        tempToLowerBound = 0;
        tempToUpperBound = 0;
        toCages = new String[10];
        toUpperBounds = new int[10];
        toLowerBounds = new int[10];
        toClassSizes = new String[10];
        capacities = new int[10];
        capacityCounters = new int[10];
        toCounter = 0;
        cagesAtCapacity = new String[10];
        cagesAtCapacityAmount = new int[10];
        cagesAtCapacityRange = new String[10];
        cagesAtCapacityCounter = 0;
        cageValid = false;
        cageTaken = false;
        hasFrom = false;
        hasToCage = false;
        gatorFile = null;
        gatorTable = null;
        cageFile = null;
        cageTable = null;
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        currentDate = dateFormat.format(date);     
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.getWidth();
        height = screenSize.getHeight();
        font1 = new Font("Arial", Font.PLAIN, 40);
        font2 = new Font("Arial", Font.PLAIN, 25); 
        years = new String[4];
        
        int year = Integer.parseInt(currentDate.substring(6));
        for (int i = 0; i < 4; i++)
        {
            int number = year - i;
            years[i] = "" + number;
        }
        
        cages = new ArrayList<String>();
        for (int i = 101; i <= 127; i++)
        {
            cages.add("" + i);
        }
        for (int i = 201; i <= 232; i++)
        {
            if (i == 227 || i == 232)
            {
                for (int j = 1; j <= 4; j++)
                {
                    cages.add("" + i + "." + j);
                }
            }
            else
            {
                cages.add("" + i);
            }
        }
        for (int i = 301; i <= 326; i++)
        {
            cages.add("" + i);
        }
        for (int i = 401; i <= 437; i++)
        {
            if (i == 410 || i == 411 || i == 420 || i == 421)
            {
                for (int j = 1; j <= 4; j++)
                {
                    cages.add("" + i + "." + j);
                }
            }
            else
            {
                cages.add("" + i);
            }
        }
        for (int i = 801; i <= 816; i++)
        {
            cages.add("" + i);
        }
        for (int i = 901; i <= 910; i++)
        {
            cages.add("" + i);
        }
        
        try
        {
            gatorFile = new File("AnimalDatabase.accdb");
            gatorTable = DatabaseBuilder.open(gatorFile).getTable("Database");
            cageFile = new File("CageDatabase.accdb");
            cageTable = DatabaseBuilder.open(cageFile).getTable("Database");
        }
        catch (IOException e1)
        {                   
        }
        
        label1 = new JLabel("");
        label2 = new JLabel("");
        label3 = new JLabel("");
        
        contentPane = getContentPane();
        bellyButtons = new JButton[33];
        for (int i = 14; i <= 46; i++)
        {
            JButton button;
            if (i == 14)
            {
                button = new JButton("Hatchling");
            }
            else
            {
                button = new JButton("" + i);
            }
            button.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    String entry = ((JButton) e.getSource()).getText();
                    int number = -1;
                    if (entry.equals("Hatchling"))
                    {  
                    }
                    else
                    {
                        number = Integer.parseInt(entry);
                    }
                    for (int i = 15; i <= 46; i++)
                    {
                        bellyButtons[i - 15].setEnabled(true);
                    }
                    String toCage = "";
                    int index = 0;
                    String classSize = "";
                    for (int i = 0; i < toCounter; i++)
                    {
                        try
                        {
                            IndexCursor cursor = CursorBuilder.createCursor(cageTable.getIndex("PenNumberIndex"));                            
                            cursor.beforeFirst();
                            cursor.findFirstRow(Collections.singletonMap("Pen Number", toCages[i]));
                            Row latestRow = cursor.getCurrentRow();
                            while (cursor.findNextRow(Collections.singletonMap("Pen Number", toCages[i])))
                            {
                                Row row = cursor.getCurrentRow();
                                if (row != null)
                                {
                                    latestRow = row;
                                }
                            }
                            classSize = latestRow.get("Size Class").toString();
                        }
                        catch (IOException e1)
                        {     
                        }
                        
                        if (classSize.equals("Family") || (number >= toLowerBounds[i] && number <= toUpperBounds[i]) || (entry.equals("Hatchling") && classSize.equals("Hatchling")))
                        {
                            toCage = toCages[i];
                            capacityCounters[i]++;
                            index = i;
                            i = toCounter;
                        }
                    }
                    label1.setText("Last Entry: " + entry);
                    fromCount++;
                    try
                    {
                        gatorTable.addRow(0, fromCage, toCage, entry, currentDate);
                    }
                    catch (IOException e1)
                    {    
                    }
                    if(capacities[index] == capacityCounters[index])
                    {
                        cagesAtCapacity[cagesAtCapacityCounter] = toCages[index];
                        cagesAtCapacityAmount[cagesAtCapacityCounter] = capacities[index];
                        cagesAtCapacityRange[cagesAtCapacityCounter] = toLowerBounds[index] + "-" + toUpperBounds[index];
                        cagesAtCapacityCounter++;
                        toCages[index] = null;
                        toLowerBounds[index] = 0;
                        toUpperBounds[index] = 0;
                        toClassSizes[index] = null;
                        capacities[index] = 0;
                        capacityCounters[index] = 0;
                            
                        toCages = stringShift(toCages);
                        toLowerBounds = intShift(toLowerBounds);
                        toUpperBounds = intShift(toUpperBounds);
                        toClassSizes = stringShift(toClassSizes);
                        capacities = intShift(capacities);
                        capacityCounters = intShift(capacityCounters);
                            
                        toCounter--;
                        if (toCounter == 0)
                        {
                            hasToCage = false;
                        }
                            
                        errorMessage = "Capacity reached on Pen " + toCage;
                        start = false;
                        setUp = false;
                        addTo = false;
                        removeTo = false;
                        add = false;
                        quit = false;
                        addComponents();
                    }
                }
            });
            bellyButtons[i - 14] = button;
        }
        
        setUpDatabase = new JButton("Set Up Database");
        setUpDatabase.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                start = false;
                setUp = true;
                addTo = false;
                removeTo = false;
                add = false;
                quit = false;
                addComponents();
            }
        });
        
        addToCage = new JButton("Add To Pen");
        addToCage.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                start = false;
                setUp = false;
                addTo = true;
                removeTo = false;
                add = false;
                quit = false;
                addComponents();
            }
        });
        
        removeToCage = new JButton("Remove To Pen");
        removeToCage.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                start = false;
                setUp = false;
                addTo = false;
                removeTo = true;
                add = false;
                quit = false;
                addComponents();
            }
        });
        
        addEntry = new JButton("Add Entry");
        addEntry.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                start = false;
                setUp = false;
                addTo = false;
                removeTo = false;
                add = true;
                quit = false;
                addComponents();
            }
        });
        
        quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                start = false;
                setUp = false;
                addTo = false;
                removeTo = false;
                add = false;
                quit = true;
                addComponents();
            }
        });
        
        cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                start = true;
                setUp = false;
                addTo = false;
                removeTo = false;
                add = false;
                quit = false;
                addComponents();
            }
        });
        
        confirm = new JButton("Confirm");
        confirm.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                errorMessage = "";
                
                if (setUp)
                {
                    fromCage = cageList.getSelectedItem().toString();
                        
                    String classSize = "";
                        
                    try
                    {
                        IndexCursor cursor = CursorBuilder.createCursor(cageTable.getIndex("PenNumberIndex"));                            
                        cursor.beforeFirst();
                        cursor.findFirstRow(Collections.singletonMap("Pen Number", fromCage));
                        Row latestRow = cursor.getCurrentRow();
                        while (cursor.findNextRow(Collections.singletonMap("Pen Number", fromCage)))
                        {
                            Row row = cursor.getCurrentRow();
                            if (row != null)
                            {
                                latestRow = row;
                            }
                        }
                        classSize = latestRow.get("Size Class").toString();
                    }
                    catch (IOException e1)
                    {      
                    }
                        
                    fromClass = classSize;
                    hasFrom = true;
                }
                else if (addTo)
                {
                    cageTaken = false;
                    for (int i = 0; i < toCounter; i++)
                    {
                        if (cageList.getSelectedItem().toString().equals(toCages[i]))
                        {
                            cageTaken = true;
                            i = toCounter;
                        }
                    }
                    if (cageTaken)
                    {
                        errorMessage = "Pen taken";
                    }
                    else
                    {
                        String pen = cageList.getSelectedItem().toString();
                        String classSize = "";
                        
                        try
                        {
                            IndexCursor cursor = CursorBuilder.createCursor(cageTable.getIndex("PenNumberIndex"));                            
                            cursor.beforeFirst();
                            cursor.findFirstRow(Collections.singletonMap("Pen Number", pen));
                            Row latestRow = cursor.getCurrentRow();
                            while (cursor.findNextRow(Collections.singletonMap("Pen Number", pen)))
                            {
                                Row row = cursor.getCurrentRow();
                                if (row != null)
                                {
                                    latestRow = row;
                                }
                            }
                            classSize = latestRow.get("Size Class").toString();
                        }
                        catch (IOException e1)
                        {      
                        }
                        if (classSize.equals("Empty"))
                        {
                            errorMessage = "Cannot transfer to designated empty pen";
                        }
                        else if (classSize.equals("Hatchling") || classSize.equals("Family"))
                        {
                            toCages[toCounter] = pen;
                            toLowerBounds[toCounter] = 0;
                            toUpperBounds[toCounter] = 0;
                            toClassSizes[toCounter] = classSize;
                            capacities[toCounter] = Integer.parseInt(input.getText());
                            capacityCounters[toCounter] = 0;
                            hasToCage = true;
                            toCounter++;
                        }
                        else if (classSize.equals("39+"))
                        {
                            toCages[toCounter] = pen;
                            toLowerBounds[toCounter] = 39;
                            toUpperBounds[toCounter] = 46;
                            toClassSizes[toCounter] = classSize;
                            capacities[toCounter] = Integer.parseInt(input.getText());
                            capacityCounters[toCounter] = 0;
                            hasToCage = true;
                            toCounter++;
                        }
                        else
                        {
                            int index = classSize.indexOf('-');
                            toCages[toCounter] = pen;
                            toLowerBounds[toCounter] = Integer.parseInt(classSize.substring(0, index));
                            toUpperBounds[toCounter] = Integer.parseInt(classSize.substring(index+1));
                            toClassSizes[toCounter] = classSize;
                            capacities[toCounter] = Integer.parseInt(input.getText());
                            capacityCounters[toCounter] = 0;
                            hasToCage = true;
                            toCounter++;
                        }
                    }   
                }
                if (!errorMessage.equals(""))
                {
                    start = false;
                }
                else
                {
                    start = true;
                }
                setUp = false;
                addTo = false;
                removeTo = false;
                add = false;
                quit = false;
                addComponents();
            }
        });
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double w = screenSize.getWidth();
        
        cageList = new JComboBox();
        for (int i = 0; i < cages.size(); i++)
        {
            cageList.addItem(cages.get(i));
        }
        cageList.setEditable(false);
        cageList.addPopupMenuListener(new PopupMenuListener()
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
                    Dimension scrollBarDim = new Dimension((int)(w / 48), scrollBar.getPreferredSize().height);
                    scrollBar.setPreferredSize(scrollBarDim);
                }
            }
            
            public void popupMenuCanceled(PopupMenuEvent e)
            {
                if (setUp)
                {
                }
                else
                {
                    cageTaken = false;
                    for (int i = 0; i < toCounter; i++)
                    {
                        if (cageList.getSelectedItem().toString().equals(toCages[i]))
                        {
                            cageTaken = true;
                            i = toCounter;
                        }
                    }
                    confirm.setEnabled(!cageTaken && isInteger(input.getText()) && Integer.parseInt(input.getText()) > 0);
                }
            }
            
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
            {
                if (setUp)
                {
                }
                else
                {
                    cageTaken = false;
                    for (int i = 0; i < toCounter; i++)
                    {
                        if (cageList.getSelectedItem().toString().equals(toCages[i]))
                        {
                            cageTaken = true;
                            i = toCounter;
                        }
                    }
                    confirm.setEnabled(!cageTaken && isInteger(input.getText()) && Integer.parseInt(input.getText()) > 0);
                }
            }
        });
        
        input = new JTextField(10);
        input.getDocument().addDocumentListener(new DocumentListener()
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
                confirm.setEnabled(!cageTaken && isInteger(input.getText()) && Integer.parseInt(input.getText()) > 0);
            }
        });
    }
    
    public void addComponents()
    {  
        contentPane.removeAll();
        cageList.setSelectedIndex(0);
        input.setText("");
        
        JPanel panel = new JPanel();
        
        if (start)
        {
            panel.setLayout(new FlowLayout());
            
            addToCage.setEnabled(hasFrom);
            addEntry.setEnabled(hasFrom && hasToCage);
            setUpDatabase.setFont(font2);
            addToCage.setFont(font2);
            removeToCage.setFont(font2);
            addEntry.setFont(font2);
            quitButton.setFont(font2);
            
            Dimension size = new Dimension((int)(width/6), (int)(height/4));
            
            setUpDatabase.setPreferredSize(size);
            addToCage.setPreferredSize(size);
            removeToCage.setPreferredSize(size);
            addEntry.setPreferredSize(size);
            quitButton.setPreferredSize(size);
            
            panel.add(addEntry);
            panel.add(addToCage);
            panel.add(setUpDatabase);
            panel.add(removeToCage);
            panel.add(quitButton);
        }
        else if (setUp)
        {
            Dimension size = new Dimension((int)(width/8), (int)(height/10));
            
            panel.setLayout(new BorderLayout());
            Panel panel2 = new Panel(new BorderLayout());
            Panel panel3 = new Panel(new FlowLayout());
            Panel panel4 = new Panel(new FlowLayout());
            Panel panel5 = new Panel(new FlowLayout());
            Panel panel6 = new Panel(new FlowLayout());
            for (int i = 0; i < 32; i++)
            {
                bellyButtons[i].setPreferredSize(size);
                bellyButtons[i].setFont(font1);
                panel3.add(bellyButtons[i]);
            }
            label3.setText("From Pen?");
            label3.setFont(font1);
            cageList.setPreferredSize(size);
            cageList.setFont(font1);
            confirm.setPreferredSize(size);
            confirm.setFont(font1);
            
            confirm.setEnabled(!cageTaken);
            
            cancel.setPreferredSize(size);
            cancel.setFont(font1);
            panel5.add(label3);
            panel5.add(cageList);
            panel4.add(cancel);
            panel4.add(confirm);
            panel2.add(panel5, BorderLayout.NORTH);
            panel2.add(panel4, BorderLayout.CENTER);
            panel.add(panel6, BorderLayout.NORTH);
            panel.add(panel2, BorderLayout.SOUTH);
        }
        else if (addTo)
        {
            Dimension size = new Dimension((int)(width/8), (int)(height/10));
            
            panel.setLayout(new BorderLayout());
            Panel panel2 = new Panel(new BorderLayout());
            Panel panel3 = new Panel(new FlowLayout());
            Panel panel4 = new Panel(new FlowLayout());
            Panel panel5 = new Panel(new FlowLayout());
            Panel panel6 = new Panel(new FlowLayout());
            Panel panel7 = new Panel(new FlowLayout());

            cageList.setPreferredSize(size);
            cageList.setFont(font1);
            confirm.setPreferredSize(size);
            confirm.setFont(font1);
            confirm.setEnabled(!cageTaken && isInteger(input.getText()) && Integer.parseInt(input.getText()) > 0);
            cancel.setPreferredSize(size);
            cancel.setFont(font1);
            input.setPreferredSize(size);
            input.setFont(font1);
            JLabel label4 = new JLabel("Pen: ");
            label4.setFont(font1);
            JLabel label5 = new JLabel("Capacity: ");
            label5.setFont(font1);
            panel5.add(label4);
            panel5.add(cageList);
            panel7.add(label5);
            panel7.add(input);
            panel4.add(cancel);
            panel4.add(confirm);
            panel2.add(panel5, BorderLayout.NORTH);
            panel2.add(panel7, BorderLayout.CENTER);
            panel2.add(panel4, BorderLayout.SOUTH);
            panel.add(panel2, BorderLayout.SOUTH);
        }
        else if(removeTo)
        {
            Dimension size = new Dimension((int)(width/8), (int)(height/10));
            
            panel.setLayout(new BorderLayout());
            Box box = Box.createVerticalBox();
            Panel panel2;
            Panel bottomPanel = new Panel(new FlowLayout());
            JButton button;
            
            for (int i = 0; i < toCounter; i++)
            {
                panel2 = new Panel(new FlowLayout());
                
                JLabel label = new JLabel("Pen " + toCages[i] + ": " + toClassSizes[i] + ", Capacity: " + capacities[i]);
                label.setFont(font1);
                panel2.add(label);
                
                button = new JButton("Remove Pen " + toCages[i]);
                button.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        String temp = ((JButton) e.getSource()).getText();
                        int index = temp.indexOf(' ');
                        int index2 = temp.indexOf(" ", index+1);
                        String cage = temp.substring(index2+1);
                        for (int i = 0; i < toCounter; i++)
                        {
                            if (cage.equals(toCages[i]))
                            {
                                index = i;
                                i = toCounter;
                            }
                        }
                        
                        toCages[index] = null;
                        toLowerBounds[index] = 0;
                        toUpperBounds[index] = 0;
                        toClassSizes[index] = null;
                        capacities[index] = 0;
                        capacityCounters[index] = 0;
                            
                        toCages = stringShift(toCages);
                        toLowerBounds = intShift(toLowerBounds);
                        toUpperBounds = intShift(toUpperBounds);
                        toClassSizes= stringShift(toClassSizes);
                        capacities = intShift(capacities);
                        capacityCounters = intShift(capacityCounters);
                            
                        toCounter--;
                        if (toCounter == 0)
                        {
                            hasToCage = false;
                        }
                        addComponents();
                    }
                });
                button.setPreferredSize(size);
                button.setFont(font2);
                panel2.add(button);
                
                box.add(panel2);
            }
            
            cancel.setPreferredSize(size);
            cancel.setFont(font1);
            bottomPanel.add(cancel);
            
            panel.add(box, BorderLayout.CENTER);
            panel.add(bottomPanel, BorderLayout.SOUTH);
        }
        else if (add)
        {
            Dimension size = new Dimension((int)(width/7), (int)(height/9));
            
            panel.setLayout(new BorderLayout());
            Panel panel2 = new Panel(new FlowLayout());
            JLabel label = new JLabel("Select Belly Size");
            label.setFont(font2);
            panel2.add(label);
            Panel panel3 = new Panel(new FlowLayout());
            for (int i = 0; i < 33; i++)
            {
                bellyButtons[i].setPreferredSize(size);
                bellyButtons[i].setFont(font1);
                panel3.add(bellyButtons[i]);
            }
            label1.setText("Last Entry: " + bellySize);
            label1.setFont(font1);
            Panel panel4 = new Panel(new BorderLayout());
            Panel panel5 = new Panel(new FlowLayout());
            Panel panel6 = new Panel(new FlowLayout());
            panel5.add(label1);
            panel6.add(cancel);
            panel4.add(panel5, BorderLayout.NORTH);
            panel4.add(panel6, BorderLayout.SOUTH);
            panel.add(panel2, BorderLayout.NORTH);
            panel.add(panel3, BorderLayout.CENTER);
            panel.add(panel4, BorderLayout.SOUTH);
        }
        else if (quit)
        {
            try
            {
                outputFile = new File("Pen" + fromCage + "_Birth" + fromYear + "_" + currentDate + "_log.txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
                writer.write("From Pen: " + fromCage + "\r\n\tTotal: " + fromCount + "\r\n\tYear: " + fromYear + "\r\n\tClass: " + fromClass + "\r\n");
                for (int i = 0; i < cagesAtCapacityCounter; i++)
                {
                    if (cagesAtCapacity[i] != null)
                    {
                        writer.write("\r\n\r\nTo Pen: " + cagesAtCapacity[i] + "\r\n\tTransferred: " + cagesAtCapacityAmount[i] + "\r\n\tCurrent Size: " + cagesAtCapacityRange[i]);
                    }
                }
                for (int i = 0; i < toCounter; i++)
                {
                    if (toCages[i] != null)
                    {
                        writer.write("\r\n\r\nTo Pen: " + toCages[i] + "\r\n\tTransferred: " + capacityCounters[i] + "\r\n\tCurrent Size: " + toLowerBounds[i] + "-" + toUpperBounds[i]);
                    }
                }
            
                int totalCount = 0;
                for (int i = 0; i < toCounter; i++)
                {
                    if (toCages[i] != null)
                    {
                        totalCount = totalCount + capacityCounters[i];
                    }
                }
                for (int i = 0; i < cagesAtCapacityCounter; i++)
                {
                    if (cagesAtCapacity[i] != null)
                    {
                        totalCount = totalCount + cagesAtCapacityAmount[i];
                    }
                }
                if (fromCount - totalCount != 0)
                {
                    writer.write("\r\n\r\nUnspecified: " + (fromCount - totalCount));
                }
            
                writer.close();
                
                IndexCursor cursor = CursorBuilder.createCursor(cageTable.getIndex("PenNumberIndex"));                            
                cursor.beforeFirst();
                if (hasFrom)
                {
                    cursor.findFirstRow(Collections.singletonMap("Pen Number", fromCage));
                    Row fromRow = cursor.getCurrentRow();
                    while (cursor.findNextRow(Collections.singletonMap("Pen Number", fromCage)))
                    {
                        Row row = cursor.getCurrentRow();
                        if (row != null)
                        {
                            fromRow = row;
                        }
                    }
                    cageTable.addRow(0, fromCage, fromRow.get("Pen Type"), fromRow.get("Square Footage"), Integer.parseInt(fromRow.get("Gator Count").toString()) - fromCount, fromRow.get("Water Change Date"), fromRow.get("Water Temperature"), fromRow.get("Feed Type"), fromRow.get("Feed Amount"), fromRow.get("Size Class"), "Transferred Gators");
                }                          
                cursor.beforeFirst();
                for (int i = 0; i < toCounter; i++)
                {
                    cursor.findFirstRow(Collections.singletonMap("Pen Number", toCages[i]));
                    Row toRow = cursor.getCurrentRow();
                    while (cursor.findNextRow(Collections.singletonMap("Pen Number", toCages[i])))
                    {
                        Row row = cursor.getCurrentRow();
                        if (row != null)
                        {
                            toRow = row;
                        }
                    }
                    cageTable.addRow(0, toCages[i], toRow.get("Pen Type"), toRow.get("Square Footage"), Integer.parseInt(toRow.get("Gator Count").toString()) + capacityCounters[i], toRow.get("Water Change Date"), toRow.get("Water Temperature"), toRow.get("Feed Type"), toRow.get("Feed Amount"), toRow.get("Size Class"), "Transferred Gators");                         
                    cursor.beforeFirst();
                }
                
                for (int i = 0; i < cagesAtCapacityCounter; i++)
                {
                    cursor.findFirstRow(Collections.singletonMap("Pen Number", cagesAtCapacity[i]));
                    Row toRow = cursor.getCurrentRow();
                    while (cursor.findNextRow(Collections.singletonMap("Pen Number", cagesAtCapacity[i])))
                    {
                        Row row = cursor.getCurrentRow();
                        if (row != null)
                        {
                            toRow = row;
                        }
                    }                      
                    cageTable.addRow(0, cagesAtCapacity[i], toRow.get("Pen Type"), toRow.get("Square Footage"), Integer.parseInt(toRow.get("Gator Count").toString()) + cagesAtCapacityAmount[i], toRow.get("Water Change Date"), toRow.get("Water Temperature"), toRow.get("Feed Type"), toRow.get("Feed Amount"), toRow.get("Size Class"), "Transferred Gators");
                    cursor.beforeFirst();
                }
            }
            catch (IOException e)
            {
                
            }
            
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
        else
        {
            Dimension size = new Dimension((int)(width/8), (int)(height/10));
            JFrame frame2 = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Panel tempPanel = new Panel(new FlowLayout());
            Panel tempPanel2 = new Panel(new FlowLayout());
            Panel tempPanel3 = new Panel(new BorderLayout());
            JLabel tempLabel = new JLabel("Warning! " + errorMessage);
            tempLabel.setFont(font1);
            JButton tempButton = new JButton("Back");
            tempButton.setPreferredSize(size);
            tempButton.setFont(font1);
            tempButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    start = true;
                    setUp = false;
                    addTo = false;
                    removeTo = false;
                    add = false;
                    quit = false;
                    addComponents();
                    frame2.dispose();
                }
            });
            
            tempPanel.add(tempLabel);
            tempPanel2.add(tempButton);
            tempPanel3.add(tempPanel, BorderLayout.NORTH);
            tempPanel3.add(tempPanel2, BorderLayout.SOUTH);
            frame2.add(tempPanel3);
            frame2.pack();
            frame2.setLocationRelativeTo(null);
            frame2.setVisible(true);   
        }
        
        if (start || setUp || addTo || removeTo || add || quit)
        {
            contentPane.add(panel);
            validate();
            setVisible(true);
        }
    }
    
    public static void createAndShowGUI()
    {
        frame = new Application();           
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        double length = rect.getHeight();
        double width = rect.getWidth();
        Dimension screenSize = new Dimension((int)width, (int)length - 50);
        frame.getContentPane().setPreferredSize(screenSize);
        frame.addComponents();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);   
    }
    
    public static void main(String[] args)
    {
        createAndShowGUI();
    }
    
    public static String[] stringShift(String[] input)
    {
        int j = 0;
        int k = 0;
        String[] temp = new String[10];
                        
        while (j < 10)
        {
            if (input[j] != null)
            {
                temp[k] = input[j];
                k++;
                }
            j++;
        }
        return temp;
    }
    
    public static int[] intShift(int[] input)
    {
        int j = 0;
        int k = 0;
        int[] temp = new int[10];
                        
        while (j < 10)
        {
            if (input[j] != 0)
            {
                temp[k] = input[j];
                k++;
            }
            j++;
        }
        return temp;
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
