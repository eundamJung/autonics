//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.ui.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class TableCellRenderers extends DefaultTableCellRenderer {
    Color[] rowColors = TableRowColors.getBasicRowColors();

    public TableCellRenderers() {
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        this.setText(value == null ? "" : value.toString());
        if (!isSelected) {
            this.setBackground(this.rowColors[row % this.rowColors.length]);
        }

        this.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 1, 1, Color.white), new EmptyBorder(0, 10, 0, 10)));
        return this;
    }
}
