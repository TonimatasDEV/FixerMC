package dev.tonimatas.fixermc.util;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.security.MessageDigest;

public class HashUtils {
    public static String sha1(Path path) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");

            try (FileInputStream fis = new FileInputStream(path.toFile())) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    digest.update(buffer, 0, bytesRead);
                }
            }

            byte[] sha1Bytes = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : sha1Bytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            return "";
        }
    }
}
