package dev.tonimatas.fixermc.profiles;

import dev.tonimatas.fixermc.Constants;
import dev.tonimatas.fixermc.gui.profiles.ProfileView;
import dev.tonimatas.fixermc.libraries.LibraryInstaller;
import dev.tonimatas.fixermc.sessions.AccountManager;
import dev.tonimatas.fixermc.util.FixerDialogs;
import dev.tonimatas.fixermc.util.FixerUtils;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.flowarg.openlauncherlib.NoFramework;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Profile {
    public String name;
    public String version;
    public Loader loader;
    public String loaderVersion;
    public boolean downloaded;

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
        new Thread(() -> {
            getProfileView().changeState(State.DOWNLOADING);

            VanillaVersion version = new VanillaVersion.VanillaVersionBuilder()
                    .withName(this.version)
                    .build();

            FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder().withVanillaVersion(version).build();
            try {
                Path profilePath = Constants.PROFILES_FOLDER.resolve(name);

                updater.update(profilePath);
                FixerUtils.moveDirectory(profilePath.resolve("assets"), Constants.MINECRAFT_ASSETS);
                FixerUtils.moveDirectory(profilePath.resolve("libraries"), Constants.MINECRAFT_LIBRARIES);
                FixerUtils.moveDirectory(profilePath.resolve("natives"), Constants.MINECRAFT_NATIVES.resolve(getCompleteName()));
            } catch (Exception e) {
                FixerDialogs.showError("Error downloading the needed files. Try again later.\nProfile: " + name + "\n" + e.getMessage());
            }

            getProfileView().changeState(State.NORMAL);
            downloaded = true;
            ProfileManager.save();
        }).start();
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
                getProfileView().changeState(State.DOWNLOADING);
                update();
            }
        }

        return process;
    }

    public void delete() {
        getProfileView().changeState(State.DELETING);

        try {
            if (ProfileManager.selectedProfile.equals(name)) {
                ProfileManager.selectedProfile = "";
            }

            LibraryInstaller.deleteOldLibraries(Constants.PROFILES_FOLDER.resolve(name));
        } catch (IOException e) {
            FixerDialogs.showError("Error deleting profile: " + name);
        }
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
        return version + "-" + loaderVersion + "-" + loader;
    }

    private ProfileView getProfileView() {
        return ProfileManager.profilesViews.get(name);
    }
}
