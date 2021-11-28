package com.apixandru.csvui.main.tables;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static com.apixandru.csvui.main.tables.TableUtils.scrollableTableView;
import static com.apixandru.csvui.main.tables.TableUtils.setupRowResizer;

public class RowNumberTable extends JTable implements ChangeListener, PropertyChangeListener, TableModelListener {

    private JTable main;

    public RowNumberTable(JTable table) {
        main = table;
        setupRowResizer(this, main);
        main.addPropertyChangeListener(this);
        main.getModel().addTableModelListener(this);

        setFocusable(false);
        setAutoCreateColumnsFromModel(false);
        setSelectionModel(main.getSelectionModel());


        TableColumn column = new TableColumn();
        column.setHeaderValue("");
        addColumn(column);
        column.setCellRenderer(new RowNumberRenderer());

        setPreferredScrollableViewportSize(getPreferredSize());
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        JTable table = new JTable(100, 6);
        JScrollPane scrollPane = scrollableTableView(table);

        jFrame.setContentPane(scrollPane);
        jFrame.setPreferredSize(new Dimension(800, 600));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }


    @Override
    public void addNotify() {
        super.addNotify();

        Component c = getParent();

        //  Keep scrolling of the row table in sync with the main table.

        if (c instanceof JViewport) {
            JViewport viewport = (JViewport) c;
            viewport.addChangeListener(this);
        }
    }

    @Override
    public int getRowCount() {
        return main.getRowCount();
    }

    @Override
    public int getRowHeight(int row) {
        int rowHeight = main.getRowHeight(row);

        if (rowHeight != super.getRowHeight(row)) {
            super.setRowHeight(row, rowHeight);
        }

        return rowHeight;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return Integer.toString(row + 1);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JViewport viewport = (JViewport) e.getSource();
        JScrollPane scrollPane = (JScrollPane) viewport.getParent();
        scrollPane.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        if ("selectionModel".equals(e.getPropertyName())) {
            setSelectionModel(main.getSelectionModel());
        }

        if ("rowHeight".equals(e.getPropertyName())) {
            repaint();
        }

        if ("model".equals(e.getPropertyName())) {
            main.getModel().addTableModelListener(this);
            revalidate();
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        revalidate();
    }

}