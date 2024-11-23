package dev.tonimatas.fixermc.libraries;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManager {
    public static List<String> getResources(Type type) {
        List<String> resources = new ArrayList<>();

        InputStream inputStream = LibraryManager.class.getResourceAsStream("/" + type.toString().toLowerCase() + ".txt");

        if (inputStream == null) {
            System.out.println("Error on get " + type.toString().toLowerCase() + ".");
            return resources;
        }

        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNext()) {
            resources.add(scanner.next());
        }

        scanner.close();
        return resources;
    }

    public enum Type {
        LIBRARIES,
        REPOSITORIES
    }
}
