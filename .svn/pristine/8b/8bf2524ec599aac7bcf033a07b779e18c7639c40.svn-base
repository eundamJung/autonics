//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.ui.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class TableColumnModel extends DefaultTableColumnModel {
    Map<Integer, String> columnActualMap;
    List<TableColumnMapping> columnList;
    public int order;
    public TableCellRenderer renderer;
    public TableCellEditor editor;

    public TableColumnModel() {
        this(new DefaultTableCellRenderer(), new DefaultCellEditor(new JTextField()));
    }

    public TableColumnModel(TableCellRenderer renderer) {
        this(renderer, new DefaultCellEditor(new JTextField()));
    }

    public TableColumnModel(TableCellEditor editor) {
        this(new DefaultTableCellRenderer(), editor);
    }

    public TableColumnModel(TableCellRenderer renderer, TableCellEditor editor) {
        this.columnActualMap = new HashMap();
        this.columnList = new ArrayList();
        this.renderer = renderer;
        this.editor = editor;
    }

    public TableColumn addColumn(TableColumnMapping columnMapping) {
        return this.addColumn(columnMapping, this.renderer, this.editor);
    }

    public TableColumn addColumn(TableColumnMapping columnMapping, TableCellRenderer renderer) {
        return this.addColumn(columnMapping, renderer, this.editor);
    }

    public TableColumn addColumn(TableColumnMapping columnMapping, TableCellEditor editor) {
        return this.addColumn(columnMapping, this.renderer, editor);
    }

    public TableColumn addColumn(TableColumnMapping columnMapping, TableCellRenderer renderer, TableCellEditor editor) {
        TableColumn tableColumn = new TableColumn();
        tableColumn.setModelIndex(this.order);
        tableColumn.setHeaderValue(columnMapping.getDisplay());
        tableColumn.setCellRenderer(renderer);
        tableColumn.setCellEditor(editor);
        this.addColumn(tableColumn);
        this.columnActualMap.put(this.order, columnMapping.getActual());
        this.columnList.add(columnMapping);
        ++this.order;
        return tableColumn;
    }

    public Map<Integer, String> getColumnActualMap() {
        return this.columnActualMap;
    }

    public List<TableColumnMapping> getColumnMapplingList() {
        return this.columnList;
    }
}
