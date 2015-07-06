/**
 * Test method to set up the databases with dummy data
 * 
 * @Phillip Dingler [phil50@ufl.edu]
 */

package test;

import com.healthmarketscience.jackcess.*;
import java.io.*;

public class InitDatabases
{    
    public static void main(String[] args)
    {
        File gatorFile;
        Database gatordb;
        Table gatorTable = null;
        File penFile;
        Database pendb;
        Table penTable = null;
        BufferedWriter classWriter;
        BufferedWriter quarteredWriter;
        
        try
        {
            gatorFile = new File("AnimalDatabase.accdb");
            gatordb = new DatabaseBuilder(gatorFile).setFileFormat(Database.FileFormat.V2000).create();
            gatorTable = new TableBuilder("Database")
                .addColumn(new ColumnBuilder("ID", DataType.LONG).setAutoNumber(true))
                .addIndex(new IndexBuilder("IDIndex").addColumns("ID"))
                .addColumn(new ColumnBuilder("Tag Number", DataType.TEXT))
                .addIndex(new IndexBuilder("TagIndex").addColumns("Tag Number"))
                .addColumn(new ColumnBuilder("Egg Nest Location", DataType.TEXT))
                .addColumn(new ColumnBuilder("Egg Nest Condition", DataType.TEXT))
                .addColumn(new ColumnBuilder("Egg Collection Date", DataType.TEXT))
                .addColumn(new ColumnBuilder("Hatch Year", DataType.TEXT))
                .addColumn(new ColumnBuilder("Gender", DataType.TEXT))
                .addColumn(new ColumnBuilder("Umbilical", DataType.TEXT))
                .addColumn(new ColumnBuilder("Date", DataType.TEXT))
                .addColumn(new ColumnBuilder("From", DataType.TEXT))
                .addIndex(new IndexBuilder("FromIndex").addColumns("From"))
                .addColumn(new ColumnBuilder("To", DataType.TEXT))
                .addIndex(new IndexBuilder("ToIndex").addColumns("To"))
                .addColumn(new ColumnBuilder("Belly Size", DataType.TEXT))
                .addColumn(new ColumnBuilder("Length", DataType.TEXT))
                .addColumn(new ColumnBuilder("Weight", DataType.TEXT))
                .addColumn(new ColumnBuilder("Special Recipe", DataType.TEXT))
                .addColumn(new ColumnBuilder("Experiment Code", DataType.TEXT))
                .addColumn(new ColumnBuilder("Vaccinated", DataType.TEXT))
                .addColumn(new ColumnBuilder("Comments", DataType.TEXT))
                .addColumn(new ColumnBuilder("Harvested?", DataType.TEXT))
                .toTable(gatordb);
            
            penFile = new File("PenDatabase.accdb");
            pendb = new DatabaseBuilder(penFile).setFileFormat(Database.FileFormat.V2000).create();
            penTable = new TableBuilder("Database")
                .addColumn(new ColumnBuilder("ID", DataType.LONG).setAutoNumber(true))
                .addIndex(new IndexBuilder(IndexBuilder.PRIMARY_KEY_NAME).addColumns("ID").setPrimaryKey())
                .addColumn(new ColumnBuilder("Pen Number", DataType.TEXT))
                .addIndex(new IndexBuilder("PenNumberIndex").addColumns("Pen Number"))
                .addColumn(new ColumnBuilder("Pen Type", DataType.TEXT))
                .addColumn(new ColumnBuilder("Square Footage", DataType.TEXT))
                .addColumn(new ColumnBuilder("Water Change Date", DataType.TEXT))
                .addColumn(new ColumnBuilder("Water Temperature", DataType.TEXT))
                .addColumn(new ColumnBuilder("Feed Type", DataType.TEXT))
                .addColumn(new ColumnBuilder("Feed Amount", DataType.TEXT))
                .addColumn(new ColumnBuilder("Size Class", DataType.TEXT))
                .addColumn(new ColumnBuilder("Comments", DataType.TEXT))
                .toTable(pendb);
            
            classWriter = new BufferedWriter(new FileWriter("ClassSizes.txt", false));
            quarteredWriter = new BufferedWriter(new FileWriter("QuarteredPens.txt", false));
            
            classWriter.write("Empty,Hatchling,Family,15-18,19-23,24-28,29-33,34-36,37-38,39+");
            quarteredWriter.write("410,411,420,421,227,232");
            
            classWriter.close();
            quarteredWriter.close();
        }
        catch (IOException e)
        {
            
        }
        
        InitPenDatabase(penTable);
        InitGatorDatabase(gatorTable);
    }
    
