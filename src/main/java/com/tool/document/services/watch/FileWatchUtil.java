//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.document.services.watch;

import com.tool.common.resource.AppConfig;
import com.tool.db.sqlite.config.table.Metadata;
import com.tool.util.StringUtil;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileWatchUtil {
    private static Logger logger = LoggerFactory.getLogger(FileWatchService.class);

    public FileWatchUtil() {
    }

    public static String toHexa32(String plainText) {
        String resultHexa32 = null;

        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plainText.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);

            String hashText;
            for(hashText = bigInt.toString(16); hashText.length() < 32; hashText = "0" + hashText) {
            }

            resultHexa32 = hashText.toUpperCase();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return resultHexa32;
    }

    public static long getBufferTime(String key) {
        long defaultValue = Long.parseLong(AppConfig.getAppConfig(key));

        long value;
        try {
            value = (long)Integer.parseInt(AppConfig.getUserConfig(key).trim());
            if (value < defaultValue) {
                value = defaultValue;
            }
        } catch (Exception var6) {
            value = defaultValue;
        }

        return value * 1000L;
    }

    public static String getLocalFileName(Map dataMap) {
        String fileName = (String)dataMap.get("File");
        String objectId = (String)dataMap.get("Id");
        String baseName = FilenameUtils.getBaseName(fileName);
        String extension = FilenameUtils.getExtension(fileName);
        return baseName + "_" + toHexa32(objectId) + "." + extension;
    }

    public static Metadata transMapToMetadata(Map rowMap) {
        return new Metadata(getLocalFileName(rowMap), (String)rowMap.get("Document Name"), (String)rowMap.get("Rev"), (String)rowMap.get("Id"), (String)rowMap.get("Format"), (String)rowMap.get("File"));
    }

    public static HashMap transJsonToMap(JSONObject json) {
        HashMap rowMap = new HashMap();
        rowMap.put("Document Name", StringUtil.NVL(json.get("name")));
        rowMap.put("Rev", StringUtil.NVL(json.get("revision")));
        rowMap.put("State", StringUtil.NVL(json.get("current")));
        rowMap.put("Owner", StringUtil.NVL(json.get("owner")));
        rowMap.put("Title", StringUtil.NVL(json.get("Title")));
        rowMap.put("Originated", StringUtil.NVL(json.get("originated")));
        rowMap.put("File", StringUtil.NVL(json.get("format.file.name")));
        rowMap.put("Format", StringUtil.NVL(json.get("format.file.format")));
        rowMap.put("File Modified", StringUtil.NVL(json.get("format.file.modified")));
        rowMap.put("Ver", StringUtil.NVL(json.get("version")));
        rowMap.put("Locked", StringUtil.NVL(json.get("locked")));
        rowMap.put("Locker", StringUtil.NVL(json.get("locker")));
        rowMap.put("Id", StringUtil.NVL(json.get("id")));
        return rowMap;
    }
}
