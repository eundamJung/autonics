package com.tool.ui.table.freeze;

import com.tool.ui.table.TableColumnAdjuster;
import java.awt.event.MouseListener;
import javax.swing.JScrollPane;
import javax.swing.RowSorter;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class FreezeTable extends FreezeDefaultTable implements Freeze {
    TableColumnAdjuster adjuster;
    FreezeDefaultTable freezeTable;
    TableColumnAdjuster freezeAdjuster;

    public FreezeTable() {
    }

    public void setFixedColumns(int fixedColumns) {
        JScrollPane scrollPane = (JScrollPane)this.getParent().getParent();
        this.setAutoCreateColumnsFromModel(false);
        this.addPropertyChangeListener(new FreezeTablePropertyChangeListener(this, this.freezeTable));
        this.freezeTable = new FreezeDefaultTable();
        this.freezeTable.setAutoCreateColumnsFromModel(false);
        this.freezeTable.setModel(this.getModel());
        this.freezeTable.setSelectionModel(this.getSelectionModel());
        this.freezeTable.setFocusable(true);
        this.freezeTable.setRowHeight(this.getRowHeight());
        this.freezeTable.setRowSelectionAllowed(this.getRowSelectionAllowed());
        this.freezeTable.setColumnSelectionAllowed(this.getColumnSelectionAllowed());
        this.freezeTable.setAutoResizeMode(0);
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
        scrollPane.setRowHeaderView(this.freezeTable);
        scrollPane.setCorner("UPPER_LEFT_CORNER", this.freezeTable.getTableHeader());
        scrollPane.getRowHeader().addChangeListener(new FreezeTableChangeListener(scrollPane));
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
