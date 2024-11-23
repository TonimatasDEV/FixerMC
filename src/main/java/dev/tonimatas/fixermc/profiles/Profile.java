package dev.tonimatas.fixermc.profiles;

import dev.tonimatas.fixermc.Constants;
import dev.tonimatas.fixermc.sessions.AccountManager;
import fr.flowarg.openlauncherlib.NoFramework;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;

import java.nio.file.Files;
import java.nio.file.Path;

public class Profile {
    public String name;
    public String version;
    public Loader loader;
    public String loaderVersion;
    public String description;
    
    public Profile(String name, String version, Loader loader, String loaderVersion, String description) {
        this.name = name;
        this.version = version;
        this.loader = loader;
        this.loaderVersion = loaderVersion;
        this.description = description;
    }
    
    public void update() {
        // TODO
    }
    
    public void launch() {
        Path gameDir = Constants.PROFILES_FOLDER.resolve(name);
        
        if (!Files.exists(gameDir)) {
            gameDir.toFile().mkdirs();
        }
        
        try {
            NoFramework noFramework = new NoFramework(Constants.PROFILES_FOLDER.resolve(name), AccountManager.accounts.get(AccountManager.selectedAccount).getAuthInfos(), getProfileGameFolder());

            noFramework.launch("1.21.3", "", NoFramework.ModLoader.VANILLA);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public String getText() {
        return "Name: " + name + "\n" + "Loader: " + loader.name + "\n" + (loader != Loader.VANILLA ? "Loader version: " + loaderVersion + "\n" : "")  + "Version: " + version + "\n" + "Description: " + description + "\n";
    }
    
    public Path getClientJar() {
        return Constants.MINECRAFT_VERSIONS; // TODO
    }

    public GameFolder getProfileGameFolder() {
        return new GameFolder(Constants.MINECRAFT_ASSETS.toString(),
                Constants.MINECRAFT_LIBRARIES.toString(),
                Constants.MINECRAFT_NATIVES.resolve(version).toString(),
                getClientJar().toString());
    }
}
