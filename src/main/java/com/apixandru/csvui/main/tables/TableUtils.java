package com.apixandru.csvui.main.tables;

import javax.swing.*;

public class TableUtils {

    public static void addDummyData(JTable table) {
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                table.getColumnModel().getColumn(j).setMinWidth(200);
                table.setValueAt(i * table.getRowCount() + j, i, j);
            }
        }
    }

    public static void setupRowResizer(JTable headerTable, JTable headerTable2) {
        HeightResizer heightResizer = new HeightResizer(headerTable, headerTable2);
        headerTable.addMouseListener(heightResizer);
        headerTable.addMouseMotionListener(heightResizer);
    }

    public static JScrollPane scrollableTableView(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(table);
        JTable rowTable = new RowNumberTable(table);
        scrollPane.setRowHeaderView(rowTable);
        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowTable.getTableHeader());
        return scrollPane;
    }


}
