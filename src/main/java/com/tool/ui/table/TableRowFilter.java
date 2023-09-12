//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.ui.table;

import java.util.ArrayList;
import java.util.List;
import javax.swing.RowFilter;
import javax.swing.RowFilter.Entry;

public class TableRowFilter extends RowFilter<TableModel, Integer> {
    int index;
    String word;

    public TableRowFilter() {
    }

    public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
        return entry.getStringValue(this.index).toLowerCase().contains(this.word.toLowerCase());
    }

    public void setFilter(int index, String word) {
        this.index = index;
        this.word = word;
    }

    public List<TableRowFilter> getRowFilters() {
        List<TableRowFilter> filters = new ArrayList();
        filters.add(this);
        return filters;
    }
}
