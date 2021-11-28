package com.apixandru.csvui.main;

import com.apixandru.csvui.main.tables.TableUtils;
import com.opencsv.CSVReader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class CsvReaderPanel extends JPanel {

    private final JTable table1;

    public CsvReaderPanel(File file) {
        setLayout(new BorderLayout());
        table1 = new JTable();
        table1.setModel(createCsvModel(file));

        JScrollPane comp = TableUtils.scrollableTableView(table1);
        add(comp, BorderLayout.CENTER);
//        add(scrollPane5, BorderLayout.CENTER);

        table1.setShowHorizontalLines(true);
        table1.setShowVerticalLines(true);
        table1.setIntercellSpacing(new Dimension(1, 1));
        table1.setRowSelectionAllowed(true);
        table1.setColumnSelectionAllowed(true);
    }

    private static Object[][] readLines(File file) {
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            List<String[]> aa = new ArrayList<>();
            for (String[] strings : reader) {
                aa.add(strings);
            }
            return aa.toArray(new Object[0][]);
        } catch (IOException | NoSuchElementException e) {
            throw new IllegalArgumentException("Bad file " + file);
        }
    }

    private static DefaultTableModel createCsvModel(File file) {
        Object[][] objects = readLines(file);
        List<String> cols = new ArrayList<>();
        for (Object[] object : objects) {
            for (Object o : object) {
                int intChar = 'A' + cols.size();
                cols.add(String.valueOf((char) intChar));
            }
            break;
        }

        String[] columnNames = cols.toArray(new String[0]);
        return new DefaultTableModel(
                objects,
                columnNames) {

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
    }

    private void dndChanged() {
        DropMode dropMode = DropMode.USE_SELECTION;
        table1.setDropMode(dropMode);

        String key = "FlatLaf.oldTransferHandler";
        table1.setTransferHandler((TransferHandler) table1.getClientProperty(key));
    }

    private void redGridColorChanged() {
        table1.setGridColor(true ? Color.red : UIManager.getColor("Table.gridColor"));
        table1.setSelectionBackground(Color.GREEN);
    }

    @Override
    public void updateUI() {
        super.updateUI();

        EventQueue.invokeLater(() -> {
            table1.setShowHorizontalLines(true);
            table1.setShowVerticalLines(true);
            table1.setIntercellSpacing(new Dimension(1, 1));
        });
    }

}
