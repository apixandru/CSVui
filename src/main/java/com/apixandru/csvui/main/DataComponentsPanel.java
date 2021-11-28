package com.apixandru.csvui.main;

import com.apixandru.csvui.main.tables.TableUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

class DataComponentsPanel extends JPanel {

    private JTable table1;

    DataComponentsPanel() {
//        setBackground(new Color(0xeeeeee));

//        setBackground(new Color(0xeeeeee));
        initComponents();
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

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void initComponents() {
        table1 = new JTable();
        setLayout(new BorderLayout());

        {

            //---- table1 ----
            table1.setModel(new DefaultTableModel(
                    new Object[][]{
                            {"item 1", "item 1b", "January", "July", 123, null},
                            {"item 2", "item 2b", "February", "August", 456, true},
                            {"item 3", null, "March", null, null, null},
                            {"item 4", null, "April", null, null, null},
                            {"item 5", null, "May", null, null, null},
                            {"item 6", null, "June", null, null, null},
                            {"item 7", null, "July", null, null, null},
                            {"item 8", null, "August", null, null, null},
                            {"item 9", null, "September", null, null, null},
                            {"item 10", null, "October", null, null, null},
                            {"item 11", null, "November", null, null, null},
                            {"item 12", null, "December", null, null, null},
                    },
                    new String[]{
                            "A", "B", "C", "D", "E", "F"
                    }
            ) {
                final Class<?>[] columnTypes = new Class<?>[]{
                        Object.class, Object.class, String.class, String.class, Integer.class, Boolean.class
                };
                final boolean[] columnEditable = new boolean[]{
                        false, true, true, true, true, true
                };

                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return columnTypes[columnIndex];
                }

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            {
                TableColumnModel cm = table1.getColumnModel();
                cm.getColumn(2).setCellEditor(new DefaultCellEditor(
                        new JComboBox(new DefaultComboBoxModel(new String[]{
                                "January",
                                "February",
                                "March",
                                "April",
                                "May",
                                "June",
                                "July",
                                "August",
                                "September",
                                "October",
                                "November",
                                "December"
                        }))));
                cm.getColumn(3).setCellEditor(new DefaultCellEditor(
                        new JComboBox(new DefaultComboBoxModel(new String[]{
                                "January",
                                "February",
                                "March",
                                "April",
                                "May",
                                "June",
                                "July",
                                "August",
                                "September",
                                "October",
                                "November",
                                "December"
                        }))));
            }
//            table1.setAutoCreateRowSorter(true);
//            scrollPane5.setViewportView(table1);
        }
        JScrollPane comp = TableUtils.scrollableTableView(table1);
        add(comp, BorderLayout.CENTER);
//        add(scrollPane5, BorderLayout.CENTER);

        table1.setShowHorizontalLines(true);
        table1.setShowVerticalLines(true);
        table1.setIntercellSpacing(new Dimension(1, 1));
        table1.setRowSelectionAllowed(true);
        table1.setColumnSelectionAllowed(true);
//            redGridColorChanged();

        DefaultCellEditor cellEditor = (DefaultCellEditor) table1.getColumnModel()
                .getColumn(3)
                .getCellEditor();
        JComboBox component = (JComboBox) cellEditor.getComponent();
//        component.setEditable(true);
        component.setEditable(false);
    }

}
