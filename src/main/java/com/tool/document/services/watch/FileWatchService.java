//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.document.services.watch;

import com.tool.common.resource.AppConfig;
import com.tool.db.sqlite.config.table.Metadata;
import com.tool.document.DocumentManager;
import java.awt.Color;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Date;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileWatchService {
    private Logger logger = LoggerFactory.getLogger(FileWatchService.class);
    private String SERVICE_NAME = AppConfig.getString("document.filewatch");
    public UploadService uploadService;
    DocumentManager documentManager;
    Path workspacePath;
    FileAlterationMonitor monitor;

    public FileWatchService(DocumentManager documentManager, String workspaceDir) throws Exception {
        this.documentManager = documentManager;
        this.workspacePath = Paths.get(workspaceDir);
        this.init();
        this.start();
    }

    void init() throws Exception {
        String initMessage = "";
        if (!Files.exists(this.workspacePath, new LinkOption[0])) {
            initMessage = "message.workspace.notexist";
        } else if (!Files.isDirectory(this.workspacePath, new LinkOption[0])) {
            initMessage = "message.workspace.notdirectory";
        } else if (!Files.isReadable(this.workspacePath)) {
            initMessage = "message.workspace.unableread";
        } else if (!Files.isWritable(this.workspacePath)) {
            initMessage = "message.workspace.unablewrite";
        }

        if (StringUtils.isNotEmpty(initMessage)) {
            initMessage = AppConfig.getString(initMessage);
            this.logger.error(this.workspacePath + " " + initMessage);
            this.documentManager.workField.setBackground(new Color(14120823));
            this.documentManager.workField.setForeground(new Color(0, 0, 0));
            throw new Exception(initMessage);
        } else {
            this.documentManager.workField.setBackground(new Color(38, 117, 191));
            this.documentManager.workField.setForeground(new Color(255, 255, 255));
            this.uploadService = new UploadService(this.documentManager);
            this.uploadService.start();
        }
    }

    public FileWatchService start() {
        this.logger.info(AppConfig.getString("log.start"), this.SERVICE_NAME);

        try {
            FileAlterationListener listener = new FileAlterationListenerAdaptor() {
                public void onFileChange(File file) {
                    String fileName = file.getName();

                    Metadata metadata;
                    try {
                        metadata = Metadata.select(fileName);
                        if (metadata == null) {
                            FileWatchService.this.logger.error(AppConfig.getString("log.metadata.notexist"), fileName);
                            return;
                        }

                        Date sysdate = new Date();
                        metadata.setLastAccessTime(sysdate.getTime());
                        Metadata.updateLastAccessTime(fileName, sysdate);
                    } catch (SQLException var6) {
                        FileWatchService.this.logger.error(var6.getMessage());
                        return;
                    }

                    try {
                        FileWatchService.this.uploadService.addWorker(new UploadWorker(FileWatchService.this.workspacePath, metadata));
                    } catch (Exception var5) {
                        var5.printStackTrace();
                    }

                }
            };
            FileAlterationObserver observer = new FileAlterationObserver(this.workspacePath.toString(), FileFilterUtils.fileFileFilter());
            observer.addListener(listener);
            this.monitor = new FileAlterationMonitor(FileWatchConfig.MONITOR_INTERVAL.getTime(), new FileAlterationObserver[]{observer});
            this.monitor.start();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return this;
    }

    public void shutdown() {
        try {
            if (this.monitor != null) {
                this.monitor.stop();
            }

            if (this.uploadService != null) {
                this.uploadService.stop();
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public Path getWorkspacePath() {
        return this.workspacePath;
    }
}
