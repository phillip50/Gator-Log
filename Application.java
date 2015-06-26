/**
 * Gator Application
 * Allows new gators to be placed in pens, existing gators to be transferred between pens, and gators to be harvested
 * All events are reflected in a single database
 * 
 * @Phillip Dingler [phil50@ufl.edu]
 */
//TODO: comment
package test;

import javax.swing.*; 
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import com.healthmarketscience.jackcess.*;
import gnu.io.*;
import java.io.*;
import java.text.*;

public class Application extends JFrame implements SerialPortEventListener
{
        //"this" object
    private static Application frame;
    
        //Content pane that every conponent is place on
    private final Container contentPane;
    
        //Database files
    private File gatorFile;
    private Table gatorTable;
    private File cageFile;
    private Table cageTable;
    
        //The current day when the application is running
    private String currentDate;
    
        //Array that stores the last 4 years
    private String[] years;
    
        //current monitor's screen size, along with its width and height, to be used when sizing the components
        //The fonts are also used when creating the components
    private Dimension screenSize;
    private double width;
    private double height;
    private Font font1;
    private Font font2;
    
        //Ports for reading the microchips
    private SerialPort serialPort;
    private BufferedReader serialInput;
    
        //List of numbers when recording Belly Size, Length and Width
    private final JButton[] numbers;
    
        //List of all pens on the farm
    private java.util.List<String> cages;
    
        //The value read in by the serial port and used to determine which gator was read
    private String tag;
    
        //When transferring a gator, this value is a copy of the last entry in the gator database
    private Row previousRow;
    
        //Determines whether the gator has been vaccinated and given special formula
    private boolean isVaccinated;
    private boolean isFormula;
    
    
        //Date of last vaccination and special formula
        //if the above booleans are false, it is the date recorded in the gator's previous row
        //if true, it is a user-entered value
    private String vaccinatedDate;
    private String formulaDate;
    
        //When transferring gators, a "to" pen needs to be selected
        //toCages refers to the selected pens, and their class size and ranges are stored
        //Capacity is the amount of gators the pen can hold, with capacityCounters referring to how many have been transferred to that pen
        //Each pen's information is stored at the same position in the array, which is reflected by the toCounter value
        //When a pen reaches capacity, it is removed from the list and added to the cagesAtCapacity array, along wiht its corresponding information
    private String[] toCages;
    private int[] toUpperBounds;
    private int[] toLowerBounds;
    private String[] toClassSizes;
    private int[] capacities;
    private int[] capacityCounters;
    private int toCounter;
    private String[] cagesAtCapacity;
    private int[] cagesAtCapacityAmount;
    private String[] cagesAtCapacityRange;
    private int cagesAtCapacityCounter;
    
        //Boolean checks when adding a "to" pen to the above arrays
        //if there is atleast 1 "to" pen, hasToCage is true
        //if the same pen has already been added to the array, cageTaken is false
    private boolean hasToCage;
    private boolean cageTaken;
    
        //Stores which pen the currently selected gator is being transferred to, along with its index in the arrays
    private String toCage;
    private int toCageIndex;
    
        //Measured values of the currently selected gator
    private int bellySize;
    private String length;
    private String weight;
    
        //When measuring values, it is possible to not enter a length or width
        //if that is the case, the corresponding boolean value is set true
    private boolean skipLength;
    private boolean skipWeight;
    
        //When an error has been encountered, display this message
    private String errorMessage; 
    
        //Components in the frame
    private final JButton skip;
    private final JButton addToCage;
    private final JButton removeToCage;
    private final JButton addEntry;
    private final JButton back;
    private final JButton addNewGator;
    private final JButton transferGator;
    private final JButton harvestGator;
    private final JButton quitButton;
    private final JComboBox cageList;
    private final JTextField input;
    private final JTextField location;
    private final JTextField condition;
    private final JTextField collectionDate;
    private final JTextField experimentalCode;
    private final JComboBox gender;
    private final JComboBox umbilical;
    private final JButton confirm;
    private final JButton cancel;    
    private final JButton didVaccinate;
    private final JTextField vaccinateField;
    private final JButton didFormula;
    private final JTextField formulaField;
    private final JTextField comments;
    
        //Boolean values to determine which components to display
    private boolean start;
    private boolean newGatorPage1;
    private boolean newGatorPage2;
    private boolean harvestPage1;
    private boolean harvestPage2;
    private boolean harvestPage3;
    private boolean harvestPage4;
    private boolean harvestPage5;
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
    
