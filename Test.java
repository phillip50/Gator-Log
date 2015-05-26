//deprecated, use Application.java

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
        String fromYear = "";
        String fromRange = "";
        
        String[] toCages = new String[10];
        String[] toRanges = new String[10];
        int[] rangeStart = new int[10];
        int[] rangeEnd = new int[10];
        int[] capacities = new int[10];
        int[] capacityCounter = new int[10];
        String[] cagesAtCapacity = new String[10];
        int[] cagesAtCapacityAmount = new int[10];
        String[] cagesAtCapacityRange = new String[10];
        int cagesAtCapacityCounter = 0;
        
        int toCounter = 0;
        
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        
        ArrayList<Table> tables = new ArrayList<Table>();
        ArrayList<String> totalTables = new ArrayList<String>();
        
        try
        {
            initCages();
            
            File file;
            Database db = null;
            Table table = null;
            Table unspecified = null;
            Table otherTable = null;
            
            while (!isDone)
            {
                String s = StartWindow.createAndShowGUI(hasDatabase, hasFrom);
            
                if (s.equals("add"))
                {
                    capacityCounter = addEntry(tables, toCages, toRanges, capacities, capacityCounter, unspecified, otherTable, from, currentDate);
                    for (int i = 0; i < toCages.length; i++)
                    {
                        if (capacities[i] != 0 && capacities[i] == capacityCounter[i])
                        {    
                            cagesAtCapacity[cagesAtCapacityCounter] = toCages[i];
                            cagesAtCapacityAmount[cagesAtCapacityCounter] = capacities[i];
                            cagesAtCapacityRange[cagesAtCapacityCounter] = toRanges[i];
                            cagesAtCapacityCounter++;
                            toCages[i] = null;
                            toRanges[i] = null;
                            rangeStart[i] = 0;
                            rangeEnd[i] = 0;
                            capacities[i] = 0;
                            capacityCounter[i] = 0;
                            tables.remove(i);

                            toCages = stringShift(toCages);
                            toRanges = stringShift(toRanges);
                            rangeStart = intShift(rangeStart);
                            rangeEnd = intShift(rangeEnd);
                            capacities = intShift(capacities);
                            capacityCounter = intShift(capacityCounter);
                            
                            toCounter--;
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
                        otherTable = new TableBuilder("Database")
                            .addColumn(new ColumnBuilder("ID", DataType.LONG).setAutoNumber(true))
                            .addColumn(new ColumnBuilder("From", DataType.TEXT))
                            .addColumn(new ColumnBuilder("To", DataType.TEXT))
                            .addColumn(new ColumnBuilder("Belly", DataType.TEXT))
                            .addColumn(new ColumnBuilder("Date", DataType.TEXT))
                            .toTable(db);
                        hasFrom = true;
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
                        boolean isCapacityValid = isInteger(capacity) && (Integer.parseInt(capacity) > 0);
                        
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
                        capacities[i] = 0;
                        capacityCounter[i] = 0;
                        tables.remove(i);

                        toCages = stringShift(toCages);
                        toRanges = stringShift(toRanges);
                        rangeStart = intShift(rangeStart);
                        rangeEnd = intShift(rangeEnd);
                        capacities = intShift(capacities);
                        capacityCounter = intShift(capacityCounter);
                        
                        toCounter--;
                    }
                }
                
                if (toCounter == 0)
                {
                    hasDatabase = false;
                }
                else
                {
                    hasDatabase = true;
                }
            }
            
            File log = new File("Cage" + from + "_Birth" + fromYear + "_" + currentDate + "_log.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(log));
            writer.write("From Pen: " + from + "\r\n\tTotal: " + fromCount + "\r\n\tYear: " + fromYear + "\r\n\tSize: " + fromRange + "\r\n");
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

    public static int[] addEntry(ArrayList<Table> tables, String[] toCages, String[] toRanges, int[] capacities, int[] capacityCounter, Table unspecified, Table otherTable, String from, String currentDate)
    {   
        String to = "";
        String belly;
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
                    otherTable.addRow(0, from, to, belly, currentDate);
                    fromCount++;
                }
                catch (IOException e)
                {
            
                }
                for (int i = 0; i < toCages.length; i++)
                {
                    if (capacities[i] != 0 && capacityCounter[i] == capacities[i])
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
    
    public static String[] stringShift(String[] input)
    {
        int j = 0;
        int k = 0;
        String[] temp = new String[10];
                        
        while (j < 10)
        {
            if (input[j] != null)
            {
                temp[k] = input[j];
                k++;
                }
            j++;
        }
        return temp;
    }
    
    public static int[] intShift(int[] input)
    {
        int j = 0;
        int k = 0;
        int[] temp = new int[10];
                        
        while (j < 10)
        {
            if (input[j] != 0)
            {
                temp[k] = input[j];
                k++;
            }
            j++;
        }
        return temp;
    }
}
