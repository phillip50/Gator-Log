package test;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import com.healthmarketscience.jackcess.*;

public class ModifyQuarters extends JFrame
{
    private ModifyQuarters frame;
    private JComboBox penComboBox;
    private String[] penList;
    private final java.util.List<String> previousQuarteredList;
    private final java.util.List<String> quarteredList;
    private JButton add;
    private JButton confirm;
    private JButton cancel;
    private final Font font;
    private File penFile;
    private Table penTable;
    
    public ModifyQuarters()
    {
        super("Modify Quartered Pens");
        
        font = new Font("Arial", Font.PLAIN, 40);
        
        add = new JButton("Add");
        add.setFont(font);
        confirm = new JButton("Confirm");
        confirm.setFont(font);
        cancel = new JButton("Cancel");
        cancel.setFont(font);
        
        penList = new String[149];
        int j = 0;
        for (int i = 101; i <= 127; i++)
        {
            penList[j] = "" + i;
            j++;
        }
        for (int i = 201; i <= 232; i++)
        {
            penList[j] = "" + i;
            j++;
        }
        for (int i = 301; i <= 326; i++)
        {
            penList[j] = "" + i;
            j++;
        }
        for (int i = 401; i <= 437; i++)
        {
            penList[j] = "" + i;
            j++;
        }
        for (int i = 801; i <= 816; i++)
        {
            penList[j] = "" + i;
            j++;
        }
        for (int i = 901; i <= 910; i++)
        {
            penList[j] = "" + i;
            j++;
        }
        
        BufferedReader reader;
        String temp = "";
        try
        {
            reader = new BufferedReader(new FileReader("QuarteredPens.txt"));
            temp = reader.readLine();
            
            penFile = new File("CageDatabase.accdb");
            penTable = DatabaseBuilder.open(penFile).getTable("Database");
        }
        catch (IOException e)
        {
            
        }
        quarteredList = new ArrayList( Arrays.asList(temp.split(",")) );
        previousQuarteredList = new ArrayList( Arrays.asList(temp.split(",")) );
    }
    
    public void addComponents()
    {
        Container contentPane = frame.getContentPane();
        contentPane.removeAll();
        
        Panel panel = new Panel();
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        
        String[] minusQuartered = new String[penList.length - quarteredList.size()];
        int j = 0;
        for (int i = 0; i < 149; i++)
        {
            if (quarteredList.indexOf(penList[i]) == -1)
            {
                minusQuartered[j] = penList[i];
                j++;
            }
        }
        
        penComboBox = new JComboBox(minusQuartered);
        penComboBox.setFont(font);
        JLabel topLabel = new JLabel("Designate New Quartered Pen: ");
        topLabel.setFont(font);
        
        Panel topPanel = new Panel();
        topPanel.setLayout(new FlowLayout());
        topPanel.add(topLabel);
        topPanel.add(penComboBox);
        topPanel.add(add);
        
        Panel topMiddlePanel = new Panel();
        topMiddlePanel.setLayout(new FlowLayout());
        JLabel topMiddleLabel = new JLabel("Remove Quartered Pens");
        topMiddleLabel.setFont(font);
        topMiddlePanel.add(topMiddleLabel);
        
        Panel bottomMiddlePanel = new Panel();
        bottomMiddlePanel.setLayout(new FlowLayout());
        for (String temp : quarteredList)
        {
            JButton button = new JButton(temp);
            button.setFont(font);
            button.addActionListener(e -> {
                String pen = ((JButton) e.getSource()).getText();
                quarteredList.remove(quarteredList.indexOf(pen));
                addComponents();
            });
            
            bottomMiddlePanel.add(button);
        }
        
        Panel bottomPanel = new Panel();
        bottomPanel.add(cancel);
        bottomPanel.add(confirm);
        
        c.gridx = 0;
        c.gridy = 0;
        panel.add(topPanel, c);
        
        c.gridy = 1;
        panel.add(topMiddlePanel, c);
        
        c.gridy = 2;
        panel.add(bottomMiddlePanel, c);
        
        c.gridy = 3;
        panel.add(bottomPanel, c);
        
        add(panel);
        validate();
    }
    
    public void setFrame(ModifyQuarters f)
    {
        frame = f;
    }
    
    public void initialize()
    {
        confirm.addActionListener(e -> {
            BufferedWriter writer;
            try
            {
                writer = new BufferedWriter(new FileWriter("QuarteredPens.txt", false));
                
                Iterator<String> it = quarteredList.iterator();
                if (it.hasNext())
                {
                    writer.write(it.next());
                }
                while (it.hasNext())
                {
                    writer.write("," + it.next());
                }
                
                writer.close();
                
                for (String temp : quarteredList)
                {
                    if (previousQuarteredList.indexOf(temp) == -1)
                    {
                        System.out.println("New: " + temp);
                        com.healthmarketscience.jackcess.Cursor cursor = CursorBuilder.createCursor(penTable);
                        cursor.afterLast();
                        boolean isDone = false;
                        
                        while (!isDone)
                        {
                            Row row = cursor.getPreviousRow();
                            if (row != null && row.get("Pen Number").toString().equals(temp))
                            {
                                penTable.addRow(0, temp + ".1", "Quartered", 150, row.get("Water Change Date"), row.get("Water Temperature"), row.get("Feed Type"), row.get("Feed Amount"), row.get("Size Class"), "Made quartered pen");
                                penTable.addRow(0, temp + ".2", "Quartered", 150, row.get("Water Change Date"), row.get("Water Temperature"), row.get("Feed Type"), row.get("Feed Amount"), row.get("Size Class"), "Made quartered pen");
                                penTable.addRow(0, temp + ".3", "Quartered", 150, row.get("Water Change Date"), row.get("Water Temperature"), row.get("Feed Type"), row.get("Feed Amount"), row.get("Size Class"), "Made quartered pen");
                                penTable.addRow(0, temp + ".4", "Quartered", 150, row.get("Water Change Date"), row.get("Water Temperature"), row.get("Feed Type"), row.get("Feed Amount"), row.get("Size Class"), "Made quartered pen");
                                isDone = true;
                            }
                        }
                    }
                }
            
                for (String temp : previousQuarteredList)
                {
                    if (quarteredList.indexOf(temp) == -1)
                    {
                        com.healthmarketscience.jackcess.Cursor cursor = CursorBuilder.createCursor(penTable);
                        cursor.afterLast();
                        boolean isDone = false;
                        
                        while (!isDone)
                        {
                            Row row = cursor.getPreviousRow();
                            if (row != null)
                            {
                                String penNumber = row.get("Pen Number").toString();
                                if (penNumber.equals(temp + ".1") || penNumber.equals(temp + ".2") || penNumber.equals(temp + ".3") || penNumber.equals(temp + ".4"))
                                {
                                    isDone = true;
                                    penTable.addRow(0, temp, "Large", 600, row.get("Water Change Date"), row.get("Water Temperature"), row.get("Feed Type"), row.get("Feed Amount"), row.get("Size Class"), "Made normal pen");
                                }
                            }
                        }
                        
                        System.out.println("Old: " + temp);
                        //add normal row to database
                    }
                }
            }
            catch (IOException e1)
            {
                
            }
            
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        
        cancel.addActionListener(e -> {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        
        add.addActionListener(e -> {
            quarteredList.add(penComboBox.getSelectedItem().toString());
            addComponents();
        });
    }
}
