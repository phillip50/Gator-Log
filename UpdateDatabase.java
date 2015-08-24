package test;

import java.io.*;
import java.nio.file.Files;
import javax.swing.*;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class UpdateDatabase
{
    public static void main(String[] args)
    {
        File gatorFile = new File("AnimalDatabase.accdb");
        File masterFile = new File("\\\\GATORSERVER\\Users\\Public\\Inventory Databases\\AnimalDatabase.accdb");
        File rowsToSync = new File("RowsToSync.txt");
        Font font = new Font("Arial", Font.PLAIN, 25);
        
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(rowsToSync));
            int rows = Integer.parseInt(reader.readLine());
            
            if (rows == 0)
            {
                gatorFile.delete();

                Files.copy( masterFile.toPath(), gatorFile.toPath() );

                JFrame frame = new JFrame("Update Database");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                c.insets = new Insets(30, 70, 30, 70);
                JLabel label = new JLabel("Complete!");
                label.setFont(font);
                JButton button = new JButton("Done");
                button.setFont(font);
                button.addActionListener(e -> {
                    frame.dispose();
                });
                panel.add(label, c);
                c.gridy = 1;
                panel.add(button, c);
                frame.getContentPane().add(panel);
                frame.pack();
                frame.setLocationRelativeTo(null);
            }
            else
            {
                JFrame frame = new JFrame("Update Database");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                c.insets = new Insets(20, 20, 20, 20);
                JLabel label1 = new JLabel("Cannot update database.");
                label1.setFont(font);
                JLabel label2 = new JLabel("The database must first be synced to the server.");
                label2.setFont(font);
                panel.add(label1, c);
                c.gridy = 1;
                panel.add(label2, c);
                frame.getContentPane().add(panel);
                frame.pack();
                frame.setLocationRelativeTo(null);
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }
}
