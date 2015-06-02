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
                .addColumn(new ColumnBuilder("From", DataType.TEXT))
                .addIndex(new IndexBuilder("FromIndex").addColumns("From"))
                .addColumn(new ColumnBuilder("To", DataType.TEXT))
                .addIndex(new IndexBuilder("ToIndex").addColumns("To"))
                .addColumn(new ColumnBuilder("Belly", DataType.TEXT))
                .addColumn(new ColumnBuilder("Date", DataType.TEXT))
                .toTable(gatordb);
            
            cageFile = new File("CageDatabase.accdb");
            cagedb = new DatabaseBuilder(cageFile).setFileFormat(Database.FileFormat.V2000).create();
            cageTable = new TableBuilder("Database")
                    .addColumn(new ColumnBuilder("ID", DataType.LONG).setAutoNumber(true))
                    .addIndex(new IndexBuilder(IndexBuilder.PRIMARY_KEY_NAME).addColumns("ID").setPrimaryKey())
                    .addColumn(new ColumnBuilder("Pen Number", DataType.TEXT))
                    .addIndex(new IndexBuilder("PenNumberIndex").addColumns("Pen Number"))
                    .addColumn(new ColumnBuilder("Gator Count", DataType.TEXT))
                    .addIndex(new IndexBuilder("CountIndex").addColumns("Gator Count"))
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
                cageTable.addRow(0, i, 600);
            }
            for (int i = 201; i <= 226; i++)
            {
                cageTable.addRow(0, i, 600);
            }
            for (int i = 1; i <= 4; i++)
            {
                cageTable.addRow(0, "227." + i, 150);
            }
            for (int i = 228; i <= 231; i++)
            {
                cageTable.addRow(0, i, 600);
            }
            for (int i = 1; i <= 4; i++)
            {
                cageTable.addRow(0, "232." + i, 150);
            }
            for (int i = 301; i <= 326; i++)
            {
                cageTable.addRow(0, i, 600);
            }
            for (int i = 401; i <= 409; i++)
            {
                cageTable.addRow(0, i, 600);
            }
            for (int i = 1; i <= 4; i++)
            {
                cageTable.addRow(0, "410." + i, 150);
            }
            for (int i = 1; i <= 4; i++)
            {
                cageTable.addRow(0, "411." + i, 150);
            }
            for (int i = 412; i <= 419; i++)
            {
                cageTable.addRow(0, i, 600);
            }
            for (int i = 1; i <= 4; i++)
            {
                cageTable.addRow(0, "420." + i, 150);
            }
            for (int i = 1; i <= 4; i++)
            {
                cageTable.addRow(0, "421." + i, 150);
            }
            for (int i = 422; i <= 437; i++)
            {
                cageTable.addRow(0, i, 600);
            }
            for (int i = 801; i <= 816; i++)
            {
                cageTable.addRow(0, i, 200);
            }
            for (int i = 901; i <= 910; i++)
            {
                cageTable.addRow(0, i, 200);
            }
        }
        catch (IOException e)
        {
            
        }
    }
}
