package com.tool.ui.table.selection;

import com.tool.ui.table.freeze.FreezeDefaultTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;

public class CheckboxTable extends FreezeDefaultTable {
    public AtomicBoolean checkedAll = new AtomicBoolean();

    public CheckboxTable() {
    }

    public void setCheckAllMouseListener() {
        final JTableHeader tableHeader = this.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        tableHeader.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                e.consume();
                if (e.getButton() == 1) {
                    int columnIndex = tableHeader.columnAtPoint(e.getPoint());
                    if (columnIndex == 0) {
                        CheckboxTable.this.checkedAll.set(!CheckboxTable.this.checkedAll.get());
                        int rowCount = CheckboxTable.this.getRowCount();

                        for(int idx = 0; idx < rowCount; ++idx) {
                            int modelIdx = CheckboxTable.this.convertRowIndexToModel(idx);
                            ((CheckboxTableModel)CheckboxTable.this.getModel()).getRow(modelIdx).put("checked", String.valueOf(CheckboxTable.this.checkedAll.get()));
                        }

                        SwingUtilities.invokeLater(() -> {
                            CheckboxTable.this.updateUI();
                        });
                    }
                }
            }
        });
    }

    public List<Map> getCheckedList() {
        List<Map> checkedList = new ArrayList();
        CheckboxTableModel model = (CheckboxTableModel)this.getModel();
        int rowCount = model.getRowCount();

        for(int idx = 0; idx < rowCount; ++idx) {
            Map data = model.getRow(idx);
            if (Boolean.parseBoolean((String)data.get("checked"))) {
                checkedList.add(data);
            }
        }

        return checkedList;
    }
}
