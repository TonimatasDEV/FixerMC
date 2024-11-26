package dev.tonimatas.fixermc.profiles;

import com.google.gson.JsonObject;
import dev.tonimatas.fixermc.Constants;
import dev.tonimatas.fixermc.gui.profiles.ProfileView;
import dev.tonimatas.fixermc.profiles.downloader.FixerDownloader;
import dev.tonimatas.fixermc.sessions.AccountManager;
import dev.tonimatas.fixermc.util.FixerDialogs;
import dev.tonimatas.fixermc.util.FixerUtils;
import dev.tonimatas.fixermc.util.MCVersions;
import fr.flowarg.openlauncherlib.NoFramework;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Profile {
    public String name;
    public String version;
    public Loader loader;
    public String loaderVersion;
    public boolean downloaded;

    public Profile(String name, String version, Loader loader) {
        this(name, version, loader, "");
    }
    
    public Profile(String name, String version, Loader loader, String loaderVersion) {
        this(name, version, loader, loaderVersion, false);
    }
    
    public Profile(String name, String version, Loader loader, String loaderVersion, boolean downloaded) {
        this.name = name;
        this.version = version;
        this.loader = loader;
        this.loaderVersion = loaderVersion;
        this.downloaded = downloaded;
        update();
    }

    public void update() {
        new Thread(() -> {
            if (!downloaded) {
                JsonObject minecraftVersion = MCVersions.getMinecraftVersion(version);
                String versionUrl = minecraftVersion.get("url").getAsString();
                JsonObject versionJson = FixerUtils.getURLAsJsonObject(versionUrl);

                if (versionJson == null) {
                    FixerDialogs.showError("Error getting the version info. Try again later.\nProfile: " + name);
                    return;
                }

                if (!FixerDownloader.downloadLibraries(versionJson.getAsJsonArray("libraries"))) {
                    FixerDialogs.showError("Error downloading libraries. Try again later.\nProfile: " + name);
                    return;
                }

                if (!FixerDownloader.downloadAssets(versionJson.getAsJsonObject("assetIndex"))) {
                    FixerDialogs.showError("Error downloading libraries. Try again later.\nProfile: " + name);
                    return;
                }

                ProfileView view = ProfileManager.profilesViews.get(name);
                
                if (view != null) {
                    view.playKill.setText("Play");
                    view.playKill.setVisible(false);
                    view.playKill.setBackground(Color.GREEN.darker().darker());
                }
                
                downloaded = true;
                ProfileManager.save();
            }
        }).start();
    }

    public void launch() {
        Path gameDir = Constants.PROFILES_FOLDER.resolve(name);

        if (!Files.exists(gameDir)) {
            try {
                Files.createDirectories(gameDir);
            } catch (IOException e) {
                FixerDialogs.showError("Error creating directory: " + gameDir);
            }
        }

        try {
            NoFramework noFramework = new NoFramework(Constants.PROFILES_FOLDER.resolve(name), AccountManager.accounts.get(AccountManager.selectedAccount).getAuthInfos(), getProfileGameFolder());

            noFramework.launch("1.21.3", "", NoFramework.ModLoader.VANILLA);
        } catch (Exception e) {
            FixerDialogs.showError("Error launching profile: " + name);
        }
    }

    public String getText() {
        return "Name: " + name + "\n" + "Loader: " + loader.name + "\n" + (loader != Loader.VANILLA ? "Loader version: " + loaderVersion + "\n" : "") + "Version: " + version;
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
