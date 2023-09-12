//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.ui.table.selection;

import com.tool.ui.table.TableRowColors;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Map;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;

public class CheckboxTableCellEditor extends AbstractCellEditor implements TableCellEditor {
    Color[] colors = TableRowColors.getBasicRowColors();

    public CheckboxTableCellEditor() {
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        boolean checked = Boolean.parseBoolean((String)value);
        JPanel panel = new JPanel();
        panel.setBackground(this.colors[row % 2]);
        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(!checked);
        this.updateCellValue(table, row, !checked);
        panel.add(checkBox);
        SwingUtilities.invokeLater(() -> {
            table.updateUI();
        });
        return panel;
    }

    public void cancelCellEditing() {
        super.cancelCellEditing();
    }

    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }

    public Object getCellEditorValue() {
        return null;
    }

    public boolean isCellEditable(EventObject e) {
        if (e instanceof MouseEvent) {
            MouseEvent event = (MouseEvent)e;
            if (event.getButton() == 1) {
                return true;
            }
        }

        return false;
    }

    void updateCellValue(JTable table, int row, boolean checked) {
        this.getRow(table, row).put("checked", String.valueOf(checked));
    }

    boolean isChecked(JTable table, int row) {
        return Boolean.parseBoolean((String)this.getRow(table, row).get("checked"));
    }

    Map getRow(JTable table, int row) {
        return ((CheckboxTableModel)table.getModel()).getRow(table.convertRowIndexToModel(row));
    }
}
