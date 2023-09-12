package com.tool.ui.table.freeze;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;

public class FreezeTableColumnWidthListener implements TableColumnModelListener {
    private FreezeDefaultTable table;

    public FreezeTableColumnWidthListener(FreezeDefaultTable table) {
        this.table = table;
    }

    public void columnMarginChanged(ChangeEvent e) {
        if (!this.table.hasColumnWidthChanged() && this.table.getTableHeader().getResizingColumn() != null) {
            this.table.setColumnWidthChanged(true);
        }

    }

    public void columnMoved(TableColumnModelEvent e) {
    }

    public void columnAdded(TableColumnModelEvent e) {
    }

    public void columnRemoved(TableColumnModelEvent e) {
    }

    public void columnSelectionChanged(ListSelectionEvent e) {
    }
}
