package dev.tonimatas.fixermc;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
    public static final Path PROGRAM_FOLDER;
    public static final Path PROFILES_FOLDER;
    
    static {
        PROGRAM_FOLDER = Paths.get(System.getProperty("user.home"), "AppData", "Local", "Programs", "FixerMC");
        PROFILES_FOLDER = PROGRAM_FOLDER.resolve("profiles");
    }
}
