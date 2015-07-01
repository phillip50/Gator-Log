/**
 * Cage Application
 * Parses the cage database to view the current state of each pen on the farm
 * From there, modifications can be made to the pen's attributes and the gators currently in the pen can be viewed as well
 * 
 * @Phillip Dingler [phil50@ufl.edu]
 */

//TODO: comment

package test;

import javax.swing.*;
import java.awt.*;
import com.healthmarketscience.jackcess.*;
import java.io.*;
import java.util.*;

public class CageApplication extends JFrame
{
        //"this" objest
    private static CageApplication frame;
    
        //list of JButtons which correspond to the pens on the farm
    private final JButton[] cages;
    
    
    private final JButton modifyClass;
    
    
    private final JButton modifyQuarters;
    
        //cage database files
    private File file;
    private Table table;
    
    
    private final String[] quarteredPens;
    
        //picture of the farm
    Image image;
    
    public CageApplication()
    {
        super("Cage Application");
        
            //read in the cage database file
        try
        {
            file = new File("CageDatabase.accdb");
            table = DatabaseBuilder.open(file).getTable("Database");
        }
        catch (IOException e)
        {
        }
            
            //initialize the list buttons
        cages = new JButton[169];
        
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
    }
    
        //when a pen button has been clicked, this method is called to create a new window
        //this window displays more information about the pen that was clicked
    public void modifyWindow(java.util.List<Row> rows, String penNumber)
    {
        ModifyWindow modifyFrame = new ModifyWindow(rows, penNumber);
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

        //add the buttons to the frame
    public void addComponents()
    {
        for (JButton cage : cages)
        {
            frame.add(cage);
        }
        frame.add(modifyClass);
        frame.add(modifyQuarters);
        validate();
        setVisible(true);
    }

    public static void main(String[] args)
    {
        frame = new CageApplication();
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
            
        });
        
