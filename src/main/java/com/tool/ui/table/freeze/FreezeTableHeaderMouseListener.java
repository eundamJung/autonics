package com.tool.ui.table.freeze;

import com.tool.ui.table.TableColumnAdjuster;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class FreezeTableHeaderMouseListener extends MouseAdapter {
    private FreezeDefaultTable table;
    private TableColumnAdjuster adjuster;
    private FreezeDefaultTable freeze;

    public FreezeTableHeaderMouseListener(FreezeDefaultTable table, TableColumnAdjuster adjuster, FreezeDefaultTable freeze) {
        this.table = table;
        this.adjuster = adjuster;
        this.freeze = freeze;
    }

    public void mouseReleased(MouseEvent e) {
        if (this.freeze.hasColumnWidthChanged()) {
            if (this.adjuster != null) {
                this.adjuster.adjustColumns(true);
            }

            this.freeze.setPreferredScrollableViewportSize(this.freeze.getPreferredSize());
            SwingUtilities.invokeLater(() -> {
                this.table.updateUI();
                this.freeze.updateUI();
            });
            this.freeze.setColumnWidthChanged(false);
        }

    }
}
