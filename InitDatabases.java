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
        File cageFile;
        Database cagedb;
        Table cageTable = null;
        
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
            
            cageFile = new File("CageDatabase.accdb");
            cagedb = new DatabaseBuilder(cageFile).setFileFormat(Database.FileFormat.V2000).create();
            cageTable = new TableBuilder("Database")
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
                .toTable(cagedb);
        }
        catch (IOException e)
        {
            
        }
        
        initCageDatabase(cageTable);
    }
    
    public static void initCageDatabase(Table cageTable)
    {
        try
        {
            for (int i = 101; i <= 127; i++)
            {
                cageTable.addRow(0, i, "Large", 600, "06-01-2015", "88", "R", "500", "39+", "");
            }
            for (int i = 201; i <= 226; i++)
            {
                cageTable.addRow(0, i, "Large", 600, "06-01-2015", "88", "R", "500", "15-18", "");
            }
            for (int i = 1; i <= 4; i++)
            {
                cageTable.addRow(0, "227." + i, "Quartered", 150, "06-01-2015", "88", "H", "50", "Hatchling", "");
            }
            for (int i = 228; i <= 231; i++)
            {
                cageTable.addRow(0, i, "Large", 600, "06-01-2015", "88", "R", "500", "19-23", "");
            }
            for (int i = 1; i <= 4; i++)
            {
                cageTable.addRow(0, "232." + i, "Quartered", 150, "06-01-2015", "88", "H", "50", "Hatchling", "");
            }
            for (int i = 301; i <= 326; i++)
            {
                cageTable.addRow(0, i, "Large", 600, "06-01-2015", "88", "R", "500", "24-28", "");
            }
            for (int i = 401; i <= 409; i++)
            {
                cageTable.addRow(0, i, "Large", 600, "06-01-2015", "88", "R", "500", "29-33", "");
            }
            for (int i = 1; i <= 4; i++)
            {
                cageTable.addRow(0, "410." + i, "Quartered", 150, "06-01-2015", "88", "H", "50", "Hatchling", "");
            }
            for (int i = 1; i <= 4; i++)
            {
                cageTable.addRow(0, "411." + i, "Quartered", 150, "06-01-2015", "88", "H", "50", "Hatchling", "");
            }
            for (int i = 412; i <= 419; i++)
            {
                cageTable.addRow(0, i, "Large", 600, "06-01-2015", "88", "R", "500", "34-36", "");
            }
            for (int i = 1; i <= 4; i++)
            {
                cageTable.addRow(0, "420." + i, "Quartered", 150, "06-01-2015", "88", "H", "50", "Hatchling", "");
            }
            for (int i = 1; i <= 4; i++)
            {
                cageTable.addRow(0, "421." + i, "Quartered", 150, "06-01-2015", "88", "H", "50", "Hatchling", "");
            }
            for (int i = 422; i <= 437; i++)
            {
                cageTable.addRow(0, i, "Large", 600, "06-01-2015", "88", "R", "500", "37-38", "");
            }
            for (int i = 801; i <= 816; i++)
            {
                cageTable.addRow(0, i, "Small", 200, "06-01-2015", "88", "H", "50", "Family", "");
            }
            for (int i = 901; i <= 910; i++)
            {
                cageTable.addRow(0, i, "Small", 200, "06-01-2015", "88", "H", "50", "Empty", "");
            }
        }
        catch (IOException e)
        {
            
        }
    }
}
