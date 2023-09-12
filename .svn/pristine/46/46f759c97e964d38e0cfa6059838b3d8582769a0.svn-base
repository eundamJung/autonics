//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.ui.table.selection;

import com.tool.ui.table.TableRowColors;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.commons.lang3.BooleanUtils;

public class CheckboxTableCellRenderers extends DefaultTableCellRenderer {
    Color[] rowColors = TableRowColors.getBasicRowColors();

    public CheckboxTableCellRenderers() {
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JPanel panel = new JPanel();
        panel.setBackground(this.rowColors[row % 2]);
        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(BooleanUtils.toBoolean((String)value));
        panel.add(checkBox);
        panel.setBorder(new MatteBorder(0, 0, 1, 1, Color.white));
        return panel;
    }
}
