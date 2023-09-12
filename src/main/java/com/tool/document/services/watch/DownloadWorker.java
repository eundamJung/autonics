//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.document.services.watch;

import com.tool.FileManager;
import com.tool.common.resource.AppConfig;
import com.tool.db.sqlite.config.table.Metadata;
import com.tool.http.FileHttpUtil;
import com.tool.util.ExceptionUtil;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class DownloadWorker extends Worker<DownloadWorker> {
    boolean overwriteFile;
    boolean lock;
    Map rowMap;

    public DownloadWorker(Path workspacePath, Map rowMap) {
        super(workspacePath, FileWatchUtil.transMapToMetadata(rowMap));
        this.rowMap = rowMap;
    }

    public DownloadWorker(Path workspacePath, Map rowMap, boolean lock) {
        this(workspacePath, rowMap);
        this.lock = lock;
    }

    public DownloadWorker call() throws Exception {
        Path tempPath = null;

        try {
            tempPath = Files.createTempFile("FileManager", (String)null);
            ExceptionUtil.ifResponseErrorThrow(FileHttpUtil.preCheckout(FileManager.session, this.metadata, this.lock));
            ExceptionUtil.ifResponseErrorThrow(FileHttpUtil.checkout(FileManager.session, this.metadata, tempPath.toFile(), this.lock));
            if (this.overwriteFile) {
                Files.delete(this.getLocalFilePath());
                Thread.sleep(FileWatchConfig.MONITOR_INTERVAL.getTime() + 100L);
            }

            FileUtils.copyFile(tempPath.toFile(), this.getLocalFilePath().toFile());
            Metadata.insertIfNotExists(this.metadata);
            this.success = true;
            if (this.overwriteFile) {
                this.logger.info(AppConfig.getString("log.download.overwrite"), this.metadata.getLocalFileName());
            } else {
                this.logger.info(AppConfig.getString("log.download.complete"), this.metadata.getLocalFileName());
            }
        } catch (Exception var6) {
            this.message = var6.getMessage();
        } finally {
            if (tempPath != null && Files.exists(tempPath, new LinkOption[0])) {
                Files.delete(tempPath);
            }

        }

        return this;
    }

    public boolean isOverwriteFile() {
        return this.overwriteFile;
    }

    public void setOverwriteFile(boolean overwriteFile) {
        this.overwriteFile = overwriteFile;
    }

    public boolean isLock() {
        return this.lock;
    }

    public Map getRowMap() {
        return this.rowMap;
    }
}
