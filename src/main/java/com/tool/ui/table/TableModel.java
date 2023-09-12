//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.ui.table;

import com.tool.util.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
    Map<Integer, String> columnMap;
    List<Map<String, Object>> list = Collections.synchronizedList(new ArrayList());

    public TableModel() {
    }

    public void add(Map data) {
        this.list.add(data);
    }

    public void add(int i, Map data) {
        this.list.add(i, data);
    }

    public void remove(Map data) {
        this.list.remove(data);
    }

    public void removeAll(List<Map> dataList) {
        this.list.removeAll(dataList);
    }

    public void clearAll() {
        this.list = new ArrayList();
    }

    public Map getRow(int row) {
        return (Map)this.list.get(row);
    }

    public List<Map<String, Object>> getRows() {
        return this.list;
    }

    public void setColumnMap(Map<Integer, String> columnMap) {
        this.columnMap = columnMap;
    }

    public int getRowCount() {
        return this.list.size();
    }

    public int getColumnCount() {
        return this.list.size();
    }

    public Object getValueAt(int row, int col) {
        Map data = this.getRow(row);
        String columnName = (String)this.columnMap.get(col);
        return StringUtil.NVL(data.get(columnName));
    }
}
