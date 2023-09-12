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
import java.nio.file.Path;
import java.util.HashMap;

public class UploadWorker extends Worker<UploadWorker> {
    private HashMap dataMap = new HashMap();

    public UploadWorker(Path workspacePath, Metadata metadata) {
        super(workspacePath, metadata);
        this.dataMap.put("Waiting Time", 0L);
        this.dataMap.put("Local File Name", metadata.getLocalFileName());
        this.dataMap.put("Upload Date", "");
        this.dataMap.put("Document Name", metadata.getName());
        this.dataMap.put("Rev", metadata.getRevision());
        this.dataMap.put("File", metadata.getPlmFileName());
        this.dataMap.put("State", "");
        this.dataMap.put("Message", "");
        this.dataMap.put("Worker", this);
    }

    public UploadWorker call() {
        try {
            this.setState(AppConfig.getString("uploading"));
            ExceptionUtil.ifResponseErrorThrow(FileHttpUtil.preCheckin(FileManager.session, this.metadata));
            ExceptionUtil.ifResponseErrorThrow(FileHttpUtil.checkin(FileManager.session, this.metadata, this.getLocalFilePath().toFile()));
            this.success = true;
            this.setState(AppConfig.getString("complete"));
        } catch (Exception var2) {
            this.setState(AppConfig.getString("fail"));
            this.setMessage(var2.getMessage());
        }

        return this;
    }

    public void setMessage(String message) {
        super.setMessage(message);
        this.dataMap.put("Message", message);
    }

    public long getWaitingTime() {
        return (Long)this.dataMap.get("Waiting Time");
    }

    public void setWaitingTime(long waitingTime) {
        this.dataMap.put("Waiting Time", waitingTime);
    }

    public String getState() {
        return (String)this.dataMap.get("State");
    }

    public void setState(String state) {
        this.dataMap.put("State", state);
    }

    public String getUploadDate() {
        return (String)this.dataMap.get("Upload Date");
    }

    public void setUploadDate(String uploadDate) {
        this.dataMap.put("Upload Date", uploadDate);
    }

    public HashMap getDataMap() {
        return this.dataMap;
    }
}
