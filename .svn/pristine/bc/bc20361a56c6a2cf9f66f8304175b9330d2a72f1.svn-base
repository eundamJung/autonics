//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tool.ui.common;

import java.awt.Component;
import javax.swing.JOptionPane;

public class Message {
    public Message() {
    }

    public static int showConfirmYesNo(Component parent, String message) {
        return showConfirmMessage(parent, message, 0);
    }

    public static int showConfirmYesNoCancle(Component parent, String message) {
        return showConfirmMessage(parent, message, 1);
    }

    private static int showConfirmMessage(Component parent, String message, int messageType) {
        return JOptionPane.showConfirmDialog(parent, message, "confirm", messageType);
    }

    public static void showErrorMessage(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "error", 0);
    }

    public static void showWarningMessage(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "waring", 2);
    }
}
