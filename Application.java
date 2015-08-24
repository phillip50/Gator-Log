/**
 * Gator Application
 * Allows new gators to be placed in pens, existing gators to be transferred between pens, and gators to be harvested
 * All events are reflected as records in a single database
 * 
 * @Phillip Dingler [phil50@ufl.edu]
 */

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
import org.apache.commons.lang.StringUtils;

public class Application extends JFrame implements SerialPortEventListener
{
        //"this" object
    private static Application frame;
    
        //Panel that every conponent is placed on
    private final Container contentPane;
    
        //Database files
    private File gatorFile;
    private Table gatorTable;
    private File penFile;
    private Table penTable;
    
    private File logFile;
    private Date startTime;
    
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
    private final java.util.List<String> pens;
    
        //List of quartered pens on the farm
    private final java.util.List<String> quarteredPens;
    
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
    
    private boolean isGoodBelly;
    
        //When transferring gators, a "to" pen needs to be selected
        //toPens refers to the selected pens, and their class size and ranges are stored
        //Capacity is the amount of gators the pen can hold, with capacityCounters referring to how many have been transferred to that pen
        //Each pen's information is stored at the same position in the array, which is reflected by the toCounter value
        //When a pen reaches capacity, it is removed from the list and added to the pensAtCapacity array, along wiht its corresponding information
    private String[] toPens;
    private int[] toUpperBounds;
    private int[] toLowerBounds;
    private String[] toClassSizes;
    private java.util.List<Boolean> isGoodQuality;
    private int[] capacities;
    private int[] capacityCounters;
    private int toCounter;
    private String[] pensAtCapacity;
    private int[] pensAtCapacityAmount;
    private String[] pensAtCapacityRange;
    private int pensAtCapacityCounter;
    
    private java.util.List<String> fromPens;
    private java.util.List<Integer> fromPensAmount;
    
    private java.util.List<String> toPensNewGator;
    private java.util.List<Integer> toPensNewGatorAmount;
    
        //Boolean checks when adding a "to" pen to the above arrays
        //if there is atleast 1 "to" pen, hasToPen is true
        //if the same pen has already been added to the array, penTaken is false
    private boolean hasToPen;
    private boolean penTaken;
    
        //Stores which pen the currently selected gator is being transferred to, along with its index in the arrays
    private String toPen;
    private int toPenIndex;
    
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
    private final JButton addToPen;
    private final JButton removeToPen;
    private final JButton addEntry;
    private final JButton back;
    private final JButton addNewGator;
    private final JButton transferGator;
    private final JButton harvestGator;
    private final JButton quitButton;
    private final JComboBox penList;
    private JComboBox toPensComboBox;
    private final JTextField capacityInput;
    private final JTextField collectionDate;
    private final JTextField eggLocation;
    private final JTextField eggNumber;
    private final JTextField eggLength;
    private final JTextField eggWeight;
    private final JTextField experimentalCode;
    private final JTextField optionalText;
    private final JComboBox gender;
    private final JComboBox umbilical;
    private final JButton confirm;
    private final JButton cancel;    
    private final JButton didVaccinate;
    private final JTextField vaccinateField;
    private final JButton didFormula;
    private final JTextField formulaField;
    private final JTextField comments;
    private final JButton goodBelly;
    private final JButton badBelly;
    
        //Boolean values to determine which components to display
    private boolean start;
    private boolean newGatorPage1;
    private boolean newGatorPage2;
    private boolean newGatorOptionalPage;
    private boolean harvestPage1;
    private boolean harvestPage2;
    private boolean harvestPage3;
    private boolean harvestPage4;
    private boolean harvestPage5;
    private boolean transferStart;
    private boolean addTo;
    private boolean removeTo;
    private boolean transferPage1;
    private boolean transferPage2;
    private boolean transferPage3;
    private boolean transferPage4;
    private boolean transferPage5;
    private boolean transferPage6;
    private boolean quit;
    
    private String portNumber = "COM3";
    
    private int addedRows;
    private File rowsToSync;
    
