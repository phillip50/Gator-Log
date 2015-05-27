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
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

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
    private String[] cages;
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
    private int tempFromLowerBound;
    private int tempFromUpperBound;
    private int fromLowerBound;
    private int fromUpperBound;
    private int tempToLowerBound;
    private int tempToUpperBound;
    private String[] toCages;
    private int[] toUpperBounds;
    private int[] toLowerBounds;
    private int[] capacities;
    private int[] capacityCounters;
    private int toCounter; //number of to cages
    private String[] cagesAtCapacity;
    private int[] cagesAtCapacityAmount;
    private String[] cagesAtCapacityRange;
    private int cagesAtCapacityCounter;
    private boolean cageValid;
    private boolean hasDatabase;
    private boolean hasToCage;
    private boolean cageTaken;
    private File file;
    private Database db;
    private Table table;
    private File outputFile;
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
        capacities = new int[10];
        capacityCounters = new int[10];
        toCounter = 0;
        cagesAtCapacity = new String[10];
        cagesAtCapacityAmount = new int[10];
        cagesAtCapacityRange = new String[10];
        cagesAtCapacityCounter = 0;
        cageValid = false;
        cageTaken = false;
        hasDatabase = false;
        hasToCage = false;
        file = null;
        db = null;
        table = null;
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
        
        cages = new String[148];
        for (int i = 0; i < 99; i++)
        {
            int j = i + 1;
            cages[i] = "" + j;
        }
        for (int i = 0; i < 16; i++)
        {
            int j = i + 1;
            cages[i + 99] = "" + j + "A";
        }
        for (int i = 0; i < 33; i++)
        {
            int j = i + 1;
            cages[i + 99 + 16] = "" + j + "B";
        }
        
        label1 = new JLabel("");
        label2 = new JLabel("");
        label3 = new JLabel("");
        
        contentPane = getContentPane();
        bellyButtons = new JButton[32];
        for (int i = 15; i <= 46; i++)
        {
            JButton button = new JButton("" + i);
            button.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    int number = Integer.parseInt(((JButton) e.getSource()).getText());
                    for (int i = 15; i <= 46; i++)
                    {
                        bellyButtons[i - 15].setEnabled(true);
                    }
                    if (setUp)
                    {
                        if (count == 0)
                        {
                            tempFromLowerBound = number;
                            for (int i = 15; i < number; i++)
                            {
                                bellyButtons[i - 15].setEnabled(false);
                            }
                            count++;
                        }
                        else if (count == 1)
                        {
                            tempFromUpperBound = number;
                            count++;
                            cageValid = true;

                        }
                        else
                        {
                            count = 0;
                            tempFromLowerBound = 0;
                            tempFromUpperBound = 0;
                            cageValid = false;
                        }
                        label1.setText("Cage: " + cageList.getSelectedItem().toString() + ", Year: " + yearList.getSelectedItem().toString() + ", Belly Range: " + tempFromLowerBound + "-" + tempFromUpperBound);
                        confirm.setEnabled(cageValid);
                    }
                    else if (addTo)
                    {
                        if (count == 0)
                        {
                            tempToLowerBound = number;
                            count++;
                            for (int i = 15; i < number; i++)
                            {
                                bellyButtons[i - 15].setEnabled(false);
                            }
                        }
                        else if (count == 1)
                        {
                            tempToUpperBound = number;
                            count++;
                            cageValid = true;
                            for (int i = 0; i < toCounter; i++)
                            {
                                for (int j = tempToLowerBound; j <= tempToUpperBound; j++)
                                {
                                    if (j == toLowerBounds[i] || j == toUpperBounds[i])
                                    {
                                        cageValid = false;
                                    }
                                }
                                if (toLowerBounds[i] == tempToLowerBound && toUpperBounds[i] == tempToUpperBound)
                                {
                                    cageValid = true;
                                }
                            }   
                        }
                        else
                        {
                            count = 0;
                            tempToLowerBound = 0;
                            tempToUpperBound = 0;
                            cageValid = false;
                        }
                        cageTaken = false;
                        for (int i = 0; i < toCounter; i++)
                        {
                            if (cageList.getSelectedItem().toString().equals(toCages[i]))
                            {
                                cageTaken = true;
                                i = toCounter;
                            }
                        }
                        confirm.setEnabled(cageValid && isInteger(input.getText()) && Integer.parseInt(input.getText()) > 0 && !cageTaken);
                        label1.setText("Cage: " + cageList.getSelectedItem().toString() + ", Belly Range: " + tempToLowerBound + "-" + tempToUpperBound + ", Capacity: " + input.getText());
                    }
                    else // add
                    {
                        bellySize = number;
                        String toCage = "";
                        int index = 0;
                        for (int i = 0; i < toCounter; i++)
                        {
                            if (number >= toLowerBounds[i] && number <= toUpperBounds[i])
                            {
                                toCage = toCages[i];
                                capacityCounters[i]++;
                                index = i;
                                i = toCounter;
                            }
                        }
                        label1.setText("Last Entry: " + bellySize);
                        fromCount++;
                        try
                        {
                            table.addRow(0, fromCage, toCage, bellySize, currentDate);
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
                            capacities[index] = 0;
                            capacityCounters[index] = 0;
                            
                            toCages = stringShift(toCages);
                            toLowerBounds = intShift(toLowerBounds);
                            toUpperBounds = intShift(toUpperBounds);
                            capacities = intShift(capacities);
                            capacityCounters = intShift(capacityCounters);
                            
                            toCounter--;
                            if (toCounter == 0)
                            {
                                hasToCage = false;
                            }
                            
                            errorMessage = "Capacity reached on Cage " + toCage;
                            start = false;
                            setUp = false;
                            addTo = false;
                            removeTo = false;
                            add = false;
                            quit = false;
                            addComponents();
                        }
                    }
                }
            });
            bellyButtons[i - 15] = button;
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
                tempFromUpperBound = 0;
                tempFromLowerBound = 0;
                count = 0;
                addComponents();
            }
        });
        
        addToCage = new JButton("Add To Cage");
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
                tempToUpperBound = 0;
                tempToLowerBound = 0;
                count = 0;
                addComponents();
            }
        });
        
        removeToCage = new JButton("Remove To Cage");
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
                    if (cageValid)
                    {
                        fromLowerBound = tempFromLowerBound;
                        fromUpperBound = tempFromUpperBound;
                        fromCage = cageList.getSelectedItem().toString();
                        fromYear = yearList.getSelectedItem().toString();
                        
                        try
                        {
                            String name = "Cage" + fromCage + "_Birth" + fromYear + "_" + currentDate;
                            file = new File(name + ".accdb");
                            db = new DatabaseBuilder(file).setFileFormat(Database.FileFormat.V2000).create();
                            table = new TableBuilder("Database")
                                .addColumn(new ColumnBuilder("ID", DataType.LONG).setAutoNumber(true))
                                .addColumn(new ColumnBuilder("From", DataType.TEXT))
                                .addColumn(new ColumnBuilder("To", DataType.TEXT))
                                .addColumn(new ColumnBuilder("Belly", DataType.TEXT))
                                .addColumn(new ColumnBuilder("Date", DataType.TEXT))
                                .toTable(db);
                            hasDatabase = true;
                        }
                        catch (IOException e1)
                        {
                            
                        }
                    }
                    else
                    {
                        errorMessage = "Invalid Input";
                    }
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
                        errorMessage = "Cage taken";
                    }
                    else if (!cageValid)
                    {
                        errorMessage = "Invalid Input";
                    }
                    else if (!isInteger(input.getText()))
                    {
                        errorMessage = "Capacity must be a number";
                    }
                    else if (Integer.parseInt(input.getText()) <= 0)
                    {
                        errorMessage = "Capacity must be a number greater than 0";
                    }
                    else
                    {
                        toUpperBounds[toCounter] = tempToUpperBound;
                        toLowerBounds[toCounter] = tempToLowerBound;
                        toCages[toCounter] = cageList.getSelectedItem().toString();
                        capacities[toCounter] = Integer.parseInt(input.getText());
                        capacityCounters[toCounter] = 0;
                        hasToCage = true;
                        toCounter++;
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
        
        cageList = new JComboBox(cages);
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
                    label1.setText("Cage: " + cageList.getSelectedItem().toString() + ", Year: " + yearList.getSelectedItem().toString() + ", BellyRange: " + tempFromLowerBound + "-" + tempFromUpperBound);
                }
                else
                {
                    label1.setText("Cage: " + cageList.getSelectedItem().toString() + ", Belly Range: " + tempToLowerBound + "-" + tempToUpperBound + ", Capacity: " + input.getText());
                    cageTaken = false;
                    for (int i = 0; i < toCounter; i++)
                    {
                        if (cageList.getSelectedItem().toString().equals(toCages[i]))
                        {
                            cageTaken = true;
                            i = toCounter;
                        }
                    }
                    confirm.setEnabled(cageValid && isInteger(input.getText()) && Integer.parseInt(input.getText()) > 0 && !cageTaken);
                }
            }
            
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
            {
                if (setUp)
                {
                    label1.setText("Cage: " + cageList.getSelectedItem().toString() + ", Year: " + yearList.getSelectedItem().toString() + ", Belly Range: " + tempFromLowerBound + "-" + tempFromUpperBound);
                }
                else
                {
                    label1.setText("Cage: " + cageList.getSelectedItem().toString() + ", Belly Range: " + tempToLowerBound + "-" + tempToUpperBound + ", Capacity: " + input.getText());
                    cageTaken = false;
                    for (int i = 0; i < toCounter; i++)
                    {
                        if (cageList.getSelectedItem().toString().equals(toCages[i]))
                        {
                            cageTaken = true;
                            i = toCounter;
                        }
                    }
                    confirm.setEnabled(cageValid && isInteger(input.getText()) && Integer.parseInt(input.getText()) > 0 && !cageTaken);
                }
            }
        });
        
        yearList = new JComboBox(years);
        yearList.setEditable(false);
        yearList.addPopupMenuListener(new PopupMenuListener()
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
                label1.setText("Cage: " + cageList.getSelectedItem().toString() + ", Year: " + yearList.getSelectedItem().toString() + ", Belly Range: " + tempFromLowerBound + "-" + tempFromUpperBound);
            }
            
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
            {
                label1.setText("Cage: " + cageList.getSelectedItem().toString() + ", Year: " + yearList.getSelectedItem().toString() + ", Belly Range: " + tempFromLowerBound + "-" + tempFromUpperBound);
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
                label1.setText("Cage: " + cageList.getSelectedItem().toString() + ", Belly Range: " + tempToLowerBound + "-" + tempToUpperBound + ", Capacity: " + input.getText());
                confirm.setEnabled(cageValid && isInteger(input.getText()) && Integer.parseInt(input.getText()) > 0);
            }
        });
    }
    
    public void addComponents()
    {  
        contentPane.removeAll();
        input.setText("");
        cageList.setSelectedIndex(0);
        yearList.setSelectedIndex(0);      
        
        JPanel panel = new JPanel();
        
        if (start)
        {
            panel.setLayout(new FlowLayout());
            
            addToCage.setEnabled(hasDatabase);
            addEntry.setEnabled(hasDatabase && hasToCage);
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
            label1.setText("Cage: " + cageList.getSelectedItem().toString() + ", Year: " + yearList.getSelectedItem().toString() + ", Belly Range: " + tempFromLowerBound + "-" + tempFromUpperBound);
            label1.setFont(font1);
            label2.setText("Specify From Range");
            label2.setFont(font2);
            label3.setText("From Cage?");
            label3.setFont(font1);
            JLabel label4 = new JLabel("Birth Year");
            label4.setFont(font1);
            cageList.setPreferredSize(size);
            cageList.setFont(font1);
            yearList.setPreferredSize(size);
            yearList.setFont(font1);
            confirm.setPreferredSize(size);
            confirm.setFont(font1);
            
            confirm.setEnabled(cageValid && !cageTaken);
            
            cancel.setPreferredSize(size);
            cancel.setFont(font1);
            panel4.add(label1);
            panel5.add(label3);
            panel5.add(cageList);
            panel5.add(label4);
            panel5.add(yearList);
            panel4.add(cancel);
            panel4.add(confirm);
            panel6.add(label2);
            panel2.add(panel5, BorderLayout.NORTH);
            panel2.add(panel4, BorderLayout.CENTER);
            panel.add(panel6, BorderLayout.NORTH);
            panel.add(panel3, BorderLayout.CENTER);
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
            for (int i = 0; i < 32; i++)
            {
                bellyButtons[i].setPreferredSize(size);
                bellyButtons[i].setFont(font1);
                panel3.add(bellyButtons[i]);
            }
            label1.setText("Cage: " + cageList.getSelectedItem().toString() + ", Belly Range: " + tempToLowerBound + "-" + tempToUpperBound + ", Capacity: " + input.getText());
            label1.setFont(font1);
            label2.setText("Select Belly Range");
            label2.setFont(font2);
            cageList.setPreferredSize(size);
            cageList.setFont(font1);
            confirm.setPreferredSize(size);
            confirm.setFont(font1);
            confirm.setEnabled(false);
            cancel.setPreferredSize(size);
            cancel.setFont(font1);
            panel6.add(label2);
            panel4.add(label1);
            JLabel label4 = new JLabel("Cage: ");
            label4.setFont(font1);
            panel5.add(label4);
            panel5.add(cageList);
            JLabel label5 = new JLabel("Capacity: ");
            label5.setFont(font1);
            panel7.add(label5);
            input.setPreferredSize(size);
            input.setFont(font1);
            panel7.add(input);
            panel4.add(cancel);
            panel4.add(confirm);
            panel2.add(panel5, BorderLayout.NORTH);
            panel2.add(panel7, BorderLayout.CENTER);
            panel2.add(panel4, BorderLayout.SOUTH);
            panel.add(panel6, BorderLayout.NORTH);
            panel.add(panel3, BorderLayout.CENTER);
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
                
                JLabel label = new JLabel("Cage " + toCages[i] + ": " + toLowerBounds[i] + "-" + toUpperBounds[i] + ", Capacity: " + capacities[i]);
                label.setFont(font1);
                panel2.add(label);
                
                button = new JButton("Remove Cage " + toCages[i]);
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
                        capacities[index] = 0;
                        capacityCounters[index] = 0;
                            
                        toCages = stringShift(toCages);
                        toLowerBounds = intShift(toLowerBounds);
                        toUpperBounds = intShift(toUpperBounds);
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
            for (int i = 0; i < 32; i++)
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
            System.out.println("stuff");
            
            System.out.println("Done");
            try
            {
                outputFile = new File("Cage" + fromCage + "_Birth" + fromYear + "_" + currentDate + "_log.txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
                writer.write("From Pen: " + fromCage + "\r\n\tTotal: " + fromCount + "\r\n\tYear: " + fromYear + "\r\n\tSize: " + fromLowerBound + "-" + fromUpperBound + "\r\n");
                for (int i = 0; i < toCages.length; i++)
                {
                    if (cagesAtCapacity[i] != null)
                    {
                        writer.write("\r\n\r\nTo Pen: " + cagesAtCapacity[i] + "\r\n\tTransferred: " + cagesAtCapacityAmount[i] + "\r\n\tCurrent Size: " + cagesAtCapacityRange[i]);
                    }
                }
                for (int i = 0; i < toCages.length; i++)
                {
                    if (toCages[i] != null)
                    {
                        writer.write("\r\n\r\nTo Pen: " + toCages[i] + "\r\n\tTransferred: " + capacityCounters[i] + "\r\n\tCurrent Size: " + toLowerBounds[i] + "-" + toUpperBounds[i]);
                    }
                }
            
                int totalCount = 0;
                for (int i = 0; i < toCages.length; i++)
                {
                    if (toCages[i] != null)
                    {
                        totalCount = totalCount + capacityCounters[i];
                    }
                }
                for (int i = 0; i < toCages.length; i++)
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
