/*  
    @Phillip Dingler
*/
package test;

import com.healthmarketscience.jackcess.DatabaseBuilder;
import java.io.*;
import com.healthmarketscience.jackcess.*;

public class Test
{
    private static String[] cages;
    private static int fromCount;
    
    public static void main(String[] args)
    {
        fromCount = 0;
        boolean isDone = false;
        boolean hasDatabase = false;
        boolean hasFrom = false;
        String from = "";
        String to = "";
        String modify = "";
        String[] toCages = new String[10];
        String[] toRanges = new String[10];
        int[] rangeStart = new int[10];
        int[] rangeEnd = new int[10];
        int[] capacities = new int[10];
        for (int i = 0; i < toCages.length; i++)
        {
            capacities[i] = -1;
        }
        int[] capacityCounter = new int[10];
        int toCounter = 0;
        try
        {
            initCages();
            
            File file;
            Database db;
            Table table = null;
            
            while (!isDone)
            {
                String s = StartWindow.createAndShowGUI(hasDatabase, hasFrom);
            
                if (s.equals("add"))
                {
                    capacityCounter = addEntry(table, from, toCages, toRanges, capacities, capacityCounter);
                    //remove cage
                }
                else if (s.equals("quit"))
                {
                    isDone = true;
                }
                else if (s.equals("new"))
                {
                    s = InputPanel.createAndShowGUI("Name?");
                    if (!s.equals(""))
                    {
                        file = new File(s + ".accdb");
                        db = new DatabaseBuilder(file).setFileFormat(Database.FileFormat.V2000).create();
                        table = new TableBuilder("Test")
                            .addColumn(new ColumnBuilder("ID", DataType.LONG).setAutoNumber(true))
                            .addColumn(new ColumnBuilder("From", DataType.TEXT))
                            .addColumn(new ColumnBuilder("To", DataType.TEXT))
                            .addColumn(new ColumnBuilder("Belly", DataType.TEXT))
                            .toTable(db);
                        hasDatabase = true;
                    }
                }
                else if (s.equals("from"))
                {
                    String temp = InputFrom.createAndShowGUI();
                    if (!temp.equals(""))
                    {
                        for (int i = 1; i < cages.length; i++)
                        {
                            if (cages[i].equals(temp))
                            {
                                from = temp;
                                hasFrom = true;
                            }
                        }
                    }

                }
                else if (s.equals("to"))
                {
                    to = GetTo.createAndShowGUI();
                    if (to.equals(":-"))
                    {                       
                    }
                    else
                    {
                        int index = to.indexOf(':');
                        int index2 = to.indexOf(':', index+1);
                        String range = to.substring(index+1, index2);
                        int index3 = range.indexOf('-');
                        boolean isRangeValid = true;
                        int start = 0;
                        int end = 0;
                        if (isInteger(range.substring(0,index3)))
                        {
                            start = Integer.parseInt(range.substring(0,index3));
                        }
                        else
                        {
                            isRangeValid = false;
                        }
                        
                        if (isInteger(range.substring(index3+1)))
                        {
                            end = Integer.parseInt(range.substring(index3+1));
                        }
                        else
                        {
                            isRangeValid = false;
                        }                        
                        
                        if (isRangeValid)
                        {
                            for (int i = 0; i < toRanges.length; i++)
                            {
                                for (int j = start; j <= end; j++)
                                {
                                    if (j == rangeStart[i] || j == rangeEnd[i])
                                    {
                                        isRangeValid = false;
                                    }
                                }
                            }
                        }
                        
                        String cage = to.substring(0,index);
                        boolean isCageValid = false;
                        for (int i = 1; i < cages.length; i++)
                        {
                            if (cages[i].equals(cage))
                            {
                                isCageValid = true;
                            }
                        }
                        
                        String capacity = to.substring(index2+1);
                        boolean isCapacityValid = isInteger(capacity);
                        
                        if (isRangeValid && isCageValid && isCapacityValid)
                        {
                            if (index == 1)
                            {
                                toCages[toCounter] = "" + to.charAt(0);
                            }
                            else
                            {
                                toCages[toCounter] = to.substring(0, index);
                            }
                            toRanges[toCounter] = to.substring(index+1, index2);
                            index = toRanges[toCounter].indexOf('-');
                            rangeStart[toCounter] = Integer.parseInt(toRanges[toCounter].substring(0,index));
                            rangeEnd[toCounter] = Integer.parseInt(toRanges[toCounter].substring(index+1));
                            capacities[toCounter] = Integer.parseInt(to.substring(index2+1));
                            toCounter++;
                        }
                        else
                        {
                            //error message
                        }
                    }
                }
                else if (s.equals("modify"))
                {
                    modify = ModifyTo.createAndShowGUI(toCages, toRanges, capacities);
                    if (!modify.equals("-1"))
                    {    
                        int i = Integer.parseInt(modify);
                        toCages[i] = null;
                        toRanges[i] = null;
                        rangeStart[i] = 0;
                        rangeEnd[i] = 0;
                        capacities[i] = -1;
                        capacityCounter[i] = 0;

                        String[] tempCages = new String[10];
                        String[] tempRanges = new String[10];
                        int[] tempStart = new int[10];
                        int[] tempEnd = new int[10];
                        int[] tempCapacity = new int[10];
                        int[] tempCounter = new int[10];
                        
                        int j = 0;
                        int k = 0;
                        
                        while (j < toCages.length)
                        {
                            if (toCages[j] != null)
                            {
                                tempCages[k] = toCages[j];
                                tempRanges[k] = toRanges[j];
                                tempStart[k] = rangeStart[j];
                                tempEnd[k] = rangeEnd[j];
                                tempCapacity[k] = capacities[j];
                                tempCounter[k] = capacityCounter[j];
                                k++;
                            }
                            j++;
                        }
                        
                        toCages = tempCages;
                        toRanges = tempRanges;
                        rangeStart = tempStart;
                        rangeEnd = tempEnd;
                        capacities = tempCapacity;
                        capacityCounter = tempCounter;
                        
                        for (int z = 0; z < toCages.length; z++)
                        {
                            if (capacities[z] == 0)
                            {
                                capacities[z] = -1;
                            }
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            
        }
    }

    public static int[] addEntry(Table table, String from, String[] toCages, String[] toRanges, int[] capacities, int[] capacityCounter)
    {   
        String to = "";
        String belly;
        boolean redo = false;
        int[] rangeStart = new int[10];
        int[] rangeEnd = new int[10];
        for(int i = 0; i < toCages.length; i++)
        {
            if (toRanges[i] != null)
            {
                int index = toRanges[i].indexOf('-');
                rangeStart[i] = Integer.parseInt(toRanges[i].substring(0,index));
                rangeEnd[i] = Integer.parseInt(toRanges[i].substring(index+1));
            }
        }
     
        do
        {
            belly = BellySize.createAndShowGUI();
            if (belly.equals("done"))
            {
                
            }
            else
            {
                to = "";
                for (int i = 0; i < toCages.length; i++)
                {
                    if (rangeStart[i] <= Integer.parseInt(belly) && Integer.parseInt(belly) <= rangeEnd[i])
                    {   
                        to = toCages[i];
                        capacityCounter[i]++;
                    }
                }
                try
                {
                    table.addRow(0, from, to, belly);
                    fromCount++;
                }
                catch (IOException e)
                {
            
                }
                for (int i = 0; i < toCages.length; i++)
                {
                    if (capacityCounter[i] == capacities[i])
                    {
                        belly = "done";
                        ErrorWindow.createAndShowGUI("Reached capacity on Cage " + toCages[i]);
                    }
                }
            }
        } while (!belly.equals("done"));
        return capacityCounter;
    }
    
    public static boolean isInteger(String str)
    {
	if (str == null)
        {
		return false;
	}
	int length = str.length();
	if (length == 0)
        {
		return false;
	}
	int i = 0;
	if (str.charAt(0) == '-')
        {
		if (length == 1)
                {
			return false;
		}
		i = 1;
	}
	for (; i < length; i++)
        {
		char c = str.charAt(i);
		if (c <= '/' || c >= ':')
                {
			return false;
		}
	}
	return true;
    }
    
    private static void initCages()
    {
        cages = new String[149];
        for (int i = 1; i < 100; i++)
        {
            cages[i] = "" + i;
        }
        for (int i = 1; i < 17; i++)
        {
            cages[99 + i] = "" + i + "A";
        }
        for (int i = 1; i < 34; i++)
        {
            cages[99 + 16 + i] = "" + i + "B";
        }
            
    }
    
    public static boolean checkCage(String s)
    {
        boolean isGood = false;
        for (int i = 1; i < cages.length; i++)
        {
            if (s.equals(cages[i]))
            {
                isGood = true;
            }
        }
        return isGood;
    }
}
