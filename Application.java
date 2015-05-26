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
    private File file;
    private Database db;
    private Table table;
    private String currentDate;
    
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
        hasDatabase = false;
        hasToCage = false;
        file = null;
        db = null;
        table = null;
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        currentDate = dateFormat.format(date);     
        
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
                    if (setUp)
                    {
                        if (count == 0)
                        {
                            tempFromLowerBound = number;
                            count++;
                        }
                        else if (count == 1)
                        {
                            tempFromUpperBound = number;
                            count++;
                            if (tempFromUpperBound > tempFromLowerBound)
                            {
                                cageValid = true;
                            }
                        }
                        else
                        {
                            count = 0;
                            tempFromLowerBound = 0;
                            tempFromUpperBound = 0;
                            cageValid = false;
                        }
                    }
                    else if (addTo)
                    {
                        if (count == 0)
                        {
                            tempToLowerBound = number;
                            count++;
                        }
                        else if (count == 1)
                        {
                            tempToUpperBound = number;
                            count++;
                            if (tempToUpperBound > tempToLowerBound)
                            {
                                cageValid = true;
                            }
                            if (cageValid)
                            {
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
                        }
                        else
                        {
                            count = 0;
                            tempToLowerBound = 0;
                            tempToUpperBound = 0;
                            cageValid = false;
                        }
                    }
                    else // add
                    {
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
                        try
                        {
                            table.addRow(0, fromCage, toCage, number, currentDate);
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
                        }
                    }
                    addComponents();
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
                if (setUp)
                {
                    if (cageValid)
                    {
                        fromLowerBound = tempFromLowerBound;
                        fromUpperBound = tempFromUpperBound;
                        fromCage = cageList.getSelectedItem().toString();
                        
                        try
                        {
                            String name = "Cage" + fromCage + "_" + currentDate;
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
                }
                else if (addTo)
                {
                    if (cageValid)
                    {
                        toUpperBounds[toCounter] = tempToUpperBound;
                        toLowerBounds[toCounter] = tempToLowerBound;
                        toCages[toCounter] = cageList.getSelectedItem().toString();
                        capacities[toCounter] = Integer.parseInt(input.getText());
                        capacityCounters[toCounter] = 0;
                        toCounter++;
                    }   
                }
                    
                start = true;
                setUp = false;
                addTo = false;
                removeTo = false;
                add = false;
                quit = false;
                addComponents();
            }
        });
        
        cageList = new JComboBox(cages);
        cageList.setEditable(false);
        /*
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
                    Dimension scrollBarDim = new Dimension((int)(width / 48), scrollBar.getPreferredSize().height);
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
        */
        
        input = new JTextField(20);
    }
    
    public void addComponents()
    {  
        contentPane.removeAll();
        
        System.out.println("From: " + fromCage + " " + fromLowerBound + "-" + fromUpperBound);
        for (int i = 0; i < toCounter; i++)
        {
            System.out.println("To: " + toCages[i] + " " + toLowerBounds[i] + "-" + toUpperBounds[i]);
        }
        System.out.println();
        
        JPanel panel = new JPanel();
        
        if (start)
        {
            panel.setLayout(new FlowLayout());
            panel.add(setUpDatabase);
            panel.add(addToCage);
            panel.add(removeToCage);
            panel.add(addEntry);
            panel.add(quitButton);
        }
        else if (setUp)
        {
            panel.setLayout(new FlowLayout());
            Panel panel2 = new Panel(new BorderLayout());
            Panel panel3 = new Panel(new FlowLayout());
            Panel panel4 = new Panel(new FlowLayout());
            Panel panel5 = new Panel(new FlowLayout());
            for (int i = 0; i < 32; i++)
            {
                panel3.add(bellyButtons[i]);
            }
            JLabel label = new JLabel(tempFromLowerBound + "-" + tempFromUpperBound);
            panel5.add(label);
            panel5.add(cageList);
            panel4.add(confirm);
            panel4.add(cancel);
            panel2.add(panel3, BorderLayout.NORTH);
            panel2.add(panel5, BorderLayout.CENTER);
            panel2.add(panel4, BorderLayout.SOUTH);
            panel.add(panel2);
        }
        else if (addTo)
        {
            panel.setLayout(new FlowLayout());
            Panel panel2 = new Panel(new BorderLayout());
            Panel panel3 = new Panel(new FlowLayout());
            Panel panel4 = new Panel(new FlowLayout());
            Panel panel5 = new Panel(new FlowLayout());
            for (int i = 0; i < 32; i++)
            {
                panel3.add(bellyButtons[i]);
            }
            JLabel label = new JLabel(tempToLowerBound + "-" + tempToUpperBound);
            panel4.add(label);
            panel5.add(new JLabel("Cage: "));
            panel5.add(cageList);
            panel5.add(new JLabel("Capacity: "));
            panel5.add(input);
            panel4.add(confirm);
            panel4.add(cancel);
            panel2.add(panel3, BorderLayout.NORTH);
            panel2.add(panel5, BorderLayout.CENTER);
            panel2.add(panel4, BorderLayout.SOUTH);
            panel.add(panel2);
        }
        else if(removeTo)
        {
            panel.setLayout(new BorderLayout());
            Box box = Box.createVerticalBox();
            Panel panel2;
            Panel bottomPanel = new Panel(new FlowLayout());
            JButton button;
            
            for (int i = 0; i < toCounter; i++)
            {
                panel2 = new Panel(new FlowLayout());
                
                JLabel label = new JLabel("Cage " + toCages[i] + ": " + toLowerBounds[i] + "-" + toUpperBounds[i] + ", Capacity: " + capacities[i]);
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
                        addComponents();
                    }
                });
                panel2.add(button);
                
                box.add(panel2);
            }
            
            bottomPanel.add(cancel);
            
            panel.add(box, BorderLayout.CENTER);
            panel.add(bottomPanel, BorderLayout.SOUTH);
        }
        else if (add)
        {
            panel.setLayout(new FlowLayout());
            Panel panel2 = new Panel(new BorderLayout());
            Panel panel3 = new Panel(new FlowLayout());
            for (int i = 0; i < 32; i++)
            {
                panel3.add(bellyButtons[i]);
            }
            panel2.add(panel3, BorderLayout.NORTH);
            panel2.add(cancel, BorderLayout.SOUTH);
            panel.add(panel2);
        }
        else //quit
        {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
        
        contentPane.add(panel);
        validate();
        setVisible(true);
    }
    
    public static void createAndShowGUI()
    {
        frame = new Application();           
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize( Toolkit.getDefaultToolkit().getScreenSize());
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
}
