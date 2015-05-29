package test;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import com.healthmarketscience.jackcess.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Collections;
import javax.swing.event.*;
import java.util.ArrayList;

public class CageApplication extends JFrame
{
    private static CageApplication frame;
    private Container contentPane;
    private JButton[] cages;
    private File file;
    private Table table;
    //private ArrayList<Row> mostRecentCages;
    private Row mostRecentCage;
    
    public CageApplication()
    {
        super("Application");
        contentPane = getContentPane();
        
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
            if (1 <= i && i <= 8)
            {
                cageNumber = "" + i;
            }
            else if (9 <= i && i <= 12)
            {
                cageNumber = "9-" + (i-8);
            }
            else if (13 <= i && i <= 16)
            {
                cageNumber = "10-" + (i-12);
            }
            else if (i == 17)
            {
                cageNumber = "11";
            }
            else if (18 <= i && i <= 21)
            {
                cageNumber = "12-" + (i-17);
            }
            else if (22 <= i && i <= 25)
            {
                cageNumber = "13-" + (i-21);
            }
            else if (i == 26)
            {
                cageNumber = "14";
            }
            else if (27 <= i && i <= 30)
            {
                cageNumber = "15-" + (i-26);
            }
            else if (31 <= i && i <= 32)
            {
                cageNumber = "" + (i-15);
            }
            else if (33 <= i && i <= 36)
            {
                cageNumber = "18-" + (i-32);
            }
            else if (37 <= i && i <= 78)
            {
                cageNumber = "" + (i-18);
            }
            else if (79 <= i && i <= 82)
            {
                cageNumber = "61-" + (i-78);
            }
            else if (83 <= i && i <= 120)
            {
                cageNumber = "" + (i-21);
            }
            else if (121 <= i && i <= 136)
            {
                cageNumber = "" + (i-120) + "A";
            }
            else
            {
                cageNumber = "" + (i-136) + "B";
            }
            button = new JButton(cageNumber);
            ArrayList<Row> allEntriesFromCage = new ArrayList<Row>();
            
            button.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    allEntriesFromCage.clear();
                    IndexCursor cursor;
                    try
                    {
                        cursor = CursorBuilder.createCursor(table.getIndex("PenNumberIndex"));                            
                        cursor.beforeFirst();
                        while (cursor.findNextRow(Collections.singletonMap("Pen Number", ((JButton) e.getSource()).getText())))
                        {
                            Row row = cursor.getCurrentRow();
                            if (row != null)
                            {
                                allEntriesFromCage.add(row);
                            }
                        }
                    }
                    catch (IOException e1)
                    {
                        
                    }
                    for (int i = 0; i < allEntriesFromCage.size(); i++)
                    {
                        System.out.println(allEntriesFromCage.get(i).get("Pen Number") + " " + allEntriesFromCage.get(i).get("Gator Count") + " " + allEntriesFromCage.get(i).get("Current Date"));
                    }
                    System.out.println();
                }
            });
            cages[i-1] = button;
        }
    }
    
    public void addComponents()
    {
        contentPane.removeAll();
        Panel panel = new Panel(new FlowLayout());
        
        for (int i = 0; i < cages.length; i++)
        {
            panel.add(cages[i]);
        }
        
        contentPane.add(panel);
        validate();
        setVisible(true);
    }
    
    public static void createAndShowGUI()
    {
        frame = new CageApplication();           
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        double length = rect.getHeight();
        double width = rect.getWidth();
        Dimension screenSize = new Dimension((int)width, (int)length - 50);
        frame.getContentPane().setPreferredSize(screenSize);
        frame.addComponents();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);   
    }

    public static void main(String[] args)
    {
        createAndShowGUI();
    }
    
}
