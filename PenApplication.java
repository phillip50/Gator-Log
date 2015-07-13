/**
 * Pen Application
 * Parses the pen database to view the current state of each pen on the farm
 * From there, modifications can be made to the pen's attributes and the gators currently in the pen can be viewed as well
 * 
 * @Phillip Dingler [phil50@ufl.edu]
 */

//TODO: load all rows at begininng of application

package test;

import javax.swing.*;
import java.awt.*;
import com.healthmarketscience.jackcess.*;
import java.io.*;
import java.util.*;

public class PenApplication extends JFrame
{
        //"this" objest
    private static PenApplication frame;
    
        //list of JButtons which correspond to the pens on the farm
    private final JButton[] pens;
    
    
    private final JButton modifyClass;
    private final JButton modifyQuarters;
    
    
    private final java.util.List<Gator> gatorRows;
    
        //pen database files
    private File penFile;
    private Table penTable;
    
    private File gatorFile;
    private Table gatorTable;
    
    
    private final String[] quarteredPens;
    
        //picture of the farm
    Image image;
    
    public PenApplication()
    {
        super("Pen Application");
        
            //read in the pen database file
        try
        {
            penFile = new File("PenDatabase.accdb");
            penTable = DatabaseBuilder.open(penFile).getTable("Database");
            
            gatorFile = new File("AnimalDatabase.accdb");
            gatorTable = DatabaseBuilder.open(gatorFile).getTable("Database");
        }
        catch (IOException e)
        {
        }
            
            //initialize the list buttons
        pens = new JButton[169];
        
        modifyClass = new JButton("Modify Class Sizes");
        modifyQuarters = new JButton("Change Quartered Pens");
        
        String temp = "";      
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("QuarteredPens.txt"));
            temp = reader.readLine();
        }
        catch (IOException e)
        {
            
        }
        quarteredPens = temp.split(",");
        
        gatorRows = new ArrayList<>();
        try
        {
            int i = 0;
            com.healthmarketscience.jackcess.Cursor cursor = CursorBuilder.createCursor(gatorTable);
            cursor.beforeFirst();
            while (cursor.moveToNextRow())
            {
                Row currentRow = cursor.getCurrentRow();
                
                if (currentRow.get("ID") == null)
                {
                    System.out.println("" + i);
                }
                
                Gator gator = new Gator();
                gator.ID = Integer.parseInt( currentRow.get("ID").toString() );
                gator.tagNumber = Integer.parseInt( currentRow.get("Tag Number").toString() );
                gator.eggNestLocation = currentRow.get("Egg Nest Location").toString();
                gator.eggNestCondition = currentRow.get("Egg Nest Condition").toString();
                gator.eggCollectionDate = currentRow.get("Egg Collection Date").toString();
                gator.hatchYear = currentRow.get("Hatch Year").toString();
                gator.gender = currentRow.get("Gender").toString();
                gator.umbilical = currentRow.get("Umbilical").toString();
                gator.date = currentRow.get("Date").toString();
                gator.from = currentRow.get("From").toString();
                gator.to = currentRow.get("To").toString();
                gator.bellySize = currentRow.get("Belly Size").toString();
                gator.length = currentRow.get("Length").toString();
                gator.weight = currentRow.get("Weight").toString();
                gator.recipe = currentRow.get("Special Recipe").toString();
                gator.code = currentRow.get("Experiment Code").toString();
                gator.vaccinate = currentRow.get("Vaccinated").toString();
                gator.comments = currentRow.get("Comments").toString();
                gator.harvested = currentRow.get("Harvested?").toString();
                
                gatorRows.add(gator);
                
                i++;
            }
                //sort the array using a custom comparator
            Collections.sort(gatorRows, new GatorComparator());
        }
        catch (IOException e)
        {
            
        }
    }
    
        //when a pen button has been clicked, this method is called to create a new window
        //this window displays more information about the pen that was clicked
    public void modifyWindow(java.util.List<Row> penRows, String penNumber)
    {
        ModifyWindow modifyFrame = new ModifyWindow(penRows, penNumber, gatorRows);
        modifyFrame.setFrame(modifyFrame);
        modifyFrame.addGators(); 
        modifyFrame.Initialize();
        modifyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        double length = rect.getHeight();
        double width = rect.getWidth();
        Dimension screenSize = new Dimension((int)width, (int)length - 50);
        modifyFrame.getContentPane().setPreferredSize(screenSize);
        modifyFrame.addComponents();
        modifyFrame.pack();
        modifyFrame.setLocationRelativeTo(null);
        modifyFrame.setVisible(true);
    }
    
    public void modifyClass()
    {
        ModifyClass modifyFrame = new ModifyClass();
        modifyFrame.setFrame(modifyFrame);
        modifyFrame.initialize();
        modifyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        double length = rect.getHeight();
        double width = rect.getWidth();
        Dimension screenSize = new Dimension((int)width, (int)length - 50);
        modifyFrame.getContentPane().setPreferredSize(screenSize);
        modifyFrame.addComponents();
        modifyFrame.pack();
        modifyFrame.setLocationRelativeTo(null);
        modifyFrame.setVisible(true);
    }
    
    public void modifyQuarters()
    {
        ModifyQuarters modifyFrame = new ModifyQuarters();
        modifyFrame.setFrame(modifyFrame);
        modifyFrame.initialize();
        modifyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        double length = rect.getHeight();
        double width = rect.getWidth();
        Dimension screenSize = new Dimension((int)width, (int)length - 50);
        modifyFrame.getContentPane().setPreferredSize(screenSize);
        modifyFrame.addComponents();
        modifyFrame.pack();
        modifyFrame.setLocationRelativeTo(null);
        modifyFrame.setVisible(true);
    }

        //add the buttons to the frame
    public void addComponents()
    {
        for (JButton pen : pens)
        {
            frame.add(pen);
        }
        frame.add(modifyClass);
        frame.add(modifyQuarters);
        validate();
        setVisible(true);
    }

    public static void main(String[] args)
    {
        frame = new PenApplication();
        frame.InitializeButtons();
        frame.setContentPane(new JPanel()
        {
            Image image = Toolkit.getDefaultToolkit().createImage("farm.jpg");
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this);
            }
        });
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = new Dimension(990, 900);
        frame.getContentPane().setPreferredSize(screenSize);
        frame.addComponents();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
        //method to initialize each pen button
        //the buttons use absolute positioning, and are placed over top of their corresponding pens in the farm image file
        //the buttons are then set invisible
    public void InitializeButtons()
    {
        modifyClass.setBounds(250, 820, 200, 50);
        modifyClass.addActionListener(e -> {
            modifyClass();
        });
        
        modifyQuarters.setBounds(550, 820, 200, 50);
        modifyQuarters.addActionListener(e -> {
            modifyQuarters();
        });
        
        for (int i = 1; i < 170; i++)
        {
            JButton button;
            String penNumber;
            int x, y, xlength, ylength; //x,y = corrdinates; xlength,ylength = length and width
            if (i == 1)
            {
                penNumber = "101";
                x = 75;
                y = 253;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 2)
            {
                penNumber = "102";
                x = 85;
                y = 304;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 3)
            {
                penNumber = "103";
                x = 95;
                y = 353;
                xlength = 45;
                ylength = 47;
            }
            else if (i == 4)
            {
                penNumber = "104";
                x = 104;
                y = 404;
                xlength = 46;
                ylength = 47;
            }
            else if (i == 5)
            {
                penNumber = "105";
                x = 113;
                y = 454;
                xlength = 46;
                ylength = 48;
            }
            else if (i == 6)
            {
                penNumber = "106";
                x = 123;
                y = 506;
                xlength = 47;
                ylength = 46;
            }
            else if (i == 7)
            {
                penNumber = "107";
                x = 133;
                y = 557;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 8)
            {
                penNumber = "108";
                x = 145;
                y = 608;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 9)
            {
                penNumber = "109";
                x = 153;
                y = 658;
                xlength = 47;
                ylength = 46;
            }
            else if (i == 10)
            {
                penNumber = "110";
                x = 124;
                y = 178;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 11)
            {
                penNumber = "111";
                x = 136;
                y = 242;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 12)
            {
                penNumber = "112";
                x = 147;
                y = 292;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 13)
            {
                penNumber = "113";
                x = 156;
                y = 342;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 14)
            {
                penNumber = "114";
                x = 165;
                y = 392;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 15)
            {
                penNumber = "115";
                x = 174;
                y = 443;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 16)
            {
                penNumber = "116";
                x = 184;
                y = 495;
                xlength = 47;
                ylength = 46;
            }
            else if (i == 17)
            {
                penNumber = "117";
                x = 195;
                y = 545;
                xlength = 46;
                ylength = 45;
            }
            else if (i == 18)
            {
                penNumber = "118";
                x = 206;
                y = 595;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 19)
            {
                penNumber = "119";
                x = 214;
                y = 646;
                xlength = 47;
                ylength = 46;
            }
            else if (i == 20)
            {
                penNumber = "120";
                x = 224;
                y = 696;
                xlength = 47;
                ylength = 45;
            }
            else if (i == 21)
            {
                penNumber = "121";
                x = 166;
                y = 117;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 22)
            {
                penNumber = "122";
                x = 175;
                y = 168;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 23)
            {
                penNumber = "123";
                x = 188;
                y = 232;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 24)
            {
                penNumber = "124";
                x = 197;
                y = 282;
                xlength = 46;
                ylength = 47;
            }
            else if (i == 25)
            {
                penNumber = "125";
                x = 207;
                y = 333;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 26)
            {
                penNumber = "126";
                x = 216;
                y = 384;
                xlength = 46;
                ylength = 45;
            }
            else if (i == 27)
            {
                penNumber = "127";
                x = 226;
                y = 434;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 28)
            {
                penNumber = "201";
                x = 227;
                y = 108;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 29)
            {
                penNumber = "202";
                x = 235;
                y = 157;
                xlength = 46;
                ylength = 44;
            }
            else if (i == 30)
            {
                penNumber = "203";
                x = 250;
                y = 221;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 31)
            {
                penNumber = "204";
                x = 259;
                y = 272;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 32)
            {
                penNumber = "205";
                x = 267;
                y = 322;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 33)
            {
                penNumber = "206";
                x = 276;
                y = 371;
                xlength = 46;
                ylength = 44;
            }
            else if (i == 34)
            {
                penNumber = "207";
                x = 286;
                y = 419;
                xlength = 44;
                ylength = 42;
            }
            else if (i == 35)
            {
                penNumber = "208";
                x = 305;
                y = 465;
                xlength = 44;
                ylength = 43;
            }
            else if (i == 36)
            {
                penNumber = "209";
                x = 307;
                y = 518;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 37)
            {
                penNumber = "210";
                x = 308;
                y = 573;
                xlength = 44;
                ylength = 43;
            }
            else if (i == 38)
            {
                penNumber = "211";
                x = 308;
                y = 627;
                xlength = 46;
                ylength = 44;
            }
            else if (i == 39)
            {
                penNumber = "212";
                x = 311;
                y = 677;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 40)
            {
                penNumber = "213";
                x = 313;
                y = 729;
                xlength = 44;
                ylength = 44;
            }
            else if (i == 41)
            {
                penNumber = "214";
                x = 300;
                y = 212;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 42)
            {
                penNumber = "215";
                x = 310;
                y = 263;
                xlength = 44;
                ylength = 46;
            }
            else if (i == 43)
            {
                penNumber = "216";
                x = 318;
                y = 316;
                xlength = 46;
                ylength = 44;
            }
            else if (i == 44)
            {
                penNumber = "217";
                x = 328;
                y = 366;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 45)
            {
                penNumber = "218";
                x = 336;
                y = 415;
                xlength = 44;
                ylength = 42;
            }
            else if (i == 46)
            {
                penNumber = "219";
                x = 361;
                y = 463;
                xlength = 44;
                ylength = 44;
            }
            else if (i == 47)
            {
                penNumber = "220";
                x = 361;
                y = 517;
                xlength = 46;
                ylength = 45;
            }
            else if (i == 48)
            {
                penNumber = "221";
                x = 364;
                y = 571;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 49)
            {
                penNumber = "222";
                x = 364;
                y = 625;
                xlength = 46;
                ylength = 45;
            }
            else if (i == 50)
            {
                penNumber = "223";
                x = 367;
                y = 676;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 51)
            {
                penNumber = "224";
                x = 369;
                y = 727;
                xlength = 44;
                ylength = 44;
            }
            else if (i == 52)
            {
                penNumber = "225";
                x = 460;
                y = 453;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 53)
            {
                penNumber = "226";
                x = 462;
                y = 506;
                xlength = 43;
                ylength = 44;
            }
            else if (i == 54)
            {
                penNumber = "227";
                x = 463;
                y = 574;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 55)
            {
                penNumber = "228";
                x = 467;
                y = 626;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 56)
            {
                penNumber = "229";
                x = 521;
                y = 451;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 57)
            {
                penNumber = "230";
                x = 523;
                y = 504;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 58)
            {
                penNumber = "231";
                x = 524;
                y = 571;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 59)
            {
                penNumber = "232";
                x = 527;
                y = 624;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 60)
            {
                penNumber = "301";
                x = 343;
                y = 51;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 61)
            {
                penNumber = "302";
                x = 352;
                y = 105;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 62)
            {
                penNumber = "303";
                x = 367;
                y = 186;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 63)
            {
                penNumber = "304";
                x = 375;
                y = 240;
                xlength = 47;
                ylength = 45;
            }
            else if (i == 64)
            {
                penNumber = "305";
                x = 404;
                y = 40;
                xlength = 46;
                ylength = 45;
            }
            else if (i == 65)
            {
                penNumber = "306";
                x = 413;
                y = 93;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 66)
            {
                penNumber = "307";
                x = 428;
                y = 174;
                xlength = 46;
                ylength = 45;
            }
            else if (i == 67)
            {
                penNumber = "308";
                x = 438;
                y = 228;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 68)
            {
                penNumber = "309";
                x = 467;
                y = 28;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 69)
            {
                penNumber = "310";
                x = 476;
                y = 82;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 70)
            {
                penNumber = "311";
                x = 491;
                y = 146;
                xlength = 44;
                ylength = 44;
            }
            else if (i == 71)
            {
                penNumber = "312";
                x = 516;
                y = 19;
                xlength = 44;
                ylength = 46;
            }
            else if (i == 72)
            {
                penNumber = "313";
                x = 526;
                y = 73;
                xlength = 43;
                ylength = 45;
            }
            else if (i == 73)
            {
                penNumber = "314";
                x = 542;
                y = 138;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 74)
            {
                penNumber = "315";
                x = 542;
                y = 188;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 75)
            {
                penNumber = "316";
                x = 543;
                y = 240;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 76)
            {
                penNumber = "317";
                x = 543;
                y = 291;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 77)
            {
                penNumber = "318";
                x = 591;
                y = 34;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 78)
            {
                penNumber = "319";
                x = 591;
                y = 85;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 79)
            {
                penNumber = "320";
                x = 593;
                y = 137;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 80)
            {
                penNumber = "321";
                x = 593;
                y = 188;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 81)
            {
                penNumber = "322";
                x = 594;
                y = 239;
                xlength = 44;
                ylength = 46;
            }
            else if (i == 82)
            {
                penNumber = "323";
                x = 594;
                y = 290;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 83)
            {
                penNumber = "324";
                x = 594;
                y = 341;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 84)
            {
                penNumber = "325";
                x = 595;
                y = 393;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 85)
            {
                penNumber = "326";
                x = 595;
                y = 445;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 86)
            {
                penNumber = "401";
                x = 654;
                y = 34;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 87)
            {
                penNumber = "402";
                x = 653;
                y = 85;
                xlength = 46;
                ylength = 45;
            }
            else if (i == 88)
            {
                penNumber = "403";
                x = 655;
                y = 136;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 89)
            {
                penNumber = "404";
                x = 655;
                y = 187;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 90)
            {
                penNumber = "405";
                x = 656;
                y = 238;
                xlength = 45;
                ylength = 47;
            }
            else if (i == 91)
            {
                penNumber = "406";
                x = 657;
                y = 290;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 92)
            {
                penNumber = "407";
                x = 657;
                y = 341;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 93)
            {
                penNumber = "408";
                x = 659;
                y = 392;
                xlength = 43;
                ylength = 46;
            }
            else if (i == 94)
            {
                penNumber = "409";
                x = 658;
                y = 444;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 95)
            {
                penNumber = "410";
                x = 713;
                y = 526;
                xlength = 46;
                ylength = 47;
            }
            else if (i == 96)
            {
                penNumber = "411";
                x = 723;
                y = 579;
                xlength = 46;
                ylength = 45;
            }
            else if (i == 97)
            {
                penNumber = "412";
                x = 705;
                y = 33;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 98)
            {
                penNumber = "413";
                x = 705;
                y = 84;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 99)
            {
                penNumber = "414";
                x = 706;
                y = 135;
                xlength = 44;
                ylength = 46;
            }
            else if (i == 100)
            {
                penNumber = "415";
                x = 707;
                y = 186;
                xlength = 45;
                ylength = 47;
            }
            else if (i == 101)
            {
                penNumber = "416";
                x = 707;
                y = 237;
                xlength = 45;
                ylength = 47;
            }
            else if (i == 102)
            {
                penNumber = "417";
                x = 708;
                y = 288;
                xlength = 45;
                ylength = 47;
            }
            else if (i == 103)
            {
                penNumber = "418";
                x = 708;
                y = 340;
                xlength = 45;
                ylength = 47;
            }
            else if (i == 104)
            {
                penNumber = "419";
                x = 708;
                y = 391;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 105)
            {
                penNumber = "420";
                x = 772;
                y = 513;
                xlength = 46;
                ylength = 47;
            }
            else if (i == 106)
            {
                penNumber = "421";
                x = 783;
                y = 566;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 107)
            {
                penNumber = "422";
                x = 767;
                y = 32;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 108)
            {
                penNumber = "423";
                x = 768;
                y = 83;
                xlength = 45;
                ylength = 47;
            }
            else if (i == 109)
            {
                penNumber = "424";
                x = 769;
                y = 134;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 110)
            {
                penNumber = "425";
                x = 768;
                y = 186;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 111)
            {
                penNumber = "426";
                x = 769;
                y = 237;
                xlength = 47;
                ylength = 47;
            }
            else if (i == 112)
            {
                penNumber = "427";
                x = 770;
                y = 288;
                xlength = 46;
                ylength = 47;
            }
            else if (i == 113)
            {
                penNumber = "428";
                x = 770;
                y = 339;
                xlength = 47;
                ylength = 47;
            }
            else if (i == 114)
            {
                penNumber = "429";
                x = 772;
                y = 390;
                xlength = 46;
                ylength = 47;
            }
            else if (i == 115)
            {
                penNumber = "430";
                x = 818;
                y = 31;
                xlength = 46;
                ylength = 47;
            }
            else if (i == 116)
            {
                penNumber = "431";
                x = 819;
                y = 82;
                xlength = 45;
                ylength = 47;
            }
            else if (i == 117)
            {
                penNumber = "432";
                x = 819;
                y = 134;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 118)
            {
                penNumber = "433";
                x = 820;
                y = 185;
                xlength = 44;
                ylength = 46;
            }
            else if (i == 119)
            {
                penNumber = "434";
                x = 820;
                y = 237;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 120)
            {
                penNumber = "435";
                x = 821;
                y = 288;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 121)
            {
                penNumber = "436";
                x = 822;
                y = 339;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 122)
            {
                penNumber = "437";
                x = 823;
                y = 390;
                xlength = 46;
                ylength = 47;
            }
            else if (i == 123)
            {
                penNumber = "801";
                x = 384;
                y = 317;
                xlength = 29;
                ylength = 29;
            }
            else if (i == 124)
            {
                penNumber = "802";
                x = 391;
                y = 349;
                xlength = 30;
                ylength = 28;
            }
            else if (i == 125)
            {
                penNumber = "803";
                x = 397;
                y = 382;
                xlength = 29;
                ylength = 27;
            }
            else if (i == 126)
            {
                penNumber = "804";
                x = 403;
                y = 414;
                xlength = 29;
                ylength = 26;
            }
            else if (i == 127)
            {
                penNumber = "805";
                x = 418;
                y = 312;
                xlength = 30;
                ylength = 27;
            }
            else if (i == 128)
            {
                penNumber = "806";
                x = 425;
                y = 342;
                xlength = 29;
                ylength = 27;
            }
            else if (i == 129)
            {
                penNumber = "807";
                x = 432;
                y = 376;
                xlength = 28;
                ylength = 27;
            }
            else if (i == 130)
            {
                penNumber = "808";
                x = 437;
                y = 406;
                xlength = 29;
                ylength = 29;
            }
            else if (i == 131)
            {
                penNumber = "809";
                x = 454;
                y = 312;
                xlength = 31;
                ylength = 29;
            }
            else if (i == 132)
            {
                penNumber = "810";
                x = 461;
                y = 344;
                xlength = 29;
                ylength = 28;
            }
            else if (i == 133)
            {
                penNumber = "811";
                x = 467;
                y = 377;
                xlength = 29;
                ylength = 27;
            }
            else if (i == 134)
            {
                penNumber = "812";
                x = 474;
                y = 408;
                xlength = 30;
                ylength = 28;
            }
            else if (i == 135)
            {
                penNumber = "813";
                x = 490;
                y = 307;
                xlength = 28;
                ylength = 27;
            }
            else if (i == 136)
            {
                penNumber = "814";
                x = 497;
                y = 337;
                xlength = 28;
                ylength = 27;
            }
            else if (i == 137)
            {
                penNumber = "815";
                x = 502;
                y = 371;
                xlength = 29;
                ylength = 28;
            }
            else if (i == 138)
            {
                penNumber = "816";
                x = 509;
                y = 404;
                xlength = 29;
                ylength = 28;
            }
            else if (i == 139)
            {
                penNumber = "901";
                x = 629;
                y = 520;
                xlength = 30;
                ylength = 28;
            }
            else if (i == 140)
            {
                penNumber = "902";
                x = 636;
                y = 552;
                xlength = 29;
                ylength = 27;
            }
            else if (i == 141)
            {
                penNumber = "903";
                x = 644;
                y = 583;
                xlength = 26;
                ylength = 26;
            }
            else if (i == 142)
            {
                penNumber = "904";
                x = 650;
                y = 612;
                xlength = 28;
                ylength = 27;
            }
            else if (i == 143)
            {
                penNumber = "905";
                x = 665;
                y = 513;
                xlength = 27;
                ylength = 27;
            }
            else if (i == 144)
            {
                penNumber = "906";
                x = 672;
                y = 545;
                xlength = 27;
                ylength = 26;
            }
            else if (i == 145)
            {
                penNumber = "907";
                x = 677;
                y = 574;
                xlength = 28;
                ylength = 27;
            }
            else if (i == 146)
            {
                penNumber = "908";
                x = 683;
                y = 604;
                xlength = 28;
                ylength = 27;
            }
            else if (i == 147)
            {
                penNumber = "909";
                x = 704;
                y = 494;
                xlength = 27;
                ylength = 27;
            }
            else
            {
                penNumber = "910";
                x = 739;
                y = 483;
                xlength = 27;
                ylength = 26;
            }
            
            button = new JButton(penNumber);
            button.setBounds(x, y, xlength, ylength);
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setForeground(new Color(0, 0, 0, 0));
            button.setToolTipText("Click for expanded information and to modify pen");
            
            button.addActionListener(e -> {
                com.healthmarketscience.jackcess.Cursor cursor;
                boolean isDone = false;
                
                    //ArrayList corresponds to the most recent record for the selected pen in the pen database
                    //However, some pens are quartered, meaning that the pen has been divided into 4 smaller pens
                    //For these pens, get the latest row for each of the 4 divided pens
                java.util.List<Row> latestRows = new ArrayList<>();
                
                String pen = ((JButton) e.getSource()).getText();
                try
                {
                    cursor = CursorBuilder.createCursor(penTable);
                    
                        //check if pen is quartered
                    boolean isQuartered = false;
                    for (String s : quarteredPens)
                    {
                        if (pen.equals(s))
                        {
                            isQuartered = true;
                            break;
                        }                       
                    }
                    
                        //quartered pens, find the lastest row for each of the four quarters
                    if (isQuartered)
                    {
                        for (int j = 1; j <= 4; j++)
                        {
                            cursor.afterLast();
                            Row latestRow = null;                      
                            while (!isDone)
                            {
                                Row row = cursor.getPreviousRow();
                                if (row != null && row.get("Pen Number").toString().equals(pen + "." + j))
                                {
                                    latestRow = row;
                                    isDone = true;
                                }
                            }
                            isDone = false;
                            latestRows.add(latestRow);
                        }
                    }
                        //regular pens
                    else
                    {
                        cursor.afterLast();
                        Row latestRow = null;
                        while (!isDone)
                        {
                            Row row = cursor.getPreviousRow();
                            if (row != null && row.get("Pen Number").toString().equals(pen))
                            {
                                latestRow = row;
                                isDone = true;
                            }
                        }
                        latestRows.add(latestRow);
                    }
                }
                catch (IOException e1)
                {
                }
                modifyWindow(latestRows, pen);
            });
            pens[i-1] = button;
        }
    }
}