package test;

import javax.swing.*; 
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import com.healthmarketscience.jackcess.*;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.event.*;

public class Application extends JFrame implements SerialPortEventListener
{
    private static Application frame;
    private Container contentPane;
    private JButton[] numbers;
    private JButton setUpDatabase;
    private JButton addToCage;
    private JButton removeToCage;
    private JButton addEntry;
    private JButton back;
    private JButton addNewGator;
    private JButton transferGator;
    private JButton killGator;
    private JButton quitButton;
    private JComboBox cageList;
    private JTextField input;
    private JTextField location;
    private JTextField condition;
    private JTextField collectionDate;
    private JComboBox gender;
    private JComboBox umbilical;
    private ArrayList<String> cages;
    private String[] years;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel killLabel;
    private boolean start;
    private boolean newGatorPage1;
    private boolean newGatorPage2;
    private boolean killPage1;
    private boolean killPage2;
    private boolean transferStart;
    private boolean setUp;
    private boolean addTo;
    private boolean removeTo;
    private boolean addPage1;
    private boolean addPage2;
    private boolean addPage3;
    private boolean addPage4;
    private boolean addPage5;
    private boolean quit;
    private JButton confirm;
    private JButton cancel;
    private String fromCage;
    private String toCage;
    private int toCageIndex;
    private int bellySize;
    private int length;
    private int weight;
    private boolean hasPreviousEntry;
    private Row previousRow;
    private int previousBellySize;
    private int previousLength;
    private int previousWeight;
    private String fromYear;
    private int fromCount;
    private String fromClass;
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
    private SerialPort serialPort;
    private BufferedReader serialInput;
    private OutputStream serialOutput;
    private String tag;
    private JButton didVaccinate;
    private JButton didNotVaccinate;
    private boolean isVaccinated;
    private JButton didFormula;
    private JButton didNotFormula;
    private boolean isFormula;
    private JTextField comments;
    private String vaccinationDate;
    private String formulaDate;
    
