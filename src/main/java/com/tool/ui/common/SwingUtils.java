//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.ui.common;

import com.tool.ui.table.TableColumnMapping;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class SwingUtils {
    public SwingUtils() {
    }

    public static void turnOnProgress(JProgressBar progressBar) {
        setProgress(progressBar, true);
    }

    public static void turnOffProgress(JProgressBar progressBar) {
        setProgress(progressBar, false);
    }

    private static void setProgress(JProgressBar progressBar, boolean value) {
        if (progressBar != null) {
            SwingUtilities.invokeLater(() -> {
                progressBar.setVisible(value);
                progressBar.setIndeterminate(value);
            });
        }
    }

    public static TableColumnMapping[] getTableFilterComboboxList(List<TableColumnMapping> list, String... filterList) {
        List<TableColumnMapping> comboboxFilterList = new ArrayList();
        Iterator var3 = list.iterator();

        while(true) {
            while(var3.hasNext()) {
                TableColumnMapping columnMapping = (TableColumnMapping)var3.next();
                String[] var5 = filterList;
                int var6 = filterList.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    String filter = var5[var7];
                    if (filter.equals(columnMapping.getActual())) {
                        comboboxFilterList.add(columnMapping);
                        break;
                    }
                }
            }

            return (TableColumnMapping[])comboboxFilterList.toArray(new TableColumnMapping[0]);
        }
    }

    public static ArrayList<Component> getChildAllComponents(Container container, ArrayList list) {
        Component[] var2 = container.getComponents();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Component component = var2[var4];
            list.add(component);
            if (component instanceof Container) {
                getChildAllComponents((Container)component, list);
            }
        }

        return list;
    }
}
