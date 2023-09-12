//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.db.sqlite;

import com.tool.db.sqlite.config.anno.SqliteColumn;
import com.tool.db.sqlite.config.anno.SqliteTable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SqliteHelper {
    public SqliteHelper() {
    }

    String getTable(Class targetClass) {
        SqliteTable annotation = (SqliteTable)targetClass.getAnnotation(SqliteTable.class);
        if (annotation != null) {
            String name = annotation.name();
            if (!"##default".equals(name)) {
                return name;
            }
        }

        return targetClass.getName();
    }

    List<String> getColumns(Class targetClass) {
        List<String> columns = new ArrayList();
        Field[] fields = targetClass.getDeclaredFields();
        Field[] var4 = fields;
        int var5 = fields.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Field f = var4[var6];
            SqliteColumn annotation = (SqliteColumn)f.getAnnotation(SqliteColumn.class);
            if (annotation != null) {
                List<String> column = new ArrayList();
                String name = annotation.name();
                if ("##default".equals(name)) {
                    name = f.getName();
                }

                column.add(name);
                column.add(annotation.dataType());
                column.add(annotation.primaryKey() ? "PRIMARY KEY" : "");
                column.add(annotation.notNull() ? "NOT NULL" : "");
                columns.add(String.join(" ", column));
            }
        }

        return columns;
    }

    public String createTableIfNotExists(Class targetClass) {
        String table = this.getTable(targetClass);
        List<String> columns = this.getColumns(targetClass);
        return "CREATE TABLE IF NOT EXISTS " + table + "(" + String.join(",", columns) + ")";
    }
}
