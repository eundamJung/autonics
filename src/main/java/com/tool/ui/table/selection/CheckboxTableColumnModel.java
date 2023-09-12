//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.ui.table.selection;

import com.tool.ui.table.TableColumnMapping;
import com.tool.ui.table.TableColumnModel;
import java.awt.Color;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class CheckboxTableColumnModel extends TableColumnModel {
    public CheckboxTableColumnModel() {
        this(new DefaultTableCellRenderer(), new DefaultCellEditor(new JTextField()));
    }

    public CheckboxTableColumnModel(TableCellRenderer renderer) {
        this(renderer, new DefaultCellEditor(new JTextField()));
    }

    public CheckboxTableColumnModel(TableCellEditor editor) {
        this(new DefaultTableCellRenderer(), editor);
    }

    public CheckboxTableColumnModel(TableCellRenderer renderer, TableCellEditor editor) {
        this.renderer = renderer;
        this.editor = editor;
        this.addColumn(new TableColumnMapping("checked"), new CheckboxTableCellRenderers(), new CheckboxTableCellEditor());
    }

    public TableColumn addColumn(TableColumnMapping columnMapping, TableCellRenderer renderer, TableCellEditor editor) {
        TableColumn column = super.addColumn(columnMapping, renderer, editor);
        column.setPreferredWidth(80);
        if (this.order == 1) {
            this.getColumn(0).setHeaderRenderer((table, value, isSelected, hasFocus, row, column1) -> {
                JPanel panel = new JPanel();
                JCheckBox checkBox = new JCheckBox();
                checkBox.setSelected(((CheckboxTable)table).checkedAll.get());
                panel.setBackground(Color.white);
                panel.add(checkBox);
                panel.setBorder(new MatteBorder(0, 0, 1, 1, new Color(230, 230, 230)));
                return panel;
            });
        }

        return column;
    }
}
