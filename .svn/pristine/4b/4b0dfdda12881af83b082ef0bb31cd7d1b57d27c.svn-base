//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.document.services.watch;

import com.tool.FileManager;
import com.tool.common.resource.AppConfig;
import com.tool.db.sqlite.config.table.Metadata;
import com.tool.document.DocumentManager;
import com.tool.http.FileHttpUtil;
import com.tool.ui.table.TableModel;
import com.tool.util.ExceptionUtil;
import com.tool.util.StringUtil;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadService implements Runnable {
    private Logger logger = LoggerFactory.getLogger(UploadService.class);
    private static final Set<String> uploading = Collections.synchronizedSet(new HashSet());
    private SimpleDateFormat dataFormat = new SimpleDateFormat(AppConfig.getAppConfig("dateformat.upload"));
    private DocumentManager documentManager;
    private final long waitBuffer;
    private final long interval;
    private Thread thread;
    private ThreadFactory threadFactory;
    private volatile boolean running;
    private final Vector<UploadWorker> waitingWorker = new Vector();

    public UploadService(DocumentManager documentManager) {
        this.documentManager = documentManager;
        this.interval = FileWatchConfig.WAIT_CHECK_INTERVAL.getTime();
        this.waitBuffer = FileWatchConfig.WAIT_BUFFER.getTime();
    }

    public synchronized void setThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    public synchronized void addWorker(UploadWorker uploadWorker) {
        String fileName = uploadWorker.getLocalFilePath().toString();
        if (!uploading.contains(fileName)) {
            boolean existsWorker = false;
            Iterator itr = this.waitingWorker.iterator();

            while(itr.hasNext()) {
                String fullName = ((UploadWorker)itr.next()).getLocalFilePath().toString();
                if (fullName.equals(uploadWorker.getLocalFilePath().toString())) {
                    existsWorker = true;
                    break;
                }
            }

            if (!existsWorker) {
                this.waitingWorker.add(uploadWorker);
                this.documentManager.waitTableModel.add(uploadWorker.getDataMap());
                SwingUtilities.invokeLater(() -> {
                    this.documentManager.waitRowFilterSet.getTableColumnAdjuster().adjustColumns();
                });
            }

        }
    }

    public void run() {
        while(true) {
            if (this.running) {
                Iterator itr = this.waitingWorker.iterator();

                while(itr.hasNext()) {
                    UploadWorker worker = (UploadWorker)itr.next();
                    Metadata metadata = worker.getMetadata();
                    String fullName = worker.getLocalFilePath().toString();
                    if (!uploading.contains(fullName)) {
                        try {
                            long lastModifiedTime = Files.getLastModifiedTime(worker.getLocalFilePath()).toMillis();
                            long currentTime = (new Date()).getTime();
                            worker.setWaitingTime((currentTime - lastModifiedTime) / 1000L);
                            if (currentTime > lastModifiedTime + this.waitBuffer) {
                                worker.setUploadDate(this.dataFormat.format(new Date()));
                                this.documentManager.waitTableModel.remove(worker.getDataMap());
                                this.documentManager.uploadTableModel.add(0, worker.getDataMap());
                                itr.remove();
                                uploading.add(fullName);
                                SwingUtilities.invokeLater(() -> {
                                    this.documentManager.uploadRowFilterSet.getTableColumnAdjuster().adjustColumns();
                                    this.documentManager.waitRowFilterSet.getTableColumnAdjuster().adjustColumns();
                                });
                                Future<UploadWorker> futureTask = FileManager.executors.submit(worker);
                                FileManager.executors.execute(() -> {
                                    try {
                                        Worker resultWorker = (Worker)futureTask.get();
                                        if (resultWorker.isSuccess()) {
                                            this.logger.info(AppConfig.getString("log.upload.complete"), metadata.getLocalFileName());
                                            FileManager.executors.execute(() -> {
                                                try {
                                                    JSONObject info = ExceptionUtil.ifResponseErrorThrow(FileHttpUtil.getInfo(FileManager.session, resultWorker.getMetadata()));
                                                    JSONObject data = (JSONObject)info.get("data");
                                                    String id = (String)data.get("id");
                                                    TableModel model = (TableModel)this.documentManager.freezeTable.getModel();
                                                    Iterator var7 = model.getRows().iterator();

                                                    while(var7.hasNext()) {
                                                        Map<String, Object> row = (Map)var7.next();
                                                        if (id.equals(row.get("Id"))) {
                                                            row.putAll(FileWatchUtil.transJsonToMap(data));
                                                            break;
                                                        }
                                                    }
                                                } catch (Exception var12) {
                                                    this.logger.info(AppConfig.getString("log.upload.info.fail"), metadata.getLocalFileName(), resultWorker.getMessage());
                                                } finally {
                                                    SwingUtilities.invokeLater(() -> {
                                                        this.documentManager.freezeTable.updateUI();
                                                    });
                                                }

                                            });
                                        } else {
                                            this.logger.info(AppConfig.getString("log.upload.fail"), metadata.getLocalFileName(), resultWorker.getMessage());
                                            JOptionPane.showMessageDialog(this.documentManager, StringUtil.replaceStr(AppConfig.getString("log.upload.fail"), new String[]{metadata.getLocalFileName(), resultWorker.getMessage()}), AppConfig.getString("execution.error"), 0);
                                        }
                                    } catch (Exception var8) {
                                        worker.setMessage(var8.getMessage());
                                    } finally {
                                        this.documentManager.uploadRowFilterSet.getTableColumnAdjuster().adjustColumns();
                                        uploading.remove(worker.getLocalFilePath().toString());
                                        SwingUtilities.invokeLater(() -> {
                                            this.documentManager.uploadRowFilterSet.getTableColumnAdjuster().adjustColumns();
                                            this.documentManager.waitRowFilterSet.getTableColumnAdjuster().adjustColumns();
                                        });
                                    }

                                });
                            }
                        } catch (Exception var11) {
                        }
                    }
                }

                SwingUtilities.invokeLater(() -> {
                    this.documentManager.waitTable.updateUI();
                    this.documentManager.uploadTable.updateUI();
                });
                if (this.running) {
                    try {
                        Thread.sleep(this.interval);
                    } catch (InterruptedException var10) {
                    }
                    continue;
                }
            }

            return;
        }
    }

    public synchronized void start() throws Exception {
        if (this.running) {
            throw new IllegalStateException(AppConfig.getString("message.upload.running"));
        } else {
            this.running = true;
            if (this.threadFactory != null) {
                this.thread = this.threadFactory.newThread(this);
            } else {
                this.thread = new Thread(this);
            }

            this.thread.start();
        }
    }

    public synchronized void stop() throws Exception {
        this.stop(this.interval);
    }

    public synchronized void stop(long stopInterval) throws Exception {
        if (!this.running) {
            throw new IllegalStateException(AppConfig.getString("message.upload.running"));
        } else {
            this.running = false;

            try {
                this.thread.interrupt();
                this.thread.join(stopInterval);
            } catch (InterruptedException var4) {
                Thread.currentThread().interrupt();
            }

        }
    }
}
