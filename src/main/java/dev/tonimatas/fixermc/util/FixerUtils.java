package dev.tonimatas.fixermc.util;

import javax.swing.*;
import java.net.URL;

public class FixerUtils {
    public static ImageIcon getImageIcon(String name) {
        URL imageIconURL = FixerUtils.class.getResource("/assets/icons/" + name + ".png");

        if (imageIconURL == null) {
            System.out.println("Error loading icon: " + name);
            return null;
        } else {
            return new ImageIcon(imageIconURL);
        }
    }
}
