//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.document.services.watch;

public enum FileWatchConfig {
    MONITOR_INTERVAL(FileWatchUtil.getBufferTime("workspace.monitor.interval")),
    WAIT_BUFFER(FileWatchUtil.getBufferTime("upload.wait.buffer")),
    WAIT_CHECK_INTERVAL(FileWatchUtil.getBufferTime("upload.wait.check.interval"));

    private long time;

    private FileWatchConfig(long time) {
        this.time = time;
    }

    public long getTime() {
        return this.time;
    }
}
