package dev.tonimatas.fixermc.util;

import com.google.gson.JsonObject;
import dev.tonimatas.fixermc.FixerMC;
import dev.tonimatas.fixermc.Main;
import dev.tonimatas.fixermc.libraries.LibraryInstaller;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

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

    public static void moveDirectory(Path source, Path target) throws IOException {
        if (!Files.exists(target)) {
            Files.createDirectories(target);
        }

        Files.walkFileTree(source, new SimpleFileVisitor<>() {
            @Override
            public @NotNull FileVisitResult visitFile(Path file, @NotNull BasicFileAttributes attrs) throws IOException {
                if (!file.toFile().isDirectory()) {
                    Path targetPath = target.resolve(source.relativize(file));
                    Files.createDirectories(targetPath.getParent());
                    Files.move(file, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public @NotNull FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                return FileVisitResult.CONTINUE;
            }
        });

        LibraryInstaller.deleteOldLibraries(source);
    }
}
