//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.ui.table;

import com.tool.common.resource.AppConfig;
import org.apache.commons.lang3.StringUtils;

public class TableColumnMapping {
    private String actual;
    private String display;

    public TableColumnMapping(String actual) {
        this(actual, (String)null);
    }

    public TableColumnMapping(String actual, String display) {
        if (display == null) {
            display = AppConfig.getString("table.column." + actual.replaceAll(" ", "_"));
            if (StringUtils.isEmpty(display)) {
                display = actual;
            }
        }

        this.actual = actual;
        this.display = display;
    }

    public String getActual() {
        return this.actual;
    }

    public String getDisplay() {
        return this.display;
    }
}
