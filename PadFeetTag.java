package test;

import com.healthmarketscience.jackcess.*;
import java.io.*;
import org.apache.commons.lang.StringUtils;

public class PadFeetTag
{    
    public static void main(String[] args)
    {
        try
        {
            File gatorFile = new File("MasterAnimalDatabase.accdb");
            Table gatorTable = DatabaseBuilder.open(gatorFile).getTable("Database");
            
            Cursor gatorCursor = CursorBuilder.createCursor(gatorTable);
            
            gatorCursor.beforeFirst();
            
            while (gatorCursor.moveToNextRow())
            {
                Row row = gatorCursor.getCurrentRow();
                
                if (row.get("Foot Tag") != null)
                {
                    String tag = row.get("Foot Tag").toString();

                    int index = tag.indexOf("-");

                    String newTag;
                    if (index == -1)
                    {
                        newTag = StringUtils.leftPad(tag, 5, "0");
                    }
                    else
                    {
                        String s1 = tag.substring(0, index);
                        String s2 = tag.substring(index+1);

                        newTag = StringUtils.leftPad(s1, 5, "0") + "-" + StringUtils.leftPad(s2, 5, "0");
                    }

                    row.replace("Foot Tag", newTag);
                    
                    gatorTable.updateRow(row);
                    System.out.println(row.get("ID").toString());
                }
            }
        }
        catch (IOException e)
        {

        }
    }
}
