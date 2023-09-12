package com.tool.ui.combobox;

import com.tool.ui.table.TableColumnMapping;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;

public class TableFilterComboboxModel extends DefaultComboBoxModel<TableColumnMapping> {
    public TableFilterComboboxModel() {
    }

    public TableFilterComboboxModel(TableColumnMapping[] items) {
        super(items);
    }

    public TableFilterComboboxModel(Vector<TableColumnMapping> v) {
        super(v);
    }
}
