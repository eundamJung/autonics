//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.ui.table.selection;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class CheckboxTableRowSorter extends TableRowSorter {
    public CheckboxTableRowSorter() {
    }

    public CheckboxTableRowSorter(TableModel model) {
        super(model);
    }

    public boolean isSortable(int column) {
        return column != 0;
    }
}
