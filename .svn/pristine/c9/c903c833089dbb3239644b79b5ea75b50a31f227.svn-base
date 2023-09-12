//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.ui.table;

import com.tool.common.resource.AppConfig;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import org.apache.commons.lang3.StringUtils;

public class TableRowFilterSet {
    JTable table;
    TableRowFilter tableRowFilter;
    TableRowSorter tableRowSorter;
    TableColumnAdjuster tableColumnAdjuster;
    JLabel label;

    public TableRowFilterSet(JTable table, TableRowFilter tableRowFilter, TableRowSorter tableRowSorter, TableColumnAdjuster tableColumnAdjuster, JLabel label) {
        this.table = table;
        this.tableRowFilter = tableRowFilter;
        this.tableRowSorter = tableRowSorter;
        this.tableColumnAdjuster = tableColumnAdjuster;
        this.label = label;
        this.table.setRowSorter(tableRowSorter);
    }

    public void filter(TableColumnMapping columnMapping, String word) {
        int index = -1;
        Map<Integer, String> columnActualMap = ((TableColumnModel)this.table.getColumnModel()).getColumnActualMap();
        Iterator itr = columnActualMap.keySet().iterator();

        while(itr.hasNext()) {
            int i = (Integer)itr.next();
            String actual = (String)columnActualMap.get(i);
            if (columnMapping.getActual().equals(actual)) {
                index = i;
                break;
            }
        }

        if (StringUtils.isNotEmpty(word)) {
            this.tableRowFilter.setFilter(index, word);
            this.tableRowSorter.setRowFilter(RowFilter.andFilter(this.tableRowFilter.getRowFilters()));
        } else {
            this.tableRowSorter.setRowFilter((RowFilter)null);
        }

        if (this.label != null) {
            JLabel var10000 = this.label;
            int var10001 = this.table.getRowCount();
            var10000.setText(var10001 + " " + AppConfig.getString("found"));
        }

    }

    public TableColumnAdjuster getTableColumnAdjuster() {
        return this.tableColumnAdjuster;
    }
}
