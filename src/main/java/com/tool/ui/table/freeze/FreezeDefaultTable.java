package com.tool.ui.table.freeze;

import javax.swing.JTable;

public class FreezeDefaultTable extends JTable {
    private boolean isColumnWidthChanged;

    public FreezeDefaultTable() {
    }

    public boolean hasColumnWidthChanged() {
        return this.isColumnWidthChanged;
    }

    public void setColumnWidthChanged(boolean widthChanged) {
        this.isColumnWidthChanged = widthChanged;
    }
}