    public Application()
    {
        super("Application");
        
        start = true;
        newGatorPage1 = false;
        newGatorPage2 = false;
        transferStart = false;
        harvestPage1 = false;
        harvestPage2 = false;
        harvestPage3 = false;
        harvestPage4 = false;
        harvestPage5 = false;
        setUp = false;
        addTo = false;
        removeTo = false;
        addPage1 = false;
        addPage2 = false;
        addPage3 = false;
        addPage4 = false;
        addPage5 = false;
        quit = false;
        toCage = "";
        bellySize = 0;
        length = "";
        weight = "";
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
        vaccinatedDate = "";
        formulaDate = "";
        isFormula = false;
        skipLength = false;
        skipWeight = false;
               
        int year = Integer.parseInt(currentDate.substring(6));
        for (int i = 0; i < 4; i++)
        {
            int number = year - i;
            years[i] = "" + number;
        }
        
        cages = new ArrayList<>();
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
        
        contentPane = getContentPane();
        numbers = new JButton[201];
        
        skip = new JButton("Skip Recording");      
        addNewGator = new JButton("Add New Gator");    
        transferGator = new JButton("Transfer Gator");      
        harvestGator = new JButton("Harvest Gator");       
        quitButton = new JButton("Quit");      
        addToCage = new JButton("Add To Pen");      
        removeToCage = new JButton("Remove To Pen");       
        addEntry = new JButton("Add Entry");            
        back = new JButton("Back");       
        cancel = new JButton("Cancel");      
        confirm = new JButton("Confirm");
        didVaccinate = new JButton("Yes");            
        vaccinateField = new JTextField(10);
        didFormula = new JButton("Yes");     
        formulaField = new JTextField(10);
        
        vaccinateField.setEnabled(false);
        formulaField.setEnabled(false);
        
        cageList = new JComboBox(cages.toArray());
        cageList.setEditable(false);
        
        input = new JTextField(10);      
        location = new JTextField(10);       
        condition = new JTextField(10);      
        collectionDate = new JTextField(10);       
        experimentalCode = new JTextField(10);
        comments = new JTextField(10);
        
        input.setFont(font1);
        location.setFont(font1);
        condition.setFont(font1);
        collectionDate.setFont(font1);
        experimentalCode.setFont(font1);
        comments.setFont(font1);
        
        String[] genderList = {"Female", "Male"};
        String[] umbilicalList = {"Y", "N"};
        
        gender = new JComboBox(genderList);
        umbilical = new JComboBox(umbilicalList);
        
        gender.setEditable(false);
        umbilical.setEditable(false);
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
            harvestGator.setPreferredSize(size);
            quitButton.setPreferredSize(size);
            addNewGator.setFont(font2);
            transferGator.setFont(font2);
            harvestGator.setFont(font2);
            quitButton.setFont(font2);
            
            panel.add(addNewGator);
            panel.add(transferGator);
            panel.add(harvestGator);
            panel.add(quitButton);
        }
        else if (newGatorPage1)
        {
            cageList.setSelectedIndex(0);
            gender.setSelectedIndex(0);
            umbilical.setSelectedIndex(0);
            location.setText("");
            condition.setText("");
            collectionDate.setText("");
            comments.setText("");
            confirm.setEnabled(true);
            
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
            GridBagConstraints cLeft = new GridBagConstraints();
            cLeft.insets = new Insets(10, 60, 10, 60);
            cLeft.anchor = GridBagConstraints.LINE_START;
            cLeft.fill = GridBagConstraints.BOTH;
            GridBagConstraints cRight = new GridBagConstraints();
            cRight.insets = new Insets(10, 60, 10, 60);
            cRight.anchor = GridBagConstraints.LINE_END;
            
            Dimension size = new Dimension((int)(width/8), (int)(height/10));
            
            JLabel gatorLabel1 = new JLabel("Gator: ");
            gatorLabel1.setFont(font1);
            JLabel gatorLabel2 = new JLabel(tag);
            gatorLabel2.setFont(font1);
            JLabel locationLabel = new JLabel("Egg Nest Location: ");
            locationLabel.setFont(font1);
            JLabel conditionLabel = new JLabel("Egg Nest Condition: ");
            conditionLabel.setFont(font1);
            JLabel collectionDateLabel = new JLabel("Egg Condition Date: ");
            collectionDateLabel.setFont(font1);
            JLabel hatchYear1 = new JLabel("Hatch Year: ");
            hatchYear1.setFont(font1);
            JLabel hatchYear2 = new JLabel(currentDate.substring(6));
            hatchYear2.setFont(font1);
            JLabel genderLabel = new JLabel("Gender: ");
            genderLabel.setFont(font1);
            JLabel umbilicalLabel = new JLabel("Umbilical: ");
            umbilicalLabel.setFont(font1);
            JLabel penLabel = new JLabel("To Pen: ");
            penLabel.setFont(font1);
            JLabel commentsLabel = new JLabel("Additional Comments: ");
            commentsLabel.setFont(font1);
            
            confirm.setPreferredSize(size);
            confirm.setFont(font1);
            back.setPreferredSize(size);
            back.setFont(font1);
            cageList.setFont(font1);
            gender.setFont(font1);
            umbilical.setFont(font1);
            
            cRight.gridx = 0;
            cRight.gridy = 0;
            panel.add(gatorLabel1, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 0;
            panel.add(gatorLabel2, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 1;
            panel.add(locationLabel, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 1;
            panel.add(location, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 2;
            panel.add(conditionLabel, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 2;
            panel.add(condition, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 3;
            panel.add(collectionDateLabel, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 3;
            panel.add(collectionDate, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 4;
            panel.add(hatchYear1, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 4;
            panel.add(hatchYear2, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 5;
            panel.add(genderLabel, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 5;
            panel.add(gender, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 6;
            panel.add(umbilicalLabel, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 6;
            panel.add(umbilical, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 7;
            panel.add(penLabel, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 7;
            panel.add(cageList, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 8;
            panel.add(commentsLabel, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 8;
            panel.add(comments, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 9;
            panel.add(back, cRight);
            
            cLeft.fill = GridBagConstraints.NONE;
            cLeft.gridx = 1;
            cLeft.gridy = 9;
            panel.add(confirm, cLeft);
        }
        else if (harvestPage1)
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
        else if (harvestPage5)
        {
            panel.setLayout(new GridBagLayout());
            GridBagConstraints cRight = new GridBagConstraints();
            cRight.insets = new Insets(10, 30, 10, 30);
            cRight.anchor = GridBagConstraints.LINE_START;
            GridBagConstraints cLeft = new GridBagConstraints();
            cLeft.insets = new Insets(10, 30, 10, 30);
            cLeft.anchor = GridBagConstraints.LINE_END;
            Dimension size = new Dimension((int)(width/7), (int)(height/9));
            confirm.setPreferredSize(size);
            confirm.setFont(font1);
            confirm.setEnabled(true);
            cancel.setPreferredSize(size);
            cancel.setFont(font1);
            
            JLabel tempLabel1 = new JLabel("Gator ID: ");
            tempLabel1.setFont(font1);
            cLeft.gridx = 0;
            cLeft.gridy = 0;
            panel.add(tempLabel1, cLeft);

            JLabel tempLabel2 = new JLabel(tag);
            tempLabel2.setFont(font1);
            cRight.gridx = 1;
            cRight.gridy = 0;
            panel.add(tempLabel2, cRight);
            
            JLabel tempLabel3 = new JLabel("From Pen: ");
            tempLabel3.setFont(font1);
            cLeft.gridx = 0;
            cLeft.gridy = 1;
            panel.add(tempLabel3, cLeft);
            
            JLabel tempLabel4 = new JLabel("" + previousRow.get("To").toString());
            tempLabel4.setFont(font1);
            cRight.gridx = 1;
            cRight.gridy = 1;
            panel.add(tempLabel4, cRight);

            JLabel tempLabel7 = new JLabel("Belly Size: ");
            tempLabel7.setFont(font1);
            cLeft.gridx = 0;
            cLeft.gridy = 2;
            panel.add(tempLabel7, cLeft);
            
            JLabel tempLabel8 = new JLabel("" + bellySize);
            tempLabel8.setFont(font1);
            cRight.gridx = 1;
            cRight.gridy = 2;
            panel.add(tempLabel8, cRight);
            
            JLabel tempLabel9 = new JLabel("Length: ");
            tempLabel9.setFont(font1);
            cLeft.gridx = 0;
            cLeft.gridy = 3;
            panel.add(tempLabel9, cLeft);
            
            JLabel tempLabel10 = new JLabel("" + length);
            tempLabel10.setFont(font1);
            cRight.gridx = 1;
            cRight.gridy = 3;
            panel.add(tempLabel10, cRight);
            
            JLabel tempLabel11 = new JLabel("Weight: ");
            tempLabel11.setFont(font1);
            cLeft.gridx = 0;
            cLeft.gridy = 4;
            panel.add(tempLabel11, cLeft);
            
            JLabel tempLabel12 = new JLabel("" + weight);
            tempLabel12.setFont(font1);
            cRight.gridx = 1;
            cRight.gridy = 4;
            panel.add(tempLabel12, cRight);
            
            JLabel tempLabel15 = new JLabel("Experimental Code: ");
            tempLabel15.setFont(font1);
            cLeft.gridx = 0;
            cLeft.gridy = 5;
            panel.add(tempLabel15, cLeft);
            
            cRight.gridx = 1;
            cRight.gridy = 5;
            panel.add(experimentalCode, cRight);
   
            JLabel tempLabel16 = new JLabel("Additional comments: ");
            tempLabel16.setFont(font1);
            cLeft.gridx = 0;
            cLeft.gridy = 6;
            panel.add(tempLabel16, cLeft);
            
            cRight.gridx = 1;
            cRight.gridy = 6;
            panel.add(comments, cRight);
            
            cLeft.gridx = 0;
            cLeft.gridy = 7;
            panel.add(cancel, cLeft);
            
            cRight.gridx = 1;
            cRight.gridy = 7;
            panel.add(confirm, cRight);
        }
        else if (transferStart)
        {
            panel.setLayout(new FlowLayout());
            
            addEntry.setEnabled(hasToCage);
            addToCage.setFont(font2);
            removeToCage.setFont(font2);
            addEntry.setFont(font2);
            back.setFont(font2);
            
            Dimension size = new Dimension((int)(width/6), (int)(height/4));
            
            addToCage.setPreferredSize(size);
            removeToCage.setPreferredSize(size);
            addEntry.setPreferredSize(size);
            back.setPreferredSize(size);
            
            panel.add(addEntry);
            panel.add(addToCage);
            panel.add(removeToCage);
            panel.add(back);
        }
        else if (addTo)
        {
            Dimension size = new Dimension((int)(width/8), (int)(height/10));
            
            panel.setLayout(new BorderLayout());
            Panel panel2 = new Panel(new BorderLayout());
            Panel panel4 = new Panel(new FlowLayout());
            Panel panel5 = new Panel(new FlowLayout());
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
                button.addActionListener(e -> {
                    String temp = ((JButton) e.getSource()).getText();
                    int index = temp.indexOf(' ');
                    int index2 = temp.indexOf(" ", index+1);
                    String cage = temp.substring(index2+1);
                    for (int j = 0; j < toCounter; j++)
                    {
                        if (cage.equals(toCages[j]))
                        {
                            index = j;
                            j = toCounter;
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
            vaccinateField.setEnabled(false);
            formulaField.setEnabled(false);
            comments.setText("");
            experimentalCode.setText("");
            skipLength = false;
            skipWeight = false;
            length = "";
            weight = "";
            
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
        else if (addPage2 || harvestPage2)
        {
            Dimension size = new Dimension((int)(width/7), (int)(height/9));
            
            panel.setLayout(new BorderLayout());
            Panel panel2 = new Panel(new FlowLayout());
            JLabel label = new JLabel("Select Belly Size");
            label.setFont(font2);
            panel2.add(label);
            Panel panel3 = new Panel(new FlowLayout());
            String temp = previousRow.get("Belly Size").toString();
            if (isInteger(temp))
            {
                for (int i = Integer.parseInt(temp) - 5; i < Integer.parseInt(temp) + 20; i++)
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
        else if (addPage3 || harvestPage3)
        {
            Dimension size = new Dimension((int)(width/7), (int)(height/9));
            
            panel.setLayout(new BorderLayout());
            Panel panel2 = new Panel(new FlowLayout());
            JLabel label = new JLabel("Select Length");
            label.setFont(font2);
            panel2.add(label);
            Panel panel3 = new Panel(new FlowLayout());
            String temp = previousRow.get("Length").toString();
            if (isInteger(temp))
            {
                for (int i = Integer.parseInt(temp) - 5; i < Integer.parseInt(temp) + 20; i++)
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
            cancel.setFont(font1);
            skip.setPreferredSize(size);
            skip.setFont(font1);
            panel6.add(cancel);
            panel6.add(skip);
            panel.add(panel2, BorderLayout.NORTH);
            panel.add(panel3, BorderLayout.CENTER);
            panel.add(panel6, BorderLayout.SOUTH);
        }
        else if (addPage4 || harvestPage4)
        {
            Dimension size = new Dimension((int)(width/7), (int)(height/9));
            
            panel.setLayout(new BorderLayout());
            Panel panel2 = new Panel(new FlowLayout());
            JLabel label = new JLabel("Select Weight");
            label.setFont(font2);
            panel2.add(label);
            Panel panel3 = new Panel(new FlowLayout());
            String temp = previousRow.get("Weight").toString();
            if (isInteger(temp))
            {
                for (int i = Integer.parseInt(temp) - 5; i < Integer.parseInt(temp) + 20; i++)
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
            cancel.setFont(font1);
            skip.setPreferredSize(size);
            skip.setFont(font1);
            panel6.add(cancel);
            panel6.add(skip);
            panel.add(panel2, BorderLayout.NORTH);
            panel.add(panel3, BorderLayout.CENTER);
            panel.add(panel6, BorderLayout.SOUTH);
        }
        else if (addPage5)
        {
            panel.setLayout(new GridBagLayout());
            GridBagConstraints cRight = new GridBagConstraints();
            cRight.insets = new Insets(10, 30, 10, 30);
            cRight.anchor = GridBagConstraints.LINE_START;
            cRight.weightx = 0;
            GridBagConstraints cLeft = new GridBagConstraints();
            cLeft.insets = new Insets(10, 30, 10, 30);
            cLeft.anchor = GridBagConstraints.LINE_END;
            cLeft.weightx = 0.8;
            GridBagConstraints cFarRight = new GridBagConstraints();
            cFarRight.insets = new Insets(10, 30, 10, 30);
            cFarRight.anchor = GridBagConstraints.LINE_START;
            cFarRight.weightx = 0.5;
            Dimension size = new Dimension((int)(width/7), (int)(height/9));
            Dimension size2 = new Dimension((int)(width/17), (int)(height/16));
            confirm.setPreferredSize(size);
            confirm.setFont(font1);
            confirm.setEnabled(true);
            cancel.setPreferredSize(size);
            cancel.setFont(font1);
            didVaccinate.setPreferredSize(size2);
            didVaccinate.setFont(font1);
            vaccinateField.setPreferredSize(size2);
            vaccinateField.setFont(font1);
            vaccinateField.setText(currentDate);
            didFormula.setPreferredSize(size2);
            didFormula.setFont(font1);
            formulaField.setPreferredSize(size2);
            formulaField.setFont(font1);
            formulaField.setText(currentDate);
            
            JLabel tempLabel1 = new JLabel("Gator ID: ");
            tempLabel1.setFont(font1);
            cLeft.gridx = 0;
            cLeft.gridy = 0;
            panel.add(tempLabel1, cLeft);

            JLabel tempLabel2 = new JLabel(tag);
            tempLabel2.setFont(font1);
            cRight.gridx = 1;
            cRight.gridy = 0;
            panel.add(tempLabel2, cRight);
            
            JLabel tempLabel3 = new JLabel("From Pen: ");
            tempLabel3.setFont(font1);
            cLeft.gridx = 0;
            cLeft.gridy = 1;
            panel.add(tempLabel3, cLeft);
            
            JLabel tempLabel4 = new JLabel("" + previousRow.get("To").toString());
            tempLabel4.setFont(font1);
            cRight.gridx = 1;
            cRight.gridy = 1;
            panel.add(tempLabel4, cRight);
            
            JLabel tempLabel5 = new JLabel("To Pen: ");
            tempLabel5.setFont(font1);
            cLeft.gridx = 0;
            cLeft.gridy = 2;
            panel.add(tempLabel5, cLeft);
            
            JLabel tempLabel6 = new JLabel("" + toCage);
            tempLabel6.setFont(font1);
            cRight.gridx = 1;
            cRight.gridy = 2;
            panel.add(tempLabel6, cRight);

            JLabel tempLabel7 = new JLabel("Belly Size: ");
            tempLabel7.setFont(font1);
            cLeft.gridx = 0;
            cLeft.gridy = 3;
            panel.add(tempLabel7, cLeft);
            
            JLabel tempLabel8 = new JLabel("" + bellySize);
            tempLabel8.setFont(font1);
            cRight.gridx = 1;
            cRight.gridy = 3;
            panel.add(tempLabel8, cRight);
            
            JLabel tempLabel9 = new JLabel("Length: ");
            tempLabel9.setFont(font1);
            cLeft.gridx = 0;
            cLeft.gridy = 4;
            panel.add(tempLabel9, cLeft);
            
            JLabel tempLabel10 = new JLabel("" + length);
            tempLabel10.setFont(font1);
            cRight.gridx = 1;
            cRight.gridy = 4;
            panel.add(tempLabel10, cRight);
            
            JLabel tempLabel11 = new JLabel("Weight: ");
            tempLabel11.setFont(font1);
            cLeft.gridx = 0;
            cLeft.gridy = 5;
            panel.add(tempLabel11, cLeft);
            
            JLabel tempLabel12 = new JLabel("" + weight);
            tempLabel12.setFont(font1);
            cRight.gridx = 1;
            cRight.gridy = 5;
            panel.add(tempLabel12, cRight);
            
            JLabel tempLabel13 = new JLabel("Vaccinated? ");
            tempLabel13.setFont(font1);
            cLeft.gridx = 0;
            cLeft.gridy = 6;
            panel.add(tempLabel13, cLeft);
            
            cRight.gridx = 1;
            cRight.gridy = 6;
            panel.add(didVaccinate, cRight);
            
            cFarRight.gridx = 2;
            cFarRight.gridy = 6;
            panel.add(vaccinateField, cFarRight);
            
            
            JLabel tempLabel14 = new JLabel("Did formula? ");
            tempLabel14.setFont(font1);
            cLeft.gridx = 0;
            cLeft.gridy = 7;
            panel.add(tempLabel14, cLeft);
            
            cRight.gridx = 1;
            cRight.gridy = 7;
            panel.add(didFormula, cRight);
            
            cFarRight.gridx = 2;
            cFarRight.gridy = 7;
            panel.add(formulaField, cFarRight);
            
            
            JLabel tempLabel15 = new JLabel("Experimental Code: ");
            tempLabel15.setFont(font1);
            cLeft.gridx = 0;
            cLeft.gridy = 8;
            panel.add(tempLabel15, cLeft);
            cRight.gridx = 1;
            cRight.gridy = 8;
            panel.add(experimentalCode, cRight);
   
            JLabel tempLabel16 = new JLabel("Additional comments: ");
            tempLabel16.setFont(font1);
            cLeft.gridx = 0;
            cLeft.gridy = 9;
            panel.add(tempLabel16, cLeft);
            
            cRight.gridx = 1;
            cRight.gridy = 9;
            panel.add(comments, cRight);
            
            cLeft.gridx = 0;
            cLeft.gridy = 10;
            panel.add(cancel, cLeft);
            
            cRight.gridx = 1;
            cRight.gridy = 10;
            panel.add(confirm, cRight);
        }
        else if (quit)
        {            
                //output file
            
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
            tempButton.addActionListener(e -> {
                start = false;
                transferStart = true;
                newGatorPage1 = false;
                newGatorPage2 = false;
                harvestPage1 = false;
                harvestPage2 = false;
                setUp = false;
                addTo = false;
                removeTo = false;
                addPage1 = false;
                addPage2 = false;
                quit = false;
                addComponents();
                frame2.dispose();
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
        
        if (start || transferStart || newGatorPage1 || newGatorPage2 || harvestPage1 || harvestPage2 || harvestPage3 || harvestPage4 || harvestPage5 || setUp || addTo || removeTo || addPage1 || addPage2 || addPage3 || addPage4 || addPage5 || quit)
        {
            contentPane.add(panel);
            validate();
            setVisible(true);
        }
    }
    
    public static void createAndShowGUI()
    {
        frame = new Application();
        frame.addListeners();
        frame.initializeButtonArray();
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

            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        }
        catch (UnsupportedCommOperationException | PortInUseException | TooManyListenersException | IOException e)
        {
 
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
    
    @Override
    public synchronized void serialEvent(SerialPortEvent oEvent)
    {
        String temp;
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
                        previousRow = latestRow;
                    }
                    else
                    {
                        previousRow = null;
                    }
                    
                    addPage1 = false;
                    addPage2 = true;
                    addComponents();
                }
                else if (harvestPage1)
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
                        previousRow = latestRow;
                    }
                    else
                    {
                        previousRow = null;
                    }
                    
                    harvestPage1 = false;
                    harvestPage2 = true;
                    addComponents();
                }
                else if (newGatorPage1)
                {
                    newGatorPage1 = false;
                    newGatorPage2 = true;
                    addComponents();
                }
            }
            catch (Exception e)
            {
                System.err.println(e.toString());
            }
        }
    }
    
    public void addListeners()
    {
        skip.addActionListener(e -> {
            if (addPage3)
            {
                skipLength = true;
                addPage4 = true;
                addPage3 = false;
                addComponents();
            }
            else if (addPage4)
            {
                skipWeight = true;
                addPage4 = false;
                addPage5 = true;
                addComponents();
            }
        });
        
        addNewGator.addActionListener(e -> {
            start = false;
            newGatorPage1 = true;
            newGatorPage2 = false;
            harvestPage1 = false;
            harvestPage2 = false;
            transferStart = false;
            setUp = false;
            addTo = false;
            removeTo = false;
            addPage1 = false;
            addPage2 = false;
            quit = false;
            addComponents();
        });
        
        transferGator.addActionListener(e -> {
            start = false;
            transferStart = true;
            newGatorPage1 = false;
            newGatorPage2 = false;
            harvestPage1 = false;
            harvestPage2 = false;
            setUp = false;
            addTo = false;
            removeTo = false;
            addPage1 = false;
            addPage2 = false;
            quit = false;
            addComponents();
        });
        
        harvestGator.addActionListener(e -> {
            start = false;
            newGatorPage1 = false;
            newGatorPage2 = false;
            transferStart = false;
            harvestPage1 = true;
            harvestPage2 = false;
            setUp = false;
            addTo = false;
            removeTo = false;
            addPage1 = false;
            addPage2 = false;
            quit = false;
            addComponents();
        });
        
        quitButton.addActionListener(e -> {
            start = false;
            transferStart = false;
            newGatorPage1 = false;
            newGatorPage2 = false;
            harvestPage1 = false;
            harvestPage2 = false;
            setUp = false;
            addTo = false;
            removeTo = false;
            addPage1 = false;
            addPage2 = false;
            quit = true;
            addComponents();
        });
        
        addToCage.addActionListener(e -> {
            start = false;
            transferStart = false;
            newGatorPage1 = false;
            newGatorPage2 = false;
            harvestPage1 = false;
            harvestPage2 = false;
            setUp = false;
            addTo = true;
            removeTo = false;
            addPage1 = false;
            addPage2 = false;
            quit = false;
            addComponents();
        });
        
        removeToCage.addActionListener(e -> {
            start = false;
            transferStart = false;
            newGatorPage1 = false;
            newGatorPage2 = false;
            harvestPage1 = false;
            harvestPage2 = false;
            setUp = false;
            addTo = false;
            removeTo = true;
            addPage1 = false;
            addPage2 = false;
            quit = false;
            addComponents();
        });
        
        addEntry.addActionListener(e -> {
            start = false;
            transferStart = false;
            newGatorPage1 = false;
            newGatorPage2 = false;
            harvestPage1 = false;
            harvestPage2 = false;
            setUp = false;
            addTo = false;
            removeTo = false;
            addPage1 = true;
            addPage2 = false;
            quit = false;
            addComponents();
        });
        
        back.addActionListener(e -> {
            start = true;
            transferStart = false;
            newGatorPage1 = false;
            newGatorPage2 = false;
            harvestPage1 = false;
            harvestPage2 = false;
            setUp = false;
            addTo = false;
            removeTo = false;
            addPage1 = false;
            addPage2 = false;
            quit = false;
            addComponents();
        });
        
        cancel.addActionListener(e -> {
            start = false;
            transferStart = true;
            newGatorPage1 = false;
            newGatorPage2 = false;
            harvestPage1 = false;
            harvestPage2 = false;
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
        });
        
        confirm.addActionListener(e -> {
            errorMessage = "";  
            if (addTo)
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
                    switch (classSize)
                    {
                        case "Empty":   
                            errorMessage = "Cannot transfer to designated empty pen";
                            break;
                            
                        case "Hatchling":
                        case "Family":  
                            toCages[toCounter] = pen;
                            toLowerBounds[toCounter] = 0;
                            toUpperBounds[toCounter] = 0;
                            toClassSizes[toCounter] = classSize;
                            capacities[toCounter] = Integer.parseInt(input.getText());
                            capacityCounters[toCounter] = 0;
                            hasToCage = true;
                            toCounter++;
                            break;
                            
                        case "39+":     
                            toCages[toCounter] = pen;
                            toLowerBounds[toCounter] = 39;
                            toUpperBounds[toCounter] = 46;
                            toClassSizes[toCounter] = classSize;
                            capacities[toCounter] = Integer.parseInt(input.getText());
                            capacityCounters[toCounter] = 0;
                            hasToCage = true;
                            toCounter++;
                            break;
                            
                        default:        
                            int index = classSize.indexOf('-');
                            toCages[toCounter] = pen;
                            toLowerBounds[toCounter] = Integer.parseInt(classSize.substring(0, index));
                            toUpperBounds[toCounter] = Integer.parseInt(classSize.substring(index+1));
                            toClassSizes[toCounter] = classSize;
                            capacities[toCounter] = Integer.parseInt(input.getText());
                            capacityCounters[toCounter] = 0;
                            hasToCage = true;
                            toCounter++;
                            break;
                    }
                }   
            }
            else if (addPage5)
            {
                try
                {
                    if (previousRow != null)
                    {
                        String lengthEntry = (skipLength) ? previousRow.get("Length").toString() : length;
                        String weightEntry = (skipWeight) ? previousRow.get("Weight").toString() : weight;
                            
                        gatorTable.addRow(0, tag, previousRow.get("Egg Nest Location"), previousRow.get("Egg Nest Condition"), previousRow.get("Egg Collection Date"), previousRow.get("Hatch Year"), previousRow.get("Gender"), previousRow.get("Umbilical"), currentDate, previousRow.get("To"), toCage, bellySize, lengthEntry, weightEntry, formulaDate, experimentalCode.getText(), vaccinatedDate, comments.getText(), "");
                    }
                    else
                    {
                        String lengthEntry = (skipLength) ? "" : length;
                        String weightEntry = (skipWeight) ? "" : weight;
                            
                        gatorTable.addRow(0, tag, "", "", "", "", "", "", currentDate, previousRow.get("To"), toCage, bellySize, lengthEntry, weightEntry, formulaDate, experimentalCode.getText(), vaccinatedDate, comments.getText(), "");
                    }
                    IndexCursor cursor = CursorBuilder.createCursor(gatorTable.getIndex("IDIndex"));
                    cursor.beforeFirst();
                    for(Map<String,Object> row : cursor)
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
                    harvestPage1 = false;
                    harvestPage2 = false;
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
            else if (newGatorPage2)
            {
                try
                {
                    gatorTable.addRow(0, tag, location.getText(), condition.getText(), collectionDate.getText(), currentDate.substring(6), gender.getSelectedItem().toString(), umbilical.getSelectedItem().toString(), currentDate, "", cageList.getSelectedItem().toString(), "", "", "", "", "", "", comments.getText(), "");
                    IndexCursor cursor = CursorBuilder.createCursor(gatorTable.getIndex("IDIndex"));
                    cursor.beforeFirst();
                    for(Map<String,Object> row : cursor)
                    {
 
                    }
                }
                catch (IOException e1)
                {
                        
                }
            }
            else if (harvestPage5)
            {
                try
                {
                    String lengthEntry = (skipLength) ? previousRow.get("Length").toString() : length;
                    String weightEntry = (skipWeight) ? previousRow.get("Weight").toString() : weight;
                        
                    gatorTable.addRow(0, tag, previousRow.get("Egg Nest Location"), previousRow.get("Egg Nest Condition"), previousRow.get("Egg Collection Date"), previousRow.get("Hatch Year"), previousRow.get("Gender"), previousRow.get("Umbilical"), currentDate, previousRow.get("To"), "", bellySize, lengthEntry, weightEntry, previousRow.get("Special Recipe"), "", previousRow.get("Vaccinated"), comments.getText(), "Yes");
                    for(Map<String,Object> row : CursorBuilder.createCursor(gatorTable.getIndex("IDIndex")))
                    {
 
                    }
                }
                catch (IOException e1)
                {
                        
                }
            }
                
                
            if (!errorMessage.equals(""))
            {
                transferStart = false;
                addPage1 = false;
                newGatorPage1 = false;
                harvestPage1 = false;
            }
            else if (addPage5)
            {
                transferStart = false;
                addPage1 = true;
                newGatorPage1 = false;
                harvestPage1 = false;
            }
            else if (newGatorPage2)
            {
                transferStart = false;
                addPage1 = false;
                newGatorPage1 = true;
                harvestPage1 = false;
            }
            else if (harvestPage5)
            {
                transferStart = false;
                addPage1 = false;
                newGatorPage1 = false;
                harvestPage1 = true;
            }
            else
            {
                transferStart = true;
                addPage1 = false;
                newGatorPage1 = false;
                harvestPage1 = false;
            }
            start = false;
            newGatorPage2 = false;
            harvestPage5 = false;
            addTo = false;
            removeTo = false;
            addPage5 = false;
            quit = false;
            addComponents();
        });
        
        cageList.addPopupMenuListener(new PopupMenuListener()
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
                    Dimension scrollBarDim = new Dimension((int)(width / 48), scrollBar.getPreferredSize().height);
                    scrollBar.setPreferredSize(scrollBarDim);
                }
            }
            
            @Override
            public void popupMenuCanceled(PopupMenuEvent e)
            {
                if (setUp)
                {
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
                    confirm.setEnabled(!cageTaken && isInteger(input.getText()) && Integer.parseInt(input.getText()) > 0);
                }
            }
            
            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
            {
                if (setUp)
                {
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
                    confirm.setEnabled(!cageTaken && isInteger(input.getText()) && Integer.parseInt(input.getText()) > 0);
                }
            }
        });
        
        input.getDocument().addDocumentListener(new DocumentListener()
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
                confirm.setEnabled(!cageTaken && isInteger(input.getText()) && Integer.parseInt(input.getText()) > 0);
            }
        });
        
        didVaccinate.addActionListener(e -> {
            if (isVaccinated)
            {
                vaccinatedDate = previousRow.get("Vaccinated").toString();
                System.out.println("Not vaccinated " + vaccinatedDate);
            }
            else
            {
                vaccinatedDate = vaccinateField.getText();
                System.out.println("Vaccinated: " + vaccinatedDate);
            }
            isVaccinated = !isVaccinated;
            vaccinateField.setEnabled(isVaccinated);     
        });
        
        vaccinateField.getDocument().addDocumentListener(new DocumentListener()
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
                vaccinatedDate = vaccinateField.getText();
            }
        });
        
        
        didFormula.addActionListener(e -> {
            if (isFormula)
            {
                formulaDate = previousRow.get("Special Recipe").toString();
                System.out.println("Not formula " + formulaDate);
            }
            else
            {
                formulaDate = formulaField.getText();
                System.out.println("Formula " + formulaDate);
            }
            isFormula = !isFormula;
            formulaField.setEnabled(isFormula);
        });
        
        formulaField.getDocument().addDocumentListener(new DocumentListener()
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
                formulaDate = formulaField.getText();
            }
        });
    }
    
    public void initializeButtonArray()
    {
        for (int i = 0; i <= 200; i++)
        {
            JButton button = new JButton("" + i);
            button.addActionListener(e -> {
                String entry = ((JButton) e.getSource()).getText();
                int number = Integer.parseInt(entry);
                if (addPage2)
                {
                    bellySize = number;
                    String classSize = "";
                        
                    for (int j = 0; j < toCounter; j++)
                    {
                        try
                        {
                            IndexCursor cursor = CursorBuilder.createCursor(cageTable.getIndex("PenNumberIndex"));                            
                            cursor.beforeFirst();
                            cursor.findFirstRow(Collections.singletonMap("Pen Number", toCages[j]));
                            Row latestRow = cursor.getCurrentRow();
                            while (cursor.findNextRow(Collections.singletonMap("Pen Number", toCages[j])))
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
                        
                        if (classSize.equals("Family") || (number >= toLowerBounds[j] && number <= toUpperBounds[j]) || (entry.equals("Hatchling") && classSize.equals("Hatchling")))
                        {
                            toCage = toCages[j];
                            toCageIndex = j;
                            j = toCounter;
                        }
                    }
                    addPage2 = false;
                    addPage3 = true;
                    addComponents();
                }
                else if (addPage3)
                {
                    length = entry;
                    addPage3 = false;
                    addPage4 = true;
                    addComponents();
                }
                else if (addPage4)
                {
                    weight = entry;
                    addPage4 = false;
                    addPage5 = true;
                    addComponents();
                }
                else if (harvestPage2)
                {
                    bellySize = number;
                    harvestPage2 = false;
                    harvestPage3 = true;
                    addComponents();
                }
                else if (harvestPage3)
                {
                    length = entry;
                    harvestPage3 = false;
                    harvestPage4 = true;
                    addComponents();
                }
                else if (harvestPage4)
                {
                    weight = entry;
                    harvestPage4 = false;
                    harvestPage5 = true;
                    addComponents();
                }
            });
            numbers[i] = button;
        }
    }
}