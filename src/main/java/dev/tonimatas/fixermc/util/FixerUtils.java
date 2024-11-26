package dev.tonimatas.fixermc.util;

import com.google.gson.JsonObject;
import dev.tonimatas.fixermc.Constants;
import dev.tonimatas.fixermc.FixerMC;
import dev.tonimatas.fixermc.Main;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
    
    public static JsonObject getURLAsJsonObject(String urlString) {
        try {
            URL url = new URI(urlString).toURL();
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            return FixerMC.GSON.fromJson(response.toString(), JsonObject.class);
        } catch (URISyntaxException | IOException e) {
            return null;
        }
    }

    public static boolean downloadFile(String urlString, Path path, String hash) {
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            System.out.println("Error creating directory: " + path.getParent());
            return false;
        }
        
        if (Files.exists(path) && HashUtils.sha1(path).equals(hash)) {
            return true;
        }
        
        int tries = 3;
        
        for (int i = 0; i < tries; i++) {
            try {
                URL url = URI.create(urlString).toURL();
                URLConnection connection = url.openConnection();
                
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(path.toFile());

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                
                outputStream.close();
                return true;
            } catch (IOException ignored) {
            }
        }
        
        return false;
    }
    
    public static boolean downloadAsset(String hash) {
        Path path = Constants.MINECRAFT_ASSETS.resolve("objects").resolve(hash.substring(0, 2)).resolve(hash);
        String url = Constants.MINECRAFT_RESOURCES + hash.substring(0, 2) + "/" + hash;
        
        return downloadFile(url, path, hash);
    }
}
