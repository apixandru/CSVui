package com.apixandru.csvui.main.tables;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
  
class Testing
{
  public void buildGUI()
  {
    JTable table1 = new JTable(1,3);
    table1.getTableHeader().setReorderingAllowed(false);
    table1.setPreferredScrollableViewportSize(table1.getPreferredSize());
    table1.setAutoCreateRowSorter(true);
    table1.setValueAt("should",0,0);
    table1.setValueAt("stay",0,1);
    table1.setValueAt("put",0,2);
    JTable table2 = new JTable(10,3);
    table2.setAutoCreateRowSorter(true);
      for (int x = 0; x < 30; x++) {
          table2.setValueAt((int) (Math.random() * 1000), x / 3, x % 3);
      }
    JTableHeader th = table1.getTableHeader();
    table2.setTableHeader(th);
    JFrame f = new JFrame();
    f.getContentPane().add(new JScrollPane(table1),BorderLayout.NORTH);
    f.getContentPane().add(table2);
    f.pack();
    f.setLocationRelativeTo(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);
  }
  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        new Testing().buildGUI();
      }
    });
  }
}