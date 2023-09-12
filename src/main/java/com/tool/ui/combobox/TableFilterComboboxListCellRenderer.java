package com.tool.ui.combobox;

import com.tool.ui.table.TableColumnMapping;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class TableFilterComboboxListCellRenderer extends DefaultListCellRenderer {
    public TableFilterComboboxListCellRenderer() {
    }

    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        this.setText(((TableColumnMapping)value).getDisplay());
        return this;
    }
}
