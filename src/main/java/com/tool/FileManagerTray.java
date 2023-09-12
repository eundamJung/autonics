package com.tool;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class FileManagerTray extends PopupMenu
{
    HashMap<String, MenuItem> trayMenuMap = new HashMap();
    FileManager               fileManger;
    SystemTray                tray;

    public FileManagerTray(FileManager fileManger, Image image, String tooltip)
    {
        this.fileManger = fileManger;
        this.tray = SystemTray.getSystemTray();

        try
        {
            TrayIcon trayIcon = new TrayIcon(image, tooltip, this);
            trayIcon.setImageAutoSize(true);
            this.tray.add(trayIcon);
        }
        catch (AWTException var5)
        {
            var5.printStackTrace();
        }

    }

    void addMenuItem(String title, ActionListener listener)
    {
        MenuItem item = new MenuItem(title);
        item.addActionListener(listener);
        this.add(item);
        this.trayMenuMap.put(title, item);
    }
}
