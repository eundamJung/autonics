//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.util;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

public class ExceptionUtil {
    public ExceptionUtil() {
    }

    public static JSONObject ifResponseErrorThrow(JSONObject obj) throws Exception {
        if (obj.containsKey("error")) {
            throw new Exception((String)((JSONObject)obj.get("error")).get("message"));
        } else {
            return obj;
        }
    }

    public static JSONObject createErrorJson(Exception e) {
        JSONObject message = new JSONObject();
        message.put("message", StringUtils.defaultString(e.getMessage(), e.toString()));
        JSONObject result = new JSONObject();
        result.put("error", message);
        return result;
    }
}
