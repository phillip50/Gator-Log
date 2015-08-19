package test;

import com.healthmarketscience.jackcess.*;
import java.io.*;

public class SyncMaster
{
    public static void main(String[] args)
    {        
        File gatorFile;
        Database gatordb;
        Table gatorTable = null;
        File masterFile;
        Database masterdb;
        Table masterTable = null;
        File startFile;
        BufferedReader reader;
        
        try
        {
            gatorFile = new File("AnimalDatabase.accdb");
            gatorTable = DatabaseBuilder.open(gatorFile).getTable("Database");
            masterFile = new File("MasterAnimalDatabase.accdb");
            masterTable = DatabaseBuilder.open(masterFile).getTable("Database");
            startFile = new File("start.txt");
            reader = new BufferedReader(new FileReader(startFile));
            
            int start = Integer.parseInt(reader.readLine());
            reader.close();
            
            Cursor gatorCursor = CursorBuilder.createCursor(gatorTable);
            
            gatorCursor.beforeFirst();
            
            gatorCursor.moveNextRows(start);
            
            while (gatorCursor.moveToNextRow())
            {
                Row row = gatorCursor.getCurrentRow();
                masterTable.addRow(0, row.get("Tag Number"), row.get("Egg Collection Date"), row.get("Egg Nest Location"), row.get("Egg Number"), row.get("Egg Length"), row.get("Egg Weight"), row.get("Hatch Year"), row.get("Gender"), row.get("Umbilical"), row.get("Date"), row.get("From"), row.get("To"), row.get("Belly Size"), row.get("Length"), row.get("Weight"), row.get("Special Formula"), row.get("Experiment Code"), row.get("Vaccinated"), row.get("Comments"), row.get("Harvested?"));
            }   
        }
        catch (IOException e)
        {
            
        }
    }
}
