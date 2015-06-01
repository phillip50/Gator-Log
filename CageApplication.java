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
import java.awt.Graphics;
import java.awt.*;
import java.awt.image.*;
import javax.*;
import javax.imageio.*;

public class CageApplication extends JFrame
{
    private static CageApplication frame;
    private Container contentPane;
    private JButton[] cages;
    private File file;
    private Table table;
    private Row mostRecentCage;
    Image image;
    
    public CageApplication()
    {
        super("Application");
        contentPane = getContentPane();
        
        try
        {
            file = new File("CageDatabase.accdb");
            table = DatabaseBuilder.open(file).getTable("Database");
        }
        catch (IOException e)
        {
                    
        }
        
        cages = new JButton[169];
        for (int i = 1; i < 170; i++)
        {
            JButton button;
            String cageNumber = "";
            int x, y, xlength, ylength;
            if (i == 1)
            {
                cageNumber = "" + i;
                x = 451;
                y = 387;
                xlength = 17;
                ylength = 17;
            }
            else if (i == 2)
            {
                cageNumber = "" + i;
                x = 477;
                y = 385;
                xlength = 16;
                ylength = 15;
            }
            else if (i == 3)
            {
                cageNumber = "" + i;
                x = 453;
                y = 411;
                xlength = 17;
                ylength = 16;
            }
            else if (i == 4)
            {
                cageNumber = "" + i;
                x = 479;
                y = 408;
                xlength = 17;
                ylength = 16;
            }
            else if (i == 5)
            {
                cageNumber = "" + i;
                x = 456;
                y = 434;
                xlength = 17;
                ylength = 15;
                
            }
            else if (i == 6)
            {
                cageNumber = "" + i;
                x = 481;
                y = 431;
                xlength = 18;
                ylength = 15;
            }
            else if (i == 7)
            {
                cageNumber = "" + i;
                x = 460;
                y = 456;
                xlength = 15;
                ylength = 15;
            }
            else if (i == 8)
            {
                cageNumber = "" + i;
                x = 484;
                y = 453;
                xlength = 17;
                ylength = 15;
            }
            else if (i == 9)
            {
                cageNumber = "" + i;
                x = 508;
                y = 402;
                xlength = 31;
                ylength = 27;
            }
            else if (i == 10)
            {
                cageNumber = "" + i;
                x = 553;
                y = 398;
                xlength = 30;
                ylength = 24;
            }
            else if (i == 11)
            {
                cageNumber = "" + i;
                x = 506;
                y = 374;
                xlength = 16;
                ylength = 15;
            }
            else if (i == 12)
            {
                cageNumber = "" + i;
                x = 513;
                y = 440;
                xlength = 32;
                ylength = 26;
            }
            else if (i == 13)
            {
                cageNumber = "" + i;
                x = 557;
                y = 435;
                xlength = 30;
                ylength = 26;
            }
            else if (i == 14)
            {
                cageNumber = "" + i;
                x = 533;
                y = 369;
                xlength = 16;
                ylength = 15;
            }
            else if (i == 15)
            {
                cageNumber = "" + i;
                x = 89;
                y = 164;
                xlength = 25;
                ylength = 24;
            }
            else if (i == 16)
            {
                cageNumber = "" + i;
                x = 90;
                y = 198;
                xlength = 29;
                ylength = 22;
            }
            else if (i == 17)
            {
                cageNumber = "" + i;
                x = 89;
                y = 234;
                xlength = 31;
                ylength = 24;
            }
            else if (i == 18)
            {
                cageNumber = "" + i;
                x = 91;
                y = 268;
                xlength = 29;
                ylength = 26;
            }
            else if (i == 19)
            {
                cageNumber = "" + i;
                x = 92;
                y = 304;
                xlength = 28;
                ylength = 26;
            }
            else if (i == 20)
            {
                cageNumber = "" + i;
                x = 89;
                y = 341;
                xlength = 32;
                ylength = 26;
            }
            else if (i == 21)
            {
                cageNumber = "" + i;
                x = 90;
                y = 378;
                xlength = 32;
                ylength = 27;
            }
            else if (i == 22)
            {
                cageNumber = "" + i;
                x = 91;
                y = 413;
                xlength = 33;
                ylength = 30;
            }
            else if (i == 23)
            {
                cageNumber = "" + i;
                x = 95;
                y = 454;
                xlength = 30;
                ylength = 26;
            }
            else if (i == 24)
            {
                cageNumber = "" + i;
                x = 129;
                y = 118;
                xlength = 28;
                ylength = 24;
            }
            else if (i == 25)
            {
                cageNumber = "" + i;
                x = 132;
                y = 161;
                xlength = 28;
                ylength = 25;
            }
            else if (i == 26)
            {
                cageNumber = "" + i;
                x = 132;
                y = 198;
                xlength = 29;
                ylength = 21;
            }
            else if (i == 27)
            {
                cageNumber = "" + i;
                x = 134;
                y = 229;
                xlength = 27;
                ylength = 26;
            }
            else if (i == 28)
            {
                cageNumber = "" + i;
                x = 135;
                y = 264;
                xlength = 27;
                ylength = 26;
            }
            else if (i == 29)
            {
                cageNumber = "" + i;
                x = 134;
                y = 300;
                xlength = 29;
                ylength = 27;
            }
            else if (i == 30)
            {
                cageNumber = "" + i;
                x = 137;
                y = 338;
                xlength = 27;
                ylength = 24;
            }
            else if (i == 31)
            {
                cageNumber = "" + i;
                x = 137;
                y = 373;
                xlength = 29;
                ylength = 26;
            }
            else if (i == 32)
            {
                cageNumber = "" + i;
                x = 138;
                y = 411;
                xlength = 30;
                ylength = 26;
            }
            else if (i == 33)
            {
                cageNumber = "" + i;
                x = 140;
                y = 448;
                xlength = 30;
                ylength = 27;
            }
            else if (i == 34)
            {
                cageNumber = "" + i;
                x = 141;
                y = 489;
                xlength = 30;
                ylength = 27;
            }
            else if (i == 35)
            {
                cageNumber = "" + i;
                x = 164;
                y = 87;
                xlength = 27;
                ylength = 20;
            }
            else if (i == 36)
            {
                cageNumber = "" + i;
                x = 166;
                y = 116;
                xlength = 26;
                ylength = 22;
            }
            else if (i == 37)
            {
                cageNumber = "" + i;
                x = 167;
                y = 158;
                xlength = 29;
                ylength = 25;
            }
            else if (i == 38)
            {
                cageNumber = "" + i;
                x = 169;
                y = 192;
                xlength = 27;
                ylength = 26;
            }
            else if (i == 39)
            {
                cageNumber = "" + i;
                x = 170;
                y = 226;
                xlength = 27;
                ylength = 25;
            }
            else if (i == 40)
            {
                cageNumber = "" + i;
                x = 172;
                y = 263;
                xlength = 27;
                ylength = 25;
            }
            else if (i == 41)
            {
                cageNumber = "" + i;
                x = 173;
                y = 296;
                xlength = 27;
                ylength = 25;
            }
            else if (i == 42)
            {
                cageNumber = "" + i;
                x = 206;
                y = 81;
                xlength = 26;
                ylength = 23;
            }
            else if (i == 43)
            {
                cageNumber = "" + i;
                x = 208;
                y = 112;
                xlength = 30;
                ylength = 24;
            }
            else if (i == 44)
            {
                cageNumber = "" + i;
                x = 210;
                y = 155;
                xlength = 30;
                ylength = 25;
            }
            else if (i == 45)
            {
                cageNumber = "" + i;
                x = 212;
                y = 191;
                xlength = 26;
                ylength = 24;
            }
            else if (i == 46)
            {
                cageNumber = "" + i;
                x = 215;
                y = 225;
                xlength = 24;
                ylength = 22;
            }
            else if (i == 47)
            {
                cageNumber = "" + i;
                x = 215;
                y = 256;
                xlength = 27;
                ylength = 28;
            }
            else if (i == 48)
            {
                cageNumber = "" + i;
                x = 216;
                y = 291;
                xlength = 27;
                ylength = 25;
            }
            else if (i == 49)
            {
                cageNumber = "" + i;
                x = 227;
                y = 324;
                xlength = 23;
                ylength = 27;
            }
            else if (i == 50)
            {
                cageNumber = "" + i;
                x = 222;
                y = 361;
                xlength = 22;
                ylength = 29;
            }
            else if (i == 51)
            {
                cageNumber = "" + i;
                x = 216;
                y = 400;
                xlength = 25;
                ylength = 30;
            }
            else if (i == 52)
            {
                cageNumber = "" + i;
                x = 213;
                y = 438;
                xlength = 23;
                ylength = 23;
            }
            else if (i == 53)
            {
                cageNumber = "" + i;
                x = 207;
                y = 478;
                xlength = 26;
                ylength = 32;
            }
            else if (i == 54)
            {
                cageNumber = "" + i;
                x = 205;
                y = 517;
                xlength = 23;
                ylength = 32;
            }
            else if (i == 55)
            {
                cageNumber = "" + i;
                x = 245;
                y = 153;
                xlength = 27;
                ylength = 24;
            }
            else if (i == 56)
            {
                cageNumber = "" + i;
                x = 247;
                y = 187;
                xlength = 28;
                ylength = 25;
            }
            else if (i == 57)
            {
                cageNumber = "" + i;
                x = 249;
                y = 225;
                xlength = 26;
                ylength = 23;
            }
            else if (i == 58)
            {
                cageNumber = "" + i;
                x = 250;
                y = 258;
                xlength = 26;
                ylength = 24;
            }
            else if (i == 59)
            {
                cageNumber = "" + i;
                x = 252;
                y = 291;
                xlength = 26;
                ylength = 26;
            }
            else if (i == 60)
            {
                cageNumber = "" + i;
                x = 266;
                y = 326;
                xlength = 24;
                ylength = 29;
            }
            else if (i == 61)
            {
                cageNumber = "" + i;
                x = 262;
                y = 364;
                xlength = 24;
                ylength = 30;
            }
            else if (i == 62)
            {
                cageNumber = "" + i;
                x = 256;
                y = 403;
                xlength = 24;
                ylength = 31;
            }
            else if (i == 63)
            {
                cageNumber = "" + i;
                x = 252;
                y = 444;
                xlength = 24;
                ylength = 29;
            }
            else if (i == 64)
            {
                cageNumber = "" + i;
                x = 248;
                y = 480;
                xlength = 25;
                ylength = 33;
            }
            else if (i == 65)
            {
                cageNumber = "" + i;
                x = 244;
                y = 520;
                xlength = 26;
                ylength = 33;
            }
            else if (i == 66)
            {
                cageNumber = "" + i;
                x = 288;
                y = 54;
                xlength = 28;
                ylength = 23;
            }
            else if (i == 67)
            {
                cageNumber = "" + i;
                x = 290;
                y = 88;
                xlength = 26;
                ylength = 23;
            }
            else if (i == 68)
            {
                cageNumber = "" + i;
                x = 294;
                y = 142;
                xlength = 27;
                ylength = 22;
            }
            else if (i == 69)
            {
                cageNumber = "" + i;
                x = 295;
                y = 177;
                xlength = 29;
                ylength = 24;
            }
            else if (i == 70)
            {
                cageNumber = "" + i;
                x = 336;
                y = 327;
                xlength = 25;
                ylength = 29;
            }
            else if (i == 71)
            {
                cageNumber = "" + i;
                x = 333;
                y = 364;
                xlength = 24;
                ylength = 31;
            }
            else if (i == 72)
            {
                cageNumber = "" + i;
                x = 327;
                y = 413;
                xlength = 26;
                ylength = 32;
            }
            else if (i == 73)
            {
                cageNumber = "" + i;
                x = 325;
                y = 453;
                xlength = 24;
                ylength = 31;
            }
            else if (i == 74)
            {
                cageNumber = "" + i;
                x = 330;
                y = 50;
                xlength = 27;
                ylength = 25;
            }
            else if (i == 75)
            {
                cageNumber = "" + i;
                x = 330;
                y = 84;
                xlength = 29;
                ylength = 24;
            }
            else if (i == 76)
            {
                cageNumber = "" + i;
                x = 336;
                y = 130;
                xlength = 28;
                ylength = 32;
            }
            else if (i == 77)
            {
                cageNumber = "" + i;
                x = 339;
                y = 173;
                xlength = 26;
                ylength = 26;
            }
            else if (i == 78)
            {
                cageNumber = "" + i;
                x = 380;
                y = 330;
                xlength = 24;
                ylength = 30;
            }
            else if (i == 79)
            {
                cageNumber = "" + i;
                x = 377;
                y = 370;
                xlength = 24;
                ylength = 28;
            }
            else if (i == 80)
            {
                cageNumber = "" + i;
                x = 372;
                y = 419;
                xlength = 24;
                ylength = 28;
            }
            else if (i == 81)
            {
                cageNumber = "" + i;
                x = 370;
                y = 456;
                xlength = 24;
                ylength = 29;
            }
            else if (i == 82)
            {
                cageNumber = "" + i;
                x = 374;
                y = 49;
                xlength = 26;
                ylength = 22;
            }
            else if (i == 83)
            {
                cageNumber = "" + i;
                x = 376;
                y = 82;
                xlength = 26;
                ylength = 23;
            }
            else if (i == 84)
            {
                cageNumber = "" + i;
                x = 382;
                y = 122;
                xlength = 24;
                ylength = 26;
            }
            else if (i == 85)
            {
                cageNumber = "" + i;
                x = 408;
                y = 46;
                xlength = 24;
                ylength = 23;
            }
            else if (i == 86)
            {
                cageNumber = "" + i;
                x = 409;
                y = 79;
                xlength = 25;
                ylength = 24;
            }
            else if (i == 87)
            {
                cageNumber = "" + i;
                x = 416;
                y = 121;
                xlength = 24;
                ylength = 27;
            }
            else if (i == 88)
            {
                cageNumber = "" + i;
                x = 413;
                y = 154;
                xlength = 23;
                ylength = 27;
            }
            else if (i == 89)
            {
                cageNumber = "" + i;
                x = 410;
                y = 188;
                xlength = 24;
                ylength = 25;
            }
            else if (i == 90)
            {
                cageNumber = "" + i;
                x = 402;
                y = 221;
                xlength = 30;
                ylength = 27;
            }
            else if (i == 91)
            {
                cageNumber = "" + i;
                x = 457;
                y = 57;
                xlength = 24;
                ylength = 27;
            }
            else if (i == 92)
            {
                cageNumber = "" + i;
                x = 455;
                y = 91;
                xlength = 23;
                ylength = 25;
            }
            else if (i == 93)
            {
                cageNumber = "" + i;
                x = 452;
                y = 123;
                xlength = 23;
                ylength = 27;
            }
            else if (i == 94)
            {
                cageNumber = "" + i;
                x = 441;
                y = 156;
                xlength = 32;
                ylength = 28;
            }
            else if (i == 95)
            {
                cageNumber = "" + i;
                x = 446;
                y = 191;
                xlength = 23;
                ylength = 28;
            }
            else if (i == 96)
            {
                cageNumber = "" + i;
                x = 443;
                y = 225;
                xlength = 23;
                ylength = 28;
            }
            else if (i == 97)
            {
                cageNumber = "" + i;
                x = 431;
                y = 261;
                xlength = 32;
                ylength = 27;
            }
            else if (i == 98)
            {
                cageNumber = "" + i;
                x = 434;
                y = 294;
                xlength = 27;
                ylength = 30;
            }
            else if (i == 99)
            {
                cageNumber = "" + i;
                x = 432;
                y = 332;
                xlength = 26;
                ylength = 28;
            }
            else if (i == 100)
            {
                cageNumber = "1A";
                x = 295;
                y = 222;
                xlength = 16;
                ylength = 19;
            }
            else if (i == 101)
            {
                cageNumber = "2A";
                x = 297;
                y = 250;
                xlength = 14;
                ylength = 14;
            }
            else if (i == 102)
            {
                cageNumber = "3A";
                x = 298;
                y = 273;
                xlength = 14;
                ylength = 14;
            }
            else if (i == 103)
            {
                cageNumber = "4A";
                x = 298;
                y = 295;
                xlength = 17;
                ylength = 15;
            }
            else if (i == 104)
            {
                cageNumber = "5A";
                x = 320;
                y = 225;
                xlength = 16;
                ylength = 16;
            }
            else if (i == 105)
            {
                cageNumber = "6A";
                x = 320;
                y = 247;
                xlength = 15;
                ylength = 16;
            }
            else if (i == 106)
            {
                cageNumber = "7A";
                x = 322;
                y = 271;
                xlength = 14;
                ylength = 15;
            }
            else if (i == 107)
            {
                cageNumber = "8A";
                x = 322;
                y = 294;
                xlength = 16;
                ylength = 12;
            }
            else if (i == 108)
            {
                cageNumber = "9A";
                x = 346;
                y = 228;
                xlength = 16;
                ylength = 19;
            }
            else if (i == 109)
            {
                cageNumber = "10A";
                x = 346;
                y = 251;
                xlength = 15;
                ylength = 15;
            }
            else if (i == 110)
            {
                cageNumber = "11A";
                x = 347;
                y = 273;
                xlength = 17;
                ylength = 16;
            }
            else if (i == 111)
            {
                cageNumber = "12A";
                x = 349;
                y = 296;
                xlength = 15;
                ylength = 17;
            }
            else if (i == 112)
            {
                cageNumber = "13A";
                x = 370;
                y = 228;
                xlength = 13;
                ylength = 12;
            }
            else if (i == 113)
            {
                cageNumber = "14A";
                x = 370;
                y = 248;
                xlength = 15;
                ylength = 17;
            }
            else if (i == 114)
            {
                cageNumber = "15A";
                x = 372;
                y = 272;
                xlength = 15;
                ylength = 15;
            }
            else if (i == 115)
            {
                cageNumber = "16A";
                x = 374;
                y = 295;
                xlength = 16;
                ylength = 16;
            }
            else if (i == 116)
            {
                cageNumber = "1B";
                x = 499;
                y = 62;
                xlength = 23;
                ylength = 27;
            }
            else if (i == 117)
            {
                cageNumber = "2B";
                x = 497;
                y = 94;
                xlength = 21;
                ylength = 27;
            }
            else if (i == 118)
            {
                cageNumber = "3B";
                x = 495;
                y = 127;
                xlength = 22;
                ylength = 27;
            }
            else if (i == 119)
            {
                cageNumber = "4B";
                x = 491;
                y = 161;
                xlength = 23;
                ylength = 27;
            }
            else if (i == 120)
            {
                cageNumber = "5B";
                x = 489;
                y = 195;
                xlength = 24;
                ylength = 28;
            }
            else if (i == 121)
            {
                cageNumber = "6B";
                x = 486;
                y = 228;
                xlength = 24;
                ylength = 29;
            }
            else if (i == 122)
            {
                cageNumber = "7B";
                x = 483;
                y = 265;
                xlength = 24;
                ylength = 29;
            }
            else if (i == 123)
            {
                cageNumber = "8B";
                x = 480;
                y = 300;
                xlength = 24;
                ylength = 30;
            }
            else if (i == 124)
            {
                cageNumber = "9B";
                x = 477;
                y = 336;
                xlength = 26;
                ylength = 29;
            }
            else if (i == 125)
            {
                cageNumber = "10B";
                x = 534;
                y = 65;
                xlength = 23;
                ylength = 26;
            }
            else if (i == 126)
            {
                cageNumber = "11B";
                x = 530;
                y = 98;
                xlength = 25;
                ylength = 27;
            }
            else if (i == 127)
            {
                cageNumber = "12B";
                x = 529;
                y = 131;
                xlength = 24;
                ylength = 27;
            }
            else if (i == 128)
            {
                cageNumber = "13B";
                x = 527;
                y = 164;
                xlength = 25;
                ylength = 27;
            }
            else if (i == 129)
            {
                cageNumber = "14B";
                x = 523;
                y = 197;
                xlength = 26;
                ylength = 30;
            }
            else if (i == 130)
            {
                cageNumber = "15B";
                x = 522;
                y = 233;
                xlength = 25;
                ylength = 29;
            }
            else if (i == 131)
            {
                cageNumber = "16B";
                x = 520;
                y = 268;
                xlength = 24;
                ylength = 27;
            }
            else if (i == 132)
            {
                cageNumber = "17B";
                x = 518;
                y = 303;
                xlength = 23;
                ylength = 28;
            }
            else if (i == 133)
            {
                cageNumber = "18B";
                x = 575;
                y = 68;
                xlength = 24;
                ylength = 27;
            }
            else if (i == 134)
            {
                cageNumber = "19B";
                x = 573;
                y = 102;
                xlength = 26;
                ylength = 27;
            }
            else if (i == 135)
            {
                cageNumber = "20B";
                x = 571;
                y = 135;
                xlength = 23;
                ylength = 26;
            }
            else if (i == 136)
            {
                cageNumber = "21B";
                x = 570;
                y = 168;
                xlength = 23;
                ylength = 27;
            }
            else if (i == 137)
            {
                cageNumber = "22B";
                x = 567;
                y = 203;
                xlength = 25;
                ylength = 27;
            }
            else if (i == 138)
            {
                cageNumber = "23B";
                x = 565;
                y = 238;
                xlength = 24;
                ylength = 28;
            }
            else if (i == 139)
            {
                cageNumber = "24B";
                x = 562;
                y = 272;
                xlength = 26;
                ylength = 29;
            }
            else if (i == 140)
            {
                cageNumber = "25B";
                x = 561;
                y = 307;
                xlength = 25;
                ylength = 30;
            }
            else if (i == 141)
            {
                cageNumber = "26B";
                x = 608;
                y = 71;
                xlength = 24;
                ylength = 28;
            }
            else if (i == 142)
            {
                cageNumber = "27B";
                x = 609;
                y = 102;
                xlength = 23;
                ylength = 28;
            }
            else if (i == 143)
            {
                cageNumber = "28B";
                x = 607;
                y = 139;
                xlength = 24;
                ylength = 25;
            }
            else if (i == 144)
            {
                cageNumber = "29B";
                x = 604;
                y = 172;
                xlength = 24;
                ylength = 26;
            }
            else if (i == 145)
            {
                cageNumber = "30B";
                x = 603;
                y = 205;
                xlength = 24;
                ylength = 29;
            }
            else if (i == 146)
            {
                cageNumber = "31B";
                x = 601;
                y = 241;
                xlength = 24;
                ylength = 27;
            }
            else if (i == 147)
            {
                cageNumber = "32B";
                x = 599;
                y = 276;
                xlength = 25;
                ylength = 27;
            }
            else
            {
                cageNumber = "33B";
                x = 597;
                y = 311;
                xlength = 25;
                ylength = 28;
            }
            button = new JButton(cageNumber);
            button.setBounds(x, y, xlength, ylength);
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setForeground(new Color(0, 0, 0, 0));
            
            button.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    IndexCursor cursor;
                    Row latestRow = null;
                    String penNumber = ((JButton) e.getSource()).getText();
                    try
                    {
                        cursor = CursorBuilder.createCursor(table.getIndex("PenNumberIndex"));
                        cursor.beforeFirst();
                        if (penNumber.equals("9") || penNumber.equals("10") || penNumber.equals("12") || penNumber.equals("13") || penNumber.equals("15") || penNumber.equals("18") || penNumber.equals("61"))
                        {
                            for (int i = 1; i <= 4; i++)
                            {
                                cursor.beforeFirst();
                                cursor.findFirstRow(Collections.singletonMap("Pen Number", penNumber + "-" + i));
                                latestRow = cursor.getCurrentRow();                         
                                while (cursor.findNextRow(Collections.singletonMap("Pen Number", ((JButton) e.getSource()).getText())))
                                {
                                    Row row = cursor.getCurrentRow();
                                    if (row != null)
                                    {
                                        latestRow = row;
                                    }
                                }
                                System.out.println(latestRow.get("Pen Number") + " " + latestRow.get("Gator Count") + " " + latestRow.get("Current Date"));
                            }
                            System.out.println();
                        }
                        else
                        {
                            cursor.findFirstRow(Collections.singletonMap("Pen Number", penNumber));
                            latestRow = cursor.getCurrentRow();
                            while (cursor.findNextRow(Collections.singletonMap("Pen Number", ((JButton) e.getSource()).getText())))
                            {
                                Row row = cursor.getCurrentRow();
                                if (row != null)
                                {
                                    latestRow = row;
                                }
                            }
                            System.out.println(latestRow.get("Pen Number") + " " + latestRow.get("Gator Count") + " " + latestRow.get("Current Date"));
                            System.out.println();
                        }
                    }
                    catch (IOException e1)
                    {
                        
                    }
                    
                }
            });
            cages[i-1] = button;
        }
                
    }
    
    public void addComponents()
    {
        //contentPane.removeAll();
        
        Panel panel = new Panel(new FlowLayout());
        
        for (int i = 0; i < cages.length; i++)
        {
            frame.add(cages[i]);
        }
        
        //contentPane.add(panel);
                
        validate();
        setVisible(true);
    }
 
    public static void main(String[] args)
    {
        frame = new CageApplication();
        frame.setContentPane(new JPanel() {
        Image image = Toolkit.getDefaultToolkit().createImage("farm.jpg");
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    });
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        double length = rect.getHeight();
        double width = rect.getWidth();
        Dimension screenSize = new Dimension(780, 594);
        frame.getContentPane().setPreferredSize(screenSize);
        frame.addComponents();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);   
    }
    
    
}
