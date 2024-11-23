package dev.tonimatas.fixermc.util;

import dev.tonimatas.fixermc.Main;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class FixerUtils {
    public static ImageIcon getImageIcon(String name) {
        URL imageIconURL;
        
        if (Main.developerMode) {
            try {
                imageIconURL = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "assets", name + ".png").toUri().toURL();
            } catch (MalformedURLException e) {
                imageIconURL = null;
            }
        } else {
            imageIconURL = FixerUtils.class.getResource("/assets/" + name + ".png");
        }

        if (imageIconURL == null) {
            System.out.println("Error loading icon: " + name);
            return null;
        } else {
            return new ImageIcon(imageIconURL);
        }
    }

    public static boolean hasLetter(String input) {
        for (char character : input.toCharArray()) {
            if (Character.isLetter(character)) {
                return true;
            }
        }
        return false;
    }
}
