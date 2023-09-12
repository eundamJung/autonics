//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.util;

import com.tool.common.resource.AppConfig;
import com.tool.ui.common.SwingUtils;
import java.awt.Component;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class ProcessUtil {
    static AtomicBoolean isProcessing = new AtomicBoolean();
    private Component parentComponent;
    private JProgressBar progressBar;

    public ProcessUtil(Component parentComponent) {
        this(parentComponent, (JProgressBar)null);
    }

    public ProcessUtil(Component parentComponent, JProgressBar progressBar) {
        this.parentComponent = parentComponent;
        this.progressBar = progressBar;
    }

    public boolean can() {
        if (isProcessing.get()) {
            JOptionPane.showMessageDialog(this.parentComponent, AppConfig.getString("message.program.processing"), AppConfig.getString("fileManager"), 2);
            return false;
        } else {
            return true;
        }
    }

    public void start() {
        this.start(this.progressBar);
    }

    public void start(JProgressBar progressBar) {
        isProcessing.set(true);
        SwingUtils.turnOnProgress(progressBar);
    }

    public void end() {
        this.end(this.progressBar);
    }

    public void end(JProgressBar progressBar) {
        isProcessing.set(false);
        SwingUtils.turnOffProgress(progressBar);
    }
}
