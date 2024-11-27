package dev.tonimatas.fixermc.profiles;

import dev.tonimatas.fixermc.Constants;
import dev.tonimatas.fixermc.gui.profiles.ProfileView;
import dev.tonimatas.fixermc.sessions.AccountManager;
import dev.tonimatas.fixermc.util.FixerDialogs;
import dev.tonimatas.fixermc.util.FixerUtils;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.flowarg.openlauncherlib.NoFramework;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;

import javax.swing.*;
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
    }

    public void update() {
        VanillaVersion version = new VanillaVersion.VanillaVersionBuilder()
                .withName(this.version)
                .build();
        
        FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder().withVanillaVersion(version).build();
        try {
            updater.update(Constants.PROFILES_FOLDER.resolve(name));
            FixerUtils.moveDirectory(Constants.PROFILES_FOLDER.resolve(name).resolve("assets"), Constants.MINECRAFT_ASSETS);
            FixerUtils.moveDirectory(Constants.PROFILES_FOLDER.resolve(name).resolve("libraries"), Constants.MINECRAFT_LIBRARIES);
            FixerUtils.moveDirectory(Constants.PROFILES_FOLDER.resolve(name).resolve("natives"), Constants.MINECRAFT_NATIVES.resolve(getCompleteName()));
        } catch (Exception e) {
            FixerDialogs.showError("Error downloading the needed files. Try again later.\nProfile: " + name + "\n" + e.getMessage());
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

    public Process launch() {
        Process process = null;

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

            process = noFramework.launch(version, loaderVersion, NoFramework.ModLoader.VANILLA);
        } catch (Exception e) {
            String[] options = {"Setup", "Ignore"};
            
            int choice = JOptionPane.showOptionDialog(null, "Missing files.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
            
            if (choice == 0) {
                ProfileView view = ProfileManager.profilesViews.get(name);
                view.playKill.setText("Downloading...");
                view.playKill.setVisible(true);
                view.playKill.setBackground(Color.BLUE.brighter());
                update();
                view.launch();
            }
        }
        
        return process;
    }

    public String getText() {
        return "Name: " + name + "\n" + "Loader: " + loader.name + "\n" + (loader != Loader.VANILLA ? "Loader version: " + loaderVersion + "\n" : "") + "Version: " + version;
    }

    public Path getClientJar() {
        return Constants.PROFILES_FOLDER.resolve(name).resolve("client.jar");
    }

    public GameFolder getProfileGameFolder() {
        return new GameFolder(Constants.MINECRAFT_ASSETS.toString(),
                Constants.MINECRAFT_LIBRARIES.toString(),
                Constants.MINECRAFT_NATIVES.resolve(version).toString(),
                getClientJar().toString());
    }
    
    private String getCompleteName() {
        return version + "-" + loaderVersion + "-" +  loader;
    }
}