    public Application()
    {
        super("Gator Application");
        
        startTime = Calendar.getInstance().getTime();
        
            //initialize the fields
        gatorFile = null;
        gatorTable = null;
        penFile = null;
        penTable = null;
        
        years = new String[4];
        
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        currentDate = dateFormat.format(date);   
        
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.getWidth();
        height = screenSize.getHeight();
        font1 = new Font("Arial", Font.PLAIN, 40);
        font2 = new Font("Arial", Font.PLAIN, 25); 
        
        tag = "";
        toPen = "";
        bellySize = 0;
        length = "";
        weight = "";
        vaccinatedDate = "";
        formulaDate = "";
        skipLength = false;
        skipWeight = false;
        isGoodBelly = false;
        
        isVaccinated = false;
        isFormula = false;
        
        toPens = new String[10];
        toUpperBounds = new int[10];
        toLowerBounds = new int[10];
        toClassSizes = new String[10];
        isGoodQuality = new ArrayList<>();
        capacities = new int[10];
        capacityCounters = new int[10];
        toCounter = 0;
        pensAtCapacity = new String[10];
        pensAtCapacityAmount = new int[10];
        pensAtCapacityRange = new String[10];
        pensAtCapacityCounter = 0;
        
        fromPens = new ArrayList<>();
        fromPensAmount = new ArrayList<>();
        
        toPensNewGator = new ArrayList<>();
        toPensNewGatorAmount = new ArrayList<>();
        
        penTaken = false;
        hasToPen = false;
        
        
            //initially display the start page
        start = true;
        newGatorPage1 = false;
        newGatorPage2 = false;
        newGatorOptionalPage = false;
        transferStart = false;
        harvestPage1 = false;
        harvestPage2 = false;
        harvestPage3 = false;
        harvestPage4 = false;
        harvestPage5 = false;
        addTo = false;
        removeTo = false;
        transferPage1 = false;
        transferPage2 = false;
        transferPage3 = false;
        transferPage4 = false;
        transferPage5 = false;
        transferPage6 = false;
        quit = false;
              
        String temp = "";
        
            //read in the gator and pen databases and the list of quartered pens
        try
        {
            gatorFile = new File("AnimalDatabase.accdb");
            gatorTable = DatabaseBuilder.open(gatorFile).getTable("Database");
            penFile = new File("PenDatabase.accdb");
            penTable = DatabaseBuilder.open(penFile).getTable("Database");
            
            BufferedReader reader = new BufferedReader(new FileReader("QuarteredPens.txt"));
            temp = reader.readLine();
        }
        catch (IOException e1)
        {
            
        }
        
        logFile = new File("log1.txt");
        int k = 1;
        while (logFile.exists())
        {
            k++;
            logFile = new File("log" + k + ".txt");
        }
        
            //format of text file is comma-separated elements
        quarteredPens = new ArrayList( Arrays.asList(temp.split(",")) );
        
            //get the last 4 years
        int year = Integer.parseInt(currentDate.substring(6));
        for (int i = 0; i < 4; i++)
        {
            int number = year - i;
            years[i] = "" + number;
        }
        
            //List of pens on the farm
            //For quartered pens, add each of the 4 quarters
        pens = new ArrayList<>();
        pens.add("");
        for (int i = 101; i <= 127; i++)
        {
            if (quarteredPens.indexOf("" + i) != -1)
            {
                for (int j = 1; j <= 4; j++)
                {
                    pens.add("" + i + "." + j);
                }
            }
            else
            {
                pens.add("" + i);
            }
        }
        for (int i = 201; i <= 232; i++)
        {
            if (quarteredPens.indexOf("" + i) != -1)
            {
                for (int j = 1; j <= 4; j++)
                {
                    pens.add("" + i + "." + j);
                }
            }
            else
            {
                pens.add("" + i);
            }
        }
        for (int i = 301; i <= 326; i++)
        {
            if (quarteredPens.indexOf("" + i) != -1)
            {
                for (int j = 1; j <= 4; j++)
                {
                    pens.add("" + i + "." + j);
                }
            }
            else
            {
                pens.add("" + i);
            }
        }
        for (int i = 401; i <= 437; i++)
        {
            if (quarteredPens.indexOf("" + i) != -1)
            {
                for (int j = 1; j <= 4; j++)
                {
                    pens.add("" + i + "." + j);
                }
            }
            else
            {
                pens.add("" + i);
            }
        }
            //Small pens can't be quartered, no need to check
        for (int i = 801; i <= 816; i++)
        {
            pens.add("" + i);
        }
        for (int i = 901; i <= 910; i++)
        {
            pens.add("" + i);
        }
   
        
            //initialize all of the components
        contentPane = getContentPane();
        numbers = new JButton[201];
        
        skip = new JButton("Skip Recording");      
        addNewGator = new JButton("Add New Gator");    
        transferGator = new JButton("Transfer Gator");      
        harvestGator = new JButton("Harvest Gator");       
        quitButton = new JButton("Quit");      
        addToPen = new JButton("Add To Pen");      
        removeToPen = new JButton("Remove To Pen");       
        addEntry = new JButton("Add Entry");            
        back = new JButton("Back");       
        cancel = new JButton("Cancel");      
        confirm = new JButton("Confirm");
        didVaccinate = new JButton("Yes");            
        didFormula = new JButton("Yes");     
        goodBelly = new JButton("Good Belly");
        badBelly = new JButton("Bad Belly");
        
        penList = new JComboBox(pens.toArray());
        penList.setEditable(false);
        
        capacityInput = new JTextField(10);                
        collectionDate = new JTextField(10);
        eggLocation = new JTextField(10);
        eggNumber = new JTextField(10);
        eggLength = new JTextField(10);
        eggWeight = new JTextField(10);
        experimentalCode = new JTextField(10);
        comments = new JTextField(10);
        vaccinateField = new JTextField(10);
        formulaField = new JTextField(10);
        optionalText = new JTextField(10);
        
        capacityInput.setFont(font1);
        collectionDate.setFont(font1);
        eggLocation.setFont(font1);
        eggNumber.setFont(font1);
        eggLength.setFont(font1);
        eggWeight.setFont(font1);
        experimentalCode.setFont(font1);
        comments.setFont(font1);
        
        vaccinateField.setEnabled(false);
        formulaField.setEnabled(false);
        
        String[] genderList = {"Female", "Male"};
        String[] umbilicalList = {"Y", "N"};
        
        gender = new JComboBox(genderList);
        umbilical = new JComboBox(umbilicalList);
        
        gender.setEditable(false);
        umbilical.setEditable(false);
        
        rowsToSync = new File("RowsToSync.txt");
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(rowsToSync));
            addedRows = Integer.parseInt(reader.readLine());
            System.out.println(addedRows);
        }
        catch (IOException e)
        {
            
        }
    }
    
        //methods to place components onto the frame, depending on the page boolean values
    public void addComponents()
    {  
            //reset the frame and components
        contentPane.removeAll();
        //penList.setSelectedIndex(0);
        capacityInput.setText("");
        
        JPanel panel = new JPanel();
        
            //initial start screen
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
            //1st page when adding a new hatchling
        else if (newGatorPage1)
        {
            gender.setSelectedIndex(0);
            umbilical.setSelectedIndex(0);
            //collectionDate.setText("");
            //eggLocation.setText("");
            eggNumber.setText("");
            eggLength.setText("");
            eggWeight.setText("");
            comments.setText("");
            confirm.setEnabled(true);
            
            panel.setLayout(new BorderLayout());
            
            Dimension size = new Dimension((int)(width/8), (int)(height/10)); 
            Dimension size2 = new Dimension((int)(width/4), (int)(height/10)); 
            
            JButton enterManually = new JButton("Or enter tag manually");
            enterManually.addActionListener(e -> {
                newGatorPage1 = false;
                newGatorOptionalPage = true;
                addComponents();
            });
            
            Panel panel2 = new Panel(new FlowLayout());
            Panel panel3 = new Panel(new FlowLayout());
            JLabel tempLabel = new JLabel("Scan Microchip");
            tempLabel.setFont(font1);
            back.setPreferredSize(size);
            back.setFont(font1);
            enterManually.setPreferredSize(size2);
            enterManually.setFont(font1);
            panel2.add(back);
            panel2.add(enterManually);
            panel3.add(tempLabel);
            panel.add(panel3, BorderLayout.NORTH);
            panel.add(panel2, BorderLayout.SOUTH);
        }
        else if (newGatorOptionalPage)
        {
            panel.setLayout(new BorderLayout());
            
            Dimension size = new Dimension((int)(width/8), (int)(height/10));
            
            Panel panel2 = new Panel(new FlowLayout());
            Panel panel3 = new Panel(new FlowLayout());
            
            JButton enterTag = new JButton("Enter");
            enterTag.addActionListener(e -> {
                tag = optionalText.getText();
                newGatorOptionalPage = false;
                newGatorPage2 = true;
                addComponents();
            });
            
            JLabel tempLabel = new JLabel("Tag Number: ");
            tempLabel.setFont(font1);
            back.setPreferredSize(size);
            back.setFont(font1);
            enterTag.setPreferredSize(size);
            enterTag.setFont(font1);
            optionalText.setFont(font1);
            
            panel2.add(optionalText);
            panel2.add(enterTag);
            
            panel3.add(back);
            
            panel.add(panel2, BorderLayout.NORTH);
            panel.add(panel3, BorderLayout.SOUTH);
        }
            //2nd page of adding a new hatchling
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
            JLabel collectionDateLabel = new JLabel("Tag Date: ");
            collectionDateLabel.setFont(font1);
            JLabel locationLabel = new JLabel("Egg Nest Location: ");
            locationLabel.setFont(font1);
            JLabel numberLabel = new JLabel("Tag Number: ");
            numberLabel.setFont(font1);
            JLabel lengthLabel = new JLabel("Length: ");
            lengthLabel.setFont(font1);
            JLabel weightLabel = new JLabel("Weight: ");
            weightLabel.setFont(font1);
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
            confirm.setEnabled(false);
            back.setPreferredSize(size);
            back.setFont(font1);
            penList.setFont(font1);
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
            panel.add(collectionDateLabel, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 1;
            panel.add(collectionDate, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 2;
            panel.add(locationLabel, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 2;
            panel.add(eggLocation, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 3;
            panel.add(numberLabel, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 3;
            panel.add(eggNumber, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 4;
            panel.add(lengthLabel, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 4;
            panel.add(eggLength, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 5;
            panel.add(weightLabel, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 5;
            panel.add(eggWeight, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 6;
            panel.add(hatchYear1, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 6;
            panel.add(hatchYear2, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 7;
            panel.add(genderLabel, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 7;
            panel.add(gender, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 8;
            panel.add(umbilicalLabel, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 8;
            panel.add(umbilical, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 9;
            panel.add(penLabel, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 9;
            panel.add(penList, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 10;
            panel.add(commentsLabel, cRight);
            
            cLeft.gridx = 1;
            cLeft.gridy = 10;
            panel.add(comments, cLeft);
            
            cRight.gridx = 0;
            cRight.gridy = 11;
            panel.add(back, cRight);
            
            cLeft.fill = GridBagConstraints.NONE;
            cLeft.gridx = 1;
            cLeft.gridy = 11;
            panel.add(confirm, cLeft);
        }
            //1st page of harvesting a gator
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
            //5th page of adding a new gator
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
            //start screen when transferring a gator
        else if (transferStart)
        {
            panel.setLayout(new FlowLayout());
            
            addEntry.setEnabled(hasToPen);
            addToPen.setFont(font2);
            removeToPen.setFont(font2);
            addEntry.setFont(font2);
            back.setFont(font2);
            
            Dimension size = new Dimension((int)(width/6), (int)(height/4));
            
            addToPen.setPreferredSize(size);
            removeToPen.setPreferredSize(size);
            addEntry.setPreferredSize(size);
            back.setPreferredSize(size);
            
            panel.add(addEntry);
            panel.add(addToPen);
            panel.add(removeToPen);
            panel.add(back);
        }
            //page to add new "to" pen in transfer option
        else if (addTo)
        {
            Dimension size = new Dimension((int)(width/8), (int)(height/10));
            
            panel.setLayout(new BorderLayout());
            Panel panel2 = new Panel(new BorderLayout());
            Panel panel4 = new Panel(new FlowLayout());
            Panel panel5 = new Panel(new FlowLayout());
            Panel panel7 = new Panel(new FlowLayout());

            penList.setPreferredSize(size);
            penList.setFont(font1);
            confirm.setPreferredSize(size);
            confirm.setFont(font1);
            confirm.setEnabled(!penTaken && isInteger(capacityInput.getText()) && Integer.parseInt(capacityInput.getText()) > 0);
            cancel.setPreferredSize(size);
            cancel.setFont(font1);
            capacityInput.setPreferredSize(size);
            capacityInput.setFont(font1);
            JLabel label4 = new JLabel("Pen: ");
            label4.setFont(font1);
            JLabel label5 = new JLabel("Capacity: ");
            label5.setFont(font1);
            panel5.add(label4);
            panel5.add(penList);
            panel7.add(label5);
            panel7.add(capacityInput);
            panel4.add(cancel);
            panel4.add(confirm);
            panel2.add(panel5, BorderLayout.NORTH);
            panel2.add(panel7, BorderLayout.CENTER);
            panel2.add(panel4, BorderLayout.SOUTH);
            panel.add(panel2, BorderLayout.SOUTH);
        }
            //page to remove "to" pen in transfer option
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
                
                String quality = (isGoodQuality.get(i)) ? "Good" : "Bad";
                
                JLabel label = new JLabel("Pen " + toPens[i] + ": " + toClassSizes[i] + ", Quality: " + quality + ", Capacity: " + capacities[i]);
                label.setFont(font1);
                panel2.add(label);
                
                button = new JButton("Remove Pen " + toPens[i]);
                button.addActionListener(e -> {
                    String temp = ((JButton) e.getSource()).getText();
                    int index = temp.indexOf(" ");
                    int index2 = temp.indexOf(" ", index+1);
                    String pen = temp.substring(index2+1);
                    for (int j = 0; j < toCounter; j++)
                    {
                        if (pen.equals(toPens[j]))
                        {
                            index = j;
                            j = toCounter;
                        }
                    }
                        
                    toPens[index] = null;
                    toLowerBounds[index] = 0;
                    toUpperBounds[index] = 0;
                    toClassSizes[index] = null;
                    isGoodQuality.remove(index);
                    capacities[index] = 0;
                    capacityCounters[index] = 0;
                            
                    toPens = stringShift(toPens);
                    toLowerBounds = intShift(toLowerBounds);
                    toUpperBounds = intShift(toUpperBounds);
                    toClassSizes= stringShift(toClassSizes);
                    capacities = intShift(capacities);
                    capacityCounters = intShift(capacityCounters);
                            
                    toCounter--;
                    if (toCounter == 0)
                    {
                        hasToPen = false;
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
            //1st transfer page
        else if (transferPage1)
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
            //2nd transfer page
        else if (transferPage2)
        {
            panel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(20, 5, 20, 5);
            
            Dimension size = new Dimension((int)(width/8), (int)(height/10));
            
            goodBelly.setFont(font1);
            badBelly.setFont(font1);
            goodBelly.setPreferredSize(size);
            badBelly.setPreferredSize(size);
            
            c.gridy = 0;
            c.gridx = 0;
            panel.add(goodBelly, c);
            
            c.gridx = 1;
            panel.add(badBelly, c);
            
            c.gridwidth = 2;
            c.gridy = 1;
            c.gridx = 0;
            panel.add(cancel, c);
        }
            //3rd transfer and 2nd harvest page
        else if (transferPage3 || harvestPage2)
        {
            Dimension size = new Dimension((int)(width/7), (int)(height/9));
            
            panel.setLayout(new BorderLayout());
            Panel panel2 = new Panel(new FlowLayout());
            JLabel label = new JLabel("Select Belly Size");
            label.setFont(font2);
            panel2.add(label);
            Panel panel3 = new Panel(new FlowLayout());
            String temp = (previousRow != null) ? previousRow.get("Belly Size").toString() : "";
            if (isInteger(temp))
            {
                for (int i = Integer.parseInt(temp) - 10; i < Integer.parseInt(temp) + 20; i++)
                {
                    numbers[i].setPreferredSize(size);
                    numbers[i].setFont(font1);
                    panel3.add(numbers[i]);
                }
            }
            else
            {
                for (int i = 15; i < 40; i++)
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
            //4rd transfer and 3rd harvest page
        else if (transferPage4 || harvestPage3)
        {
            Dimension size = new Dimension((int)(width/7), (int)(height/9));
            
            panel.setLayout(new BorderLayout());
            Panel panel2 = new Panel(new FlowLayout());
            JLabel label = new JLabel("Select Length");
            label.setFont(font2);
            panel2.add(label);
            Panel panel3 = new Panel(new FlowLayout());
            String temp = (previousRow != null) ? previousRow.get("Length").toString() : "";
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
            //5th transfer and 4th harvest page
        else if (transferPage5 || harvestPage4)
        {
            Dimension size = new Dimension((int)(width/7), (int)(height/9));
            
            panel.setLayout(new BorderLayout());
            Panel panel2 = new Panel(new FlowLayout());
            JLabel label = new JLabel("Select Weight");
            label.setFont(font2);
            panel2.add(label);
            Panel panel3 = new Panel(new FlowLayout());
            String temp = (previousRow != null) ? previousRow.get("Weight").toString() : "";
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
            //6th transfer page
        else if (transferPage6)
        {
            System.out.println(vaccinatedDate);
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
            
            String fromPen = (previousRow != null) ? previousRow.get("To").toString() : "";
            
            JLabel tempLabel4 = new JLabel("" + fromPen);
            tempLabel4.setFont(font1);
            cRight.gridx = 1;
            cRight.gridy = 1;
            panel.add(tempLabel4, cRight);
            
            JLabel tempLabel5 = new JLabel("To Pen: ");
            tempLabel5.setFont(font1);
            cLeft.gridx = 0;
            cLeft.gridy = 2;
            panel.add(tempLabel5, cLeft);
            
            java.util.List<String> toPensArrayList = new ArrayList<>();
            toPensArrayList.add(null);
            for (String pen : toPens)
            {
                if (pen != null)
                {
                    toPensArrayList.add(pen);
                }
            }
            
            toPensComboBox = new JComboBox(toPensArrayList.toArray());
            toPensComboBox.setSelectedItem(toPen);
            toPensComboBox.setFont(font1);
            
            cRight.gridx = 1;
            cRight.gridy = 2;
            panel.add(toPensComboBox, cRight);

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
            //quit page exits the application
        else if (quit)
        {         
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            System.exit(0);
        }
            //if its none of the other options, error window
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
                harvestPage3 = false;
                harvestPage4 = false;
                harvestPage5 = false;
                addTo = false;
                removeTo = false;
                transferPage1 = false;
                transferPage2 = false;
                transferPage3 = false;
                transferPage4 = false;
                transferPage5 = false;
                transferPage6 = false;
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
        
            //add the panel to the frame
        contentPane.add(panel);
        validate();
        setVisible(true);
    }
    
        //initialize the frame and its components
    public static void createAndShowGUI()
    {
        frame = new Application();
        frame.addListeners();
        frame.initializeButtonArray();
        frame.initialize();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addComponents();
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);   
    }
    
    public static void main(String[] args)
    {
        createAndShowGUI();
    }
    
        //helper method to remove null's from a string array and shifts the array left to fill those places occupied by null
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
    
        //helper method to remove 0's from an int array and shifts the array left to fill those places left by 0
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
    
        //checks if a given string is an integer
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
    
        //set up the serial stream
    public void initialize()
    {  
	CommPortIdentifier portId = null;
        try
        {
            portId = CommPortIdentifier.getPortIdentifier(portNumber);
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
    
        //close the serial stream
    public synchronized void close()
    {
	if (serialPort != null)
        {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }
    
        //event method thrown when new data is detected in the serial stream
    @Override
    public synchronized void serialEvent(SerialPortEvent oEvent)
    {
        String temp;
	if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        {
            try
            {
                    //in every case, read the information and flush the stream
                temp = serialInput.readLine();
                int index = temp.indexOf('.');
                tag = temp.substring(index + 1);
                    //if on the 1st transfer page, read the tag, get the previous row in the gator database, and move to the next transfer page
                if (transferPage1)
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
                    
                    transferPage1 = false;
                    transferPage2 = true;
                    addComponents();
                }
                    //if on the 1st harvest page, read the tag, get the previous row in the gator database, and move to the next harvest page
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
                    //if on the first new gator page, read the tag then move to the next new gator page
                else if (newGatorPage1)
                {
                    newGatorPage1 = false;
                    newGatorPage2 = true;
                    addComponents();                   
                }
                    //in every other case just ignore the event
            }
            catch (Exception e)
            {
                System.err.println(e.toString());
            }
        }
    }
    
        //add event listeners to the components
    public void addListeners()
    {
            //button to skip length and weight measurements
            //page3 = length, page4 = weight
        skip.addActionListener(e -> {
            if (transferPage4)
            {
                skipLength = true;
                transferPage5 = true;
                transferPage4 = false;
                addComponents();
            }
            else if (transferPage5)
            {
                skipWeight = true;
                transferPage5 = false;
                transferPage6 = true;
                addComponents();
            }
            else if (harvestPage3)
            {
                skipLength = true;
                harvestPage3 = false;
                harvestPage4 = true;
                addComponents();
            }
            else if (harvestPage4)
            {
                skipWeight = true;
                harvestPage4 = false;
                harvestPage5 = true;
                addComponents();
            }
        });
        
            //button on the start screen that begins the new hatchling option
        addNewGator.addActionListener(e -> {
            start = false;
            newGatorPage1 = true;
            newGatorPage2 = false;
            harvestPage1 = false;
            harvestPage2 = false;
            transferStart = false;
            addTo = false;
            removeTo = false;
            transferPage1 = false;
            transferPage2 = false;
            quit = false;
            addComponents();
        });
        
            //button on the start screen that begins the transfer gator option
        transferGator.addActionListener(e -> {
            start = false;
            transferStart = true;
            newGatorPage1 = false;
            newGatorPage2 = false;
            harvestPage1 = false;
            harvestPage2 = false;
            addTo = false;
            removeTo = false;
            transferPage1 = false;
            transferPage2 = false;
            quit = false;
            addComponents();
        });
        
            //button on the start screen that begins the harvest gator option
        harvestGator.addActionListener(e -> {
            start = false;
            newGatorPage1 = false;
            newGatorPage2 = false;
            transferStart = false;
            harvestPage1 = true;
            harvestPage2 = false;
            addTo = false;
            removeTo = false;
            transferPage1 = false;
            transferPage2 = false;
            quit = false;
            addComponents();
        });
        
            //button on the start screen that closes the application
        quitButton.addActionListener(e -> {
            start = false;
            transferStart = false;
            newGatorPage1 = false;
            newGatorPage2 = false;
            harvestPage1 = false;
            harvestPage2 = false;
            addTo = false;
            removeTo = false;
            transferPage1 = false;
            transferPage2 = false;
            quit = true;
            addComponents();
        });
        
            //button in the transfer option that leads to the page that adds "to" pens
        addToPen.addActionListener(e -> {
            start = false;
            transferStart = false;
            newGatorPage1 = false;
            newGatorPage2 = false;
            harvestPage1 = false;
            harvestPage2 = false;
            addTo = true;
            removeTo = false;
            transferPage1 = false;
            transferPage2 = false;
            quit = false;
            addComponents();
        });
        
            //button in the transfer option that leads to the page that removes "to" pens
        removeToPen.addActionListener(e -> {
            start = false;
            transferStart = false;
            newGatorPage1 = false;
            newGatorPage2 = false;
            harvestPage1 = false;
            harvestPage2 = false;
            addTo = false;
            removeTo = true;
            transferPage1 = false;
            transferPage2 = false;
            quit = false;
            addComponents();
        });
        
            //button in the transfer option that leads to the page that begins the transfer procedure
        addEntry.addActionListener(e -> {
            start = false;
            transferStart = false;
            newGatorPage1 = false;
            newGatorPage2 = false;
            harvestPage1 = false;
            harvestPage2 = false;
            addTo = false;
            removeTo = false;
            transferPage1 = true;
            transferPage2 = false;
            quit = false;
            addComponents();
        });
        
            //button in each of the options that leads back to the start screen
        back.addActionListener(e -> {
            start = true;
            transferStart = false;
            newGatorPage1 = false;
            newGatorPage2 = false;
            newGatorOptionalPage = false;
            harvestPage1 = false;
            harvestPage2 = false;
            addTo = false;
            removeTo = false;
            transferPage1 = false;
            transferPage2 = false;
            quit = false;
            addComponents();
        });
        
        goodBelly.addActionListener(e -> {
            isGoodBelly = true;
            transferPage2 = false;
            transferPage3 = true;
            addComponents();
        });
        
        badBelly.addActionListener(e -> {
            isGoodBelly = false;
            transferPage2 = false;
            transferPage3 = true;
            addComponents();
        });
        
            //when transferring a gator, leads back to the transfer start screen
        cancel.addActionListener(e -> {
            start = false;
            transferStart = true;
            newGatorPage1 = false;
            newGatorPage2 = false;
            harvestPage1 = false;
            harvestPage2 = false;
            addTo = false;
            removeTo = false;
            transferPage1 = false;
            transferPage2 = false;
            transferPage3 = false;
            transferPage4 = false;
            transferPage5 = false;
            transferPage6 = false;
            quit = false;
            addComponents();
        });
        
            //button that is used on multiple  screens
            //in the add "to" pen screen, add the currently-selected pen to application storage, return to the transfer start page
            //in the last add new gator screen, add the new hatchling to gator database, return to the first new gator page
            //in the last transfer screen, add the transfer entry to the gator database, return to the first transfer page
            //in the last harvest screen, add the harvest entry to the gator database, return to the first harvest page
        confirm.addActionListener(e -> {
            errorMessage = "";  
            if (addTo)
            {
                penTaken = false;
                for (int i = 0; i < toCounter; i++)
                {
                    if (penList.getSelectedItem().toString().equals(toPens[i]))
                    {
                        penTaken = true;
                        i = toCounter;
                    }
                }
                if (penTaken)
                {
                    errorMessage = "Pen taken";
                }
                else
                {
                    String pen = penList.getSelectedItem().toString();
                    String classSize = "";
                    String quality = "";
                        
                    try
                    {
                        IndexCursor cursor = CursorBuilder.createCursor(penTable.getIndex("PenNumberIndex"));                            
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
                        quality = latestRow.get("Belly Quality").toString();
                    }
                    catch (IOException e1)
                    {      
                        
                    }
                    
                    if ("Empty".equals(classSize))
                    {
                        errorMessage = "Cannot transfer to designated empty pen";
                    }
                    else if ("Hatchling".equals(classSize) || "Family".equals(classSize))
                    {
                        toPens[toCounter] = pen;
                        toLowerBounds[toCounter] = -1;
                        toUpperBounds[toCounter] = -1;
                        toClassSizes[toCounter] = classSize;
                        isGoodQuality.add( (quality.equals("Good")) );
                        capacities[toCounter] = Integer.parseInt(capacityInput.getText());
                        capacityCounters[toCounter] = 0;
                        hasToPen = true;
                        toCounter++;
                    }
                    else if (classSize.contains("+"))
                    {
                        toPens[toCounter] = pen;
                        toLowerBounds[toCounter] = Integer.parseInt(classSize.substring(0, 2));
                        toUpperBounds[toCounter] = 46;
                        toClassSizes[toCounter] = classSize;
                        isGoodQuality.add( (quality.equals("Good")) );
                        capacities[toCounter] = Integer.parseInt(capacityInput.getText());
                        capacityCounters[toCounter] = 0;
                        hasToPen = true;
                        toCounter++;
                    }
                    else
                    {
                        int index = classSize.indexOf('-');
                        toPens[toCounter] = pen;
                        toLowerBounds[toCounter] = Integer.parseInt(classSize.substring(0, index));
                        toUpperBounds[toCounter] = Integer.parseInt(classSize.substring(index+1));
                        toClassSizes[toCounter] = classSize;
                        isGoodQuality.add( (quality.equals("Good")) );
                        capacities[toCounter] = Integer.parseInt(capacityInput.getText());
                        capacityCounters[toCounter] = 0;
                        hasToPen = true;
                        toCounter++;
                    }
                }   
            }
            else if (transferPage6)
            {
                String from = "";
                String to = "";
                try
                {
                    to = toPensComboBox.getSelectedItem().toString();
                    if (isVaccinated)
                    {
                        vaccinatedDate = vaccinateField.getText();
                    }
                    
                    if (isFormula)
                    {
                        formulaDate = formulaField.getText();
                    }
                    
                    if (previousRow != null)
                    {
                        from = previousRow.get("To").toString();
                        String lengthEntry = (skipLength) ? previousRow.get("Length").toString() : length;
                        String weightEntry = (skipWeight) ? previousRow.get("Weight").toString() : weight;
                            
                        gatorTable.addRow(0, tag, previousRow.get("Egg Collection Date"), previousRow.get("Egg Nest Location"), previousRow.get("Foot Tag"), previousRow.get("Hatchling Length"), previousRow.get("Hatchling Weight"), previousRow.get("Hatch Year"), previousRow.get("Gender"), previousRow.get("Umbilical"), currentDate, from, to, bellySize, lengthEntry, weightEntry, formulaDate, experimentalCode.getText(), vaccinatedDate, comments.getText(), "");
                    }
                    else
                    {
                        String lengthEntry = (skipLength) ? "" : length;
                        String weightEntry = (skipWeight) ? "" : weight;
                            
                        gatorTable.addRow(0, tag, "", "", "", "", "", "", "", "", currentDate, from, to, bellySize, lengthEntry, weightEntry, formulaDate, experimentalCode.getText(), vaccinatedDate, comments.getText(), "");
                    }
                    IndexCursor cursor = CursorBuilder.createCursor(gatorTable.getIndex("IDIndex"));
                    cursor.beforeFirst();
                    for(Map<String,Object> row : cursor)
                    {
 
                    }
                    
                    addedRows++;
                    BufferedWriter writer = new BufferedWriter(new FileWriter(rowsToSync, false));
                    writer.write("" + addedRows);
                    writer.close();
                }
                catch (IOException e1)
                {    
                }
                toPenIndex = Arrays.asList(toPens).indexOf(to);
                if (toPenIndex != -1)
                {
                    capacityCounters[toPenIndex]++;
                }
                if(toPenIndex != -1 && capacities[toPenIndex] == capacityCounters[toPenIndex])
                {
                    pensAtCapacity[pensAtCapacityCounter] = toPens[toPenIndex];
                    pensAtCapacityAmount[pensAtCapacityCounter] = capacities[toPenIndex];
                    pensAtCapacityRange[pensAtCapacityCounter] = toLowerBounds[toPenIndex] + "-" + toUpperBounds[toPenIndex];
                    pensAtCapacityCounter++;
                    toPens[toPenIndex] = null;
                    toLowerBounds[toPenIndex] = 0;
                    toUpperBounds[toPenIndex] = 0;
                    toClassSizes[toPenIndex] = null;
                    isGoodQuality.remove(toPenIndex);
                    capacities[toPenIndex] = 0;
                    capacityCounters[toPenIndex] = 0;
                            
                    toPens = stringShift(toPens);
                    toLowerBounds = intShift(toLowerBounds);
                    toUpperBounds = intShift(toUpperBounds);
                    toClassSizes = stringShift(toClassSizes);
                    capacities = intShift(capacities);
                    capacityCounters = intShift(capacityCounters);
                            
                    toCounter--;
                    if (toCounter == 0)
                    {
                        hasToPen = false;
                    }
                            
                    errorMessage = "Capacity reached on Pen " + toPen;
                    start = false;
                    harvestPage1 = false;
                    harvestPage2 = false;
                    newGatorPage1 = false;
                    newGatorPage2 = false;
                    transferStart = false;
                    addTo = false;
                    removeTo = false;
                    transferPage1 = false;
                    transferPage2 = false;
                    quit = false;
                    addComponents();
                }
                    
                toPen = "";
                toPenIndex = -1;
                
                int index = fromPens.indexOf(from);
                if (index == -1)
                {
                    fromPens.add(from);
                    fromPensAmount.add(1);
                }
                else
                {
                    fromPensAmount.set(index, fromPensAmount.get(index) + 1);
                }
                
                outputFile();
            }
            else if (newGatorPage2)
            {
                try
                {
                    String footTag = eggNumber.getText();
                    String newTag;
                    int index = footTag.indexOf("-");
                    
                    if (index == -1)
                    {
                        newTag = StringUtils.leftPad(footTag, 5, "0");
                    }
                    else
                    {
                        String s1 = footTag.substring(0, index);
                        String s2 = footTag.substring(index + 1);
                        
                        newTag = StringUtils.leftPad(s1, 5, "0") + "-" + StringUtils.leftPad(s2, 5, "0");
                    }
                    
                    
                    gatorTable.addRow(0, tag, collectionDate.getText(), eggLocation.getText(), newTag, eggLength.getText(), eggWeight.getText(), currentDate.substring(6), gender.getSelectedItem().toString(), umbilical.getSelectedItem().toString(), currentDate, "", penList.getSelectedItem().toString(), "", "", "", "", "", "", comments.getText(), "");
                    IndexCursor cursor = CursorBuilder.createCursor(gatorTable.getIndex("IDIndex"));
                    cursor.beforeFirst();
                    for(Map<String,Object> row : cursor)
                    {
 
                    }
                    
                    index = toPensNewGator.indexOf(penList.getSelectedItem().toString());
                    if (index == -1)
                    {
                        toPensNewGator.add(penList.getSelectedItem().toString());
                        toPensNewGatorAmount.add(1);
                    }
                    else
                    {
                        toPensNewGatorAmount.set(index, toPensNewGatorAmount.get(index) + 1);
                    }
                    
                    addedRows++;
                    BufferedWriter writer = new BufferedWriter(new FileWriter(rowsToSync, false));
                    writer.write("" + addedRows);
                    writer.close();
                }
                catch (IOException e1)
                {
                        
                }
                outputFile();
            }
            else if (harvestPage5)
            {
                try
                {
                    String lengthEntry = (skipLength) ? previousRow.get("Length").toString() : length;
                    String weightEntry = (skipWeight) ? previousRow.get("Weight").toString() : weight;
                        
                    gatorTable.addRow(0, tag, previousRow.get("Egg Collection Date"), previousRow.get("Egg Nest Location"), previousRow.get("Foot Tag"), previousRow.get("Hatchling Length"), previousRow.get("Hatchling Weight"), previousRow.get("Hatch Year"), previousRow.get("Gender"), previousRow.get("Umbilical"), currentDate, previousRow.get("To"), "", bellySize, lengthEntry, weightEntry, previousRow.get("Special Recipe"), "", previousRow.get("Vaccinated"), comments.getText(), "Yes");
                    for(Map<String,Object> row : CursorBuilder.createCursor(gatorTable.getIndex("IDIndex")))
                    {
 
                    }
                    
                    addedRows++;
                    BufferedWriter writer = new BufferedWriter(new FileWriter(rowsToSync, false));
                    writer.write("" + addedRows);
                    writer.close();
                }
                catch (IOException e1)
                {
                        
                }
            }
                
                
            if (!errorMessage.equals(""))
            {
                transferStart = false;
                transferPage1 = false;
                newGatorPage1 = false;
                harvestPage1 = false;
            }
            else if (transferPage6)
            {
                transferStart = false;
                transferPage1 = true;
                newGatorPage1 = false;
                harvestPage1 = false;
            }
            else if (newGatorPage2)
            {
                transferStart = false;
                transferPage1 = false;
                newGatorPage1 = true;
                harvestPage1 = false;
            }
            else if (harvestPage5)
            {
                transferStart = false;
                transferPage1 = false;
                newGatorPage1 = false;
                harvestPage1 = true;
            }
            else
            {
                transferStart = true;
                transferPage1 = false;
                newGatorPage1 = false;
                harvestPage1 = false;
            }
            start = false;
            newGatorPage2 = false;
            harvestPage5 = false;
            addTo = false;
            removeTo = false;
            transferPage6 = false;
            quit = false;
            addComponents();
        });
        
            //resize the scroll bar to make it more useable on a touchscreen
            //also when a pen that has already been selected when adding a "to" pen is selected again,
            //throw penTaken flag
        penList.addPopupMenuListener(new PopupMenuListener()
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
                if (addTo)
                {
                    penTaken = false;
                    for (int i = 0; i < toCounter; i++)
                    {
                        if (penList.getSelectedItem().toString().equals(toPens[i]))
                        {
                            penTaken = true;
                            i = toCounter;
                        }
                    }
                    confirm.setEnabled(!penTaken && isInteger(capacityInput.getText()) && Integer.parseInt(capacityInput.getText()) > 0);
                }
            }
            
            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
            {
                if (addTo)
                {
                    penTaken = false;
                    for (int i = 0; i < toCounter; i++)
                    {
                        if (penList.getSelectedItem().toString().equals(toPens[i]))
                        {
                            penTaken = true;
                            i = toCounter;
                        }
                    }
                    confirm.setEnabled(!penTaken && isInteger(capacityInput.getText()) && Integer.parseInt(capacityInput.getText()) > 0);
                }
            }
        });
        
            //when the capacity input is not an integer, don't allow the user to confirm
        capacityInput.getDocument().addDocumentListener(new DocumentListener()
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
                confirm.setEnabled(!penTaken && isInteger(capacityInput.getText()) && Integer.parseInt(capacityInput.getText()) > 0);
            }
        });
        
            //toggle enable on vaccinateField when this button is clicked
            //If the gator has been vaccinated, this button is clicked the field is enabled and the date entered in the field is stored into the database for this record
            //If the gator hasn't been vaccinated, this button is clicked again the field is disabled and the vaccination date from the previous record is stored for this record
        didVaccinate.addActionListener(e -> {
            if (isVaccinated)
            {
                vaccinatedDate = (previousRow != null) ? previousRow.get("Vaccinated").toString() : "";
            }
            else
            {
                vaccinatedDate = vaccinateField.getText();
            }
            isVaccinated = !isVaccinated;
            vaccinateField.setEnabled(isVaccinated);     
        });
        
            //toggle enable on formulaField when this button is click
            //works in the same way as didVaccination
        didFormula.addActionListener(e -> {
            if (isFormula)
            {
                formulaDate = (previousRow != null) ? previousRow.get("Special Recipe").toString() : "";
            }
            else
            {
                formulaDate = formulaField.getText();
            }
            isFormula = !isFormula;
            formulaField.setEnabled(isFormula);
        });
        
        eggNumber.getDocument().addDocumentListener(new DocumentListener()
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
                confirm.setEnabled(eggNumber.getText().length() > 0 && eggLength.getText().length() > 0 && eggWeight.getText().length() > 0);
            }
        });
        
        eggLength.getDocument().addDocumentListener(new DocumentListener()
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
                confirm.setEnabled(eggNumber.getText().length() > 0 && eggLength.getText().length() > 0 && eggWeight.getText().length() > 0);
            }
        });
        
        eggWeight.getDocument().addDocumentListener(new DocumentListener()
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
                confirm.setEnabled(eggNumber.getText().length() > 0 && eggLength.getText().length() > 0 && eggWeight.getText().length() > 0);
            }
        });
    }
    
        //initialize the list of numbered buttons 0-200 which are used as belly size, length and weight inputs
        //in the transfer and harvest options
    public void initializeButtonArray()
    {
        for (int i = 0; i <= 200; i++)
        {
            JButton button = new JButton("" + i);
            button.addActionListener(e -> {
                String entry = ((JButton) e.getSource()).getText();
                int number = Integer.parseInt(entry);
                if (transferPage3)
                {
                    bellySize = number;
                    String classSize = "";
                        
                    for (int j = 0; j < toCounter; j++)
                    {
                        try
                        {
                            IndexCursor cursor = CursorBuilder.createCursor(penTable.getIndex("PenNumberIndex"));                            
                            cursor.beforeFirst();
                            cursor.findFirstRow(Collections.singletonMap("Pen Number", toPens[j]));
                            Row latestRow = cursor.getCurrentRow();
                            while (cursor.findNextRow(Collections.singletonMap("Pen Number", toPens[j])))
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
                        
                        if (classSize.equals("Family") || (number >= toLowerBounds[j] && number <= toUpperBounds[j] && isGoodQuality.get(j) == isGoodBelly) || (entry.equals("Hatchling") && classSize.equals("Hatchling")))
                        {
                            toPen = toPens[j];
                            toPenIndex = j;
                            j = toCounter;
                        }
                    }
                    transferPage3 = false;
                    transferPage4 = true;
                    addComponents();
                }
                else if (transferPage4)
                {
                    length = entry;
                    transferPage4 = false;
                    transferPage5 = true;
                    addComponents();
                }
                else if (transferPage5)
                {
                    weight = entry;
                    transferPage5 = false;
                    transferPage6 = true;
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
    
    public void outputFile()
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, false)))
        {
            Date currentTime = Calendar.getInstance().getTime();
            
            DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat df2 = new SimpleDateFormat("h:mm a");
            
            writer.write(df1.format(startTime));
            writer.newLine();
            writer.write(df2.format(startTime) + " - " + df2.format(currentTime));
            writer.newLine();
            writer.newLine();
            
            writer.write("Transferred Gators");
            writer.newLine();
            writer.newLine();
            
            for (int i = 0; i < fromPens.size(); i++)
            {
                String fromPen = (fromPens.get(i).equals("")) ? "No Pen" : "Pen " + fromPens.get(i);
                
                writer.write("\tFrom " + fromPen + ": " + fromPensAmount.get(i));
                writer.newLine();
            }
            
            for (int i = 0; i < toPens.length; i++)
            {
                if (toPens[i] != null)
                {
                    writer.write("\tTo Pen " + toPens[i] + ": " + capacityCounters[i]);
                    writer.newLine();
                }
            }
            
            for (int i = 0; i < pensAtCapacity.length; i++)
            {
                if (pensAtCapacity[i] != null)
                {
                    writer.write("\tTo Pen " + pensAtCapacity[i] + ": " + pensAtCapacityAmount[i]);
                    writer.newLine();
                }
            }
            
            writer.newLine();
            writer.write("New Gators");
            writer.newLine();
            writer.newLine();
            
            for (int i = 0; i < toPensNewGator.size(); i++)
            {
                writer.write("\t" + toPensNewGator.get(i) + ": " + toPensNewGatorAmount.get(i));
                writer.newLine();
            }
            
            writer.close();
        }
        catch (IOException e)
        {
            
        }
    }
}