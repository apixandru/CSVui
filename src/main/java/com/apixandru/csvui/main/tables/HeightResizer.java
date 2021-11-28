package com.apixandru.csvui.main.tables;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HeightResizer extends MouseAdapter {

    private static final int minHeight = 16;

    private final JTable gutterTable;
    private final JTable contentTable;

    private int rowToResize = -1;
    private int originalY = -1;

    public HeightResizer(JTable gutterTable, JTable contentTable) {
        this.gutterTable = gutterTable;
        this.contentTable = contentTable;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (this.shouldResizeRow(e)) {
            gutterTable.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
        } else {
            gutterTable.setCursor(Cursor.getDefaultCursor());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (this.shouldResizeRow(e)) {
            SwingUtilities.invokeLater(() -> {
                gutterTable.setRowSelectionAllowed(false);
                contentTable.setRowSelectionAllowed(false);
            });
            rowToResize = getRowToResize(e);
            originalY = e.getY();
            System.out.println("Resizing row " + getRowToResize(e) + " from " + originalY);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (rowToResize == -1) {
            return;
        }
        Rectangle cellRect = gutterTable.getCellRect(rowToResize, 0, true);
        int diff = e.getY() - originalY;
        double newHeight = Math.max(cellRect.getHeight() + diff, minHeight);
        originalY += diff;
        SwingUtilities.invokeLater(() -> {
            contentTable.setRowHeight(rowToResize, (int) newHeight);
            contentTable.addNotify();
        });
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        SwingUtilities.invokeLater(() -> {
            gutterTable.setRowSelectionAllowed(true);
            contentTable.setRowSelectionAllowed(true);
        });
        rowToResize = -1;
        originalY = -1;
    }

    private boolean shouldResizeRow(MouseEvent e) {
        return this.getRowToResize(e) != -1;
    }

    private int getRowToResize(MouseEvent e) {
        int rowAtPoint = gutterTable.rowAtPoint(e.getPoint());
        if (rowAtPoint == -1) {
            return -1;
        }
        Rectangle cellRect = gutterTable.getCellRect(rowAtPoint, 0, true);
        int yInCellRect = e.getY() - cellRect.y;
        if (yInCellRect < 4) {
            if (rowAtPoint == 0) {
                return -1;
            }
            return rowAtPoint - 1;
        } else if (yInCellRect > cellRect.height - 4) {
            return rowAtPoint;
        }
        return -1;
    }

}
