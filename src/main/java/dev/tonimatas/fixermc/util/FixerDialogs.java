package dev.tonimatas.fixermc.util;

import javax.swing.*;

public class FixerDialogs {
    public static void showError(String message) {
        JOptionPane.showConfirmDialog(null, message, "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }
}