    public Application()
    {
        super("Application");
        
        start = true;
        newGatorPage1 = false;
        newGatorPage2 = false;
        transferStart = false;
        killPage1 = false;
        killPage2 = false;
        setUp = false;
        addTo = false;
        removeTo = false;
        addPage1 = false;
        addPage2 = false;
        quit = false;
        fromCage = "";
        fromCount = 0;
        toCage = "";
        bellySize = 0;
        length = 0;
        weight = 0;
        hasPreviousEntry = false;
        previousBellySize = 0;
        previousLength = 0;
        previousWeight = 0;
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
        cageTaken = false;
        hasFrom = true;
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
        tag = "";
        isVaccinated = false;
        isFormula = false;
        
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
        killLabel = new JLabel("");
        
        contentPane = getContentPane();
        numbers = new JButton[201];
        for (int i = 0; i <= 200; i++)
        {
            JButton button = new JButton("" + i);
            button.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    String entry = ((JButton) e.getSource()).getText();
                    int number = Integer.parseInt(entry);
                    if (addPage2)
                    {
                        bellySize = number;
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
                                toCageIndex = i;
                                i = toCounter;
                            }
                        }
                        /*
                        
                    */
                        addPage2 = false;
                        addPage3 = true;
                        addComponents();
                    }
                    else if (addPage3)
                    {
                        length = number;
                        addPage3 = false;
                        addPage4 = true;
                        addComponents();
                    }
                    else if (addPage4)
                    {
                        weight = number;
                        addPage4 = false;
                        addPage5 = true;
                        addComponents();
                    }
                }
            });
            numbers[i] = button;
        }
        
        addNewGator = new JButton("Add New Gator");
        addNewGator.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                start = false;
                newGatorPage1 = true;
                newGatorPage2 = false;
                killPage1 = false;
                killPage2 = false;
                transferStart = false;
                setUp = false;
                addTo = false;
                removeTo = false;
                addPage1 = false;
                addPage2 = false;
                quit = false;
                addComponents();
            }
        });
        
        transferGator = new JButton("Transfer Gator");
        transferGator.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                start = false;
                transferStart = true;
                newGatorPage1 = false;
                newGatorPage2 = false;
                killPage1 = false;
                killPage2 = false;
                setUp = false;
                addTo = false;
                removeTo = false;
                addPage1 = false;
                addPage2 = false;
                quit = false;
                addComponents();
            }
        });
        
        killGator = new JButton("Kill Gator");
        killGator.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                start = false;
                newGatorPage1 = false;
                newGatorPage2 = false;
                transferStart = false;
                killPage1 = true;
                killPage2 = false;
                setUp = false;
                addTo = false;
                removeTo = false;
                addPage1 = false;
                addPage2 = false;
                quit = false;
                killLabel.setText("");
                addComponents();
            }
        });
        
        quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                start = false;
                transferStart = false;
                newGatorPage1 = false;
                newGatorPage2 = false;
                killPage1 = false;
                killPage2 = false;
                setUp = false;
                addTo = false;
                removeTo = false;
                addPage1 = false;
                addPage2 = false;
                quit = true;
                addComponents();
            }
        });
        
        setUpDatabase = new JButton("Set Up Database");
        setUpDatabase.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                start = false;
                transferStart = false;
                newGatorPage1 = false;
                newGatorPage2 = false;
                killPage1 = false;
                killPage2 = false;
                setUp = true;
                addTo = false;
                removeTo = false;
                addPage1 = false;
                addPage2 = false;
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
                transferStart = false;
                newGatorPage1 = false;
                newGatorPage2 = false;
                killPage1 = false;
                killPage2 = false;
                setUp = false;
                addTo = true;
                removeTo = false;
                addPage1 = false;
                addPage2 = false;
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
                transferStart = false;
                newGatorPage1 = false;
                newGatorPage2 = false;
                killPage1 = false;
                killPage2 = false;
                setUp = false;
                addTo = false;
                removeTo = true;
                addPage1 = false;
                addPage2 = false;
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
                transferStart = false;
                newGatorPage1 = false;
                newGatorPage2 = false;
                killPage1 = false;
                killPage2 = false;
                setUp = false;
                addTo = false;
                removeTo = false;
                addPage1 = true;
                addPage2 = false;
                quit = false;
                addComponents();
            }
        });
        
        back = new JButton("Back");
        back.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                start = true;
                transferStart = false;
                newGatorPage1 = false;
                newGatorPage2 = false;
                killPage1 = false;
                killPage2 = false;
                setUp = false;
                addTo = false;
                removeTo = false;
                addPage1 = false;
                addPage2 = false;
                quit = false;
                addComponents();
            }
        });
        
        cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                start = false;
                transferStart = true;
                newGatorPage1 = false;
                newGatorPage2 = false;
                killPage1 = false;
                killPage2 = false;
                setUp = false;
                addTo = false;
                removeTo = false;
                addPage1 = false;
                addPage2 = false;
                addPage3 = false;
                addPage4 = false;
                addPage5 = false;
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
                    /*
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
                        
                    fromClass = classSize;*/
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
                else if (addPage5)
                {
                    fromCount++;
                    try
                    {
                        if (previousRow != null)
                        {
                            gatorTable.addRow(0, tag, previousRow.get("Egg Nest Location"), previousRow.get("Egg Nest Condition"), previousRow.get("Egg Collection Date"), previousRow.get("Hatch Year"), previousRow.get("Gender"), previousRow.get("Umbilical"), currentDate, fromCage, toCage, bellySize, length, weight, isFormula, "", isVaccinated, comments.getText());
                        }
                        else
                        {
                            gatorTable.addRow(0, tag, "", "", "", "", "", "", currentDate, fromCage, toCage, bellySize, length, weight, isFormula, "", isVaccinated, comments.getText());
                        }
                        for(Map<String,Object> row : CursorBuilder.createCursor(gatorTable.getIndex("IDIndex")))
                        {
 
                        }
                    }
                    catch (IOException e1)
                    {    
                    }
                    if (toCageIndex != -1)
                    {
                        capacityCounters[toCageIndex]++;
                    }
                    if(toCageIndex != -1 && capacities[toCageIndex] == capacityCounters[toCageIndex])
                    {
                        cagesAtCapacity[cagesAtCapacityCounter] = toCages[toCageIndex];
                        cagesAtCapacityAmount[cagesAtCapacityCounter] = capacities[toCageIndex];
                        cagesAtCapacityRange[cagesAtCapacityCounter] = toLowerBounds[toCageIndex] + "-" + toUpperBounds[toCageIndex];
                        cagesAtCapacityCounter++;
                        toCages[toCageIndex] = null;
                        toLowerBounds[toCageIndex] = 0;
                        toUpperBounds[toCageIndex] = 0;
                        toClassSizes[toCageIndex] = null;
                        capacities[toCageIndex] = 0;
                        capacityCounters[toCageIndex] = 0;
                            
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
                        killPage1 = false;
                        killPage2 = false;
                        newGatorPage1 = false;
                        newGatorPage2 = false;
                        transferStart = false;
                        setUp = false;
                        addTo = false;
                        removeTo = false;
                        addPage1 = false;
                        addPage2 = false;
                        quit = false;
                        addComponents();
                    }
                    
                    toCage = "";
                    toCageIndex = -1;
                }
                if (!errorMessage.equals(""))
                {
                    transferStart = false;
                    addPage1 = false;
                }
                else if (addPage5)
                {
                    transferStart = false;
                    addPage1 = true;
                }
                else
                {
                    transferStart = true;
                    addPage1 = false;
                }
                start = false;
                newGatorPage1 = false;
                newGatorPage2 = false;
                killPage1 = false;
                killPage2 = false;
                setUp = false;
                addTo = false;
                removeTo = false;
                addPage2 = false;
                addPage3 = false;
                addPage4 = false;
                addPage5 = false;
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
        
        location = new JTextField(10);
        location.getDocument().addDocumentListener(new DocumentListener()
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
                
            }
        });
        
        condition = new JTextField(10);
        condition.getDocument().addDocumentListener(new DocumentListener()
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
                
            }
        });
        
        collectionDate = new JTextField(10);
        collectionDate.getDocument().addDocumentListener(new DocumentListener()
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
                
            }
        });
        
        didVaccinate = new JButton("Yes");
        didVaccinate.setEnabled(true);
        didVaccinate.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                isVaccinated = true;
                didVaccinate.setEnabled(false);
                didNotVaccinate.setEnabled(true);
            }
        });
        
        didNotVaccinate = new JButton("No");
        didNotVaccinate.setEnabled(false);
        didNotVaccinate.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                isVaccinated = false;
                didVaccinate.setEnabled(true);
                didNotVaccinate.setEnabled(false);
            }
        });
        
        didFormula = new JButton("Yes");
        didFormula.setEnabled(true);
        didFormula.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                isFormula = true;
                didFormula.setEnabled(false);
                didNotFormula.setEnabled(true);
            }
        });
        
        didNotFormula = new JButton("No");
        didNotFormula.setEnabled(false);
        didNotFormula.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                isFormula = false;
                didFormula.setEnabled(true);
                didNotFormula.setEnabled(false);
            }
        });
        
        String[] genderList = {"Female", "Male"};
        gender = new JComboBox(genderList);
        
        String[] umbilicalList = {"Y", "N"};
        umbilical = new JComboBox(umbilicalList);
        
        comments = new JTextField(10);
        comments.setFont(font1);
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
            
            Dimension size = new Dimension((int)(width/6), (int)(height/4));
            
            addNewGator.setPreferredSize(size);
            transferGator.setPreferredSize(size);
            killGator.setPreferredSize(size);
            quitButton.setPreferredSize(size);
            addNewGator.setFont(font2);
            transferGator.setFont(font2);
            killGator.setFont(font2);
            quitButton.setFont(font2);
            
            panel.add(addNewGator);
            panel.add(transferGator);
            panel.add(killGator);
            panel.add(quitButton);
        }
        else if (newGatorPage1)
        {
            panel.setLayout(new BorderLayout());
            
            Dimension size = new Dimension((int)(width/8), (int)(height/10));           
            
            Panel panel2 = new Panel(new FlowLayout());
            Panel panel3 = new Panel(new FlowLayout());
            JLabel tempLabel = new JLabel("Scan Microchip");
            tempLabel.setFont(font1);
            back.setPreferredSize(size);
            back.setFont(font1);
            panel2.add(back);
            panel3.add(tempLabel);
            panel.add(panel3, BorderLayout.NORTH);
            panel.add(panel2, BorderLayout.SOUTH);
        }
        else if (newGatorPage2)
        {
            panel.setLayout(new GridBagLayout());
            
            Dimension size = new Dimension((int)(width/8), (int)(height/10));
            
            JLabel gatorLabel = new JLabel("Gator: " + tag);
            JLabel location = new JLabel("Egg Nest Location: ");
            JLabel condition = new JLabel("Egg Nest Condition: ");
            JLabel collectionDate = new JLabel("Egg Condition Date: ");
            JLabel hatchYear1 = new JLabel("Hatch Year: ");
            JLabel hatchYear2 = new JLabel(currentDate.substring(6));
            JLabel genderLabel = new JLabel("Gender: ");
            JLabel umbilicalLabel = new JLabel("Umbilical: ");
            JLabel commentsLabel = new JLabel("Additional Comments: ");
        }
        else if (killPage1)
        {
            panel.setLayout(new BorderLayout());
            
            Dimension size = new Dimension((int)(width/8), (int)(height/10));           
            
            Panel panel2 = new Panel(new FlowLayout());
            Panel panel3 = new Panel(new FlowLayout());
            Panel panel4 = new Panel(new FlowLayout());
            JLabel tempLabel = new JLabel("Scan Microchip");
            tempLabel.setFont(font1);
            killLabel.setFont(font1);
            back.setPreferredSize(size);
            back.setFont(font1);
            panel2.add(back);
            panel3.add(tempLabel);
            panel4.add(killLabel);
            panel.add(panel3, BorderLayout.NORTH);
            panel.add(panel4, BorderLayout.CENTER);
            panel.add(panel2, BorderLayout.SOUTH);
        }
        else if (killPage2)
        {
            
        }
        else if (transferStart)
        {
            panel.setLayout(new FlowLayout());
            
            addToCage.setEnabled(hasFrom);
            addEntry.setEnabled(hasFrom && hasToCage);
            setUpDatabase.setFont(font2);
            addToCage.setFont(font2);
            removeToCage.setFont(font2);
            addEntry.setFont(font2);
            back.setFont(font2);
            
            Dimension size = new Dimension((int)(width/6), (int)(height/4));
            
            setUpDatabase.setPreferredSize(size);
            addToCage.setPreferredSize(size);
            removeToCage.setPreferredSize(size);
            addEntry.setPreferredSize(size);
            back.setPreferredSize(size);
            
            panel.add(addEntry);
            panel.add(addToCage);
            //panel.add(setUpDatabase);
            panel.add(removeToCage);
            panel.add(back);
        }
        else if (setUp)
        {
            Dimension size = new Dimension((int)(width/8), (int)(height/10));
            
            panel.setLayout(new BorderLayout());
            Panel panel2 = new Panel(new BorderLayout());
            Panel panel4 = new Panel(new FlowLayout());
            Panel panel5 = new Panel(new FlowLayout());
            Panel panel6 = new Panel(new FlowLayout());

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
        else if (addPage1)
        {
            panel.setLayout(new BorderLayout());
            
            Dimension size = new Dimension((int)(width/8), (int)(height/10));
            
            isVaccinated = false;
            isFormula = false;
            didVaccinate.setEnabled(true);
            didNotVaccinate.setEnabled(false);
            didFormula.setEnabled(true);
            didNotFormula.setEnabled(false);
            comments.setText("");
            
            Panel panel2 = new Panel();
            Panel panel3 = new Panel();
            JLabel tempLabel = new JLabel("Scan Microchip");
            tempLabel.setFont(font1);
            cancel.setPreferredSize(size);
            panel2.add(tempLabel);
            panel3.add(cancel);
            panel.add(panel2, BorderLayout.NORTH);
            panel.add(panel3, BorderLayout.SOUTH);
        }
        else if (addPage2)
        {
            Dimension size = new Dimension((int)(width/7), (int)(height/9));
            
            panel.setLayout(new BorderLayout());
            Panel panel2 = new Panel(new FlowLayout());
            JLabel label = new JLabel("Select Belly Size");
            label.setFont(font2);
            panel2.add(label);
            Panel panel3 = new Panel(new FlowLayout());
            if (hasPreviousEntry)
            {
                for (int i = previousBellySize - 5; i < previousBellySize + 20; i++)
                {
                    numbers[i].setPreferredSize(size);
                    numbers[i].setFont(font1);
                    panel3.add(numbers[i]);
                }
            }
            else
            {
                for (int i = 15; i < 30; i++)
                {
                    numbers[i].setPreferredSize(size);
                    numbers[i].setFont(font1);
                    panel3.add(numbers[i]);
                }
            }
            Panel panel6 = new Panel(new FlowLayout());
            cancel.setPreferredSize(size);
            panel6.add(cancel);
            panel.add(panel2, BorderLayout.NORTH);
            panel.add(panel3, BorderLayout.CENTER);
            panel.add(panel6, BorderLayout.SOUTH);
        }
        else if (addPage3)
        {
            Dimension size = new Dimension((int)(width/7), (int)(height/9));
            
            panel.setLayout(new BorderLayout());
            Panel panel2 = new Panel(new FlowLayout());
            JLabel label = new JLabel("Select Length");
            label.setFont(font2);
            panel2.add(label);
            Panel panel3 = new Panel(new FlowLayout());
            if (hasPreviousEntry)
            {
                for (int i = previousLength - 5; i < previousLength + 20; i++)
                {
                    numbers[i].setPreferredSize(size);
                    numbers[i].setFont(font1);
                    panel3.add(numbers[i]);
                }
            }
            else
            {
                for (int i = 15; i < 30; i++)
                {
                    numbers[i].setPreferredSize(size);
                    numbers[i].setFont(font1);
                    panel3.add(numbers[i]);
                }
            }
            Panel panel6 = new Panel(new FlowLayout());
            cancel.setPreferredSize(size);
            panel6.add(cancel);
            panel.add(panel2, BorderLayout.NORTH);
            panel.add(panel3, BorderLayout.CENTER);
            panel.add(panel6, BorderLayout.SOUTH);
        }
        else if (addPage4)
        {
            Dimension size = new Dimension((int)(width/7), (int)(height/9));
            
            panel.setLayout(new BorderLayout());
            Panel panel2 = new Panel(new FlowLayout());
            JLabel label = new JLabel("Select Weight");
            label.setFont(font2);
            panel2.add(label);
            Panel panel3 = new Panel(new FlowLayout());
            if (hasPreviousEntry)
            {
                for (int i = previousWeight - 5; i < previousWeight + 20; i++)
                {
                    numbers[i].setPreferredSize(size);
                    numbers[i].setFont(font1);
                    panel3.add(numbers[i]);
                }
            }
            else
            {
                for (int i = 15; i < 30; i++)
                {
                    numbers[i].setPreferredSize(size);
                    numbers[i].setFont(font1);
                    panel3.add(numbers[i]);
                }
            }
            Panel panel6 = new Panel(new FlowLayout());
            cancel.setPreferredSize(size);
            panel6.add(cancel);
            panel.add(panel2, BorderLayout.NORTH);
            panel.add(panel3, BorderLayout.CENTER);
            panel.add(panel6, BorderLayout.SOUTH);
        }
        else if (addPage5)
        {
            panel.setLayout(new GridBagLayout());
            GridBagConstraints cLeft = new GridBagConstraints();
            cLeft.insets = new Insets(10, 30, 10, 30);
            cLeft.anchor = GridBagConstraints.LINE_START;
            GridBagConstraints cRight = new GridBagConstraints();
            cRight.insets = new Insets(10, 30, 10, 30);
            cRight.anchor = GridBagConstraints.LINE_END;
            Dimension size = new Dimension((int)(width/7), (int)(height/9));
            Dimension size2 = new Dimension((int)(width/17), (int)(height/16));
            confirm.setPreferredSize(size);
            confirm.setFont(font1);
            confirm.setEnabled(true);
            cancel.setPreferredSize(size);
            cancel.setFont(font1);
            didVaccinate.setPreferredSize(size2);
            didVaccinate.setFont(font1);
            didNotVaccinate.setPreferredSize(size2);
            didNotVaccinate.setFont(font1);
            didFormula.setPreferredSize(size2);
            didFormula.setFont(font1);
            didNotFormula.setPreferredSize(size2);
            didNotFormula.setFont(font1);
            
            JLabel tempLabel1 = new JLabel("Gator ID: ");
            tempLabel1.setFont(font1);
            cRight.gridx = 0;
            cRight.gridy = 0;
            panel.add(tempLabel1, cRight);

            JLabel tempLabel2 = new JLabel(tag);
            tempLabel2.setFont(font1);
            cLeft.gridx = 1;
            cLeft.gridy = 0;
            panel.add(tempLabel2, cLeft);
            
            JLabel tempLabel3 = new JLabel("From Pen: ");
            tempLabel3.setFont(font1);
            cRight.gridx = 0;
            cRight.gridy = 1;
            panel.add(tempLabel3, cRight);
            
            JLabel tempLabel4 = new JLabel("" + fromCage);
            tempLabel4.setFont(font1);
            cLeft.gridx = 1;
            cLeft.gridy = 1;
            panel.add(tempLabel4, cLeft);
            
            JLabel tempLabel5 = new JLabel("To Pen: ");
            tempLabel5.setFont(font1);
            cRight.gridx = 0;
            cRight.gridy = 2;
            panel.add(tempLabel5, cRight);
            
            JLabel tempLabel6 = new JLabel("" + toCage);
            tempLabel6.setFont(font1);
            cLeft.gridx = 1;
            cLeft.gridy = 2;
            panel.add(tempLabel6, cLeft);

            JLabel tempLabel7 = new JLabel("Belly Size: ");
            tempLabel7.setFont(font1);
            cRight.gridx = 0;
            cRight.gridy = 3;
            panel.add(tempLabel7, cRight);
            
            JLabel tempLabel8 = new JLabel("" + bellySize);
            tempLabel8.setFont(font1);
            cLeft.gridx = 1;
            cLeft.gridy = 3;
            panel.add(tempLabel8, cLeft);
            
            JLabel tempLabel9 = new JLabel("Length: ");
            tempLabel9.setFont(font1);
            cRight.gridx = 0;
            cRight.gridy = 4;
            panel.add(tempLabel9, cRight);
            
            JLabel tempLabel10 = new JLabel("" + length);
            tempLabel10.setFont(font1);
            cLeft.gridx = 1;
            cLeft.gridy = 4;
            panel.add(tempLabel10, cLeft);
            
            JLabel tempLabel11 = new JLabel("Weight: ");
            tempLabel11.setFont(font1);
            cRight.gridx = 0;
            cRight.gridy = 5;
            panel.add(tempLabel11, cRight);
            
            JLabel tempLabel12 = new JLabel("" + weight);
            tempLabel12.setFont(font1);
            cLeft.gridx = 1;
            cLeft.gridy = 5;
            panel.add(tempLabel12, cLeft);
            
            JLabel tempLabel13 = new JLabel("Vaccinated? ");
            tempLabel13.setFont(font1);
            cRight.gridx = 0;
            cRight.gridy = 6;
            panel.add(tempLabel13, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 6;
            panel.add(didNotVaccinate, cLeft);
            cLeft.anchor = GridBagConstraints.LINE_END;
            panel.add(didVaccinate, cLeft);
            cLeft.anchor = GridBagConstraints.LINE_START;
            
            JLabel tempLabel14 = new JLabel("Did formula? ");
            tempLabel14.setFont(font1);
            cRight.gridx = 0;
            cRight.gridy = 7;
            panel.add(tempLabel14, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 7;
            panel.add(didNotFormula, cLeft);
            cLeft.anchor = GridBagConstraints.LINE_END;
            panel.add(didFormula, cLeft);
            cLeft.anchor = GridBagConstraints.LINE_START;
   
            JLabel tempLabel15 = new JLabel("Additional comments: ");
            tempLabel15.setFont(font1);
            cRight.gridx = 0;
            cRight.gridy = 8;
            panel.add(tempLabel15, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 8;
            panel.add(comments, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 9;
            panel.add(cancel, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 9;
            panel.add(confirm, cLeft);
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
                /*
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
                */
            }
            catch (IOException e)
            {
                
            }
            
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            System.exit(0);
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
                    start = false;
                    transferStart = true;
                    newGatorPage1 = false;
                    newGatorPage2 = false;
                    killPage1 = false;
                    killPage2 = false;
                    setUp = false;
                    addTo = false;
                    removeTo = false;
                    addPage1 = false;
                    addPage2 = false;
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
        
        if (start || transferStart || newGatorPage1 || killPage1 || setUp || addTo || removeTo || addPage1 || addPage2 || addPage3 || addPage4 || addPage5 || quit)
        {
            contentPane.add(panel);
            validate();
            setVisible(true);
        }
    }
    
    public static void createAndShowGUI()
    {
        frame = new Application();     
        frame.initialize();
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
    
    public void initialize()
    {  
	CommPortIdentifier portId = null;
        try
        {
            portId = CommPortIdentifier.getPortIdentifier("COM3");
        }
        catch (NoSuchPortException e)
        {
        }

	if (portId == null)
        {
            System.out.println("Could not find COM port.");
	    return;
	}

	try
        {
            serialPort = (SerialPort) portId.open(this.getClass().getName(), 2000);

            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            serialInput = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            serialOutput = serialPort.getOutputStream();

            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
        }
    }
    
    public synchronized void close()
    {
	if (serialPort != null)
        {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }
    
    public synchronized void serialEvent(SerialPortEvent oEvent)
    {
        String temp = "";
	if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        {
            try
            {
                temp = serialInput.readLine();
                int index = temp.indexOf('.');
                tag = temp.substring(0, index);
                if (addPage1)
                {
                    IndexCursor cursor = CursorBuilder.createCursor(gatorTable.getIndex("TagIndex"));
                    cursor.beforeFirst();
                    Row latestRow = null;
                    while (cursor.findNextRow(Collections.singletonMap("Tag Number", tag)))
                    {
                        Row row = cursor.getCurrentRow();
                        if (row != null)
                        {
                            latestRow = row;
                        }
                    }
                    if (latestRow != null)
                    {
                        previousBellySize = Integer.parseInt(latestRow.get("Belly Size").toString());
                        previousLength = Integer.parseInt(latestRow.get("Length").toString());
                        previousWeight = Integer.parseInt(latestRow.get("Weight").toString());
                        previousRow = latestRow;
                        fromCage = latestRow.get("To").toString();
                        hasPreviousEntry = true;
                    }
                    else
                    {
                        hasPreviousEntry = false;
                    }

                    
                    System.out.println(hasPreviousEntry);
                    System.out.println(previousBellySize);
                    System.out.println(previousLength);
                    System.out.println(previousWeight);
                    System.out.println();
                    
                    addPage1 = false;
                    addPage2 = true;
                    addComponents();
                }
                else if (killPage1)
                {
                    killLabel.setText("Last killed: " + tag);
                    addComponents();
                }
            }
            catch (Exception e)
            {
                System.err.println(e.toString());
            }
        }
    }
}
