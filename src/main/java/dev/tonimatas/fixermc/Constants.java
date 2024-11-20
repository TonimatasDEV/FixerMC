package dev.tonimatas.fixermc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
    public static final Path PROGRAM_FOLDER;
    public static final Path PROFILES_FOLDER;
    public static final Path ACCOUNTS_JSON;
    public static final Gson GSON;
    
    static {
        PROGRAM_FOLDER = Paths.get(System.getProperty("user.home"), "AppData", "Local", "Programs", "FixerMC");
        PROFILES_FOLDER = PROGRAM_FOLDER.resolve("profiles");
        ACCOUNTS_JSON = PROFILES_FOLDER.resolve("accounts.json");
        GSON = new GsonBuilder().setPrettyPrinting().create();
    }
}