        for (int i = 1; i < 170; i++)
        {
            JButton button;
            String cageNumber;
            int x, y, xlength, ylength; //x,y = corrdinates; xlength,ylength = length and width
            if (i == 1)
            {
                cageNumber = "101";
                x = 75;
                y = 253;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 2)
            {
                cageNumber = "102";
                x = 85;
                y = 304;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 3)
            {
                cageNumber = "103";
                x = 95;
                y = 353;
                xlength = 45;
                ylength = 47;
            }
            else if (i == 4)
            {
                cageNumber = "104";
                x = 104;
                y = 404;
                xlength = 46;
                ylength = 47;
            }
            else if (i == 5)
            {
                cageNumber = "105";
                x = 113;
                y = 454;
                xlength = 46;
                ylength = 48;
            }
            else if (i == 6)
            {
                cageNumber = "106";
                x = 123;
                y = 506;
                xlength = 47;
                ylength = 46;
            }
            else if (i == 7)
            {
                cageNumber = "107";
                x = 133;
                y = 557;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 8)
            {
                cageNumber = "108";
                x = 145;
                y = 608;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 9)
            {
                cageNumber = "109";
                x = 153;
                y = 658;
                xlength = 47;
                ylength = 46;
            }
            else if (i == 10)
            {
                cageNumber = "110";
                x = 124;
                y = 178;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 11)
            {
                cageNumber = "111";
                x = 136;
                y = 242;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 12)
            {
                cageNumber = "112";
                x = 147;
                y = 292;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 13)
            {
                cageNumber = "113";
                x = 156;
                y = 342;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 14)
            {
                cageNumber = "114";
                x = 165;
                y = 392;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 15)
            {
                cageNumber = "115";
                x = 174;
                y = 443;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 16)
            {
                cageNumber = "116";
                x = 184;
                y = 495;
                xlength = 47;
                ylength = 46;
            }
            else if (i == 17)
            {
                cageNumber = "117";
                x = 195;
                y = 545;
                xlength = 46;
                ylength = 45;
            }
            else if (i == 18)
            {
                cageNumber = "118";
                x = 206;
                y = 595;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 19)
            {
                cageNumber = "119";
                x = 214;
                y = 646;
                xlength = 47;
                ylength = 46;
            }
            else if (i == 20)
            {
                cageNumber = "120";
                x = 224;
                y = 696;
                xlength = 47;
                ylength = 45;
            }
            else if (i == 21)
            {
                cageNumber = "121";
                x = 166;
                y = 117;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 22)
            {
                cageNumber = "122";
                x = 175;
                y = 168;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 23)
            {
                cageNumber = "123";
                x = 188;
                y = 232;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 24)
            {
                cageNumber = "124";
                x = 197;
                y = 282;
                xlength = 46;
                ylength = 47;
            }
            else if (i == 25)
            {
                cageNumber = "125";
                x = 207;
                y = 333;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 26)
            {
                cageNumber = "126";
                x = 216;
                y = 384;
                xlength = 46;
                ylength = 45;
            }
            else if (i == 27)
            {
                cageNumber = "127";
                x = 226;
                y = 434;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 28)
            {
                cageNumber = "201";
                x = 227;
                y = 108;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 29)
            {
                cageNumber = "202";
                x = 235;
                y = 157;
                xlength = 46;
                ylength = 44;
            }
            else if (i == 30)
            {
                cageNumber = "203";
                x = 250;
                y = 221;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 31)
            {
                cageNumber = "204";
                x = 259;
                y = 272;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 32)
            {
                cageNumber = "205";
                x = 267;
                y = 322;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 33)
            {
                cageNumber = "206";
                x = 276;
                y = 371;
                xlength = 46;
                ylength = 44;
            }
            else if (i == 34)
            {
                cageNumber = "207";
                x = 286;
                y = 419;
                xlength = 44;
                ylength = 42;
            }
            else if (i == 35)
            {
                cageNumber = "208";
                x = 305;
                y = 465;
                xlength = 44;
                ylength = 43;
            }
            else if (i == 36)
            {
                cageNumber = "209";
                x = 307;
                y = 518;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 37)
            {
                cageNumber = "210";
                x = 308;
                y = 573;
                xlength = 44;
                ylength = 43;
            }
            else if (i == 38)
            {
                cageNumber = "211";
                x = 308;
                y = 627;
                xlength = 46;
                ylength = 44;
            }
            else if (i == 39)
            {
                cageNumber = "212";
                x = 311;
                y = 677;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 40)
            {
                cageNumber = "213";
                x = 313;
                y = 729;
                xlength = 44;
                ylength = 44;
            }
            else if (i == 41)
            {
                cageNumber = "214";
                x = 300;
                y = 212;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 42)
            {
                cageNumber = "215";
                x = 310;
                y = 263;
                xlength = 44;
                ylength = 46;
            }
            else if (i == 43)
            {
                cageNumber = "216";
                x = 318;
                y = 316;
                xlength = 46;
                ylength = 44;
            }
            else if (i == 44)
            {
                cageNumber = "217";
                x = 328;
                y = 366;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 45)
            {
                cageNumber = "218";
                x = 336;
                y = 415;
                xlength = 44;
                ylength = 42;
            }
            else if (i == 46)
            {
                cageNumber = "219";
                x = 361;
                y = 463;
                xlength = 44;
                ylength = 44;
            }
            else if (i == 47)
            {
                cageNumber = "220";
                x = 361;
                y = 517;
                xlength = 46;
                ylength = 45;
            }
            else if (i == 48)
            {
                cageNumber = "221";
                x = 364;
                y = 571;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 49)
            {
                cageNumber = "222";
                x = 364;
                y = 625;
                xlength = 46;
                ylength = 45;
            }
            else if (i == 50)
            {
                cageNumber = "223";
                x = 367;
                y = 676;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 51)
            {
                cageNumber = "224";
                x = 369;
                y = 727;
                xlength = 44;
                ylength = 44;
            }
            else if (i == 52)
            {
                cageNumber = "225";
                x = 460;
                y = 453;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 53)
            {
                cageNumber = "226";
                x = 462;
                y = 506;
                xlength = 43;
                ylength = 44;
            }
            else if (i == 54)
            {
                cageNumber = "227";
                x = 463;
                y = 574;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 55)
            {
                cageNumber = "228";
                x = 467;
                y = 626;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 56)
            {
                cageNumber = "229";
                x = 521;
                y = 451;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 57)
            {
                cageNumber = "230";
                x = 523;
                y = 504;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 58)
            {
                cageNumber = "231";
                x = 524;
                y = 571;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 59)
            {
                cageNumber = "232";
                x = 527;
                y = 624;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 60)
            {
                cageNumber = "301";
                x = 343;
                y = 51;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 61)
            {
                cageNumber = "302";
                x = 352;
                y = 105;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 62)
            {
                cageNumber = "303";
                x = 367;
                y = 186;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 63)
            {
                cageNumber = "304";
                x = 375;
                y = 240;
                xlength = 47;
                ylength = 45;
            }
            else if (i == 64)
            {
                cageNumber = "305";
                x = 404;
                y = 40;
                xlength = 46;
                ylength = 45;
            }
            else if (i == 65)
            {
                cageNumber = "306";
                x = 413;
                y = 93;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 66)
            {
                cageNumber = "307";
                x = 428;
                y = 174;
                xlength = 46;
                ylength = 45;
            }
            else if (i == 67)
            {
                cageNumber = "308";
                x = 438;
                y = 228;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 68)
            {
                cageNumber = "309";
                x = 467;
                y = 28;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 69)
            {
                cageNumber = "310";
                x = 476;
                y = 82;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 70)
            {
                cageNumber = "311";
                x = 491;
                y = 146;
                xlength = 44;
                ylength = 44;
            }
            else if (i == 71)
            {
                cageNumber = "312";
                x = 516;
                y = 19;
                xlength = 44;
                ylength = 46;
            }
            else if (i == 72)
            {
                cageNumber = "313";
                x = 526;
                y = 73;
                xlength = 43;
                ylength = 45;
            }
            else if (i == 73)
            {
                cageNumber = "314";
                x = 542;
                y = 138;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 74)
            {
                cageNumber = "315";
                x = 542;
                y = 188;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 75)
            {
                cageNumber = "316";
                x = 543;
                y = 240;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 76)
            {
                cageNumber = "317";
                x = 543;
                y = 291;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 77)
            {
                cageNumber = "318";
                x = 591;
                y = 34;
                xlength = 45;
                ylength = 45;
            }
            else if (i == 78)
            {
                cageNumber = "319";
                x = 591;
                y = 85;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 79)
            {
                cageNumber = "320";
                x = 593;
                y = 137;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 80)
            {
                cageNumber = "321";
                x = 593;
                y = 188;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 81)
            {
                cageNumber = "322";
                x = 594;
                y = 239;
                xlength = 44;
                ylength = 46;
            }
            else if (i == 82)
            {
                cageNumber = "323";
                x = 594;
                y = 290;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 83)
            {
                cageNumber = "324";
                x = 594;
                y = 341;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 84)
            {
                cageNumber = "325";
                x = 595;
                y = 393;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 85)
            {
                cageNumber = "326";
                x = 595;
                y = 445;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 86)
            {
                cageNumber = "401";
                x = 654;
                y = 34;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 87)
            {
                cageNumber = "402";
                x = 653;
                y = 85;
                xlength = 46;
                ylength = 45;
            }
            else if (i == 88)
            {
                cageNumber = "403";
                x = 655;
                y = 136;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 89)
            {
                cageNumber = "404";
                x = 655;
                y = 187;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 90)
            {
                cageNumber = "405";
                x = 656;
                y = 238;
                xlength = 45;
                ylength = 47;
            }
            else if (i == 91)
            {
                cageNumber = "406";
                x = 657;
                y = 290;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 92)
            {
                cageNumber = "407";
                x = 657;
                y = 341;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 93)
            {
                cageNumber = "408";
                x = 659;
                y = 392;
                xlength = 43;
                ylength = 46;
            }
            else if (i == 94)
            {
                cageNumber = "409";
                x = 658;
                y = 444;
                xlength = 45;
                ylength = 44;
            }
            else if (i == 95)
            {
                cageNumber = "410";
                x = 713;
                y = 526;
                xlength = 46;
                ylength = 47;
            }
            else if (i == 96)
            {
                cageNumber = "411";
                x = 723;
                y = 579;
                xlength = 46;
                ylength = 45;
            }
            else if (i == 97)
            {
                cageNumber = "412";
                x = 705;
                y = 33;
                xlength = 44;
                ylength = 45;
            }
            else if (i == 98)
            {
                cageNumber = "413";
                x = 705;
                y = 84;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 99)
            {
                cageNumber = "414";
                x = 706;
                y = 135;
                xlength = 44;
                ylength = 46;
            }
            else if (i == 100)
            {
                cageNumber = "415";
                x = 707;
                y = 186;
                xlength = 45;
                ylength = 47;
            }
            else if (i == 101)
            {
                cageNumber = "416";
                x = 707;
                y = 237;
                xlength = 45;
                ylength = 47;
            }
            else if (i == 102)
            {
                cageNumber = "417";
                x = 708;
                y = 288;
                xlength = 45;
                ylength = 47;
            }
            else if (i == 103)
            {
                cageNumber = "418";
                x = 708;
                y = 340;
                xlength = 45;
                ylength = 47;
            }
            else if (i == 104)
            {
                cageNumber = "419";
                x = 708;
                y = 391;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 105)
            {
                cageNumber = "420";
                x = 772;
                y = 513;
                xlength = 46;
                ylength = 47;
            }
            else if (i == 106)
            {
                cageNumber = "421";
                x = 783;
                y = 566;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 107)
            {
                cageNumber = "422";
                x = 767;
                y = 32;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 108)
            {
                cageNumber = "423";
                x = 768;
                y = 83;
                xlength = 45;
                ylength = 47;
            }
            else if (i == 109)
            {
                cageNumber = "424";
                x = 769;
                y = 134;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 110)
            {
                cageNumber = "425";
                x = 768;
                y = 186;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 111)
            {
                cageNumber = "426";
                x = 769;
                y = 237;
                xlength = 47;
                ylength = 47;
            }
            else if (i == 112)
            {
                cageNumber = "427";
                x = 770;
                y = 288;
                xlength = 46;
                ylength = 47;
            }
            else if (i == 113)
            {
                cageNumber = "428";
                x = 770;
                y = 339;
                xlength = 47;
                ylength = 47;
            }
            else if (i == 114)
            {
                cageNumber = "429";
                x = 772;
                y = 390;
                xlength = 46;
                ylength = 47;
            }
            else if (i == 115)
            {
                cageNumber = "430";
                x = 818;
                y = 31;
                xlength = 46;
                ylength = 47;
            }
            else if (i == 116)
            {
                cageNumber = "431";
                x = 819;
                y = 82;
                xlength = 45;
                ylength = 47;
            }
            else if (i == 117)
            {
                cageNumber = "432";
                x = 819;
                y = 134;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 118)
            {
                cageNumber = "433";
                x = 820;
                y = 185;
                xlength = 44;
                ylength = 46;
            }
            else if (i == 119)
            {
                cageNumber = "434";
                x = 820;
                y = 237;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 120)
            {
                cageNumber = "435";
                x = 821;
                y = 288;
                xlength = 46;
                ylength = 46;
            }
            else if (i == 121)
            {
                cageNumber = "436";
                x = 822;
                y = 339;
                xlength = 45;
                ylength = 46;
            }
            else if (i == 122)
            {
                cageNumber = "437";
                x = 823;
                y = 390;
                xlength = 46;
                ylength = 47;
            }
            else if (i == 123)
            {
                cageNumber = "801";
                x = 384;
                y = 317;
                xlength = 29;
                ylength = 29;
            }
            else if (i == 124)
            {
                cageNumber = "802";
                x = 391;
                y = 349;
                xlength = 30;
                ylength = 28;
            }
            else if (i == 125)
            {
                cageNumber = "803";
                x = 397;
                y = 382;
                xlength = 29;
                ylength = 27;
            }
            else if (i == 126)
            {
                cageNumber = "804";
                x = 403;
                y = 414;
                xlength = 29;
                ylength = 26;
            }
            else if (i == 127)
            {
                cageNumber = "805";
                x = 418;
                y = 312;
                xlength = 30;
                ylength = 27;
            }
            else if (i == 128)
            {
                cageNumber = "806";
                x = 425;
                y = 342;
                xlength = 29;
                ylength = 27;
            }
            else if (i == 129)
            {
                cageNumber = "807";
                x = 432;
                y = 376;
                xlength = 28;
                ylength = 27;
            }
            else if (i == 130)
            {
                cageNumber = "808";
                x = 437;
                y = 406;
                xlength = 29;
                ylength = 29;
            }
            else if (i == 131)
            {
                cageNumber = "809";
                x = 454;
                y = 312;
                xlength = 31;
                ylength = 29;
            }
            else if (i == 132)
            {
                cageNumber = "810";
                x = 461;
                y = 344;
                xlength = 29;
                ylength = 28;
            }
            else if (i == 133)
            {
                cageNumber = "811";
                x = 467;
                y = 377;
                xlength = 29;
                ylength = 27;
            }
            else if (i == 134)
            {
                cageNumber = "812";
                x = 474;
                y = 408;
                xlength = 30;
                ylength = 28;
            }
            else if (i == 135)
            {
                cageNumber = "813";
                x = 490;
                y = 307;
                xlength = 28;
                ylength = 27;
            }
            else if (i == 136)
            {
                cageNumber = "814";
                x = 497;
                y = 337;
                xlength = 28;
                ylength = 27;
            }
            else if (i == 137)
            {
                cageNumber = "815";
                x = 502;
                y = 371;
                xlength = 29;
                ylength = 28;
            }
            else if (i == 138)
            {
                cageNumber = "816";
                x = 509;
                y = 404;
                xlength = 29;
                ylength = 28;
            }
            else if (i == 139)
            {
                cageNumber = "901";
                x = 629;
                y = 520;
                xlength = 30;
                ylength = 28;
            }
            else if (i == 140)
            {
                cageNumber = "902";
                x = 636;
                y = 552;
                xlength = 29;
                ylength = 27;
            }
            else if (i == 141)
            {
                cageNumber = "903";
                x = 644;
                y = 583;
                xlength = 26;
                ylength = 26;
            }
            else if (i == 142)
            {
                cageNumber = "904";
                x = 650;
                y = 612;
                xlength = 28;
                ylength = 27;
            }
            else if (i == 143)
            {
                cageNumber = "905";
                x = 665;
                y = 513;
                xlength = 27;
                ylength = 27;
            }
            else if (i == 144)
            {
                cageNumber = "906";
                x = 672;
                y = 545;
                xlength = 27;
                ylength = 26;
            }
            else if (i == 145)
            {
                cageNumber = "907";
                x = 677;
                y = 574;
                xlength = 28;
                ylength = 27;
            }
            else if (i == 146)
            {
                cageNumber = "908";
                x = 683;
                y = 604;
                xlength = 28;
                ylength = 27;
            }
            else if (i == 147)
            {
                cageNumber = "909";
                x = 704;
                y = 494;
                xlength = 27;
                ylength = 27;
            }
            else
            {
                cageNumber = "910";
                x = 739;
                y = 483;
                xlength = 27;
                ylength = 26;
            }
            
            button = new JButton(cageNumber);
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
                
                String penNumber = ((JButton) e.getSource()).getText();
                try
                {
                    cursor = CursorBuilder.createCursor(table);
                    
                        //check if pen is quartered
                    boolean isQuartered = false;
                    for (String s : quarteredPens)
                    {
                        if (penNumber.equals(s))
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
                                if (row != null && row.get("Pen Number").toString().equals(penNumber + "." + j))
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
                            if (row != null && row.get("Pen Number").toString().equals(penNumber))
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
                modifyWindow(latestRows, penNumber);
            });
            cages[i-1] = button;
        }
    }
}