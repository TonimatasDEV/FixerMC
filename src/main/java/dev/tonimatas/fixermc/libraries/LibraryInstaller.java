package dev.tonimatas.fixermc.libraries;

import dev.tonimatas.fixermc.Agent;
import dev.tonimatas.fixermc.Constants;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class LibraryInstaller {
    public static void init() {
        int max = LibraryManager.getResources(LibraryManager.Type.LIBRARIES).size();
        List<JarFile> jarFiles = new ArrayList<>();

        JProgressBar progressBar = new JProgressBar(0, max);
        JOptionPane optionPane = new JOptionPane(progressBar, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        JDialog dialog = optionPane.createDialog("Updating...");

        if (!isUpdated()) {
            try {
                deleteOldLibraries(Constants.PROGRAM_LIBRARIES);
            } catch (IOException ignored) {
            }

            new Thread(() -> dialog.setVisible(true)).start();
        }

        boolean update = true;

        for (String repository : LibraryManager.getResources(LibraryManager.Type.REPOSITORIES)) {
            for (String library : LibraryManager.getResources(LibraryManager.Type.LIBRARIES)) {

                String[] strings = library.split(":");
                String jarPath = strings[0].replace(".", "/") + "/" + strings[1] + "/" + strings[2];
                String jarName = strings[1] + "-" + strings[2] + ".jar";

                if (update) {
                    progressBar.setValue(progressBar.getValue() + 1);
                    update = false;
                }

                if (!new File(Constants.PROGRAM_LIBRARIES + "/" + jarPath + "/" + jarName).exists())
                    downloadLibrary(repository + jarPath, jarPath, jarName);

                JarFile jarFile = null;
                try {
                    jarFile = new JarFile(Constants.PROGRAM_LIBRARIES + "/" + jarPath + "/" + jarName);
                } catch (IOException ignored) {
                }

                if (jarFiles.contains(jarFile) || jarFile == null) continue;

                jarFiles.add(jarFile);
                try {
                    Agent.appendJarFile(jarFile);
                } catch (IOException e) {
                    System.out.println("Error on load libraries.");
                    throw new RuntimeException(e);
                }

                update = true;
            }
        }

        setUpdated();
        dialog.dispose();
    }

    private static void downloadLibrary(String url, String jarDirectory, String jarName) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(url + "/" + jarName).toURL().openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                new File(Constants.PROGRAM_LIBRARIES + "/" + jarDirectory).mkdirs();
                FileOutputStream outputStream = new FileOutputStream(Constants.PROGRAM_LIBRARIES + "/" + jarDirectory + "/" + jarName);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();
            }
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error on download library: " + jarName);
        }
    }

    private static void setUpdated() {
        try {
            Constants.LIBRARY_CHECK_FILE.toFile().createNewFile();
        } catch (IOException ignored) {
        }
    }

    private static boolean isUpdated() {
        return Files.exists(Constants.LIBRARY_CHECK_FILE);
    }

    public static void deleteOldLibraries(Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<>() {
            @Override
            public @NotNull FileVisitResult visitFile(Path file, @NotNull BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public @NotNull FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
