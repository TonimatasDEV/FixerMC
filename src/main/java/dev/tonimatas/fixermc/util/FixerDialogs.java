package dev.tonimatas.fixermc.util;

import javax.swing.*;

public class FixerDialogs {
    public static void showError(String message) {
        JOptionPane.showConfirmDialog(null, message, "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }
    
    public static boolean showConfirm(String title, String message) {
        return JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Cancel", "Ok"}, "Ok") == 1;
    }
}
