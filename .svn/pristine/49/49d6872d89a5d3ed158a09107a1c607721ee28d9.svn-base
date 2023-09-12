//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.document.services.watch;

import com.tool.db.sqlite.config.table.Metadata;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Worker<T> implements Callable<T> {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private Path workspacePath;
    Metadata metadata;
    boolean success;
    String message;

    public Worker(Path workspacePath, Metadata metadata) {
        this.workspacePath = workspacePath;
        this.metadata = metadata;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Path getLocalFilePath() {
        return this.workspacePath.resolve(this.metadata.getLocalFileName());
    }
}
