package com.tool.ui.combobox;

import com.tool.ui.table.TableColumnMapping;
import com.tool.ui.table.TableRowFilterSet;
import com.tool.ui.textfield.TableFilterTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;

public class TableFilterCombobox extends JComboBox<TableColumnMapping> {
    public TableFilterCombobox() {
    }

    public void initializeEvent(final TableRowFilterSet tableRowFilterSet, final TableFilterTextField textField) {
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tableRowFilterSet.filter((TableColumnMapping)TableFilterCombobox.this.getSelectedItem(), textField.getText());
            }
        });
    }
}
