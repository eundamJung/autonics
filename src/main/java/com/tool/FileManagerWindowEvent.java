package com.tool;

import java.awt.Window;
import java.awt.event.WindowEvent;

public class FileManagerWindowEvent extends WindowEvent
{
    private boolean exit;

    public FileManagerWindowEvent(Window source, int id, boolean exit)
    {
        super(source, id);
        this.exit = exit;
    }

    public boolean isExit()
    {
        return this.exit;
    }
}
