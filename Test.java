package test;

import com.healthmarketscience.jackcess.DatabaseBuilder;
import java.io.*;
import com.healthmarketscience.jackcess.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
        String[] cagesAtCapacity = new String[10];
        int[] cagesAtCapacityAmount = new int[10];
        String[] cagesAtCapacityRange = new String[10];
        int cagesAtCapacityCounter = 0;
        for (int i = 0; i < toCages.length; i++)
        {
            capacities[i] = -1;
        }
        int[] capacityCounter = new int[10];
        int toCounter = 0;
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        String fromYear = "";
        String fromRange = "";
        ArrayList<Table> tables = new ArrayList<Table>();
        ArrayList<String> totalTables = new ArrayList<String>();
        try
        {
            initCages();
            
            File file;
            Database db = null;
            Table table = null;
            Table unspecified = null;
            
            while (!isDone)
            {
                String s = StartWindow.createAndShowGUI(hasDatabase, hasFrom);
            
                if (s.equals("add"))
                {
                    capacityCounter = addEntry(tables, toCages, toRanges, capacities, capacityCounter, unspecified);
                    for (int i = 0; i < toCages.length; i++)
                    {
                        if (capacities[i] == capacityCounter[i])
                        {    
                            cagesAtCapacity[cagesAtCapacityCounter] = toCages[i];
                            cagesAtCapacityAmount[cagesAtCapacityCounter] = capacities[i];
                            cagesAtCapacityRange[cagesAtCapacityCounter] = toRanges[i];
                            cagesAtCapacityCounter++;
                            toCages[i] = null;
                            toRanges[i] = null;
                            rangeStart[i] = 0;
                            rangeEnd[i] = 0;
                            capacities[i] = -1;
                            capacityCounter[i] = 0;
                            tables.remove(i);

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
                else if (s.equals("quit"))
                {
                    isDone = true;
                }
                else if (s.equals("new"))
                {
                    String temp = DatabasePanel.createAndShowGUI();
                    int index = temp.indexOf('_');
                    int index2 = temp.indexOf('_', index+1);
                    
                    if (index == 0 || index == temp.length() - 1)
                    {
                        
                    }
                    else
                    {
                        from = temp.substring(0, index);
                        fromYear = temp.substring(index+1, index2);
                        fromRange = temp.substring(index2+1);
                        String name = "Cage" + from + "_Birth" + fromYear + "_" + currentDate;
                        file = new File(name + ".accdb");
                        db = new DatabaseBuilder(file).setFileFormat(Database.FileFormat.V2000).create();
                        unspecified = new TableBuilder("Unspecified")
                            .addColumn(new ColumnBuilder("ID", DataType.LONG).setAutoNumber(true))
                            .addColumn(new ColumnBuilder("Belly", DataType.TEXT))
                            .toTable(db);
                        hasFrom = true;
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
                    if (to.equals(":-:"))
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
                                if (rangeStart[i] == start && rangeEnd[i] == end)
                                {
                                    isRangeValid = true;
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
                        
                        boolean tableExists = false;
                        for (int i = 0; i < toCages.length; i++)
                        {
                            if (cage.equals(toCages[i]))
                            {
                                tableExists = true;
                            }
                        }
                        
                        if (isRangeValid && isCageValid && isCapacityValid && !tableExists)
                        {
                            String temp;
                            if (index == 1)
                            {
                                temp = "" + to.charAt(0);
                            }
                            else
                            {
                                temp = to.substring(0, index);
                            }
                            toCages[toCounter] = temp;
                            toRanges[toCounter] = to.substring(index+1, index2);
                            index = toRanges[toCounter].indexOf('-');
                            rangeStart[toCounter] = Integer.parseInt(toRanges[toCounter].substring(0,index));
                            rangeEnd[toCounter] = Integer.parseInt(toRanges[toCounter].substring(index+1));
                            capacities[toCounter] = Integer.parseInt(to.substring(index2+1));
                            toCounter++;
                            
                                table = new TableBuilder("To pen: " + temp)
                                    .addColumn(new ColumnBuilder("ID", DataType.LONG).setAutoNumber(true))
                                    .addColumn(new ColumnBuilder("Belly", DataType.TEXT))
                                    .toTable(db);
                                totalTables.add(temp);
                            
                            tables.add(table);
                            hasDatabase = true;
                        }
                        else
                        {
                            ErrorWindow.createAndShowGUI("Illegal input");
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
                        tables.remove(i);

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
                        toCounter--;
                        
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
            
            File log = new File("Cage" + from + "_Birth" + fromYear + "_" + currentDate + "_log.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(log));
            writer.write("From Cage:\r\n\tCage Number: " + from + "\r\n\tTotal: " + fromCount + "\r\n\tYear: " + fromYear + "\r\n\tSize: " + fromRange + "\r\n");
            for (int i = 0; i < toCages.length; i++)
            {
                if (cagesAtCapacity[i] != null)
                {
                    writer.write("\r\n\r\nTo Pen: " + cagesAtCapacity[i] + "\r\n\tTransferred: " + cagesAtCapacityAmount[i] + "\r\n\tCurrent Size: " + cagesAtCapacityRange[i]);
                }
            }
            for (int i = 0; i < toCages.length; i++)
            {
                if (toCages[i] != null)
                {
                    writer.write("\r\n\r\nTo Pen: " + toCages[i] + "\r\n\tTransferred: " + capacityCounter[i] + "\r\n\tCurrent Size: " + toRanges[i]);
                }
            }
            
            int totalCount = 0;
            for (int i = 0; i < toCages.length; i++)
            {
                if (toCages[i] != null)
                {
                    totalCount = totalCount + capacityCounter[i];
                }
            }
            for (int i = 0; i < toCages.length; i++)
            {
                if (cagesAtCapacity[i] != null)
                {
                    totalCount = totalCount + cagesAtCapacityAmount[i];
                }
            }
            if (fromCount - totalCount != 0)
            {
                writer.write("\r\n\r\nUnspecified: " + (fromCount - totalCount));
            }
            
            writer.close();
        }
        catch (IOException e)
        {
            
        }
        
        
    }

    public static int[] addEntry(ArrayList<Table> tables, String[] toCages, String[] toRanges, int[] capacities, int[] capacityCounter, Table unspecified)
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
                int index = 0;
                boolean inRange = false;
                for (int i = 0; i < toCages.length; i++)
                {
                    if (rangeStart[i] <= Integer.parseInt(belly) && Integer.parseInt(belly) <= rangeEnd[i])
                    {   
                        index = i;
                        to = toCages[i];
                        capacityCounter[i]++;
                        i = toCages.length;
                        inRange = true;
                    }
                }
                try
                {
                    if (inRange)
                    {
                        tables.get(index).addRow(0, belly);
                    }
                    else
                    {
                        unspecified.addRow(0, belly);
                    }
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
