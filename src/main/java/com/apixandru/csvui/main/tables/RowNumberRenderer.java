package com.apixandru.csvui.main.tables;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

class RowNumberRenderer extends DefaultTableCellRenderer {

    public RowNumberRenderer() {
        setHorizontalAlignment(JLabel.CENTER);
    }

    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (table != null) {
            JTableHeader header = table.getTableHeader();

            if (header != null) {
                setForeground(header.getForeground());
                setBackground(header.getBackground());
                setFont(header.getFont());
            }
        }

        if (isSelected) {
            setFont(getFont().deriveFont(Font.BOLD));
        }

        setText((value == null) ? "" : value.toString());
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        return this;
    }

}