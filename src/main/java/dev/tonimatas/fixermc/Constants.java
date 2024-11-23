package dev.tonimatas.fixermc;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Constants {
    public static final Path PROGRAM_FOLDER;
    public static final Path PROGRAM_LIBRARIES;
    public static final Path PROFILES_FOLDER;
    public static final Path ACCOUNTS_JSON;
    public static final Path MINECRAFT_FOLDER;
    public static final Path MINECRAFT_LIBRARIES;
    public static final Path MINECRAFT_ASSETS;
    public static final Path MINECRAFT_NATIVES;
    public static final Path MINECRAFT_VERSIONS;
    public static final String MINECRAFT_VERSION_MANIFEST;
    public static final String VERSION;
    public static final Path LIBRARY_CHECK_FILE;
    
    static {
        PROGRAM_FOLDER = Paths.get(System.getProperty("user.home"), "AppData", "Local", "Programs", "FixerMC");
        PROGRAM_LIBRARIES = PROGRAM_FOLDER.resolve("libraries");
        PROFILES_FOLDER = PROGRAM_FOLDER.resolve("profiles");
        ACCOUNTS_JSON = PROFILES_FOLDER.resolve("accounts.json");
        MINECRAFT_FOLDER = PROGRAM_FOLDER.resolve("minecraft");
        MINECRAFT_LIBRARIES = MINECRAFT_FOLDER.resolve("libraries");
        MINECRAFT_ASSETS = MINECRAFT_FOLDER.resolve("assets");
        MINECRAFT_NATIVES = MINECRAFT_FOLDER.resolve("natives");
        MINECRAFT_VERSIONS = MINECRAFT_FOLDER.resolve("versions");
        MINECRAFT_VERSION_MANIFEST = "https://launchermeta.mojang.com/mc/game/version_manifest.json";
        
        Properties fixerProperties = new Properties();
        String versionResult;
        try {
            InputStream propertiesFile = Constants.class.getResourceAsStream("/fixer.properties");
            if (propertiesFile != null) {
                fixerProperties.load(propertiesFile);
                
                versionResult = fixerProperties.getProperty("VERSION");
            } else {
                versionResult = "null";
            }
        } catch (IOException e) {
            versionResult = "null";
            System.out.println("Error loading FixerMC properties");
        }
        
        VERSION = versionResult;
        LIBRARY_CHECK_FILE = PROGRAM_LIBRARIES.resolve(VERSION);
    }
}
