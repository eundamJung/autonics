package com.tool.ui.table.freeze;

import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FreezeTableChangeListener implements ChangeListener {
    JScrollPane scrollPane;

    public FreezeTableChangeListener(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public void stateChanged(ChangeEvent e) {
        JViewport viewport = (JViewport)e.getSource();
        this.scrollPane.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
    }
}
