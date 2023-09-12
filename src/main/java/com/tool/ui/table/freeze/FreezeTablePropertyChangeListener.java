package com.tool.ui.table.freeze;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FreezeTablePropertyChangeListener implements PropertyChangeListener {
    private FreezeDefaultTable table;
    private FreezeDefaultTable freeze;

    public FreezeTablePropertyChangeListener(FreezeDefaultTable table, FreezeDefaultTable freeze) {
        this.table = table;
        this.freeze = freeze;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if ("selectionModel".equals(propertyName)) {
            this.freeze.setSelectionModel(this.table.getSelectionModel());
        }

        if ("dataModel".equals(propertyName)) {
            this.freeze.setModel(this.table.getModel());
        }

    }
}
