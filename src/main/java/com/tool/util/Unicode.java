//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.util;

public class Unicode {
    public Unicode() {
    }

    public static String decode(String unicode) {
        StringBuffer str = new StringBuffer();
        for(int i = unicode.indexOf("\\u"); i > -1; i = unicode.indexOf("\\u")) {
            char ch = (char)Integer.parseInt(unicode.substring(i + 2, i + 6), 16);
            str.append(unicode.substring(0, i));
            str.append(String.valueOf(ch));
            unicode = unicode.substring(i + 6);
        }

        str.append(unicode);
        return str.toString();
    }

    public static String encode(String unicode) {
        StringBuffer str = new StringBuffer();

        for(int i = 0; i < unicode.length(); ++i) {
            if (unicode.charAt(i) == ' ') {
                str.append(" ");
            } else {
                str.append("\\u");
                str.append(Integer.toHexString(unicode.charAt(i)));
            }
        }

        return str.toString();
    }
}
