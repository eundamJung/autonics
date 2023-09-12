//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class StringUtil {
    static String REGEX_REPLACE_BRACE = "\\{\\}";

    public StringUtil() {
    }

    public static String replaceStr(String str, String... params) {
        if (params != null && params.length != 0) {
            int paramLength = params.length;
            StringBuffer sb = new StringBuffer();
            Pattern pattern = Pattern.compile(REGEX_REPLACE_BRACE);
            Matcher matcher = pattern.matcher(str);

            for(int idx = 0; matcher.find() && idx < paramLength; ++idx) {
                matcher.appendReplacement(sb, StringUtils.defaultString(params[idx]));
            }

            matcher.appendTail(sb);
            str = sb.toString();
            return str;
        } else {
            return str;
        }
    }

    public static String NVL(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return obj instanceof String ? (String)obj : obj.toString();
        }
    }
}
