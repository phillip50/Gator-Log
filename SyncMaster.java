package test;

import com.healthmarketscience.jackcess.*;
import java.io.*;
import javax.swing.*;
import java.text.*;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.border.EmptyBorder;

public class SyncMaster
{
    public static void main(String[] args)
    {        
        File gatorFile;
        Database gatordb;
        Table gatorTable;
        File masterFile;
        Database masterdb;
        Table masterTable;
        File startFile;
        BufferedReader reader;
        
        Font font = new Font("Arial", Font.PLAIN, 25);
        
        try
        {
            gatorFile = new File("AnimalDatabase.accdb");
            gatordb = DatabaseBuilder.open(gatorFile);
            gatorTable = gatordb.getTable("Database");
            masterFile = new File("\\\\GATORSERVER\\Users\\Public\\Inventory Databases\\AnimalDatabase.accdb");
            masterdb = DatabaseBuilder.open(masterFile);
            masterTable = masterdb.getTable("Database");
            startFile = new File("RowsToSync.txt");
            reader = new BufferedReader(new FileReader(startFile));
            
            int start = Integer.parseInt(reader.readLine());
            reader.close();
            
            Cursor gatorCursor = CursorBuilder.createCursor(gatorTable);
            
            gatorCursor.afterLast();
            
            gatorCursor.movePreviousRows(start + 1);
            
            JFrame frame = new JFrame("Sync");
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setVisible(true);
            JPanel panel = new JPanel(new FlowLayout());
            panel.setBorder(new EmptyBorder(20, 40, 20, 40));
            JLabel label = new JLabel("0% complete");
            label.setFont(font);
            panel.add(label);
            frame.getContentPane().add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            
            DecimalFormat df = new DecimalFormat("0.0");
            
            int i = 0;
            while (gatorCursor.moveToNextRow())
            {
                Row row = gatorCursor.getCurrentRow();
                masterTable.addRow(0, row.get("Tag Number"), row.get("Egg Collection Date"), row.get("Egg Nest Location"), row.get("Foot Tag"), row.get("Hatchling Length"), row.get("Hatchling Weight"), row.get("Hatch Year"), row.get("Gender"), row.get("Umbilical"), row.get("Date"), row.get("From"), row.get("To"), row.get("Belly Size"), row.get("Length"), row.get("Weight"), row.get("Special Formula"), row.get("Experiment Code"), row.get("Vaccinated"), row.get("Comments"), row.get("Harvested?"));
                
                String percent = df.format(i * 100.0 / start);
                System.out.println("" + i + " " + start + " " + percent);
                
                panel.removeAll();
                label = new JLabel("" + percent + "% Complete");
                label.setFont(font);
                panel.add(label);
                
                frame.getContentPane().removeAll();
                frame.getContentPane().add(panel);
                frame.validate();
                i++;
            }
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(startFile, false));
            writer.write("0");
            writer.close();
            
            gatordb.close();
            masterdb.close();
            
            frame.dispose();
        }
        catch (IOException e)
        {
            
        }
    }
}
