//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.document.services.watch;

import com.tool.FileManager;
import com.tool.common.resource.AppConfig;
import com.tool.document.DocumentManager;
import com.tool.http.FileHttpUtil;
import com.tool.ui.common.Message;
import com.tool.ui.common.SwingUtils;
import com.tool.util.ExceptionUtil;
import com.tool.util.StringUtil;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;
import javax.swing.SwingUtilities;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;

public class DownloadService {
    private static final Set<String> downloading = Collections.synchronizedSet(new HashSet());

    public DownloadService() {
    }

    public static void download(DocumentManager documentManager, DownloadWorker worker) {
        Path filePath = worker.getLocalFilePath();
        String fileName = filePath.toString();
        if (downloading.contains(fileName)) {
            Message.showWarningMessage(documentManager, StringUtil.replaceStr(AppConfig.getString("message.download.downloading"), new String[]{FilenameUtils.getName(fileName)}));
        } else {
            if (Files.exists(filePath, new LinkOption[0]) && !Files.isDirectory(filePath, new LinkOption[0])) {
                String message = filePath + "\n" + AppConfig.getString("confirm.download.overwrite");
                if (0 != Message.showConfirmYesNo(documentManager, message)) {
                    return;
                }

                worker.setOverwriteFile(true);
            }

            SwingUtils.turnOnProgress(documentManager.listProgress);
            downloading.add(fileName);
            Future<DownloadWorker> futureTask = FileManager.executors.submit(worker);
            FileManager.executors.execute(() -> {
                try {
                    DownloadWorker resultWorker = (DownloadWorker)futureTask.get();
                    if (resultWorker.isSuccess()) {
                        try {
                            JSONObject info = ExceptionUtil.ifResponseErrorThrow(FileHttpUtil.getInfo(FileManager.session, resultWorker.getMetadata()));
                            resultWorker.getRowMap().putAll(FileWatchUtil.transJsonToMap((JSONObject)info.get("data")));
                            SwingUtilities.invokeLater(() -> {
                                documentManager.freezeTable.updateUI();
                            });
                        } catch (Exception var9) {
                        }

                        Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + fileName);
                        p.getOutputStream().close();
                        p.waitFor();
                    } else {
                        Message.showErrorMessage(documentManager, resultWorker.getMessage());
                    }
                } catch (Exception var10) {
                    Message.showErrorMessage(documentManager, var10.getMessage());
                } finally {
                    if (downloading.contains(fileName)) {
                        downloading.remove(fileName);
                    }

                    if (!isDownloading()) {
                        SwingUtils.turnOffProgress(documentManager.listProgress);
                    }

                }

            });
        }
    }

    public static boolean isDownloading() {
        return !downloading.isEmpty();
    }
}
