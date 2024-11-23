package dev.tonimatas.fixermc;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Constants {
    public static final Path PROGRAM_FOLDER;
    public static final Path PROGRAM_LIBRARIES;
    public static final Path LIBRARY_CHECK_FILE;
    public static final Path PROFILES_FOLDER;
    public static final Path ACCOUNTS_JSON;
    public static final String VERSION;
    
    static {
        PROGRAM_FOLDER = Paths.get(System.getProperty("user.home"), "AppData", "Local", "Programs", "FixerMC");
        PROGRAM_LIBRARIES = PROGRAM_FOLDER.resolve("libraries");
        PROFILES_FOLDER = PROGRAM_FOLDER.resolve("profiles");
        ACCOUNTS_JSON = PROFILES_FOLDER.resolve("accounts.json");
        
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