    public static void InitPenDatabase(Table penTable)
    {
        try
        {
            for (int i = 101; i <= 127; i++)
            {
                penTable.addRow(0, i, "Large", 600, "06-01-2015", "88", "R", "500", "39+", "");
            }
            for (int i = 201; i <= 226; i++)
            {
                penTable.addRow(0, i, "Large", 600, "06-01-2015", "88", "R", "500", "15-18", "");
            }
            for (int i = 1; i <= 4; i++)
            {
                penTable.addRow(0, "227." + i, "Quartered", 150, "06-01-2015", "88", "H", "50", "Hatchling", "");
            }
            for (int i = 228; i <= 231; i++)
            {
                penTable.addRow(0, i, "Large", 600, "06-01-2015", "88", "R", "500", "19-23", "");
            }
            for (int i = 1; i <= 4; i++)
            {
                penTable.addRow(0, "232." + i, "Quartered", 150, "06-01-2015", "88", "H", "50", "Hatchling", "");
            }
            for (int i = 301; i <= 326; i++)
            {
                penTable.addRow(0, i, "Large", 600, "06-01-2015", "88", "R", "500", "24-28", "");
            }
            for (int i = 401; i <= 409; i++)
            {
                penTable.addRow(0, i, "Large", 600, "06-01-2015", "88", "R", "500", "29-33", "");
            }
            for (int i = 1; i <= 4; i++)
            {
                penTable.addRow(0, "410." + i, "Quartered", 150, "06-01-2015", "88", "H", "50", "Hatchling", "");
            }
            for (int i = 1; i <= 4; i++)
            {
                penTable.addRow(0, "411." + i, "Quartered", 150, "06-01-2015", "88", "H", "50", "Hatchling", "");
            }
            for (int i = 412; i <= 419; i++)
            {
                penTable.addRow(0, i, "Large", 600, "06-01-2015", "88", "R", "500", "34-36", "");
            }
            for (int i = 1; i <= 4; i++)
            {
                penTable.addRow(0, "420." + i, "Quartered", 150, "06-01-2015", "88", "H", "50", "Hatchling", "");
            }
            for (int i = 1; i <= 4; i++)
            {
                penTable.addRow(0, "421." + i, "Quartered", 150, "06-01-2015", "88", "H", "50", "Hatchling", "");
            }
            for (int i = 422; i <= 437; i++)
            {
                penTable.addRow(0, i, "Large", 600, "06-01-2015", "88", "R", "500", "37-38", "");
            }
            for (int i = 801; i <= 816; i++)
            {
                penTable.addRow(0, i, "Small", 200, "06-01-2015", "88", "H", "50", "Family", "");
            }
            for (int i = 901; i <= 910; i++)
            {
                penTable.addRow(0, i, "Small", 200, "06-01-2015", "88", "H", "50", "Empty", "");
            }
        }
        catch (IOException e)
        {
            
        }
    }
    
