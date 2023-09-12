package com.tool.ui.table.freeze;

import com.tool.ui.table.TableColumnAdjuster;
import com.tool.ui.table.selection.CheckboxTable;
import java.awt.event.MouseListener;
import javax.swing.JScrollPane;
import javax.swing.RowSorter;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class FreezeCheckboxTable extends CheckboxTable implements Freeze {
    JScrollPane scrollPane;
    TableColumnAdjuster adjuster;
    CheckboxTable freezeTable;
    TableColumnAdjuster freezeAdjuster;

    public FreezeCheckboxTable() {
    }

    public void setFixedColumns(int fixedColumns) {
        this.scrollPane = (JScrollPane)this.getParent().getParent();
        this.setAutoCreateColumnsFromModel(false);
        this.addPropertyChangeListener(new FreezeTablePropertyChangeListener(this, this.freezeTable));
        this.freezeTable = new CheckboxTable();
        this.freezeTable.setAutoCreateColumnsFromModel(false);
        this.freezeTable.setModel(this.getModel());
        this.freezeTable.setSelectionModel(this.getSelectionModel());
        this.freezeTable.setFocusable(true);
        this.freezeTable.setRowHeight(this.getRowHeight());
        this.freezeTable.setRowSelectionAllowed(this.getRowSelectionAllowed());
        this.freezeTable.setColumnSelectionAllowed(this.getColumnSelectionAllowed());
        this.freezeTable.setAutoResizeMode(0);
        this.freezeTable.setCheckAllMouseListener();
        this.freezeAdjuster = new TableColumnAdjuster(this.freezeTable);

        for(int i = 0; i < fixedColumns; ++i) {
            TableColumnModel colModel = this.getColumnModel();
            TableColumn column = colModel.getColumn(0);
            colModel.removeColumn(column);
            this.freezeTable.getColumnModel().addColumn(column);
        }

        this.freezeTable.getColumnModel().addColumnModelListener(new FreezeTableColumnWidthListener(this.freezeTable));
        this.freezeTable.getTableHeader().addMouseListener(new FreezeTableHeaderMouseListener(this, this.adjuster, this.freezeTable));
        this.setAutoCreateRowSorter(true);
        this.freezeTable.setRowSorter(this.getRowSorter());
        this.setUpdateSelectionOnSort(true);
        this.freezeTable.setUpdateSelectionOnSort(false);
        this.freezeTable.setPreferredScrollableViewportSize(this.freezeTable.getPreferredSize());
        this.scrollPane.setRowHeaderView(this.freezeTable);
        this.scrollPane.setCorner("UPPER_LEFT_CORNER", this.freezeTable.getTableHeader());
        this.scrollPane.getRowHeader().addChangeListener(new FreezeTableChangeListener(this.scrollPane));
    }

    public void setAdjuster(TableColumnAdjuster adjuster) {
        this.adjuster = adjuster;
    }

    public void setRowSorter(RowSorter<? extends TableModel> sorter) {
        super.setRowSorter(sorter);
        this.freezeTable.setRowSorter(sorter);
    }

    public void setFreezeScrollableViewportSize(boolean isFreezeTable) {
        if (!isFreezeTable) {
            this.freezeAdjuster.adjustColumns();
        }

        this.freezeTable.setPreferredScrollableViewportSize(this.freezeTable.getPreferredSize());
        SwingUtilities.invokeLater(() -> {
            this.freezeTable.updateUI();
            this.updateUI();
        });
    }

    public void addMouseRightButtonPopupMenu(MouseListener l) {
        this.addMouseListener(l);
        this.freezeTable.addMouseListener(l);
    }
}
