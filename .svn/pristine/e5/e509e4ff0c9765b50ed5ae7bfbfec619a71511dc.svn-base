//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.db.sqlite;

import com.tool.db.sqlite.config.table.Metadata;
import com.tool.document.services.watch.DownloadService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.SQLiteConfig;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

public class SqliteSession {
    private static Logger logger = LoggerFactory.getLogger(DownloadService.class);
    static SqliteSession instance;
    private static SQLiteConnectionPoolDataSource dataSource = new SQLiteConnectionPoolDataSource();

    private SqliteSession() {
        try {
            Path databaseDir = Paths.get(System.getProperty("user.dir") + File.separator + ".data");
            if (!Files.exists(databaseDir, new LinkOption[0])) {
                Files.createDirectory(databaseDir);
            }

            if (!Files.isHidden(databaseDir)) {
                Files.setAttribute(databaseDir, "dos:hidden", true);
            }

            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            config.enableLoadExtension(true);
            dataSource = new SQLiteConnectionPoolDataSource();
            dataSource.setUrl("jdbc:sqlite:" + databaseDir.resolve("database.db"));
            dataSource.setConfig(config);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    /**
     * <pre>
     *     Sqlite 초기화
     * </pre>
     * @throws SQLException
     */
    public static void init() throws SQLException {
        instance = new SqliteSession();
        createTableIfNotExists(Metadata.class);
    }

    /**
     * <pre>
     *      Sqlite 테이블이 없다면 생성.
     * </pre>
     * @param cazz
     * @throws SQLException
     */
    public static void createTableIfNotExists(Class cazz) throws SQLException {
        Statement stmt = null;
        Connection conn = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            SqliteHelper handler = new SqliteHelper();
            String expr = handler.createTableIfNotExists(cazz);
            stmt.execute(expr);
        } catch (Exception var8) {
            logger.error(var8.getMessage());
        } finally {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }

            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }

    public static SqliteSession getInstance() {
        return instance;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
