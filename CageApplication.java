package test;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import com.healthmarketscience.jackcess.*;
import java.io.*;
import java.util.Collections;
import java.awt.Graphics;

public class CageApplication extends JFrame
{
    private static CageApplication frame;
    private JButton[] cages;
    private File file;
    private Table table;
    Image image;
    
    public CageApplication()
    {
        super("Application");
        
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
                cageNumber = "901";
                x = 451;
                y = 386;
                xlength = 20;
                ylength = 19;
            }
            else if (i == 2)
            {
                cageNumber = "905";
                x = 477;
                y = 384;
                xlength = 18;
                ylength = 18;
            }
            else if (i == 3)
            {
                cageNumber = "902";
                x = 454;
                y = 410;
                xlength = 19;
                ylength = 19;
            }
            else if (i == 4)
            {
                cageNumber = "906";
                x = 479;
                y = 407;
                xlength = 19;
                ylength = 19;
            }
            else if (i == 5)
            {
                cageNumber = "903";
                x = 456;
                y = 433;
                xlength = 19;
                ylength = 18;
                
            }
            else if (i == 6)
            {
                cageNumber = "907";
                x = 482;
                y = 430;
                xlength = 18;
                ylength = 18;
            }
            else if (i == 7)
            {
                cageNumber = "904";
                x = 459;
                y = 455;
                xlength = 18;
                ylength = 18;
            }
            else if (i == 8)
            {
                cageNumber = "908";
                x = 484;
                y = 452;
                xlength = 19;
                ylength = 19;
            }
            else if (i == 9)
            {
                cageNumber = "410";
                x = 508;
                y = 398;
                xlength = 35;
                ylength = 35;
            }
            else if (i == 10)
            {
                cageNumber = "420";
                x = 553;
                y = 394;
                xlength = 33;
                ylength = 32;
            }
            else if (i == 11)
            {
                cageNumber = "909";
                x = 505;
                y = 373;
                xlength = 18;
                ylength = 17;
            }
            else if (i == 12)
            {
                cageNumber = "411";
                x = 513;
                y = 437;
                xlength = 34;
                ylength = 32;
            }
            else if (i == 13)
            {
                cageNumber = "421";
                x = 557;
                y = 432;
                xlength = 34;
                ylength = 33;
            }
            else if (i == 14)
            {
                cageNumber = "910";
                x = 532;
                y = 367;
                xlength = 18;
                ylength = 17;
            }
            else if (i == 15)
            {
                cageNumber = "101";
                x = 89;
                y = 162;
                xlength = 29;
                ylength = 29;
            }
            else if (i == 16)
            {
                cageNumber = "102";
                x = 89;
                y = 196;
                xlength = 30;
                ylength = 29;
            }
            else if (i == 17)
            {
                cageNumber = "103";
                x = 89;
                y = 230;
                xlength = 31;
                ylength = 30;
            }
            else if (i == 18)
            {
                cageNumber = "104";
                x = 90;
                y = 265;
                xlength = 31;
                ylength = 30;
            }
            else if (i == 19)
            {
                cageNumber = "105";
                x = 92;
                y = 302;
                xlength = 29;
                ylength = 30;
            }
            else if (i == 20)
            {
                cageNumber = "106";
                x = 90;
                y = 338;
                xlength = 32;
                ylength = 31;
            }
            else if (i == 21)
            {
                cageNumber = "107";
                x = 91;
                y = 375;
                xlength = 32;
                ylength = 32;
            }
            else if (i == 22)
            {
                cageNumber = "108";
                x = 92;
                y = 412;
                xlength = 33;
                ylength = 31;
            }
            else if (i == 23)
            {
                cageNumber = "109";
                x = 95;
                y = 451;
                xlength = 30;
                ylength = 31;
            }
            else if (i == 24)
            {
                cageNumber = "110";
                x = 129;
                y = 116;
                xlength = 30;
                ylength = 28;
            }
            else if (i == 25)
            {
                cageNumber = "111";
                x = 132;
                y = 159;
                xlength = 29;
                ylength = 29;
            }
            else if (i == 26)
            {
                cageNumber = "112";
                x = 132;
                y = 193;
                xlength = 31;
                ylength = 29;
            }
            else if (i == 27)
            {
                cageNumber = "113";
                x = 134;
                y = 227;
                xlength = 29;
                ylength = 29;
            }
            else if (i == 28)
            {
                cageNumber = "114";
                x = 135;
                y = 262;
                xlength = 29;
                ylength = 30;
            }
            else if (i == 29)
            {
                cageNumber = "115";
                x = 135;
                y = 297;
                xlength = 31;
                ylength = 31;
            }
            else if (i == 30)
            {
                cageNumber = "116";
                x = 137;
                y = 335;
                xlength = 29;
                ylength = 30;
            }
            else if (i == 31)
            {
                cageNumber = "117";
                x = 137;
                y = 371;
                xlength = 31;
                ylength = 30;
            }
            else if (i == 32)
            {
                cageNumber = "118";
                x = 138;
                y = 409;
                xlength = 32;
                ylength = 30;
            }
            else if (i == 33)
            {
                cageNumber = "119";
                x = 140;
                y = 447;
                xlength = 31;
                ylength = 30;
            }
            else if (i == 34)
            {
                cageNumber = "120";
                x = 141;
                y = 485;
                xlength = 31;
                ylength = 31;
            }
            else if (i == 35)
            {
                cageNumber = "121";
                x = 164;
                y = 80;
                xlength = 29;
                ylength = 28;
            }
            else if (i == 36)
            {
                cageNumber = "122";
                x = 166;
                y = 114;
                xlength = 28;
                ylength = 27;
            }
            else if (i == 37)
            {
                cageNumber = "123";
                x = 167;
                y = 156;
                xlength = 29;
                ylength = 29;
            }
            else if (i == 38)
            {
                cageNumber = "124";
                x = 169;
                y = 190;
                xlength = 29;
                ylength = 29;
            }
            else if (i == 39)
            {
                cageNumber = "125";
                x = 170;
                y = 224;
                xlength = 29;
                ylength = 30;
            }
            else if (i == 40)
            {
                cageNumber = "126";
                x = 171;
                y = 259;
                xlength = 29;
                ylength = 30;
            }
            else if (i == 41)
            {
                cageNumber = "127";
                x = 172;
                y = 295;
                xlength = 30;
                ylength = 30;
            }
            else if (i == 42)
            {
                cageNumber = "201";
                x = 206;
                y = 79;
                xlength = 30;
                ylength = 27;
            }
            else if (i == 43)
            {
                cageNumber = "202";
                x = 208;
                y = 111;
                xlength = 30;
                ylength = 27;
            }
            else if (i == 44)
            {
                cageNumber = "203";
                x = 210;
                y = 153;
                xlength = 30;
                ylength = 29;
            }
            else if (i == 45)
            {
                cageNumber = "204";
                x = 211;
                y = 187;
                xlength = 30;
                ylength = 30;
            }
            else if (i == 46)
            {
                cageNumber = "205";
                x = 212;
                y = 221;
                xlength = 31;
                ylength = 29;
            }
            else if (i == 47)
            {
                cageNumber = "206";
                x = 214;
                y = 255;
                xlength = 30;
                ylength = 29;
            }
            else if (i == 48)
            {
                cageNumber = "207";
                x = 215;
                y = 289;
                xlength = 30;
                ylength = 29;
            }
            else if (i == 49)
            {
                cageNumber = "208";
                x = 223;
                y = 322;
                xlength = 31;
                ylength = 30;
            }
            else if (i == 50)
            {
                cageNumber = "209";
                x = 219;
                y = 360;
                xlength = 32;
                ylength = 31;
            }
            else if (i == 51)
            {
                cageNumber = "210";
                x = 215;
                y = 400;
                xlength = 30;
                ylength = 30;
            }
            else if (i == 52)
            {
                cageNumber = "211";
                x = 210;
                y = 438;
                xlength = 31;
                ylength = 33;
            }
            else if (i == 53)
            {
                cageNumber = "212";
                x = 206;
                y = 477;
                xlength = 30;
                ylength = 32;
            }
            else if (i == 54)
            {
                cageNumber = "213";
                x = 201;
                y = 516;
                xlength = 31;
                ylength = 33;
            }
            else if (i == 55)
            {
                cageNumber = "214";
                x = 245;
                y = 151;
                xlength = 29;
                ylength = 28;
            }
            else if (i == 56)
            {
                cageNumber = "215";
                x = 247;
                y = 186;
                xlength = 30;
                ylength = 28;
            }
            else if (i == 57)
            {
                cageNumber = "216";
                x = 248;
                y = 221;
                xlength = 30;
                ylength = 29;
            }
            else if (i == 58)
            {
                cageNumber = "217";
                x = 250;
                y = 255;
                xlength = 30;
                ylength = 30;
            }
            else if (i == 59)
            {
                cageNumber = "218";
                x = 251;
                y = 290;
                xlength = 30;
                ylength = 29;
            }
            else if (i == 60)
            {
                cageNumber = "219";
                x = 263;
                y = 325;
                xlength = 31;
                ylength = 31;
            }
            else if (i == 61)
            {
                cageNumber = "220";
                x = 259;
                y = 363;
                xlength = 30;
                ylength = 32;
            }
            else if (i == 62)
            {
                cageNumber = "221";
                x = 253;
                y = 402;
                xlength = 32;
                ylength = 33;
            }
            else if (i == 63)
            {
                cageNumber = "222";
                x = 250;
                y = 442;
                xlength = 31;
                ylength = 33;
            }
            else if (i == 64)
            {
                cageNumber = "223";
                x = 247;
                y = 480;
                xlength = 31;
                ylength = 34;
            }
            else if (i == 65)
            {
                cageNumber = "224";
                x = 243;
                y = 520;
                xlength = 31;
                ylength = 33;
            }
            else if (i == 66)
            {
                cageNumber = "301";
                x = 288;
                y = 52;
                xlength = 30;
                ylength = 27;
            }
            else if (i == 67)
            {
                cageNumber = "302";
                x = 290;
                y = 86;
                xlength = 29;
                ylength = 27;
            }
            else if (i == 68)
            {
                cageNumber = "303";
                x = 294;
                y = 139;
                xlength = 29;
                ylength = 29;
            }
            else if (i == 69)
            {
                cageNumber = "304";
                x = 295;
                y = 174;
                xlength = 30;
                ylength = 30;
            }
            else if (i == 70)
            {
                cageNumber = "225";
                x = 334;
                y = 326;
                xlength = 30;
                ylength = 31;
            }
            else if (i == 71)
            {
                cageNumber = "226";
                x = 331;
                y = 363;
                xlength = 31;
                ylength = 32;
            }
            else if (i == 72)
            {
                cageNumber = "227";
                x = 326;
                y = 413;
                xlength = 31;
                ylength = 32;
            }
            else if (i == 73)
            {
                cageNumber = "228";
                x = 324;
                y = 452;
                xlength = 31;
                ylength = 33;
            }
            else if (i == 74)
            {
                cageNumber = "305";
                x = 330;
                y = 48;
                xlength = 30;
                ylength = 28;
            }
            else if (i == 75)
            {
                cageNumber = "306";
                x = 331;
                y = 83;
                xlength = 31;
                ylength = 28;
            }
            else if (i == 76)
            {
                cageNumber = "307";
                x = 336;
                y = 134;
                xlength = 30;
                ylength = 31;
            }
            else if (i == 77)
            {
                cageNumber = "308";
                x = 338;
                y = 171;
                xlength = 30;
                ylength = 30;
            }
            else if (i == 78)
            {
                cageNumber = "229";
                x = 378;
                y = 328;
                xlength = 30;
                ylength = 32;
            }
            else if (i == 79)
            {
                cageNumber = "230";
                x = 375;
                y = 367;
                xlength = 30;
                ylength = 31;
            }
            else if (i == 80)
            {
                cageNumber = "231";
                x = 371;
                y = 416;
                xlength = 30;
                ylength = 32;
            }
            else if (i == 81)
            {
                cageNumber = "232";
                x = 368;
                y = 454;
                xlength = 30;
                ylength = 33;
            }
            else if (i == 82)
            {
                cageNumber = "309";
                x = 373;
                y = 46;
                xlength = 29;
                ylength = 27;
            }
            else if (i == 83)
            {
                cageNumber = "310";
                x = 376;
                y = 80;
                xlength = 29;
                ylength = 28;
            }
            else if (i == 84)
            {
                cageNumber = "311";
                x = 380;
                y = 120;
                xlength = 29;
                ylength = 30;
            }
            else if (i == 85)
            {
                cageNumber = "312";
                x = 407;
                y = 44;
                xlength = 28;
                ylength = 27;
            }
            else if (i == 86)
            {
                cageNumber = "313";
                x = 409;
                y = 77;
                xlength = 29;
                ylength = 29;
            }
            else if (i == 87)
            {
                cageNumber = "314";
                x = 416;
                y = 119;
                xlength = 29;
                ylength = 30;
            }
            else if (i == 88)
            {
                cageNumber = "315";
                x = 412;
                y = 152;
                xlength = 30;
                ylength = 30;
            }
            else if (i == 89)
            {
                cageNumber = "316";
                x = 408;
                y = 186;
                xlength = 31;
                ylength = 30;
            }
            else if (i == 90)
            {
                cageNumber = "317";
                x = 405;
                y = 221;
                xlength = 31;
                ylength = 30;
            }
            else if (i == 91)
            {
                cageNumber = "318";
                x = 455;
                y = 57;
                xlength = 30;
                ylength = 28;
            }
            else if (i == 92)
            {
                cageNumber = "319";
                x = 453;
                y = 90;
                xlength = 29;
                ylength = 28;
            }
            else if (i == 93)
            {
                cageNumber = "320";
                x = 450;
                y = 122;
                xlength = 29;
                ylength = 29;
            }
            else if (i == 94)
            {
                cageNumber = "321";
                x = 447;
                y = 156;
                xlength = 30;
                ylength = 29;
            }
            else if (i == 95)
            {
                cageNumber = "322";
                x = 444;
                y = 189;
                xlength = 30;
                ylength = 30;
            }
            else if (i == 96)
            {
                cageNumber = "323";
                x = 441;
                y = 223;
                xlength = 30;
                ylength = 31;
            }
            else if (i == 97)
            {
                cageNumber = "324";
                x = 438;
                y = 259;
                xlength = 29;
                ylength = 31;
            }
            else if (i == 98)
            {
                cageNumber = "325";
                x = 434;
                y = 294;
                xlength = 31;
                ylength = 32;
            }
            else if (i == 99)
            {
                cageNumber = "326";
                x = 431;
                y = 330;
                xlength = 30;
                ylength = 31;
            }
            else if (i == 100)
            {
                cageNumber = "801";
                x = 295;
                y = 226;
                xlength = 17;
                ylength = 18;
            }
            else if (i == 101)
            {
                cageNumber = "802";
                x = 296;
                y = 248;
                xlength = 18;
                ylength = 18;
            }
            else if (i == 102)
            {
                cageNumber = "803";
                x = 297;
                y = 271;
                xlength = 18;
                ylength = 18;
            }
            else if (i == 103)
            {
                cageNumber = "804";
                x = 298;
                y = 294;
                xlength = 18;
                ylength = 18;
            }
            else if (i == 104)
            {
                cageNumber = "805";
                x = 319;
                y = 224;
                xlength = 18;
                ylength = 18;
            }
            else if (i == 105)
            {
                cageNumber = "806";
                x = 320;
                y = 246;
                xlength = 18;
                ylength = 18;
            }
            else if (i == 106)
            {
                cageNumber = "807";
                x = 322;
                y = 269;
                xlength = 17;
                ylength = 18;
            }
            else if (i == 107)
            {
                cageNumber = "808";
                x = 322;
                y = 292;
                xlength = 18;
                ylength = 17;
            }
            else if (i == 108)
            {
                cageNumber = "809";
                x = 344;
                y = 228;
                xlength = 18;
                ylength = 19;
            }
            else if (i == 109)
            {
                cageNumber = "810";
                x = 346;
                y = 250;
                xlength = 17;
                ylength = 17;
            }
            else if (i == 110)
            {
                cageNumber = "811";
                x = 347;
                y = 273;
                xlength = 18;
                ylength = 17;
            }
            else if (i == 111)
            {
                cageNumber = "812";
                x = 349;
                y = 296;
                xlength = 17;
                ylength = 18;
            }
            else if (i == 112)
            {
                cageNumber = "813";
                x = 369;
                y = 226;
                xlength = 17;
                ylength = 17;
            }
            else if (i == 113)
            {
                cageNumber = "814";
                x = 370;
                y = 248;
                xlength = 18;
                ylength = 18;
            }
            else if (i == 114)
            {
                cageNumber = "815";
                x = 372;
                y = 272;
                xlength = 17;
                ylength = 17;
            }
            else if (i == 115)
            {
                cageNumber = "816";
                x = 374;
                y = 295;
                xlength = 17;
                ylength = 17;
            }
            else if (i == 116)
            {
                cageNumber = "401";
                x = 498;
                y = 61;
                xlength = 29;
                ylength = 29;
            }
            else if (i == 117)
            {
                cageNumber = "402";
                x = 495;
                y = 93;
                xlength = 30;
                ylength = 30;
            }
            else if (i == 118)
            {
                cageNumber = "403";
                x = 492;
                y = 126;
                xlength = 30;
                ylength = 30;
            }
            else if (i == 119)
            {
                cageNumber = "404";
                x = 489;
                y = 159;
                xlength = 31;
                ylength = 31;
            }
            else if (i == 120)
            {
                cageNumber = "405";
                x = 487;
                y = 193;
                xlength = 30;
                ylength = 31;
            }
            else if (i == 121)
            {
                cageNumber = "406";
                x = 484;
                y = 227;
                xlength = 31;
                ylength = 32;
            }
            else if (i == 122)
            {
                cageNumber = "407";
                x = 480;
                y = 261;
                xlength = 32;
                ylength = 33;
            }
            else if (i == 123)
            {
                cageNumber = "408";
                x = 479;
                y = 298;
                xlength = 30;
                ylength = 32;
            }
            else if (i == 124)
            {
                cageNumber = "409";
                x = 476;
                y = 334;
                xlength = 31;
                ylength = 32;
            }
            else if (i == 125)
            {
                cageNumber = "412";
                x = 532;
                y = 64;
                xlength = 29;
                ylength = 29;
            }
            else if (i == 126)
            {
                cageNumber = "413";
                x = 529;
                y = 96;
                xlength = 30;
                ylength = 29;
            }
            else if (i == 127)
            {
                cageNumber = "414";
                x = 527;
                y = 129;
                xlength = 30;
                ylength = 30;
            }
            else if (i == 128)
            {
                cageNumber = "415";
                x = 525;
                y = 162;
                xlength = 30;
                ylength = 30;
            }
            else if (i == 129)
            {
                cageNumber = "416";
                x = 521;
                y = 195;
                xlength = 31;
                ylength = 32;
            }
            else if (i == 130)
            {
                cageNumber = "417";
                x = 519;
                y = 230;
                xlength = 31;
                ylength = 32;
            }
            else if (i == 131)
            {
                cageNumber = "418";
                x = 517;
                y = 265;
                xlength = 31;
                ylength = 32;
            }
            else if (i == 132)
            {
                cageNumber = "419";
                x = 515;
                y = 301;
                xlength = 30;
                ylength = 32;
            }
            else if (i == 133)
            {
                cageNumber = "422";
                x = 574;
                y = 67;
                xlength = 29;
                ylength = 30;
            }
            else if (i == 134)
            {
                cageNumber = "423";
                x = 572;
                y = 100;
                xlength = 30;
                ylength = 30;
            }
            else if (i == 135)
            {
                cageNumber = "424";
                x = 570;
                y = 133;
                xlength = 30;
                ylength = 30;
            }
            else if (i == 136)
            {
                cageNumber = "425";
                x = 568;
                y = 166;
                xlength = 30;
                ylength = 31;
            }
            else if (i == 137)
            {
                cageNumber = "426";
                x = 565;
                y = 200;
                xlength = 31;
                ylength = 32;
            }
            else if (i == 138)
            {
                cageNumber = "427";
                x = 564;
                y = 235;
                xlength = 30;
                ylength = 31;
            }
            else if (i == 139)
            {
                cageNumber = "428";
                x = 561;
                y = 270;
                xlength = 32;
                ylength = 32;
            }
            else if (i == 140)
            {
                cageNumber = "429";
                x = 560;
                y = 305;
                xlength = 31;
                ylength = 32;
            }
            else if (i == 141)
            {
                cageNumber = "430";
                x = 608;
                y = 71;
                xlength = 30;
                ylength = 29;
            }
            else if (i == 142)
            {
                cageNumber = "431";
                x = 607;
                y = 102;
                xlength = 29;
                ylength = 31;
            }
            else if (i == 143)
            {
                cageNumber = "432";
                x = 604;
                y = 136;
                xlength = 30;
                ylength = 30;
            }
            else if (i == 144)
            {
                cageNumber = "433";
                x = 603;
                y = 169;
                xlength = 29;
                ylength = 31;
            }
            else if (i == 145)
            {
                cageNumber = "434";
                x = 601;
                y = 203;
                xlength = 30;
                ylength = 32;
            }
            else if (i == 146)
            {
                cageNumber = "435";
                x = 600;
                y = 238;
                xlength = 30;
                ylength = 32;
            }
            else if (i == 147)
            {
                cageNumber = "436";
                x = 598;
                y = 273;
                xlength = 30;
                ylength = 33;
            }
            else
            {
                cageNumber = "437";
                x = 596;
                y = 308;
                xlength = 31;
                ylength = 33;
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
                        if (penNumber.equals("227") || penNumber.equals("232") || penNumber.equals("410") || penNumber.equals("411") || penNumber.equals("420") || penNumber.equals("421"))
                        {
                            for (int i = 1; i <= 4; i++)
                            {
                                cursor.beforeFirst();
                                cursor.findFirstRow(Collections.singletonMap("Pen Number", penNumber + "." + i));
                                latestRow = cursor.getCurrentRow();                         
                                while (cursor.findNextRow(Collections.singletonMap("Pen Number", penNumber + "." + i)))
                                {
                                    Row row = cursor.getCurrentRow();
                                    if (row != null)
                                    {
                                        latestRow = row;
                                    }
                                }
                                System.out.println("Pen: " + latestRow.get("Pen Number") + ", Gator Count: " + latestRow.get("Gator Count"));
                            }
                        }
                        else
                        {
                            cursor.findFirstRow(Collections.singletonMap("Pen Number", penNumber));
                            latestRow = cursor.getCurrentRow();
                            while (cursor.findNextRow(Collections.singletonMap("Pen Number", penNumber)))
                            {
                                Row row = cursor.getCurrentRow();
                                if (row != null)
                                {
                                    latestRow = row;
                                }
                            }
                            System.out.println("Pen: " + latestRow.get("Pen Number") + ", Gator Count: " + latestRow.get("Gator Count"));
                        }
                    }
                    catch (IOException e1)
                    {
                        
                    }
                    System.out.println();
                }
            });
            cages[i-1] = button;
        }
                
    }
    
    public void addComponents()
    {        
        for (int i = 0; i < cages.length; i++)
        {
            frame.add(cages[i]);
        }
                
        validate();
        setVisible(true);
    }
 
    public static void main(String[] args)
    {
        frame = new CageApplication();
        frame.setContentPane(new JPanel()
        {
            Image image = Toolkit.getDefaultToolkit().createImage("farm.jpg");
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this);
            }
        });
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = new Dimension(780, 594);
        frame.getContentPane().setPreferredSize(screenSize);
        frame.addComponents();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);   
    }
    
    
}