    public static void InitGatorDatabase(Table gatorTable)
    {
        try
        {
            String currentDate = "06-29-2015";
            int j = 1;
            for (int i = 0; i < 200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "101", "15", "25", "25", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 200; i < 400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "102", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 400; i < 600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "103", "25", "40", "40", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 600; i < 800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "104", "32", "50", "50", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 800; i < 1000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "105", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1000; i < 1200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "106", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1200; i < 1400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "107", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1400; i < 1600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "108", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1600; i < 1800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "109", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1800; i < 2000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "110", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2000; i < 2200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "111", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2200; i < 2400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "112", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2400; i < 2600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "113", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2600; i < 2800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "114", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2800; i < 3000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "115", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3000; i < 3200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "116", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3200; i < 3400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "117", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3400; i < 3600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "118", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3600; i < 3800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "119", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3800; i < 4000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "120", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4000; i < 4200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "121", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4200; i < 4400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "122", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4400; i < 4600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "123", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4600; i < 4800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "124", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4800; i < 5000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "125", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 5000; i < 5200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "126", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 5200; i < 5400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "", "127", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            
            
            currentDate = "06-30-2015";
            j++;
            for (int i = 0; i < 200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "101", "102", "15", "25", "25", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 200; i < 400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "102", "103", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 400; i < 600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "103", "104", "25", "40", "40", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 600; i < 800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "104", "105", "32", "50", "50", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 800; i < 1000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "105", "106", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1000; i < 1200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "106", "107", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1200; i < 1400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "107", "108", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1400; i < 1600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "108", "109", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1600; i < 1800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "109", "110", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1800; i < 2000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "110", "111", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2000; i < 2200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "111", "112", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2200; i < 2400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "112", "113", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2400; i < 2600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "113", "114", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2600; i < 2800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "114", "115", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2800; i < 3000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "115", "116", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3000; i < 3200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "116", "117", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3200; i < 3400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "117", "118", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3400; i < 3600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "118", "119", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3600; i < 3800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "119", "120", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3800; i < 4000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "120", "121", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4000; i < 4200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "121", "122", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4200; i < 4400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "122", "123", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4400; i < 4600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "123", "124", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4600; i < 4800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "124", "125", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4800; i < 5000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "125", "126", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 5000; i < 5200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "126", "127", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 5200; i < 5400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "127", "101", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            
            currentDate = "07-01-2015";
            j++;
            for (int i = 0; i < 200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "102", "103", "15", "25", "25", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 200; i < 400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "103", "104", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 400; i < 600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "104", "105", "25", "40", "40", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 600; i < 800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "105", "106", "32", "50", "50", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 800; i < 1000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "106", "107", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1000; i < 1200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "107", "108", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1200; i < 1400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "108", "109", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1400; i < 1600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "109", "110", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1600; i < 1800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "110", "111", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1800; i < 2000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "111", "112", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2000; i < 2200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "112", "113", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2200; i < 2400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "113", "114", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2400; i < 2600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "114", "115", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2600; i < 2800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "115", "116", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2800; i < 3000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "116", "117", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3000; i < 3200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "117", "118", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3200; i < 3400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "118", "119", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3400; i < 3600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "119", "120", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3600; i < 3800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "120", "121", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3800; i < 4000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "121", "122", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4000; i < 4200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "122", "123", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4200; i < 4400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "123", "124", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4400; i < 4600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "124", "125", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4600; i < 4800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "125", "126", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4800; i < 5000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "126", "127", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 5000; i < 5200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "127", "101", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 5200; i < 5400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "101", "102", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            
            currentDate = "07-02-2015";
            j++;
            for (int i = 0; i < 200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "103", "104", "15", "25", "25", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 200; i < 400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "104", "105", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 400; i < 600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "105", "106", "25", "40", "40", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 600; i < 800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "106", "107", "32", "50", "50", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 800; i < 1000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "107", "108", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1000; i < 1200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "108", "109", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1200; i < 1400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "109", "110", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1400; i < 1600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "110", "111", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1600; i < 1800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "111", "112", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1800; i < 2000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "112", "113", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2000; i < 2200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "113", "114", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2200; i < 2400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "114", "115", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2400; i < 2600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "115", "116", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2600; i < 2800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "116", "117", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2800; i < 3000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "117", "118", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3000; i < 3200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "118", "119", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3200; i < 3400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "119", "120", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3400; i < 3600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "120", "121", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3600; i < 3800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "121", "122", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3800; i < 4000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "122", "123", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4000; i < 4200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "123", "124", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4200; i < 4400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "124", "125", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4400; i < 4600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "125", "126", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4600; i < 4800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "126", "127", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4800; i < 5000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "127", "101", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 5000; i < 5200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "101", "102", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 5200; i < 5400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "102", "103", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            
            currentDate = "07-03-2015";
            j++;
            for (int i = 0; i < 200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "104", "105", "15", "25", "25", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 200; i < 400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "105", "106", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 400; i < 600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "106", "107", "25", "40", "40", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 600; i < 800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "107", "108", "32", "50", "50", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 800; i < 1000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "108", "109", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1000; i < 1200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "109", "110", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1200; i < 1400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "110", "111", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1400; i < 1600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "111", "112", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1600; i < 1800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "112", "113", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1800; i < 2000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "113", "114", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2000; i < 2200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "114", "115", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2200; i < 2400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "115", "116", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2400; i < 2600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "116", "117", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2600; i < 2800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "117", "118", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2800; i < 3000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "118", "119", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3000; i < 3200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "119", "120", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3200; i < 3400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "120", "121", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3400; i < 3600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "121", "122", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3600; i < 3800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "122", "123", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3800; i < 4000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "123", "124", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4000; i < 4200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "124", "125", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4200; i < 4400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "125", "126", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4400; i < 4600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "126", "127", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4600; i < 4800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "127", "101", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4800; i < 5000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "101", "102", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 5000; i < 5200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "102", "103", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 5200; i < 5400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "103", "104", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            
            currentDate = "07-04-2015";
            j++;
            for (int i = 0; i < 200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "105", "106", "15", "25", "25", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 200; i < 400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "106", "107", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 400; i < 600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "107", "108", "25", "40", "40", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 600; i < 800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "108", "109", "32", "50", "50", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 800; i < 1000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "109", "110", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1000; i < 1200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "110", "111", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1200; i < 1400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "111", "112", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1400; i < 1600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "112", "113", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1600; i < 1800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "113", "114", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1800; i < 2000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "114", "115", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2000; i < 2200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "115", "116", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2200; i < 2400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "116", "117", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2400; i < 2600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "117", "118", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2600; i < 2800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "118", "119", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2800; i < 3000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "119", "120", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3000; i < 3200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "120", "121", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3200; i < 3400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "121", "122", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3400; i < 3600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "122", "123", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3600; i < 3800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "123", "124", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3800; i < 4000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "124", "125", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4000; i < 4200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "125", "126", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4200; i < 4400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "126", "127", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4400; i < 4600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "127", "101", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4600; i < 4800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "101", "102", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4800; i < 5000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "102", "103", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 5000; i < 5200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "103", "104", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 5200; i < 5400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "104", "105", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            
            currentDate = "07-05-2015";
            j++;
            for (int i = 0; i < 200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "106", "107", "15", "25", "25", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 200; i < 400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "107", "108", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 400; i < 600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "108", "109", "25", "40", "40", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 600; i < 800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "109", "110", "32", "50", "50", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 800; i < 1000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "110", "111", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1000; i < 1200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "111", "112", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1200; i < 1400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "112", "113", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1400; i < 1600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "113", "114", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1600; i < 1800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "114", "115", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 1800; i < 2000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "115", "116", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2000; i < 2200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "116", "117", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2200; i < 2400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "117", "118", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2400; i < 2600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "118", "119", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2600; i < 2800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "119", "120", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 2800; i < 3000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "120", "121", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3000; i < 3200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "121", "122", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3200; i < 3400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "122", "123", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3400; i < 3600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "123", "124", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3600; i < 3800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "124", "125", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 3800; i < 4000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "125", "126", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4000; i < 4200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "126", "127", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4200; i < 4400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "127", "101", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4400; i < 4600; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "101", "102", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4600; i < 4800; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "102", "103", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 4800; i < 5000; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "103", "104", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 5000; i < 5200; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "104", "105", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
            for (int i = 5200; i < 5400; i++)
            {
                gatorTable.addRow("", "" + i, "", "", "", "", "", "", currentDate, "105", "106", "18", "30", "30", currentDate, "EG3", currentDate, "no comments", "");
                System.out.println("" + j + " " + i);
            }
        }
        catch (IOException e)
        {
            
        }
    }
}