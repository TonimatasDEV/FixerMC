package dev.tonimatas.fixermc.util;

import javax.swing.*;

public class FixerDialogs {
    public static void showError(String message) {
        createConfirmDialog("Error", message, JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }

    public static boolean showConfirm(String title, String message) {
        return createConfirmDialog(title, message,JOptionPane.CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
    
    @SuppressWarnings("MagicConstant")
    private static boolean createConfirmDialog(String title, String message, int option, int type) {
        return JOptionPane.showConfirmDialog(null, message, title, option, type) == 0;
    }
}
